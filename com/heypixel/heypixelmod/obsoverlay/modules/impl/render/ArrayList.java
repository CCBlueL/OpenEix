/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.render;

import com.heypixel.heypixelmod.obsoverlay.EixClientCore;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.CrosshairRenderEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleManager;
import io.github.humbleui.skija.Font;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ModuleInfo(a="ArrayList", b="\u6a21\u5757\u5217\u8868", c="\u663e\u793a\u4f60\u6253\u5f00\u7684\u6a21\u5757", d=com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory.RENDER)
public class ArrayList
extends ModuleBase {
    private static final float h = 5.0f;
    private static final float i = 5.0f;
    public com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a c = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Pretty Module Name").a((com.heypixel.heypixelmod.obsoverlay.d.SettingValue c2) -> {
        com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase.b = true;
    }).a(false).a().b();
    public com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a d = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Hide Render Modules").a((com.heypixel.heypixelmod.obsoverlay.d.SettingValue c2) -> {
        com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase.b = true;
    }).a(true).a().b();
    public com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a e = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Sync Color").a(true).a().b();
    public com.heypixel.heypixelmod.obsoverlay.d.SettingModel.c f = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Speed").b(1.0f).c(15.0f).a(4.0f).d(0.5f).a(() -> this.e.m()).a().c();
    public com.heypixel.heypixelmod.obsoverlay.d.SettingModel.c g = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "ArrayList Size").a(10.0f).d(1.0f).b(1.0f).c(32.0f).a().c();
    private List<ModuleBase> j;
    private final Map<ModuleBase, String> k = new HashMap<ModuleBase, String>();
    private final Map<ModuleBase, Float> l = new HashMap<ModuleBase, Float>();
    private float m = -1.0f;
    private long n = 0L;
    private static final long o = 200L;

    @EventTarget
    public void onSkia(CrosshairRenderEvent s2) {
        Font font = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.c.a(this.g.q());
        float f2 = this.g.q();
        float f3 = f2 * 1.35f;
        float f4 = f2 * 0.18f;
        float f5 = f2 * 0.32f;
        long l2 = System.currentTimeMillis();
        if ((com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase.b || this.m != f2) && l2 - this.n > 200L) {
            this.a(font);
            this.m = f2;
            com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase.b = false;
            this.n = l2;
        }
        float f6 = 5.0f;
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a("EixClient GV1.0", 5.0f, f6, new Color(255, 255, 255), font);
        f6 += f3 + f5;
        for (ModuleBase b2 : this.j) {
            if (!b2.m()) continue;
            String string = this.k.getOrDefault(b2, b2.h());
            Color color = this.e.m() ? com.heypixel.heypixelmod.obsoverlay.utils.renderer.RenderTargetManager.a((int)this.f.q(), (int)f6, new Color(120, 255, 160), new Color(255, 255, 140), false) : new Color(140, 255, 180);
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string, 5.0f, f6, color, font);
            f6 += f3 + f4;
        }
    }

    private void a(Font font) {
        ModuleManager c2 = com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g();
        this.j = new ArrayList<ModuleBase>();
        this.k.clear();
        this.l.clear();
        for (ModuleBase b4 : c2.a()) {
            if (!b4.m() || this.d.m() && b4.l() == com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory.c) continue;
            String string = this.a(b4);
            float f2 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string, font);
            this.j.add(b4);
            this.k.put(b4, string);
            this.l.put(b4, Float.valueOf(f2));
        }
        this.j.sort((b2, b3) -> {
            float f2 = this.l.getOrDefault(b2, Float.valueOf(0.0f)).floatValue();
            float f3 = this.l.getOrDefault(b3, Float.valueOf(0.0f)).floatValue();
            return Float.compare(f3, f2);
        });
    }

    public String a(ModuleBase b2) {
        String string = this.c.m() ? b2.i() : b2.h();
        return string + (String)(b2.k() == null ? "" : " \u00a77" + b2.k());
    }
}

