/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.render;

import com.heypixel.heypixelmod.obsoverlay.d.a.NumericSetting;
import com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.FovUpdateEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;

@ModuleInfo(a="Fov", b="\u89c6\u573a\u89d2", c="Change fov.", d=ModuleCategory.RENDER)
public class Fov
extends ModuleBase {
    public AntiNausea c = SettingBuilder.a(this, "Fov").a(120.0f).d(1.0f).b(1.0f).c(150.0f).a().c();

    @EventTarget
    public void onFovUpdate(FovUpdateEvent ae2) {
        ae2.a(this.c.q() / 100.0f);
    }
}

