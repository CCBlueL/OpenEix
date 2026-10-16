/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules;

import com.heypixel.heypixelmod.obsoverlay.EixClientCore;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.ClickGUI;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.HUD;
import com.heypixel.heypixelmod.obsoverlay.utils.RotationInterpolation;
import com.heypixel.heypixelmod.obsoverlay.utils.SoundResourcePaths;
import com.heypixel.heypixelmod.obsoverlay.utils.c.PlayerRotationController;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModuleBase
extends com.heypixel.heypixelmod.obsoverlay.d.SettingModel {
    private static final Logger c = LogManager.getLogger(ModuleBase.class);
    protected static final Minecraft a = Minecraft.m_91087_();
    public static boolean b = true;
    private final RotationInterpolation d = new RotationInterpolation(100.0f);
    private String e;
    private String f;
    private String g;
    private String h;
    private String i;
    private com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory j;
    private boolean k;
    private int l = 0;
    private int m;

    public ModuleBase(String string, String string2, com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory a2) {
        this.e = string;
        this.h = string2;
        this.j = a2;
        super.c(string);
        this.p();
    }

    public ModuleBase(String string, String string2, String string3, com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory a2) {
        this.e = string;
        this.f = string2;
        this.h = string3;
        this.j = a2;
        super.c(string);
        this.p();
    }

    public ModuleBase() {
    }

    public String a() {
        return this.f;
    }

    @Override
    public String b() {
        return this.e;
    }

    private void p() {
        StringBuilder stringBuilder = new StringBuilder();
        char[] cArray = this.e.toCharArray();
        for (int i2 = 0; i2 < cArray.length - 1; ++i2) {
            if (Character.isLowerCase(cArray[i2]) && Character.isUpperCase(cArray[i2 + 1])) {
                stringBuilder.append(cArray[i2]).append(" ");
                continue;
            }
            stringBuilder.append(cArray[i2]);
        }
        stringBuilder.append(cArray[cArray.length - 1]);
        this.g = stringBuilder.toString();
    }

    protected void c() {
        if (this.getClass().isAnnotationPresent(ModuleInfo.class)) {
            ModuleInfo moduleInfo = this.getClass().getAnnotation(ModuleInfo.class);
            this.e = moduleInfo.a();
            this.f = moduleInfo.b();
            this.h = moduleInfo.c();
            this.j = moduleInfo.d();
            super.c(this.e);
            this.p();
            com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().e().a(this);
        }
    }

    public void d() {
    }

    public void e() {
    }

    public void f() {
        this.a(!this.k);
    }

    public RotationInterpolation g() {
        return this.d;
    }

    @Override
    public String h() {
        try {
            EixClientCore a2 = com.heypixel.heypixelmod.obsoverlay.EixClientCore.a();
            if (a2 == null || a2.g() == null) {
                return this.e;
            }
            ClickGUI g2 = a2.g().a(ClickGUI.class);
            if (g2 == null || g2.c == null) {
                return this.e;
            }
            if (g2.c.l().equals("English")) {
                return this.e;
            }
            return this.f == null || this.f.isEmpty() ? this.e : this.f;
        }
        catch (Exception exception) {
            return this.e;
        }
    }

    public String i() {
        return this.g;
    }

    public String j() {
        return this.h;
    }

    public String k() {
        return this.i;
    }

    public void a(String string) {
        if (string == null) {
            this.i = null;
            b = true;
        } else if (!string.equals(this.i)) {
            this.i = string;
            b = true;
        }
    }

    public com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory l() {
        return this.j;
    }

    public boolean m() {
        return this.k;
    }

    public void a(boolean bl) {
        if (this.k == bl) {
            return;
        }
        try {
            EixClientCore a2 = com.heypixel.heypixelmod.obsoverlay.EixClientCore.a();
            if (bl) {
                this.k = true;
                a2.b().a(this);
                this.d();
                DynamicIsland.a(this, true);
                if (!(this instanceof ClickGUI)) {
                    HUD m2 = com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a(HUD.class);
                    if (m2 != null && m2.f.m()) {
                        SoundResourcePaths.a("enable.wav", 1.0f);
                    }
                    if (a2.j() != null) {
                        com.heypixel.heypixelmod.obsoverlay.c.MainMenuScreen.a a3 = new com.heypixel.heypixelmod.obsoverlay.c.MainMenuScreen.a(com.heypixel.heypixelmod.obsoverlay.c.MainMenuScreen.b.a, this.h() + " Enabled!", 3000L);
                        a2.j().a(a3);
                    }
                }
            } else {
                this.k = false;
                a2.b().b(this);
                this.e();
                DynamicIsland.a(this, false);
                if (!(this instanceof ClickGUI)) {
                    HUD m3 = com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a(HUD.class);
                    if (m3 != null && m3.f.m()) {
                        SoundResourcePaths.a("disable.wav", 1.0f);
                    }
                    if (a2.j() != null) {
                        com.heypixel.heypixelmod.obsoverlay.c.MainMenuScreen.a a4 = new com.heypixel.heypixelmod.obsoverlay.c.MainMenuScreen.a(com.heypixel.heypixelmod.obsoverlay.c.MainMenuScreen.b.d, this.h() + " Disabled!", 3000L);
                        a2.j().a(a4);
                    }
                }
            }
            com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.d();
        }
        catch (Exception exception) {
            c.warn("\u5207\u6362\u6a21\u5757\u72b6\u6001\u5931\u8d25: " + this.e, (Throwable)exception);
        }
    }

    public int n() {
        return this.l;
    }

    public void a(int n2) {
        this.l = n2;
    }

    public int o() {
        return this.m;
    }

    public void b(int n2) {
        this.m = n2;
    }
}

