/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.c;

import com.heypixel.heypixelmod.mixin.O.accessors.MultiPlayerGameModeAccessor;
import com.heypixel.heypixelmod.obsoverlay.d.a.ModeSetting;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.KeybindActionEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerMotionEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;
import com.heypixel.heypixelmod.obsoverlay.utils.c.PlayerRotationController;
import com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils;
import com.heypixel.heypixelmod.obsoverlay.utils.BlockPredictionUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ServerboundUseItemPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

@ModuleInfo(a="NoFall", b="\u514d\u6454\u4f24\u5bb3", c="Prevents fall damage", d=ModuleCategory.MOVEMENT)
public class NoFall
extends ModuleBase {
    private final Fly c = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "FallDistance Mode").a("Calc", "Custom").a().e();
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.c d = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "FallDistance").a(10.0f).b(2.0f).c(100.0f).d(1.0f).a(() -> "Custom".equals(this.c.l())).a().c();
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.c e = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Rotation Speed").a(180.0f).d(1.0f).b(1.0f).c(180.0f).a().c();
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a f = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Retrieve Water").a(true).a().b();
    private static final long g = 50L;
    private boolean h = false;
    private boolean i = false;
    private boolean j = false;
    private boolean k = false;
    private int l = 0;
    private int m = -1;
    private long n = 0L;
    private BlockPos o = null;
    private com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.b p = null;

    @EventTarget
    public void onMotion(PlayerMotionEvent k2) {
        if (k2.b() == com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.a) {
            if (com.heypixel.heypixelmod.obsoverlay.modules.impl.c.NoFall.a.f_91074_ == null || com.heypixel.heypixelmod.obsoverlay.modules.impl.c.NoFall.a.f_91073_ == null) {
                return;
            }
            if (this.h && com.heypixel.heypixelmod.obsoverlay.modules.impl.c.NoFall.a.f_91074_.m_20096_()) {
                this.i = true;
                if (this.o != null) {
                    this.p = com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.d.a(this.o, Direction.UP);
                }
            }
            if (this.j || this.k || this.i) {
                com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.b b2 = this.p == null ? new com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.b(com.heypixel.heypixelmod.obsoverlay.modules.impl.c.NoFall.a.f_91074_.m_146908_(), 90.0f) : this.p;
                com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.a(b2, (double)this.e.q());
            }
            if (this.j) {
                if (this.r()) {
                    this.k = true;
                }
                if (this.l > 0) {
                    --this.l;
                } else {
                    this.b(true);
                }
                return;
            }
            if (this.p()) {
                this.q();
            }
        }
    }

    @Override
    public void d() {
        this.b(true);
        this.n = 0L;
    }

    @Override
    public void e() {
        this.b(true);
    }

    @EventTarget
    public void onClick(KeybindActionEvent d2) {
        BlockPos blockPos;
        HitResult hitResult;
        if (com.heypixel.heypixelmod.obsoverlay.modules.impl.c.NoFall.a.f_91074_ == null || com.heypixel.heypixelmod.obsoverlay.modules.impl.c.NoFall.a.f_91073_ == null) {
            return;
        }
        if (this.k) {
            long l2 = System.currentTimeMillis();
            if (l2 - this.n < 50L) {
                return;
            }
            this.n = l2;
            this.k = false;
            HitResult hitResult2 = this.s();
            if (hitResult2 != null && hitResult2.m_6662_() == HitResult.Type.BLOCK) {
                Direction direction = ((BlockHitResult)hitResult2).m_82434_();
                boolean bl = com.heypixel.heypixelmod.obsoverlay.modules.impl.c.NoFall.a.f_91074_.f_19789_ >= 2.0f && com.heypixel.heypixelmod.obsoverlay.modules.impl.c.NoFall.a.f_91074_.f_19789_ < 4.0f;
                BlockPos blockPos2 = ((BlockHitResult)hitResult2).m_82425_();
                if (direction == Direction.UP || bl && direction != Direction.DOWN) {
                    this.o = blockPos2.m_7494_();
                    this.a(InteractionHand.MAIN_HAND);
                    this.h = this.f.m();
                    this.j = false;
                    return;
                }
            }
            this.j = false;
            return;
        }
        if (this.i && this.o != null && (hitResult = this.s()) != null && hitResult.m_6662_() == HitResult.Type.BLOCK && (blockPos = ((BlockHitResult)hitResult).m_82425_().m_7494_()).equals((Object)this.o)) {
            this.a(InteractionHand.MAIN_HAND);
            this.i = false;
            this.h = false;
            this.o = null;
            this.j = false;
            if (this.m != -1) {
                com.heypixel.heypixelmod.obsoverlay.modules.impl.c.NoFall.a.f_91074_.m_150109_().f_35977_ = this.m;
            }
            this.m = -1;
        }
    }

    private boolean p() {
        if (a == null || com.heypixel.heypixelmod.obsoverlay.modules.impl.c.NoFall.a.f_91074_ == null || com.heypixel.heypixelmod.obsoverlay.modules.impl.c.NoFall.a.f_91073_ == null) {
            return false;
        }
        if (com.heypixel.heypixelmod.obsoverlay.modules.impl.c.NoFall.a.f_91074_.m_21255_()) {
            return false;
        }
        String string = this.c.l();
        if ("Custom".equals(string)) {
            return com.heypixel.heypixelmod.obsoverlay.modules.impl.c.NoFall.a.f_91074_.f_19789_ > this.d.q();
        }
        return (com.heypixel.heypixelmod.obsoverlay.modules.impl.c.NoFall.a.f_91074_.f_19789_ - 3.0f) / 2.0f + 3.5f > com.heypixel.heypixelmod.obsoverlay.modules.impl.c.NoFall.a.f_91074_.m_21223_() / 3.0f;
    }

    private void q() {
        Integer n2 = com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.a(Items.f_42447_);
        if (n2 == null) {
            return;
        }
        double d2 = com.heypixel.heypixelmod.obsoverlay.modules.impl.c.NoFall.a.f_91074_.m_20184_().f_82480_;
        float f2 = com.heypixel.heypixelmod.obsoverlay.modules.impl.c.NoFall.a.f_91074_.f_19789_;
        boolean bl = f2 >= 2.0f && f2 < 4.0f;
        double d3 = bl ? 1.5 : 2.0;
        if (!(com.heypixel.heypixelmod.obsoverlay.modules.impl.c.NoFall.a(d2 * d3) || bl && com.heypixel.heypixelmod.obsoverlay.modules.impl.c.NoFall.a(d2 * 1.2))) {
            return;
        }
        if (this.m == -1) {
            this.m = com.heypixel.heypixelmod.obsoverlay.modules.impl.c.NoFall.a.f_91074_.m_150109_().f_35977_;
        }
        com.heypixel.heypixelmod.obsoverlay.modules.impl.c.NoFall.a.f_91074_.m_150109_().f_35977_ = n2;
        BlockPos blockPos = com.heypixel.heypixelmod.obsoverlay.modules.impl.c.NoFall.a.f_91074_.m_20183_().m_7495_();
        this.p = com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.d.a(blockPos, Direction.UP);
        this.j = true;
        this.l = bl ? 8 : 5;
    }

    private boolean r() {
        float f2 = com.heypixel.heypixelmod.obsoverlay.modules.impl.c.NoFall.a.f_91074_.f_19789_;
        boolean bl = f2 >= 2.0f && f2 < 4.0f;
        double d2 = com.heypixel.heypixelmod.obsoverlay.modules.impl.c.NoFall.a.f_91074_.m_20184_().f_82480_;
        return com.heypixel.heypixelmod.obsoverlay.modules.impl.c.NoFall.a(d2) || bl && com.heypixel.heypixelmod.obsoverlay.modules.impl.c.NoFall.a(d2 * 0.8);
    }

    private static boolean a(double d2) {
        Iterable iterable = com.heypixel.heypixelmod.obsoverlay.modules.impl.c.NoFall.a.f_91073_.m_186431_((Entity)com.heypixel.heypixelmod.obsoverlay.modules.impl.c.NoFall.a.f_91074_, com.heypixel.heypixelmod.obsoverlay.modules.impl.c.NoFall.a.f_91074_.m_20191_().m_82386_(0.0, d2, 0.0));
        return iterable.iterator().hasNext();
    }

    private void b(boolean bl) {
        this.j = false;
        this.k = false;
        this.l = 0;
        this.h = false;
        this.i = false;
        this.o = null;
        this.p = null;
        if (bl && this.m != -1 && com.heypixel.heypixelmod.obsoverlay.modules.impl.c.NoFall.a.f_91074_ != null) {
            com.heypixel.heypixelmod.obsoverlay.modules.impl.c.NoFall.a.f_91074_.m_150109_().f_35977_ = this.m;
        }
        this.m = -1;
    }

    private void a(InteractionHand interactionHand) {
        MultiPlayerGameModeAccessor multiPlayerGameModeAccessor = (MultiPlayerGameModeAccessor)com.heypixel.heypixelmod.obsoverlay.modules.impl.c.NoFall.a.f_91072_;
        if (multiPlayerGameModeAccessor != null) {
            multiPlayerGameModeAccessor.invokeEnsureHasSentCarriedItem();
        }
        BlockPredictionUtils.a(n2 -> new ServerboundUseItemPacket(interactionHand, n2));
    }

    private HitResult s() {
        HitResult hitResult;
        if (this.p != null && (hitResult = com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.a.a(this.p, 4.5)) != null) {
            return hitResult;
        }
        return com.heypixel.heypixelmod.obsoverlay.modules.impl.c.NoFall.a.f_91077_;
    }
}

