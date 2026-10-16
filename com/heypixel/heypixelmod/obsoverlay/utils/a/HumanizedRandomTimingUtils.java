/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils.a;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public final class HumanizedRandomTimingUtils {
    private static final Random a = new Random(System.nanoTime());
    private static final double b = 0.07;
    private static final double c = 0.13;

    private HumanizedRandomTimingUtils() {
    }

    private static double b() {
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        return (threadLocalRandom.nextDouble() + threadLocalRandom.nextDouble() + threadLocalRandom.nextDouble()) / 3.0 - 0.5;
    }

    public static long a(double d2) {
        if (d2 <= 0.0) {
            d2 = 1.0;
        }
        double d3 = 1000.0 / d2;
        double d4 = d3 * (1.0 + com.heypixel.heypixelmod.obsoverlay.utils.SmoothValueInterpolator.a.b() * 0.55);
        double d5 = a.nextDouble();
        if (d5 < 0.07) {
            d4 += d3 * (0.35 + a.nextDouble() * 0.85);
        } else if (d5 > 0.87) {
            d4 -= d3 * (0.15 + a.nextDouble() * 0.2);
        }
        return Math.max(1L, (long)d4);
    }

    public static int a(int n2) {
        if (n2 <= 0) {
            return 0;
        }
        double d2 = (double)n2 + com.heypixel.heypixelmod.obsoverlay.utils.SmoothValueInterpolator.a.b() * 1.6;
        if (a.nextDouble() < 0.1) {
            d2 += 1.0 + a.nextDouble();
        }
        return Math.max(0, (int)Math.round(d2));
    }

    public static boolean b(double d2) {
        return a.nextDouble() < d2;
    }

    public static float a(float f2, float f3) {
        return (float)((double)f2 * (1.0 + com.heypixel.heypixelmod.obsoverlay.utils.SmoothValueInterpolator.a.b() * (double)f3 * 2.0));
    }

    public static double a(double d2, double d3) {
        return d2 + a.nextDouble() * (d3 - d2);
    }

    public static int a(int n2, int n3) {
        if (n3 <= n2) {
            return n2;
        }
        return n2 + a.nextInt(n3 - n2 + 1);
    }

    public static Random a() {
        return a;
    }
}

