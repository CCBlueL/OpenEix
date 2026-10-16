/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.render;

import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerListTextEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;
import net.minecraft.network.chat.Component;
import org.apache.commons.lang3.StringUtils;

@ModuleInfo(a="NameProtect", b="\u540d\u79f0\u4fdd\u62a4", c="Protect your name", d=ModuleCategory.RENDER)
public class NameProtect
extends ModuleBase {
    public static NameProtect c;

    public NameProtect() {
        c = this;
    }

    public static String b(String string) {
        if (!c.m() || NameProtect.a.f_91074_ == null) {
            return string;
        }
        return string.contains(NameProtect.a.f_91074_.m_7755_().getString()) ? StringUtils.replace((String)string, (String)NameProtect.a.f_91074_.m_7755_().getString(), (String)"\u00a7dAlpha\u00a77") : string;
    }

    @EventTarget
    public void onRenderTab(PlayerListTextEvent t2) {
        t2.a((Component)Component.m_237113_((String)NameProtect.b(t2.b().getString())));
    }
}

