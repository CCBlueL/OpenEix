/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.events.api.events.a;

import com.heypixel.heypixelmod.obsoverlay.events.api.events.Event;
import com.heypixel.heypixelmod.obsoverlay.events.api.events.EventStageAccessor;

public abstract class EventStageBase
implements Event,
EventStageAccessor {
    private final byte a;

    protected EventStageBase(byte by) {
        this.a = by;
    }

    @Override
    public byte a() {
        return this.a;
    }
}

