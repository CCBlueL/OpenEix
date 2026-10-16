/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.b;

import com.heypixel.heypixelmod.mixin.O.accessors.MinecraftAccessor;
import com.heypixel.heypixelmod.obsoverlay.d.a.NumericSetting;
import com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerMotionEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;
import net.minecraft.world.item.BlockItem;

@ModuleInfo(a="FastPlace", b="\u5feb\u901f\u653e\u7f6e", c="Place blocks faster", d=ModuleCategory.MISC)
public class FastPlace
extends ModuleBase {
    private final AutoSoup c = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "CPS").a(10.0f).d(1.0f).b(5.0f).c(20.0f).a().c();
    private float d = 0.0f;

    @EventTarget
    public void onMotion(PlayerMotionEvent k2) {
        if (k2.b() == com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.a) {
            MinecraftAccessor minecraftAccessor = (MinecraftAccessor)a;
            if (FastPlace.a.f_91066_.f_92095_.m_90857_() && FastPlace.a.f_91074_.m_21205_().m_41720_() instanceof BlockItem) {
                this.d += this.c.q() / 20.0f;
                if (this.d >= 1.0f / this.c.q()) {
                    minecraftAccessor.setRightClickDelay(0);
                    this.d -= 1.0f;
                }
            } else {
                this.d = 0.0f;
            }
        }
    }
}

