/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.c;

import com.heypixel.heypixelmod.obsoverlay.EixClientCore;
import com.heypixel.heypixelmod.obsoverlay.d.SettingValue;
import com.heypixel.heypixelmod.obsoverlay.d.SettingValueType;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.PostProcess;
import com.heypixel.heypixelmod.obsoverlay.utils.EntityOverlayRenderer;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class HudEditorScreen
extends Screen {
    private final List<com.heypixel.heypixelmod.obsoverlay.d.SettingModel.b> a = new ArrayList<com.heypixel.heypixelmod.obsoverlay.d.SettingModel.b>();
    private com.heypixel.heypixelmod.obsoverlay.d.SettingModel.b b = null;
    private float c = 0.0f;
    private float d = 0.0f;

    public HudEditorScreen() {
        super((Component)Component.m_237113_((String)"HUD Editor"));
    }

    protected void a() {
        this.a.clear();
        com.heypixel.heypixelmod.obsoverlay.modules.ModuleManager c2 = com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g();
        for (com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase b2 : c2.a()) {
            for (SettingValue c3 : com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().d().a()) {
                if (c3.i() != b2 || c3.a() != SettingValueType.e) continue;
                this.a.add(c3.f());
            }
        }
    }

    public void a(GuiGraphics guiGraphics, int n2, int n3, float f2) {
        com.heypixel.heypixelmod.obsoverlay.utils.RemotePlayer.a.a.a.a(com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a(PostProcess.class).p());
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a.a(canvas -> {
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c();
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a((float)Minecraft.m_91087_().m_91268_().m_85449_());
            for (com.heypixel.heypixelmod.obsoverlay.d.SettingModel.b b2 : this.a) {
                boolean bl;
                float f2;
                float f3 = b2.n();
                float f4 = b2.o();
                float f5 = b2.p();
                float f6 = b2.q();
                float f7 = f5 <= 0.0f ? 80.0f : f5;
                float f8 = f2 = f6 <= 0.0f ? 20.0f : f6;
                if (this.b == b2) {
                    b2.a((float)n2 - this.c);
                    b2.b((float)n3 - this.d);
                    f3 = b2.n();
                    f4 = b2.o();
                }
                if (!(bl = EntityOverlayRenderer.b(n2, n3, f3, f4, f7, f2))) continue;
                com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f3 - 5.0f, f4 - 5.0f, f7 + 10.0f, f2 + 10.0f, 5.0f, 2.0f, new Color(255, 255, 255));
            }
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.d();
        });
        super.m_88315_(guiGraphics, n2, n3, f2);
    }

    public boolean a(double d2, double d3, int n2) {
        if (n2 == 0) {
            for (int i2 = this.a.size() - 1; i2 >= 0; --i2) {
                float f2;
                com.heypixel.heypixelmod.obsoverlay.d.SettingModel.b b2 = this.a.get(i2);
                float f3 = b2.p();
                float f4 = b2.q();
                float f5 = f3 <= 0.0f ? 80.0f : f3;
                float f6 = f2 = f4 <= 0.0f ? 20.0f : f4;
                if (!EntityOverlayRenderer.b((int)d2, (int)d3, b2.n(), b2.o(), f5, f2)) continue;
                this.b = b2;
                this.c = (float)d2 - b2.n();
                this.d = (float)d3 - b2.o();
                return true;
            }
        }
        return super.m_6375_(d2, d3, n2);
    }

    public boolean b(double d2, double d3, int n2) {
        if (n2 == 0 && this.b != null) {
            this.b = null;
            return true;
        }
        return super.m_6348_(d2, d3, n2);
    }

    public boolean a(double d2, double d3, int n2, double d4, double d5) {
        if (n2 == 0 && this.b != null) {
            this.b.a((float)d2 - this.c);
            this.b.b((float)d3 - this.d);
            return true;
        }
        return super.m_7979_(d2, d3, n2, d4, d5);
    }

    public void b() {
        com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().i().c();
        super.m_7379_();
    }

    public boolean c() {
        return false;
    }
}

