/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.events.impl;

import net.minecraft.client.Options;
import net.minecraft.client.player.Input;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(value=Dist.CLIENT)
public class MovementInputState
extends Input {
    private final Options a;
    private boolean b;

    public MovementInputState(Options options) {
        this.a = options;
        this.b = false;
    }

    public void a(boolean bl) {
        this.f_108568_ = this.a.f_92085_.m_90857_();
        this.f_108569_ = this.a.f_92087_.m_90857_();
        this.f_108570_ = this.a.f_92086_.m_90857_();
        this.f_108571_ = this.a.f_92088_.m_90857_();
        float f2 = this.f_108568_ == this.f_108569_ ? 0.0f : (this.f_108567_ = this.f_108568_ ? 1.0f : -1.0f);
        this.f_108566_ = this.f_108570_ == this.f_108571_ ? 0.0f : (this.f_108570_ ? 1.0f : -1.0f);
        this.f_108572_ = this.a.f_92089_.m_90857_();
        this.f_108573_ = this.a.f_92090_.m_90857_();
        if (bl) {
            this.f_108566_ = (float)((double)this.f_108566_ * 0.3);
            this.f_108567_ = (float)((double)this.f_108567_ * 0.3);
        }
        if (this.b) {
            this.f_108566_ *= 5.0f;
            this.f_108567_ *= 5.0f;
        }
    }

    public void b(boolean bl) {
        this.b = bl;
    }
}

