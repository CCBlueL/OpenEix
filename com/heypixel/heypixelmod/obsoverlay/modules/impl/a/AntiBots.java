/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.a;

import com.heypixel.heypixelmod.obsoverlay.EixClientCore;
import com.heypixel.heypixelmod.obsoverlay.d.a.NumericSetting;
import com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerMotionEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PacketInterceptEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerRespawnEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.mojang.authlib.GameProfile;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.network.protocol.game.ClientboundAddPlayerPacket;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.GameType;

@ModuleInfo(a="AntiBots", b="\u53cd\u4eba\u673a", d=com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory.COMBAT, c="Prevents bots from attacking you")
public class AntiBots
extends com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase {
    private static final Map<UUID, String> c = new ConcurrentHashMap<UUID, String>();
    private static final Map<Integer, String> d = new ConcurrentHashMap<Integer, String>();
    private static final Map<UUID, Long> e = new ConcurrentHashMap<UUID, Long>();
    private static final Set<Integer> f = new HashSet<Integer>();
    private static final Map<UUID, Long> g = new ConcurrentHashMap<UUID, Long>();
    private final CrystalAura h = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Respawn Time").a(2500.0f).d(100.0f).b(0.0f).c(10000.0f).a().c();

    public static boolean a(Entity entity) {
        AntiBots b2 = com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a(AntiBots.class);
        if (b2.h.q() < 1.0f) {
            return false;
        }
        return g.containsKey(entity.m_20148_()) && (float)(System.currentTimeMillis() - g.get(entity.m_20148_())) < b2.h.q();
    }

    public static boolean b(Entity entity) {
        return (f.contains(entity.m_19879_()) || !a.m_91403_().m_105143_().contains(entity.m_20148_())) && com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a(AntiBots.class).m();
    }

    @EventTarget
    public void bedWarsBot(PacketInterceptEvent m2) {
        if (m2.b() == com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.d && com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AntiBots.a.f_91073_ != null) {
            Object object = m2.c();
            if (object instanceof ClientboundPlayerInfoUpdatePacket) {
                ClientboundPlayerInfoUpdatePacket clientboundPlayerInfoUpdatePacket = (ClientboundPlayerInfoUpdatePacket)object;
                if (clientboundPlayerInfoUpdatePacket.m_246097_().contains(ClientboundPlayerInfoUpdatePacket.Action.ADD_PLAYER)) {
                    for (ClientboundPlayerInfoUpdatePacket.Entry entry : clientboundPlayerInfoUpdatePacket.m_246778_()) {
                        GameProfile gameProfile = entry.f_243688_();
                        UUID uUID = gameProfile.getId();
                        g.put(uUID, System.currentTimeMillis());
                    }
                }
            } else {
                ClientboundAnimatePacket clientboundAnimatePacket;
                object = m2.c();
                if (object instanceof ClientboundAnimatePacket && (object = com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AntiBots.a.f_91073_.m_6815_((clientboundAnimatePacket = (ClientboundAnimatePacket)object).m_131624_())) != null && clientboundAnimatePacket.m_131627_() == 0) {
                    g.remove(object.m_20148_());
                }
            }
        }
    }

    @EventTarget
    public void onRespawn(PlayerRespawnEvent u2) {
        c.clear();
        d.clear();
        f.clear();
        e.clear();
    }

    @EventTarget
    public void onMotion(PlayerMotionEvent k2) {
        if (k2.b() == com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.a) {
            for (Map.Entry<UUID, Long> entry : e.entrySet()) {
                if (System.currentTimeMillis() - entry.getValue() <= 500L) continue;
                e.remove(entry.getKey());
            }
        }
    }

    @EventTarget
    public void onPacket(PacketInterceptEvent m2) {
        block3: {
            Object object;
            block5: {
                block4: {
                    if (m2.b() != com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.d) break block3;
                    object = m2.c();
                    if (!(object instanceof ClientboundPlayerInfoUpdatePacket)) break block4;
                    ClientboundPlayerInfoUpdatePacket clientboundPlayerInfoUpdatePacket = (ClientboundPlayerInfoUpdatePacket)object;
                    if (!clientboundPlayerInfoUpdatePacket.m_246097_().contains(ClientboundPlayerInfoUpdatePacket.Action.ADD_PLAYER)) break block3;
                    for (ClientboundPlayerInfoUpdatePacket.Entry entry : clientboundPlayerInfoUpdatePacket.m_246778_()) {
                        if (entry.f_244512_() == null || !entry.f_244512_().m_7360_().isEmpty() || entry.f_244162_() != GameType.SURVIVAL) continue;
                        UUID uUID = entry.f_243688_().getId();
                        e.put(uUID, System.currentTimeMillis());
                        c.put(uUID, entry.f_244512_().getString());
                    }
                    break block3;
                }
                object = m2.c();
                if (!(object instanceof ClientboundAddPlayerPacket)) break block5;
                ClientboundAddPlayerPacket clientboundAddPlayerPacket = (ClientboundAddPlayerPacket)object;
                if (!e.containsKey(clientboundAddPlayerPacket.m_131606_())) break block3;
                object = c.get(clientboundAddPlayerPacket.m_131606_());
                d.put(clientboundAddPlayerPacket.m_131603_(), (String)object);
                e.remove(clientboundAddPlayerPacket.m_131606_());
                f.add(clientboundAddPlayerPacket.m_131603_());
                break block3;
            }
            object = m2.c();
            if (object instanceof ClientboundRemoveEntitiesPacket) {
                ClientboundRemoveEntitiesPacket clientboundRemoveEntitiesPacket = (ClientboundRemoveEntitiesPacket)object;
                for (Integer n2 : clientboundRemoveEntitiesPacket.m_182730_()) {
                    if (!f.contains(n2)) continue;
                    String string = d.get(n2);
                    f.remove(n2);
                }
            }
        }
    }
}

