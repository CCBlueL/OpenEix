/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.render;

import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.ScoreboardTextEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerListTextEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

@ModuleInfo(a="ServerNameSpoof", b="\u670d\u52a1\u5668\u540d\u5b57\u4f2a\u88c5", c="Spoof the server name", d=ModuleCategory.RENDER)
public class ServerNameSpoof
extends ModuleBase {
    @EventTarget
    public void onRenderScoreboard(ScoreboardTextEvent r2) {
        String string = r2.a().getString();
        if (string.contains("\u5e03\u5409\u5c9b")) {
            MutableComponent mutableComponent = Component.m_237113_((String)"\u00a7e2b2t.gg");
            mutableComponent.m_6270_(r2.a().m_7383_());
            r2.a((Component)mutableComponent);
        }
    }

    @EventTarget
    public void onRenderTab(PlayerListTextEvent t2) {
        String string = t2.b().getString();
        if (string.contains("\u5e03\u5409\u5c9b")) {
            if (t2.a() == com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.g) {
                t2.a((Component)Component.m_237113_((String)"\u00a7e2b2t.gg"));
            } else if (t2.a() == com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.f) {
                t2.a((Component)Component.m_237113_((String)""));
            }
        }
    }
}

