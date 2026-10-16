/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.mixin.O;

import com.heypixel.heypixelmod.obsoverlay.EixClientCore;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.AntiBlindness;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value={FogRenderer.class})
public class MixinFogRenderer {
    @Redirect(method={"setupColor"}, at=@At(value="INVOKE", target="Lnet/minecraft/world/entity/LivingEntity;hasEffect(Lnet/minecraft/world/effect/MobEffect;)Z", ordinal=0))
    private static boolean onSetupColor(LivingEntity livingEntity, MobEffect mobEffect) {
        return (mobEffect != MobEffects.f_19610_ || !EixClientCore.a().g().a(AntiBlindness.class).m()) && livingEntity.m_21023_(mobEffect);
    }

    @Redirect(method={"setupFog(Lnet/minecraft/client/Camera;Lnet/minecraft/client/renderer/FogRenderer$FogMode;FZF)V"}, at=@At(value="INVOKE", target="Lnet/minecraft/world/entity/LivingEntity;hasEffect(Lnet/minecraft/world/effect/MobEffect;)Z"))
    private static boolean onSetupFog(LivingEntity livingEntity, MobEffect mobEffect) {
        return (mobEffect != MobEffects.f_19610_ || !EixClientCore.a().g().a(AntiBlindness.class).m()) && livingEntity.m_21023_(mobEffect);
    }
}

