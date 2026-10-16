/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.render;

import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;
import net.minecraft.client.gui.screens.Screen;

@ModuleInfo(a="HUDEditor", b="HUD\u7f16\u8f91\u5668", d=ModuleCategory.RENDER, c="HUD editor screen.")
public class HUDEditor
extends ModuleBase {
    com.heypixel.heypixelmod.obsoverlay.c.HudEditorScreen c = null;

    @Override
    protected void c() {
        super.c();
        this.b(345);
    }

    @Override
    public void d() {
        if (this.c == null) {
            this.c = new com.heypixel.heypixelmod.obsoverlay.c.HudEditorScreen();
        }
        a.m_91152_((Screen)this.c);
        this.f();
    }
}

