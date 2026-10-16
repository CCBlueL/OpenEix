/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.events.impl;

import com.heypixel.heypixelmod.obsoverlay.events.api.events.a.CancellableEventBase;

public class ChatMessageEvent
extends MovementInputState {
    private final String b;

    public ChatMessageEvent(String string) {
        this.b = string;
    }

    public String b() {
        return this.b;
    }
}

