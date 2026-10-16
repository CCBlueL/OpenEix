/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.mixin.O.accessors;

import net.minecraft.network.protocol.game.ClientboundRotateHeadPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={ClientboundRotateHeadPacket.class})
public interface ClientboundRotateHeadPacketAccessor {
    @Accessor(value="entityId")
    public int getEntityId();

    @Accessor(value="yHeadRot")
    public byte getYHeadRot();
}

