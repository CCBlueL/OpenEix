/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.d.a;

import com.heypixel.heypixelmod.obsoverlay.d.SettingModel;
import com.heypixel.heypixelmod.obsoverlay.d.SettingValue;
import com.heypixel.heypixelmod.obsoverlay.d.SettingValueType;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ModeSetting
extends SettingValue {
    private final String[] a;
    private final Consumer<SettingValue> b;
    private int c;

    public ModeSetting(SettingModel a2, String string, String[] stringArray, int n2, Consumer<SettingValue> consumer, Supplier<Boolean> supplier) {
        super(a2, string, supplier);
        this.b = consumer;
        this.a = stringArray;
        this.c = n2;
    }

    public boolean a(String string) {
        return this.l().equalsIgnoreCase(string);
    }

    @Override
    public SettingValueType a() {
        return SettingValueType.c;
    }

    @Override
    public ModeSetting e() {
        return this;
    }

    public String l() {
        return this.a[this.c];
    }

    public String[] m() {
        return this.a;
    }

    public Consumer<SettingValue> n() {
        return this.b;
    }

    public int o() {
        return this.c;
    }

    public void a(int n2) {
        this.c = n2;
        if (this.b != null) {
            this.b.accept(this);
        }
    }
}

