/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils;

import com.heypixel.heypixelmod.obsoverlay.EixClientCore;
import com.heypixel.heypixelmod.obsoverlay.c.a.ToastSeverity;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.NetworkPacketEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerMotionEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PacketInterceptEvent;
import com.heypixel.heypixelmod.obsoverlay.utils.ElapsedTimer;
import com.heypixel.heypixelmod.obsoverlay.utils.PacketSerializationUtils;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket;
import net.minecraft.network.protocol.game.ClientboundPingPacket;
import net.minecraft.network.protocol.game.ClientboundSetPlayerTeamPacket;
import net.minecraft.network.protocol.game.ClientboundSetTimePacket;
import net.minecraft.network.protocol.game.ServerboundCustomPayloadPacket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PacketUtils {
    public static final Logger a = LogManager.getLogger((String)"PacketUtil");
    private static final ElapsedTimer c = new ElapsedTimer();
    private static final com.heypixel.heypixelmod.obsoverlay.c.MainMenuScreen.a d = new com.heypixel.heypixelmod.obsoverlay.c.MainMenuScreen.a(com.heypixel.heypixelmod.obsoverlay.c.MainMenuScreen.b.c, "Server lagging!", 2000L);
    public static Set<Packet<?>> b = new HashSet();
    private static long e = 0L;

    public static boolean a() {
        return c.a(500.0);
    }

    public static void a(Packet<?> packet) {
        a.info("Sending: " + packet.getClass().getName());
        if (packet instanceof ServerboundCustomPayloadPacket) {
            ServerboundCustomPayloadPacket serverboundCustomPayloadPacket = (ServerboundCustomPayloadPacket)packet;
            a.info("RE custompayload, {}", (Object)serverboundCustomPayloadPacket.m_179589_().toString());
            if (serverboundCustomPayloadPacket.m_179589_().toString().equals("heypixelmod:s2cevent")) {
                FriendlyByteBuf friendlyByteBuf = serverboundCustomPayloadPacket.m_179590_();
                friendlyByteBuf.markReaderIndex();
                int n2 = friendlyByteBuf.m_130242_();
                a.info("after packet ({}", (Object)n2);
                if (n2 == 2) {
                    a.info("after packet");
                    a.info(Arrays.toString(PacketSerializationUtils.a(friendlyByteBuf, friendlyByteBuf.readableBytes())));
                }
                friendlyByteBuf.resetReaderIndex();
            }
        }
        b.add(packet);
        Minecraft.m_91087_().m_91403_().m_104955_(packet);
    }

    @EventTarget
    public void onMotion(PlayerMotionEvent k2) {
        if (k2.b() == com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.a) {
            if (PacketUtils.a()) {
                com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().j().a(d);
                d.b(System.currentTimeMillis());
                d.a(com.heypixel.heypixelmod.obsoverlay.c.MainMenuScreen.b.c);
                e = Math.round(c.d());
                d.a("Server lagging. Aura disabled! (" + e + "ms)");
            } else {
                d.a(com.heypixel.heypixelmod.obsoverlay.c.MainMenuScreen.b.a);
                d.a("Server currently online! (" + e + "ms)");
            }
        }
    }

    @EventTarget(a=4)
    public void onGlobalPacket(NetworkPacketEvent g2) {
        if (g2.c() instanceof ClientboundPingPacket || g2.c() instanceof ClientboundMoveEntityPacket || g2.c() instanceof ClientboundSetTimePacket || g2.c() instanceof ClientboundSetPlayerTeamPacket) {
            c.a();
        }
        if (!g2.a()) {
            Packet<?> packet = g2.c();
            PacketInterceptEvent m2 = new PacketInterceptEvent(g2.b(), packet);
            com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().b().a(m2);
            if (m2.a()) {
                g2.a(true);
            }
            g2.a(m2.c());
        }
    }
}

