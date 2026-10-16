/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.render;

import com.heypixel.heypixelmod.obsoverlay.d.a.NumericSetting;
import com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;

@ModuleInfo(a="FullBright", b="\u5149\u660e", c="Make your world brighter.", d=ModuleCategory.RENDER)
public class FullBright
extends ModuleBase {
    public AntiNausea c = SettingBuilder.a(this, "Brightness").a(1.0f).d(0.1f).b(0.0f).c(1.0f).a().c();
}

