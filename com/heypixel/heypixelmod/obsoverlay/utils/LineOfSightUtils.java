/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils;

import com.heypixel.heypixelmod.obsoverlay.utils.f.Vector2f;
import net.minecraft.client.Minecraft;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class LineOfSightUtils {
    private static final Minecraft a = Minecraft.m_91087_();

    public static boolean a() {
        return LineOfSightUtils.a.f_91066_.f_92085_.m_90857_() || LineOfSightUtils.a.f_91066_.f_92087_.m_90857_() || LineOfSightUtils.a.f_91066_.f_92086_.m_90857_() || LineOfSightUtils.a.f_91066_.f_92088_.m_90857_();
    }

    public static int b() {
        return LineOfSightUtils.a.f_91074_.m_21023_(MobEffects.f_19596_) ? LineOfSightUtils.a.f_91074_.m_21124_(MobEffects.f_19596_).m_19564_() + 1 : 0;
    }

    public static Vec3 a(EntityTargetList b2) {
        float f2 = (float)Math.cos(-b2.a() * ((float)Math.PI / 180) - (float)Math.PI);
        float f3 = (float)Math.sin(-b2.a() * ((float)Math.PI / 180) - (float)Math.PI);
        float f4 = (float)(-Math.cos(-b2.b() * ((float)Math.PI / 180)));
        float f5 = (float)Math.sin(-b2.b() * ((float)Math.PI / 180));
        return new Vec3((double)(f3 * f4), (double)f5, (double)(f2 * f4));
    }

    public static HitResult a(double d2, float f2, float f3) {
        if (LineOfSightUtils.a.f_91074_ != null && LineOfSightUtils.a.f_91073_ != null) {
            Vec3 vec3 = LineOfSightUtils.a.f_91074_.m_20299_(1.0f);
            Vec3 vec32 = LineOfSightUtils.a(new EntityTargetList(f2, f3));
            Vec3 vec33 = vec3.m_82520_(vec32.f_82479_ * d2, vec32.f_82480_ * d2, vec32.f_82481_ * d2);
            return LineOfSightUtils.a.f_91073_.m_45547_(new ClipContext(vec3, vec33, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, (Entity)LineOfSightUtils.a.f_91074_));
        }
        return null;
    }
}

