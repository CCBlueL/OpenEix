/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.c.a;

import com.heypixel.heypixelmod.obsoverlay.c.a.ToastSeverity;
import com.heypixel.heypixelmod.obsoverlay.utils.RotationInterpolation;
import com.heypixel.heypixelmod.obsoverlay.utils.e.b.BundledFontRegistry;
import java.awt.Color;
import java.util.Objects;

public class ToastNotification {
    public static byte[] a;
    private ToastSeverity b;
    private String c;
    private long d;
    private long e = System.currentTimeMillis();
    private RotationInterpolation f = new RotationInterpolation(0.0f);
    private RotationInterpolation g = new RotationInterpolation(0.0f);

    public ToastNotification(ToastSeverity b2, String string, long l2) {
        this.b = b2;
        this.c = string;
        this.d = l2;
    }

    public void a(float f2, float f3) {
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.b(f2 + 2.0f, f3 + 4.0f, this.a(), 20.0f, 5.0f, this.b.a());
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f2 + 2.0f, f3 + 4.0f, this.a(), 20.0f, 5.0f);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f2 + 2.0f, f3 + 4.0f, this.a(), 20.0f, 5.0f, new Color(0, 0, 0, 100));
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(this.c, f2 + 6.0f, f3 + 9.0f, Color.WHITE, com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.c.a(12.0f));
    }

    public float a() {
        float f2 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(this.c, com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.c.a(12.0f));
        return f2 + 12.0f;
    }

    public float b() {
        return 24.0f;
    }

    public ToastSeverity c() {
        return this.b;
    }

    public void a(ToastSeverity b2) {
        this.b = b2;
    }

    public String d() {
        return this.c;
    }

    public void a(String string) {
        this.c = string;
    }

    public long e() {
        return this.d;
    }

    public void a(long l2) {
        this.d = l2;
    }

    public long f() {
        return this.e;
    }

    public void b(long l2) {
        this.e = l2;
    }

    public RotationInterpolation g() {
        return this.f;
    }

    public void a(RotationInterpolation ag2) {
        this.f = ag2;
    }

    public RotationInterpolation h() {
        return this.g;
    }

    public void b(RotationInterpolation ag2) {
        this.g = ag2;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof ToastNotification)) {
            return false;
        }
        ToastNotification a2 = (ToastNotification)object;
        return this.d == a2.d && this.e == a2.e && Objects.equals((Object)this.b, (Object)a2.b) && Objects.equals(this.c, a2.c);
    }

    protected boolean a(Object object) {
        return object instanceof ToastNotification;
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.b, this.c, this.d, this.e});
    }

    public String toString() {
        return "Notification(level=" + String.valueOf((Object)this.c()) + ", message=" + this.d() + ", maxAge=" + this.e() + ", createTime=" + this.f() + ", widthTimer=" + String.valueOf(this.g()) + ", heightTimer=" + String.valueOf(this.h()) + ")";
    }
}

