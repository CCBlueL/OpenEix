/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.render;

import com.heypixel.heypixelmod.obsoverlay.d.a.NumericSetting;
import com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;

@ModuleInfo(a="PostProcess", b="\u540e\u5904\u7406", c="Post process effects", d=ModuleCategory.RENDER)
public class PostProcess
extends ModuleBase {
    private static PostProcess c;
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a d = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "FastBlur").a(false).a().b();
    private final AntiNausea e = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Blur FPS").d(1.0f).a(90.0f).b(15.0f).c(120.0f).a(this.d::m).a().c();
    private final AntiNausea f = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Blur Strength").a(2.0f).b(0.0f).c(19.0f).d(1.0f).a().c();
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a g = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Glow").a(true).a().b();

    public PostProcess() {
        c = this;
    }

    @Override
    public void d() {
        this.a(false);
    }

    public int p() {
        return (int)this.f.q();
    }

    public int q() {
        return (int)this.e.q();
    }

    public boolean r() {
        return this.d.m();
    }

    public static boolean s() {
        return c != null && PostProcess.c.g.m();
    }
}

