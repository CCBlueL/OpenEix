/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.c;

import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerMotionEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.world.entity.Entity;

@ModuleInfo(a="SafeWalk", b="\u5b89\u5168\u884c\u8d70", c="Prevents you from falling off blocks", d=ModuleCategory.MOVEMENT)
public class SafeWalk
extends ModuleBase {
    public static boolean a(float f2) {
        return !SafeWalk.a.f_91073_.m_186431_((Entity)SafeWalk.a.f_91074_, SafeWalk.a.f_91074_.m_20191_().m_82386_(0.0, -0.5, 0.0).m_82377_((double)(-f2), 0.0, (double)(-f2))).iterator().hasNext();
    }

    @EventTarget
    public void onMotion(PlayerMotionEvent k2) {
        if (k2.b() == com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.a) {
            SafeWalk.a.f_91066_.f_92090_.m_7249_(SafeWalk.a.f_91074_.m_20096_() && SafeWalk.a(0.3f));
        }
    }

    @Override
    public void e() {
        boolean bl = InputConstants.m_84830_((long)a.m_91268_().m_85439_(), (int)SafeWalk.a.f_91066_.f_92090_.getKey().m_84873_());
        SafeWalk.a.f_91066_.f_92090_.m_7249_(bl);
    }
}

