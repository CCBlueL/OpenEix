/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.b;

import com.heypixel.heypixelmod.obsoverlay.EixClientCore;
import com.heypixel.heypixelmod.obsoverlay.d.a.ModeSetting;
import com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;
import java.util.Objects;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

@ModuleInfo(a="Teams", b="\u961f\u4f0d", c="Prevent attack teammates", d=com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory.MISC)
public class Teams
extends ModuleBase {
    public static Teams c;
    public ChestStealer d = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Mode").a(0).a("Scoreboard", "Color").a().e();

    public Teams() {
        c = this;
    }

    public static boolean a(Entity entity) {
        if (!com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a(Teams.class).m()) {
            return false;
        }
        if (entity instanceof Player) {
            if (Teams.c.d.a("Color")) {
                Integer n2 = entity.m_19876_();
                Integer n3 = Teams.a.f_91074_.m_19876_();
                return n2.equals(n3);
            }
            String string = Teams.b(entity);
            String string2 = Teams.b((Entity)Teams.a.f_91074_);
            return Objects.equals(string, string2);
        }
        return false;
    }

    public static String b(Entity entity) {
        PlayerInfo playerInfo = a.m_91403_().m_104949_(entity.m_20148_());
        if (playerInfo == null) {
            return null;
        }
        return playerInfo.m_105340_() != null ? playerInfo.m_105340_().m_5758_() : null;
    }
}

