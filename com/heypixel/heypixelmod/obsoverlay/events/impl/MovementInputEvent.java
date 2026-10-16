/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.events.impl;

import com.heypixel.heypixelmod.obsoverlay.events.api.events.Event;

public class MovementInputEvent
implements Event {
    public static Object a;
    public static Object b;
    private float c;
    private float d;
    private boolean e;
    private boolean f;
    private double g;

    public MovementInputEvent(float f2, float f3, boolean bl, boolean bl2, double d2) {
        this.c = f2;
        this.d = f3;
        this.e = bl;
        this.f = bl2;
        this.g = d2;
    }

    public float a() {
        return this.c;
    }

    public void a(float f2) {
        this.c = f2;
    }

    public float b() {
        return this.d;
    }

    public void b(float f2) {
        this.d = f2;
    }

    public boolean c() {
        return this.e;
    }

    public void a(boolean bl) {
        this.e = bl;
    }

    public boolean d() {
        return this.f;
    }

    public void b(boolean bl) {
        this.f = bl;
    }

    public double e() {
        return this.g;
    }

    public void a(double d2) {
        this.g = d2;
    }
}

