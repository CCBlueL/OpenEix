/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.c;

import com.heypixel.heypixelmod.obsoverlay.d.a.ModeSetting;
import com.heypixel.heypixelmod.obsoverlay.d.a.StringSetting;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventShader;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;
import com.heypixel.heypixelmod.obsoverlay.utils.EntityOverlayRenderer;
import com.heypixel.heypixelmod.obsoverlay.utils.RotationInterpolation;
import com.heypixel.heypixelmod.obsoverlay.utils.TargetOverlayRenderer;
import com.heypixel.heypixelmod.obsoverlay.utils.UnicodeTextHeuristic;
import com.heypixel.heypixelmod.obsoverlay.utils.ElapsedTimer;
import com.heypixel.heypixelmod.obsoverlay.utils.ArgbColorPalette;
import com.heypixel.heypixelmod.obsoverlay.utils.IconGlyphRegistry;
import com.heypixel.heypixelmod.obsoverlay.utils.renderer.MiSansFontRenderer;
import com.heypixel.heypixelmod.obsoverlay.utils.renderer.text.FontManager;
import com.heypixel.heypixelmod.obsoverlay.utils.MathUtils;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class MainMenuScreen
extends Screen {
    private static final Minecraft U = Minecraft.m_91087_();
    public static float a = 100.0f;
    public static float b = 100.0f;
    public static float c = 400.0f;
    public static float d = 250.0f;
    private final RotationInterpolation V = new RotationInterpolation(0.0f);
    private final ElapsedTimer W = new ElapsedTimer();
    private final RotationInterpolation X = new RotationInterpolation(0.0f);
    private final ElapsedTimer Y = new ElapsedTimer();
    com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory e = null;
    ModuleBase f = null;
    int[] g = new int[]{-1, -1};
    boolean h = false;
    RotationInterpolation i = new RotationInterpolation(100.0f);
    RotationInterpolation j = new RotationInterpolation(140.0f);
    RotationInterpolation k = new RotationInterpolation(100.0f);
    RotationInterpolation l = new RotationInterpolation(0.0f);
    RotationInterpolation m = new RotationInterpolation(0.0f);
    RotationInterpolation n = new RotationInterpolation(0.0f);
    HashMap<com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory, RotationInterpolation> o = new HashMap<com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory, RotationInterpolation>(){
        {
            for (com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory a3 : com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory.values()) {
                this.put(a3, new RotationInterpolation(0.0f));
            }
        }
    };
    HashMap<com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory, RotationInterpolation> p = new HashMap<com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory, RotationInterpolation>(){
        {
            for (com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory a3 : com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory.values()) {
                this.put(a3, new RotationInterpolation(0.0f));
            }
        }
    };
    HashMap<com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory, List<ModuleBase>> q = new HashMap<com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory, List<ModuleBase>>(){
        {
            for (com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory a3 : com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory.values()) {
                this.put(a3, com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a(a3));
            }
        }
    };
    HashMap<ModuleBase, RotationInterpolation> r = new HashMap<ModuleBase, RotationInterpolation>(){
        {
            for (ModuleBase b2 : com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a()) {
                this.put(b2, new RotationInterpolation(0.0f, 255.0f));
            }
        }
    };
    HashMap<ModuleBase, RotationInterpolation> s = new HashMap<ModuleBase, RotationInterpolation>(){
        {
            for (ModuleBase b2 : com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a()) {
                this.put(b2, new RotationInterpolation(0.0f));
            }
        }
    };
    HashMap<com.heypixel.heypixelmod.obsoverlay.d.SettingValue, RotationInterpolation> t = new HashMap<com.heypixel.heypixelmod.obsoverlay.d.SettingValue, RotationInterpolation>(){
        {
            for (com.heypixel.heypixelmod.obsoverlay.d.SettingValue c2 : com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().d().a()) {
                this.put(c2, new RotationInterpolation(0.0f));
            }
        }
    };
    String u = "";
    float v;
    float w;
    boolean x = false;
    boolean y = false;
    boolean z = false;
    boolean A = false;
    boolean B = false;
    com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory C = null;
    ModuleBase D = null;
    ModuleBase E = null;
    RotationInterpolation F = new RotationInterpolation(0.0f);
    RotationInterpolation G = new RotationInterpolation(0.0f);
    List<ModuleBase> H;
    List<com.heypixel.heypixelmod.obsoverlay.d.SettingValue> I;
    com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a J;
    com.heypixel.heypixelmod.obsoverlay.d.SettingModel.c K;
    com.heypixel.heypixelmod.obsoverlay.d.SettingModel.c L;
    e M;
    int N;
    f O;
    f P;
    String Q = "";
    com.heypixel.heypixelmod.obsoverlay.d.SettingModel.d R;
    com.heypixel.heypixelmod.obsoverlay.d.SettingModel.d S;
    String T;
    private boolean Z = false;
    private float aa = 0.0f;
    private float ab = 0.0f;

    public MainMenuScreen() {
        super(Component.m_130674_((String)"EixClient"));
    }

    public void a() {
        com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().i().c();
        com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().b().b((Object)this);
        super.m_7379_();
    }

    public boolean a(double d2, double d3, int n2) {
        if (n2 == 0) {
            this.Z = true;
        }
        if (this.E == null || n2 != 3 && n2 != 4) {
            if (n2 != 2 && this.E == null) {
                if (this.D != null) {
                    if (n2 == 0) {
                        this.D.f();
                    } else if (n2 == 1) {
                        this.f = this.D;
                        this.I = com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().d().a(this.D);
                        this.n.c = 0.0f;
                        this.n.a = 0.0f;
                    }
                }
                if (n2 == 0) {
                    boolean bl;
                    if (this.h && !this.A && !this.B) {
                        this.e = null;
                        this.f = null;
                        this.I = null;
                    }
                    if (this.z && this.C != null) {
                        this.e = this.C;
                        this.m.a = 0.0f;
                        this.m.c = 0.0f;
                        this.F.c = 5.0f;
                        this.F.a = 255.0f;
                        this.z = false;
                    }
                    boolean bl2 = bl = this.e != null ? EntityOverlayRenderer.a((int)d2, (int)d3, a, b, a + c, b + 25.0f) : EntityOverlayRenderer.a((int)d2, (int)d3, a, b, a + 100.0f, b + 40.0f);
                    if ((this.e == null || !this.h) && bl) {
                        this.a(d2, d3);
                        this.B = true;
                    }
                    if (EntityOverlayRenderer.a((int)d2, (int)d3, a + c - 10.0f, b + d - 10.0f, a + c, b + d)) {
                        this.a(d2, d3);
                        this.A = true;
                    }
                    if (this.J != null) {
                        this.J.a(!this.J.m());
                    }
                    if (this.K != null) {
                        this.L = this.K;
                    }
                    if (this.M != null) {
                        this.M.a(this.N);
                        RotationInterpolation ag2 = this.t.get(this.M);
                        ag2.c = 0.0f;
                        ag2.a = 255.0f;
                    }
                    if (this.R != null) {
                        this.S = this.R;
                    }
                    if (this.O != null) {
                        this.P = this.O;
                        this.Q = String.valueOf(this.O.n());
                    } else if (this.P != null) {
                        this.P.a(this.Q);
                        this.P = null;
                    }
                }
            } else if (n2 == 2 && this.D != null) {
                this.E = this.D;
            }
            return true;
        }
        this.E.b(-n2);
        this.E = null;
        return true;
    }

    public boolean b(double d2, double d3, int n2) {
        if (n2 == 0) {
            this.Z = false;
        }
        if (this.A) {
            this.A = false;
            this.a(-1, -1);
        }
        if (this.B) {
            this.B = false;
            this.a(-1, -1);
        }
        if (this.L != null) {
            this.L = null;
        }
        return true;
    }

    public boolean a(int n2, int n3, int n4) {
        if (this.S != null) {
            if (n2 == 256) {
                this.S.a(0);
                this.S = null;
                return true;
            }
            this.S.a(n2);
            this.S = null;
            return true;
        }
        if (this.P != null) {
            if (n2 == 256) {
                this.P = null;
                return true;
            }
            if (n2 == 257 || n2 == 335) {
                this.P.a(this.Q);
                this.P = null;
                return true;
            }
            if (n2 == 259) {
                if (!this.Q.isEmpty()) {
                    this.Q = this.Q.substring(0, this.Q.length() - 1);
                }
                return true;
            }
            if (n2 == 86 && (n4 & 2) != 0) {
                String string = com.heypixel.heypixelmod.obsoverlay.c.MainMenuScreen.U.f_91068_.m_90876_();
                if (string != null) {
                    this.Q = this.Q + string.replace("\n", " ").replace("\r", "");
                }
                return true;
            }
            if (n2 == 65 && (n4 & 2) != 0) {
                this.Q = "";
                return true;
            }
            return true;
        }
        if (this.E != null) {
            if (n2 == 256) {
                this.E.b(0);
                this.E = null;
                return true;
            }
            this.E.b(n2);
            this.E = null;
        }
        return super.m_7933_(n2, n3, n4);
    }

    public boolean a(char c2, int n2) {
        if (this.P != null) {
            if (c2 >= ' ' && c2 != '\u007f') {
                this.Q = this.Q + c2;
            }
            return true;
        }
        return super.m_5534_(c2, n2);
    }

    private static String a(int n2) {
        if (n2 == 0) {
            return "NONE";
        }
        if (n2 < 0) {
            return "M" + -n2;
        }
        InputConstants.Key key = InputConstants.m_84827_((int)n2, (int)0);
        if (key == InputConstants.f_84822_) {
            return "NONE";
        }
        String string = key.m_84875_().getString();
        if (string == null || string.isEmpty()) {
            return "NONE";
        }
        return string.toUpperCase();
    }

    private static String a(FontManager c2, String string, double d2, float f2) {
        if (string == null || string.isEmpty()) {
            return "";
        }
        if (c2.a(string, d2) <= f2) {
            return string;
        }
        String string2 = string;
        while (!string2.isEmpty() && c2.a(string2 + "...", d2) > f2) {
            string2 = string2.substring(0, string2.length() - 1);
        }
        return string2 + "...";
    }

    protected void b() {
        com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().b().a((Object)this);
        this.t.forEach((c2, ag2) -> {
            if (c2.a() == com.heypixel.heypixelmod.obsoverlay.d.SettingValueType.c) {
                ag2.c = 0.0f;
                ag2.a = 255.0f;
            }
        });
    }

    public boolean a(double d2, double d3, double d4) {
        if (this.E == null && EntityOverlayRenderer.b((int)d2, (int)d3, a + 5.0f, b + 20.0f, 100.0f, d - 5.0f)) {
            this.m.a = (float)((double)this.m.a + d4 * 15.0);
            this.W.a();
        }
        if (this.I != null && this.E == null && EntityOverlayRenderer.b((int)d2, (int)d3, a + 140.0f, b + 20.0f, c - 155.0f, d - 25.0f)) {
            this.n.a = (float)((double)this.n.a + d4 * 15.0);
            this.Y.a();
        }
        return true;
    }

    @EventTarget
    public void onShader(EventShader eventShader) {
        if (com.heypixel.heypixelmod.obsoverlay.c.MainMenuScreen.U.f_91080_ == this) {
            EntityOverlayRenderer.b(eventShader.stack(), a, b, this.i.c, this.j.c, 5.0f, 0x40000000);
        }
    }

    public void a(GuiGraphics guiGraphics, int n2, int n3, float f2) {
        int c6;
        RotationInterpolation f25;
        PoseStack poseStack = guiGraphics.m_280168_();
        this.D = null;
        this.z = false;
        this.y = false;
        this.x = false;
        FontManager c2 = com.heypixel.heypixelmod.obsoverlay.utils.renderer.MiSansFontRenderer.a;
        if (this.e == null) {
            this.i.a = 100.0f;
            this.j.a = 140.0f;
        } else {
            this.i.a = c;
            this.j.a = d;
        }
        this.i.a(true);
        this.j.a(true);
        EntityOverlayRenderer.b(poseStack, a, b, this.i.c, this.j.c, 5.0f, com.heypixel.heypixelmod.obsoverlay.utils.ArgbColorPalette.a(0, 0, 0, 100));
        for (com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory f6 : com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory.values()) {
            RotationInterpolation f13 = this.o.get((Object)f6);
            f25 = this.p.get((Object)f6);
            f25.a = this.e == null ? 255.0f : 0.0f;
            f13.a(true);
            f25.a(true);
            float c3 = (float)(f6.ordinal() * 25) * (f25.c / 255.0f);
            if (f25.a >= 4.0f) {
                c2.a(f25.c / 255.0f);
                com.heypixel.heypixelmod.obsoverlay.utils.renderer.MiSansFontRenderer.b.a(poseStack, f6.b(), a + 8.0f + f13.c, b + 41.0f + c3, Color.WHITE, true, 0.4);
                c2.a(poseStack, f6.a(), a + 25.0f + f13.c, b + 40.0f + c3, Color.WHITE, true, 0.4);
                c2.a(1.0f);
            }
            f13.a = (c6 = EntityOverlayRenderer.a(n2, n3, a, b + 40.0f + c3, a + 100.0f, b + 40.0f + c3 + 20.0f)) != 0 ? 5.0f : 0.0f;
            if (!(f25.c >= 250.0f) || c6 == 0) continue;
            this.C = f6;
            this.z = true;
        }
        this.k.a(true);
        this.l.a(true);
        if (this.k.c > 5.0f) {
            c2.a(this.k.c / 255.0f);
            c2.a(poseStack, this.u, a + 6.0f + this.l.c, b + 3.0f, Color.WHITE, true, 0.4);
        }
        c2.a((255.0f - this.k.c) / 255.0f);
        c2.a(poseStack, "EixClient", a + 50.0f - c2.a("EixClient", 0.75) / 2.0f, b + 5.0f, Color.WHITE, true, 0.75);
        c2.a(1.0f);
        if (this.e != null) {
            this.k.a = 255.0f;
            this.u = "< " + this.e.a() + (String)(this.f != null ? " / " + this.f.h() + " - " + this.f.j() : "");
            this.h = EntityOverlayRenderer.a(n2, n3, a + 8.0f, b + 5.0f, a + 5.0f + c2.a(this.u, 0.4), (float)((double)(b + 5.0f) + c2.a(true, (double)0.4f)));
            this.l.a = this.h && !this.A && !this.B ? -2.0f : 0.0f;
            if (this.m.a < -this.v) {
                this.m.a = -this.v;
            }
            if (this.m.a > 0.0f) {
                this.m.a = 0.0f;
            }
            this.m.a(true);
        } else {
            this.k.a = 4.0f;
        }
        TargetOverlayRenderer.a(false);
        EntityOverlayRenderer.a(poseStack, a, b + 20.0f, a + this.i.c, b + this.j.c - 5.0f, Integer.MIN_VALUE);
        TargetOverlayRenderer.b(true);
        List<ModuleBase> list = this.q.get((Object)this.e);
        if (list != null) {
            this.H = list;
            this.F.a = 255.0f;
        } else {
            this.F.a = 5.0f;
        }
        this.F.a(true);
        if (list == null && this.F.c < 8.0f) {
            this.H = null;
        }
        if (this.H != null) {
            float f31 = 0.0f;
            this.aa = d - 25.0f;
            for (ModuleBase b2 : this.H) {
                boolean bl = EntityOverlayRenderer.b(n2, n3, a + 5.0f, b + 20.0f, 120.0f, d - 25.0f) && EntityOverlayRenderer.b(n2, n3, a + 5.0f, b + 20.0f + f31 + this.m.c, 120.0f, 25.0f) && this.F.c > 250.0f && this.E == null;
                f25 = this.s.get(b2);
                f25.a = b2.m() ? this.F.c : 6.0f;
                f25.a(true);
                int c5 = (int)this.F.c;
                c6 = com.heypixel.heypixelmod.obsoverlay.utils.ArgbColorPalette.a(54, 98, 236, (int)f25.c);
                int ag7 = com.heypixel.heypixelmod.obsoverlay.utils.ArgbColorPalette.a(25, 25, 25, c5);
                EntityOverlayRenderer.b(poseStack, a + 5.0f, b + 20.0f + f31 + this.m.c, 120.0f, 25.0f, 5.0f, ag7);
                EntityOverlayRenderer.b(poseStack, a + 5.0f, b + 20.0f + f31 + this.m.c, 120.0f, 25.0f, 5.0f, c6);
                RotationInterpolation i2 = this.r.get(b2);
                if (bl) {
                    i2.a = 150.0f;
                    this.D = b2;
                } else {
                    i2.a = 5.0f;
                }
                i2.a(true);
                int string = com.heypixel.heypixelmod.obsoverlay.utils.ArgbColorPalette.a(255, 255, 255, (int)i2.c / 3);
                EntityOverlayRenderer.b(poseStack, a + 5.0f, b + 20.0f + f31 + this.m.c, 120.0f, 25.0f, 5.0f, string);
                c2.a((float)c5 / 255.0f);
                c2.a(poseStack, b2.h(), a + 13.0f, b + 25.0f + f31 + this.m.c, Color.WHITE, true, 0.4);
                c2.a(1.0f);
                f31 += 30.0f;
            }
            this.v = f31 + 20.0f - d;
            this.aa -= f31 - 5.0f;
            float f11 = this.v + d;
            if (f11 > d - 25.0f) {
                this.V.a(true);
                this.V.a = this.W.a(1000.0) ? 0.0f : 255.0f;
                float f3 = d - 25.0f;
                float f4 = (float)com.heypixel.heypixelmod.obsoverlay.utils.MathUtils.a((double)(-this.m.c / -this.aa), 0.0, 1.0);
                float f5 = f3 / f11 * f3;
                float c7 = Math.max(f5, 20.0f);
                float e2 = f4 * (f3 - c7);
                EntityOverlayRenderer.b(poseStack, a + 127.0f, b + 20.0f + e2, 3.0f, c7, 1.5f, EntityOverlayRenderer.a(3630060, this.V.c / 255.0f));
            }
        }
        if (this.I != null) {
            int bl2;
            int f4 = EntityOverlayRenderer.b(n2, n3, a + 140.0f, b + 20.0f, c - 155.0f, d - 25.0f) ? 1 : 0;
            float f32 = this.n.c;
            if (this.n.a < -this.w) {
                this.n.a = -this.w;
            }
            if (this.n.a > 0.0f) {
                this.n.a = 0.0f;
            }
            this.n.a(true);
            float f6 = 0.0f;
            float f7 = 0.0f;
            this.ab = d - 25.0f;
            this.J = null;
            for (com.heypixel.heypixelmod.obsoverlay.d.SettingValue c8 : this.I) {
                float f24;
                if (!c8.h() || c8.a() != com.heypixel.heypixelmod.obsoverlay.d.SettingValueType.a) continue;
                com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a f18 = c8.b();
                RotationInterpolation f19 = this.t.get(f18);
                f19.a = f18.m() ? 255.0f : 0.0f;
                f19.a(true);
                float f20 = 0.4f;
                FontManager f21 = com.heypixel.heypixelmod.obsoverlay.utils.renderer.MiSansFontRenderer.a;
                bl2 = 0;
                if (UnicodeTextHeuristic.a(c8.j())) {
                    f21 = com.heypixel.heypixelmod.obsoverlay.utils.renderer.MiSansFontRenderer.a;
                    f20 = 0.325f;
                    bl2 = 2;
                }
                if (f6 + (f24 = f21.a(c8.j(), (double)f20) + 23.0f) + 20.0f > c - 155.0f) {
                    f6 = 0.0f;
                    f7 += 20.0f;
                }
                if (f4 != 0 && EntityOverlayRenderer.b(n2, n3, a + 130.0f + f6, b + f7 + f32 + 20.0f, f24, 13.0f)) {
                    this.J = f18;
                }
                int n4 = com.heypixel.heypixelmod.obsoverlay.utils.ArgbColorPalette.a(54, 98, 236, (int)f19.c);
                EntityOverlayRenderer.b(poseStack, a + 140.0f + f6, b + f7 + f32 + 20.0f, 12.0f, 12.0f, 2.0f, com.heypixel.heypixelmod.obsoverlay.utils.ArgbColorPalette.a(0, 0, 0, 150));
                EntityOverlayRenderer.b(poseStack, a + 142.0f + f6, b + f7 + f32 + 22.0f, 8.0f, 8.0f, 2.0f, n4);
                f21.a(poseStack, c8.j(), a + 155.0f + f6, b + f7 + f32 + 19.0f + (float)bl2, Color.WHITE, true, f20);
                f6 += f24;
            }
            f7 += 10.0f;
            this.K = null;
            for (com.heypixel.heypixelmod.obsoverlay.d.SettingValue c9 : this.I) {
                if (!c9.h() || c9.a() != com.heypixel.heypixelmod.obsoverlay.d.SettingValueType.b) continue;
                com.heypixel.heypixelmod.obsoverlay.d.SettingModel.c d2 = c9.c();
                RotationInterpolation bl = this.t.get(d2);
                if (f4 != 0 && EntityOverlayRenderer.b(n2, n3, a + 140.0f, b + f7 + f32 + 39.5f, c - 155.0f, 10.0f)) {
                    this.K = d2;
                }
                c2.a(poseStack, c9.j(), a + 140.0f, b + f7 + f32 + 25.0f, Color.WHITE, true, 0.4);
                String string = (float)Math.round(d2.q() * 100.0f) / 100.0f + " / " + d2.n();
                c2.a(poseStack, string, a + c - c2.a(string, 0.4) - 15.0f, b + f7 + f32 + 25.0f, Color.WHITE, true, 0.4);
                float f22 = (d2.q() - d2.m()) / (d2.n() - d2.m());
                bl2 = com.heypixel.heypixelmod.obsoverlay.utils.ArgbColorPalette.a(54, 98, 236, 255);
                EntityOverlayRenderer.b(poseStack, a + 140.0f, b + f7 + f32 + 42.0f, c - 155.0f, 5.0f, 3.0f, com.heypixel.heypixelmod.obsoverlay.utils.ArgbColorPalette.a(0, 0, 0, 150));
                bl.a = (c - 155.0f) * f22;
                bl.a(true);
                EntityOverlayRenderer.b(poseStack, a + 140.0f, b + f7 + f32 + 42.0f, bl.c, 5.0f, 3.0f, bl2);
                EntityOverlayRenderer.b(poseStack, a + 135.0f + bl.c, b + f7 + f32 + 39.5f, 10.0f, 10.0f, 5.0f, com.heypixel.heypixelmod.obsoverlay.utils.ArgbColorPalette.a(255, 255, 255, 255));
                f7 += 25.0f;
            }
            this.M = null;
            for (com.heypixel.heypixelmod.obsoverlay.d.SettingValue f26 : this.I) {
                if (!f26.h() || f26.a() != com.heypixel.heypixelmod.obsoverlay.d.SettingValueType.c) continue;
                e f27 = f26.e();
                RotationInterpolation f28 = this.t.get(f27);
                f28.a(true);
                c2.a(poseStack, f26.j(), a + 140.0f, b + f7 + f32 + 25.0f, Color.WHITE, true, 0.4);
                f6 = 0.0f;
                f7 += 15.0f;
                for (int f29 = 0; f29 < f27.m().length; ++f29) {
                    String f30 = f27.m()[f29];
                    float f8 = c2.a(f30, 0.4) + 20.0f;
                    if (f6 + f8 + 20.0f > c - 155.0f) {
                        f6 = 0.0f;
                        f7 += 20.0f;
                    }
                    if (f4 != 0 && EntityOverlayRenderer.b(n2, n3, a + 140.0f + f6, b + f7 + f32 + 25.0f, f8, 13.0f)) {
                        this.M = f27;
                        this.N = f29;
                    }
                    int n5 = com.heypixel.heypixelmod.obsoverlay.utils.ArgbColorPalette.a(54, 98, 236, f27.a(f30) ? (int)f28.c : 10);
                    EntityOverlayRenderer.b(poseStack, a + 140.0f + f6, b + f7 + f32 + 27.0f, 10.0f, 10.0f, 5.0f, com.heypixel.heypixelmod.obsoverlay.utils.ArgbColorPalette.a(0, 0, 0, 150));
                    EntityOverlayRenderer.b(poseStack, a + 141.0f + f6, b + f7 + f32 + 28.0f, 8.0f, 8.0f, 5.0f, n5);
                    c2.a(poseStack, f30, a + 152.0f + f6, b + f7 + f32 + 25.0f, Color.WHITE, true, 0.4);
                    f6 += f8;
                }
                f7 += 20.0f;
            }
            this.O = null;
            for (com.heypixel.heypixelmod.obsoverlay.d.SettingValue c3 : this.I) {
                Object object;
                boolean bl;
                if (!c3.h() || c3.a() != com.heypixel.heypixelmod.obsoverlay.d.SettingValueType.d) continue;
                f f9 = c3.d();
                c2.a(poseStack, c3.j(), a + 140.0f, b + f7 + f32 + 25.0f, Color.WHITE, true, 0.4);
                float f10 = a + 140.0f;
                float f11 = b + (f7 += 14.0f) + f32 + 25.0f;
                float f12 = c - 155.0f;
                boolean bl3 = this.P == f9;
                boolean bl4 = bl = f4 != 0 && EntityOverlayRenderer.b(n2, n3, f10, f11, f12, 14.0f);
                if (bl) {
                    this.O = f9;
                }
                EntityOverlayRenderer.b(poseStack, f10, f11, f12, 14.0f, 3.0f, com.heypixel.heypixelmod.obsoverlay.utils.ArgbColorPalette.a(0, 0, 0, 150));
                if (bl3) {
                    EntityOverlayRenderer.b(poseStack, f10, f11, f12, 14.0f, 3.0f, com.heypixel.heypixelmod.obsoverlay.utils.ArgbColorPalette.a(54, 98, 236, 90));
                } else if (bl) {
                    EntityOverlayRenderer.b(poseStack, f10, f11, f12, 14.0f, 3.0f, com.heypixel.heypixelmod.obsoverlay.utils.ArgbColorPalette.a(255, 255, 255, 25));
                }
                Object object2 = object = bl3 ? this.Q : String.valueOf(f9.n());
                if (bl3 && System.currentTimeMillis() / 500L % 2L == 0L) {
                    object = (String)object + "|";
                }
                c2.a(poseStack, com.heypixel.heypixelmod.obsoverlay.c.MainMenuScreen.a(c2, (String)object, 0.4, f12 - 10.0f), f10 + 5.0f, f11 + 3.0f, Color.WHITE, true, 0.4);
                f7 += 22.0f;
            }
            this.R = null;
            for (com.heypixel.heypixelmod.obsoverlay.d.SettingValue c4 : this.I) {
                if (!c4.h() || c4.a() != com.heypixel.heypixelmod.obsoverlay.d.SettingValueType.f) continue;
                com.heypixel.heypixelmod.obsoverlay.d.SettingModel.d d2 = c4.g();
                c2.a(poseStack, c4.j(), a + 140.0f, b + f7 + f32 + 25.0f, Color.WHITE, true, 0.4);
                boolean bl = this.S == d2;
                String string = bl ? "\u6309\u4e0b\u6309\u952e..." : com.heypixel.heypixelmod.obsoverlay.c.MainMenuScreen.a(d2.m());
                float f13 = c2.a(string, 0.4) + 20.0f;
                float f14 = a + 140.0f;
                float f15 = b + (f7 += 15.0f) + f32 + 25.0f;
                if (f4 != 0 && EntityOverlayRenderer.b(n2, n3, f14, f15, f13, 14.0f)) {
                    this.R = d2;
                }
                EntityOverlayRenderer.b(poseStack, f14, f15, f13, 14.0f, 7.0f, com.heypixel.heypixelmod.obsoverlay.utils.ArgbColorPalette.a(0, 0, 0, 150));
                EntityOverlayRenderer.b(poseStack, f14, f15, f13, 14.0f, 7.0f, com.heypixel.heypixelmod.obsoverlay.utils.ArgbColorPalette.a(54, 98, 236, bl ? 150 : 60));
                c2.a(poseStack, string, f14 + 8.0f, f15 + 3.0f, Color.WHITE, true, 0.4);
                f7 += 20.0f;
            }
            this.w = f7 - d + 25.0f;
            this.ab -= f7;
            float f16 = this.w + d;
            if (f16 > d - 25.0f) {
                this.X.a(true);
                this.X.a = this.Y.a(1000.0) ? 0.0f : 255.0f;
                float f17 = d - 25.0f;
                float f18 = (float)com.heypixel.heypixelmod.obsoverlay.utils.MathUtils.a((double)(-this.n.c / -this.ab), 0.0, 1.0);
                float f19 = f17 / f16 * f17;
                float f20 = Math.max(f19, 20.0f);
                float f21 = f18 * (f17 - f20);
                EntityOverlayRenderer.b(poseStack, a + c - 8.0f, b + 20.0f + f21, 3.0f, f20, 1.5f, EntityOverlayRenderer.a(3630060, this.X.c / 255.0f));
            }
        }
        if (this.L != null) {
            float string = ((float)n2 - a - 140.0f) / (c - 160.0f);
            float string2 = this.L.m() + (this.L.n() - this.L.m()) * string;
            if (string2 < this.L.m()) {
                string2 = this.L.m();
            }
            if (string2 > this.L.n()) {
                string2 = this.L.n();
            }
            string2 = (float)Math.round(string2 / this.L.o()) * this.L.o();
            this.L.a(string2);
        }
        if (this.B && this.Z) {
            a += (float)(n2 - this.g[0]);
            b += (float)(n3 - this.g[1]);
            this.a(n2, n3);
        }
        if (this.H != null && !this.h && this.A && this.Z) {
            c += (float)(n2 - this.g[0]);
            d += (float)(n3 - this.g[1]);
            if (c < 500.0f) {
                c = 500.0f;
            }
            if (d < 300.0f) {
                d = 300.0f;
            }
            this.a(n2, n3);
        }
        if (this.E != null || this.S != null) {
            this.G.a = 250.0f;
            this.T = this.E != null ? this.E.h() : this.S.j();
        } else {
            this.G.a = 5.0f;
        }
        this.G.a(true);
        if (this.G.c > 6.0f) {
            EntityOverlayRenderer.a(poseStack, a, b, a + this.i.c, b + this.j.c, com.heypixel.heypixelmod.obsoverlay.utils.ArgbColorPalette.a(0, 0, 0, (int)this.G.c / 2));
            c2.a(this.G.c / 255.0f);
            String string = "Press a key to bind " + this.T;
            String string2 = "(Press ESC to remove/cancel key bind)";
            c2.a(poseStack, string, a + this.i.c / 2.0f - c2.a(string, 0.6) / 2.0f, (double)b + ((double)this.j.c - c2.a(true, 0.6)) / 2.0 - 10.0, Color.WHITE, true, 0.6);
            c2.a(poseStack, string2, a + this.i.c / 2.0f - c2.a(string2, 0.4) / 2.0f, (double)b + ((double)this.j.c - c2.a(true, 0.4)) / 2.0 + 15.0, Color.WHITE, true, 0.4);
            c2.a(1.0f);
        }
        if (this.e != null) {
            com.heypixel.heypixelmod.obsoverlay.utils.renderer.MiSansFontRenderer.b.a(0.5f);
            com.heypixel.heypixelmod.obsoverlay.utils.renderer.MiSansFontRenderer.b.a(poseStack, com.heypixel.heypixelmod.obsoverlay.utils.IconGlyphRegistry.a, a + this.i.c - 10.0f, b + this.j.c - 10.0f, Color.WHITE, false, 0.3);
            com.heypixel.heypixelmod.obsoverlay.utils.renderer.MiSansFontRenderer.b.a(1.0f);
        }
        TargetOverlayRenderer.a();
    }

    public void a(double d2, double d3) {
        this.a((int)d2, (int)d3);
    }

    public void a(int n2, int n3) {
        this.g[0] = n2;
        this.g[1] = n3;
    }
}

