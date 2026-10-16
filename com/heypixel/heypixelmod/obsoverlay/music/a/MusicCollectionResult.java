/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.music.a;

import com.heypixel.heypixelmod.obsoverlay.music.a.PlaybackTimelineModel;
import java.util.List;

public final class MusicCollectionResult {
    private final CollectionKind kind;
    private final long id;
    private final String name;
    private final String cover;
    private final String creator;
    private final String description;
    private final int songCount;
    private final List<PlaybackTimelineModel> songs;

    public MusicCollectionResult(CollectionKind a2, long l2, String string, String string2, String string3, String string4, int n2, List<PlaybackTimelineModel> list) {
        this.kind = a2;
        this.id = l2;
        this.name = string == null ? "" : string;
        this.cover = string2 == null ? "" : string2;
        this.creator = string3 == null ? "" : string3;
        this.description = string4 == null ? "" : string4;
        this.songCount = n2;
        this.songs = list == null ? List.of() : List.copyOf(list);
    }

    public CollectionKind a() {
        return this.kind;
    }

    public long b() {
        return this.id;
    }

    public String c() {
        return this.name;
    }

    public String d() {
        return this.cover;
    }

    public String e() {
        return this.creator;
    }

    public String f() {
        return this.description;
    }

    public int g() {
        return this.songCount;
    }

    public List<PlaybackTimelineModel> h() {
        return this.songs;
    }

    public static enum CollectionKind {
        PLAYLIST("\u6b4c\u5355"),
        ALBUM("\u4e13\u8f91"),
        TOPLIST("\u699c\u5355");

        private final String cnName;

        private CollectionKind(String string2) {
            this.cnName = string2;
        }

        public String a() {
            return this.cnName;
        }
    }
}

