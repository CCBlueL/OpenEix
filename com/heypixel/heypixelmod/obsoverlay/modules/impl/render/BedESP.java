/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.render;

import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerMotionEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.WorldRenderEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerRespawnEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;
import com.heypixel.heypixelmod.obsoverlay.utils.EntityOverlayRenderer;
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
import net.minecraft.world.level.block.entity.BedBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;

@ModuleInfo(a="BedESP", b="\u5e8a\u900f\u89c6", c="Highlights beds", d=ModuleCategory.RENDER)
public class BedESP
extends ModuleBase {
    private static final float[] c = new float[]{1.0f, 0.0f, 0.0f};
    private final List<AABB> d = new CopyOnWriteArrayList<AABB>();

    @EventTarget
    public void onRespawn(PlayerRespawnEvent u2) {
        this.d.clear();
    }

    @EventTarget
    public void onTick(PlayerMotionEvent k2) {
        if (k2.b() == com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.a) {
            ArrayList arrayList = ChunkBlockEntityUtils.a().collect(Collectors.toCollection(ArrayList::new));
            this.d.clear();
            for (BlockEntity blockEntity : arrayList) {
                if (!(blockEntity instanceof BedBlockEntity)) continue;
                BedBlockEntity bedBlockEntity = (BedBlockEntity)blockEntity;
                AABB aABB = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a(bedBlockEntity.m_58899_());
                this.d.add(aABB);
            }
        }
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
        RenderSystem.setShaderColor((float)c[0], (float)c[1], (float)c[2], (float)0.25f);
        for (AABB aABB : this.d) {
            EntityOverlayRenderer.a(bufferBuilder, poseStack.m_85850_().m_252922_(), aABB);
        }
        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        poseStack.m_85849_();
    }
}

