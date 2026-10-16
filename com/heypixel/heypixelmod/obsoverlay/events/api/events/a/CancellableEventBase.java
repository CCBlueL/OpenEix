/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.events.api.events.a;

import com.heypixel.heypixelmod.obsoverlay.events.api.events.Event;

public abstract class CancellableEventBase
implements Event,
com.heypixel.heypixelmod.obsoverlay.events.api.events.CancellableEvent {
    public boolean a;

    protected CancellableEventBase() {
    }

    @Override
    public boolean a() {
        return this.a;
    }

    @Override
    public void a(boolean bl) {
        this.a = bl;
    }
}

