/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.mixin.O;

import com.heypixel.heypixelmod.mixin.O.accessors.ShapeIndexBufferAccessor;
import com.heypixel.heypixelmod.obsoverlay.utils.renderer.RenderStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.VertexBuffer;
import java.nio.ByteBuffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={VertexBuffer.class})
public abstract class MixinVertexBuffer {
    @Shadow
    private int f_166860_;

    @Inject(method={"uploadIndexBuffer"}, at={@At(value="RETURN")})
    private void onConfigureIndexBuffer(BufferBuilder.DrawState drawState, ByteBuffer byteBuffer, CallbackInfoReturnable<RenderSystem.AutoStorageIndexBuffer> callbackInfoReturnable) {
        RenderStateManager.a = callbackInfoReturnable.getReturnValue() == null ? this.f_166860_ : ((ShapeIndexBufferAccessor)callbackInfoReturnable.getReturnValue()).getId();
    }
}

