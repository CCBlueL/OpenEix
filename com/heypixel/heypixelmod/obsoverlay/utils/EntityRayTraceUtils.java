/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils;

import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class EntityRayTraceUtils {
    private static final Minecraft a = Minecraft.m_91087_();

    public static Vec3 a(float f2, float f3) {
        float f4 = f2 * ((float)Math.PI / 180);
        float f5 = -f3 * ((float)Math.PI / 180);
        float f6 = Mth.m_14089_((float)f5);
        float f7 = Mth.m_14031_((float)f5);
        float f8 = Mth.m_14089_((float)f4);
        float f9 = Mth.m_14031_((float)f4);
        return new Vec3((double)(f7 * f8), (double)(-f9), (double)(f6 * f8));
    }

    public static HitResult a(double d2, boolean bl, float f2, float f3) {
        Vec3 vec3 = new Vec3(EntityRayTraceUtils.a.f_91074_.m_20185_(), EntityRayTraceUtils.a.f_91074_.m_20186_() + 1.62, EntityRayTraceUtils.a.f_91074_.m_20189_());
        Vec3 vec32 = EntityRayTraceUtils.a(f3, f2);
        Vec3 vec33 = vec3.m_82520_(vec32.f_82479_ * d2, vec32.f_82480_ * d2, vec32.f_82481_ * d2);
        return EntityRayTraceUtils.a.f_91074_.m_9236_().m_45547_(new ClipContext(vec3, vec33, ClipContext.Block.OUTLINE, bl ? ClipContext.Fluid.ANY : ClipContext.Fluid.NONE, (Entity)EntityRayTraceUtils.a.f_91074_));
    }

    public static HitResult a(Vec3 vec3, Vec3 vec32, boolean bl, boolean bl2, boolean bl3, Entity entity) {
        ClipContext.Block block = bl2 ? ClipContext.Block.COLLIDER : (bl3 ? ClipContext.Block.VISUAL : ClipContext.Block.OUTLINE);
        ClipContext.Fluid fluid = bl ? ClipContext.Fluid.ANY : ClipContext.Fluid.NONE;
        ClipContext clipContext = new ClipContext(vec3, vec32, block, fluid, entity);
        return EntityRayTraceUtils.a.f_91073_.m_45547_(clipContext);
    }

    public static EntityHitResult a(AABB aABB, Vec3 vec32, Vec3 vec33) {
        Optional optional = aABB.m_82371_(vec32, vec33);
        return optional.map(vec3 -> new EntityHitResult(null, vec3)).orElse(null);
    }
}

