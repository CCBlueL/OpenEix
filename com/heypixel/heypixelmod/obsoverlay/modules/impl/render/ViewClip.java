/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.render;

import com.heypixel.heypixelmod.obsoverlay.d.a.NumericSetting;
import com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventRender2D;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;
import com.heypixel.heypixelmod.obsoverlay.utils.RotationInterpolation;
import net.minecraft.client.CameraType;

@ModuleInfo(a="ViewClip", b="\u89c6\u89d2\u7a7f\u5899", c="Allows you to see through blocks", d=ModuleCategory.RENDER)
public class ViewClip
extends ModuleBase {
    public AntiNausea c = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Scale").b(0.5f).c(2.0f).a(1.0f).d(0.01f).a().c();
    public com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a d = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Animation").a(true).a().b();
    public AntiNausea e = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Animation Speed").b(0.01f).c(0.5f).a(0.3f).d(0.01f).a(() -> this.d.m()).a().c();
    public RotationInterpolation f = new RotationInterpolation(100.0f);
    CameraType g;

    @EventTarget
    public void onRender(EventRender2D eventRender2D) {
        if (this.g != ViewClip.a.f_91066_.m_92176_()) {
            this.g = ViewClip.a.f_91066_.m_92176_();
            if (this.g == CameraType.FIRST_PERSON || this.g == CameraType.THIRD_PERSON_BACK) {
                this.f.c = 0.0f;
            }
        }
        this.f.b = this.e.q();
        this.f.a(true);
    }
}

