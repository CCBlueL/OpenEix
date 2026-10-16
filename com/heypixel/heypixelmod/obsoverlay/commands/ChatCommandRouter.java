/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.commands;

import com.heypixel.heypixelmod.obsoverlay.commands.a.BindCommand;
import com.heypixel.heypixelmod.obsoverlay.commands.a.LanguageCommand;
import com.heypixel.heypixelmod.obsoverlay.commands.a.MusicCommand;
import com.heypixel.heypixelmod.obsoverlay.commands.a.ProxyCommand;
import com.heypixel.heypixelmod.obsoverlay.commands.a.ModuleToggleCommand;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import java.util.HashMap;
import java.util.Map;

public class ChatCommandRouter {
    public static final String a = ".";
    public final Map<String, com.heypixel.heypixelmod.obsoverlay.commands.BaseCommand> b = new HashMap<String, com.heypixel.heypixelmod.obsoverlay.commands.BaseCommand>();

    public ChatCommandRouter() {
        try {
            this.a();
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
        com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().b().a(this);
    }

    private void a() {
        this.a(new BaseCommand());
        this.a(new f());
        this.a(new com.heypixel.heypixelmod.obsoverlay.commands.BaseCommand.b());
        this.a(new c());
        this.a(new e());
        this.a(new d());
    }

    private void a(com.heypixel.heypixelmod.obsoverlay.commands.BaseCommand a2) {
        a2.a();
        this.b.put(a2.b().toLowerCase(), a2);
        for (String string : a2.d()) {
            this.b.put(string.toLowerCase(), a2);
        }
    }

    @EventTarget
    public void onChat(com.heypixel.heypixelmod.obsoverlay.events.impl.ChatMessageEvent e2) {
        if (e2.b().startsWith(a)) {
            e2.a(true);
            String string = e2.b().substring(a.length());
            String[] stringArray = string.split(" ");
            if (stringArray.length < 1) {
                com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.a("Invalid command.");
                return;
            }
            String string2 = stringArray[0].toLowerCase();
            com.heypixel.heypixelmod.obsoverlay.commands.BaseCommand a2 = this.b.get(string2);
            if (a2 == null) {
                com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.a("Invalid command.");
                return;
            }
            String[] stringArray2 = new String[stringArray.length - 1];
            System.arraycopy(stringArray, 1, stringArray2, 0, stringArray2.length);
            a2.a(stringArray2);
        }
    }
}

