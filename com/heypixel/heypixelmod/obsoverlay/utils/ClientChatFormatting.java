/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils;

import cn.paradisemc.ZKMIndy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.network.chat.Component;

@ZKMIndy
public class ClientChatFormatting {
    private static final String a = "\u00a77[\u00a7bN\u00a77] ";

    public static void a(Component component) {
        ChatComponent chatComponent = Minecraft.m_91087_().f_91065_.m_93076_();
        chatComponent.m_93785_(component);
    }

    public static void a(String string) {
        ClientChatFormatting.a(true, string);
    }

    public static void a(boolean bl, String string) {
        ClientChatFormatting.a(Component.m_130674_((String)((bl ? a : "") + string)));
    }
}

