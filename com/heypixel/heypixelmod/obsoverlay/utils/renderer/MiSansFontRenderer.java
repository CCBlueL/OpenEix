/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils.renderer;

import com.heypixel.heypixelmod.obsoverlay.utils.renderer.text.FontManager;
import java.awt.FontFormatException;
import java.io.IOException;

public class MiSansFontRenderer {
    public static FontManager a;
    public static FontManager b;

    public static void a() throws IOException, FontFormatException {
        a = new FontManager("MiSans-Regular", 32, 0, 65535, 16384);
        b = new FontManager("icon", 32, 59648, 59652, 512);
    }
}

