/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils.c;

import com.heypixel.heypixelmod.mixin.O.accessors.AccessorEntity;
import com.heypixel.heypixelmod.obsoverlay.utils.c.VectorMath;
import com.heypixel.heypixelmod.obsoverlay.utils.c.RotationVector;
import com.heypixel.heypixelmod.obsoverlay.utils.c.PlayerRotationController;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class EntityRotationFactory {
    private static final Minecraft a = Minecraft.m_91087_();

    public static RotationVector a(Vec3 vec3, Vec3 vec32) {
        Vec3 vec33 = vec32.m_82546_(vec3);
        double d2 = Math.hypot(vec33.f_82479_, vec33.f_82481_);
        float f2 = (float)(Mth.m_14136_((double)vec33.f_82481_, (double)vec33.f_82479_) * 57.2957763671875) - 90.0f;
        float f3 = (float)(-(Mth.m_14136_((double)vec33.f_82480_, (double)d2) * 57.2957763671875));
        return new RotationVector(f2, f3);
    }

    public static RotationVector a(Entity entity) {
        return EntityRotationFactory.a(entity.m_20182_().m_82520_(0.0, Math.max(0.0, Math.min(EntityRotationFactory.a.f_91074_.m_20186_() - entity.m_20186_() + (double)EntityRotationFactory.a.f_91074_.m_20192_(), (entity.m_20191_().f_82292_ - entity.m_20191_().f_82289_) * 0.9)), 0.0));
    }

    public static RotationVector a(Entity entity, boolean bl, double d2) {
        RotationVector b2 = EntityRotationFactory.a(entity);
        HitResult hitResult = com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.a.a(b2, d2, 0.0f);
        if (!bl || hitResult != null && hitResult.m_6662_() == HitResult.Type.ENTITY) {
            return b2;
        }
        AABB aABB = entity.m_20191_();
        double d3 = aABB.f_82288_;
        double d4 = aABB.f_82291_;
        double d5 = aABB.f_82289_;
        double d6 = aABB.f_82292_;
        double d7 = aABB.f_82290_;
        double d8 = aABB.f_82293_;
        Vec3 vec3 = entity.m_20182_();
        for (double d9 = 1.0; d9 >= 0.0; d9 -= 0.25 + Math.random() * 0.1) {
            for (double d10 = 1.0; d10 >= -0.5; d10 -= 0.5) {
                for (double d11 = 1.0; d11 >= -0.5; d11 -= 0.5) {
                    double d12 = (d4 - d3) * d10;
                    double d13 = (d6 - d5) * d9;
                    double d14 = (d8 - d7) * d11;
                    Vec3 vec32 = vec3.m_82520_(d12, d13, d14);
                    RotationVector b3 = EntityRotationFactory.a(vec32);
                    HitResult hitResult2 = com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.a.a(b3, d2, 0.0f);
                    if (hitResult2 == null || hitResult2.m_6662_() != HitResult.Type.ENTITY) continue;
                    return b3;
                }
            }
        }
        return b2;
    }

    public static RotationVector a(BlockPos blockPos) {
        return EntityRotationFactory.a(EntityRotationFactory.a.f_91074_.m_146892_(), new Vec3((double)blockPos.m_123341_(), (double)blockPos.m_123342_(), (double)blockPos.m_123343_()).m_82520_(0.5, 0.5, 0.5));
    }

    public static RotationVector a(Vec3 vec3) {
        return EntityRotationFactory.a(EntityRotationFactory.a.f_91074_.m_146892_(), vec3);
    }

    public static RotationVector a(Vec3 vec3, Direction direction) {
        double d2 = vec3.f_82479_ + 0.5;
        double d3 = vec3.f_82480_ + 0.5;
        double d4 = vec3.f_82481_ + 0.5;
        return EntityRotationFactory.a(new Vec3(d2 += (double)direction.m_122436_().m_123341_() * 0.5, d3 += (double)direction.m_122436_().m_123342_() * 0.5, d4 += (double)direction.m_122436_().m_123343_() * 0.5));
    }

    public static RotationVector a(BlockPos blockPos, Direction direction) {
        double d2 = (double)blockPos.m_123341_() + 0.5;
        double d3 = (double)blockPos.m_123342_() + 0.5;
        double d4 = (double)blockPos.m_123343_() + 0.5;
        return EntityRotationFactory.a(new Vec3(d2 += (double)direction.m_122436_().m_123341_() * 0.5, d3 += (double)direction.m_122436_().m_123342_() * 0.5, d4 += (double)direction.m_122436_().m_123343_() * 0.5));
    }

    public static RotationVector a(RotationVector b2) {
        AccessorEntity accessorEntity = (AccessorEntity)EntityRotationFactory.a.f_91074_;
        RotationVector b3 = new RotationVector(accessorEntity.getPreviousYRot(), accessorEntity.getPreviousXRot());
        float f2 = (float)((Double)EntityRotationFactory.a.f_91066_.m_231964_().m_231551_() * (1.0 + Math.random() / 1.0E7) * (double)0.6f + (double)0.2f);
        double d2 = (double)(f2 * f2 * f2 * 8.0f) * 0.15;
        float f3 = b3.a() + (float)((double)Math.round((double)(b2.a() - b3.a()) / d2) * d2);
        float f4 = b3.b() + (float)((double)Math.round((double)(b2.b() - b3.b()) / d2) * d2);
        return new RotationVector(f3, Mth.m_14036_((float)f4, (float)-90.0f, (float)90.0f));
    }

    public static RotationVector a(RotationVector b2, RotationVector b3) {
        float f2 = (float)((Double)EntityRotationFactory.a.f_91066_.m_231964_().m_231551_() * (1.0 + Math.random() / 1.0E7) * (double)0.6f + (double)0.2f);
        double d2 = (double)(f2 * f2 * f2 * 8.0f) * 0.15;
        float f3 = b3.a() + (float)((double)Math.round((double)(b2.a() - b3.a()) / d2) * d2);
        float f4 = b3.b() + (float)((double)Math.round((double)(b2.b() - b3.b()) / d2) * d2);
        return new RotationVector(f3, Mth.m_14036_((float)f4, (float)-90.0f, (float)90.0f));
    }

    public static RotationVector b(RotationVector b2) {
        AccessorEntity accessorEntity = (AccessorEntity)EntityRotationFactory.a.f_91074_;
        RotationVector b3 = new RotationVector(accessorEntity.getPreviousYRot(), accessorEntity.getPreviousXRot());
        float f2 = b3.a() + Mth.m_14177_((float)(b2.a() - b3.a()));
        float f3 = Mth.m_14036_((float)b2.b(), (float)-90.0f, (float)90.0f);
        return new RotationVector(f2, f3);
    }

    public static RotationVector c(RotationVector b2) {
        if (b2 == null) {
            return null;
        }
        float f2 = b2.a() + Mth.m_14177_((float)(EntityRotationFactory.a.f_91074_.m_146908_() - b2.a()));
        float f3 = EntityRotationFactory.a.f_91074_.m_146909_();
        return new RotationVector(f2, f3);
    }

    public static RotationVector a(RotationVector b2, double d2) {
        return EntityRotationFactory.a(PlayerRotationController.b, b2, d2);
    }

    public static RotationVector a(RotationVector b2, RotationVector b3, double d2) {
        if (d2 != 0.0) {
            double d3 = Mth.m_14177_((float)(b3.a() - b2.a()));
            double d4 = b3.b() - b2.b();
            double d5 = Math.sqrt(d3 * d3 + d4 * d4);
            double d6 = Math.abs(d3 / d5);
            double d7 = Math.abs(d4 / d5);
            double d8 = d2 * d6;
            double d9 = d2 * d7;
            float f2 = (float)Math.max(Math.min(d3, d8), -d8);
            float f3 = (float)Math.max(Math.min(d4, d9), -d9);
            return new RotationVector(f2, f3);
        }
        return new RotationVector(0.0f, 0.0f);
    }

    public static RotationVector b(RotationVector b2, double d2) {
        return EntityRotationFactory.b(PlayerRotationController.b, b2, d2);
    }

    public static RotationVector b(RotationVector b2, RotationVector b3, double d2) {
        float f2 = b3.a();
        float f3 = b3.b();
        float f4 = b2.a();
        float f5 = b2.b();
        if (d2 != 0.0) {
            RotationVector b4 = EntityRotationFactory.a(b3, d2);
            f2 = f4 + b4.a();
            f3 = f5 + b4.b();
            for (int i2 = 0; i2 < 2; ++i2) {
                if ((double)(Math.abs(b4.a()) + Math.abs(b4.b())) > 1.0E-4) {
                    f2 += (float)((Math.random() - 0.5) / 1000.0);
                    f3 += (float)((Math.random() - 0.5) / 1000.0);
                }
                RotationVector b5 = new RotationVector(f2, f3);
                RotationVector b6 = EntityRotationFactory.a(b5);
                f2 = EntityRotationFactory.a(f4, b6.a());
                f3 = Math.max(-90.0f, Math.min(90.0f, b6.b()));
            }
        }
        return new RotationVector(f2, f3);
    }

    public static double b(Entity entity) {
        Vec3 vec3 = EntityRotationFactory.a.f_91074_.m_146892_();
        AABB aABB = entity.m_20191_();
        double d2 = Mth.m_14008_((double)vec3.f_82479_, (double)aABB.f_82288_, (double)aABB.f_82291_);
        double d3 = Mth.m_14008_((double)vec3.f_82480_, (double)aABB.f_82289_, (double)aABB.f_82292_);
        double d4 = Mth.m_14008_((double)vec3.f_82481_, (double)aABB.f_82290_, (double)aABB.f_82293_);
        return Math.sqrt(vec3.m_82531_(d2, d3, d4));
    }

    public static double a(Player player, Entity entity) {
        Vec3 vec3 = player.m_146892_();
        AABB aABB = entity.m_20191_();
        double d2 = Mth.m_14008_((double)vec3.f_82479_, (double)aABB.f_82288_, (double)aABB.f_82291_);
        double d3 = Mth.m_14008_((double)vec3.f_82480_, (double)aABB.f_82289_, (double)aABB.f_82292_);
        double d4 = Mth.m_14008_((double)vec3.f_82481_, (double)aABB.f_82290_, (double)aABB.f_82293_);
        return Math.sqrt(vec3.m_82531_(d2, d3, d4));
    }

    public static float a(float f2, float f3) {
        return f2 + Mth.m_14177_((float)(f3 - f2));
    }
}

