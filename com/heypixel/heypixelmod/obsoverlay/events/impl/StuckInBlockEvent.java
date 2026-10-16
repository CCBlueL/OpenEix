/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.events.impl;

import com.heypixel.heypixelmod.obsoverlay.events.api.events.a.CancellableEventBase;
import java.util.Objects;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class StuckInBlockEvent
extends MovementInputState {
    private BlockState b;
    private Vec3 c;

    public StuckInBlockEvent(BlockState blockState, Vec3 vec3) {
        this.b = blockState;
        this.c = vec3;
    }

    public BlockState b() {
        return this.b;
    }

    public void a(BlockState blockState) {
        this.b = blockState;
    }

    public Vec3 c() {
        return this.c;
    }

    public void a(Vec3 vec3) {
        this.c = vec3;
    }

    public String toString() {
        return "EventStuckInBlock(state=" + String.valueOf(this.b()) + ", stuckSpeedMultiplier=" + String.valueOf(this.c()) + ")";
    }

    public boolean equals(Object object) {
        BlockState blockState;
        if (object == this) {
            return true;
        }
        if (!(object instanceof StuckInBlockEvent)) {
            return false;
        }
        StuckInBlockEvent ac2 = (StuckInBlockEvent)object;
        if (!ac2.a(this)) {
            return false;
        }
        BlockState blockState2 = this.b();
        if (Objects.equals(blockState2, blockState = ac2.b())) {
            Vec3 vec3 = this.c();
            Vec3 vec32 = ac2.c();
            return Objects.equals(vec3, vec32);
        }
        return false;
    }

    protected boolean a(Object object) {
        return object instanceof StuckInBlockEvent;
    }

    public int hashCode() {
        int n2 = 59;
        int n3 = 1;
        BlockState blockState = this.b();
        n3 = n3 * 59 + (blockState == null ? 43 : blockState.hashCode());
        Vec3 vec3 = this.c();
        return n3 * 59 + (vec3 == null ? 43 : vec3.hashCode());
    }
}

