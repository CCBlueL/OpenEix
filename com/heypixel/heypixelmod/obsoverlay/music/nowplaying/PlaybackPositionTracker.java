/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.music.nowplaying;

import com.heypixel.heypixelmod.obsoverlay.music.nowplaying.NowPlayingInfo;

final class PlaybackPositionTracker {
    private String trackKey = "";
    private long anchorAt;
    private long basePosition;
    private boolean playing;

    PlaybackPositionTracker() {
    }

    long a() {
        if (!this.playing) {
            return this.basePosition;
        }
        return this.basePosition + (System.currentTimeMillis() - this.anchorAt);
    }

    boolean b() {
        return !this.trackKey.isEmpty();
    }

    void c() {
        this.trackKey = "";
        this.anchorAt = 0L;
        this.basePosition = 0L;
        this.playing = false;
    }

    NowPlayingInfo a(NowPlayingInfo b2) {
        if (b2 == null || b2.k()) {
            this.c();
            return b2 == null ? NowPlayingInfo.NONE : b2;
        }
        if (b2.l() && b2.e() > 0L) {
            this.c();
            return b2;
        }
        long l2 = System.currentTimeMillis();
        if (!b2.n().equals(this.trackKey)) {
            this.trackKey = b2.n();
            this.basePosition = 0L;
            this.anchorAt = l2;
            this.playing = b2.g();
            return b2.a(0L);
        }
        if (b2.g() != this.playing) {
            if (b2.g()) {
                this.anchorAt = l2;
            } else {
                this.basePosition += l2 - this.anchorAt;
            }
            this.playing = b2.g();
        }
        long l3 = this.a();
        long l4 = b2.f();
        if (l4 > 0L && l3 > l4) {
            l3 = l4;
        }
        if (l3 < 0L) {
            l3 = 0L;
        }
        return b2.a(l3);
    }
}

