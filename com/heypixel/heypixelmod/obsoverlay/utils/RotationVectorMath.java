/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils;

import net.minecraft.util.Mth;

public class RotationVectorMath {
    public static final float a = (float)Math.PI;
    public static final float b = (float)Math.PI / 180;
    public static final float c = 57.295776f;
    public static final float[] d = new float[361];
    public static final float[] e = new float[361];

    public static int a(float f2) {
        return (int)(f2 % 360.0f + 360.0f) % 360;
    }

    static {
        for (int i2 = 0; i2 <= 360; ++i2) {
            RotationVectorMath.d[i2] = Mth.m_14089_((float)((float)i2 * ((float)Math.PI / 180)));
            RotationVectorMath.e[i2] = Mth.m_14031_((float)((float)i2 * ((float)Math.PI / 180)));
        }
    }
}

