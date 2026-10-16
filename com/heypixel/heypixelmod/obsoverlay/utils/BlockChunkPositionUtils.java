/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.phys.Vec3;

public class BlockChunkPositionUtils {
    private final int a;
    private final int b;

    public BlockChunkPositionUtils(int n2, int n3) {
        this.a = n2;
        this.b = n3;
    }

    public static BlockChunkPositionUtils a(BlockPos blockPos) {
        return new BlockChunkPositionUtils(blockPos.m_123341_() >> 9 << 9, blockPos.m_123343_() >> 9 << 9);
    }

    public static BlockChunkPositionUtils a(ChunkPos chunkPos) {
        return new BlockChunkPositionUtils(chunkPos.f_45578_ >> 5 << 9, chunkPos.f_45579_ >> 5 << 9);
    }

    public BlockChunkPositionUtils a() {
        return new BlockChunkPositionUtils(-this.a, -this.b);
    }

    public Vec3 b() {
        return new Vec3((double)this.a, 0.0, (double)this.b);
    }

    public BlockPos c() {
        return new BlockPos(this.a, 0, this.b);
    }
}

