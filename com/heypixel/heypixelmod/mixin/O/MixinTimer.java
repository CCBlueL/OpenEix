/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.mixin.O;

import com.heypixel.heypixelmod.obsoverlay.EixClientCore;
import net.minecraft.client.Timer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={Timer.class})
public class MixinTimer {
    @Shadow
    public float f_92518_;
    @Shadow
    public float f_92519_;
    @Shadow
    private long f_92520_;
    @Final
    @Shadow
    private float f_92521_;

    @Inject(method={"advanceTime"}, at={@At(value="HEAD")}, cancellable=true)
    private void beginRenderTickHook(long l2, CallbackInfoReturnable<Integer> callbackInfoReturnable) {
        if (EixClientCore.c != 1.0f) {
            this.f_92519_ = (float)(l2 - this.f_92520_) / this.f_92521_ * EixClientCore.c;
            this.f_92520_ = l2;
            this.f_92518_ += this.f_92519_;
            int n2 = (int)this.f_92518_;
            this.f_92518_ -= (float)n2;
            callbackInfoReturnable.setReturnValue((Object)n2);
        }
    }
}

