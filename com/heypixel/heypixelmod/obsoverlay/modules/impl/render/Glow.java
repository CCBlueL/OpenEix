/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.render;

import com.heypixel.heypixelmod.obsoverlay.EixClientCore;
import com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;

@ModuleInfo(a="Glow", b="\u900f\u89c6", c="Glow effect for entities", d=com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory.RENDER)
public class Glow
extends ModuleBase {
    com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a c = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Player").a(true).a().b();
    com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a d = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Items").a(false).a().b();
    com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a e = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Mobs").a(false).a().b();
    com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a f = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Animals").a(false).a().b();
    com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a g = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Arrows").a(false).a().b();

    public static boolean a(Entity entity) {
        Glow l2 = com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a(Glow.class);
        if (!l2.m()) {
            return false;
        }
        if (entity instanceof Player && l2.c.m()) {
            return true;
        }
        if (entity instanceof ItemEntity && l2.d.m()) {
            return true;
        }
        if (entity instanceof Mob && l2.e.m()) {
            return true;
        }
        return entity instanceof Animal && l2.f.m() || entity instanceof Arrow && l2.g.m();
    }
}

