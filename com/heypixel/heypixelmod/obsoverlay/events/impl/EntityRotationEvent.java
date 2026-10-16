/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.events.impl;

import com.heypixel.heypixelmod.obsoverlay.events.api.events.Event;
import net.minecraft.world.entity.Entity;

public class EntityRotationEvent
implements Event {
    public Entity a;
    public float b;
    public float c;

    public EntityRotationEvent(Entity entity, float f2, float f3) {
        this.a = entity;
        this.b = f2;
        this.c = f3;
    }

    public Entity a() {
        return this.a;
    }

    public void a(Entity entity) {
        this.a = entity;
    }

    public float b() {
        return this.b;
    }

    public void a(float f2) {
        this.b = f2;
    }

    public float c() {
        return this.c;
    }

    public void b(float f2) {
        this.c = f2;
    }
}

