/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.mixin.O.accessors;

import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.level.GameType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value={MultiPlayerGameMode.class})
public interface MultiPlayerGameModeAccessor {
    @Invoker(value="ensureHasSentCarriedItem")
    public void invokeEnsureHasSentCarriedItem();

    @Accessor(value="localPlayerMode")
    public GameType getLocalPlayerMode();
}

