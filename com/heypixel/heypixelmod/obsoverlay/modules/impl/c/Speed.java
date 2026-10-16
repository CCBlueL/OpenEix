/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.c;

import com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.RelativeMovementVectorEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.LocalPlayerTickPreEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerMotionEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;
import com.heypixel.heypixelmod.obsoverlay.utils.c.PlayerRotationController;
import com.heypixel.heypixelmod.obsoverlay.utils.PlayerRotationUtils;
import java.util.stream.StreamSupport;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.WebBlock;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

@ModuleInfo(a="Speed", b="\u563b\u563b\u54c8\u54c8", c="Movement speed adjustments with Grim modes", d=ModuleCategory.MOVEMENT)
public class Speed
extends ModuleBase {
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.c c = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Bounding Box Size").a(0.4f).b(0.0f).c(1.0f).d(0.1f).a().c();
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.c d = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "In Player Speed").a(0.08f).b(0.0f).c(0.08f).d(0.01f).a().c();
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.c e = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Move Flying Increase").a(0.1f).b(-1.0f).c(1.0f).d(0.01f).a().c();
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a f = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Fast Fall").a(true).a().b();
    private int g;
    private int h;

    @Override
    public void d() {
        this.g = 0;
        this.h = 0;
    }

    @Override
    public void e() {
        if (Speed.a.f_91066_ != null) {
            Speed.a.f_91066_.f_92090_.m_7249_(false);
            Speed.a.f_91066_.f_92089_.m_7249_(false);
        }
        this.g = 0;
        this.h = 0;
    }

    @EventTarget
    public void onUpdate(LocalPlayerTickPreEvent ad2) {
        if (Speed.a.f_91074_ == null || Speed.a.f_91073_ == null) {
            return;
        }
        int n2 = this.g = !Speed.a.f_91074_.m_20096_() ? this.g + 1 : 0;
        if (!(this.r() || Speed.a.f_91074_.m_20096_() || Speed.a.f_91066_.f_92085_.m_90857_() && (Speed.a.f_91066_.f_92088_.m_90857_() || Speed.a.f_91066_.f_92086_.m_90857_()) || this.f.m())) {
            float f2 = (float)((double)(Speed.a.f_91074_.m_146908_() + 45.0f) + Math.random());
            com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.a(new com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.b(f2, Speed.a.f_91074_.m_146909_()), 10.0);
        }
    }

    @EventTarget
    public void onMotion(PlayerMotionEvent k2) {
        if (Speed.a.f_91074_ == null) {
            return;
        }
        if (!this.f.m()) {
            return;
        }
        if (k2.b() == com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.a) {
            this.h = this.h == 0 ? (k2.h() ? 1 : 0) : 0;
            k2.b(this.h != 0);
            if (Speed.a.f_91074_.m_20096_()) {
                Speed.a.f_91074_.m_6135_();
            }
        } else if (k2.b() == com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.b && !Speed.a.f_91074_.m_20096_() && this.h >= 0) {
            if (this.g < 1 || this.g > 5) {
                return;
            }
            this.p();
            Speed.a.f_91066_.f_92090_.m_7249_(false);
            if (this.g == 2) {
                Speed.a.f_91074_.m_6135_();
            }
        }
    }

    @EventTarget
    public void onStrafe(RelativeMovementVectorEvent ab2) {
        if (Speed.a.f_91074_ == null || Speed.a.f_91073_ == null) {
            return;
        }
        AABB aABB = Speed.a.f_91074_.m_20191_().m_82400_((double)this.c.q());
        StreamSupport.stream(Speed.a.f_91073_.m_104735_().spliterator(), false).filter(entity -> entity instanceof LivingEntity).map(entity -> (LivingEntity)entity).filter(livingEntity -> livingEntity != Speed.a.f_91074_ && aABB.m_82381_(livingEntity.m_20191_())).forEach(livingEntity -> this.a(this.d.q()));
        this.a((double)this.e.q() / 1000.0);
    }

    private void p() {
        if (a.m_91403_() == null || Speed.a.f_91074_ == null) {
            return;
        }
        a.m_91403_().m_104955_((Packet)new ServerboundPlayerCommandPacket((Entity)Speed.a.f_91074_, ServerboundPlayerCommandPacket.Action.START_FALL_FLYING));
    }

    private void a(double d2) {
        if (!PlayerRotationUtils.a()) {
            return;
        }
        double d3 = this.q();
        Vec3 vec3 = Speed.a.f_91074_.m_20184_();
        Speed.a.f_91074_.m_20334_(vec3.f_82479_ + -Math.sin(d3) * d2, vec3.f_82480_, vec3.f_82481_ + Math.cos(d3) * d2);
    }

    private double q() {
        float f2 = Speed.a.f_91074_.f_108618_.f_108567_;
        float f3 = Speed.a.f_91074_.f_108618_.f_108566_;
        float f4 = Speed.a.f_91074_.m_146908_();
        if (f2 < 0.0f) {
            f4 += 180.0f;
        }
        float f5 = 1.0f;
        if (f2 < 0.0f) {
            f5 = -0.5f;
        } else if (f2 > 0.0f) {
            f5 = 0.5f;
        }
        if (f3 > 0.0f) {
            f4 -= 90.0f * f5;
        }
        if (f3 < 0.0f) {
            f4 += 90.0f * f5;
        }
        return Math.toRadians(f4);
    }

    private boolean r() {
        return Speed.a.f_91073_.m_8055_(Speed.a.f_91074_.m_20183_()).m_60734_() instanceof WebBlock;
    }
}

