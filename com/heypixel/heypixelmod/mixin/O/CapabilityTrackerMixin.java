/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.mixin.O;

import com.heypixel.heypixelmod.obsoverlay.utils.GenericValueAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(targets={"com.mojang.blaze3d.platform.GlStateManager$BooleanState"})
public abstract class CapabilityTrackerMixin
implements GenericValueAccessor {
    @Shadow
    private boolean f_84586_;

    @Shadow
    public abstract void m_84590_(boolean var1);

    @Override
    public boolean get() {
        return this.f_84586_;
    }

    @Override
    public void set(boolean bl) {
        this.m_84590_(bl);
    }
}

