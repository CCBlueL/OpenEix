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
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.world.level.block.entity.BedBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;

@ModuleInfo(a="GhostBedMine", b="\u9694\u5899\u6316\u5e8a", c="Mines beds through walls", d=ModuleCategory.MISC)
public class GhostBedMine
extends ModuleBase {
    private BlockPos c = null;
    private Direction d = Direction.UP;
    private int e = 0;
    private boolean f = false;
    private int g = 0;

    @EventTarget
    public void onMotion(PlayerMotionEvent k2) {
        if (k2.b() != com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.a) {
            return;
        }
        if (GhostBedMine.a.f_91074_ == null || GhostBedMine.a.f_91073_ == null || a.m_91403_() == null) {
            return;
        }
        if (!GhostBedMine.a.f_91066_.f_92096_.m_90857_()) {
            this.p();
            return;
        }
        BlockPos blockPos = this.q();
        if (blockPos == null) {
            this.p();
            return;
        }
        if (!blockPos.equals((Object)this.c)) {
            this.p();
            this.c = blockPos;
            this.d = this.a(this.c);
            this.e = 0;
            this.f = false;
        }
        if (this.c == null) {
            return;
        }
        if (!this.f) {
            this.a(ServerboundPlayerActionPacket.Action.START_DESTROY_BLOCK);
            this.f = true;
            this.e = 0;
            return;
        }
        ++this.e;
        if (this.e >= 6) {
            this.a(ServerboundPlayerActionPacket.Action.STOP_DESTROY_BLOCK);
            this.p();
        }
    }

    private void a(ServerboundPlayerActionPacket.Action action) {
        if (a.m_91403_() == null || this.c == null) {
            return;
        }
        a.m_91403_().m_104955_((Packet)new ServerboundPlayerActionPacket(action, this.c, this.d, this.g++));
    }

    private void p() {
        this.c = null;
        this.d = Direction.UP;
        this.e = 0;
        this.f = false;
    }

    private BlockPos q() {
        double d2 = 4.5;
        Vec3 vec3 = GhostBedMine.a.f_91074_.m_20299_(1.0f);
        Vec3 vec32 = GhostBedMine.a.f_91074_.m_20252_(1.0f);
        for (double d3 = 0.1; d3 <= d2; d3 += 0.1) {
            Vec3 vec33 = vec3.m_82549_(vec32.m_82490_(d3));
            BlockPos blockPos = BlockPos.m_274446_((Position)vec33);
            BlockEntity blockEntity = GhostBedMine.a.f_91073_.m_7702_(blockPos);
            if (!(blockEntity instanceof BedBlockEntity) || !(GhostBedMine.a.f_91074_.m_20238_(Vec3.m_82512_((Vec3i)blockPos)) <= d2 * d2)) continue;
            return blockPos;
        }
        return null;
    }

    private Direction a(BlockPos blockPos) {
        Vec3 vec3 = GhostBedMine.a.f_91074_.m_20299_(1.0f);
        Vec3 vec32 = Vec3.m_82512_((Vec3i)blockPos);
        Vec3 vec33 = vec32.m_82546_(vec3).m_82541_();
        return Direction.m_122366_((double)vec33.f_82479_, (double)vec33.f_82480_, (double)vec33.f_82481_);
    }
}

