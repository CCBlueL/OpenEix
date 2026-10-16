/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.b;

import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventRunTicks;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;
import com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Items;

@ModuleInfo(a="AutoSoup", b="\u81ea\u52a8\u6c64", c="Automatically uses mushroom stew when health is low", d=ModuleCategory.MISC)
public class AutoSoup
extends ModuleBase {
    public static Integer c;

    @Override
    public void d() {
        c = null;
    }

    @Override
    public void e() {
        c = null;
    }

    @EventTarget
    public void onTick(EventRunTicks eventRunTicks) {
        if (eventRunTicks.type() != com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.a) {
            return;
        }
        Minecraft minecraft = Minecraft.m_91087_();
        if (c == null) {
            Integer n2;
            if (minecraft.f_91074_.f_19797_ % 10 != 0) {
                return;
            }
            if (minecraft.f_91074_.m_21223_() < minecraft.f_91074_.m_21233_() / 2.0f && (n2 = EquipmentActionUtils.a(Items.f_42400_)) != null) {
                c = minecraft.f_91074_.m_150109_().f_35977_;
                minecraft.f_91074_.m_150109_().f_35977_ = n2;
                KeyMapping.m_90835_((InputConstants.Key)minecraft.f_91066_.f_92095_.getKey());
            }
        } else {
            minecraft.f_91074_.m_150109_().f_35977_ = c;
            c = null;
        }
    }
}

