/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils;

import com.heypixel.heypixelmod.obsoverlay.utils.SmoothValueInterpolator;

public class RotationInterpolation {
    public float a;
    public float b = 0.4f;
    public float c;

    public RotationInterpolation(float f2) {
        this.a = f2;
        this.c = f2;
    }

    public RotationInterpolation(float f2, float f3) {
        this.a = f2;
        this.c = f3;
    }

    public RotationInterpolation(float f2, float f3, float f4) {
        this.a = f2;
        this.b = f4;
        this.c = f3;
    }

    public void a(boolean bl) {
        this.c = com.heypixel.heypixelmod.obsoverlay.utils.SmoothValueInterpolator.a(this.c, bl ? this.a : 0.0f, Math.max(10.0f, Math.abs(this.c - (bl ? this.a : 0.0f)) * 40.0f) * this.b);
    }

    public boolean b(boolean bl) {
        return bl ? this.c == this.a : this.c == 0.0f;
    }
}

