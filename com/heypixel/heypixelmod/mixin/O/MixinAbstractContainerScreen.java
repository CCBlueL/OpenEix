/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.mixin.O;

import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.HUD;
import com.heypixel.heypixelmod.obsoverlay.utils.e.a.SkiaSurfaceRenderer;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={AbstractContainerScreen.class})
public abstract class MixinAbstractContainerScreen {
    @Shadow
    protected int f_97735_;
    @Shadow
    protected int f_97736_;
    @Shadow
    protected int f_97726_;
    @Shadow
    protected int f_97727_;

    @Shadow
    protected abstract void m_7286_(GuiGraphics var1, float var2, int var3, int var4);

    @Inject(method={"render"}, at={@At(value="HEAD")})
    private void renderSkia(GuiGraphics guiGraphics, int n2, int n3, float f2, CallbackInfo callbackInfo) {
        HUD m2 = com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a(HUD.class);
        if (m2 == null || !m2.m() || !m2.r.m()) {
            return;
        }
        try {
            a.a(canvas -> {
                com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c();
                com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a((float)Minecraft.m_91087_().m_91268_().m_85449_());
                float f2 = this.f_97735_;
                float f3 = this.f_97736_;
                float f4 = this.f_97726_;
                float f5 = this.f_97727_;
                float f6 = m2.u.q();
                if (m2.t.m()) {
                    com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c(f2, f3, f4, f5, f6);
                }
                if (m2.s.m()) {
                    com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f2, f3, f4, f5, f6);
                }
                com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f2, f3, f4, f5, f6, new Color(18, 18, 18, (int)m2.v.q()));
                AbstractContainerMenu abstractContainerMenu = ((AbstractContainerScreen)this).m_6262_();
                if (abstractContainerMenu != null) {
                    Color color = new Color(25, 25, 25, (int)m2.w.q());
                    for (Slot slot : abstractContainerMenu.f_38839_) {
                        float f7 = f2 + (float)slot.f_40220_;
                        float f8 = f3 + (float)slot.f_40221_;
                        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f7, f8, 16.0f, 16.0f, 4.0f, color);
                    }
                }
                com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.d();
            });
        }
        catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Redirect(method={"render"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/gui/screens/inventory/AbstractContainerScreen;renderBg(Lnet/minecraft/client/gui/GuiGraphics;FII)V"))
    private void redirectRenderBg(AbstractContainerScreen<?> abstractContainerScreen, GuiGraphics guiGraphics, float f2, int n2, int n3) {
        HUD m2 = com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a(HUD.class);
        if (m2 != null && m2.m() && m2.r.m() && !(abstractContainerScreen instanceof InventoryScreen)) {
            return;
        }
        this.m_7286_(guiGraphics, f2, n2, n3);
    }
}

