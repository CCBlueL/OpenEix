/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.events.impl;

import com.heypixel.heypixelmod.obsoverlay.events.api.events.Event;

public class FovUpdateEvent
implements Event {
    private float a;

    public FovUpdateEvent(float f2) {
        this.a = f2;
    }

    public float a() {
        return this.a;
    }

    public void a(float f2) {
        this.a = f2;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof FovUpdateEvent)) {
            return false;
        }
        FovUpdateEvent ae2 = (FovUpdateEvent)object;
        return ae2.a(this) && Float.compare(this.a(), ae2.a()) == 0;
    }

    protected boolean a(Object object) {
        return object instanceof FovUpdateEvent;
    }

    public int hashCode() {
        int n2 = 59;
        int n3 = 1;
        return n3 * 59 + Float.floatToIntBits(this.a());
    }

    public String toString() {
        return "EventUpdateFoV(fov=" + this.a() + ")";
    }
}

