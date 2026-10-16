/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.a;

import com.heypixel.heypixelmod.obsoverlay.d.a.NumericSetting;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventRunTicks;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AntiBots;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.ClientFriend;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.Target;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.Teams;
import com.heypixel.heypixelmod.obsoverlay.utils.c.EntityRotationFactory;
import com.heypixel.heypixelmod.obsoverlay.utils.EntityNameFilter;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

@ModuleInfo(a="TickBase", b="Tick\u57fa", c="Manipulates client ticks for combat timing", d=com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory.COMBAT)
public class TickBase
extends com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase {
    private final CrystalAura c = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Range").a(8.0f).d(0.1f).b(3.0f).c(15.0f).a().c();
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a d = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Debug").a(false).a().b();
    private Mode e = com.heypixel.heypixelmod.obsoverlay.modules.impl.a.TickBase.Mode.c;
    private long f;
    private long g;
    private double h;
    private double i;
    private boolean j;
    private Entity k;

    @Override
    public void d() {
        this.p();
    }

    @Override
    public void e() {
        this.p();
    }

    @EventTarget
    public void onPreTick(EventRunTicks eventRunTicks) {
        double d2;
        if (eventRunTicks.type() != com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.a || com.heypixel.heypixelmod.obsoverlay.modules.impl.a.TickBase.a.f_91074_ == null || com.heypixel.heypixelmod.obsoverlay.modules.impl.a.TickBase.a.f_91073_ == null) {
            return;
        }
        if (this.e == com.heypixel.heypixelmod.obsoverlay.modules.impl.a.TickBase.Mode.a) {
            return;
        }
        this.k = this.a(20.0);
        if (this.k == null) {
            return;
        }
        this.i = com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.d.b(this.k);
        double d3 = this.i;
        if (d3 > (d2 = Math.max(2.0, (double)this.c.q() - 1.0)) && this.g >= 50L && this.e == com.heypixel.heypixelmod.obsoverlay.modules.impl.a.TickBase.Mode.b) {
            this.g -= 50L;
            this.j = true;
        } else {
            this.g = 0L;
            this.e = com.heypixel.heypixelmod.obsoverlay.modules.impl.a.TickBase.Mode.c;
            this.j = false;
        }
        if (d3 <= (double)this.c.q() && this.h > (double)this.c.q() && this.e == com.heypixel.heypixelmod.obsoverlay.modules.impl.a.TickBase.Mode.c) {
            this.e = com.heypixel.heypixelmod.obsoverlay.modules.impl.a.TickBase.Mode.a;
            this.f = System.currentTimeMillis();
            this.g = 0L;
            this.j = false;
            this.b(String.format("TickBase REDUCING range=%.2f prev=%.2f limit=%.2f", d3, this.h, Float.valueOf(this.c.q())));
        }
        this.h = d3;
    }

    @EventTarget
    public void onRenderAfterWorld(com.heypixel.heypixelmod.obsoverlay.events.impl.WorldRenderTailEvent q2) {
        if (com.heypixel.heypixelmod.obsoverlay.modules.impl.a.TickBase.a.f_91074_ == null || com.heypixel.heypixelmod.obsoverlay.modules.impl.a.TickBase.a.f_91073_ == null) {
            return;
        }
        if (this.e != com.heypixel.heypixelmod.obsoverlay.modules.impl.a.TickBase.Mode.a) {
            com.heypixel.heypixelmod.obsoverlay.EixClientCore.c = this.j ? 2.0f : 1.0f;
            this.j = false;
            return;
        }
        if (this.k == null || !this.k.m_6084_()) {
            this.e = com.heypixel.heypixelmod.obsoverlay.modules.impl.a.TickBase.Mode.c;
            this.g = 0L;
            com.heypixel.heypixelmod.obsoverlay.EixClientCore.c = 1.0f;
            this.j = false;
            this.b("TickBase target lost");
            return;
        }
        this.i = com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.d.b(this.k);
        double d2 = com.heypixel.heypixelmod.obsoverlay.modules.impl.a.TickBase.a.f_91074_.m_21023_(MobEffects.f_19596_) ? 0.36 : 0.25;
        double d3 = Math.max(2.0, (double)this.c.q() - 1.0);
        if (this.i <= d3 || (double)(System.currentTimeMillis() - this.f) >= this.h / d2 * 25.0 + 25.0) {
            com.heypixel.heypixelmod.obsoverlay.EixClientCore.c = 1.0f;
            this.e = com.heypixel.heypixelmod.obsoverlay.modules.impl.a.TickBase.Mode.b;
            this.g = System.currentTimeMillis() - this.f;
            this.b(String.format("TickBase BASING balance=%d", this.g));
            return;
        }
        com.heypixel.heypixelmod.obsoverlay.EixClientCore.c = 0.0f;
    }

    private void p() {
        this.e = com.heypixel.heypixelmod.obsoverlay.modules.impl.a.TickBase.Mode.c;
        this.f = 0L;
        this.g = 0L;
        this.h = Double.MAX_VALUE;
        this.i = 0.0;
        this.j = false;
        this.k = null;
        com.heypixel.heypixelmod.obsoverlay.EixClientCore.c = 1.0f;
    }

    private void b(String string) {
        if (this.d.m()) {
            com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.a(string);
        }
    }

    private Entity a(double d2) {
        if (com.heypixel.heypixelmod.obsoverlay.modules.impl.a.TickBase.a.f_91074_ == null || com.heypixel.heypixelmod.obsoverlay.modules.impl.a.TickBase.a.f_91073_ == null) {
            return null;
        }
        double d3 = d2 * d2;
        Entity entity = null;
        double d4 = Double.MAX_VALUE;
        Target p2 = com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a(Target.class);
        for (Entity entity2 : com.heypixel.heypixelmod.obsoverlay.modules.impl.a.TickBase.a.f_91073_.m_104735_()) {
            double d5;
            if (!(entity2 instanceof LivingEntity) || entity2 == com.heypixel.heypixelmod.obsoverlay.modules.impl.a.TickBase.a.f_91074_ || !entity2.m_6084_() || entity2.m_5833_() || com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AntiBots.b(entity2) || p2 != null && !p2.a(entity2) || Teams.a(entity2) || EntityNameFilter.a(entity2) || com.heypixel.heypixelmod.obsoverlay.modules.impl.b.ClientFriend.a(entity2) || !((d5 = com.heypixel.heypixelmod.obsoverlay.modules.impl.a.TickBase.a.f_91074_.m_20280_(entity2)) <= d3) || !(d5 < d4)) continue;
            d4 = d5;
            entity = entity2;
        }
        return entity;
    }

    private static enum Mode {
        a,
        b,
        c;

    }
}

