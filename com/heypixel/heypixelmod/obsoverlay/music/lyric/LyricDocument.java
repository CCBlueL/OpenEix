/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.music.lyric;

import com.heypixel.heypixelmod.obsoverlay.music.lyric.LyricLine;
import java.util.Collections;
import java.util.List;

public final class LyricDocument {
    public static final LyricDocument EMPTY = new LyricDocument(Collections.emptyList(), "", "");
    private final List<LyricLine> lines;
    private final String songName;
    private final String artistName;

    public LyricDocument(List<LyricLine> list, String string, String string2) {
        this.lines = list == null ? Collections.emptyList() : list;
        this.songName = string == null ? "" : string;
        this.artistName = string2 == null ? "" : string2;
    }

    public List<LyricLine> a() {
        return this.lines;
    }

    public String b() {
        return this.songName;
    }

    public String c() {
        return this.artistName;
    }

    public boolean d() {
        return this.lines.isEmpty();
    }

    public int e() {
        return this.lines.size();
    }

    public int a(long l2) {
        if (this.lines.isEmpty()) {
            return -1;
        }
        int n2 = 0;
        int n3 = this.lines.size() - 1;
        int n4 = -1;
        while (n2 <= n3) {
            int n5 = n2 + n3 >>> 1;
            if (this.lines.get(n5).a() <= l2) {
                n4 = n5;
                n2 = n5 + 1;
                continue;
            }
            n3 = n5 - 1;
        }
        return n4;
    }

    public LyricLine a(int n2) {
        if (n2 < 0 || n2 >= this.lines.size()) {
            return null;
        }
        return this.lines.get(n2);
    }

    public LyricLine b(int n2) {
        for (int i2 = Math.max(0, n2); i2 < this.lines.size(); ++i2) {
            LyricLine a2 = this.lines.get(i2);
            if (a2.e()) continue;
            return a2;
        }
        return null;
    }

    public LyricLine c(int n2) {
        for (int i2 = Math.min(n2, this.lines.size() - 1); i2 >= 0; --i2) {
            LyricLine a2 = this.lines.get(i2);
            if (a2.e()) continue;
            return a2;
        }
        return null;
    }
}

