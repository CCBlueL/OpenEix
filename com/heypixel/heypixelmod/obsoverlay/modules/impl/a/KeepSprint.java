/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.a;

import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;

@ModuleInfo(a="KeepSprint", b="\u4fdd\u6301\u75be\u8dd1", c="Maintain a sprinting state while attacking.", d=ModuleCategory.COMBAT)
public class KeepSprint
extends ModuleBase {
    @EventTarget
    public void onAttackSlowdown(com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerAttackMotionEvent b2) {
        b2.a(true);
    }
}

