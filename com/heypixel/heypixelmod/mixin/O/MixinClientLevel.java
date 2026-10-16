/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.mixin.O;

import com.heypixel.heypixelmod.obsoverlay.EixClientCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value={ClientLevel.class})
public class MixinClientLevel {
    @Redirect(method={"tickNonPassenger"}, at=@At(value="INVOKE", target="Lnet/minecraft/world/entity/Entity;tick()V"))
    public void hookSkipTicks(Entity entity) {
        if (EixClientCore.d > 0 && entity == Minecraft.m_91087_().f_91074_) {
            --EixClientCore.d;
        } else {
            entity.m_8119_();
        }
    }
}

