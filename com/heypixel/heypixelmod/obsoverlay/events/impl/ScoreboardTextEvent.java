/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.events.impl;

import com.heypixel.heypixelmod.obsoverlay.events.api.events.Event;
import net.minecraft.network.chat.Component;

public class ScoreboardTextEvent
implements Event {
    private Component a;

    public ScoreboardTextEvent(Component component) {
        this.a = component;
    }

    public Component a() {
        return this.a;
    }

    public void a(Component component) {
        this.a = component;
    }
}

