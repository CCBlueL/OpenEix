/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils.c;

public class RotationVector {
    public float a;
    public float b;

    public RotationVector(float f2, float f3) {
        this.a = f2;
        this.b = f3;
    }

    public RotationVector(com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.b b2) {
        this.a = b2.a;
        this.b = b2.b;
    }

    public float a() {
        return this.a;
    }

    public void a(float f2) {
        this.a = f2;
    }

    public float b() {
        return this.b;
    }

    public void b(float f2) {
        this.b = f2;
    }
}

