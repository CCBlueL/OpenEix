/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils.f;

import lombok.Generated;

public final class Vector2d {
    public double a;
    public double b;

    public Vector2d() {
    }

    public Vector2d(double d2, double d3) {
        this.a = d2;
        this.b = d3;
    }

    public Vector2d a(double d2, double d3) {
        return new Vector2d(this.a + d2, this.b + d3);
    }

    public Vector2d a(Vector2d a2) {
        return this.a(a2.a, a2.b);
    }

    @Generated
    public double a() {
        return this.a;
    }

    @Generated
    public double b() {
        return this.b;
    }

    @Generated
    public void a(double d2) {
        this.a = d2;
    }

    @Generated
    public void b(double d2) {
        this.b = d2;
    }
}

