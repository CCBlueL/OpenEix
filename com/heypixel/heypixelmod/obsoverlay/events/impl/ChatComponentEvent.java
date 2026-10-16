/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.events.impl;

import com.heypixel.heypixelmod.obsoverlay.events.api.events.a.CancellableEventBase;
import net.minecraft.network.chat.Component;

public class ChatComponentEvent
extends MovementInputState {
    private com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a b;
    private Component c;

    public ChatComponentEvent(com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a a2, Component component) {
        this.b = a2;
        this.c = component;
    }

    public com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a b() {
        return this.b;
    }

    public void a(com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a a2) {
        this.b = a2;
    }

    public Component c() {
        return this.c;
    }

    public void a(Component component) {
        this.c = component;
    }
}

