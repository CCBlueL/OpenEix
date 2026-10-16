/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.music;

import com.heypixel.heypixelmod.obsoverlay.music.a.PlaybackTimelineModel;
import com.heypixel.heypixelmod.obsoverlay.music.MusicPlaybackEngine;
import com.heypixel.heypixelmod.obsoverlay.music.MusicTrackMetadata;
import com.heypixel.heypixelmod.obsoverlay.music.nowplaying.NowPlayingInfo;
import com.heypixel.heypixelmod.obsoverlay.music.nowplaying.NowPlayingStatusManager;

public final class MusicPlayerController {
    private static volatile MusicPlayerController instance;
    private volatile String title = "";
    private volatile String artist = "";
    private volatile String sourceApp = "";
    private volatile long positionMs;
    private volatile long durationMs;
    private volatile boolean playing;
    private volatile boolean fromLocal;
    private volatile boolean active;
    private volatile long songId = -1L;

    private MusicPlayerController() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static MusicPlayerController a() {
        if (instance != null) return instance;
        Class<MusicPlayerController> clazz = MusicPlayerController.class;
        synchronized (MusicPlayerController.class) {
            if (instance != null) return instance;
            instance = new MusicPlayerController();
            // ** MonitorExit[var0] (shouldn't be in output)
            return instance;
        }
    }

    public void b() {
        try {
            this.m();
        }
        catch (Throwable throwable) {
            this.title = "";
            this.artist = "";
            this.sourceApp = "";
            this.positionMs = 0L;
            this.durationMs = -1L;
            this.playing = false;
            this.fromLocal = false;
            this.active = false;
            this.songId = -1L;
        }
    }

    private void m() {
        com.heypixel.heypixelmod.obsoverlay.music.MusicLibraryManager c2 = com.heypixel.heypixelmod.obsoverlay.music.MusicLibraryManager.a();
        com.heypixel.heypixelmod.obsoverlay.music.MusicPlaybackEngine b2 = c2.c();
        i i2 = c2.o();
        if (b2.d() && i2 != null) {
            this.title = i2.b();
            this.artist = i2.c();
            this.sourceApp = "\u5728\u7ebf\u64ad\u653e";
            this.positionMs = b2.k();
            long l2 = b2.j();
            this.durationMs = l2 > 0L ? l2 : i2.f();
            this.playing = b2.c() == b.a.PLAYING;
            this.fromLocal = true;
            this.active = true;
            this.songId = i2.a();
        } else {
            MusicTrackMetadata d2 = c2.h();
            if (b2.d() && d2 != null) {
                this.title = d2.b();
                this.artist = d2.c();
                this.sourceApp = "\u672c\u5730\u64ad\u653e\u5668";
                this.positionMs = b2.k();
                this.durationMs = b2.j();
                this.playing = b2.c() == b.a.PLAYING;
                this.fromLocal = true;
                this.active = true;
                this.songId = -1L;
            } else {
                b b3 = NowPlayingStatusManager.a().i();
                if (b3.k()) {
                    this.title = "";
                    this.artist = "";
                    this.sourceApp = "";
                    this.positionMs = 0L;
                    this.durationMs = -1L;
                    this.playing = false;
                    this.fromLocal = false;
                    this.active = false;
                    this.songId = -1L;
                } else {
                    this.title = b3.b();
                    this.artist = b3.c();
                    this.sourceApp = b3.a();
                    this.positionMs = b3.m();
                    this.durationMs = b3.f();
                    this.playing = b3.g();
                    this.fromLocal = false;
                    this.active = true;
                    this.songId = b3.j();
                }
            }
        }
        com.heypixel.heypixelmod.obsoverlay.music.lyric.LyricParser.a().a(this.title, this.artist, this.durationMs, this.positionMs, this.songId);
    }

    public boolean c() {
        return this.active;
    }

    public String d() {
        return this.title;
    }

    public String e() {
        return this.artist;
    }

    public String f() {
        return this.sourceApp;
    }

    public long g() {
        return this.positionMs;
    }

    public long h() {
        return this.durationMs;
    }

    public boolean i() {
        return this.playing;
    }

    public boolean j() {
        return this.fromLocal;
    }

    public long k() {
        return this.songId;
    }

    public String l() {
        if (this.title.isEmpty()) {
            return "";
        }
        return this.artist.isEmpty() ? this.title : this.artist + " - " + this.title;
    }
}

