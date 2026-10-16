/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils.a;

import com.heypixel.heypixelmod.obsoverlay.utils.c.VectorMath;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public final class EntityRaycastTargetingUtils {
    private static final Minecraft d = Minecraft.m_91087_();
    public static final double a = 3.0;
    public static final double b = 4.5;
    public static final double c = 0.06;

    private EntityRaycastTargetingUtils() {
    }

    public static double a(Entity entity) {
        if (com.heypixel.heypixelmod.obsoverlay.utils.SmoothValueInterpolator.b.d.f_91074_ == null || entity == null) {
            return Double.MAX_VALUE;
        }
        return com.heypixel.heypixelmod.obsoverlay.utils.SmoothValueInterpolator.b.a(com.heypixel.heypixelmod.obsoverlay.utils.SmoothValueInterpolator.b.d.f_91074_.m_146892_(), entity.m_20191_());
    }

    public static double a(Vec3 vec3, AABB aABB) {
        double d2 = Mth.m_14008_((double)vec3.f_82479_, (double)aABB.f_82288_, (double)aABB.f_82291_);
        double d3 = Mth.m_14008_((double)vec3.f_82480_, (double)aABB.f_82289_, (double)aABB.f_82292_);
        double d4 = Mth.m_14008_((double)vec3.f_82481_, (double)aABB.f_82290_, (double)aABB.f_82293_);
        return Math.sqrt(vec3.m_82531_(d2, d3, d4));
    }

    public static double a(Vec3 vec3, Vec3 vec32) {
        return vec3.m_82554_(vec32);
    }

    public static boolean a(Entity entity, com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.b b2, double d2) {
        EntityHitResult entityHitResult;
        double d3;
        if (com.heypixel.heypixelmod.obsoverlay.utils.SmoothValueInterpolator.b.d.f_91074_ == null || com.heypixel.heypixelmod.obsoverlay.utils.SmoothValueInterpolator.b.d.f_91073_ == null || entity == null || b2 == null) {
            return false;
        }
        double d4 = d3 = d2 <= 0.0 ? 3.0 : d2;
        if (com.heypixel.heypixelmod.obsoverlay.utils.SmoothValueInterpolator.b.a(entity) > d3 - 0.06) {
            return false;
        }
        HitResult hitResult = com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.a.a(b2, d3, 0.0f);
        return hitResult instanceof EntityHitResult && (entityHitResult = (EntityHitResult)hitResult).m_82443_() == entity;
    }

    public static boolean a(com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.b b2, BlockPos blockPos, Direction direction) {
        Vec3 vec3;
        Vec3 vec32;
        if (com.heypixel.heypixelmod.obsoverlay.utils.SmoothValueInterpolator.b.d.f_91074_ == null || com.heypixel.heypixelmod.obsoverlay.utils.SmoothValueInterpolator.b.d.f_91073_ == null || blockPos == null || b2 == null) {
            return false;
        }
        if (!com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.a.a(b2, direction, blockPos, true)) {
            return false;
        }
        Vec3 vec33 = com.heypixel.heypixelmod.obsoverlay.utils.SmoothValueInterpolator.b.d.f_91074_.m_146892_();
        double d2 = com.heypixel.heypixelmod.obsoverlay.utils.SmoothValueInterpolator.b.a(vec33, (vec32 = new Vec3((double)blockPos.m_123341_() + 0.5, (double)blockPos.m_123342_() + 0.5, (double)blockPos.m_123343_() + 0.5)).m_82549_(vec3 = new Vec3((double)direction.m_122429_() * 0.5, (double)direction.m_122430_() * 0.5, (double)direction.m_122431_() * 0.5)));
        return d2 <= 4.44;
    }

    public static boolean a(BlockPos blockPos, Direction direction) {
        Vec3 vec3;
        Vec3 vec32;
        if (com.heypixel.heypixelmod.obsoverlay.utils.SmoothValueInterpolator.b.d.f_91074_ == null) {
            return false;
        }
        Vec3 vec33 = com.heypixel.heypixelmod.obsoverlay.utils.SmoothValueInterpolator.b.d.f_91074_.m_146892_();
        return com.heypixel.heypixelmod.obsoverlay.utils.SmoothValueInterpolator.b.a(vec33, (vec32 = new Vec3((double)blockPos.m_123341_() + 0.5, (double)blockPos.m_123342_() + 0.5, (double)blockPos.m_123343_() + 0.5)).m_82549_(vec3 = new Vec3((double)direction.m_122429_() * 0.5, (double)direction.m_122430_() * 0.5, (double)direction.m_122431_() * 0.5))) <= 4.44;
    }
}

