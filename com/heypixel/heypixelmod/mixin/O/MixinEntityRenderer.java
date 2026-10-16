/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.mixin.O;

import com.heypixel.heypixelmod.obsoverlay.EixClientCore;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.NameTags;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={EntityRenderer.class})
public class MixinEntityRenderer<T extends Entity> {
    @Inject(method={"renderNameTag"}, at={@At(value="HEAD")}, cancellable=true)
    private void renderNameTag(T t2, Component component, PoseStack poseStack, MultiBufferSource multiBufferSource, int n2, CallbackInfo callbackInfo) {
        if (t2 instanceof Player && EixClientCore.a().g().a(NameTags.class).m()) {
            callbackInfo.cancel();
        }
    }
}

