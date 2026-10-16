/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils.a;

import com.heypixel.heypixelmod.obsoverlay.utils.a.HumanizedRandomTimingUtils;
import net.minecraft.client.Minecraft;

public final class SprintResetController {
    private static final Minecraft a = Minecraft.m_91087_();
    private static int b = -1;

    private SprintResetController() {
    }

    public static boolean a() {
        if (SprintResetController.a.f_91074_ == null || !SprintResetController.a.f_91074_.m_20142_()) {
            return false;
        }
        SprintResetController.a.f_91074_.m_6858_(false);
        b = com.heypixel.heypixelmod.obsoverlay.utils.SmoothValueInterpolator.a.a(1, 2);
        return true;
    }

    public static void b() {
        boolean bl;
        if (b < 0) {
            return;
        }
        if (--b > 0) {
            return;
        }
        b = -1;
        if (SprintResetController.a.f_91074_ == null) {
            return;
        }
        boolean bl2 = bl = SprintResetController.a.f_91066_.f_92091_.m_90857_() || SprintResetController.a.f_91066_.f_92085_.m_90857_() && !SprintResetController.a.f_91074_.m_6144_();
        if (bl && !SprintResetController.a.f_91074_.m_20142_()) {
            SprintResetController.a.f_91074_.m_6858_(true);
        }
    }

    public static boolean c() {
        return b >= 0;
    }

    public static void d() {
        boolean bl;
        b = -1;
        if (SprintResetController.a.f_91074_ == null) {
            return;
        }
        boolean bl2 = bl = SprintResetController.a.f_91066_.f_92091_.m_90857_() || SprintResetController.a.f_91066_.f_92085_.m_90857_() && !SprintResetController.a.f_91074_.m_6144_();
        if (bl && !SprintResetController.a.f_91074_.m_20142_()) {
            SprintResetController.a.f_91074_.m_6858_(true);
        }
    }

    public static void e() {
        b = -1;
    }
}

