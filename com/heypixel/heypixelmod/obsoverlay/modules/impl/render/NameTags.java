/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.render;

import com.heypixel.heypixelmod.obsoverlay.d.a.NumericSetting;
import com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.WorldRenderEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.Teams;
import com.heypixel.heypixelmod.obsoverlay.utils.EntityDistanceUtils;
import com.heypixel.heypixelmod.obsoverlay.utils.EntityNameFilter;
import io.github.humbleui.skija.Font;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

@ModuleInfo(a="NameTags", b="\u540d\u79f0\u6807\u7b7e", d=ModuleCategory.RENDER, c="Renders name tags")
public class NameTags
extends ModuleBase {
    private final Map<Entity, com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.b> f = new ConcurrentHashMap<Entity, com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.b>();
    public AntiNausea c = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Scale").a(0.3f).d(0.01f).b(0.1f).c(1.0f).a().c();
    public com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a d = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Blur").a(true).a().b();
    public com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a e = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Bloom").a(true).a().b();
    private static final float g = 10.0f;
    private static final float h = 8.0f;
    private static final float i = 16.0f;
    private static final Color j = new Color(0, 0, 0, 100);
    private static final Color k = new Color(200, 200, 200);
    private static final Color l = new Color(255, 85, 85);
    private final List<String> m = new ArrayList<String>();

    @EventTarget
    public void update(WorldRenderEvent p2) {
        try {
            this.a(p2.a());
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    @EventTarget
    public void onRenderSkia(com.heypixel.heypixelmod.obsoverlay.events.impl.CrosshairRenderEvent s2) {
        for (Map.Entry<Entity, com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.b> entry : this.f.entrySet()) {
            Entity entity;
            if (entry.getKey() == NameTags.a.f_91074_ || !((entity = entry.getKey()) instanceof Player)) continue;
            Player player = (Player)entity;
            float f2 = player.m_21223_();
            if (f2 > 20.0f) {
                player.m_21153_(20.0f);
            }
            com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.b b2 = entry.getValue();
            float f3 = 6.0f * this.c.q();
            float f4 = 6.0f * this.c.q();
            float f5 = 4.0f * this.c.q();
            float f6 = 3.0f * this.c.q();
            this.m.clear();
            if (Teams.a((Entity)player)) {
                this.m.add("\u00a7aTeam");
            }
            if (com.heypixel.heypixelmod.obsoverlay.utils.EntityNameFilter.a((Entity)player)) {
                this.m.add("\u00a7aFriend");
            }
            String string = String.join((CharSequence)" ", this.m);
            String string2 = player.m_7755_().getString();
            Object object = String.valueOf(Math.round(f2));
            if (player.m_6103_() > 0.0f) {
                object = (String)object + "+" + Math.round(player.m_6103_());
            }
            float f7 = 14.0f * this.c.q();
            Font font = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.c.a(f7);
            Font font2 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.c.b(f7);
            float f8 = string.isEmpty() ? 0.0f : com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string, font) + f3 * 2.0f;
            float f9 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string2, font) + f3 * 2.0f;
            float f10 = font2.measureTextWidth("\ue87d");
            float f11 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a((String)object, font) + f10 + f3 * 2.0f + f6;
            float f12 = f9 + f11 + (f8 > 0.0f ? f8 + f5 : 0.0f) + f5;
            float f13 = f7 + f3 * 1.5f;
            float f14 = b2.a - f12 / 2.0f;
            float f15 = b2.b - f13 / 2.0f;
            float f16 = f14;
            if (!string.isEmpty()) {
                this.a(f16, f15, f8, f13, f4, f3, f6, string, font, Color.WHITE, null, null);
                f16 += f8 + f5;
            }
            this.a(f16, f15, f9, f13, f4, f3, f6, string2, font, Color.WHITE, null, null);
            this.a(f16 += f9 + f5, f15, f11, f13, f4, f3, f6, (String)object, font, k, "\ue87d", font2);
        }
    }

    private float a(double d2) {
        double d3 = Math.max(8.0, Math.min(d2, 16.0));
        return (float)((double)(10.0f * this.c.q()) / d3);
    }

    private void a(float f2, float f3, float f4, float f5, float f6, float f7, float f8, String string, Font font, Color color, String string2, Font font2) {
        if (this.e.m()) {
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c(f2, f3, f4, f5, f6);
        }
        if (this.d.m()) {
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f2, f3, f4, f5, f6);
        }
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f2, f3, f4, f5, f6, j);
        float f9 = f3 + f5 / 2.0f - font.getMetrics().getCapHeight() / 2.0f;
        float f10 = f2 + f7;
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string, f10, f9, color, font);
        if (string2 != null && font2 != null) {
            float f11 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string, font);
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string2, f10 + f11 + f8, f9, l, font2);
        }
    }

    private void a(float f2) {
        this.f.clear();
        for (Entity entity : NameTags.a.f_91073_.m_104735_()) {
            if (!(entity instanceof Player) || entity.m_7755_().getString().startsWith("CIT-")) continue;
            double d2 = com.heypixel.heypixelmod.obsoverlay.utils.MathUtils.b(entity.f_19854_, entity.m_20185_(), (double)f2);
            double d3 = com.heypixel.heypixelmod.obsoverlay.utils.MathUtils.b(entity.f_19855_, entity.m_20186_(), (double)f2) + (double)entity.m_20206_() + 0.5;
            double d4 = com.heypixel.heypixelmod.obsoverlay.utils.MathUtils.b(entity.f_19856_, entity.m_20189_(), (double)f2);
            com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.b b2 = EntityDistanceUtils.a(d2, d3, d4, f2);
            b2.b(b2.b() - 2.0f);
            this.f.put(entity, b2);
        }
    }
}

