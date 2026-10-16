/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.d;

import com.heypixel.heypixelmod.obsoverlay.d.SettingModel;
import com.heypixel.heypixelmod.obsoverlay.d.SettingValue;
import java.util.ArrayList;
import java.util.List;

public class SettingCollection {
    private final List<c> a = new ArrayList<c>();

    public void a(c c2) {
        this.a.add(c2);
    }

    public List<c> a(a a2) {
        ArrayList<c> arrayList = new ArrayList<c>();
        for (c c2 : this.a) {
            if (c2.i() != a2) continue;
            arrayList.add(c2);
        }
        return arrayList;
    }

    public c a(a a2, String string) {
        for (c c2 : this.a) {
            if (c2.i() != a2 || !c2.j().equals(string)) continue;
            return c2;
        }
        return null;
    }

    public List<c> a() {
        return this.a;
    }
}

