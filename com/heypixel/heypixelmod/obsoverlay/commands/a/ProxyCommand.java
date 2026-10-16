/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.commands.a;

import com.heypixel.heypixelmod.obsoverlay.b.ClientConfig;
import com.heypixel.heypixelmod.obsoverlay.commands.CommandInfo;
import com.heypixel.heypixelmod.obsoverlay.commands.BaseCommand;
import com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting;

@CommandInfo(a="proxy", b="Set client proxy", c={"prox"})
public class ProxyCommand
extends BaseCommand {
    @Override
    public void a(String[] stringArray) {
        BaseCommand.c c2 = com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().i().a().proxy;
        if (stringArray.length == 0) {
            if (c2.host == null) {
                ClientChatFormatting.a("No proxy set.");
            } else {
                ClientChatFormatting.a("Current Proxy: " + c2.host + ":" + c2.port);
            }
        } else if (stringArray.length == 1) {
            if (stringArray[0].equals("cancel")) {
                c2.host = null;
                c2.port = 0;
                ClientChatFormatting.a("Proxy cancelled.");
            } else {
                try {
                    String[] stringArray2 = stringArray[0].split(":");
                    c2.host = stringArray2[0];
                    c2.port = Integer.parseInt(stringArray2[1]);
                    ClientChatFormatting.a("Proxy set to " + c2.host + ":" + c2.port);
                }
                catch (Exception exception) {
                    ClientChatFormatting.a("Invalid proxy.");
                }
            }
        }
    }

    @Override
    public String[] b(String[] stringArray) {
        return new String[0];
    }
}

