/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.music.a;

public final class TimedLyricsBundle {
    public static final TimedLyricsBundle EMPTY = new TimedLyricsBundle("", "", "");
    private final String lrc;
    private final String translation;
    private final String yrc;

    public TimedLyricsBundle(String string, String string2, String string3) {
        this.lrc = string == null ? "" : string;
        this.translation = string2 == null ? "" : string2;
        this.yrc = string3 == null ? "" : string3;
    }

    public String a() {
        return this.lrc;
    }

    public String b() {
        return this.translation;
    }

    public String c() {
        return this.yrc;
    }

    public boolean d() {
        return this.lrc.isBlank();
    }
}

