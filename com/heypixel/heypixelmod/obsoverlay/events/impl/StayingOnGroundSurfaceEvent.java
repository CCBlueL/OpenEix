/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.events.impl;

import com.heypixel.heypixelmod.obsoverlay.events.api.events.Event;

public class StayingOnGroundSurfaceEvent
implements Event {
    private boolean a;

    public StayingOnGroundSurfaceEvent(boolean bl) {
        this.a = bl;
    }

    public boolean a() {
        return this.a;
    }

    public void a(boolean bl) {
        this.a = bl;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof StayingOnGroundSurfaceEvent)) {
            return false;
        }
        StayingOnGroundSurfaceEvent aa2 = (StayingOnGroundSurfaceEvent)object;
        return aa2.a(this) && this.a() == aa2.a();
    }

    protected boolean a(Object object) {
        return object instanceof StayingOnGroundSurfaceEvent;
    }

    public int hashCode() {
        int n2 = 59;
        int n3 = 1;
        return n3 * 59 + (this.a() ? 79 : 97);
    }

    public String toString() {
        return "EventStayingOnGroundSurface(stay=" + this.a() + ")";
    }
}

