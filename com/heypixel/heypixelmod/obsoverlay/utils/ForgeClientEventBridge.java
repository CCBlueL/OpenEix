/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils;

import com.heypixel.heypixelmod.obsoverlay.EixClientCore;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.ChatMessageEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerMotionEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerRespawnEvent;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ForgeClientEventBridge {
    @SubscribeEvent
    public void a(RenderGuiEvent.Post post) {
    }

    @SubscribeEvent
    public void a(ClientChatEvent clientChatEvent) {
        ChatMessageEvent e2 = new ChatMessageEvent(clientChatEvent.getMessage());
        EixClientCore.a().b().a(e2);
        if (e2.a()) {
            clientChatEvent.setCanceled(true);
        }
    }

    @EventTarget
    public void onMotion(PlayerMotionEvent k2) {
        if (k2.b() == com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.a && Minecraft.m_91087_().f_91074_.f_19797_ <= 1) {
            EixClientCore.a().b().a(new PlayerRespawnEvent());
        }
    }
}

