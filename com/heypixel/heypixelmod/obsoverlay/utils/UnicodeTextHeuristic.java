/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils;

public class UnicodeTextHeuristic {
    public static boolean a(String string) {
        if (string != null && !string.isEmpty()) {
            for (char c2 : string.toCharArray()) {
                if (c2 <= '\u4e00') continue;
                return true;
            }
            return false;
        }
        return false;
    }
}

