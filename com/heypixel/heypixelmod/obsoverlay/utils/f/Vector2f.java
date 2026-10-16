/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils.f;

import lombok.Generated;

public final class Vector2f {
    public float a;
    public float b;

    public Vector2f(Vector2f b2) {
        this(b2.a, b2.b);
    }

    public Vector2f a(float f2, float f3) {
        return new Vector2f(this.a + f2, this.b + f3);
    }

    @Generated
    public float a() {
        return this.a;
    }

    @Generated
    public float b() {
        return this.b;
    }

    @Generated
    public void a(float f2) {
        this.a = f2;
    }

    @Generated
    public void b(float f2) {
        this.b = f2;
    }

    @Generated
    public Vector2f(float f2, float f3) {
        this.a = f2;
        this.b = f3;
    }
}

