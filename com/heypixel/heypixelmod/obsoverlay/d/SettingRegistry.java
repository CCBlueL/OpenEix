/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.d;

import com.heypixel.heypixelmod.obsoverlay.d.SettingModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingRegistry {
    private final List<a> a = new ArrayList<a>();
    private final Map<String, a> b = new HashMap<String, a>();

    public void a(a a2) {
        this.a.add(a2);
        this.b.put(a2.h().toLowerCase(), a2);
    }

    public a a(String string) {
        return this.b.get(string.toLowerCase());
    }

    public List<a> a() {
        return this.a;
    }
}

