/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils.e.b;

public enum FontTypeDescriptor {
    a("ttf"),
    b("otf");

    private final String c;

    private FontTypeDescriptor(String string2) {
        this.c = string2;
    }

    public static FontTypeDescriptor a(String string) {
        String string2;
        return switch (string2 = string.toLowerCase()) {
            case "ttf" -> a;
            case "otf" -> b;
            default -> throw new IllegalArgumentException("Unsupported font type: " + string);
        };
    }

    public String toString() {
        return this.c;
    }
}

