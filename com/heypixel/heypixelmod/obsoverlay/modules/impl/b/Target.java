/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.b;

import com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ambient.AmbientCreature;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;

@ModuleInfo(a="Target", b="\u76ee\u6807", c="Prevent attack teammates", d=ModuleCategory.MISC)
public class Target
extends ModuleBase {
    com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a c = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Player").a(true).a().b();
    com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a d = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Invisibles").a(true).a().b();
    com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a e = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Animals").a(true).a().b();
    com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a f = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Mobs").a(true).a().b();
    com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a g = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Villager").a(true).a().b();

    @Override
    public void d() {
        this.a(false);
    }

    public boolean a(Entity entity) {
        if (!(entity instanceof LivingEntity)) {
            return false;
        }
        if (entity == Target.a.f_91074_) {
            return false;
        }
        if (entity instanceof Player && entity.m_5833_()) {
            return false;
        }
        if (!entity.m_6084_()) {
            return false;
        }
        if (entity.m_20145_() && !this.d.m()) {
            return false;
        }
        if (entity instanceof Player) {
            return this.c.m();
        }
        if (entity instanceof AbstractVillager) {
            return this.g.m();
        }
        if (entity instanceof Monster || entity instanceof Slime || entity instanceof EnderDragon || entity instanceof EnderDragonPart) {
            return this.f.m();
        }
        if (entity instanceof Animal || entity instanceof AmbientCreature || entity instanceof WaterAnimal || entity instanceof AbstractHorse) {
            return this.e.m();
        }
        return false;
    }
}

