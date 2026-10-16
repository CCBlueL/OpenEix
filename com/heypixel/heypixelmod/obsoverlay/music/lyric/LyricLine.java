/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.music.lyric;

public final class LyricLine {
    private final long timeMs;
    private final String text;
    private final String translation;

    public LyricLine(long l2, String string, String string2) {
        this.timeMs = l2;
        this.text = string == null ? "" : string;
        this.translation = string2 == null ? "" : string2;
    }

    public long a() {
        return this.timeMs;
    }

    public String b() {
        return this.text;
    }

    public String c() {
        return this.translation;
    }

    public boolean d() {
        return !this.translation.isEmpty();
    }

    public boolean e() {
        return this.text.trim().isEmpty();
    }

    public String toString() {
        return "[" + this.timeMs + "]" + this.text;
    }
}

