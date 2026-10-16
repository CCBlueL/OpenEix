/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.music.a;

public final class MusicPlaylistMetadata {
    private final long id;
    private final String name;
    private final String cover;
    private final String updateFrequency;

    public MusicPlaylistMetadata(long l2, String string, String string2, String string3) {
        this.id = l2;
        this.name = string == null ? "" : string;
        this.cover = string2 == null ? "" : string2;
        this.updateFrequency = string3 == null ? "" : string3;
    }

    public long a() {
        return this.id;
    }

    public String b() {
        return this.name;
    }

    public String c() {
        return this.cover;
    }

    public String d() {
        return this.updateFrequency;
    }
}

