/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils;

import com.heypixel.heypixelmod.obsoverlay.events.impl.MovementInputEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;

public class PlayerRotationUtils {
    private static final Minecraft a = Minecraft.m_91087_();

    public static void a(MovementInputEvent l2, float f2) {
        float f3 = l2.a();
        float f4 = l2.b();
        if (f3 == 0.0f && f4 == 0.0f) {
            return;
        }
        if (PlayerRotationUtils.a.f_91074_ == null || f2 != f2 || Float.isInfinite(f2)) {
            return;
        }
        float f5 = Mth.m_14177_((float)(f2 - PlayerRotationUtils.a.f_91074_.m_146908_()));
        if (Math.abs(f5) < 1.0E-4f) {
            return;
        }
        double d2 = Math.toRadians(-f5);
        double d3 = Math.cos(d2);
        double d4 = Math.sin(d2);
        float f6 = (float)Math.hypot(f3, f4);
        float f7 = (float)((double)f3 * d3 - (double)f4 * d4);
        float f8 = (float)((double)f3 * d4 + (double)f4 * d3);
        float f9 = (float)Math.hypot(f7, f8);
        if (f9 > 1.0E-6f) {
            float f10 = f6 / f9;
            f7 *= f10;
            f8 *= f10;
        } else {
            f7 = f3;
            f8 = f4;
        }
        l2.a(f7);
        l2.b(f8);
    }

    public static boolean a() {
        return PlayerRotationUtils.a.f_91074_.f_108618_.f_108566_ != 0.0f || PlayerRotationUtils.a.f_91074_.f_108618_.f_108567_ != 0.0f || PlayerRotationUtils.a.f_91066_.f_92089_.m_90857_() || PlayerRotationUtils.a.f_91066_.f_92086_.m_90857_() || PlayerRotationUtils.a.f_91066_.f_92088_.m_90857_() || PlayerRotationUtils.a.f_91066_.f_92085_.m_90857_() || PlayerRotationUtils.a.f_91066_.f_92087_.m_90857_();
    }
}

