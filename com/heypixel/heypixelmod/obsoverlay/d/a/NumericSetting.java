/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.d.a;

import com.heypixel.heypixelmod.obsoverlay.d.SettingModel;
import com.heypixel.heypixelmod.obsoverlay.d.SettingValueType;
import com.heypixel.heypixelmod.obsoverlay.utils.MathUtils;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class NumericSetting
extends com.heypixel.heypixelmod.obsoverlay.d.SettingValue {
    private final float a;
    private final float b;
    private final float c;
    private final float d;
    private final Consumer<com.heypixel.heypixelmod.obsoverlay.d.SettingValue> e;
    private float f;

    public NumericSetting(SettingModel a2, String string, float f2, float f3, float f4, float f5, Consumer<com.heypixel.heypixelmod.obsoverlay.d.SettingValue> consumer, Supplier<Boolean> supplier) {
        super(a2, string, supplier);
        this.e = consumer;
        this.f = this.a = f2;
        this.b = f3;
        this.c = f4;
        this.d = f5;
    }

    @Override
    public SettingValueType a() {
        return com.heypixel.heypixelmod.obsoverlay.d.SettingValueType.b;
    }

    @Override
    public NumericSetting c() {
        return this;
    }

    public float l() {
        return this.a;
    }

    public float m() {
        return this.b;
    }

    public float n() {
        return this.c;
    }

    public float o() {
        return this.d;
    }

    public Consumer<com.heypixel.heypixelmod.obsoverlay.d.SettingValue> p() {
        return this.e;
    }

    public float q() {
        return this.f;
    }

    public void a(float f2) {
        this.f = MathUtils.b(f2, this.b, this.c);
        if (this.e != null) {
            this.e.accept(this);
        }
    }
}

