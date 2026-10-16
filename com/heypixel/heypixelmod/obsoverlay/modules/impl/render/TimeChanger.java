/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.render;

import com.heypixel.heypixelmod.obsoverlay.d.a.NumericSetting;
import com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerMotionEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PacketInterceptEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;
import net.minecraft.network.protocol.game.ClientboundSetTimePacket;

@ModuleInfo(a="TimeChanger", b="\u65f6\u95f4\u66f4\u6539\u5668", c="Change the time of the world", d=ModuleCategory.RENDER)
public class TimeChanger
extends ModuleBase {
    AntiNausea c = SettingBuilder.a(this, "World Time").a(8000.0f).d(1.0f).b(0.0f).c(24000.0f).a().c();

    @EventTarget
    public void onMotion(PlayerMotionEvent k2) {
        if (k2.b() == com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.a) {
            TimeChanger.a.f_91073_.m_104746_((long)this.c.q());
        }
    }

    @EventTarget
    public void onPacket(PacketInterceptEvent m2) {
        if (m2.c() instanceof ClientboundSetTimePacket) {
            m2.a(true);
        }
    }
}

