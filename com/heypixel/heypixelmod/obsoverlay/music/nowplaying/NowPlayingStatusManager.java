/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.music.nowplaying;

import com.heypixel.heypixelmod.obsoverlay.music.nowplaying.WindowTitleBridge;
import com.heypixel.heypixelmod.obsoverlay.music.nowplaying.NeteasePlaybackHistoryParser;
import com.heypixel.heypixelmod.obsoverlay.music.nowplaying.NowPlayingInfo;
import com.heypixel.heypixelmod.obsoverlay.music.nowplaying.PlaybackPositionTracker;
import com.heypixel.heypixelmod.obsoverlay.music.nowplaying.WindowsSMTCBridge;
import com.heypixel.heypixelmod.obsoverlay.utils.BackgroundExecutor;
import java.util.Locale;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public final class NowPlayingStatusManager {
    private static volatile NowPlayingStatusManager instance;
    private final WindowsSMTCBridge smtc = new WindowsSMTCBridge();
    private volatile NowPlayingInfo cached = NowPlayingInfo.NONE;
    private volatile boolean running;
    private volatile boolean neteaseEnabled = true;
    private volatile boolean otherPlayersEnabled = true;
    private volatile boolean smtcEnabled = false;
    private volatile ScheduledFuture<?> task;
    private volatile String status = "\u672a\u542f\u52a8";
    private volatile String lastSongName = "";
    private final PlaybackPositionTracker clock = new PlaybackPositionTracker();

    private NowPlayingStatusManager() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static NowPlayingStatusManager a() {
        if (instance != null) return instance;
        Class<NowPlayingStatusManager> clazz = NowPlayingStatusManager.class;
        synchronized (NowPlayingStatusManager.class) {
            if (instance != null) return instance;
            instance = new NowPlayingStatusManager();
            // ** MonitorExit[var0] (shouldn't be in output)
            return instance;
        }
    }

    public synchronized void b() {
        if (this.running) {
            return;
        }
        this.running = true;
        if (this.smtcEnabled) {
            this.smtc.g();
        }
        this.task = BackgroundExecutor.b(this::l, 0L, 1000L, TimeUnit.MILLISECONDS);
    }

    public synchronized void c() {
        this.running = false;
        ScheduledFuture<?> scheduledFuture = this.task;
        this.task = null;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
        }
        this.smtc.h();
        this.cached = NowPlayingInfo.NONE;
        this.status = "\u5df2\u505c\u6b62";
        this.n();
    }

    public boolean d() {
        return this.running;
    }

    public void e() {
        if (!this.running) {
            this.b();
        }
    }

    public void a(boolean bl) {
        this.smtcEnabled = bl;
        if (!this.running) {
            return;
        }
        if (bl) {
            this.smtc.g();
        } else {
            this.smtc.h();
        }
    }

    public void b(boolean bl) {
        this.neteaseEnabled = bl;
    }

    public void c(boolean bl) {
        this.otherPlayersEnabled = bl;
    }

    public boolean f() {
        return this.smtcEnabled;
    }

    public boolean g() {
        return this.neteaseEnabled;
    }

    public boolean h() {
        return this.otherPlayersEnabled;
    }

    public NowPlayingInfo i() {
        return this.cached;
    }

    public String j() {
        NowPlayingInfo b2;
        boolean bl;
        if (!this.running) {
            return "\u672a\u542f\u52a8";
        }
        StringBuilder stringBuilder = new StringBuilder();
        boolean bl2 = bl = WindowTitleBridge.a() && WindowTitleBridge.c();
        if (!this.neteaseEnabled) {
            stringBuilder.append("\u7f51\u6613\u4e91\u8bc6\u522b: \u5df2\u5173\u95ed");
        } else if (bl) {
            stringBuilder.append("\u7f51\u6613\u4e91\u5df2\u8fd0\u884c");
            int n2 = NeteasePlaybackHistoryParser.c();
            if (n2 > 0) {
                stringBuilder.append(" \u00b7 \u672c\u5730\u961f\u5217 ").append(n2).append(" \u9996\uff08\u53ef\u76f4\u53d6\u6b4c\u66f2 ID\uff09");
            } else {
                stringBuilder.append(" \u00b7 \u672a\u8bfb\u5230\u672c\u5730\u64ad\u653e\u961f\u5217\uff0c\u5c06\u56de\u9000\u5230\u641c\u7d22");
            }
        } else {
            stringBuilder.append("\u672a\u68c0\u6d4b\u5230\u7f51\u6613\u4e91\u8fdb\u7a0b");
        }
        if (this.smtcEnabled) {
            stringBuilder.append("\nSMTC: ");
            if (this.smtc.a() && this.smtc.c()) {
                stringBuilder.append("\u5df2\u8fde\u63a5");
            } else if (!this.smtc.b()) {
                stringBuilder.append("\u4e0d\u53ef\u7528").append((String)(this.smtc.d() == null ? "" : "\uff08" + this.smtc.d() + "\uff09"));
            } else {
                stringBuilder.append("\u542f\u52a8\u4e2d");
            }
        }
        if (!(b2 = this.cached).k()) {
            stringBuilder.append("\n\u5f53\u524d: ").append(b2.o()).append(" [").append(b2.i()).append("]");
            if (b2.j() > 0L) {
                stringBuilder.append(" id=").append(b2.j());
            }
            stringBuilder.append(b2.g() ? " \u64ad\u653e\u4e2d" : " \u5df2\u6682\u505c");
        } else {
            stringBuilder.append("\n\u5f53\u524d: \u672a\u68c0\u6d4b\u5230\u6b63\u5728\u64ad\u653e\u7684\u6b4c\u66f2");
        }
        return stringBuilder.toString();
    }

    public String k() {
        return this.lastSongName;
    }

    private void l() {
        if (!this.running) {
            return;
        }
        try {
            NowPlayingInfo b2 = this.m();
            this.cached = b2.k() ? NowPlayingInfo.NONE : this.a(b2);
            this.lastSongName = this.cached.b();
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    private NowPlayingInfo m() {
        NowPlayingInfo b2;
        NowPlayingInfo b3 = b2 = this.smtcEnabled ? this.smtc.e() : NowPlayingInfo.NONE;
        if (this.neteaseEnabled || this.otherPlayersEnabled) {
            for (com.heypixel.heypixelmod.obsoverlay.music.nowplaying.WindowTitleBridge.DetectedTrack b4 : WindowTitleBridge.b()) {
                if (b4.e()) {
                    if (!this.neteaseEnabled) continue;
                    return this.a(b4, b2);
                }
                if (!this.otherPlayersEnabled) continue;
                NowPlayingInfo b5 = new NowPlayingInfo(b4.a(), b4.b(), b4.c(), "", 0L, -1L, true, System.currentTimeMillis(), "\u7a97\u53e3\u6807\u9898");
                if (!b2.k() && b2.a().toLowerCase(Locale.ROOT).contains(b4.a().replace(".exe", ""))) {
                    b5 = b5.a(b2.g()).c(b2.f()).a(b2.e());
                }
                return b5;
            }
        }
        if (!b2.k()) {
            return b2;
        }
        return NowPlayingInfo.NONE;
    }

    private NowPlayingInfo a(com.heypixel.heypixelmod.obsoverlay.music.nowplaying.WindowTitleBridge.DetectedTrack b2, NowPlayingInfo b3) {
        String string = b2.b();
        String string2 = b2.c();
        long l2 = -1L;
        long l3 = -1L;
        com.heypixel.heypixelmod.obsoverlay.music.nowplaying.NeteasePlaybackHistoryParser.QueueTrack a2 = NeteasePlaybackHistoryParser.a(string, string2);
        if (a2 != null) {
            l2 = a2.a();
            l3 = a2.d();
            if (string2.isEmpty() && !a2.c().isEmpty()) {
                string2 = a2.c();
            }
            if (!a2.b().isEmpty()) {
                string = a2.b();
            }
        }
        boolean bl = true;
        if (!b3.k()) {
            boolean bl2;
            String string3 = b3.a().toLowerCase(Locale.ROOT);
            boolean bl3 = bl2 = string3.contains("cloudmusic") || string3.contains("netease");
            if (bl2) {
                bl = b3.g();
            }
            if (l3 <= 0L) {
                l3 = b3.f();
            }
        }
        return new NowPlayingInfo("cloudmusic.exe", string, string2, "", 0L, l3, bl, System.currentTimeMillis(), l2 > 0L ? "\u7f51\u6613\u4e91\u672c\u5730" : "\u7f51\u6613\u4e91\u6807\u9898", l2);
    }

    private void n() {
        this.clock.c();
    }

    private NowPlayingInfo a(NowPlayingInfo b2) {
        return this.clock.a(b2);
    }
}

