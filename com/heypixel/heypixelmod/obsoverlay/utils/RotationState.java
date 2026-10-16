/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

public class RotationState {
    private final float d;
    private final float e;
    private final float f;
    private final Minecraft g = Minecraft.m_91087_();
    public double a;
    public double b;
    public double c;
    private double h;
    private double i;
    private double j;
    private float k;

    public RotationState(double d2, double d3, double d4, double d5, double d6, double d7, float f2, float f3, float f4) {
        this.a = d2;
        this.b = d3;
        this.c = d4;
        this.h = d5;
        this.i = d6;
        this.j = d7;
        this.d = f2;
        this.e = f3;
        this.f = f4;
    }

    public RotationState(Player player) {
        this(player.m_20185_(), player.m_20186_(), player.m_20189_(), player.m_20184_().f_82479_, player.m_20184_().f_82480_, player.m_20184_().f_82481_, player.m_146908_(), player.f_20900_, player.f_20902_);
        float f2;
        float f3 = player.m_9236_().m_8055_(player.m_20183_()).m_60734_().m_49964_();
        float f4 = player.m_9236_().m_8055_(player.m_20097_()).m_60734_().m_49964_();
        this.k = f2 = 0.42f * ((double)f3 == 1.0 ? f4 : f3) + player.m_285755_();
    }

    private void a() {
        float f2 = this.e;
        float f3 = this.f;
        float f4 = f2 * f2 + f3 * f3;
        if (f4 >= 1.0E-4f) {
            if ((f4 = Mth.m_14116_((float)f4)) < 1.0f) {
                f4 = 1.0f;
            }
            float f5 = this.k;
            if (this.g.f_91074_.m_20142_()) {
                f5 *= 1.3f;
            }
            f4 = f5 / f4;
            float f6 = Mth.m_14031_((float)(this.d * (float)Math.PI / 180.0f));
            float f7 = Mth.m_14089_((float)(this.d * (float)Math.PI / 180.0f));
            this.h += (double)((f2 *= f4) * f7 - (f3 *= f4) * f6);
            this.j += (double)(f3 * f7 + f2 * f6);
        }
        this.i -= 0.08;
        this.i *= (double)0.98f;
        this.a += this.h;
        this.b += this.i;
        this.c += this.j;
    }

    private void b() {
        float f2 = this.e * 0.98f;
        float f3 = this.f * 0.98f;
        float f4 = f2 * f2 + f3 * f3;
        if (f4 >= 1.0E-4f) {
            if ((f4 = Mth.m_14116_((float)f4)) < 1.0f) {
                f4 = 1.0f;
            }
            float f5 = this.k;
            if (this.g.f_91074_.m_20142_()) {
                f5 *= 1.3f;
            }
            f4 = f5 / f4;
            float f6 = Mth.m_14031_((float)(this.d * (float)Math.PI / 180.0f));
            float f7 = Mth.m_14089_((float)(this.d * (float)Math.PI / 180.0f));
            this.h += (double)((f2 *= f4) * f7 - (f3 *= f4) * f6);
            this.j += (double)(f3 * f7 + f2 * f6);
        }
        this.i -= 0.08;
        this.i *= (double)0.98f;
        this.a += this.h;
        this.b += this.i;
        this.c += this.j;
        this.h *= 0.91;
        this.j *= 0.91;
    }

    public void a(int n2) {
        for (int i2 = 0; i2 < n2; ++i2) {
            this.a();
        }
    }

    public void b(int n2) {
        for (int i2 = 0; i2 < n2; ++i2) {
            this.b();
        }
    }
}

