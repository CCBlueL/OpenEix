/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.d.a;

import com.heypixel.heypixelmod.obsoverlay.d.SettingModel;
import com.heypixel.heypixelmod.obsoverlay.d.SettingValue;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class StringSetting
extends SettingValue {
    private final String a;
    private final Consumer<SettingValue> b;
    private String c;

    public StringSetting(SettingModel a2, String string, String string2, Consumer<SettingValue> consumer, Supplier<Boolean> supplier) {
        super(a2, string, supplier);
        this.b = consumer;
        this.a = string2;
        this.c = string2;
    }

    @Override
    public com.heypixel.heypixelmod.obsoverlay.d.SettingValueType a() {
        return com.heypixel.heypixelmod.obsoverlay.d.SettingValueType.d;
    }

    @Override
    public StringSetting d() {
        return this;
    }

    public String l() {
        return this.a;
    }

    public Consumer<SettingValue> m() {
        return this.b;
    }

    public String n() {
        return this.c;
    }

    public void a(String string) {
        if (string == null) {
            return;
        }
        this.c = string;
        if (this.b != null) {
            this.b.accept(this);
        }
    }
}

