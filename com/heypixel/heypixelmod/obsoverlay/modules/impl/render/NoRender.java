/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.render;

import com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;

@ModuleInfo(a="NoRender", b="\u65e0\u6e32\u67d3", c="Disables rendering", d=ModuleCategory.RENDER)
public class NoRender
extends ModuleBase {
    public com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a c = SettingBuilder.a(this, "Disable Effects").a(true).a().b();
}

