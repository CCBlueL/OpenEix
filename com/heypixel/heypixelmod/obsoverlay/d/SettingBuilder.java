/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.d;

import com.heypixel.heypixelmod.obsoverlay.d.a.BooleanSetting;
import com.heypixel.heypixelmod.obsoverlay.d.a.FloatSetting;
import com.heypixel.heypixelmod.obsoverlay.d.a.NumericSetting;
import com.heypixel.heypixelmod.obsoverlay.d.a.ModeSetting;
import com.heypixel.heypixelmod.obsoverlay.d.a.StringSetting;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SettingBuilder {
    private final com.heypixel.heypixelmod.obsoverlay.d.a a;
    private final String b;
    private com.heypixel.heypixelmod.obsoverlay.d.f c;
    private Consumer<com.heypixel.heypixelmod.obsoverlay.d.c> d;
    private Supplier<Boolean> e;
    private boolean f;
    private float g;
    private float h;
    private float i;
    private float j;
    private String[] k;
    private int l;
    private String m;
    private float n;
    private float o;
    private int p;

    private SettingBuilder(com.heypixel.heypixelmod.obsoverlay.d.a a2, String string) {
        this.a = a2;
        this.b = string;
    }

    public static d a(com.heypixel.heypixelmod.obsoverlay.d.a a2, String string) {
        return new d(a2, string);
    }

    public d a(com.heypixel.heypixelmod.obsoverlay.d.f f2) {
        this.c = f2;
        return this;
    }

    public d a(boolean bl) {
        if (this.c == null) {
            this.a(com.heypixel.heypixelmod.obsoverlay.d.f.a);
        }
        if (this.c != com.heypixel.heypixelmod.obsoverlay.d.f.a) {
            throw new IllegalStateException("Value type is not boolean");
        }
        this.f = bl;
        return this;
    }

    public d a(float f2) {
        if (this.c == null) {
            this.a(com.heypixel.heypixelmod.obsoverlay.d.f.b);
        }
        if (this.c != com.heypixel.heypixelmod.obsoverlay.d.f.b) {
            throw new IllegalStateException("Value type is not float");
        }
        this.g = f2;
        return this;
    }

    public d b(float f2) {
        if (this.c == null) {
            this.a(com.heypixel.heypixelmod.obsoverlay.d.f.b);
        }
        if (this.c != com.heypixel.heypixelmod.obsoverlay.d.f.b) {
            throw new IllegalStateException("Value type is not float");
        }
        this.h = f2;
        return this;
    }

    public d c(float f2) {
        if (this.c == null) {
            this.a(com.heypixel.heypixelmod.obsoverlay.d.f.b);
        }
        if (this.c != com.heypixel.heypixelmod.obsoverlay.d.f.b) {
            throw new IllegalStateException("Value type is not float");
        }
        this.i = f2;
        return this;
    }

    public d d(float f2) {
        if (this.c == null) {
            this.a(com.heypixel.heypixelmod.obsoverlay.d.f.b);
        }
        if (this.c != com.heypixel.heypixelmod.obsoverlay.d.f.b) {
            throw new IllegalStateException("Value type is not float");
        }
        this.j = f2;
        return this;
    }

    public d a(String ... stringArray) {
        if (this.c == null) {
            this.a(com.heypixel.heypixelmod.obsoverlay.d.f.c);
        }
        if (this.c != com.heypixel.heypixelmod.obsoverlay.d.f.c) {
            throw new IllegalStateException("Value type is not mode");
        }
        this.k = stringArray;
        return this;
    }

    public d a(int n2) {
        if (this.c == null) {
            this.a(com.heypixel.heypixelmod.obsoverlay.d.f.c);
        }
        if (this.c != com.heypixel.heypixelmod.obsoverlay.d.f.c) {
            throw new IllegalStateException("Value type is not mode");
        }
        this.l = n2;
        return this;
    }

    public d a(String string) {
        if (this.c == null) {
            this.a(com.heypixel.heypixelmod.obsoverlay.d.f.d);
        }
        if (this.c != com.heypixel.heypixelmod.obsoverlay.d.f.d) {
            throw new IllegalStateException("Value type is not string");
        }
        this.m = string;
        return this;
    }

    public d e(float f2) {
        if (this.c == null) {
            this.a(com.heypixel.heypixelmod.obsoverlay.d.f.e);
        }
        if (this.c != com.heypixel.heypixelmod.obsoverlay.d.f.e) {
            throw new IllegalStateException("Value type is not drag");
        }
        this.n = f2;
        return this;
    }

    public d f(float f2) {
        if (this.c == null) {
            this.a(com.heypixel.heypixelmod.obsoverlay.d.f.e);
        }
        if (this.c != com.heypixel.heypixelmod.obsoverlay.d.f.e) {
            throw new IllegalStateException("Value type is not drag");
        }
        this.o = f2;
        return this;
    }

    public d b(int n2) {
        if (this.c == null) {
            this.a(com.heypixel.heypixelmod.obsoverlay.d.f.f);
        }
        if (this.c != com.heypixel.heypixelmod.obsoverlay.d.f.f) {
            throw new IllegalStateException("Value type is not key");
        }
        this.p = n2;
        return this;
    }

    public d a(Consumer<com.heypixel.heypixelmod.obsoverlay.d.c> consumer) {
        this.d = consumer;
        return this;
    }

    public d a(Supplier<Boolean> supplier) {
        this.e = supplier;
        return this;
    }

    public com.heypixel.heypixelmod.obsoverlay.d.c a() {
        if (this.c == null) {
            throw new IllegalStateException("Value type is not set");
        }
        switch (this.c) {
            case a: {
                return new a(this.a, this.b, this.f, this.d, this.e);
            }
            case b: {
                return new c(this.a, this.b, this.g, this.h, this.i, this.j, this.d, this.e);
            }
            case c: {
                if (this.k == null) {
                    throw new IllegalStateException("Modes are not set");
                }
                return new e(this.a, this.b, this.k, this.l, this.d, this.e);
            }
            case d: {
                if (this.m == null) {
                    throw new IllegalStateException("Default string value is not set");
                }
                return new f(this.a, this.b, this.m, this.d, this.e);
            }
            case e: {
                return new b(this.a, this.b, this.n, this.o, this.d, this.e);
            }
            case f: {
                return new com.heypixel.heypixelmod.obsoverlay.d.a.d(this.a, this.b, this.p, this.d, this.e);
            }
        }
        throw new IllegalStateException("Unknown value type");
    }
}

