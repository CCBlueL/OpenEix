/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.mixin.O;

import com.heypixel.heypixelmod.obsoverlay.EixClientCore;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.HUD;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.ChatScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={ChatScreen.class})
public class MixinChatScreen {
    @Inject(method={"render"}, at={@At(value="HEAD")})
    private void beginChatInputRender(GuiGraphics guiGraphics, int n2, int n3, float f2, CallbackInfo callbackInfo) {
        HUD m2 = EixClientCore.a().g().a(HUD.class);
        if (m2 != null) {
            m2.s();
        }
    }

    @Redirect(method={"render"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/gui/GuiGraphics;fill(IIIII)V", ordinal=0), require=0)
    private void redirectChatInputBackground(GuiGraphics guiGraphics, int n2, int n3, int n4, int n5, int n6) {
        HUD m2 = EixClientCore.a().g().a(HUD.class);
        if (m2 != null && m2.m() && m2.q.m()) {
            m2.b(n2, n3, n4, n5);
            return;
        }
        guiGraphics.m_280509_(n2, n3, n4, n5, n6);
    }

    @Inject(method={"init"}, at={@At(value="TAIL")})
    private void onChatScreenInit(CallbackInfo callbackInfo) {
        HUD m2 = EixClientCore.a().g().a(HUD.class);
        if (m2 != null) {
            m2.t();
        }
    }

    @Inject(method={"removed"}, at={@At(value="HEAD")})
    private void onChatScreenRemoved(CallbackInfo callbackInfo) {
        HUD m2 = EixClientCore.a().g().a(HUD.class);
        if (m2 != null) {
            m2.u();
        }
    }
}

