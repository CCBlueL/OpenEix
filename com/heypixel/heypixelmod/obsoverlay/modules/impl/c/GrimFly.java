/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.c;

import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerMotionEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PacketInterceptEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerRespawnEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;
import com.heypixel.heypixelmod.obsoverlay.utils.PacketUtils;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.protocol.game.ServerboundInteractPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket;
import net.minecraft.network.protocol.game.ServerboundPongPacket;
import net.minecraft.world.entity.Entity;

@ModuleInfo(a="GrimFly", b="\u6d6a\u5b50\u95f2\u8bdd", c="Attempts to desync brief keepalive timing after knockback", d=ModuleCategory.MOVEMENT)
public class GrimFly
extends ModuleBase {
    private final Queue<Packet<?>> c = new ConcurrentLinkedQueue();
    private boolean d;
    private boolean e;
    private int f;

    @Override
    public void d() {
        this.b(false);
        super.d();
    }

    @Override
    public void e() {
        this.q();
        this.b(false);
        super.e();
    }

    @EventTarget
    public void onRespawn(PlayerRespawnEvent u2) {
        if (this.m()) {
            this.a(false);
        }
        this.b(true);
    }

    @EventTarget
    public void onMotion(PlayerMotionEvent k2) {
        if (k2.b() != com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.a) {
            return;
        }
        if (!this.e || com.heypixel.heypixelmod.obsoverlay.modules.impl.c.GrimFly.a.f_91074_ == null) {
            return;
        }
        ++this.f;
        if (this.f < 8) {
            return;
        }
        PacketUtils.a(new ServerboundPlayerCommandPacket((Entity)com.heypixel.heypixelmod.obsoverlay.modules.impl.c.GrimFly.a.f_91074_, ServerboundPlayerCommandPacket.Action.START_FALL_FLYING));
        this.e = false;
        this.f = 0;
    }

    @EventTarget
    public void onPacket(PacketInterceptEvent m2) {
        if (com.heypixel.heypixelmod.obsoverlay.modules.impl.c.GrimFly.a.f_91074_ == null) {
            return;
        }
        Packet<?> packet = m2.c();
        if (m2.b() == com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.c) {
            if (this.d && packet instanceof ServerboundPongPacket) {
                m2.a(true);
                if (this.c.isEmpty()) {
                    this.e = true;
                    this.f = 0;
                }
                this.c.add(packet);
                if (this.c.size() > 200) {
                    this.p();
                }
                return;
            }
            if (packet instanceof ServerboundInteractPacket) {
                if (this.d && !this.c.isEmpty()) {
                    this.p();
                }
                return;
            }
        }
        if (m2.b() == com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.d) {
            ClientboundSetEntityMotionPacket clientboundSetEntityMotionPacket;
            if (packet instanceof ClientboundPlayerPositionPacket) {
                if (this.d && !this.c.isEmpty()) {
                    this.p();
                }
                return;
            }
            if (packet instanceof ClientboundSetEntityMotionPacket && (clientboundSetEntityMotionPacket = (ClientboundSetEntityMotionPacket)packet).m_133192_() == com.heypixel.heypixelmod.obsoverlay.modules.impl.c.GrimFly.a.f_91074_.m_19879_()) {
                if (this.d || !this.c.isEmpty()) {
                    return;
                }
                this.d = true;
                this.e = false;
                this.f = 0;
                m2.a(true);
            }
        }
    }

    private void p() {
        this.q();
        this.d = false;
        this.e = false;
        this.f = 0;
    }

    private void q() {
        while (!this.c.isEmpty()) {
            PacketUtils.a(this.c.poll());
        }
    }

    private void b(boolean bl) {
        this.d = false;
        this.e = false;
        this.f = 0;
        if (bl) {
            this.c.clear();
        }
    }
}

