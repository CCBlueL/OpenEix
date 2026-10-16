/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.c.a;

import java.awt.Color;

public enum ToastSeverity {
    a(new Color(23, 150, 38, 255)),
    b(new Color(23, 22, 38, 255)),
    c(new Color(138, 90, 92, 255)),
    d(new Color(148, 42, 43, 255));

    private final Color e;

    private ToastSeverity(Color color) {
        this.e = color;
    }

    public Color a() {
        return this.e;
    }
}

