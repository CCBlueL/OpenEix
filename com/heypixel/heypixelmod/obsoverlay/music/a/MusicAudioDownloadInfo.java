/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.music.a;

import java.util.Locale;

public final class MusicAudioDownloadInfo {
    private final long id;
    private final String url;
    private final String level;
    private final int bitrate;
    private final long size;
    private final String md5;
    private final String channelLayout;
    private final String cookieLabel;

    public MusicAudioDownloadInfo(long l2, String string, String string2, int n2, long l3, String string3, String string4, String string5) {
        this.id = l2;
        this.url = string == null ? "" : string;
        this.level = string2 == null ? "" : string2;
        this.bitrate = n2;
        this.size = l3;
        this.md5 = string3 == null ? "" : string3;
        this.channelLayout = string4 == null ? "" : string4;
        this.cookieLabel = string5 == null ? "" : string5;
    }

    public long a() {
        return this.id;
    }

    public String b() {
        return this.url;
    }

    public String c() {
        return this.level;
    }

    public int d() {
        return this.bitrate;
    }

    public long e() {
        return this.size;
    }

    public String f() {
        return this.md5;
    }

    public String g() {
        return this.channelLayout;
    }

    public String h() {
        return this.cookieLabel;
    }

    public boolean i() {
        return this.url.isBlank();
    }

    public String j() {
        String string;
        int n2;
        String string2 = this.url.toLowerCase(Locale.ROOT);
        int n3 = string2.indexOf(63);
        if (n3 >= 0) {
            string2 = string2.substring(0, n3);
        }
        if ((n2 = string2.lastIndexOf(46)) >= 0 && n2 < string2.length() - 1 && (string = string2.substring(n2 + 1)).length() <= 5) {
            return string;
        }
        return "mp3";
    }
}

