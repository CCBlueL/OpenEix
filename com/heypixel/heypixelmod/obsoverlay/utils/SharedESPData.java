/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils;

import java.util.Arrays;
import java.util.Objects;

public class SharedESPData {
    public String a;
    public double b;
    public double c;
    public double d;
    public double e;
    public double f;
    public double g;
    public double[] h;
    public String[] i;
    public long j;

    public SharedESPData(String string, double d2, double d3, double d4, double d5, double d6, double d7, double[] dArray, String[] stringArray, long l2) {
        this.a = string;
        this.b = d2;
        this.c = d3;
        this.d = d4;
        this.e = d5;
        this.f = d6;
        this.g = d7;
        this.h = dArray;
        this.i = stringArray;
        this.j = l2;
    }

    public String a() {
        return this.a;
    }

    public void a(String string) {
        this.a = string;
    }

    public double b() {
        return this.b;
    }

    public void a(double d2) {
        this.b = d2;
    }

    public double c() {
        return this.c;
    }

    public void b(double d2) {
        this.c = d2;
    }

    public double d() {
        return this.d;
    }

    public void c(double d2) {
        this.d = d2;
    }

    public double e() {
        return this.e;
    }

    public void d(double d2) {
        this.e = d2;
    }

    public double f() {
        return this.f;
    }

    public void e(double d2) {
        this.f = d2;
    }

    public double g() {
        return this.g;
    }

    public void f(double d2) {
        this.g = d2;
    }

    public double[] h() {
        return this.h;
    }

    public void a(double[] dArray) {
        this.h = dArray;
    }

    public String[] i() {
        return this.i;
    }

    public void a(String[] stringArray) {
        this.i = stringArray;
    }

    public long j() {
        return this.j;
    }

    public void a(long l2) {
        this.j = l2;
    }

    public boolean equals(Object object) {
        String string;
        if (object == this) {
            return true;
        }
        if (!(object instanceof SharedESPData)) {
            return false;
        }
        SharedESPData af2 = (SharedESPData)object;
        if (!af2.a(this)) {
            return false;
        }
        if (Double.compare(this.b(), af2.b()) != 0) {
            return false;
        }
        if (Double.compare(this.c(), af2.c()) != 0) {
            return false;
        }
        if (Double.compare(this.d(), af2.d()) != 0) {
            return false;
        }
        if (Double.compare(this.e(), af2.e()) != 0) {
            return false;
        }
        if (Double.compare(this.f(), af2.f()) != 0) {
            return false;
        }
        if (Double.compare(this.g(), af2.g()) != 0) {
            return false;
        }
        if (this.j() != af2.j()) {
            return false;
        }
        String string2 = this.a();
        if (Objects.equals(string2, string = af2.a())) {
            return Arrays.equals(this.h(), af2.h()) && Arrays.deepEquals(this.i(), af2.i());
        }
        return false;
    }

    protected boolean a(Object object) {
        return object instanceof SharedESPData;
    }

    public int hashCode() {
        int n2 = 59;
        int n3 = 1;
        long l2 = Double.doubleToLongBits(this.b());
        n3 = n3 * 59 + (int)(l2 >>> 32 ^ l2);
        long l3 = Double.doubleToLongBits(this.c());
        n3 = n3 * 59 + (int)(l3 >>> 32 ^ l3);
        long l4 = Double.doubleToLongBits(this.d());
        n3 = n3 * 59 + (int)(l4 >>> 32 ^ l4);
        long l5 = Double.doubleToLongBits(this.e());
        n3 = n3 * 59 + (int)(l5 >>> 32 ^ l5);
        long l6 = Double.doubleToLongBits(this.f());
        n3 = n3 * 59 + (int)(l6 >>> 32 ^ l6);
        long l7 = Double.doubleToLongBits(this.g());
        n3 = n3 * 59 + (int)(l7 >>> 32 ^ l7);
        long l8 = this.j();
        n3 = n3 * 59 + (int)(l8 >>> 32 ^ l8);
        String string = this.a();
        n3 = n3 * 59 + (string == null ? 43 : string.hashCode());
        n3 = n3 * 59 + Arrays.hashCode(this.h());
        return n3 * 59 + Arrays.deepHashCode(this.i());
    }

    public String toString() {
        return "SharedESPData(displayName=" + this.a() + ", posX=" + this.b() + ", posY=" + this.c() + ", posZ=" + this.d() + ", health=" + this.e() + ", maxHealth=" + this.f() + ", absorption=" + this.g() + ", renderPosition=" + Arrays.toString(this.h()) + ", tags=" + Arrays.deepToString(this.i()) + ", updateTime=" + this.j() + ")";
    }
}

