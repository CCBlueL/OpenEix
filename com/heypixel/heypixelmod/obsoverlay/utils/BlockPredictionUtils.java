/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils;

import com.heypixel.heypixelmod.mixin.O.accessors.ClientLevelAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.prediction.BlockStatePredictionHandler;
import net.minecraft.client.multiplayer.prediction.PredictiveAction;

public class BlockPredictionUtils {
    private static final Minecraft a = Minecraft.m_91087_();

    public static void a(PredictiveAction predictiveAction) {
        if (a.m_91403_() != null && BlockPredictionUtils.a.f_91073_ != null) {
            try (BlockStatePredictionHandler blockStatePredictionHandler = ((ClientLevelAccessor)BlockPredictionUtils.a.f_91073_).getBlockStatePredictionHandler().m_233855_();){
                int n2 = blockStatePredictionHandler.m_233871_();
                a.m_91403_().m_104955_(predictiveAction.m_233885_(n2));
            }
        }
    }
}

