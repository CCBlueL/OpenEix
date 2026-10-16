/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.mixin.O;

import com.heypixel.heypixelmod.obsoverlay.EixClientCore;
import com.heypixel.heypixelmod.obsoverlay.events.impl.MovementInputEvent;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.KeyboardInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={KeyboardInput.class})
public class MixinKeyboardInput
extends Input {
    @Inject(at={@At(value="TAIL")}, method={"tick"})
    private void onTickTail(boolean bl, float f2, CallbackInfo callbackInfo) {
        float f3 = this.f_108568_ == this.f_108569_ ? 0.0f : (this.f_108567_ = this.f_108568_ ? 1.0f : -1.0f);
        this.f_108566_ = this.f_108570_ == this.f_108571_ ? 0.0f : (this.f_108570_ ? 1.0f : -1.0f);
        MovementInputEvent l2 = new MovementInputEvent(this.f_108567_, this.f_108566_, this.f_108572_, this.f_108573_, 0.3);
        EixClientCore.a().b().a(l2);
        double d2 = l2.e();
        this.f_108567_ = l2.a();
        this.f_108566_ = l2.b();
        this.f_108572_ = l2.c();
        this.f_108573_ = l2.d();
        if (bl) {
            this.f_108566_ = (float)((double)this.f_108566_ * d2);
            this.f_108567_ = (float)((double)this.f_108567_ * d2);
        }
    }
}

