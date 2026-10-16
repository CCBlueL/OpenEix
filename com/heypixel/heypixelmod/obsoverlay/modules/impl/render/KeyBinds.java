/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.render;

import com.heypixel.heypixelmod.obsoverlay.EixClientCore;
import com.heypixel.heypixelmod.obsoverlay.d.a.NumericSetting;
import com.heypixel.heypixelmod.obsoverlay.d.a.ModeSetting;
import com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.CrosshairRenderEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;
import com.heypixel.heypixelmod.obsoverlay.utils.RotationInterpolation;
import com.mojang.blaze3d.platform.InputConstants;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Font;
import io.github.humbleui.types.RRect;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@ModuleInfo(a="KeyBinds", b="\u6309\u952e\u663e\u793a", c="\u663e\u793a\u6240\u6709\u6309\u952e\u7ed1\u5b9a", d=com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory.RENDER)
public class KeyBinds
extends ModuleBase {
    private static final Color e = new Color(0, 0, 0, 100);
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.b f = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Position").e(10.0f).f(80.0f).a().f();
    public AntiNausea c = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "KeyBinds Size").a(10.0f).d(1.0f).b(6.0f).c(24.0f).a().c();
    public BedESP d = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Style").a("Style A", "Style B").a(0).a().e();
    private final Map<ModuleBase, RotationInterpolation> g = new HashMap<ModuleBase, RotationInterpolation>();
    private final Map<ModuleBase, RotationInterpolation> h = new HashMap<ModuleBase, RotationInterpolation>();
    private final Map<ModuleBase, RotationInterpolation> i = new HashMap<ModuleBase, RotationInterpolation>();
    private final Map<ModuleBase, RotationInterpolation> j = new HashMap<ModuleBase, RotationInterpolation>();
    private final RotationInterpolation k = new RotationInterpolation(100.0f, 100.0f, 0.2f);
    private final RotationInterpolation l = new RotationInterpolation(20.0f, 20.0f, 0.2f);

    @EventTarget
    public void onSkia(CrosshairRenderEvent s2) {
        if (this.d.a("Style A")) {
            this.q();
        } else if (this.d.a("Style B")) {
            this.p();
        }
    }

    private void p() {
        float f2;
        float f3;
        float f4 = this.c.q() * 1.0f;
        float f5 = this.c.q() * 0.9f;
        Font font = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.c.a(f4);
        Font font2 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.c.b(f4);
        Font font3 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.c.a(f5);
        ArrayList<ModuleBase> arrayList = new ArrayList<ModuleBase>(com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a());
        boolean bl = KeyBinds.a.f_91080_ instanceof com.heypixel.heypixelmod.obsoverlay.c.HudEditorScreen;
        float f6 = f4 * 1.5f;
        float f7 = f5 * 1.5f;
        float f8 = this.c.q() / 3.0f;
        float f9 = f8 * 0.6f;
        float f10 = f8 * 2.0f;
        Color color = new Color(0, 0, 0, 70);
        boolean bl2 = this.f.o() < (float)a.m_91268_().m_85446_() / 2.0f;
        arrayList.sort((b2, b3) -> {
            float f3;
            float f4;
            float f5 = 0.0f;
            if (b2 != this && b2.o() != 0) {
                f4 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(b2.h(), font3);
                f3 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(this.c(b2.o()), font3) + f8 * 2.0f;
                f5 = f4 + f8 * 2.0f + f8 + f3;
            }
            f4 = 0.0f;
            if (b3 != this && b3.o() != 0) {
                f3 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(b3.h(), font3);
                float f6 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(this.c(b3.o()), font3) + f8 * 2.0f;
                f4 = f3 + f8 * 2.0f + f8 + f6;
            }
            return bl2 ? Float.compare(f4, f5) : Float.compare(f5, f4);
        });
        ArrayList<ModuleBase> arrayList2 = new ArrayList<ModuleBase>();
        for (ModuleBase b4 : arrayList) {
            if (b4 == this || b4.o() == 0 || !bl && b4.m()) continue;
            arrayList2.add(b4);
        }
        String string = "Key Bind";
        float f11 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string, font);
        float f12 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a("\ue312", font2);
        float f13 = f11 + f8 * 4.0f + f12;
        for (ModuleBase b5 : arrayList2) {
            String string2 = b5.h();
            String string3 = this.c(b5.o());
            f3 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string2, font3);
            float f14 = f3 + f8 * 4.0f + (f2 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string3, font3));
            if (!(f14 > f13)) continue;
            f13 = f14;
        }
        float f15 = f13 + f8 * 2.0f;
        float f16 = f6 + f9 + (float)arrayList2.size() * f7 + f8 * 2.0f;
        this.k.a = f15;
        this.l.a = f16;
        this.k.a(true);
        this.l.a(true);
        float f17 = this.k.c;
        float f18 = this.l.c;
        f3 = this.f.n();
        f2 = this.f.o();
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c(f3, f2, f17, f18, f10);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f3, f2, f17, f18, f10);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f3, f2, f17, f18, f10, color);
        Canvas canvas = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e();
        int n2 = canvas.save();
        RRect rRect = RRect.makeXYWH((float)f3, (float)f2, (float)f17, (float)f18, (float)f10);
        canvas.clipRRect(rRect, true);
        float f19 = f2 + f8 + f6 / 2.0f;
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c(string, f3 + f8, f19, Color.WHITE, font);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c("\ue312", f3 + f17 - f8 - f12, f19, Color.WHITE, font2);
        HashMap<ModuleBase, Float> hashMap = new HashMap<ModuleBase, Float>();
        float f20 = f8 + f6 + f9;
        for (ModuleBase b6 : arrayList2) {
            hashMap.put(b6, Float.valueOf(f20));
            f20 += f7;
        }
        for (ModuleBase b6 : arrayList) {
            if (b6 == this || b6.o() == 0) continue;
            boolean bl3 = arrayList2.contains(b6);
            RotationInterpolation ag2 = this.i.computeIfAbsent(b6, b2 -> new RotationInterpolation(0.0f, 0.0f, 0.2f));
            RotationInterpolation ag3 = this.j.computeIfAbsent(b6, b2 -> new RotationInterpolation(0.0f, 0.0f, 0.2f));
            if (bl3) {
                ag2.a = ((Float)hashMap.get(b6)).floatValue();
                ag3.a = 255.0f;
            } else {
                ag2.a = ag2.c;
                ag3.a = 0.0f;
            }
            ag2.a(true);
            ag3.a(true);
            float f21 = ag3.c;
            if (f21 < 1.0f) continue;
            float f22 = ag2.c;
            float f23 = f2 + f22 + f7 / 2.0f;
            Color color2 = new Color(255, 255, 255, (int)Math.min(255.0f, Math.max(0.0f, f21)));
            String string4 = b6.h();
            String string5 = this.c(b6.o());
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c(string4, f3 + f8, f23, color2, font3);
            float f24 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string5, font3);
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c(string5, f3 + f17 - f8 - f24, f23, color2, font3);
        }
        canvas.restoreToCount(n2);
        this.f.c(f17);
        this.f.d(f18);
    }

    private void q() {
        float f2;
        float f3;
        float f4 = this.c.q() * 1.0f;
        float f5 = this.c.q() * 0.9f;
        Font font = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.c.a(f4);
        Font font2 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.c.b(f4);
        Font font3 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.c.a(f5);
        ArrayList<ModuleBase> arrayList = new ArrayList<ModuleBase>(com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a());
        boolean bl = KeyBinds.a.f_91080_ instanceof com.heypixel.heypixelmod.obsoverlay.c.HudEditorScreen;
        float f6 = f4 * 1.5f;
        float f7 = f5 * 1.5f;
        float f8 = this.c.q() / 3.0f;
        float f9 = f8 * 0.6f;
        boolean bl2 = this.f.o() < (float)a.m_91268_().m_85446_() / 2.0f;
        arrayList.sort((b2, b3) -> {
            float f3;
            float f4;
            float f5 = 0.0f;
            if (b2 != this && b2.o() != 0) {
                f4 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(b2.h(), font3);
                f3 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(this.c(b2.o()), font3) + f8 * 2.0f;
                f5 = f4 + f8 * 2.0f + f8 + f3;
            }
            f4 = 0.0f;
            if (b3 != this && b3.o() != 0) {
                f3 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(b3.h(), font3);
                float f6 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(this.c(b3.o()), font3) + f8 * 2.0f;
                f4 = f3 + f8 * 2.0f + f8 + f6;
            }
            return bl2 ? Float.compare(f4, f5) : Float.compare(f5, f4);
        });
        String string = "Key Binds";
        float f10 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string, font);
        float f11 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a("\ue312", font2);
        float f12 = f3 = f11 + f8 + f10 + f8 * 2.0f;
        for (ModuleBase b4 : arrayList) {
            float f13;
            int n2;
            if (b4 == this || (n2 = b4.o()) == 0) continue;
            String string2 = b4.h();
            String string3 = this.c(n2);
            f2 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string2, font3);
            float f14 = f2 + f8 * 2.0f;
            float f15 = f14 + f8 + (f13 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string3, font3) + f8 * 2.0f);
            if (!(f15 > f12)) continue;
            f12 = f15;
        }
        boolean bl3 = this.f.n() >= (float)a.m_91268_().m_85445_() / 2.0f;
        float f16 = this.f.o();
        float f17 = bl3 ? this.f.n() + f12 - f3 : this.f.n();
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c(f17, f16, f3, f6, f8);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f17, f16, f3, f6, f8);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f17, f16, f3, f6, f8, e);
        float f18 = f17 + f8;
        float f19 = f18 + f11 + f8;
        f2 = f16 + f6 / 2.0f;
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c("\ue312", f18, f2, Color.WHITE, font2);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c(string, f19, f2, Color.WHITE, font);
        f16 += f6 + f8 + f9;
        for (ModuleBase b5 : arrayList) {
            float f20;
            RotationInterpolation ag2;
            RotationInterpolation ag3;
            int n3;
            if (b5 == this || (n3 = b5.o()) == 0) continue;
            String string4 = b5.h();
            String string5 = this.c(n3);
            float f21 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string4, font3);
            float f22 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string5, font3) + f8 * 2.0f;
            float f23 = f21 + f8 * 2.0f;
            float f24 = f23 + f8 + f22;
            float f25 = bl3 ? this.f.n() + f12 - f24 : this.f.n();
            float f26 = bl3 ? this.f.n() + f12 + f24 : this.f.n() - f24 - f8;
            RotationInterpolation ag4 = this.g.get(b5);
            if (ag4 == null) {
                ag4 = new RotationInterpolation(f26, f26, 0.2f);
                this.g.put(b5, ag4);
            }
            if ((ag3 = this.h.get(b5)) == null) {
                ag3 = new RotationInterpolation(f16, f16, 0.2f);
                this.h.put(b5, ag3);
            }
            if ((ag2 = this.j.get(b5)) == null) {
                ag2 = new RotationInterpolation(0.0f, 0.0f, 0.25f);
                this.j.put(b5, ag2);
            }
            boolean bl4 = bl || !b5.m();
            ag4.a = bl4 ? f25 : f26;
            ag4.a(true);
            ag2.a = bl4 ? 255.0f : 0.0f;
            ag2.a(true);
            if (bl4) {
                ag3.a = f16;
                ag3.a(true);
                f16 += f7 + f8;
            } else {
                ag3.a = ag3.c;
                ag3.a(true);
            }
            if (!bl4 && ag2.b(true) && ag4.b(true) || (f20 = Math.min(1.0f, ag2.c / 255.0f)) <= 0.01f) continue;
            float f27 = ag4.c;
            float f28 = ag3.c;
            float f29 = f27 + f23 + f8;
            if (f20 > 0.1f) {
                com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c(f27, f28, f23, f7, f8);
                com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f27, f28, f23, f7, f8);
                com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c(f29, f28, f22, f7, f8);
                com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f29, f28, f22, f7, f8);
            }
            Color color = new Color(0, 0, 0, Math.min(255, Math.max(0, (int)((float)e.getAlpha() * f20))));
            Color color2 = new Color(255, 255, 255, Math.min(255, Math.max(0, (int)(255.0f * f20))));
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f27, f28, f23, f7, f8, color);
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f29, f28, f22, f7, f8, color);
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string4, f27 + f8, f28 + f8, color2, font3);
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string5, f29 + f8, f28 + f8, color2, font3);
        }
        this.f.c(f12);
        this.f.d(f16);
    }

    private String c(int n2) {
        if (n2 == 0) {
            return "NONE";
        }
        if (n2 < 0) {
            return "M" + -n2;
        }
        InputConstants.Key key = InputConstants.m_84827_((int)n2, (int)0);
        if (key == InputConstants.f_84822_) {
            return "NONE";
        }
        String string = key.m_84875_().getString();
        if (string == null || string.isEmpty()) {
            return "NONE";
        }
        return string.toUpperCase();
    }
}

