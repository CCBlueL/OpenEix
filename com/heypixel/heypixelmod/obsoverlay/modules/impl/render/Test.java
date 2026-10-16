/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.render;

import com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.CrosshairRenderEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;
import java.awt.Color;

@ModuleInfo(a="Test", b="\u6d4b\u8bd5", c="", d=ModuleCategory.RENDER)
public class Test
extends ModuleBase {
    public com.heypixel.heypixelmod.obsoverlay.d.SettingModel.b c = SettingBuilder.a(this, "Position").e(10.0f).f(10.0f).a().f();

    @EventTarget
    public void onRenderSkia(CrosshairRenderEvent s2) {
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c(this.c.n(), this.c.o(), 50.0f, 50.0f, 5.0f);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(this.c.n(), this.c.o(), 50.0f, 50.0f, 5.0f);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(this.c.n(), this.c.o(), 50.0f, 50.0f, 5.0f, new Color(0, 0, 0, 90));
        this.c.c(50.0f);
        this.c.d(50.0f);
    }
}

