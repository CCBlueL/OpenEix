/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class EntityNameFilter {
    private static final List<String> a = new CopyOnWriteArrayList<String>();

    public static boolean a(Entity entity) {
        return entity instanceof Player && a.contains(entity.m_7755_().getString());
    }

    public static boolean a(String string) {
        return a.contains(string);
    }

    public static void a(Player player) {
        a.add(player.m_7755_().getString());
    }

    public static void b(String string) {
        a.add(string);
    }

    public static void b(Player player) {
        a.remove(player.m_7755_().getString());
    }

    public static List<String> a() {
        return a;
    }
}

