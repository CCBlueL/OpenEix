/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.render;

import com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.CrosshairRenderEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;
import com.heypixel.heypixelmod.obsoverlay.utils.RotationInterpolation;
import com.heypixel.heypixelmod.obsoverlay.utils.e.b.BundledFontRegistry;
import io.github.humbleui.skija.ClipMode;
import io.github.humbleui.skija.Path;
import io.github.humbleui.types.RRect;
import java.awt.Color;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.util.StringUtil;
import net.minecraft.world.effect.MobEffect;

@ModuleInfo(a="EffectHUD", b="\u6548\u679c\u663e\u793a", c="Displays potion effects on the HUD", d=com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory.RENDER)
public class EffectHUD
extends ModuleBase {
    private final Map<MobEffect, EffectDisplayState> c = new ConcurrentHashMap<MobEffect, EffectDisplayState>();
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.b d = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Position").e(10.0f).f(250.0f).a().f();

    /*
     * WARNING - void declaration
     */
    @EventTarget
    public void onRender(CrosshairRenderEvent s2) {
        for (Object object : EffectHUD.a.f_91074_.m_21220_()) {
            void object2;
            if (this.c.containsKey(object.m_19544_())) {
                EffectDisplayState a2 = this.c.get(object.m_19544_());
            } else {
                EffectDisplayState a3 = new EffectDisplayState();
                this.c.put(object.m_19544_(), a3);
            }
            object2.d = Math.max(object2.d, object.m_19557_());
            object2.e = object.m_19557_();
            object2.f = object.m_19564_();
            object2.g = false;
        }
        int n2 = (int)this.d.o();
        for (Map.Entry entry : this.c.entrySet()) {
            EffectDisplayState a4 = (EffectDisplayState)entry.getValue();
            String string = this.a((MobEffect)entry.getKey(), a4);
            if (a4.b.c == -1.0f) {
                a4.b.c = n2;
            }
            float f2 = a4.a.c;
            float f3 = a4.b.c;
            String string2 = StringUtil.m_14404_((int)a4.e);
            float f4 = f2;
            float f5 = f3;
            float f6 = 20.0f;
            float f7 = 20.0f;
            float f8 = f2 + 25.0f;
            float f9 = f3;
            float f10 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string, com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.c.a(10.0f)) + com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string2, com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.c.a(8.0f)) + 12.0f;
            float f11 = 20.0f;
            a4.h = 25.0f + f10;
            boolean bl = a4.g = !EffectHUD.a.f_91074_.m_21023_((MobEffect)entry.getKey());
            if (a4.g) {
                a4.a.a = -a4.h - 20.0f;
                if (f2 <= -a4.h - 20.0f) {
                    this.c.remove(entry.getKey());
                }
            } else {
                a4.c.a = (float)a4.e / (float)a4.d * a4.h;
                if (a4.c.c <= 0.0f) {
                    a4.c.c = a4.c.a;
                }
                a4.a.a = this.d.n();
                a4.b.a = n2;
                a4.b.a(true);
            }
            a4.c.a(true);
            a4.a.a(true);
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c(f4, f5, f6, f7, 5.0f);
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f4, f5, f6, f7, 5.0f);
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c(f8, f9, f10, f11, 5.0f);
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f8, f9, f10, f11, 5.0f);
            Path path = new Path();
            path.addRRect(RRect.makeXYWH((float)f4, (float)f5, (float)f6, (float)f7, (float)5.0f));
            path.addRRect(RRect.makeXYWH((float)f8, (float)f9, (float)f10, (float)f11, (float)5.0f));
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c();
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().clipPath(path, ClipMode.INTERSECT, true);
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f2, f3, a4.h, 30.0f, new Color(0, 0, 0, 50));
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f2, f3, a4.c.c, 30.0f, new Color(0, 0, 0, 50));
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.d();
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a((MobEffect)entry.getKey(), f4 + 2.0f, f5 + 2.0f, 16.0f, 16.0f);
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string, f8 + 5.0f, f9 + 5.0f, new Color(255, 255, 255), com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.c.a(10.0f));
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string2, f8 + 7.0f + com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string, com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.c.a(10.0f)), f9 + 8.0f, new Color(200, 200, 200), com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.c.a(8.0f));
            this.d.c(Math.max(a4.h, this.d.p()));
            this.d.d((float)(n2 += 28) - this.d.o());
        }
    }

    public String a(MobEffect mobEffect, EffectDisplayState a2) {
        String string = mobEffect.m_19482_().getString();
        Object object = a2.f == 0 ? "" : (a2.f == 1 ? " " + I18n.m_118938_((String)"enchantment.level.2", (Object[])new Object[0]) : (a2.f == 2 ? " " + I18n.m_118938_((String)"enchantment.level.3", (Object[])new Object[0]) : (a2.f == 3 ? " " + I18n.m_118938_((String)"enchantment.level.4", (Object[])new Object[0]) : " " + a2.f)));
        return string + (String)object;
    }

    public static class EffectDisplayState {
        public RotationInterpolation a = new RotationInterpolation(-60.0f, 0.2f);
        public RotationInterpolation b = new RotationInterpolation(-1.0f, 0.2f);
        public RotationInterpolation c = new RotationInterpolation(-1.0f, 0.2f);
        public int d = -1;
        public int e = 0;
        public int f = 0;
        public boolean g = false;
        public float h;
    }
}

