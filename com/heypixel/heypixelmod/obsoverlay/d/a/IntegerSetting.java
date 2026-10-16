/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.d.a;

import com.heypixel.heypixelmod.obsoverlay.d.SettingModel;
import com.heypixel.heypixelmod.obsoverlay.d.SettingValue;
import com.heypixel.heypixelmod.obsoverlay.d.SettingValueType;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class IntegerSetting
extends SettingValue {
    private final int a;
    private final Consumer<SettingValue> b;
    private int c;

    public IntegerSetting(SettingModel a2, String string, int n2, Consumer<SettingValue> consumer, Supplier<Boolean> supplier) {
        super(a2, string, supplier);
        this.a = n2;
        this.c = n2;
        this.b = consumer;
    }

    @Override
    public SettingValueType a() {
        return SettingValueType.f;
    }

    @Override
    public IntegerSetting g() {
        return this;
    }

    public int l() {
        return this.a;
    }

    public int m() {
        return this.c;
    }

    public void a(int n2) {
        this.c = n2;
        if (this.b != null) {
            this.b.accept(this);
        }
    }
}

