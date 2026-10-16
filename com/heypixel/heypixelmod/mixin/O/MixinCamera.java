/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.mixin.O;

import com.heypixel.heypixelmod.obsoverlay.EixClientCore;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.ViewClip;
import net.minecraft.client.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={Camera.class})
public class MixinCamera {
    @Inject(at={@At(value="HEAD")}, method={"getMaxZoom"}, cancellable=true)
    private void getMaxZoom(double d2, CallbackInfoReturnable<Double> callbackInfoReturnable) {
        ViewClip ad2;
        if (EixClientCore.a() != null && EixClientCore.a().g() != null && (ad2 = EixClientCore.a().g().a(ViewClip.class)).m()) {
            callbackInfoReturnable.setReturnValue((Object)(d2 * (double)ad2.c.q() * (double)ad2.f.c / 100.0));
            callbackInfoReturnable.cancel();
        }
    }
}

