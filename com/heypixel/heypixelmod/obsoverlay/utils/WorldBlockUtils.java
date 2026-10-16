/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WorldBlockUtils {
    private static final Minecraft a = Minecraft.m_91087_();

    public static AABB a(BlockPos blockPos) {
        return WorldBlockUtils.e(blockPos).m_83215_().m_82338_(blockPos);
    }

    private static VoxelShape e(BlockPos blockPos) {
        return WorldBlockUtils.b(blockPos).m_60808_((BlockGetter)WorldBlockUtils.a.f_91073_, blockPos);
    }

    public static BlockState b(BlockPos blockPos) {
        return WorldBlockUtils.a.f_91073_.m_8055_(blockPos);
    }

    public static boolean c(BlockPos blockPos) {
        return WorldBlockUtils.e(blockPos) != Shapes.m_83040_();
    }

    public static boolean d(BlockPos blockPos) {
        if (WorldBlockUtils.a.f_91073_ != null && WorldBlockUtils.a.f_91074_ != null) {
            Block block = WorldBlockUtils.a.f_91073_.m_8055_(blockPos).m_60734_();
            return block instanceof AirBlock;
        }
        return false;
    }
}

