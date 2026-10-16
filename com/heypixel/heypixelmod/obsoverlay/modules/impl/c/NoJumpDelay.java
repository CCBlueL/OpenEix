/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.c;

import com.heypixel.heypixelmod.mixin.O.accessors.LivingEntityAccessor;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;

@ModuleInfo(a="NoJumpDelay", b="\u65e0\u8df3\u8dc3\u5ef6\u8fdf", c="Removes the delay when jumping", d=ModuleCategory.MOVEMENT)
public class NoJumpDelay
extends ModuleBase {
    @EventTarget
    public void onMotion(com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerMotionEvent k2) {
        if (k2.b() == com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.a) {
            ((LivingEntityAccessor)NoJumpDelay.a.f_91074_).setNoJumpDelay(0);
        }
    }
}

