/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.mixin.O.accessors;

import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value={CrossbowItem.class})
public interface CrossbowItemAccessor {
    @Invoker(value="getShootingPower")
    public static float getShootingPower(ItemStack itemStack) {
        return 0.0f;
    }
}

