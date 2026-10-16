/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.mixin.O;

import com.heypixel.heypixelmod.obsoverlay.EixClientCore;
import com.heypixel.heypixelmod.obsoverlay.events.impl.LivingEntityRotationEvent;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={LivingEntityRenderer.class})
public class MixinLivingEntityRenderer<T extends LivingEntity, M extends EntityModel<T>> {
    @Inject(method={"render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"}, at={@At(value="HEAD")})
    private void renderHead(T t2, float f2, float f3, PoseStack poseStack, MultiBufferSource multiBufferSource, int n2, CallbackInfo callbackInfo) {
        LivingEntityRotationEvent.a = t2;
    }

    @Redirect(method={"render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"}, at=@At(value="INVOKE", target="Lnet/minecraft/util/Mth;rotLerp(FFF)F", ordinal=1))
    private float rotAnimationYaw(float f2, float f3, float f4) {
        LivingEntityRotationEvent v2 = new LivingEntityRotationEvent(f4, f3, 0.0f, 0.0f);
        if (LivingEntityRotationEvent.a == Minecraft.m_91087_().f_91074_) {
            EixClientCore.a().b().a(v2);
        }
        return Mth.m_14189_((float)f2, (float)v2.b(), (float)v2.a());
    }

    @Redirect(method={"render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"}, at=@At(value="INVOKE", target="Lnet/minecraft/util/Mth;lerp(FFF)F", ordinal=0))
    private float rotAnimationPitch(float f2, float f3, float f4) {
        LivingEntityRotationEvent v2 = new LivingEntityRotationEvent(0.0f, 0.0f, f4, f3);
        if (LivingEntityRotationEvent.a == Minecraft.m_91087_().f_91074_) {
            EixClientCore.a().b().a(v2);
        }
        return Mth.m_14179_((float)f2, (float)v2.d(), (float)v2.c());
    }
}

