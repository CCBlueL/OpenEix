/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.mixin.O;

import com.heypixel.heypixelmod.obsoverlay.EixClientCore;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventRender2D;
import com.heypixel.heypixelmod.obsoverlay.events.impl.WorldRenderEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.WorldRenderTailEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.FullBright;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.NoHurtCam;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={GameRenderer.class})
public class MixinGameRenderer {
    @Shadow
    @Final
    private Minecraft f_109059_;
    @Shadow
    @Final
    private RenderBuffers f_109064_;

    @Inject(method={"getNightVisionScale"}, at={@At(value="HEAD")}, cancellable=true)
    private static void getNightVisionScale(LivingEntity livingEntity, float f2, CallbackInfoReturnable<Float> callbackInfoReturnable) {
        FullBright k2 = EixClientCore.a().g().a(FullBright.class);
        if (k2.m()) {
            callbackInfoReturnable.setReturnValue((Object)Float.valueOf(k2.c.q()));
            callbackInfoReturnable.cancel();
        }
    }

    @Inject(method={"renderLevel"}, at={@At(value="FIELD", target="Lnet/minecraft/client/renderer/GameRenderer;renderHand:Z", opcode=180, ordinal=0)})
    private void renderLevel(float f2, long l2, PoseStack poseStack, CallbackInfo callbackInfo) {
        EixClientCore.a().b().a(new WorldRenderEvent(f2, poseStack));
    }

    @Inject(method={"renderLevel"}, at={@At(value="TAIL")})
    private void onRenderWorldTail(CallbackInfo callbackInfo) {
        EixClientCore.a().b().a(new WorldRenderTailEvent());
    }

    @Inject(method={"render"}, at={@At(value="TAIL")})
    public void render(float f2, long l2, boolean bl, CallbackInfo callbackInfo) {
        com.heypixel.heypixelmod.obsoverlay.modules.impl.render.MotionBlur q2 = com.heypixel.heypixelmod.obsoverlay.modules.impl.render.MotionBlur.c;
        if (q2 != null && q2.m() && this.f_109059_.f_91074_ != null && q2.d != null) {
            q2.d.m_110023_(f2);
        }
    }

    @Inject(method={"render"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/gui/Gui;render(Lnet/minecraft/client/gui/GuiGraphics;F)V")})
    public void injectRender2DEvent(float f2, long l2, boolean bl, CallbackInfo callbackInfo) {
        GuiGraphics guiGraphics = new GuiGraphics(this.f_109059_, this.f_109064_.m_110104_());
        EventRender2D eventRender2D = new EventRender2D(guiGraphics.m_280168_(), guiGraphics);
        EixClientCore.a().b().a(eventRender2D);
    }

    @Inject(method={"bobHurt"}, at={@At(value="HEAD")}, cancellable=true)
    private void bobHurt(PoseStack poseStack, float f2, CallbackInfo callbackInfo) {
        NoHurtCam t2 = EixClientCore.a().g().a(NoHurtCam.class);
        if (t2.m()) {
            callbackInfo.cancel();
        }
    }
}

