/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils;

import java.util.Objects;
import java.util.stream.Stream;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;

public class ChunkBlockEntityUtils {
    private static final Minecraft a = Minecraft.m_91087_();

    public static Stream<BlockEntity> a() {
        return ChunkBlockEntityUtils.b().flatMap(levelChunk -> levelChunk.m_62954_().values().stream());
    }

    public static Stream<LevelChunk> b() {
        int n2 = Math.max(2, ChunkBlockEntityUtils.a.f_91066_.m_193772_()) + 3;
        int n3 = n2 * 2 + 1;
        ChunkPos chunkPos2 = ChunkBlockEntityUtils.a.f_91074_.m_146902_();
        ChunkPos chunkPos4 = new ChunkPos(chunkPos2.f_45578_ - n2, chunkPos2.f_45579_ - n2);
        ChunkPos chunkPos5 = new ChunkPos(chunkPos2.f_45578_ + n2, chunkPos2.f_45579_ + n2);
        return Stream.iterate(chunkPos4, chunkPos3 -> {
            int n2 = chunkPos3.f_45578_;
            int n3 = chunkPos3.f_45579_;
            if (++n2 > chunkPos.f_45578_) {
                n2 = chunkPos2.f_45578_;
                ++n3;
            }
            if (n3 > chunkPos.f_45579_) {
                throw new IllegalStateException("Stream limit didn't work.");
            }
            return new ChunkPos(n2, n3);
        }).limit((long)n3 * (long)n3).filter(chunkPos -> ChunkBlockEntityUtils.a.f_91073_.m_7232_(chunkPos.f_45578_, chunkPos.f_45579_)).map(chunkPos -> ChunkBlockEntityUtils.a.f_91073_.m_6325_(chunkPos.f_45578_, chunkPos.f_45579_)).filter(Objects::nonNull);
    }
}

