/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils.c;

import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.api.a.EventPriority;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventRunTicks;
import com.heypixel.heypixelmod.obsoverlay.events.impl.RelativeMovementVectorEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.UseItemRayTraceEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.LivingEntityPitchEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.LivingEntityYawEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerMotionEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.MovementInputEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.UseItemPacketEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EntityRotationEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerRespawnEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.LivingEntityRotationEvent;
import com.heypixel.heypixelmod.obsoverlay.utils.c.RotationVector;
import com.heypixel.heypixelmod.obsoverlay.utils.c.EntityRotationFactory;
import java.util.function.Function;
import lombok.Generated;
import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.util.Mth;

public class PlayerRotationController {
    private static final Minecraft f = Minecraft.m_91087_();
    public static RotationVector a = new RotationVector(0.0f, 0.0f);
    public static RotationVector b = new RotationVector(0.0f, 0.0f);
    public static RotationVector c = new RotationVector(0.0f, 0.0f);
    public static RotationVector d;
    public static RotationVector e;
    private static boolean g;
    private static boolean h;
    private static boolean i;
    private static boolean j;
    private static double k;
    private static Function<RotationVector, Boolean> l;
    private static final float m = 60.0f;
    private static Object n;
    private static float o;
    private static float p;

    public static Object a() {
        return n;
    }

    public static boolean a(Object object) {
        return g && n != null && n == object;
    }

    public static void a(boolean bl) {
        j = bl;
    }

    public static boolean b() {
        return j;
    }

    public static void c() {
        g = false;
        h = false;
        j = false;
        k = 0.0;
        l = null;
        n = null;
        if (com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.f.f_91074_ != null) {
            float f2 = com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.f.f_91074_.m_146908_();
            float f3 = com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.f.f_91074_.m_146909_();
            a = new RotationVector(f2, f3);
            b = new RotationVector(f2, f3);
            c = new RotationVector(f2, f3);
        }
        d = null;
        e = null;
        com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.e();
    }

    public static boolean b(Object object) {
        if (!com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.a(object)) {
            return false;
        }
        com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.c();
        return true;
    }

    public static void b(boolean bl) {
        if (!bl) {
            com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.c();
            return;
        }
        g = true;
    }

    public static void a(RotationVector b2, double d2) {
        com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.c(b2, d2, null);
    }

    public static void a(RotationVector b2, double d2, Function<RotationVector, Boolean> function) {
        com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.c(b2, d2, function);
    }

    public static void a(Object object, RotationVector b2, double d2) {
        com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.b(object, b2, d2, null);
    }

    public static void a(Object object, RotationVector b2, double d2, Function<RotationVector, Boolean> function) {
        com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.b(object, b2, d2, function);
    }

    public static void b(RotationVector b2, double d2) {
        j = true;
        com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.c(b2, d2, null);
    }

    public static void b(RotationVector b2, double d2, Function<RotationVector, Boolean> function) {
        j = true;
        com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.c(b2, d2, function);
    }

    private static void c(RotationVector b2, double d2, Function<RotationVector, Boolean> function) {
        com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.b(null, b2, d2, function);
    }

    private static void b(Object object, RotationVector b2, double d2, Function<RotationVector, Boolean> function) {
        if (com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.f.f_91074_ == null || i) {
            return;
        }
        if (b2 == null) {
            return;
        }
        if (a == null) {
            a = new RotationVector(com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.f.f_91074_.m_146908_(), com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.f.f_91074_.m_146909_());
        }
        if (b == null) {
            b = new RotationVector(a.a(), a.b());
        }
        if (c == null) {
            c = new RotationVector(b2.a(), b2.b());
        }
        c = b2;
        k = d2;
        l = function;
        g = true;
        if (object != null) {
            n = object;
        }
        com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.i();
        if (object != null) {
            com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.m();
        }
        com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.n();
    }

    public static void d() {
        com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.c();
        if (com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.f.f_91074_ == null) {
            a = new RotationVector(0.0f, 0.0f);
            b = new RotationVector(0.0f, 0.0f);
            c = new RotationVector(0.0f, 0.0f);
        }
    }

    public static void e() {
        o = Float.NaN;
        p = Float.NaN;
    }

    public static RotationVector a(float f2, float f3) {
        if (Float.isNaN(o) || Float.isNaN(p)) {
            o = f2;
            p = f3;
            return new RotationVector(f2, f3);
        }
        float f4 = com.heypixel.heypixelmod.obsoverlay.utils.SmoothValueInterpolator.c.a(o, f2);
        float f5 = com.heypixel.heypixelmod.obsoverlay.utils.SmoothValueInterpolator.c.b(p, f3);
        o = f4;
        p = f5;
        return new RotationVector(f4, f5);
    }

    public static void f() {
        i = true;
        com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.d();
    }

    public static void g() {
        i = false;
    }

    public static void h() {
        if (j) {
            return;
        }
        if (!g || a == null || com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.f.f_91074_ == null || f.m_91403_() == null) {
            return;
        }
        f.m_91403_().m_104955_((Packet)new ServerboundMovePlayerPacket.PosRot(com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.f.f_91074_.m_20185_(), com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.f.f_91074_.m_20186_(), com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.f.f_91074_.m_20189_(), a.a(), a.b(), com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.f.f_91074_.m_20096_()));
    }

    public static void i() {
        Function<RotationVector, Boolean> function;
        if (h || i) {
            return;
        }
        if (com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.f.f_91074_ == null) {
            h = true;
            return;
        }
        if (a == null) {
            a = new RotationVector(com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.f.f_91074_.m_146908_(), com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.f.f_91074_.m_146909_());
        }
        if (c == null) {
            c = new RotationVector(a.a(), a.b());
        }
        if ((function = l) != null && Boolean.TRUE.equals(function.apply(c))) {
            a = new RotationVector(c.a(), c.b());
            h = true;
            return;
        }
        double d2 = k * (1.0 + (Math.random() - 0.5) * 0.06);
        a = com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.d.b(new RotationVector(c.a(), c.b()), d2);
        h = true;
    }

    private static void m() {
        RotationVector b2 = a;
        if (b2 == null || com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.f.f_91074_ == null) {
            return;
        }
        float f2 = b2.a();
        float f3 = b2.b();
        if (Float.isNaN(f2) || Float.isNaN(f3) || Float.isInfinite(f2) || Float.isInfinite(f3)) {
            return;
        }
        float f4 = Float.isNaN(o) ? com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.f.f_91074_.m_146908_() : o;
        float f5 = Float.isNaN(p) ? com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.f.f_91074_.m_146909_() : p;
        double d2 = Mth.m_14177_((float)(f2 - f4));
        double d3 = f3 - f5;
        if (d2 == 0.0 && d3 == 0.0) {
            return;
        }
        double d4 = k <= 0.0 ? 60.0 : k;
        double d5 = Math.min(d4, 60.0);
        RotationVector b3 = new RotationVector(f4, f5);
        RotationVector b4 = com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.d.a(b3, new RotationVector(f2, f3), d5);
        a = new RotationVector(b3.a() + b4.a(), Mth.m_14036_((float)(b3.b() + b4.b()), (float)-90.0f, (float)90.0f));
    }

    private static void n() {
        RotationVector b2 = a;
        if (b2 == null) {
            return;
        }
        float f2 = b2.a();
        float f3 = b2.b();
        if (Float.isNaN(f2) || Float.isNaN(f3) || Float.isInfinite(f2) || Float.isInfinite(f3)) {
            return;
        }
        a = com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.a(f2, f3);
    }

    public static RotationVector j() {
        if (g && a != null) {
            return a;
        }
        if (com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.f.f_91074_ == null) {
            return new RotationVector(0.0f, 0.0f);
        }
        return new RotationVector(com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.f.f_91074_.m_146908_(), com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.f.f_91074_.m_146909_());
    }

    @EventTarget
    public void onRespawn(PlayerRespawnEvent u2) {
        com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.d();
    }

    @EventTarget(a=4)
    public void updateGlobalYaw(EventRunTicks eventRunTicks) {
        if (i) {
            return;
        }
        if (eventRunTicks.type() != com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.a || com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.f.f_91074_ == null) {
            return;
        }
        if (!g) {
            return;
        }
        if (a == null || b == null || c == null) {
            float f2 = com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.f.f_91074_.m_146908_();
            float f3 = com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.f.f_91074_.m_146909_();
            a = new RotationVector(f2, f3);
            b = new RotationVector(f2, f3);
            c = new RotationVector(f2, f3);
        }
    }

    @EventTarget
    public void onAnimation(LivingEntityRotationEvent v2) {
        if (i) {
            return;
        }
        if (g && d != null && e != null) {
            v2.a(d.a());
            v2.b(e.a());
            v2.c(d.b());
            v2.d(e.b());
        }
    }

    @EventTarget(a=4)
    public void onPre(PlayerMotionEvent k2) {
        if (i) {
            return;
        }
        if (k2.b() != com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.a) {
            return;
        }
        if (com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.f.f_91074_ == null) {
            return;
        }
        if (!g) {
            h = false;
            com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.e();
            return;
        }
        if (a != null) {
            com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.n();
            float f2 = a.a();
            float f3 = a.b();
            if (!Float.isNaN(f2) && !Float.isNaN(f3)) {
                k2.a(f2);
                k2.b(f3);
            }
            b = a;
        }
        e = d;
        d = new RotationVector(k2.f(), k2.g());
        h = false;
    }

    @EventTarget(a=1)
    public void onMove(MovementInputEvent l2) {
        if (i) {
            return;
        }
        if (!g || a == null || j) {
            return;
        }
    }

    @EventTarget
    public void onMove(EntityRotationEvent o2) {
        if (i) {
            return;
        }
        if (a != null && o2.a == com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.f.f_91074_ && g && !j) {
            o2.a(a.a());
            o2.b(a.b());
        }
    }

    @EventTarget
    public void onItemRayTrace(UseItemRayTraceEvent ag2) {
        if (i) {
            return;
        }
        if (a != null && g && !j) {
            ag2.a(a.a());
            ag2.b(a.b());
        }
    }

    @EventTarget
    public void onStrafe(RelativeMovementVectorEvent ab2) {
        if (i) {
            return;
        }
        if (g && a != null && !j) {
            ab2.a(a.a());
        }
    }

    @EventTarget
    public void onJump(LivingEntityYawEvent i2) {
        if (i) {
            return;
        }
        if (g && a != null && !j) {
            i2.a(a.a());
        }
    }

    @EventTarget(a=0)
    public void onPositionItem(UseItemPacketEvent n2) {
        if (i) {
            return;
        }
        if (!g || a == null) {
            return;
        }
        Object object = n2.b();
        if (object instanceof ServerboundMovePlayerPacket.PosRot) {
            ServerboundMovePlayerPacket.PosRot posRot = (ServerboundMovePlayerPacket.PosRot)object;
            object = com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.a(a.a(), a.b());
            ServerboundMovePlayerPacket.PosRot posRot2 = new ServerboundMovePlayerPacket.PosRot(posRot.m_134129_(0.0), posRot.m_134140_(0.0), posRot.m_134146_(0.0), ((RotationVector)object).a(), ((RotationVector)object).b(), posRot.m_134139_());
            n2.a((Packet<?>)posRot2);
        }
    }

    @EventTarget
    public void onFallFlying(LivingEntityPitchEvent f2) {
        if (i) {
            return;
        }
        if (g && a != null && !j) {
            f2.a(a.b());
        }
    }

    @EventTarget
    public void onAttack(com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerAttackRotationEvent c2) {
        if (i) {
            return;
        }
        if (g && a != null && !j) {
            c2.a(a.a());
        }
    }

    @Generated
    public static boolean k() {
        return g;
    }

    @Generated
    public static boolean l() {
        return h;
    }

    @Generated
    public static void c(boolean bl) {
        h = bl;
    }

    static {
        i = false;
        j = false;
        n = null;
        o = Float.NaN;
        p = Float.NaN;
    }
}

