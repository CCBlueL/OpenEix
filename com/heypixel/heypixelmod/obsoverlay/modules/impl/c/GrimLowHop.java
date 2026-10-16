/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.c;

import com.heypixel.heypixelmod.obsoverlay.EixClientCore;
import com.heypixel.heypixelmod.obsoverlay.d.a.NumericSetting;
import com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventRunTicks;
import com.heypixel.heypixelmod.obsoverlay.events.impl.LocalPlayerTickPreEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.MovementInputEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;
import com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting;

@ModuleInfo(a="GrimLowHop", b="\u4e25\u5cfb\u7684\u563b\u563b\u54c8\u54c8", c="Movement speed adjustments with Grim modes", d=com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory.MOVEMENT)
public class GrimLowHop
extends ModuleBase {
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a c = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Logging").a(false).a().b();
    private final Eagle d = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Start Tick").a(2.0f).b(0.0f).c(10.0f).d(1.0f).a().c();
    private final Eagle e = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Skip Ticks").a(2.0f).b(1.0f).c(10.0f).d(1.0f).a().c();
    private final Eagle f = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Ticks").a(3.0f).b(1.0f).c(10.0f).d(1.0f).a().c();
    private int g;
    private boolean h;
    private boolean i;

    private void b(String string) {
        if (this.c.m()) {
            com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.a(string);
        }
    }

    @Override
    public void d() {
        this.g = 0;
        this.h = false;
    }

    @Override
    public void e() {
        this.g = 0;
        this.h = false;
    }

    @EventTarget
    public void onPreTick(EventRunTicks eventRunTicks) {
        if (com.heypixel.heypixelmod.obsoverlay.modules.impl.c.GrimLowHop.a.f_91074_ == null || eventRunTicks.type() != com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.a) {
            return;
        }
        if ((float)this.g >= this.d.q() && !this.h) {
            com.heypixel.heypixelmod.obsoverlay.EixClientCore.d = (int)((float)com.heypixel.heypixelmod.obsoverlay.EixClientCore.d + this.e.q());
            this.h = true;
            this.i = false;
        }
    }

    @EventTarget
    public void onUpdate(LocalPlayerTickPreEvent ad2) {
        if (com.heypixel.heypixelmod.obsoverlay.modules.impl.c.GrimLowHop.a.f_91074_.m_20096_()) {
            this.g = 0;
            this.h = false;
        } else {
            ++this.g;
        }
        if (this.h && !this.i) {
            this.i = true;
            for (int i2 = 0; i2 < (int)this.f.q(); ++i2) {
                com.heypixel.heypixelmod.obsoverlay.modules.impl.c.GrimLowHop.a.f_91074_.m_8119_();
            }
        }
    }

    @EventTarget
    public void onMoveInput(MovementInputEvent l2) {
        if (com.heypixel.heypixelmod.obsoverlay.modules.impl.c.GrimLowHop.a.f_91074_ == null || com.heypixel.heypixelmod.obsoverlay.modules.impl.c.GrimLowHop.a.f_91073_ == null) {
            return;
        }
        l2.a(true);
    }
}

