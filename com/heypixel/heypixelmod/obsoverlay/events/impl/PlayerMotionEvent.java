/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.events.impl;

import com.heypixel.heypixelmod.obsoverlay.events.api.events.a.CancellableEventBase;

public class PlayerMotionEvent
extends MovementInputState {
    private final com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a b;
    private double c;
    private double d;
    private double e;
    private float f;
    private float g;
    private boolean h;

    public PlayerMotionEvent(com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a a2, float f2, float f3) {
        this.b = a2;
        this.f = f2;
        this.g = f3;
    }

    public PlayerMotionEvent(com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a a2, double d2, double d3, double d4, float f2, float f3, boolean bl) {
        this.b = a2;
        this.c = d2;
        this.d = d3;
        this.e = d4;
        this.f = f2;
        this.g = f3;
        this.h = bl;
    }

    public com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a b() {
        return this.b;
    }

    public double c() {
        return this.c;
    }

    public void a(double d2) {
        this.c = d2;
    }

    public double d() {
        return this.d;
    }

    public void b(double d2) {
        this.d = d2;
    }

    public double e() {
        return this.e;
    }

    public void c(double d2) {
        this.e = d2;
    }

    public float f() {
        return this.f;
    }

    public void a(float f2) {
        this.f = f2;
    }

    public float g() {
        return this.g;
    }

    public void b(float f2) {
        this.g = f2;
    }

    public boolean h() {
        return this.h;
    }

    public void b(boolean bl) {
        this.h = bl;
    }
}

