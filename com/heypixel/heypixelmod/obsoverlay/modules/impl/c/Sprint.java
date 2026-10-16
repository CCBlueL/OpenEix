/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.c;

import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerMotionEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;

@ModuleInfo(a="Sprint", b="\u81ea\u52a8\u75be\u8dd1", c="Automatically sprints", d=ModuleCategory.MOVEMENT)
public class Sprint
extends ModuleBase {
    @EventTarget(a=0)
    public void onMotion(PlayerMotionEvent k2) {
        if (k2.b() == com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.a) {
            Sprint.a.f_91066_.f_92091_.m_7249_(true);
            Sprint.a.f_91066_.m_231832_().m_231514_((Object)false);
        }
    }

    @Override
    public void e() {
        Sprint.a.f_91066_.f_92091_.m_7249_(false);
    }
}

