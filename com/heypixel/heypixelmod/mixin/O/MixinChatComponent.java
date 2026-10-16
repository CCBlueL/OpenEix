/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.mixin.O;

import com.heypixel.heypixelmod.obsoverlay.EixClientCore;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.HUD;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ChatComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={ChatComponent.class})
public class MixinChatComponent {
    @Inject(method={"render"}, at={@At(value="HEAD")})
    private void beginChatRender(GuiGraphics guiGraphics, int n2, int n3, int n4, CallbackInfo callbackInfo) {
        HUD m2 = EixClientCore.a().g().a(HUD.class);
        if (m2 != null) {
            m2.r();
        }
    }

    @Redirect(method={"render"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/gui/GuiGraphics;fill(IIIII)V"))
    private void redirectChatBackground(GuiGraphics guiGraphics, int n2, int n3, int n4, int n5, int n6) {
        HUD m2 = EixClientCore.a().g().a(HUD.class);
        if (m2 != null && m2.m() && m2.m.m()) {
            m2.a(n2, n3, n4, n5);
            return;
        }
        guiGraphics.m_280509_(n2, n3, n4, n5, n6);
    }
}

