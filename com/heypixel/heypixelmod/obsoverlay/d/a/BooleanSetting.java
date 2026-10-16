/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.d.a;

import com.heypixel.heypixelmod.obsoverlay.d.SettingValue;
import com.heypixel.heypixelmod.obsoverlay.d.SettingValueType;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class BooleanSetting
extends SettingValue {
    private final boolean a;
    private final Consumer<SettingValue> b;
    private boolean c;

    public BooleanSetting(com.heypixel.heypixelmod.obsoverlay.d.SettingModel a2, String string, boolean bl, Consumer<SettingValue> consumer, Supplier<Boolean> supplier) {
        super(a2, string, supplier);
        this.b = consumer;
        this.c = this.a = bl;
    }

    @Override
    public SettingValueType a() {
        return SettingValueType.a;
    }

    @Override
    public BooleanSetting b() {
        return this;
    }

    public boolean l() {
        return this.a;
    }

    public boolean m() {
        return this.c;
    }

    public void a(boolean bl) {
        this.c = bl;
        if (this.b != null) {
            this.b.accept(this);
        }
    }
}

