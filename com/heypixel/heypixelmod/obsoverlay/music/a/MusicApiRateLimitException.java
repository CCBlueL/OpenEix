/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.music.a;

import java.io.IOException;

public class MusicApiRateLimitException
extends IOException {
    private final int code;

    public MusicApiRateLimitException(int n2, String string) {
        super(string);
        this.code = n2;
    }

    public int a() {
        return this.code;
    }

    public boolean b() {
        return this.code == 429;
    }
}

