/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.a;

import com.heypixel.heypixelmod.obsoverlay.d.a.NumericSetting;
import com.heypixel.heypixelmod.obsoverlay.d.a.ModeSetting;
import com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventRunTicks;
import com.heypixel.heypixelmod.obsoverlay.events.impl.MovementInputEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PacketInterceptEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.WorldRenderEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.CrosshairRenderEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AntiBots;
import com.heypixel.heypixelmod.obsoverlay.utils.EntityOverlayRenderer;
import com.heypixel.heypixelmod.obsoverlay.utils.RotationInterpolation;
import com.heypixel.heypixelmod.obsoverlay.utils.PlayerRotationUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import io.github.humbleui.skija.ClipMode;
import io.github.humbleui.skija.Font;
import io.github.humbleui.skija.Path;
import io.github.humbleui.types.RRect;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundDisconnectPacket;
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket;
import net.minecraft.network.protocol.game.ClientboundPingPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerLookAtPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
import net.minecraft.network.protocol.game.ClientboundRespawnPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.protocol.game.ClientboundSetHealthPacket;
import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

@ModuleInfo(a="Velocity", b="\u53cd\u51fb\u9000", c="Reduces knockback.", d=com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory.COMBAT)
public class Velocity
extends com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase {
    private final AutoHeal d = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Mode").a("Legit", "Reduce", "NoXZ").a(0).a().e();
    private final Queue<Packet<? super ClientPacketListener>> e = new ConcurrentLinkedQueue<Packet<? super ClientPacketListener>>();
    private final CrystalAura f = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "AttackCounts").a(3.0f).d(1.0f).b(1.0f).c(5.0f).a(() -> this.d.a("NoXZ")).a().c();
    private final CrystalAura g = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "MaxAlinkTime (ms)").a(5000.0f).d(50.0f).b(50.0f).c(10000.0f).a(() -> this.d.a("NoXZ")).a().c();
    private final CrystalAura h = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Horizontal %").a(35.0f).d(5.0f).b(0.0f).c(100.0f).a(() -> this.d.a("Reduce")).a().c();
    private final CrystalAura i = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Vertical %").a(100.0f).d(5.0f).b(0.0f).c(100.0f).a(() -> this.d.a("Reduce")).a().c();
    private final CrystalAura j = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Jump Window (ms)").a(280.0f).d(20.0f).b(60.0f).c(600.0f).a(() -> this.d.a("Legit")).a().c();
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.b k = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "AlinkHUD Position").e(0.0f).f(0.0f).a().f();
    private final RotationInterpolation l = new RotationInterpolation(1.0f, 0.0f, 0.2f);
    private final RotationInterpolation m = new RotationInterpolation(0.0f, 0.0f, 0.12f);
    private final Map<Entity, com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.d> n = new HashMap<Entity, com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.d>();
    public boolean c;
    private boolean o;
    private long p;
    private Vec3 q;
    private long r;
    private long s;
    private Player t;
    private PacketState u;
    private boolean v;
    private static final int w = 64;

    private void a(PacketState a2) {
        this.u = a2;
        this.s = System.currentTimeMillis();
    }

    private boolean a(long l2) {
        return System.currentTimeMillis() - this.s > l2;
    }

    @EventTarget
    public void onPacket(PacketInterceptEvent m2) {
        switch (this.d.l()) {
            case "Legit": {
                ClientboundSetEntityMotionPacket clientboundSetEntityMotionPacket;
                Packet<?> packet = m2.c();
                if (!(packet instanceof ClientboundSetEntityMotionPacket) || (clientboundSetEntityMotionPacket = (ClientboundSetEntityMotionPacket)packet).m_133192_() != com.heypixel.heypixelmod.obsoverlay.modules.impl.a.Velocity.a.f_91074_.m_19879_()) break;
                this.o = true;
                this.p = System.currentTimeMillis();
                break;
            }
            case "Reduce": {
                ClientboundSetEntityMotionPacket clientboundSetEntityMotionPacket;
                Packet<?> packet = m2.c();
                if (!(packet instanceof ClientboundSetEntityMotionPacket) || (clientboundSetEntityMotionPacket = (ClientboundSetEntityMotionPacket)packet).m_133192_() != com.heypixel.heypixelmod.obsoverlay.modules.impl.a.Velocity.a.f_91074_.m_19879_()) break;
                double d2 = (double)this.h.q() / 100.0;
                double d3 = (double)this.i.q() / 100.0;
                if (d2 >= 0.999 && d3 >= 0.999) {
                    return;
                }
                Vec3 vec3 = new Vec3((double)clientboundSetEntityMotionPacket.m_133195_() / 8000.0 * d2, (double)clientboundSetEntityMotionPacket.m_133196_() / 8000.0 * d3, (double)clientboundSetEntityMotionPacket.m_133197_() / 8000.0 * d2);
                m2.a((Packet<?>)new ClientboundSetEntityMotionPacket(clientboundSetEntityMotionPacket.m_133192_(), vec3));
                break;
            }
            case "NoXZ": {
                Entity entity;
                Packet<?> packet;
                if (m2.c() instanceof ClientboundPlayerPositionPacket && this.u == com.heypixel.heypixelmod.obsoverlay.modules.impl.a.Velocity.PacketState.a) {
                    this.c = true;
                    return;
                }
                Object object = m2.c();
                if (object instanceof ClientboundSetEntityMotionPacket && (packet = (Packet<?>)object).m_133192_() == com.heypixel.heypixelmod.obsoverlay.modules.impl.a.Velocity.a.f_91074_.m_19879_()) {
                    if (this.u == com.heypixel.heypixelmod.obsoverlay.modules.impl.a.Velocity.PacketState.a) {
                        if (!this.c) {
                            this.a(com.heypixel.heypixelmod.obsoverlay.modules.impl.a.Velocity.PacketState.b);
                            this.r = System.currentTimeMillis();
                            m2.a(true);
                            this.q = new Vec3((double)packet.m_133195_() / 8000.0, (double)packet.m_133196_() / 8000.0, (double)packet.m_133197_() / 8000.0);
                        } else {
                            this.c = false;
                        }
                        return;
                    }
                    this.q = new Vec3((double)packet.m_133195_() / 8000.0, (double)packet.m_133196_() / 8000.0, (double)packet.m_133197_() / 8000.0);
                    this.a(com.heypixel.heypixelmod.obsoverlay.modules.impl.a.Velocity.PacketState.e);
                    m2.a(true);
                    return;
                }
                if (this.u == com.heypixel.heypixelmod.obsoverlay.modules.impl.a.Velocity.PacketState.a || m2.b() != com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.d) break;
                packet = m2.c();
                if (packet instanceof ClientboundPlayerPositionPacket) {
                    while (!this.e.isEmpty()) {
                        object = this.e.poll();
                        if (object == null || a.m_91403_() == null) continue;
                        object.m_5797_((PacketListener)a.m_91403_());
                    }
                    this.b(false);
                    return;
                }
                if (packet instanceof ClientboundPlayerLookAtPacket) {
                    this.a(com.heypixel.heypixelmod.obsoverlay.modules.impl.a.Velocity.PacketState.e);
                    return;
                }
                if (packet instanceof ClientboundDisconnectPacket || packet instanceof ClientboundRespawnPacket) {
                    this.b(false);
                    return;
                }
                if (!(packet instanceof ClientboundPingPacket || packet instanceof ClientboundMoveEntityPacket || packet instanceof ClientboundTeleportEntityPacket || packet instanceof ClientboundSetEntityMotionPacket || packet instanceof ClientboundSetHealthPacket)) {
                    return;
                }
                if (packet instanceof ClientboundMoveEntityPacket && (entity = (object = (Packet<?>)packet).m_132519_((Level)com.heypixel.heypixelmod.obsoverlay.modules.impl.a.Velocity.a.f_91073_)) != null) {
                    com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.d d4 = this.n.getOrDefault(entity, new com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.d(entity.m_20185_(), entity.m_20186_(), entity.m_20189_()));
                    if (object.m_132534_()) {
                        double d5 = (double)object.m_178997_() / 4096.0;
                        double d6 = (double)object.m_178998_() / 4096.0;
                        double d7 = (double)object.m_178999_() / 4096.0;
                        this.n.put(entity, new com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.d(d4.b() + d5, d4.c() + d6, d4.d() + d7));
                    }
                }
                if (packet instanceof ClientboundTeleportEntityPacket && (entity = com.heypixel.heypixelmod.obsoverlay.modules.impl.a.Velocity.a.f_91073_.m_6815_((object = (ClientboundTeleportEntityPacket)packet).m_133545_())) != null) {
                    this.n.put(entity, new com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.d(object.m_133548_(), object.m_133549_(), object.m_133550_()));
                }
                if (this.e.size() >= 64) {
                    this.b(false);
                    return;
                }
                this.e.add(packet);
                m2.a(true);
            }
        }
    }

    @EventTarget
    public void onRespawn(com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerRespawnEvent u2) {
        this.b(true);
        if (this.m()) {
            this.a(false);
        }
    }

    @EventTarget
    public void onPreTick(EventRunTicks eventRunTicks) {
        if (com.heypixel.heypixelmod.obsoverlay.modules.impl.a.Velocity.a.f_91074_ == null) {
            return;
        }
        if (eventRunTicks.type() == com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.b && this.u == com.heypixel.heypixelmod.obsoverlay.modules.impl.a.Velocity.PacketState.d) {
            this.b(true);
        }
        if (eventRunTicks.type() == com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.b && this.u == com.heypixel.heypixelmod.obsoverlay.modules.impl.a.Velocity.PacketState.e) {
            com.heypixel.heypixelmod.obsoverlay.modules.impl.a.Velocity.a.f_91074_.m_20334_(this.q.f_82479_, this.q.f_82480_, this.q.f_82481_);
            this.b(true);
        }
        if (eventRunTicks.type() != com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.a) {
            return;
        }
        switch (this.d.l()) {
            case "NoXZ": {
                if (this.u == com.heypixel.heypixelmod.obsoverlay.modules.impl.a.Velocity.PacketState.c) {
                    Player player;
                    EntityHitResult entityHitResult;
                    HitResult hitResult = com.heypixel.heypixelmod.obsoverlay.modules.impl.a.Velocity.a.f_91077_;
                    if (hitResult instanceof EntityHitResult && (hitResult = (entityHitResult = (EntityHitResult)hitResult).m_82443_()) instanceof Player && !com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AntiBots.b((Entity)(player = (Player)hitResult))) {
                        double d2 = 1.0;
                        for (int i2 = 0; i2 < (int)this.f.q(); ++i2) {
                            if (com.heypixel.heypixelmod.obsoverlay.modules.impl.a.Velocity.a.f_91074_.m_20142_()) {
                                com.heypixel.heypixelmod.obsoverlay.modules.impl.a.Velocity.a.f_91074_.m_6858_(false);
                            }
                            com.heypixel.heypixelmod.obsoverlay.modules.impl.a.Velocity.a.f_91072_.m_105223_((Player)com.heypixel.heypixelmod.obsoverlay.modules.impl.a.Velocity.a.f_91074_, (Entity)this.t);
                            com.heypixel.heypixelmod.obsoverlay.modules.impl.a.Velocity.a.f_91074_.m_6674_(InteractionHand.MAIN_HAND);
                            d2 *= 0.6;
                        }
                        com.heypixel.heypixelmod.obsoverlay.modules.impl.a.Velocity.a.f_91074_.m_20334_(this.q.f_82479_ * d2, this.q.f_82480_, this.q.f_82481_ * d2);
                        this.a(com.heypixel.heypixelmod.obsoverlay.modules.impl.a.Velocity.PacketState.d);
                    } else if (this.a(500L)) {
                        com.heypixel.heypixelmod.obsoverlay.modules.impl.a.Velocity.a.f_91074_.m_20334_(this.q.f_82479_, this.q.f_82480_, this.q.f_82481_);
                        this.a(com.heypixel.heypixelmod.obsoverlay.modules.impl.a.Velocity.PacketState.d);
                    }
                } else if (this.u == com.heypixel.heypixelmod.obsoverlay.modules.impl.a.Velocity.PacketState.b && (float)(System.currentTimeMillis() - this.r) >= this.g.q()) {
                    com.heypixel.heypixelmod.obsoverlay.modules.impl.a.Velocity.a.f_91074_.m_20334_(this.q.f_82479_, this.q.f_82480_, this.q.f_82481_);
                    this.a(com.heypixel.heypixelmod.obsoverlay.modules.impl.a.Velocity.PacketState.d);
                }
                if (!this.c || com.heypixel.heypixelmod.obsoverlay.modules.impl.a.Velocity.a.f_91074_.f_20916_ != 0) break;
                this.c = false;
                break;
            }
            case "Legit": {
                if (!this.o || System.currentTimeMillis() - this.p <= (long)this.j.q()) break;
                this.o = false;
            }
        }
        this.a(this.d.l() + (String)(this.u == com.heypixel.heypixelmod.obsoverlay.modules.impl.a.Velocity.PacketState.b ? " Alink " + (System.currentTimeMillis() - this.r) / 50L + "Ticks" : ""));
    }

    public void b(boolean bl) {
        this.c = false;
        this.u = com.heypixel.heypixelmod.obsoverlay.modules.impl.a.Velocity.PacketState.a;
        this.n.clear();
        this.t = null;
        if (!bl) {
            this.e.clear();
            return;
        }
        while (!this.e.isEmpty()) {
            Packet<? super ClientPacketListener> packet = this.e.poll();
            if (packet == null || a.m_91403_() == null) continue;
            packet.m_5797_((PacketListener)a.m_91403_());
        }
    }

    public boolean p() {
        return this.d.a("NoXZ") && this.u != com.heypixel.heypixelmod.obsoverlay.modules.impl.a.Velocity.PacketState.a;
    }

    @Override
    public void d() {
        this.o = false;
        this.p = 0L;
        this.c = false;
        this.n.clear();
        this.t = null;
        this.e.clear();
        this.q = null;
        this.a(com.heypixel.heypixelmod.obsoverlay.modules.impl.a.Velocity.PacketState.a);
    }

    @EventTarget
    public void onMoveInput(MovementInputEvent l2) {
        switch (this.d.l()) {
            case "NoXZ": {
                Player player;
                EntityHitResult entityHitResult;
                HitResult hitResult;
                if (this.u != com.heypixel.heypixelmod.obsoverlay.modules.impl.a.Velocity.PacketState.b || this.q == null || !((hitResult = com.heypixel.heypixelmod.obsoverlay.modules.impl.a.Velocity.a.f_91077_) instanceof EntityHitResult) || !((hitResult = (entityHitResult = (EntityHitResult)hitResult).m_82443_()) instanceof Player) || com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AntiBots.b((Entity)(player = (Player)hitResult))) break;
                l2.a(1.0f);
                l2.b(0.0f);
                this.a(com.heypixel.heypixelmod.obsoverlay.modules.impl.a.Velocity.PacketState.c);
                this.t = player;
                break;
            }
            case "Legit": {
                if (!this.o) break;
                if (com.heypixel.heypixelmod.obsoverlay.modules.impl.a.Velocity.a.f_91074_.m_20096_() && com.heypixel.heypixelmod.obsoverlay.utils.PlayerRotationUtils.a()) {
                    l2.a(true);
                }
                this.o = false;
            }
        }
    }

    @Override
    public void e() {
        this.o = false;
        this.c = false;
        this.n.clear();
        this.t = null;
        this.q = null;
        this.b(true);
        this.a(com.heypixel.heypixelmod.obsoverlay.modules.impl.a.Velocity.PacketState.a);
    }

    @EventTarget
    public void onRender(WorldRenderEvent p2) {
        if (this.u == com.heypixel.heypixelmod.obsoverlay.modules.impl.a.Velocity.PacketState.a) {
            return;
        }
        PoseStack poseStack = p2.b();
        for (Entity entity : this.n.keySet()) {
            if (!(entity instanceof Player)) continue;
            com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.d d2 = this.n.get(entity);
            if (entity.equals((Object)this.t)) {
                EntityOverlayRenderer.a(poseStack, d2.b(), d2.c(), d2.d(), entity.m_20205_(), entity.m_20206_(), new Color(200, 0, 0, 60).getRGB());
                continue;
            }
            EntityOverlayRenderer.a(poseStack, d2.b(), d2.c(), d2.d(), entity.m_20205_(), entity.m_20206_(), new Color(0, 200, 0, 60).getRGB());
        }
    }

    @EventTarget
    public void onRenderSkia(CrosshairRenderEvent s2) {
        float f2;
        float f3;
        long l2;
        boolean bl = com.heypixel.heypixelmod.obsoverlay.modules.impl.a.Velocity.a.f_91080_ instanceof com.heypixel.heypixelmod.obsoverlay.c.HudEditorScreen;
        boolean bl2 = this.d.a("NoXZ");
        boolean bl3 = this.u != null && this.u != com.heypixel.heypixelmod.obsoverlay.modules.impl.a.Velocity.PacketState.a;
        boolean bl4 = bl || this.m() && bl2;
        this.l.a = 1.0f;
        this.l.a(bl4);
        if (this.l.c <= 0.01f) {
            return;
        }
        Font font = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.c.a(10.0f);
        long l3 = System.currentTimeMillis();
        long l4 = (long)this.g.q();
        long l5 = l2 = bl3 && this.u == com.heypixel.heypixelmod.obsoverlay.modules.impl.a.Velocity.PacketState.b ? Math.max(0L, l3 - this.r) : 0L;
        float f4 = bl ? 0.6f : (l4 <= 0L ? 0.0f : (bl3 && this.u == com.heypixel.heypixelmod.obsoverlay.modules.impl.a.Velocity.PacketState.b ? Math.min(1.0f, (float)l2 / (float)l4) : (bl3 && this.u == com.heypixel.heypixelmod.obsoverlay.modules.impl.a.Velocity.PacketState.c ? 1.0f : 0.0f)));
        this.m.a = f4;
        this.m.a(true);
        String string = "Alink";
        float f5 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string, font);
        float f6 = 32.0f;
        float f7 = 120.0f;
        float f8 = Math.max(f7, f5 + 20.0f);
        float f9 = 10.0f;
        if (!this.v && this.k.n() == this.k.l() && this.k.o() == this.k.m()) {
            f3 = a.m_91268_().m_85445_();
            f2 = a.m_91268_().m_85446_();
            float f10 = f3 / 2.0f - f8 / 2.0f;
            float f11 = f2 - 55.0f;
            this.k.a(f10, f11);
            this.v = true;
        }
        f3 = this.k.n();
        f2 = this.k.o();
        int n2 = Math.min(255, Math.max(0, (int)(80.0f * this.l.c)));
        Color color = new Color(0, 0, 0, n2);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c(f3, f2, f8, f6, f9);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f3, f2, f8, f6, f9);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f3, f2, f8, f6, f9, color);
        float f12 = 5.0f;
        float f13 = 6.0f;
        float f14 = f3 + f12;
        float f15 = f2 + f6 - f12 - f13;
        float f16 = f8 - f12 * 2.0f;
        float f17 = f16 * this.m.c;
        float f18 = f13 / 2.0f;
        Path path = new Path();
        path.addRRect(RRect.makeXYWH((float)f14, (float)f15, (float)f16, (float)f13, (float)f18));
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c();
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().clipPath(path, ClipMode.INTERSECT, true);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f14, f15, f16, f13, f18, new Color(255, 255, 255, 50));
        if (f17 > 0.5f) {
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f14, f15, f17, f13, f18, new Color(196, 128, 224, 220));
        }
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.d();
        path.close();
        float f19 = f3 + f8 / 2.0f;
        float f20 = f2 + 7.0f;
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.d(string, f19, f20, new Color(255, 255, 255, (int)(220.0f * this.l.c)), font);
        String string2 = Math.round(this.m.c * 100.0f) + "%";
        float f21 = f2 + 17.0f;
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.d(string2, f3 + f8 / 2.0f, f21, new Color(255, 255, 255, (int)(230.0f * this.l.c)), font);
        this.k.c(f8);
        this.k.d(f6);
    }

    private static enum PacketState {
        a,
        b,
        c,
        d,
        e;

    }
}

