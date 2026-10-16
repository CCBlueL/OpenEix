/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.render;

import com.google.gson.JsonSyntaxException;
import com.heypixel.heypixelmod.mixin.O.accessors.PostChainAccessor;
import com.heypixel.heypixelmod.obsoverlay.d.a.NumericSetting;
import com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventRunTicks;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;
import com.mojang.blaze3d.shaders.Uniform;
import java.io.IOException;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.resources.ResourceLocation;

@ModuleInfo(a="MotionBlur", b="\u52a8\u6001\u6a21\u7cca", c="Make your game smoother.", d=ModuleCategory.RENDER)
public class MotionBlur
extends ModuleBase {
    public static MotionBlur c;
    private final AntiNausea e = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Strength").d(0.1f).a(7.0f).b(0.0f).c(10.0f).a().c();
    private final ResourceLocation f = new ResourceLocation("minecraft", "shaders/post/motion_blur.json");
    public PostChain d;
    private int g;
    private float h;
    private int i;

    public MotionBlur() {
        c = this;
    }

    @EventTarget
    public void onTick(EventRunTicks eventRunTicks) {
        if (eventRunTicks.type() != com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.b && MotionBlur.a.f_91074_ != null && MotionBlur.a.f_91073_ != null && MotionBlur.a.f_91074_.f_19797_ > 10) {
            float f2;
            if ((this.d == null || a.m_91268_().m_85441_() != this.g || a.m_91268_().m_85442_() != this.i) && a.m_91268_().m_85441_() > 0 && a.m_91268_().m_85442_() > 0) {
                this.h = Float.NaN;
                try {
                    this.d = new PostChain(a.m_91097_(), a.m_91098_(), a.m_91385_(), this.f);
                    this.d.m_110025_(a.m_91268_().m_85441_(), a.m_91268_().m_85442_());
                }
                catch (JsonSyntaxException | IOException throwable) {
                    throwable.printStackTrace();
                }
            }
            if (this.h != (f2 = 1.0f - Math.min(this.e.q() / 10.0f, 0.9f)) && this.d != null) {
                ((PostChainAccessor)this.d).getPasses().forEach(postPass -> {
                    Uniform uniform = postPass.m_110074_().m_108952_("BlurFactor");
                    if (uniform != null) {
                        uniform.m_5889_(f2, 0.0f, 0.0f);
                    }
                });
                this.h = f2;
            }
            this.g = a.m_91268_().m_85441_();
            this.i = a.m_91268_().m_85442_();
        }
    }
}

