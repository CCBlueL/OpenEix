/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.render;

import com.heypixel.heypixelmod.obsoverlay.d.a.ModeSetting;
import com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerMotionEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;

@ModuleInfo(a="Weather", b="\u5929\u6c14", c="Customize world weather", d=ModuleCategory.RENDER)
public class Weather
extends ModuleBase {
    private final BedESP c = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Mode").a("Clear", "Rain", "Snow").a(0).a().e();
    private float d;
    private float e;
    private boolean f;

    @Override
    public void d() {
        if (Weather.a.f_91073_ == null) {
            this.f = false;
            return;
        }
        this.d = Weather.a.f_91073_.m_46722_(1.0f);
        this.e = Weather.a.f_91073_.m_46661_(1.0f);
        this.f = true;
    }

    @Override
    public void e() {
        if (Weather.a.f_91073_ != null && this.f) {
            Weather.a.f_91073_.m_46734_(this.d);
            Weather.a.f_91073_.m_46707_(this.e);
        }
    }

    @EventTarget
    public void onMotion(PlayerMotionEvent k2) {
        if (k2.b() != com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.a || Weather.a.f_91073_ == null) {
            return;
        }
        this.a(this.c.l());
        switch (this.c.l()) {
            case "Clear": {
                this.p();
                break;
            }
            case "Rain": {
                this.q();
                break;
            }
            case "Snow": {
                this.r();
            }
        }
    }

    private void p() {
        Weather.a.f_91073_.m_46734_(0.0f);
        Weather.a.f_91073_.m_46707_(0.0f);
    }

    private void q() {
        Weather.a.f_91073_.m_46734_(1.0f);
        Weather.a.f_91073_.m_46707_(0.0f);
    }

    private void r() {
        Weather.a.f_91073_.m_46734_(1.0f);
        Weather.a.f_91073_.m_46707_(0.0f);
        if (Weather.a.f_91074_ == null) {
            return;
        }
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        double d2 = Weather.a.f_91074_.m_20185_();
        double d3 = Weather.a.f_91074_.m_20186_() + 2.0;
        double d4 = Weather.a.f_91074_.m_20189_();
        for (int i2 = 0; i2 < 20; ++i2) {
            double d5 = d2 + threadLocalRandom.nextDouble(-8.0, 8.0);
            double d6 = d3 + threadLocalRandom.nextDouble(0.0, 6.0);
            double d7 = d4 + threadLocalRandom.nextDouble(-8.0, 8.0);
            Weather.a.f_91073_.m_7106_((ParticleOptions)ParticleTypes.f_175821_, d5, d6, d7, 0.0, -0.01, 0.0);
        }
    }
}

