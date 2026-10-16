/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.render;

import com.heypixel.heypixelmod.obsoverlay.d.a.NumericSetting;
import com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.CrosshairRenderEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

@ModuleInfo(a="Scoreboard", b="\u8ba1\u5206\u677f", c="Modifies the scoreboard", d=com.heypixel.heypixelmod.obsoverlay.modules.a.RENDER)
public class Scoreboard
extends b {
    public com.heypixel.heypixelmod.obsoverlay.d.a.a c = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Hide Red Score").a(true).a().b();
    public com.heypixel.heypixelmod.obsoverlay.d.a.a d = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Font").a(false).a().b();
    public c e = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "X Offset").a(0.0f).d(1.0f).b(-300.0f).c(300.0f).a().c();
    public c f = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Down").a(120.0f).d(1.0f).b(0.0f).c(300.0f).a().c();
    private float h = Float.MAX_VALUE;
    private float i = Float.MAX_VALUE;
    private float j = Float.MIN_VALUE;
    private float k = Float.MIN_VALUE;
    private float l = Float.MAX_VALUE;
    private float m = Float.MAX_VALUE;
    private float n = Float.MIN_VALUE;
    private float o = Float.MIN_VALUE;
    private boolean p = false;
    private boolean q = false;
    private int r = 0;
    private int s = -1;
    private boolean t = false;
    private boolean u = false;
    private float v = 0.0f;
    private float w = 0.0f;
    private boolean x = false;
    private float y = 0.0f;
    private float z = 0.0f;
    private final List<a> A = new ArrayList<a>();
    public com.heypixel.heypixelmod.obsoverlay.d.a.b g = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Position").e(0.0f).f(120.0f).a((com.heypixel.heypixelmod.obsoverlay.d.c c2) -> {
        if (this.t) {
            return;
        }
        com.heypixel.heypixelmod.obsoverlay.d.a.b b2 = c2.f();
        if (!this.q) {
            if (this.x) {
                this.t = true;
                this.e.a(b2.n() - this.y);
                this.f.a(b2.o() - this.z);
                this.t = false;
                this.u = false;
            } else {
                this.v = b2.n();
                this.w = b2.o();
                this.u = true;
            }
            return;
        }
        this.t = true;
        this.e.a(b2.n() - this.l);
        this.f.a(b2.o() - this.m);
        this.t = false;
    }).a().f();

    public void p() {
        ++this.r;
        this.h = Float.MAX_VALUE;
        this.i = Float.MAX_VALUE;
        this.j = Float.MIN_VALUE;
        this.k = Float.MIN_VALUE;
        this.l = Float.MAX_VALUE;
        this.m = Float.MAX_VALUE;
        this.n = Float.MIN_VALUE;
        this.o = Float.MIN_VALUE;
        this.p = false;
        this.q = false;
        this.A.clear();
        if (!this.u) {
            this.q();
        }
    }

    private void r() {
        if (!this.u || !this.q) {
            return;
        }
        float f2 = this.e.q();
        float f3 = this.f.q();
        float f4 = this.v - this.l;
        float f5 = this.w - this.m;
        float f6 = f4 - f2;
        float f7 = f5 - f3;
        this.t = true;
        this.e.a(f4);
        this.f.a(f5);
        this.t = false;
        if (this.p) {
            this.h += f6;
            this.j += f6;
            this.i += f7;
            this.k += f7;
        }
        this.u = false;
    }

    public void q() {
        if (this.t) {
            return;
        }
        float f2 = this.e.q();
        float f3 = this.f.q();
        if (this.q) {
            f2 = this.l + f2;
            f3 = this.m + f3;
        } else if (this.x) {
            f2 = this.y + f2;
            f3 = this.z + f3;
        }
        if (this.g.n() != f2 || this.g.o() != f3) {
            this.t = true;
            this.g.a(f2, f3);
            this.t = false;
        }
    }

    public void a(float f2, float f3, int n2, int n3) {
        if (!this.m()) {
            return;
        }
        float f4 = this.e.q();
        float f5 = this.f.q();
        float f6 = (float)n2 - 2.0f;
        float f7 = (float)n3 - 2.0f;
        float f8 = (float)n2 + f2 + 2.0f;
        float f9 = (float)n3 + f3 + 2.0f;
        float f10 = f6 + f4;
        float f11 = f7 + f5;
        float f12 = f8 + f4;
        float f13 = f9 + f5;
        this.l = Math.min(this.l, f6);
        this.m = Math.min(this.m, f7);
        this.n = Math.max(this.n, f8);
        this.o = Math.max(this.o, f9);
        this.q = true;
        this.x = true;
        this.y = this.l;
        this.z = this.m;
        this.h = Math.min(this.h, f10);
        this.i = Math.min(this.i, f11);
        this.j = Math.max(this.j, f12);
        this.k = Math.max(this.k, f13);
        this.p = true;
        float f14 = this.j - this.h;
        float f15 = this.k - this.i;
        if (f14 > 0.0f && f15 > 0.0f) {
            this.g.c(f14);
            this.g.d(f15);
        }
        if (f14 > 0.0f && f15 > 0.0f) {
            this.t = true;
            this.g.a(this.h, this.i);
            this.t = false;
        }
    }

    public void a(String string, int n2, int n3, int n4, float f2) {
        if (!this.m()) {
            return;
        }
        this.A.add(new a(string, n2, n3, n4, f2));
    }

    public void a(int n2, int n3, int n4, int n5) {
        if (!this.m()) {
            return;
        }
        float f2 = this.e.q();
        float f3 = this.f.q();
        float f4 = (float)Math.min(n2, n4) + f2;
        float f5 = (float)Math.min(n3, n5) + f3;
        float f6 = (float)Math.max(n2, n4) + f2;
        float f7 = (float)Math.max(n3, n5) + f3;
        float f8 = Math.min(n2, n4);
        float f9 = Math.min(n3, n5);
        float f10 = Math.max(n2, n4);
        float f11 = Math.max(n3, n5);
        this.l = Math.min(this.l, f8);
        this.m = Math.min(this.m, f9);
        this.n = Math.max(this.n, f10);
        this.o = Math.max(this.o, f11);
        this.q = true;
        this.x = true;
        this.y = this.l;
        this.z = this.m;
        this.h = Math.min(this.h, f4);
        this.i = Math.min(this.i, f5);
        this.j = Math.max(this.j, f6);
        this.k = Math.max(this.k, f7);
        this.p = true;
        float f12 = this.j - this.h;
        float f13 = this.k - this.i;
        if (f12 > 0.0f && f13 > 0.0f) {
            this.g.c(f12);
            this.g.d(f13);
        }
        if (f12 > 0.0f && f13 > 0.0f) {
            this.t = true;
            this.g.a(this.h, this.i);
            this.t = false;
        }
    }

    @EventTarget
    public void onRenderSkia(s s2) {
        if (!this.m()) {
            return;
        }
        if (!this.p || this.s == this.r) {
            return;
        }
        this.r();
        float f2 = this.j - this.h;
        float f3 = this.k - this.i;
        if (f2 <= 0.0f || f3 <= 0.0f) {
            return;
        }
        com.heypixel.heypixelmod.obsoverlay.utils.e.a.c(this.h, this.i, f2, f3, 5.0f);
        com.heypixel.heypixelmod.obsoverlay.utils.e.a.a(this.h, this.i, f2, f3, 5.0f);
        com.heypixel.heypixelmod.obsoverlay.utils.e.a.a(this.h, this.i, f2, f3, 5.0f, new Color(0, 0, 0, 60));
        if (this.d.m()) {
            float f4 = this.e.q();
            float f5 = this.f.q();
            for (a a2 : this.A) {
                com.heypixel.heypixelmod.obsoverlay.utils.e.a.a(a2.a, (float)a2.b + f4, (float)a2.c + f5, new Color(a2.d, true), com.heypixel.heypixelmod.obsoverlay.utils.e.b.c.a(a2.e));
            }
        }
        this.s = this.r;
    }

    private static class a {
        private final String a;
        private final int b;
        private final int c;
        private final int d;
        private final float e;

        private a(String string, int n2, int n3, int n4, float f2) {
            this.a = string;
            this.b = n2;
            this.c = n3;
            this.d = n4;
            this.e = f2;
        }
    }
}

