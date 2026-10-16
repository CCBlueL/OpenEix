/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.a;

import com.heypixel.heypixelmod.obsoverlay.EixClientCore;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventRunTicks;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerRespawnEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AntiBots;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.a.CrystalAura;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.ClientFriend;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.Target;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.Teams;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Scaffold;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland;
import com.heypixel.heypixelmod.obsoverlay.utils.a.SprintResetController;
import com.heypixel.heypixelmod.obsoverlay.utils.EntityOverlayRenderer;
import com.heypixel.heypixelmod.obsoverlay.utils.EntityNameFilter;
import com.mojang.blaze3d.vertex.PoseStack;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

@ModuleInfo(a="KillAura", b="\u6740\u622e\u5149\u73af", c="Automatically attacks entities", d=com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory.COMBAT)
public class KillAura
extends com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase {
    public Entity c;
    com.heypixel.heypixelmod.obsoverlay.d.SettingModel.c d = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Aim Range").a(5.0f).d(0.1f).b(1.0f).c(6.0f).a().c();
    com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a e = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "1.9 Mode").a(false).a().b();
    com.heypixel.heypixelmod.obsoverlay.d.SettingModel.c f = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "CPS").a(10.0f).d(1.0f).b(1.0f).c(30.0f).a(() -> !this.e.m()).a().c();
    com.heypixel.heypixelmod.obsoverlay.d.SettingModel.c g = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Rotation Speed").a(45.0f).d(1.0f).b(1.0f).c(180.0f).a().c();
    com.heypixel.heypixelmod.obsoverlay.d.SettingModel.c h = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "FOV").a(360.0f).d(1.0f).b(0.0f).c(360.0f).a().c();
    com.heypixel.heypixelmod.obsoverlay.d.SettingModel.c i = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Drift").a(0.1f).d(0.1f).b(0.0f).c(5.0f).a().c();
    com.heypixel.heypixelmod.obsoverlay.d.SettingModel.c j = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Jitter").a(0.02f).d(0.01f).b(0.0f).c(1.0f).a().c();
    com.heypixel.heypixelmod.obsoverlay.d.SettingModel.c k = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Reach").a(3.0f).d(0.1f).b(1.0f).c(4.5f).a().c();
    com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a l = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Sprint Reset").a(false).a().b();
    com.heypixel.heypixelmod.obsoverlay.d.SettingModel.c m = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Sprint Reset Chance").a(0.35f).d(0.05f).b(0.0f).c(1.0f).a(this.l::m).a().c();
    com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a n = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Raycast Check").a(true).a().b();
    private static final long o = 55L;
    private static final long p = 600L;
    private static final int q = 4;
    private List<Entity> r = new ArrayList<Entity>();
    private long s = 0L;
    private long t = 0L;
    private long u = 0L;
    private int v = -1;
    private int w = 0;
    private int x = -10;
    private Random y;
    private double z;
    private double A;
    private double B;
    private double C;
    private double D;
    private double E;
    private double F;
    private double G;
    private double H;

    public KillAura() {
        this.q();
    }

    @Override
    public void d() {
        this.q();
        this.r.clear();
        this.c = null;
        this.x = -10;
        this.s = 0L;
        this.t = 0L;
        this.u = 0L;
        this.v = -1;
        this.w = 0;
        com.heypixel.heypixelmod.obsoverlay.utils.SmoothValueInterpolator.d.e();
    }

    @Override
    public void e() {
        this.c = null;
        this.x = -10;
        this.r.clear();
        com.heypixel.heypixelmod.obsoverlay.utils.SmoothValueInterpolator.d.d();
        com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.b(this);
    }

    private void q() {
        this.y = new Random(System.nanoTime());
        this.z = 0.0;
        this.A = this.y.nextDouble() * 0.3 + 0.1;
        this.B = this.y.nextDouble() * 0.5 + 0.5;
        this.C = this.y.nextDouble() * 0.3 + 0.1;
        this.D = this.y.nextDouble() * 0.5 + 0.5;
        this.E = this.y.nextDouble() * Math.PI * 2.0;
        this.F = this.y.nextDouble() * Math.PI * 2.0;
        this.G = this.y.nextDouble() * Math.PI * 2.0;
        this.H = this.y.nextDouble() * Math.PI * 2.0;
    }

    private com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.b a(com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.b b2, com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.b b3, float f2) {
        double d2;
        double d3;
        double d4;
        double d5;
        if (b2 == null || b3 == null) {
            return b3;
        }
        float f3 = Mth.m_14177_((float)(b3.a() - b2.a()));
        float f4 = b3.b() - b2.b();
        double d6 = this.g.q();
        double d7 = this.i.q();
        double d8 = this.j.q();
        if (d6 <= 0.0) {
            return b3;
        }
        float f5 = f3 * f2;
        float f6 = f4 * f2;
        double d9 = Math.sqrt(f5 * f5 + f6 * f6);
        if (d9 < d7) {
            return new com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.b(b2.a() + f5, b2.b() + f6);
        }
        if (d9 > 0.0) {
            d5 = (double)Math.abs(f5) / d9;
            d4 = (double)Math.abs(f6) / d9;
            d3 = d6 * d5 * (double)f2;
            d2 = d6 * d4 * (double)f2;
            f5 = Mth.m_14036_((float)f5, (float)((float)(-d3)), (float)((float)d3));
            f6 = Mth.m_14036_((float)f6, (float)((float)(-d2)), (float)((float)d2));
        }
        this.z += (double)f2;
        d5 = Math.sin(this.z * this.A + this.E) + (this.y.nextDouble() * 0.1 + 0.45) * Math.sin(this.z * this.B + this.F);
        d4 = Math.sin(this.z * this.C + this.G) + (this.y.nextDouble() * 0.1 + 0.45) * Math.sin(this.z * this.D + this.H);
        d3 = d5 * d7 * (double)f2;
        d2 = d4 * d7 * (double)f2;
        double d10 = (this.y.nextDouble() * 2.0 - 1.0) * d8 * (double)f2;
        double d11 = (this.y.nextDouble() * 2.0 - 1.0) * d8 * (double)f2;
        float f7 = f5 + (float)d3 + (float)d10;
        float f8 = f6 + (float)d2 + (float)d11;
        float f9 = b2.a() + f7;
        float f10 = Mth.m_14036_((float)(b2.b() + f8), (float)-90.0f, (float)90.0f);
        return com.heypixel.heypixelmod.obsoverlay.modules.impl.a.KillAura.a(new com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.b(f9, f10), b2);
    }

    private static com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.b a(com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.b b2, com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.b b3) {
        if (b2 == null || b3 == null) {
            return b2;
        }
        double d2 = (double)((Double)com.heypixel.heypixelmod.obsoverlay.modules.impl.a.KillAura.a.f_91066_.m_231964_().m_231551_()).floatValue() * 0.6 + 0.2;
        double d3 = d2 * d2 * d2 * 8.0;
        double d4 = d3 * 0.15;
        float f2 = b2.a() - b3.a();
        float f3 = b2.b() - b3.b();
        float f4 = b3.a() + (float)((double)Math.round((double)f2 / d4) * d4);
        float f5 = Mth.m_14036_((float)(b3.b() + (float)((double)Math.round((double)f3 / d4) * d4)), (float)-90.0f, (float)90.0f);
        return new com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.b(f4, f5);
    }

    @EventTarget
    public void onRespawn(PlayerRespawnEvent u2) {
        this.c = null;
        this.x = -10;
        this.r.clear();
        if (this.m()) {
            this.a(false);
        }
    }

    @EventTarget
    public void onPreTick(EventRunTicks eventRunTicks) {
        com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.b b2;
        double d2;
        Function<com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.b, Boolean> function;
        boolean bl;
        com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.b b3;
        boolean bl2;
        if (com.heypixel.heypixelmod.obsoverlay.modules.impl.a.KillAura.a.f_91074_ == null || com.heypixel.heypixelmod.obsoverlay.modules.impl.a.KillAura.a.f_91073_ == null || eventRunTicks.type() != com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.a) {
            return;
        }
        ++this.w;
        com.heypixel.heypixelmod.obsoverlay.utils.SmoothValueInterpolator.d.b();
        boolean bl3 = com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a(Scaffold.class).m();
        boolean bl4 = com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a(com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Blink.class).m();
        CrystalAura c2 = com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a(CrystalAura.class);
        if (bl3 || c2 != null && c2.c != null || bl4) {
            this.r();
            return;
        }
        this.s();
        if (this.c == null) {
            this.r();
            return;
        }
        double d3 = this.d.q();
        double d4 = this.k.q();
        double d5 = com.heypixel.heypixelmod.obsoverlay.utils.SmoothValueInterpolator.b.a(this.c);
        boolean bl5 = this.c.m_6084_() && !this.c.m_213877_();
        boolean bl6 = bl5 && d5 <= d3;
        boolean bl7 = bl2 = bl5 && d5 <= d4 + 0.06;
        if (!bl5) {
            this.r();
            return;
        }
        if (!bl6) {
            if (this.w - this.x > 4) {
                this.r();
                return;
            }
        } else {
            this.x = this.w;
        }
        if ((b3 = com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.d.a(this.c)) == null) {
            this.r();
            return;
        }
        com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.b b4 = com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.j();
        if (b4 == null) {
            b4 = new com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.b(com.heypixel.heypixelmod.obsoverlay.modules.impl.a.KillAura.a.f_91074_.m_146908_(), com.heypixel.heypixelmod.obsoverlay.modules.impl.a.KillAura.a.f_91074_.m_146909_());
        }
        com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.b b5 = (bl = Boolean.TRUE.equals((function = new Function<com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.b, Boolean>(d2 = d4){
            private float c = Float.NaN;
            private float d = Float.NaN;
            private boolean e = false;
            private boolean f = false;
            final /* synthetic */ double a;
            {
                this.a = d2;
            }

            public Boolean a(com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.b b2) {
                EntityHitResult entityHitResult;
                if (b2 == null) {
                    return false;
                }
                if (this.f && b2.a() == this.c && b2.b() == this.d) {
                    return this.e;
                }
                HitResult hitResult = com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.a.a(b2, this.a);
                boolean bl = hitResult instanceof EntityHitResult && (entityHitResult = (EntityHitResult)hitResult).m_82443_().equals((Object)KillAura.this.c);
                this.c = b2.a();
                this.d = b2.b();
                this.e = bl;
                this.f = true;
                return bl;
            }

            @Override
            public /* synthetic */ Object apply(Object object) {
                return this.a((com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.b)object);
            }
        }).apply(b3))) ? b3 : ((b2 = this.a(b4, b3, 1.0f)) != null && !Float.isNaN(b2.a()) && !Float.isNaN(b2.b()) && !Float.isInfinite(b2.a()) && !Float.isInfinite(b2.b()) ? b2 : b3);
        com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.a(this, b5, this.g.q(), function);
        if (!bl2) {
            return;
        }
        this.a(b5, Boolean.TRUE.equals(function.apply(b5)));
    }

    private void r() {
        boolean bl = this.c != null;
        this.c = null;
        if (!bl) {
            return;
        }
        com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.b(this);
    }

    private void a(com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.b b2, boolean bl) {
        Entity entity;
        long l2;
        if (this.c == null || com.heypixel.heypixelmod.obsoverlay.modules.impl.a.KillAura.a.f_91074_ == null || com.heypixel.heypixelmod.obsoverlay.modules.impl.a.KillAura.a.f_91072_ == null) {
            return;
        }
        if (this.v == this.w) {
            return;
        }
        long l3 = System.currentTimeMillis();
        if (this.e.m() ? com.heypixel.heypixelmod.obsoverlay.modules.impl.a.KillAura.a.f_91074_.m_36403_(0.0f) < 1.0f : l3 - this.s < (l2 = Math.max(this.t, 55L))) {
            return;
        }
        if (!bl) {
            if (this.n.m() && !com.heypixel.heypixelmod.obsoverlay.utils.SmoothValueInterpolator.b.a(this.c, b2, this.k.q())) {
                return;
            }
            if (!this.n.m() && com.heypixel.heypixelmod.obsoverlay.utils.SmoothValueInterpolator.b.a(this.c) > (double)this.k.q() - 0.06) {
                return;
            }
        }
        if (this.l.m() && l3 - this.u >= 600L && com.heypixel.heypixelmod.obsoverlay.utils.SmoothValueInterpolator.a.b(this.m.q())) {
            com.heypixel.heypixelmod.obsoverlay.utils.SmoothValueInterpolator.d.a();
            this.u = l3;
        }
        com.heypixel.heypixelmod.obsoverlay.modules.impl.a.KillAura.a.f_91072_.m_105223_((Player)com.heypixel.heypixelmod.obsoverlay.modules.impl.a.KillAura.a.f_91074_, this.c);
        com.heypixel.heypixelmod.obsoverlay.modules.impl.a.KillAura.a.f_91074_.m_6674_(InteractionHand.MAIN_HAND);
        this.v = this.w;
        if (!this.e.m()) {
            this.s = l3;
            this.t = com.heypixel.heypixelmod.obsoverlay.utils.SmoothValueInterpolator.a.a(this.f.q());
        }
        if ((entity = this.c) instanceof Player) {
            Player player = (Player)entity;
            DynamicIsland.a(player);
        }
    }

    private void s() {
        if (com.heypixel.heypixelmod.obsoverlay.modules.impl.a.KillAura.a.f_91074_ == null || com.heypixel.heypixelmod.obsoverlay.modules.impl.a.KillAura.a.f_91073_ == null) {
            this.c = null;
            this.r = new ArrayList<Entity>();
            return;
        }
        float f2 = this.d.q();
        double d2 = f2 * f2;
        float f3 = this.h.q();
        this.c = null;
        double d3 = Double.MAX_VALUE;
        Target p2 = com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a(Target.class);
        double d4 = com.heypixel.heypixelmod.obsoverlay.modules.impl.a.KillAura.a.f_91074_.m_20185_();
        double d5 = com.heypixel.heypixelmod.obsoverlay.modules.impl.a.KillAura.a.f_91074_.m_20189_();
        float f4 = com.heypixel.heypixelmod.obsoverlay.modules.impl.a.KillAura.a.f_91074_.m_146908_();
        float f5 = f3;
        AABB aABB = com.heypixel.heypixelmod.obsoverlay.modules.impl.a.KillAura.a.f_91074_.m_20191_().m_82400_((double)f2);
        List list = com.heypixel.heypixelmod.obsoverlay.modules.impl.a.KillAura.a.f_91073_.m_6249_((Entity)com.heypixel.heypixelmod.obsoverlay.modules.impl.a.KillAura.a.f_91074_, aABB, entity -> entity instanceof LivingEntity && entity != com.heypixel.heypixelmod.obsoverlay.modules.impl.a.KillAura.a.f_91074_ && entity.m_6084_() && !entity.m_5833_() && !com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AntiBots.b(entity) && (p2 == null || p2.a((Entity)entity)) && !com.heypixel.heypixelmod.obsoverlay.modules.impl.b.Teams.a(entity) && !com.heypixel.heypixelmod.obsoverlay.utils.EntityNameFilter.a(entity) && !com.heypixel.heypixelmod.obsoverlay.modules.impl.b.ClientFriend.a(entity) && com.heypixel.heypixelmod.obsoverlay.modules.impl.a.KillAura.a.f_91074_.m_20280_(entity) <= d2 && com.heypixel.heypixelmod.obsoverlay.modules.impl.a.KillAura.a(entity, d4, d5, f4, f5));
        this.r = list != null ? list : new ArrayList();
        for (Entity entity2 : this.r) {
            double d6 = com.heypixel.heypixelmod.obsoverlay.modules.impl.a.KillAura.a.f_91074_.m_20280_(entity2);
            if (!(d6 < d3) || !(d6 <= d2)) continue;
            d3 = d6;
            this.c = entity2;
        }
        this.a(this.r.size() + " Targets");
    }

    private static boolean a(Entity entity, double d2, double d3, float f2, float f3) {
        double d4;
        if (f3 >= 360.0f) {
            return true;
        }
        double d5 = entity.m_20185_() - d2;
        if (d5 * d5 + (d4 = entity.m_20189_() - d3) * d4 < 1.0E-6) {
            return true;
        }
        float f4 = (float)(-Mth.m_14136_((double)d5, (double)d4) * 57.29577951308232);
        return Math.abs(Mth.m_14177_((float)(f4 - f2))) <= f3;
    }

    public List<Entity> p() {
        return this.r;
    }

    @EventTarget
    public void onRender(com.heypixel.heypixelmod.obsoverlay.events.impl.WorldRenderEvent p2) {
        if (this.r == null || this.r.isEmpty()) {
            return;
        }
        PoseStack poseStack = p2.b();
        for (Entity entity : this.r) {
            if (entity == null) continue;
            if (entity.equals((Object)this.c)) {
                EntityOverlayRenderer.a(poseStack, entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), entity.m_20205_(), entity.m_20206_(), new Color(200, 0, 0, 60).getRGB());
                continue;
            }
            EntityOverlayRenderer.a(poseStack, entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), entity.m_20205_(), entity.m_20206_(), new Color(0, 200, 0, 60).getRGB());
        }
    }
}

