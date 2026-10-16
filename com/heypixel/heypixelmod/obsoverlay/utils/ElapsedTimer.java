/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils;

import net.minecraft.util.Mth;

public final class ElapsedTimer {
    private long a = 0L;
    private long b = -1L;

    public boolean a(long l2) {
        if (this.b() >= l2) {
            this.a();
            return true;
        }
        return false;
    }

    public boolean a(float f2) {
        return (float)(System.currentTimeMillis() - this.b) >= f2;
    }

    public boolean a(double d2) {
        return this.a(d2, false);
    }

    public boolean a(double d2, boolean bl) {
        boolean bl2;
        boolean bl3 = bl2 = (double)Mth.m_14036_((float)(this.c() - this.a), (float)0.0f, (float)((float)d2)) >= d2;
        if (bl2 && bl) {
            this.a();
        }
        return bl2;
    }

    public void a() {
        this.b = System.currentTimeMillis();
        this.a = this.c();
    }

    public void b(long l2) {
        this.b = System.currentTimeMillis();
        this.a = this.c() + l2;
    }

    public long b() {
        return System.nanoTime() / 1000000L - this.a;
    }

    public long c() {
        return System.nanoTime() / 1000000L;
    }

    public double d() {
        return this.c() - this.e();
    }

    public long e() {
        return this.a;
    }
}

