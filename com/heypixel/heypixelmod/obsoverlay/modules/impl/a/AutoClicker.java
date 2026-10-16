/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.a;

import com.heypixel.heypixelmod.mixin.O.accessors.MinecraftAccessor;
import com.heypixel.heypixelmod.obsoverlay.d.a.NumericSetting;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerMotionEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;
import com.heypixel.heypixelmod.obsoverlay.utils.MathUtils;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.phys.HitResult;

@ModuleInfo(a="AutoClicker", b="\u81ea\u52a8\u70b9\u51fb", c="Automatically clicks for you", d=ModuleCategory.COMBAT)
public class AutoClicker
extends ModuleBase {
    private final CrystalAura c = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Min CPS").a(12.0f).d(1.0f).b(1.0f).c(20.0f).a().c();
    private final CrystalAura d = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Max CPS").a(18.0f).d(1.0f).b(1.0f).c(20.0f).a().c();
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a e = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Item Check").a(false).a().b();
    private long f = 0L;

    @EventTarget
    public void onMotion(PlayerMotionEvent k2) {
        if (k2.b() == com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.a) {
            double d2;
            long l2;
            long l3;
            MinecraftAccessor minecraftAccessor = (MinecraftAccessor)a;
            Item item = com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AutoClicker.a.f_91074_.m_21205_().m_41720_();
            if (com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AutoClicker.a.f_91066_.f_92096_.m_90857_() && (item instanceof SwordItem || item instanceof AxeItem || !this.e.m()) && com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AutoClicker.a.f_91077_.m_6662_() != HitResult.Type.BLOCK && (l3 = System.currentTimeMillis()) - this.f >= (l2 = (long)((d2 = 1000.0 / (double)MathUtils.a((int)this.c.q(), (int)this.d.q())) + (Math.random() - 0.5) * d2 * 0.4))) {
                minecraftAccessor.setMissTime(0);
                KeyMapping.m_90835_((InputConstants.Key)com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AutoClicker.a.f_91066_.f_92096_.getKey());
                this.f = l3;
            }
        }
    }
}

