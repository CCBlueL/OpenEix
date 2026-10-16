/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils.f;

import lombok.Generated;

public class Vector3d {
    public double a;
    public double b;
    public double c;

    public Vector3d(double d2, double d3, double d4) {
        this.a = d2;
        this.b = d3;
        this.c = d4;
    }

    public Vector3d a(double d2, double d3, double d4) {
        return new Vector3d(this.a + d2, this.b + d3, this.c + d4);
    }

    public Vector3d a(Vector3d d2) {
        return this.a(d2.a, d2.b, d2.c);
    }

    public Vector3d b(double d2, double d3, double d4) {
        return this.a(-d2, -d3, -d4);
    }

    public Vector3d b(Vector3d d2) {
        return this.a(-d2.a, -d2.b, -d2.c);
    }

    public double a() {
        return Math.sqrt(this.a * this.a + this.b * this.b + this.c * this.c);
    }

    public double b() {
        return this.a;
    }

    public double c() {
        return this.b;
    }

    public double d() {
        return this.c;
    }

    public Vector3d a(double d2) {
        return new Vector3d(this.a * d2, this.b * d2, this.c * d2);
    }

    public double c(Vector3d d2) {
        return Math.sqrt(Math.pow(d2.a - this.a, 2.0) + Math.pow(d2.b - this.b, 2.0) + Math.pow(d2.c - this.c, 2.0));
    }

    public boolean equals(Object object) {
        if (!(object instanceof Vector3d)) {
            return false;
        }
        Vector3d d2 = (Vector3d)object;
        return Math.floor(this.a) == Math.floor(d2.a) && Math.floor(this.b) == Math.floor(d2.b) && Math.floor(this.c) == Math.floor(d2.c);
    }

    @Generated
    public void b(double d2) {
        this.a = d2;
    }

    @Generated
    public void c(double d2) {
        this.b = d2;
    }

    @Generated
    public void d(double d2) {
        this.c = d2;
    }
}

