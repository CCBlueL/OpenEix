/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.mixin.O;

import com.heypixel.heypixelmod.obsoverlay.utils.d.a.BlurShaderProgram;
import com.mojang.blaze3d.platform.Window;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={Window.class})
public class MixinWindow {
    @Shadow
    private int f_85359_;
    @Shadow
    private int f_85360_;

    @Inject(method={"onFramebufferResize"}, at={@At(value="HEAD")})
    private void onFramebufferResize(long l2, int n2, int n3, CallbackInfo callbackInfo) {
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a.a(n2, n3);
        a.a.a();
        a.b.a();
    }
}

