/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils.e.b;

import com.heypixel.heypixelmod.obsoverlay.utils.e.b.FontTypeDescriptor;
import io.github.humbleui.skija.Data;
import io.github.humbleui.skija.Font;
import io.github.humbleui.skija.Typeface;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class FontAssetLoader {
    private static final Map<String, Typeface> a = new ConcurrentHashMap<String, Typeface>();
    private static final Map<String, Font> b = new ConcurrentHashMap<String, Font>();

    private static Typeface a(String string2, FontTypeDescriptor b2) {
        return a.computeIfAbsent(string2, string -> com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.a.b(string, b2));
    }

    private static Typeface b(String string, FontTypeDescriptor b2) {
        Optional<Data> optional = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.d.a.b("/assets/heypixel/VcX6svVqmeT8/fonts/" + string);
        return optional.map(Typeface::makeFromData).orElseThrow(() -> new IllegalArgumentException("Font not found: " + string));
    }

    public static Font a(String string, float f2, FontTypeDescriptor b2) {
        String string2 = string + "@" + f2;
        Font font = b.get(string2);
        if (font != null) {
            return font;
        }
        Typeface typeface = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.a.a(string, b2);
        Font font2 = new Font(typeface, f2);
        b.put(string2, font2);
        return font2;
    }

    public static Font a(String string, float f2) {
        return com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.a.a(string, f2, com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.a.a(string));
    }

    private static FontTypeDescriptor a(String string) {
        String string2 = string.substring(string.lastIndexOf(46) + 1).toLowerCase();
        return com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.b.a(string2);
    }

    public static void a() {
        a.clear();
        b.clear();
    }

    public static void a(String ... stringArray) {
        for (String string : stringArray) {
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.a.a(string, com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.a.a(string));
        }
    }
}

