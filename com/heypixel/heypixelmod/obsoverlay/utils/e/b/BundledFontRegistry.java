/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils.e.b;

import com.heypixel.heypixelmod.obsoverlay.utils.e.b.FontAssetLoader;
import io.github.humbleui.skija.Font;

public class BundledFontRegistry {
    private static final String a = "MaterialSymbolsRounded_Fill.ttf";
    private static final String b = "MaterialSymbolsRounded.ttf";
    private static final String c = "icon.ttf";
    private static final String d = "MiSans-Regular.ttf";
    private static final String e = "Urbanist-VariableFont_wght.ttf";

    public static void a() {
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.a.a(d, a, b);
    }

    public static Font a(float f2) {
        return com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.a.a(d, f2);
    }

    public static Font b(float f2) {
        return com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.a.a(a, f2);
    }

    public static Font c(float f2) {
        return com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.a.a(b, f2);
    }

    public static Font d(float f2) {
        return com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.a.a(e, f2);
    }

    public static Font e(float f2) {
        return com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.a.a(c, f2);
    }
}

