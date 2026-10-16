/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.music.nowplaying;

import java.util.Locale;

public final class NowPlayingInfo {
    public static final NowPlayingInfo NONE = new NowPlayingInfo("", "", "", "", 0L, -1L, false, 0L, "");
    private final String appId;
    private final String title;
    private final String artist;
    private final String album;
    private final long positionMs;
    private final long durationMs;
    private final boolean playing;
    private final long capturedAt;
    private final String source;
    private final long songId;

    public NowPlayingInfo(String string, String string2, String string3, String string4, long l2, long l3, boolean bl, long l4, String string5) {
        this(string, string2, string3, string4, l2, l3, bl, l4, string5, -1L);
    }

    public NowPlayingInfo(String string, String string2, String string3, String string4, long l2, long l3, boolean bl, long l4, String string5, long l5) {
        this.appId = NowPlayingInfo.a(string);
        this.title = NowPlayingInfo.a(string2);
        this.artist = NowPlayingInfo.a(string3);
        this.album = NowPlayingInfo.a(string4);
        this.positionMs = Math.max(0L, l2);
        this.durationMs = l3;
        this.playing = bl;
        this.capturedAt = l4 <= 0L ? System.currentTimeMillis() : l4;
        this.source = NowPlayingInfo.a(string5);
        this.songId = l5;
    }

    private static String a(String string) {
        return string == null ? "" : string.trim();
    }

    public String a() {
        return this.appId;
    }

    public String b() {
        return this.title;
    }

    public String c() {
        return this.artist;
    }

    public String d() {
        return this.album;
    }

    public long e() {
        return this.positionMs;
    }

    public long f() {
        return this.durationMs;
    }

    public boolean g() {
        return this.playing;
    }

    public long h() {
        return this.capturedAt;
    }

    public String i() {
        return this.source;
    }

    public long j() {
        return this.songId;
    }

    public boolean k() {
        return this.title.isEmpty();
    }

    public boolean l() {
        return this.durationMs > 0L;
    }

    public long m() {
        if (!this.playing) {
            return this.positionMs;
        }
        long l2 = System.currentTimeMillis() - this.capturedAt;
        if (l2 < 0L || l2 > 15000L) {
            return this.positionMs;
        }
        long l3 = this.positionMs + l2;
        if (this.durationMs > 0L && l3 > this.durationMs) {
            l3 = this.durationMs;
        }
        return l3;
    }

    public String n() {
        return (this.title + "\u0000" + this.artist).toLowerCase(Locale.ROOT);
    }

    public String o() {
        if (this.k()) {
            return "";
        }
        return this.artist.isEmpty() ? this.title : this.artist + " - " + this.title;
    }

    public boolean p() {
        String string = this.appId.toLowerCase(Locale.ROOT);
        return string.contains("cloudmusic") || string.contains("netease") || string.contains("qqmusic") || string.contains("kugou") || string.contains("kuwo") || string.contains("spotify") || string.contains("foobar") || string.contains("aimp") || string.contains("music") || string.contains("player");
    }

    public NowPlayingInfo a(long l2) {
        return new NowPlayingInfo(this.appId, this.title, this.artist, this.album, l2, this.durationMs, this.playing, System.currentTimeMillis(), this.source, this.songId);
    }

    public NowPlayingInfo a(boolean bl) {
        return new NowPlayingInfo(this.appId, this.title, this.artist, this.album, this.positionMs, this.durationMs, bl, this.capturedAt, this.source, this.songId);
    }

    public NowPlayingInfo b(long l2) {
        return new NowPlayingInfo(this.appId, this.title, this.artist, this.album, this.positionMs, this.durationMs, this.playing, this.capturedAt, this.source, l2);
    }

    public NowPlayingInfo c(long l2) {
        return new NowPlayingInfo(this.appId, this.title, this.artist, this.album, this.positionMs, l2, this.playing, this.capturedAt, this.source, this.songId);
    }

    public String toString() {
        return "NowPlaying{" + this.appId + " | " + this.title + " - " + this.artist + " | " + this.positionMs + "/" + this.durationMs + " | playing=" + this.playing + " | id=" + this.songId + " | " + this.source + "}";
    }
}

