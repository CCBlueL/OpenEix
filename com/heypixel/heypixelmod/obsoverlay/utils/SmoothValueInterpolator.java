/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils;

public class SmoothValueInterpolator {
    public static int a;

    public static float a(float f2, float f3, float f4) {
        float f5 = (float)a * (f4 / 1000.0f);
        f2 = f2 < f3 ? (f2 + f5 < f3 ? (f2 += f5) : f3) : (f2 - f5 > f3 ? (f2 -= f5) : f3);
        return f2;
    }
}

