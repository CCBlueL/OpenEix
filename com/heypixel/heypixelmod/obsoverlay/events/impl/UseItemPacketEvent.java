/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.events.impl;

import com.heypixel.heypixelmod.obsoverlay.events.api.events.a.CancellableEventBase;
import net.minecraft.network.protocol.Packet;

public class UseItemPacketEvent
extends MovementInputState {
    private Packet<?> b;

    public UseItemPacketEvent(Packet<?> packet) {
        this.b = packet;
    }

    public Packet<?> b() {
        return this.b;
    }

    public void a(Packet<?> packet) {
        this.b = packet;
    }
}

