/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.events.impl;

import com.heypixel.heypixelmod.obsoverlay.events.api.events.Event;
import net.minecraft.network.protocol.Packet;

public class ServerPositionCorrectionEvent
implements Event {
    private Packet<?> a;

    public ServerPositionCorrectionEvent(Packet<?> packet) {
        this.a = packet;
    }

    public Packet<?> a() {
        return this.a;
    }

    public void a(Packet<?> packet) {
        this.a = packet;
    }
}

