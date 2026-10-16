/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.c;

import com.heypixel.heypixelmod.obsoverlay.d.a.NumericSetting;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.StuckInBlockEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerMotionEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.MovementInputEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

@ModuleInfo(a="FastWeb", b="\u5feb\u901f\u8718\u86db\u7f51", d=ModuleCategory.MOVEMENT, c="Allows you to walk faster on cobwebs")
public class FastWeb
extends ModuleBase {
    public final Eagle c = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Ground Multiplier").a(0.66f).b(0.0f).c(1.0f).d(0.001f).a().c();
    private int d = -1;
    private int e = -100;
    private int f = -1;
    private float g = 0.0f;
    private float h = 0.0f;
    private boolean i = false;
    private boolean j = false;

    @EventTarget
    public void onMoveInput(MovementInputEvent l2) {
        if (com.heypixel.heypixelmod.obsoverlay.modules.impl.c.FastWeb.a.f_91074_ == null) {
            return;
        }
        int n2 = com.heypixel.heypixelmod.obsoverlay.modules.impl.c.FastWeb.a.f_91074_.f_19797_;
        if (n2 != this.d) {
            return;
        }
        this.f = n2;
        this.g = l2.a();
        this.h = l2.b();
        this.i = l2.c();
        this.j = l2.d();
        if (!com.heypixel.heypixelmod.obsoverlay.modules.impl.c.FastWeb.a.f_91074_.m_20096_()) {
            return;
        }
        if (l2.d()) {
            this.e = com.heypixel.heypixelmod.obsoverlay.modules.impl.c.FastWeb.a.f_91074_.f_19797_;
        }
        if (!l2.d() && com.heypixel.heypixelmod.obsoverlay.modules.impl.c.FastWeb.a.f_91074_.f_19797_ - this.e > 1) {
            return;
        }
        l2.a(false);
    }

    @EventTarget
    public void onMotion(PlayerMotionEvent k2) {
        double d2;
        double d3;
        double d4;
        if (com.heypixel.heypixelmod.obsoverlay.modules.impl.c.FastWeb.a.f_91074_ == null) {
            return;
        }
        if (k2.b() != com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.a) {
            return;
        }
        int n2 = com.heypixel.heypixelmod.obsoverlay.modules.impl.c.FastWeb.a.f_91074_.f_19797_;
        if (n2 != this.d) {
            return;
        }
        Vec3 vec3 = com.heypixel.heypixelmod.obsoverlay.modules.impl.c.FastWeb.a.f_91074_.m_20184_();
        boolean bl = com.heypixel.heypixelmod.obsoverlay.modules.impl.c.FastWeb.a.f_91074_.m_20096_();
        float f2 = 0.0f;
        float f3 = 0.0f;
        boolean bl2 = false;
        boolean bl3 = false;
        if (com.heypixel.heypixelmod.obsoverlay.modules.impl.c.FastWeb.a.f_91074_.f_108618_ != null) {
            f2 = com.heypixel.heypixelmod.obsoverlay.modules.impl.c.FastWeb.a.f_91074_.f_108618_.f_108567_;
            f3 = com.heypixel.heypixelmod.obsoverlay.modules.impl.c.FastWeb.a.f_91074_.f_108618_.f_108566_;
            bl2 = com.heypixel.heypixelmod.obsoverlay.modules.impl.c.FastWeb.a.f_91074_.f_108618_.f_108572_;
            bl3 = com.heypixel.heypixelmod.obsoverlay.modules.impl.c.FastWeb.a.f_91074_.f_108618_.f_108573_;
        } else if (this.f == n2) {
            f2 = this.g;
            f3 = this.h;
            bl2 = this.i;
            bl3 = this.j;
        }
        if (f2 != 0.0f && f3 != 0.0f) {
            f2 *= 0.707f;
            f3 *= 0.707f;
        }
        if (bl) {
            d4 = vec3.f_82479_;
            d3 = vec3.f_82481_;
        } else {
            d4 = 0.0;
            d3 = 0.0;
            if (f2 != 0.0f || f3 != 0.0f) {
                d2 = 0.14122;
                float f4 = com.heypixel.heypixelmod.obsoverlay.modules.impl.c.FastWeb.a.f_91074_.m_146908_();
                double d5 = Math.toRadians(f4);
                d4 = (-Math.sin(d5) * (double)f2 + Math.cos(d5) * (double)f3) * d2;
                d3 = (Math.cos(d5) * (double)f2 + Math.sin(d5) * (double)f3) * d2;
            }
        }
        d2 = bl ? vec3.f_82480_ : (bl2 && bl3 ? 0.0 : (bl2 ? 0.06222 : (bl3 ? (double)-0.18777f : 0.0)));
        com.heypixel.heypixelmod.obsoverlay.modules.impl.c.FastWeb.a.f_91074_.m_20334_(d4, d2, d3);
        com.heypixel.heypixelmod.obsoverlay.modules.impl.c.FastWeb.a.f_91074_.f_19789_ = 0.0f;
    }

    @EventTarget
    public void onStuck(StuckInBlockEvent ac2) {
        if (com.heypixel.heypixelmod.obsoverlay.modules.impl.c.FastWeb.a.f_91074_ == null) {
            return;
        }
        if (ac2.b().m_60734_() == Blocks.f_50033_) {
            this.d = com.heypixel.heypixelmod.obsoverlay.modules.impl.c.FastWeb.a.f_91074_.f_19797_;
            Vec3 vec3 = ac2.c();
            if (com.heypixel.heypixelmod.obsoverlay.modules.impl.c.FastWeb.a.f_91074_.m_20096_()) {
                double d2 = this.c.q();
                double d3 = vec3.f_82479_ + (1.0 - vec3.f_82479_) * d2;
                double d4 = vec3.f_82481_ + (1.0 - vec3.f_82481_) * d2;
                ac2.a(new Vec3(d3, vec3.f_82480_, d4));
            } else {
                ac2.a(new Vec3(1.0, 1.0, 1.0));
            }
        }
    }
}

