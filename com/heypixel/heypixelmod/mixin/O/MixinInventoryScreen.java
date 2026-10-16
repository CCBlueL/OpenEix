/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.mixin.O;

import com.heypixel.heypixelmod.obsoverlay.EixClientCore;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.HUD;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value={InventoryScreen.class})
public class MixinInventoryScreen {
    @Redirect(method={"renderBg"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V"))
    private void redirectBlit(GuiGraphics guiGraphics, ResourceLocation resourceLocation, int n2, int n3, int n4, int n5, int n6, int n7) {
        HUD m2 = EixClientCore.a().g().a(HUD.class);
        if (m2 != null && m2.m() && m2.r.m()) {
            return;
        }
        guiGraphics.m_280218_(resourceLocation, n2, n3, n4, n5, n6, n7);
    }
}

