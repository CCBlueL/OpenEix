/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.music.lyric;

import com.heypixel.heypixelmod.obsoverlay.music.lyric.NeteaseLyricsClient;
import com.heypixel.heypixelmod.obsoverlay.music.lyric.LyricLine;
import com.heypixel.heypixelmod.obsoverlay.music.lyric.LyricDocument;
import com.heypixel.heypixelmod.obsoverlay.utils.BackgroundExecutor;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class LyricParser {
    private static final Logger LOGGER = LogManager.getLogger(LyricParser.class);
    private static volatile LyricParser instance;
    private final NeteaseLyricsClient client = new NeteaseLyricsClient();
    private final AtomicBoolean fetching = new AtomicBoolean(false);
    private volatile LyricDocument lyrics = LyricDocument.EMPTY;
    private volatile String trackKey = "";
    private volatile String pendingKey = "";
    private volatile String lastError = "";
    private volatile String currentLine = "";
    private volatile String currentTranslation = "";
    private volatile String nextLine = "";
    private volatile int currentIndex = -1;
    private volatile long currentLineStartMs = -1L;
    private volatile long currentLineEndMs = -1L;
    private volatile long lyricOffsetMs;
    private volatile boolean showTranslation = true;
    private volatile boolean enabled = true;
    private String lastTitle = "";
    private String lastArtist = "";
    private long lastSongId = -1L;
    private long lastComputeAt;
    private long lastComputedPosition = Long.MIN_VALUE;
    private volatile long positionSnapshot = 0L;

    private LyricParser() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static LyricParser a() {
        if (instance != null) return instance;
        Class<LyricParser> clazz = LyricParser.class;
        synchronized (LyricParser.class) {
            if (instance != null) return instance;
            instance = new LyricParser();
            // ** MonitorExit[var0] (shouldn't be in output)
            return instance;
        }
    }

    public void a(boolean bl) {
        if (this.enabled == bl) {
            return;
        }
        this.enabled = bl;
        if (!bl) {
            this.p();
            return;
        }
        this.trackKey = "";
        this.lastTitle = "";
        this.lastArtist = "";
        this.lastSongId = -1L;
        this.p();
    }

    public boolean b() {
        return this.enabled;
    }

    public void a(long l2) {
        this.lyricOffsetMs = l2;
        this.lastComputedPosition = Long.MIN_VALUE;
    }

    public void b(boolean bl) {
        this.showTranslation = bl;
        this.lastComputedPosition = Long.MIN_VALUE;
    }

    public boolean c() {
        return this.showTranslation;
    }

    public String d() {
        return this.currentLine;
    }

    public String e() {
        return this.currentTranslation;
    }

    public String f() {
        return this.nextLine;
    }

    public int g() {
        return this.currentIndex;
    }

    public long h() {
        return this.currentLineStartMs;
    }

    public long i() {
        return this.currentLineEndMs;
    }

    public float j() {
        long l2 = this.currentLineStartMs;
        long l3 = this.currentLineEndMs;
        if (l2 < 0L || l3 <= l2) {
            return -1.0f;
        }
        float f2 = (float)(this.positionSnapshot - l2) / (float)(l3 - l2);
        if (f2 < 0.0f) {
            return 0.0f;
        }
        return f2 > 1.0f ? 1.0f : f2;
    }

    public LyricDocument k() {
        return this.lyrics;
    }

    public boolean l() {
        return !this.lyrics.d();
    }

    public boolean m() {
        return this.fetching.get();
    }

    public String n() {
        return this.lastError;
    }

    public String o() {
        return this.trackKey;
    }

    public void p() {
        this.lyrics = LyricDocument.EMPTY;
        this.currentLine = "";
        this.currentTranslation = "";
        this.nextLine = "";
        this.currentIndex = -1;
        this.currentLineStartMs = -1L;
        this.currentLineEndMs = -1L;
        this.lastComputedPosition = Long.MIN_VALUE;
    }

    public void a(String string, String string2, long l2, long l3) {
        this.a(string, string2, l2, l3, -1L);
    }

    public void a(String string, String string2, long l2, long l3, long l4) {
        long l5;
        String string3;
        if (!this.enabled) {
            return;
        }
        if (string == null || string.isBlank()) {
            if (!this.trackKey.isEmpty()) {
                this.trackKey = "";
                this.lastTitle = "";
                this.lastArtist = "";
                this.lastSongId = -1L;
                this.p();
            }
            return;
        }
        String string4 = string3 = string2 == null ? "" : string2;
        if (!string.equals(this.lastTitle) || !string3.equals(this.lastArtist) || l4 != this.lastSongId) {
            this.lastTitle = string;
            this.lastArtist = string3;
            this.lastSongId = l4;
            String string5 = (String)(l4 > 0L ? l4 + "\u0000" : "") + (string + "\u0000" + string3).toLowerCase(Locale.ROOT);
            if (!string5.equals(this.trackKey)) {
                this.trackKey = string5;
                this.p();
                this.a(string5, string, string3, l2, l4);
            }
        }
        long l6 = System.currentTimeMillis();
        this.positionSnapshot = l5 = l3 + this.lyricOffsetMs;
        if (l6 - this.lastComputeAt < 50L && Math.abs(l5 - this.lastComputedPosition) < 200L) {
            return;
        }
        this.lastComputeAt = l6;
        this.lastComputedPosition = l5;
        this.b(l5);
    }

    private void b(long l2) {
        LyricLine a2;
        LyricLine a3;
        LyricDocument c2 = this.lyrics;
        if (c2.d()) {
            this.currentLine = "";
            this.currentTranslation = "";
            this.nextLine = "";
            this.currentIndex = -1;
            this.currentLineStartMs = -1L;
            this.currentLineEndMs = -1L;
            return;
        }
        int n2 = c2.a(l2);
        if (n2 >= 0 && n2 < c2.a().size()) {
            this.currentLineStartMs = c2.a().get(n2).a();
            a3 = c2.a(n2 + 1);
            this.currentLineEndMs = a3 == null ? -1L : a3.a();
        } else {
            this.currentLineStartMs = -1L;
            this.currentLineEndMs = -1L;
        }
        a3 = c2.a(n2);
        if ((a3 == null || a3.e()) && (a2 = c2.c(n2)) != null) {
            a3 = a2;
        }
        this.currentIndex = n2;
        this.currentLine = a3 == null ? "" : a3.b();
        this.currentTranslation = a3 == null || !this.showTranslation ? "" : a3.c();
        a2 = c2.b(n2 + 1);
        this.nextLine = a2 == null ? "" : a2.b();
    }

    private void a(String string, String string2, String string3, long l2, long l3) {
        if (string.equals(this.pendingKey)) {
            return;
        }
        this.pendingKey = string;
        if (!this.fetching.compareAndSet(false, true)) {
            return;
        }
        BackgroundExecutor.a(() -> {
            try {
                LyricDocument c2;
                LyricDocument c3 = c2 = l3 > 0L ? this.client.a(l3, string2, string3) : this.client.a(string2, string3, l2);
                if (!string.equals(this.trackKey)) {
                    return;
                }
                this.lyrics = c2;
                this.lastError = c2.d() ? "\u672a\u627e\u5230\u6b4c\u8bcd" : "";
                this.lastComputedPosition = Long.MIN_VALUE;
            }
            catch (Throwable throwable) {
                LOGGER.debug("\u62c9\u53d6\u6b4c\u8bcd\u5f02\u5e38", throwable);
                this.lastError = "\u62c9\u53d6\u5931\u8d25";
            }
            finally {
                this.fetching.set(false);
            }
        });
    }

    public void a(String string, String string2, long l2) {
        if (string == null || string.isBlank()) {
            return;
        }
        BackgroundExecutor.a(() -> {
            try {
                this.client.a(string, string2, l2);
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        });
    }

    public void q() {
        this.client.a();
    }
}

