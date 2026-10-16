/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils.renderer;

public enum GuiQuadRenderer {
    a(2),
    b(3);

    public final int c;

    private GuiQuadRenderer(int n3) {
        this.c = n3;
    }

    public int a() {
        if (this == a) {
            return 1;
        }
        return this == b ? 4 : 0;
    }
}

