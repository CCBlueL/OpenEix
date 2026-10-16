/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils;

import java.util.ArrayList;
import java.util.List;

public class EntityTrackingList {
    private static final List<EntityTrackingList> a = new ArrayList<EntityTrackingList>();
    private int b = 0;

    public EntityTrackingList() {
        a.add(this);
    }

    public static void a() {
        for (EntityTrackingList ak2 : a) {
            ++ak2.b;
        }
    }

    public boolean a(int n2) {
        return this.b >= n2;
    }

    public boolean a(float f2) {
        return (float)this.b >= f2;
    }

    public void b() {
        this.b = 0;
    }
}

