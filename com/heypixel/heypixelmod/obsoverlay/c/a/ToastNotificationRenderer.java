/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.c.a;

import com.heypixel.heypixelmod.obsoverlay.c.a.ToastNotification;
import com.heypixel.heypixelmod.obsoverlay.d.a.FloatSetting;
import com.heypixel.heypixelmod.obsoverlay.events.impl.CrosshairRenderEvent;
import com.heypixel.heypixelmod.obsoverlay.utils.RotationInterpolation;
import io.github.humbleui.skija.ClipMode;
import io.github.humbleui.skija.Path;
import io.github.humbleui.types.Rect;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ToastNotificationRenderer {
    private final List<ToastNotification> a = new CopyOnWriteArrayList<ToastNotification>();

    public void a(ToastNotification a2) {
        if (!this.a.contains(a2)) {
            this.a.add(a2);
        }
    }

    public void a(CrosshairRenderEvent s2, ToastSeverity b2) {
        float f2 = 5.0f;
        Path path = new Path();
        path.addRect(Rect.makeXYWH((float)b2.n(), (float)(b2.o() + 5.0f), (float)(b2.p() + 10.0f), (float)b2.q()));
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c();
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().clipPath(path, ClipMode.INTERSECT, true);
        for (ToastNotification a2 : this.a) {
            float f3 = a2.a();
            f2 += a2.b();
            RotationInterpolation ag2 = a2.g();
            RotationInterpolation ag3 = a2.h();
            float f4 = System.currentTimeMillis() - a2.f();
            if (f4 > (float)a2.e()) {
                ag2.a = 0.0f;
                ag3.a = 0.0f;
                if (ag2.b(true)) {
                    this.a.remove(a2);
                }
            } else {
                ag2.a = f3;
                ag3.a = f2;
            }
            ag2.a(true);
            ag3.a(true);
            b2.c(Math.max(f3, b2.p()));
            b2.d(f2);
            a2.a(b2.n() - a2.a() + ag2.c + 2.0f, b2.o() - a2.b() + ag3.c);
        }
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.d();
    }
}

