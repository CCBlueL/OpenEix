/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.render;

import com.heypixel.heypixelmod.obsoverlay.EixClientCore;
import com.heypixel.heypixelmod.obsoverlay.d.a.NumericSetting;
import com.heypixel.heypixelmod.obsoverlay.d.a.ModeSetting;
import com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.WorldRenderEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.a.KillAura;
import com.heypixel.heypixelmod.obsoverlay.utils.EntityDistanceUtils;
import com.heypixel.heypixelmod.obsoverlay.utils.RotationInterpolation;
import com.heypixel.heypixelmod.obsoverlay.utils.MathUtils;
import java.awt.Color;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

@ModuleInfo(a="TargetESP", b="\u76ee\u6807ESP", c="Render face overlay", d=com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory.RENDER)
public class TargetESP
extends ModuleBase {
    private static final float c = 10.0f;
    private static final float d = 6.0f;
    private static final float e = 18.0f;
    private static final float f = 48.0f;
    private final Map<Entity, com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.b> g = new ConcurrentHashMap<Entity, com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.b>();
    private final Map<Entity, RotationInterpolation> h = new ConcurrentHashMap<Entity, RotationInterpolation>();
    private final BedESP i = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Mode").a("Face", "Rectangle").a(0).a().e();
    private final BedESP j = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Image").a("\u4e8e\u54f2", "\u4e8e\u601d\u793c", "\u674e\u4e39").a(0).a(() -> this.i.a("Face")).a().e();
    private final AntiNausea k = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Size").a(48.0f).d(1.0f).b(8.0f).c(128.0f).a().c();
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a l = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Hurt").a(true).a(() -> this.i.a("Face")).a().b();

    @EventTarget
    public void onRender(WorldRenderEvent p2) {
        try {
            this.a(p2.a());
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    @EventTarget
    public void onRenderSkia(com.heypixel.heypixelmod.obsoverlay.events.impl.CrosshairRenderEvent s2) {
        for (Map.Entry<Entity, com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.b> entry : this.g.entrySet()) {
            Object object;
            if (entry.getKey() == TargetESP.a.f_91074_ || !((object = entry.getKey()) instanceof Player)) continue;
            Player player = (Player)object;
            object = entry.getValue();
            if (object.a == Float.MAX_VALUE || object.b == Float.MAX_VALUE) continue;
            if (this.i.a("Rectangle")) {
                this.a(player, (com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.b)object);
                continue;
            }
            double d2 = TargetESP.a.f_91074_.m_20270_((Entity)player);
            float f2 = this.a(d2);
            float f3 = Math.max(6.0f, this.k.q() * f2);
            float f4 = object.a - f3 / 2.0f;
            float f5 = object.b - f3 / 2.0f;
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(this.p(), f4, f5, f3, f3);
            this.a(player, f4, f5, f3);
        }
    }

    private void a(Player player, float f2, float f3, float f4) {
        RotationInterpolation ag2 = this.h.computeIfAbsent((Entity)player, entity -> new RotationInterpolation(0.0f));
        ag2.a = this.l.m() && player.f_20916_ > 0 ? 120.0f : 0.0f;
        ag2.a(true);
        if (ag2.c > 1.0f) {
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f2, f3, f4, f4, 0.0f, new Color(255, 0, 0, (int)ag2.c));
        }
    }

    private String p() {
        if (this.j.a("\u4e8e\u601d\u793c")) {
            return "yuzhe/ysl.png";
        }
        if (this.j.a("\u674e\u4e39")) {
            return "yuzhe/lidan.png";
        }
        return "yuzhe/yuzhe.jpg";
    }

    private String q() {
        return "targetesp/rectangle.png";
    }

    private float a(double d2) {
        double d3 = Math.max(6.0, Math.min(d2, 18.0));
        return (float)(10.0 / d3);
    }

    private void a(float f2) {
        this.g.clear();
        KillAura h2 = com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a(KillAura.class);
        if (h2 == null || !h2.m() || h2.c == null) {
            this.h.clear();
            return;
        }
        List<Entity> list = h2.p();
        if (list == null || list.isEmpty()) {
            this.h.clear();
            return;
        }
        for (Entity entity : list) {
            if (!(entity instanceof Player)) continue;
            com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.b b2 = this.i.a("Rectangle") ? this.b(entity, f2) : this.a(entity, f2);
            this.g.put(entity, b2);
        }
        this.h.keySet().retainAll(this.g.keySet());
    }

    private com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.b a(Entity entity, float f2) {
        double d2 = MathUtils.b(entity.f_19854_, entity.m_20185_(), (double)f2);
        double d3 = MathUtils.b(entity.f_19855_, entity.m_20186_(), (double)f2) + (double)entity.m_20192_();
        double d4 = MathUtils.b(entity.f_19856_, entity.m_20189_(), (double)f2);
        return EntityDistanceUtils.a(d2, d3, d4, f2);
    }

    private com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.b b(Entity entity, float f2) {
        Object object;
        double d2 = MathUtils.b(entity.f_19854_, entity.m_20185_(), (double)f2);
        double d3 = MathUtils.b(entity.f_19855_, entity.m_20186_(), (double)f2);
        double d4 = MathUtils.b(entity.f_19856_, entity.m_20189_(), (double)f2);
        double d5 = entity.m_20206_();
        if (entity instanceof LivingEntity && (object = (LivingEntity)entity).m_6162_()) {
            d5 /= 1.75;
        }
        object = EntityDistanceUtils.a(d2, d3, d4, f2);
        com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.b b2 = EntityDistanceUtils.a(d2, d3 + (d5 /= 2.0), d4, f2);
        float f3 = Float.MAX_VALUE;
        float f4 = Float.MAX_VALUE;
        if (object.a != Float.MAX_VALUE && object.b != Float.MAX_VALUE) {
            f3 = Math.min(f3, object.a);
            f4 = Math.min(f4, object.b);
        }
        if (b2.a != Float.MAX_VALUE && b2.b != Float.MAX_VALUE) {
            f3 = Math.min(f3, b2.a);
            f4 = Math.min(f4, b2.b);
        }
        return f3 == Float.MAX_VALUE ? new com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.b(Float.MAX_VALUE, Float.MAX_VALUE) : new com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.b(f3, f4);
    }

    private void a(Player player, com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.b b2) {
        float f2 = TargetESP.a.f_91074_.m_20270_((Entity)player);
        float f3 = 1.0f - Mth.m_14036_((float)(Math.abs(f2 - 6.0f) / 60.0f), (float)0.0f, (float)0.75f);
        long l2 = System.currentTimeMillis() + 1200L;
        double d2 = Mth.m_14008_((double)((Math.sin((double)l2 / 150.0) + 1.0) / 2.0 * 30.0), (double)0.0, (double)30.0);
        double d3 = Mth.m_14008_((double)((Math.sin((double)l2 / 500.0) + 1.0) / 2.0), (double)0.8, (double)1.0);
        double d4 = Mth.m_14008_((double)((Math.sin((double)l2 / 1000.0) + 1.0) / 2.0 * 360.0), (double)0.0, (double)360.0);
        d4 = 45.0 - (d2 - 15.0) + d4;
        float f4 = 128.0f * f3 * (float)d3;
        float f5 = b2.a - f4 / 2.0f;
        float f6 = b2.b - f4 / 2.0f;
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c();
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.f(f5, f6, f4, f4, (float)d4);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(this.q(), f5, f6, f4, f4);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.d();
    }
}

