/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.events.impl;

import com.heypixel.heypixelmod.obsoverlay.events.api.events.Event;

public class LivingEntityPitchEvent
implements Event {
    private float a;

    public LivingEntityPitchEvent(float f2) {
        this.a = f2;
    }

    public float a() {
        return this.a;
    }

    public void a(float f2) {
        this.a = f2;
    }
}

