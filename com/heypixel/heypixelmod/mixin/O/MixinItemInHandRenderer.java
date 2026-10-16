/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.mixin.O;

import com.heypixel.heypixelmod.obsoverlay.EixClientCore;
import com.heypixel.heypixelmod.obsoverlay.events.impl.HeldItemUpdateEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value={ItemInHandRenderer.class})
public class MixinItemInHandRenderer {
    @Redirect(method={"tick"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/player/LocalPlayer;getMainHandItem()Lnet/minecraft/world/item/ItemStack;"))
    public ItemStack hookMainHand(LocalPlayer localPlayer) {
        HeldItemUpdateEvent af2 = new HeldItemUpdateEvent(InteractionHand.MAIN_HAND, localPlayer.m_21205_());
        if (localPlayer == Minecraft.m_91087_().f_91074_) {
            EixClientCore.a().b().a(af2);
        }
        return af2.b();
    }

    @Redirect(method={"tick"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/player/LocalPlayer;getOffhandItem()Lnet/minecraft/world/item/ItemStack;"))
    public ItemStack hookOffHand(LocalPlayer localPlayer) {
        HeldItemUpdateEvent af2 = new HeldItemUpdateEvent(InteractionHand.OFF_HAND, localPlayer.m_21206_());
        if (localPlayer == Minecraft.m_91087_().f_91074_) {
            EixClientCore.a().b().a(af2);
        }
        return af2.b();
    }
}

