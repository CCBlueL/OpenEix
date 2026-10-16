/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.mixin.O.accessors;

import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={ClientboundMoveEntityPacket.class})
public interface ClientboundMoveEntityPacketAccessor {
    @Accessor(value="entityId")
    public int getEntityId();

    @Accessor(value="xa")
    public short getXa();

    @Accessor(value="ya")
    public short getYa();

    @Accessor(value="za")
    public short getZa();

    @Accessor(value="yRot")
    public byte getYRot();

    @Accessor(value="xRot")
    public byte getXRot();

    @Accessor(value="onGround")
    public boolean getOnGround();

    @Accessor(value="hasRot")
    public boolean getHasRot();

    @Accessor(value="hasPos")
    public boolean getHasPos();
}

