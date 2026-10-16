/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.c;

import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.PostProcess;
import com.heypixel.heypixelmod.obsoverlay.utils.EntityOverlayRenderer;
import io.github.humbleui.skija.Font;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.OptionsScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.network.chat.Component;

public class GameMenuScreen
extends Screen {
    private static final Minecraft a = Minecraft.m_91087_();
    private final List<MenuButton> b = new ArrayList<MenuButton>();
    private final List<BackgroundParticle> c = new ArrayList<BackgroundParticle>();
    private final Random d = new Random();

    public GameMenuScreen() {
        super(Component.m_130674_((String)"EixClient"));
    }

    protected void a() {
        if (this.b.isEmpty()) {
            this.b.add(new MenuButton("\u5355\u4eba\u6e38\u620f", () -> a.m_91152_((Screen)new SelectWorldScreen((Screen)this))));
            this.b.add(new MenuButton("\u591a\u4eba\u6e38\u620f", () -> a.m_91152_((Screen)new JoinMultiplayerScreen((Screen)this))));
            this.b.add(new MenuButton("\u6e38\u620f\u83dc\u5355", () -> a.m_91152_((Screen)new OptionsScreen((Screen)this, com.heypixel.heypixelmod.obsoverlay.c.GameMenuScreen.a.f_91066_))));
            this.b.add(new MenuButton("\u9000\u51fa\u6e38\u620f", () -> a.close()));
        }
        if (this.c.isEmpty()) {
            for (int i2 = 0; i2 < 80; ++i2) {
                this.c.add(new BackgroundParticle(this.d.nextFloat() * (float)this.f_96543_, this.d.nextFloat() * (float)this.f_96544_, this.c(), this.c(), 2.0f + this.d.nextFloat() * 2.0f));
            }
        }
    }

    public void b() {
        for (BackgroundParticle b2 : this.c) {
            b2.a += b2.c;
            b2.b += b2.d;
            if (!(b2.a < -10.0f || b2.a > (float)this.f_96543_ + 10.0f || b2.b < -10.0f) && !(b2.b > (float)this.f_96544_ + 10.0f)) continue;
            b2.a = this.d.nextFloat() * (float)this.f_96543_;
            b2.b = this.d.nextFloat() * (float)this.f_96544_;
            b2.c = this.c();
            b2.d = this.c();
        }
    }

    public void a(GuiGraphics guiGraphics, int n2, int n3, float f2) {
        PostProcess w2 = com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a(PostProcess.class);
        int n4 = w2 != null ? w2.p() : 0;
        float f3 = 160.0f;
        float f4 = 24.0f;
        float f5 = 10.0f;
        float f6 = 18.0f;
        float f7 = 56.0f;
        float f8 = f3 + f6 * 2.0f;
        float f9 = (float)this.b.size() * (f4 + f5) - f5 + f7 + f6;
        float f10 = (float)this.f_96543_ / 2.0f - f8 / 2.0f;
        float f11 = (float)this.f_96544_ / 2.0f - f9 / 2.0f;
        float f12 = 10.0f;
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a.a(canvas -> {
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c();
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a((float)a.m_91268_().m_85449_());
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a("mainmenu/cat.png", 0.0f, 0.0f, (float)this.f_96543_, (float)this.f_96544_);
            for (BackgroundParticle b2 : this.c) {
                com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(b2.a, b2.b, b2.e / 2.0f, new Color(255, 255, 255, 80));
            }
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.d();
        });
        if (n4 > 0) {
            com.heypixel.heypixelmod.obsoverlay.utils.RemotePlayer.a.a.a.a(n4);
        }
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a.a(canvas -> {
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c();
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a((float)a.m_91268_().m_85449_());
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c(f10, f11, f8, f9, f12);
            if (n4 > 0) {
                com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.b(f10, f11, f8, f9, f12);
            }
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f10, f11, f8, f9, f12, new Color(0, 0, 0, 90));
            this.a(f10, f11);
            float f11 = f11 + f7;
            Font font = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.c.a(16.0f);
            for (MenuButton a2 : this.b) {
                a2.c = (float)this.f_96543_ / 2.0f - f3 / 2.0f;
                a2.d = f11;
                a2.e = f3;
                a2.f = f4;
                a2.a(n2, n3, font);
                f11 += f4 + f5;
            }
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a();
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.d();
        });
        super.m_88315_(guiGraphics, n2, n3, f2);
    }

    public boolean a(double d2, double d3, int n2) {
        if (n2 == 0) {
            for (MenuButton a2 : this.b) {
                if (!a2.a((int)d2, (int)d3)) continue;
                a2.b.run();
                return true;
            }
        }
        return super.m_6375_(d2, d3, n2);
    }

    private void a(float f2, float f3) {
        String string = "EixClient";
        int n2 = Math.min(5, string.length());
        String string2 = string.substring(0, n2);
        String string3 = string.substring(n2);
        Color color = new Color(120, 180, 255, 255);
        Color color2 = new Color(255, 255, 255, 255);
        Font font = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.c.a(28.0f);
        float f4 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string2, font);
        float f5 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string3, font);
        float f6 = f4 + f5;
        float f7 = (float)this.f_96543_ / 2.0f - f6 / 2.0f;
        float f8 = f3 + 18.0f;
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string2, f7, f8, color, font);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string3, f7 + f4, f8, color2, font);
    }

    private float c() {
        float f2 = 0.3f + this.d.nextFloat() * 0.6f;
        return this.d.nextBoolean() ? f2 : -f2;
    }

    private static class MenuButton {
        private final String a;
        private final Runnable b;
        private float c;
        private float d;
        private float e;
        private float f;

        private MenuButton(String string, Runnable runnable) {
            this.a = string;
            this.b = runnable;
        }

        private void a(int n2, int n3, Font font) {
            boolean bl = this.a(n2, n3);
            Color color = bl ? new Color(30, 30, 35, 170) : new Color(20, 20, 25, 140);
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(this.c, this.d, this.e, this.f, 6.0f, color);
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.d(this.a, this.c + this.e / 2.0f, this.d + this.f / 2.0f, new Color(255, 255, 255, 230), font);
        }

        private boolean a(int n2, int n3) {
            return EntityOverlayRenderer.b(n2, n3, this.c, this.d, this.e, this.f);
        }
    }

    private static class BackgroundParticle {
        private float a;
        private float b;
        private float c;
        private float d;
        private final float e;

        private BackgroundParticle(float f2, float f3, float f4, float f5, float f6) {
            this.a = f2;
            this.b = f3;
            this.c = f4;
            this.d = f5;
            this.e = f6;
        }
    }
}

