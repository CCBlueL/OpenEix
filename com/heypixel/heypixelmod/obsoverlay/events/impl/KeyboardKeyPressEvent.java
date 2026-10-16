/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.events.impl;

import com.heypixel.heypixelmod.obsoverlay.events.api.events.a.CancellableEventBase;

public class KeyboardKeyPressEvent
extends MovementInputState {
    private final int b;
    private final boolean c;

    public KeyboardKeyPressEvent(int n2, boolean bl) {
        this.b = n2;
        this.c = bl;
    }

    public int b() {
        return this.b;
    }

    public boolean c() {
        return this.c;
    }
}

