/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils;

public class ElapsedTimeTimer {
    private long a = 0L;

    public void a() {
        this.a = System.currentTimeMillis();
    }

    public boolean a(long l2) {
        return System.currentTimeMillis() - this.a >= l2;
    }

    public boolean a(float f2, boolean bl) {
        if ((float)(System.currentTimeMillis() - this.a) >= f2) {
            if (bl) {
                this.a();
            }
            return true;
        }
        return false;
    }

    public boolean a(double d2) {
        return (double)(System.currentTimeMillis() - this.a) >= d2;
    }

    public long b() {
        return System.currentTimeMillis() - this.a;
    }
}

