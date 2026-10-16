/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.events.api.events;

import com.heypixel.heypixelmod.obsoverlay.events.api.events.Event;

public abstract class EventFlagBase
implements Event {
    private boolean a;

    protected EventFlagBase() {
    }

    public void a() {
        this.a = true;
    }

    public boolean b() {
        return this.a;
    }
}

