/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules;

import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.ClickGUI;
import com.heypixel.heypixelmod.obsoverlay.utils.IconGlyphRegistry;

public enum ModuleCategory {
    a("Combat", "\u6218\u6597", IconGlyphRegistry.b),
    b("Movement", "\u79fb\u52a8", IconGlyphRegistry.c),
    c("Render", "\u89c6\u89c9", IconGlyphRegistry.d),
    d("Misc", "\u6742\u9879", IconGlyphRegistry.e);

    private final String e;
    private final String f;
    private final String g;

    private ModuleCategory(String string2, String string3, String string4) {
        this.e = string2;
        this.f = string3;
        this.g = string4;
    }

    public String a() {
        com.heypixel.heypixelmod.obsoverlay.EixClientCore a2 = com.heypixel.heypixelmod.obsoverlay.EixClientCore.a();
        if (a2 == null || a2.g() == null) {
            return this.e;
        }
        ClickGUI g2 = a2.g().a(ClickGUI.class);
        if (g2 == null || g2.c == null) {
            return this.e;
        }
        return g2.c.l().equals("English") ? this.e : this.f;
    }

    public String b() {
        return this.g;
    }
}

