/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.mixin.O;

import com.heypixel.heypixelmod.obsoverlay.EixClientCore;
import com.heypixel.heypixelmod.obsoverlay.events.impl.FovUpdateEvent;
import net.minecraft.client.player.AbstractClientPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={AbstractClientPlayer.class})
public abstract class MixinAbstractClientPlayer {
    @Inject(method={"getFieldOfViewModifier"}, at={@At(value="RETURN")}, cancellable=true)
    private void hookFoV(CallbackInfoReturnable<Float> callbackInfoReturnable) {
        Float f2 = (Float)callbackInfoReturnable.getReturnValue();
        FovUpdateEvent ae2 = new FovUpdateEvent(f2.floatValue());
        EixClientCore.a().b().a(ae2);
        callbackInfoReturnable.setReturnValue((Object)Float.valueOf(ae2.a()));
    }
}

