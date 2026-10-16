/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.render;

import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerMotionEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PacketInterceptEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.WorldRenderEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerRespawnEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;
import com.heypixel.heypixelmod.obsoverlay.utils.EntityOverlayRenderer;
import com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils;
import com.heypixel.heypixelmod.obsoverlay.utils.ChunkBlockEntityUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundBlockEventPacket;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.AABB;

@ModuleInfo(a="ChestESP", b="\u7bb1\u5b50\u900f\u89c6", c="Highlights chests", d=ModuleCategory.RENDER)
public class ChestESP
extends ModuleBase {
    private static final float[] c = new float[]{0.0f, 1.0f, 0.0f};
    private static final float[] d = new float[]{1.0f, 0.0f, 0.0f};
    private final List<BlockPos> e = new CopyOnWriteArrayList<BlockPos>();
    private final List<AABB> f = new CopyOnWriteArrayList<AABB>();

    @Override
    public void e() {
    }

    @EventTarget
    public void onRespawn(PlayerRespawnEvent u2) {
        this.e.clear();
    }

    @EventTarget
    public void onPacket(PacketInterceptEvent m2) {
        ClientboundBlockEventPacket clientboundBlockEventPacket;
        Packet<?> packet;
        if (m2.b() == com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.d && (packet = m2.c()) instanceof ClientboundBlockEventPacket && ((clientboundBlockEventPacket = (ClientboundBlockEventPacket)packet).m_131730_() == Blocks.f_50087_ || clientboundBlockEventPacket.m_131730_() == Blocks.f_50325_) && clientboundBlockEventPacket.m_131728_() == 1 && clientboundBlockEventPacket.m_131729_() == 1) {
            this.e.add(clientboundBlockEventPacket.m_131725_());
        }
    }

    @EventTarget
    public void onTick(PlayerMotionEvent k2) {
        if (k2.b() == com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.a) {
            ArrayList arrayList = ChunkBlockEntityUtils.a().collect(Collectors.toCollection(ArrayList::new));
            this.f.clear();
            for (BlockEntity blockEntity : arrayList) {
                ChestBlockEntity chestBlockEntity;
                AABB aABB;
                if (!(blockEntity instanceof ChestBlockEntity) || (aABB = this.a(chestBlockEntity = (ChestBlockEntity)blockEntity)) == null) continue;
                this.f.add(aABB);
            }
        }
    }

    private AABB a(ChestBlockEntity chestBlockEntity) {
        BlockPos blockPos;
        BlockState blockState = chestBlockEntity.m_58900_();
        if (!blockState.m_61138_((Property)ChestBlock.f_51479_)) {
            return null;
        }
        ChestType chestType = (ChestType)blockState.m_61143_((Property)ChestBlock.f_51479_);
        if (chestType == ChestType.LEFT) {
            return null;
        }
        BlockPos blockPos2 = chestBlockEntity.m_58899_();
        AABB aABB = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a(blockPos2);
        if (chestType != ChestType.SINGLE && com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.c(blockPos = blockPos2.m_121945_(ChestBlock.m_51584_((BlockState)blockState)))) {
            AABB aABB2 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a(blockPos);
            aABB = aABB.m_82367_(aABB2);
        }
        return aABB;
    }

    @EventTarget
    public void onRender(WorldRenderEvent p2) {
        PoseStack poseStack = p2.b();
        poseStack.m_85836_();
        RenderSystem.disableDepthTest();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::m_172808_);
        Tesselator tesselator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tesselator.m_85915_();
        for (AABB aABB : this.f) {
            BlockPos blockPos = BlockPos.m_274561_((double)aABB.f_82288_, (double)aABB.f_82289_, (double)aABB.f_82290_);
            float[] fArray = this.e.contains(blockPos) ? d : c;
            RenderSystem.setShaderColor((float)fArray[0], (float)fArray[1], (float)fArray[2], (float)0.25f);
            EntityOverlayRenderer.a(bufferBuilder, poseStack.m_85850_().m_252922_(), aABB);
        }
        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        poseStack.m_85849_();
    }
}

