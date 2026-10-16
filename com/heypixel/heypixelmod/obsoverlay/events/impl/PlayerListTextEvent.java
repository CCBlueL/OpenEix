/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.events.impl;

import com.heypixel.heypixelmod.obsoverlay.events.api.a.EventPriority;
import com.heypixel.heypixelmod.obsoverlay.events.api.events.Event;
import net.minecraft.network.chat.Component;

public class PlayerListTextEvent
implements Event {
    private MovementInputState a;
    private Component b;

    public PlayerListTextEvent(MovementInputState a2, Component component) {
        this.a = a2;
        this.b = component;
    }

    public MovementInputState a() {
        return this.a;
    }

    public void a(MovementInputState a2) {
        this.a = a2;
    }

    public Component b() {
        return this.b;
    }

    public void a(Component component) {
        this.b = component;
    }
}

