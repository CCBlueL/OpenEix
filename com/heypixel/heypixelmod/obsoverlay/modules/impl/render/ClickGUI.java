/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.render;

import com.heypixel.heypixelmod.obsoverlay.d.a.ModeSetting;
import com.heypixel.heypixelmod.obsoverlay.d.SettingValue;
import com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;
import net.minecraft.client.gui.screens.Screen;

@ModuleInfo(a="ClickGUI", b="\u70b9\u51fb\u7528\u6237\u56fe\u5f62\u754c\u9762", d=ModuleCategory.RENDER, c="The ClickGUI")
public class ClickGUI
extends ModuleBase {
    public BedESP c = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Language").a("English", "\u4e2d\u6587").a((SettingValue c2) -> {
        com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase.b = true;
    }).a().e();
    public BedESP d = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Style").a(new String[]{"Naven"}).a((SettingValue c2) -> {
        com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase.b = true;
    }).a().e();
    com.heypixel.heypixelmod.obsoverlay.c.MainMenuScreen e = null;

    @Override
    protected void c() {
        super.c();
        this.b(344);
    }

    @Override
    public void d() {
        if (this.e == null) {
            this.e = new com.heypixel.heypixelmod.obsoverlay.c.MainMenuScreen();
        }
        a.m_91152_((Screen)this.e);
        this.f();
    }

    public String p() {
        return this.c.l();
    }
}

