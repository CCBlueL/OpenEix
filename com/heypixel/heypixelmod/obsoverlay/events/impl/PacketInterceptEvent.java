/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.events.impl;

import com.heypixel.heypixelmod.obsoverlay.events.api.events.a.CancellableEventBase;
import net.minecraft.network.protocol.Packet;

public class PacketInterceptEvent
extends MovementInputState {
    private final com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a b;
    private Packet<?> c;

    public PacketInterceptEvent(com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a a2, Packet<?> packet) {
        this.b = a2;
        this.c = packet;
    }

    public com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a b() {
        return this.b;
    }

    public Packet<?> c() {
        return this.c;
    }

    public void a(Packet<?> packet) {
        this.c = packet;
    }
}

