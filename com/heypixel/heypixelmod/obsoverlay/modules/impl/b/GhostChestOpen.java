/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.b;

import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerMotionEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.Vec3i;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerboundUseItemOnPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.block.entity.BarrelBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

@ModuleInfo(a="GhostChestOpen", b="\u9694\u7a7a\u5f00\u7bb1", c="Opens containers through walls", d=ModuleCategory.MISC)
public class GhostChestOpen
extends ModuleBase {
    private int c = 0;
    private boolean d = false;
    private boolean e = false;
    private int f = 0;

    @EventTarget
    public void onMotion(PlayerMotionEvent k2) {
        if (k2.b() != com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.a) {
            return;
        }
        if (GhostChestOpen.a.f_91074_ == null || GhostChestOpen.a.f_91073_ == null || a.m_91403_() == null) {
            return;
        }
        if (GhostChestOpen.a.f_91080_ != null) {
            this.e = false;
            this.f = 0;
            this.d = true;
            return;
        }
        if (this.e) {
            ++this.f;
            if (this.f > 20) {
                this.e = false;
                this.f = 0;
                this.d = false;
            }
            return;
        }
        if (!GhostChestOpen.a.f_91066_.f_92095_.m_90857_()) {
            this.d = false;
            return;
        }
        if (this.d) {
            return;
        }
        BlockPos blockPos = this.p();
        if (blockPos == null) {
            return;
        }
        Vec3 vec3 = GhostChestOpen.a.f_91074_.m_20299_(1.0f);
        Vec3 vec32 = Vec3.m_82512_((Vec3i)blockPos);
        Vec3 vec33 = vec32.m_82546_(vec3).m_82541_();
        Direction direction = Direction.m_122366_((double)vec33.f_82479_, (double)vec33.f_82480_, (double)vec33.f_82481_);
        BlockHitResult blockHitResult = new BlockHitResult(vec32, direction, blockPos, false);
        a.m_91403_().m_104955_((Packet)new ServerboundUseItemOnPacket(InteractionHand.MAIN_HAND, blockHitResult, this.c++));
        this.d = true;
        this.e = true;
        this.f = 0;
    }

    private BlockPos p() {
        double d2 = 4.5;
        Vec3 vec3 = GhostChestOpen.a.f_91074_.m_20299_(1.0f);
        Vec3 vec32 = GhostChestOpen.a.f_91074_.m_20252_(1.0f);
        for (double d3 = 0.1; d3 <= d2; d3 += 0.1) {
            Vec3 vec33 = vec3.m_82549_(vec32.m_82490_(d3));
            BlockPos blockPos = BlockPos.m_274446_((Position)vec33);
            BlockEntity blockEntity = GhostChestOpen.a.f_91073_.m_7702_(blockPos);
            if (!(blockEntity instanceof ChestBlockEntity) && !(blockEntity instanceof ShulkerBoxBlockEntity) && !(blockEntity instanceof BarrelBlockEntity) || !(GhostChestOpen.a.f_91074_.m_20238_(Vec3.m_82512_((Vec3i)blockPos)) <= d2 * d2)) continue;
            return blockPos;
        }
        return null;
    }
}

