/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils.f;

import com.heypixel.heypixelmod.obsoverlay.utils.f.Vector3d;
import lombok.Generated;

public class IntVector3 {
    public int a;
    public int b;
    public int c;

    public IntVector3(int n2, int n3, int n4) {
        this.a = n2;
        this.b = n3;
        this.c = n4;
    }

    public IntVector3 a(int n2, int n3, int n4) {
        return new IntVector3(this.a + n2, this.b + n3, this.c + n4);
    }

    public IntVector3 a(IntVector3 e2) {
        return this.a(e2.a, e2.b, e2.c);
    }

    public IntVector3 b(int n2, int n3, int n4) {
        return this.a(-n2, -n3, -n4);
    }

    public IntVector3 b(IntVector3 e2) {
        return this.a(-e2.a, -e2.b, -e2.c);
    }

    public double a() {
        return Math.sqrt(this.a * this.a + this.b * this.b + this.c * this.c);
    }

    public int b() {
        return this.a;
    }

    public int c() {
        return this.b;
    }

    public int d() {
        return this.c;
    }

    public Vector3d a(double d2) {
        return new Vector3d((double)this.a * d2, (double)this.b * d2, (double)this.c * d2);
    }

    public double c(IntVector3 e2) {
        return Math.sqrt(Math.pow(e2.a - this.a, 2.0) + Math.pow(e2.b - this.b, 2.0) + Math.pow(e2.c - this.c, 2.0));
    }

    public boolean equals(Object object) {
        if (!(object instanceof Vector3d)) {
            return false;
        }
        Vector3d d2 = (Vector3d)object;
        return (double)this.a == Math.floor(d2.a) && (double)this.b == Math.floor(d2.b) && (double)this.c == Math.floor(d2.c);
    }

    @Generated
    public void a(int n2) {
        this.a = n2;
    }

    @Generated
    public void b(int n2) {
        this.b = n2;
    }

    @Generated
    public void c(int n2) {
        this.c = n2;
    }
}

