/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils;

import com.heypixel.heypixelmod.mixin.O.accessors.LivingEntityAccessor;
import com.heypixel.heypixelmod.obsoverlay.EixClientCore;
import com.heypixel.heypixelmod.obsoverlay.c.a.ToastSeverity;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerMotionEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerRespawnEvent;
import com.heypixel.heypixelmod.obsoverlay.utils.SharedESPData;
import com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils;
import com.heypixel.heypixelmod.obsoverlay.utils.PotionEffectLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import org.antlr.v4.runtime.misc.OrderedHashSet;

public class PlayerEffectTracker {
    private static final Minecraft a = Minecraft.m_91087_();
    private static final Map<Entity, Set<String>> b = new ConcurrentHashMap<Entity, Set<String>>();
    private static final Map<String, SharedESPData> c = new ConcurrentHashMap<String, SharedESPData>();

    public static Set<String> a(AbstractClientPlayer abstractClientPlayer) {
        List<MobEffect> list = PotionEffectLoader.a((Integer)abstractClientPlayer.m_20088_().m_135370_(LivingEntityAccessor.getEffectColorId()));
        list.remove(MobEffects.f_19617_);
        OrderedHashSet orderedHashSet = new OrderedHashSet();
        if (b.containsKey(abstractClientPlayer)) {
            orderedHashSet.addAll((Collection)b.get(abstractClientPlayer));
        }
        Set set = list.stream().map(mobEffect -> "effect.minecraft." + BuiltInRegistries.f_256974_.m_7981_(mobEffect).m_135815_()).collect(Collectors.toSet());
        orderedHashSet.addAll(set);
        return orderedHashSet;
    }

    public static Map<String, SharedESPData> a() {
        return c;
    }

    @EventTarget
    public void onRespawn(PlayerRespawnEvent u2) {
        b.clear();
        c.clear();
    }

    @EventTarget
    public void onMotion(PlayerMotionEvent k2) {
        if (k2.b() == com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.a && PlayerEffectTracker.a.f_91073_ != null) {
            PlayerEffectTracker.a().forEach((string, af2) -> {
                if (System.currentTimeMillis() - af2.j() > 500L) {
                    PlayerEffectTracker.a().remove(string);
                }
            });
            for (AbstractClientPlayer abstractClientPlayer : new ArrayList(PlayerEffectTracker.a.f_91073_.m_6907_())) {
                com.heypixel.heypixelmod.obsoverlay.c.MainMenuScreen.a a2;
                if (abstractClientPlayer == PlayerEffectTracker.a.f_91074_) continue;
                if (!b.containsKey(abstractClientPlayer)) {
                    b.put((Entity)abstractClientPlayer, new HashSet());
                }
                Set<String> set = b.get(abstractClientPlayer);
                if ((EquipmentActionUtils.c(abstractClientPlayer.m_21205_()) || EquipmentActionUtils.c(abstractClientPlayer.m_21206_())) && !set.contains("God Axe")) {
                    a2 = new com.heypixel.heypixelmod.obsoverlay.c.MainMenuScreen.a(com.heypixel.heypixelmod.obsoverlay.c.MainMenuScreen.b.c, abstractClientPlayer.m_7755_().getString() + " is holding god axe!", 3000L);
                    com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().j().a(a2);
                    set.add("God Axe");
                }
                if ((EquipmentActionUtils.d(abstractClientPlayer.m_21205_()) || EquipmentActionUtils.d(abstractClientPlayer.m_21206_())) && !set.contains("Enchanted Golden Apple")) {
                    a2 = new com.heypixel.heypixelmod.obsoverlay.c.MainMenuScreen.a(com.heypixel.heypixelmod.obsoverlay.c.MainMenuScreen.b.c, abstractClientPlayer.m_7755_().getString() + " is holding enchanted golden apple!", 3000L);
                    com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().j().a(a2);
                    set.add("Enchanted Golden Apple");
                }
                if ((EquipmentActionUtils.e(abstractClientPlayer.m_21205_()) || EquipmentActionUtils.e(abstractClientPlayer.m_21206_())) && !set.contains("End Crystal")) {
                    a2 = new com.heypixel.heypixelmod.obsoverlay.c.MainMenuScreen.a(com.heypixel.heypixelmod.obsoverlay.c.MainMenuScreen.b.c, abstractClientPlayer.m_7755_().getString() + " is holding end crystal!", 3000L);
                    com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().j().a(a2);
                    set.add("End Crystal");
                }
                if ((EquipmentActionUtils.f(abstractClientPlayer.m_21205_()) || EquipmentActionUtils.f(abstractClientPlayer.m_21206_())) && !set.contains("KB Ball")) {
                    a2 = new com.heypixel.heypixelmod.obsoverlay.c.MainMenuScreen.a(com.heypixel.heypixelmod.obsoverlay.c.MainMenuScreen.b.c, abstractClientPlayer.m_7755_().getString() + " is holding KB Ball!", 3000L);
                    com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().j().a(a2);
                    set.add("KB Ball");
                }
                if ((EquipmentActionUtils.g(abstractClientPlayer.m_21205_()) || EquipmentActionUtils.g(abstractClientPlayer.m_21206_())) && !set.contains("KB Stick")) {
                    a2 = new com.heypixel.heypixelmod.obsoverlay.c.MainMenuScreen.a(com.heypixel.heypixelmod.obsoverlay.c.MainMenuScreen.b.c, abstractClientPlayer.m_7755_().getString() + " is holding KB Stick!", 3000L);
                    com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().j().a(a2);
                    set.add("KB Stick");
                }
                if (!(EquipmentActionUtils.h(abstractClientPlayer.m_21205_()) <= 2 && EquipmentActionUtils.h(abstractClientPlayer.m_21206_()) <= 2 || set.contains("Punch Bow"))) {
                    a2 = new com.heypixel.heypixelmod.obsoverlay.c.MainMenuScreen.a(com.heypixel.heypixelmod.obsoverlay.c.MainMenuScreen.b.c, abstractClientPlayer.m_7755_().getString() + " is holding Punch Bow!", 3000L);
                    com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().j().a(a2);
                    set.add("Punch Bow");
                }
                if (EquipmentActionUtils.i(abstractClientPlayer.m_21205_()) <= 3 && EquipmentActionUtils.i(abstractClientPlayer.m_21206_()) <= 3 || set.contains("Power Bow")) continue;
                a2 = new com.heypixel.heypixelmod.obsoverlay.c.MainMenuScreen.a(com.heypixel.heypixelmod.obsoverlay.c.MainMenuScreen.b.c, abstractClientPlayer.m_7755_().getString() + " is holding Power Bow!", 3000L);
                com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().j().a(a2);
                set.add("Power Bow");
            }
        }
    }
}

