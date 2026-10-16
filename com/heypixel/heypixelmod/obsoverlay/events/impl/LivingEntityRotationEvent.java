/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.events.impl;

import com.heypixel.heypixelmod.obsoverlay.events.api.events.Event;
import net.minecraft.world.entity.Entity;

public class LivingEntityRotationEvent
implements Event {
    public static Entity a;
    private float b;
    private float c;
    private float d;
    private float e;

    public LivingEntityRotationEvent(float f2, float f3, float f4, float f5) {
        this.b = f2;
        this.c = f3;
        this.d = f4;
        this.e = f5;
    }

    public float a() {
        return this.b;
    }

    public void a(float f2) {
        this.b = f2;
    }

    public float b() {
        return this.c;
    }

    public void b(float f2) {
        this.c = f2;
    }

    public float c() {
        return this.d;
    }

    public void c(float f2) {
        this.d = f2;
    }

    public float d() {
        return this.e;
    }

    public void d(float f2) {
        this.e = f2;
    }
}

