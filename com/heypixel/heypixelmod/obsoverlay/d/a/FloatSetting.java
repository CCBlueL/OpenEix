/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.d.a;

import com.heypixel.heypixelmod.obsoverlay.d.SettingModel;
import com.heypixel.heypixelmod.obsoverlay.d.SettingValue;
import com.heypixel.heypixelmod.obsoverlay.d.SettingValueType;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class FloatSetting
extends SettingValue {
    private final float a;
    private final float b;
    private final Consumer<SettingValue> c;
    private float d;
    private float e;
    private float f;
    private float g;

    public FloatSetting(SettingModel a2, String string, float f2, float f3, Consumer<SettingValue> consumer, Supplier<Boolean> supplier) {
        super(a2, string, supplier);
        this.a = f2;
        this.b = f3;
        this.d = f2;
        this.e = f3;
        this.c = consumer;
    }

    @Override
    public SettingValueType a() {
        return com.heypixel.heypixelmod.obsoverlay.d.SettingValueType.e;
    }

    @Override
    public FloatSetting f() {
        return this;
    }

    public float l() {
        return this.a;
    }

    public float m() {
        return this.b;
    }

    public float n() {
        return this.d;
    }

    public void a(float f2) {
        this.d = f2;
        if (this.c != null) {
            this.c.accept(this);
        }
    }

    public float o() {
        return this.e;
    }

    public void b(float f2) {
        this.e = f2;
        if (this.c != null) {
            this.c.accept(this);
        }
    }

    public float p() {
        return this.f;
    }

    public void c(float f2) {
        this.f = f2;
    }

    public float q() {
        return this.g;
    }

    public void d(float f2) {
        this.g = f2;
    }

    public void a(float f2, float f3) {
        this.d = f2;
        this.e = f3;
        if (this.c != null) {
            this.c.accept(this);
        }
    }
}

