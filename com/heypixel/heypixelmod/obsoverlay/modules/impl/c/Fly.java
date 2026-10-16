/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.c;

import com.heypixel.heypixelmod.obsoverlay.d.a.NumericSetting;
import com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerMotionEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;

@ModuleInfo(a="Fly", b="\u98de\u884c", c="Double-tap space to fly like creative mode", d=ModuleCategory.MOVEMENT)
public class Fly
extends ModuleBase {
    private final Eagle c = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Speed").a(1.0f).b(0.1f).c(5.0f).d(0.1f).a().c();
    private boolean d = false;
    private boolean e = false;
    private long f = 0L;

    @EventTarget
    public void onMotion(PlayerMotionEvent k2) {
        double d2;
        if (k2.b() != com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.a) {
            return;
        }
        if (com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Fly.a.f_91074_ == null) {
            return;
        }
        boolean bl = com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Fly.a.f_91066_.f_92089_.m_90857_();
        if (bl && !this.e) {
            long l2 = System.currentTimeMillis();
            if (l2 - this.f < 300L) {
                boolean bl2 = this.d = !this.d;
                if (!this.d) {
                    com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Fly.a.f_91074_.m_20334_(com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Fly.a.f_91074_.m_20184_().f_82479_, 0.0, com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Fly.a.f_91074_.m_20184_().f_82481_);
                }
            }
            this.f = l2;
        }
        this.e = bl;
        if (!this.d) {
            return;
        }
        double d3 = (double)this.c.q() * 0.1;
        double d4 = com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Fly.a.f_91074_.f_20902_;
        double d5 = com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Fly.a.f_91074_.f_20900_;
        float f2 = com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Fly.a.f_91074_.m_146908_();
        double d6 = 0.0;
        double d7 = 0.0;
        if (d4 != 0.0 || d5 != 0.0) {
            d2 = Math.toRadians(f2);
            d6 = (d5 * Math.cos(d2) - d4 * Math.sin(d2)) * d3;
            d7 = (d4 * Math.cos(d2) + d5 * Math.sin(d2)) * d3;
        }
        d2 = 0.0;
        if (com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Fly.a.f_91066_.f_92089_.m_90857_()) {
            d2 += d3;
        }
        if (com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Fly.a.f_91066_.f_92090_.m_90857_()) {
            d2 -= d3;
        }
        com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Fly.a.f_91074_.m_20334_(d6, d2, d7);
        com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Fly.a.f_91074_.f_19789_ = 0.0f;
    }
}

