/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.music.a;

import java.util.Locale;

public final class PlaybackTimelineModel {
    private final long id;
    private final String name;
    private final String artist;
    private final String album;
    private final String cover;
    private final long durationMillis;
    private final boolean free;
    private final int copyright;

    public PlaybackTimelineModel(long l2, String string, String string2, String string3, String string4, long l3, boolean bl, int n2) {
        this.id = l2;
        this.name = string == null ? "" : string;
        this.artist = string2 == null ? "" : string2;
        this.album = string3 == null ? "" : string3;
        this.cover = string4 == null ? "" : string4;
        this.durationMillis = l3;
        this.free = bl;
        this.copyright = n2;
    }

    public long a() {
        return this.id;
    }

    public String b() {
        return this.name;
    }

    public String c() {
        return this.artist;
    }

    public String d() {
        return this.album;
    }

    public String e() {
        return this.cover;
    }

    public long f() {
        return this.durationMillis;
    }

    public boolean g() {
        return this.free;
    }

    public int h() {
        return this.copyright;
    }

    public boolean i() {
        return this.copyright != 0;
    }

    public String j() {
        return this.artist.isEmpty() ? this.name : this.artist + " - " + this.name;
    }

    public static long a(String string) {
        if (string == null || string.isBlank()) {
            return -1L;
        }
        String[] stringArray = string.trim().split(":");
        try {
            long l2 = 0L;
            for (String string2 : stringArray) {
                l2 = l2 * 60L + Long.parseLong(string2.trim());
            }
            return l2 * 1000L;
        }
        catch (NumberFormatException numberFormatException) {
            return -1L;
        }
    }

    public String k() {
        if (this.durationMillis <= 0L) {
            return "--:--";
        }
        long l2 = this.durationMillis / 1000L;
        return String.format(Locale.ROOT, "%02d:%02d", l2 / 60L, l2 % 60L);
    }

    public String toString() {
        return this.j();
    }
}

