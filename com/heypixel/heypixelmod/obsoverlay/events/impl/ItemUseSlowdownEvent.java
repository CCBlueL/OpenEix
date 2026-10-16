/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.events.impl;

import com.heypixel.heypixelmod.obsoverlay.events.api.events.Event;

public class ItemUseSlowdownEvent
implements Event {
    private boolean a;

    public ItemUseSlowdownEvent(boolean bl) {
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
        if (!(object instanceof ItemUseSlowdownEvent)) {
            return false;
        }
        ItemUseSlowdownEvent z2 = (ItemUseSlowdownEvent)object;
        return z2.a(this) && this.a() == z2.a();
    }

    protected boolean a(Object object) {
        return object instanceof ItemUseSlowdownEvent;
    }

    public int hashCode() {
        int n2 = 59;
        int n3 = 1;
        return n3 * 59 + (this.a() ? 79 : 97);
    }

    public String toString() {
        return "EventSlowdown(slowdown=" + this.a() + ")";
    }
}

