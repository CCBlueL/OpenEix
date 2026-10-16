/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.events.impl;

import com.heypixel.heypixelmod.obsoverlay.events.api.events.a.CancellableEventBase;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;

public class ClientboundPacketEvent
extends MovementInputState {
    private Packet<ClientGamePacketListener> b;

    public ClientboundPacketEvent(Packet<ClientGamePacketListener> packet) {
        this.b = packet;
    }

    public Packet<ClientGamePacketListener> b() {
        return this.b;
    }

    public void a(Packet<ClientGamePacketListener> packet) {
        this.b = packet;
    }
}

