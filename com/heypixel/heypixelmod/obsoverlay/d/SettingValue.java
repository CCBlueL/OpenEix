/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.d;

import com.heypixel.heypixelmod.obsoverlay.d.a.BooleanSetting;
import com.heypixel.heypixelmod.obsoverlay.d.a.FloatSetting;
import com.heypixel.heypixelmod.obsoverlay.d.a.IntegerSetting;
import com.heypixel.heypixelmod.obsoverlay.d.a.ModeSetting;
import com.heypixel.heypixelmod.obsoverlay.d.a.StringSetting;
import java.util.function.Supplier;

public abstract class SettingValue {
    private final com.heypixel.heypixelmod.obsoverlay.d.a a;
    private final String b;
    private final Supplier<Boolean> c;

    protected SettingValue(com.heypixel.heypixelmod.obsoverlay.d.a a2, String string, Supplier<Boolean> supplier) {
        this.a = a2;
        this.b = string;
        this.c = supplier;
        com.heypixel.heypixelmod.obsoverlay.a.a().d().a(this);
    }

    public abstract com.heypixel.heypixelmod.obsoverlay.d.f a();

    public a b() {
        throw new com.heypixel.heypixelmod.obsoverlay.a.a();
    }

    public com.heypixel.heypixelmod.obsoverlay.d.a.c c() {
        throw new com.heypixel.heypixelmod.obsoverlay.a.a();
    }

    public f d() {
        throw new com.heypixel.heypixelmod.obsoverlay.a.a();
    }

    public e e() {
        throw new com.heypixel.heypixelmod.obsoverlay.a.a();
    }

    public b f() {
        throw new com.heypixel.heypixelmod.obsoverlay.a.a();
    }

    public d g() {
        throw new com.heypixel.heypixelmod.obsoverlay.a.a();
    }

    public boolean h() {
        return this.c == null || this.c.get() != false;
    }

    public com.heypixel.heypixelmod.obsoverlay.d.a i() {
        return this.a;
    }

    public String j() {
        return this.b;
    }

    public Supplier<Boolean> k() {
        return this.c;
    }
}

