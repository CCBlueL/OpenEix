/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.mixin.O;

import com.heypixel.heypixelmod.obsoverlay.EixClientCore;
import com.heypixel.heypixelmod.obsoverlay.events.impl.KeyboardKeyPressEvent;
import net.minecraft.client.KeyboardHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={KeyboardHandler.class})
public class MixinKeyboardHandler {
    @Inject(at={@At(value="HEAD")}, method={"keyPress"})
    private void onKeyPress(long l2, int n2, int n3, int n4, int n5, CallbackInfo callbackInfo) {
        if (n2 != -1 && EixClientCore.a() != null && EixClientCore.a().b() != null) {
            EixClientCore.a().b().a(new KeyboardKeyPressEvent(n2, n4 != 0));
        }
    }
}

