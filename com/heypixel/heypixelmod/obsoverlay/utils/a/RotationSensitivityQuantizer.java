/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils.a;

import com.heypixel.heypixelmod.obsoverlay.utils.c.RotationVector;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;

public final class RotationSensitivityQuantizer {
    private static final Minecraft a = Minecraft.m_91087_();
    private static final double b = 1.0E-7;
    private static final double c = 1.0 + Math.random() * 1.0E-7;
    private static float d = Float.NaN;
    private static float e = Float.NaN;
    private static float f = Float.NaN;
    private static float g = Float.NaN;
    private static float h = Float.NaN;
    private static float i = Float.NaN;

    private RotationSensitivityQuantizer() {
    }

    public static double a() {
        double d2 = (Double)com.heypixel.heypixelmod.obsoverlay.utils.SmoothValueInterpolator.c.a.f_91066_.m_231964_().m_231551_();
        double d3 = d2 * c * 0.6 + 0.2;
        return d3 * d3 * d3 * 8.0 * 0.15;
    }

    public static double b() {
        double d2 = (Double)com.heypixel.heypixelmod.obsoverlay.utils.SmoothValueInterpolator.c.a.f_91066_.m_231964_().m_231551_();
        double d3 = d2 * 0.6 + 0.2;
        return d3 * d3 * d3 * 8.0 * 0.15;
    }

    public static float a(float f2, float f3) {
        float f4;
        if (f2 == d && f3 == f && !Float.isNaN(h)) {
            return h;
        }
        double d2 = com.heypixel.heypixelmod.obsoverlay.utils.SmoothValueInterpolator.c.a();
        if (d2 <= 0.0) {
            f4 = f3;
        } else {
            double d3 = Mth.m_14177_((float)(f3 - f2));
            f4 = f2 + (float)((double)Math.round(d3 / d2) * d2);
        }
        d = f2;
        f = f3;
        h = f4;
        return f4;
    }

    public static float b(float f2, float f3) {
        float f4;
        if (f2 == e && f3 == g && !Float.isNaN(i)) {
            return i;
        }
        double d2 = com.heypixel.heypixelmod.obsoverlay.utils.SmoothValueInterpolator.c.a();
        if (d2 <= 0.0) {
            f4 = Mth.m_14036_((float)f3, (float)-90.0f, (float)90.0f);
        } else {
            double d3 = f3 - f2;
            f4 = Mth.m_14036_((float)(f2 + (float)((double)Math.round(d3 / d2) * d2)), (float)-90.0f, (float)90.0f);
        }
        e = f2;
        g = f3;
        i = f4;
        return f4;
    }

    public static void c() {
        d = Float.NaN;
        e = Float.NaN;
        f = Float.NaN;
        g = Float.NaN;
        h = Float.NaN;
        i = Float.NaN;
    }

    public static EntityRaycastTargetingUtils a(EntityRaycastTargetingUtils b2, EntityRaycastTargetingUtils b3) {
        if (b3 == null) {
            return null;
        }
        if (b2 == null) {
            return new EntityRaycastTargetingUtils(b3.a(), Mth.m_14036_((float)b3.b(), (float)-90.0f, (float)90.0f));
        }
        return new EntityRaycastTargetingUtils(com.heypixel.heypixelmod.obsoverlay.utils.SmoothValueInterpolator.c.a(b2.a(), b3.a()), com.heypixel.heypixelmod.obsoverlay.utils.SmoothValueInterpolator.c.b(b2.b(), b3.b()));
    }

    public static boolean a(float f2, double d2) {
        double d3 = com.heypixel.heypixelmod.obsoverlay.utils.SmoothValueInterpolator.c.b();
        if (d3 <= 0.0) {
            return true;
        }
        double d4 = Math.abs((double)f2 / d3 - (double)Math.round((double)f2 / d3));
        return d4 <= d2;
    }
}

