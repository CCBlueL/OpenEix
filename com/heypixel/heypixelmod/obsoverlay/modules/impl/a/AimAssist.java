/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.a;

import com.heypixel.heypixelmod.obsoverlay.d.a.NumericSetting;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerMotionEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AntiBots;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.ClientFriend;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.Target;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.Teams;
import com.heypixel.heypixelmod.obsoverlay.utils.c.EntityRotationFactory;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.phys.HitResult;

@ModuleInfo(a="AimAssist", b="\u7784\u51c6\u8f85\u52a9", c="Automatically aims at targets", d=com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory.COMBAT)
public class AimAssist
extends com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase {
    private final CrystalAura c = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Speed").a(2.0f).b(1.0f).c(10.0f).d(0.1f).a().c();
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a d = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Aim Pitch").a(false).a().b();
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a e = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Require Swinging").a(true).a().b();
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a f = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Sticky").a(false).a().b();
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a g = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Require Mouse Movement").a(false).a().b();
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a h = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Limit Items").a(false).a().b();
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a i = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Aim Whilst on Target").a(false).a().b();
    private final CrystalAura j = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "On Target Speed").a(1.0f).b(1.0f).c(10.0f).d(0.1f).a(this.i::m).a().c();
    private final CrystalAura k = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "FOV").a(90.0f).b(0.0f).c(180.0f).d(1.0f).a().c();
    private float l;
    private float m;
    private LivingEntity n;
    private double o;
    private double p;

    @EventTarget
    public void onMotion(PlayerMotionEvent k2) {
        if (k2.b() != com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.a) {
            return;
        }
        double d2 = 4.0;
        this.l = 0.0f;
        this.m = 0.0f;
        this.n = null;
        List<LivingEntity> list = this.a(d2);
        if (list.isEmpty()) {
            return;
        }
        this.n = list.get(0);
        com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.b b2 = com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.d.a((Entity)this.n);
        float f2 = Math.abs(Mth.m_14177_((float)(b2.a() - com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AimAssist.a.f_91074_.m_146908_())));
        if (f2 > this.k.q()) {
            this.n = null;
            return;
        }
        if (this.h.m() && (com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AimAssist.a.f_91074_.m_21205_().m_41619_() || !(com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AimAssist.a.f_91074_.m_21205_().m_41720_() instanceof SwordItem))) {
            this.n = null;
            return;
        }
        float f3 = this.c.q();
        if (com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AimAssist.a.f_91077_ != null && com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AimAssist.a.f_91077_.m_6662_() == HitResult.Type.ENTITY) {
            f3 = this.j.q();
        }
        if (this.f.m()) {
            f3 *= 10.0f;
        }
        float f4 = f3 / (float)Minecraft.m_91087_().m_260875_() * 100.0f;
        com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.b b3 = new com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.b(com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AimAssist.a.f_91074_.m_146908_(), com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AimAssist.a.f_91074_.m_146909_());
        com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.b b4 = com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.d.a(b3, b2, (double)f4);
        this.l = b4.a();
        this.m = b4.b();
    }

    @EventTarget
    public void onRender3D(com.heypixel.heypixelmod.obsoverlay.events.impl.WorldRenderTailEvent q2) {
        boolean bl;
        if (this.n == null || this.l == 0.0f && this.m == 0.0f) {
            return;
        }
        double d2 = com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AimAssist.a.f_91067_.m_91589_();
        double d3 = com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AimAssist.a.f_91067_.m_91594_();
        boolean bl2 = d2 != this.o || d3 != this.p;
        this.o = d2;
        this.p = d3;
        if (this.g.m() && !bl2) {
            return;
        }
        if (com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AimAssist.a.f_91077_ != null && com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AimAssist.a.f_91077_.m_6662_() == HitResult.Type.ENTITY && !this.i.m()) {
            return;
        }
        if (this.e.m() && !com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AimAssist.a.f_91066_.f_92096_.m_90857_()) {
            return;
        }
        double d4 = (Double)com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AimAssist.a.f_91066_.m_231964_().m_231551_() * 0.6 + 0.2;
        double d5 = d4 * d4 * d4 * 8.0;
        double d6 = d5 * 0.15;
        float f2 = (float)((double)Math.round((double)this.l / d6) * d6);
        float f3 = (float)((double)Math.round((double)this.m / d6) * d6);
        boolean bl3 = (double)Math.abs(f2) >= d6;
        boolean bl4 = bl = this.d.m() && (double)Math.abs(f3) >= d6;
        if (!bl3 && !bl) {
            return;
        }
        com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AimAssist.a.f_91074_.m_19884_(bl3 ? (double)f2 : 0.0, bl ? (double)f3 : 0.0);
    }

    private List<LivingEntity> a(double d2) {
        ArrayList<LivingEntity> arrayList = new ArrayList<LivingEntity>();
        if (com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AimAssist.a.f_91073_ == null) {
            return arrayList;
        }
        for (Entity entity : com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AimAssist.a.f_91073_.m_104735_()) {
            com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.b b2;
            float f2;
            LivingEntity livingEntity2;
            if (!(entity instanceof LivingEntity) || !this.a(livingEntity2 = (LivingEntity)entity) || !((double)com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AimAssist.a.f_91074_.m_20270_((Entity)livingEntity2) <= d2) || !((f2 = Math.abs(Mth.m_14177_((float)((b2 = com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.d.a((Entity)livingEntity2)).a() - com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AimAssist.a.f_91074_.m_146908_())))) <= this.k.q())) continue;
            arrayList.add(livingEntity2);
        }
        arrayList.sort(Comparator.comparingDouble(livingEntity -> {
            com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.b b2 = com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.d.a((Entity)livingEntity);
            float f2 = Math.abs(Mth.m_14177_((float)(b2.a() - com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AimAssist.a.f_91074_.m_146908_())));
            float f3 = Math.abs(b2.b() - com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AimAssist.a.f_91074_.m_146909_());
            return Math.sqrt(f2 * f2 + f3 * f3);
        }));
        return arrayList;
    }

    private boolean a(LivingEntity livingEntity) {
        if (livingEntity == com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AimAssist.a.f_91074_) {
            return false;
        }
        if (!livingEntity.m_6084_()) {
            return false;
        }
        if (!com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AimAssist.a.f_91074_.m_142582_((Entity)livingEntity)) {
            return false;
        }
        if (com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AntiBots.b((Entity)livingEntity)) {
            return false;
        }
        if (Teams.a((Entity)livingEntity)) {
            return false;
        }
        if (com.heypixel.heypixelmod.obsoverlay.modules.impl.b.ClientFriend.a((Entity)livingEntity)) {
            return false;
        }
        Target p2 = com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a(Target.class);
        if (p2 != null && p2.m()) {
            return p2.a((Entity)livingEntity);
        }
        return livingEntity instanceof Player;
    }
}

