/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils;

import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.api.a.EventPriority;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventRender2D;
import com.heypixel.heypixelmod.obsoverlay.events.impl.NetworkPacketEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.ClientboundPacketEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerRespawnEvent;
import com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.network.protocol.game.ClientboundPingPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
import net.minecraft.network.protocol.game.ClientboundSetHealthPacket;
import net.minecraft.network.protocol.game.ClientboundSetScorePacket;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;

public class ServerLagbackMonitor {
    public static final Map<String, AtomicInteger> a = new HashMap<String, AtomicInteger>();
    private static int b = 0;
    private static int c = 0;
    private static final double d = 0.0625;
    private static long e = 0L;
    private static final long f = 1000L;
    private static final long g = 500L;
    private static long h = 0L;

    public static int a() {
        return b;
    }

    public static int b() {
        return c;
    }

    @EventTarget(a=0)
    public void onAllPackets(NetworkPacketEvent g2) {
        if (g2.b() == com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.d) {
            ClientboundPlayerPositionPacket clientboundPlayerPositionPacket;
            Object object;
            if (g2.c() instanceof ClientboundPingPacket) {
                ++b;
            }
            if ((object = g2.c()) instanceof ClientboundPlayerPositionPacket) {
                clientboundPlayerPositionPacket = (ClientboundPlayerPositionPacket)object;
                ServerLagbackMonitor.a(clientboundPlayerPositionPacket);
            }
            if ((object = g2.c()) instanceof ClientboundSetScorePacket) {
                clientboundPlayerPositionPacket = (ClientboundSetScorePacket)object;
                if (Minecraft.m_91087_().f_91073_ != null && ("belowHealth".equals(clientboundPlayerPositionPacket.m_133342_()) || "health".equals(clientboundPlayerPositionPacket.m_133342_())) && Minecraft.m_91087_().f_91074_ != null && !clientboundPlayerPositionPacket.m_133339_().equals(Minecraft.m_91087_().f_91074_.m_36316_().getName())) {
                    if (!a.containsKey(clientboundPlayerPositionPacket.m_133339_())) {
                        object = new AtomicInteger();
                        a.put(clientboundPlayerPositionPacket.m_133339_(), (AtomicInteger)object);
                    }
                    a.get(clientboundPlayerPositionPacket.m_133339_()).set(clientboundPlayerPositionPacket.m_133343_());
                }
            }
            if ((object = g2.c()) instanceof ClientboundSetHealthPacket && (clientboundPlayerPositionPacket = (ClientboundSetHealthPacket)object).m_133247_() > 20.0f) {
                g2.a(true);
            }
        }
    }

    private static void a(ClientboundPlayerPositionPacket clientboundPlayerPositionPacket) {
        double d2;
        double d3;
        Minecraft minecraft = Minecraft.m_91087_();
        if (minecraft.f_91074_ == null || minecraft.f_91073_ == null) {
            return;
        }
        if (clientboundPlayerPositionPacket.m_132826_() != null && !clientboundPlayerPositionPacket.m_132826_().isEmpty()) {
            return;
        }
        double d4 = clientboundPlayerPositionPacket.m_132818_() - minecraft.f_91074_.m_20185_();
        double d5 = d4 * d4 + (d3 = clientboundPlayerPositionPacket.m_132821_() - minecraft.f_91074_.m_20186_()) * d3 + (d2 = clientboundPlayerPositionPacket.m_132822_() - minecraft.f_91074_.m_20189_()) * d2;
        if (d5 < 0.00390625) {
            return;
        }
        long l2 = System.currentTimeMillis();
        if (l2 - e < 1000L) {
            return;
        }
        e = l2;
        ++c;
        if (l2 - h >= 500L) {
            h = l2;
            com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.a("\u00a7bLagback detected. Count: " + c);
        }
    }

    @EventTarget
    public void onUpdate(EventRender2D eventRender2D) {
        if (Minecraft.m_91087_().f_91073_ == null) {
            return;
        }
        for (AbstractClientPlayer abstractClientPlayer : Minecraft.m_91087_().f_91073_.m_6907_()) {
            if (abstractClientPlayer == Minecraft.m_91087_().f_91074_ || !a.containsKey(abstractClientPlayer.m_7755_().getString())) continue;
            abstractClientPlayer.m_21153_((float)Math.max(1, a.get(abstractClientPlayer.m_7755_().getString()).get()));
        }
    }

    @EventTarget
    public void onRespawn(PlayerRespawnEvent u2) {
        b = 0;
        c = 0;
        e = 0L;
        h = 0L;
    }

    @EventTarget(a=0)
    public void onPacket(ClientboundPacketEvent h2) {
        ClientboundSystemChatPacket clientboundSystemChatPacket;
        Object object = h2.b();
        if (object instanceof ClientboundSystemChatPacket && (((String)(object = (clientboundSystemChatPacket = (ClientboundSystemChatPacket)object).f_237849_().getString())).contains("\u6e38\u620f\u51c6\u5907\u5f00\u59cb") || ((String)object).contains("\u6e38\u620f\u7ed3\u675f"))) {
            c = 0;
            e = 0L;
            h = 0L;
        }
    }
}

