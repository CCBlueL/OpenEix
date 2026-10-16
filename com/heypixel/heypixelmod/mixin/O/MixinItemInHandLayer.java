/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.mixin.O;

import com.heypixel.heypixelmod.obsoverlay.EixClientCore;
import com.heypixel.heypixelmod.obsoverlay.events.impl.HeldItemUpdateEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value={ItemInHandLayer.class})
public class MixinItemInHandLayer {
    @Redirect(method={"render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V"}, at=@At(value="INVOKE", target="Lnet/minecraft/world/entity/LivingEntity;getMainHandItem()Lnet/minecraft/world/item/ItemStack;"))
    private ItemStack hookMainHand(LivingEntity livingEntity) {
        HeldItemUpdateEvent af2 = new HeldItemUpdateEvent(InteractionHand.MAIN_HAND, livingEntity.m_21205_());
        if (livingEntity == Minecraft.m_91087_().f_91074_) {
            EixClientCore.a().b().a(af2);
        }
        return af2.b();
    }

    @Redirect(method={"render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V"}, at=@At(value="INVOKE", target="Lnet/minecraft/world/entity/LivingEntity;getOffhandItem()Lnet/minecraft/world/item/ItemStack;"))
    private ItemStack hookOffHand(LivingEntity livingEntity) {
        HeldItemUpdateEvent af2 = new HeldItemUpdateEvent(InteractionHand.OFF_HAND, livingEntity.m_21206_());
        if (livingEntity == Minecraft.m_91087_().f_91074_) {
            EixClientCore.a().b().a(af2);
        }
        return af2.b();
    }
}

