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

@ModuleInfo(a="Notification", b="\u901a\u77e5", c="", d=ModuleCategory.RENDER)
public class Notification
extends ModuleBase {
    public com.heypixel.heypixelmod.obsoverlay.d.SettingModel.b c = SettingBuilder.a(this, "Position").e(10.0f).f(50.0f).a().f();

    @EventTarget
    public void onRenderSkia(CrosshairRenderEvent s2) {
        com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().j().a(s2, this.c);
    }
}

