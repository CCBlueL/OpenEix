/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.events.impl;

import com.heypixel.heypixelmod.obsoverlay.events.api.events.Event;

public record EventMouseClick(int key, boolean state) implements Event
{
    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof EventMouseClick)) {
            return false;
        }
        EventMouseClick eventMouseClick = (EventMouseClick)object;
        if (!eventMouseClick.canEqual(this)) {
            return false;
        }
        return this.key() == eventMouseClick.key() && this.state() == eventMouseClick.state();
    }

    private boolean canEqual(Object object) {
        return object instanceof EventMouseClick;
    }

    @Override
    public int hashCode() {
        int n2 = 59;
        int n3 = 1;
        n3 = n3 * 59 + this.key();
        return n3 * 59 + (this.state() ? 79 : 97);
    }

    @Override
    public String toString() {
        return "EventMouseClick(key=" + this.key() + ", state=" + this.state() + ")";
    }
}

