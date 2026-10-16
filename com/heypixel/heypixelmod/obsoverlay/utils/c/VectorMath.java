/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils.c;

import com.heypixel.heypixelmod.obsoverlay.utils.c.RotationVector;
import com.heypixel.heypixelmod.obsoverlay.utils.c.PlayerRotationController;
import com.heypixel.heypixelmod.obsoverlay.utils.c.EntityRotationFactory;
import java.util.List;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public final class VectorMath {
    private static final Minecraft a = Minecraft.m_91087_();

    public static HitResult a(RotationVector b2, double d2) {
        return com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.a.a(b2, d2, 0.0f);
    }

    public static boolean a(Entity entity) {
        Minecraft minecraft = Minecraft.m_91087_();
        if (minecraft.f_91074_ == null || entity == null) {
            return false;
        }
        double d2 = (double)((Integer)minecraft.f_91066_.m_231984_().m_231551_()).intValue() * 16.0;
        RotationVector b2 = EntityRotationFactory.a(entity);
        if (Math.abs(Mth.m_14177_((float)(minecraft.f_91074_.m_146908_() - b2.a()))) > (float)((Integer)minecraft.f_91066_.m_231837_().m_231551_()).intValue()) {
            return false;
        }
        double d3 = minecraft.f_91074_.m_20270_(entity);
        if (d3 > 100.0 || !(entity instanceof Player)) {
            HitResult hitResult = com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.a.a(b2, d2, 0.2f);
            return hitResult != null && hitResult.m_6662_() == HitResult.Type.ENTITY;
        }
        AABB aABB = entity.m_20191_();
        double d4 = aABB.m_82362_();
        double d5 = aABB.m_82376_();
        double d6 = aABB.m_82385_();
        Vec3 vec3 = entity.m_20182_();
        for (double d7 = 1.0; d7 >= -1.0; d7 -= 0.5) {
            for (double d8 = 1.0; d8 >= -1.0; d8 -= 1.0) {
                for (double d9 = 1.0; d9 >= -1.0; d9 -= 1.0) {
                    Vec3 vec32 = vec3.m_82520_(d4 * d8, d5 * d7, d6 * d9);
                    RotationVector b3 = EntityRotationFactory.a(vec32);
                    HitResult hitResult = com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.a.a(b3, d2, 0.2f);
                    if (hitResult == null || hitResult.m_6662_() != HitResult.Type.ENTITY) continue;
                    return true;
                }
            }
        }
        return false;
    }

    public static HitResult a(RotationVector b2, double d2, float f2) {
        return com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.a.a(b2, d2, f2, (Entity)com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.a.a.f_91074_);
    }

    public static HitResult a(RotationVector b2, double d2, float f2, Entity entity2) {
        Minecraft minecraft = Minecraft.m_91087_();
        if (minecraft.f_91073_ == null || entity2 == null) {
            return null;
        }
        float f3 = minecraft.m_91296_();
        Vec3 vec3 = entity2.m_20299_(f3);
        Vec3 vec32 = Vec3.m_82498_((float)b2.b(), (float)b2.a());
        Vec3 vec33 = vec3.m_82549_(vec32.m_82490_(d2));
        BlockHitResult blockHitResult = minecraft.f_91073_.m_45547_(new ClipContext(vec3, vec33, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, entity2));
        double d3 = d2;
        if (blockHitResult.m_6662_() != HitResult.Type.MISS) {
            d3 = blockHitResult.m_82450_().m_82554_(vec3);
        }
        Vec3 vec34 = vec3.m_82549_(vec32.m_82490_(d2));
        Entity entity3 = null;
        Vec3 vec35 = null;
        double d4 = d3;
        AABB aABB = entity2.m_20191_().m_82369_(vec32.m_82490_(d2)).m_82400_(1.0);
        List list = minecraft.f_91073_.m_6249_(entity2, aABB, entity -> !entity.m_5833_() && entity.m_6087_());
        for (Entity entity4 : list) {
            Vec3 vec36;
            double d5;
            float f4 = entity4.m_6143_() + f2;
            AABB aABB2 = entity4.m_20191_().m_82400_((double)f4);
            Optional optional = aABB2.m_82371_(vec3, vec34);
            if (aABB2.m_82390_(vec3)) {
                if (!(d4 >= 0.0)) continue;
                entity3 = entity4;
                vec35 = optional.orElse(vec3);
                d4 = 0.0;
                continue;
            }
            if (!optional.isPresent() || !((d5 = vec3.m_82554_(vec36 = (Vec3)optional.get())) < d4) && d4 != 0.0) continue;
            if (entity4.m_20201_() == entity2.m_20201_() && !entity4.canRiderInteract()) {
                if (d4 != 0.0) continue;
                entity3 = entity4;
                vec35 = vec36;
                continue;
            }
            entity3 = entity4;
            vec35 = vec36;
            d4 = d5;
        }
        if (entity3 != null && (d4 < d3 || blockHitResult.m_6662_() == HitResult.Type.MISS)) {
            return new EntityHitResult(entity3, vec35);
        }
        return blockHitResult;
    }

    public static boolean a(RotationVector b2, Direction direction, BlockPos blockPos, boolean bl) {
        double d2;
        Vec3 vec3;
        Minecraft minecraft = Minecraft.m_91087_();
        if (minecraft.f_91074_ == null || minecraft.f_91073_ == null) {
            return false;
        }
        Vec3 vec32 = Vec3.m_82498_((float)b2.b(), (float)b2.a());
        Vec3 vec33 = minecraft.f_91074_.m_20299_(1.0f);
        BlockHitResult blockHitResult = minecraft.f_91073_.m_45547_(new ClipContext(vec33, vec3 = vec33.m_82549_(vec32.m_82490_(d2 = 4.5)), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, (Entity)minecraft.f_91074_));
        if (blockHitResult.m_6662_() == HitResult.Type.MISS) {
            return false;
        }
        return blockHitResult.m_82425_().equals((Object)blockPos) && (!bl || blockHitResult.m_82434_() == direction);
    }

    public static boolean a(Direction direction, BlockPos blockPos, boolean bl) {
        return com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.a.a(PlayerRotationController.j(), Direction.UP, blockPos, false);
    }

    public static Boolean a(RotationVector b2, BlockPos blockPos) {
        return com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.a.a(b2, Direction.UP, blockPos, false);
    }

    public static Boolean a(RotationVector b2, BlockPos blockPos, Direction direction) {
        return com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.a.a(b2, direction, blockPos, true);
    }
}

