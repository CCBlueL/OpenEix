/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.mixin.O.accessors;

import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={LocalPlayer.class})
public interface LocalPlayerAccessor {
    @Accessor
    public boolean isWasSprinting();

    @Accessor(value="yRotLast")
    public float getYRotLast();

    @Accessor(value="yRotLast")
    public void setYRotLast(float var1);

    @Accessor(value="xRotLast")
    public float getXRotLast();

    @Accessor(value="xRotLast")
    public void setXRotLast(float var1);
}

