/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils;

import java.awt.Color;

public enum ArgbColorPalette {
    a(-16711423),
    b(-12028161),
    c(-12621684),
    d(-9830551),
    e(-9320847),
    f(-65794),
    g(-7820064),
    h(-12621684),
    i(-9868951),
    j(-14342875),
    k(-65536),
    l(-8388608),
    m(-29696),
    n(-2263808),
    o(-256),
    p(-2702025),
    q(-18751),
    r(-2252579);

    public int s;

    private ArgbColorPalette(int n3) {
        this.s = n3;
    }

    public static int a(Color color) {
        return com.heypixel.heypixelmod.obsoverlay.utils.ArgbColorPalette.a(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public static int a(int n2) {
        return com.heypixel.heypixelmod.obsoverlay.utils.ArgbColorPalette.a(n2, n2, n2, 255);
    }

    public static int a(int n2, int n3) {
        return com.heypixel.heypixelmod.obsoverlay.utils.ArgbColorPalette.a(n2, n2, n2, n3);
    }

    public static int a(int n2, int n3, int n4) {
        return com.heypixel.heypixelmod.obsoverlay.utils.ArgbColorPalette.a(n2, n3, n4, 255);
    }

    public static int a(int n2, int n3, int n4, int n5) {
        int n6 = 0;
        n6 |= n5 << 24;
        n6 |= n2 << 16;
        return (n6 |= n3 << 8) | n4;
    }
}

