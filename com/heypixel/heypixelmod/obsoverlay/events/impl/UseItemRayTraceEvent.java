/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.events.impl;

import com.heypixel.heypixelmod.obsoverlay.events.api.events.Event;

public class UseItemRayTraceEvent
implements Event {
    private float a;
    private float b;

    public UseItemRayTraceEvent(float f2, float f3) {
        this.a = f2;
        this.b = f3;
    }

    public float a() {
        return this.a;
    }

    public void a(float f2) {
        this.a = f2;
    }

    public float b() {
        return this.b;
    }

    public void b(float f2) {
        this.b = f2;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof UseItemRayTraceEvent)) {
            return false;
        }
        UseItemRayTraceEvent ag2 = (UseItemRayTraceEvent)object;
        if (!ag2.a(this)) {
            return false;
        }
        return Float.compare(this.a(), ag2.a()) == 0 && Float.compare(this.b(), ag2.b()) == 0;
    }

    protected boolean a(Object object) {
        return object instanceof UseItemRayTraceEvent;
    }

    public int hashCode() {
        int n2 = 59;
        int n3 = 1;
        n3 = n3 * 59 + Float.floatToIntBits(this.a());
        return n3 * 59 + Float.floatToIntBits(this.b());
    }

    public String toString() {
        return "EventUseItemRayTrace(yaw=" + this.a() + ", pitch=" + this.b() + ")";
    }
}

