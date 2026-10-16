/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.mixin.O;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={Screen.class})
public abstract class MixinScreen {
    @Inject(method={"renderBackground(Lnet/minecraft/client/gui/GuiGraphics;)V"}, at={@At(value="HEAD")}, cancellable=true, require=0)
    private void cancelBackground(GuiGraphics guiGraphics, CallbackInfo callbackInfo) {
        callbackInfo.cancel();
    }
}

