/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.mixin.O;

import com.heypixel.heypixelmod.obsoverlay.EixClientCore;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventMouseClick;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={MouseHandler.class})
public class MixinMouseHandler {
    @Inject(at={@At(value="INVOKE", target="Lnet/minecraft/client/KeyMapping;set(Lcom/mojang/blaze3d/platform/InputConstants$Key;Z)V")}, method={"onPress"})
    private void onPress(long l2, int n2, int n3, int n4, CallbackInfo callbackInfo) {
        EventMouseClick eventMouseClick = new EventMouseClick(n2, n3 == 0);
        EixClientCore.a().b().a(eventMouseClick);
    }
}

