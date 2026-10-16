/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.c;

import com.heypixel.heypixelmod.obsoverlay.d.a.NumericSetting;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventRender2D;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerMotionEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.Teams;
import com.heypixel.heypixelmod.obsoverlay.utils.EntityRayTraceUtils;
import com.heypixel.heypixelmod.obsoverlay.utils.RotationInterpolation;
import com.heypixel.heypixelmod.obsoverlay.utils.c.EntityRotationFactory;
import com.heypixel.heypixelmod.obsoverlay.utils.EntityNameFilter;
import com.heypixel.heypixelmod.obsoverlay.utils.PacketUtils;
import java.awt.Color;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.player.RemotePlayer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.network.protocol.handshake.ClientIntentionPacket;
import net.minecraft.network.protocol.login.ServerboundHelloPacket;
import net.minecraft.network.protocol.login.ServerboundKeyPacket;
import net.minecraft.network.protocol.status.ServerboundPingRequestPacket;
import net.minecraft.network.protocol.status.ServerboundStatusRequestPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

@ModuleInfo(a="Blink", b="\u77ac\u79fb", d=ModuleCategory.MOVEMENT, c="Suspends all movement packets for teleporting!")
public class Blink
extends com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase {
    public static final Set<Class<?>> c = new HashSet<Class<?>>(){
        {
            this.add(ClientIntentionPacket.class);
            this.add(ServerboundStatusRequestPacket.class);
            this.add(ServerboundPingRequestPacket.class);
            this.add(ServerboundHelloPacket.class);
            this.add(ServerboundKeyPacket.class);
        }
    };
    private static final int j = new Color(150, 45, 45, 255).getRGB();
    private final com.heypixel.heypixelmod.obsoverlay.modules.impl.render.Animations.a.b k = new com.heypixel.heypixelmod.obsoverlay.modules.impl.render.Animations.a.b();
    private final com.heypixel.heypixelmod.obsoverlay.modules.impl.render.Animations.a.a l = new com.heypixel.heypixelmod.obsoverlay.modules.impl.render.Animations.a.a(Collections.singleton(ThrownEgg.class), new Color(255, 238, 154));
    private final com.heypixel.heypixelmod.obsoverlay.modules.impl.render.Animations.a.a m = new com.heypixel.heypixelmod.obsoverlay.modules.impl.render.Animations.a.a(Collections.singleton(Snowball.class), new Color(255, 255, 255));
    private final Queue<Packet<?>> n = new ConcurrentLinkedQueue();
    private final RotationInterpolation o = new RotationInterpolation(0.0f, 0.2f);
    public Eagle d = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Release Ticks on Damage").b(0.0f).c(50.0f).a(20.0f).d(1.0f).a().c();
    public Eagle e = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Release Speed (Tick)").b(3.0f).c(20.0f).a(10.0f).d(1.0f).a().c();
    public Eagle f = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Max Ticks").b(10.0f).c(200.0f).a(20.0f).d(1.0f).a().c();
    public Eagle g = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Player Distance").b(3.0f).c(10.0f).a(4.0f).d(0.1f).a().c();
    public Eagle h = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "TNT Distance").b(3.0f).c(10.0f).a(5.0f).d(0.1f).a().c();
    public Eagle i = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Fake Player HitBoxes").b(0.0f).c(3.0f).a(0.2f).d(0.01f).a().c();
    private boolean p = false;
    private RemotePlayer q;
    private int r = 0;
    private int s = 0;

    public long p() {
        return this.n.stream().filter(packet -> packet instanceof ServerboundMovePlayerPacket).count();
    }

    private void a(ServerboundMovePlayerPacket serverboundMovePlayerPacket) {
        this.q.m_6453_(serverboundMovePlayerPacket.m_134129_(this.q.m_20185_()), serverboundMovePlayerPacket.m_134140_(this.q.m_20186_()), serverboundMovePlayerPacket.m_134146_(this.q.m_20189_()), serverboundMovePlayerPacket.m_134131_(this.q.m_146908_()), serverboundMovePlayerPacket.m_134142_(this.q.m_146909_()), 3, false);
        if (serverboundMovePlayerPacket.m_179684_()) {
            this.q.m_146922_(serverboundMovePlayerPacket.m_134131_(this.q.m_146908_()));
            this.q.m_5616_(serverboundMovePlayerPacket.m_134131_(this.q.m_146908_()));
            this.q.m_146926_(serverboundMovePlayerPacket.m_134142_(this.q.m_146909_()));
        }
    }

    private void q() {
        while (!this.n.isEmpty()) {
            Packet<?> packet = this.n.poll();
            PacketUtils.a(packet);
            if (!(packet instanceof ServerboundMovePlayerPacket)) continue;
            ++this.s;
            this.a((ServerboundMovePlayerPacket)packet);
            break;
        }
    }

    @Override
    public void d() {
        this.n.clear();
        this.r = 0;
        this.p = false;
        this.q = new com.heypixel.heypixelmod.obsoverlay.utils.RemotePlayer((AbstractClientPlayer)com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Blink.a.f_91074_);
        this.q.m_6858_(com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Blink.a.f_91074_.m_20142_());
        com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Blink.a.f_91073_.m_104630_(-1337, (AbstractClientPlayer)this.q);
    }

    @Override
    public void e() {
        if (this.q != null) {
            com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Blink.a.f_91073_.m_171642_(this.q.m_19879_(), Entity.RemovalReason.DISCARDED);
            this.q = null;
        }
    }

    @EventTarget
    public void onRender(EventRender2D eventRender2D) {
    }

    private boolean a(double d2) {
        long l2 = com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Blink.a.f_91073_.m_6907_().stream().filter(abstractClientPlayer -> {
            if (abstractClientPlayer == com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Blink.a.f_91074_) {
                return false;
            }
            if (abstractClientPlayer instanceof com.heypixel.heypixelmod.obsoverlay.utils.RemotePlayer) {
                return false;
            }
            if (com.heypixel.heypixelmod.obsoverlay.modules.impl.b.Teams.a((Entity)abstractClientPlayer)) {
                return false;
            }
            if (com.heypixel.heypixelmod.obsoverlay.utils.EntityNameFilter.a((Entity)abstractClientPlayer)) {
                return false;
            }
            if (com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AntiBots.b((Entity)abstractClientPlayer)) {
                return false;
            }
            return com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.d.a((Player)this.q, (Entity)abstractClientPlayer) < d2;
        }).count();
        return l2 > 0L;
    }

    private boolean b(double d2) {
        Stream<Entity> stream = StreamSupport.stream(com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Blink.a.f_91073_.m_104735_().spliterator(), true);
        long l2 = stream.filter(entity -> entity instanceof PrimedTnt && (double)this.q.m_20270_(entity) <= d2).count();
        return l2 > 0L;
    }

    private boolean c(double d2) {
        for (Entity entity : com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Blink.a.f_91073_.m_104735_()) {
            com.heypixel.heypixelmod.obsoverlay.modules.impl.render.Animations.a.a a2;
            if (entity instanceof Arrow) {
                a2 = this.k;
            } else if (entity instanceof ThrownEgg) {
                a2 = this.l;
            } else {
                if (!(entity instanceof Snowball)) continue;
                a2 = this.m;
            }
            if (a2 == null || !this.a(entity, a2, d2)) continue;
            return true;
        }
        return false;
    }

    private boolean a(Entity entity, com.heypixel.heypixelmod.obsoverlay.modules.impl.render.Animations.a a2, double d2) {
        LocalPlayer localPlayer = com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Blink.a.f_91074_;
        ClientLevel clientLevel = com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Blink.a.f_91073_;
        double d3 = entity.m_20185_();
        double d4 = entity.m_20186_();
        double d5 = entity.m_20189_();
        double d6 = entity.m_20184_().f_82479_;
        double d7 = entity.m_20184_().f_82480_;
        double d8 = entity.m_20184_().f_82481_;
        while (true) {
            float f2 = a2.a();
            float f3 = a2.b();
            AABB aABB = new AABB(d3 - (double)f2, d4, d5 - (double)f2, d3 + (double)f2, d4 + (double)f3, d5 + (double)f2);
            Vec3 vec3 = new Vec3(d3, d4, d5);
            Vec3 vec32 = new Vec3(d3 + d6, d4 + d7, d5 + d8);
            HitResult hitResult = EntityRayTraceUtils.a(vec3, vec32, false, entity instanceof Arrow, false, entity);
            List list = clientLevel.m_45933_((Entity)localPlayer, aABB.m_82310_(d6, d7, d8).m_82363_(1.0, 1.0, 1.0).m_82377_(d2, d2, d2));
            if (list.contains(this.q)) {
                return true;
            }
            d3 += d6;
            d4 += d7;
            d5 += d8;
            if (!hitResult.m_6662_().equals((Object)HitResult.Type.MISS) || d4 < -128.0) {
                return false;
            }
            d6 *= entity.m_20069_() ? 0.8 : 0.99;
            double d9 = d7 * (entity.m_20069_() ? 0.8 : 0.99);
            d8 *= entity.m_20069_() ? 0.8 : 0.99;
            d7 = d9 - (double)a2.c();
        }
    }

    private boolean r() {
        return this.b(this.h.q()) || this.a(this.g.q()) || this.c(this.i.q());
    }

    @Override
    public void a(boolean bl) {
        if (com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Blink.a.f_91074_ != null) {
            if (bl) {
                super.a(true);
            } else if (!this.p) {
                this.p = true;
            } else if (this.n.isEmpty()) {
                super.a(false);
            }
        }
    }

    @EventTarget
    public void onMotion(PlayerMotionEvent k2) {
        if (k2.b() == com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.a && com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Blink.a.f_91074_ != null) {
            this.a(this.p() + " Ticks Behind");
            this.o.a = Mth.m_14036_((float)((float)this.p() / this.f.q() * 100.0f), (float)0.0f, (float)100.0f);
            this.s = 0;
            if (com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Blink.a.f_91074_.f_20916_ == 10) {
                this.r += (int)this.d.q();
            }
            while ((float)this.s < this.e.q() && this.r > 0 && !this.n.isEmpty()) {
                this.q();
                --this.r;
            }
            while ((float)this.s < this.e.q() && this.r() && !this.n.isEmpty()) {
                this.q();
            }
            while ((float)this.s < this.e.q() && (float)this.p() >= this.f.q() && !this.n.isEmpty()) {
                this.q();
            }
            if (this.p) {
                while ((float)this.s < this.e.q() && !this.n.isEmpty()) {
                    this.q();
                }
                if (this.n.isEmpty()) {
                    this.a(false);
                }
            }
        }
    }

    @EventTarget(a=4)
    public void onPacket(com.heypixel.heypixelmod.obsoverlay.events.impl.PacketInterceptEvent m2) {
        if (m2.b() == com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.c && com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Blink.a.f_91074_ != null && !m2.a()) {
            if (c.contains(m2.c().getClass())) {
                return;
            }
            m2.a(true);
            this.n.offer(m2.c());
        }
    }
}

