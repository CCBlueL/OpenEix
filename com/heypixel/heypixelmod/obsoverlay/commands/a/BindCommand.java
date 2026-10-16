/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.commands.a;

import com.heypixel.heypixelmod.obsoverlay.a.RuntimeExceptionWithMarkers;
import com.heypixel.heypixelmod.obsoverlay.commands.CommandInfo;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.KeyboardKeyPressEvent;
import com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting;
import com.mojang.blaze3d.platform.InputConstants;

@CommandInfo(a="bind", b="Bind a command to a key", c={"b"})
public class BindCommand
extends com.heypixel.heypixelmod.obsoverlay.commands.BaseCommand {
    @Override
    public void a(String[] stringArray) {
        block14: {
            if (stringArray.length == 1) {
                final String string = stringArray[0];
                try {
                    final com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase b2 = com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().c(string);
                    if (b2 != null) {
                        ClientChatFormatting.a("Press a key to bind " + string + " to.");
                        com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().b().a(new Object(){

                            @EventTarget
                            public void onKey(KeyboardKeyPressEvent j2) {
                                if (j2.c()) {
                                    b2.b(j2.b());
                                    InputConstants.Key key = InputConstants.m_84827_((int)j2.b(), (int)0);
                                    String string2 = key.m_84875_().getString().toUpperCase();
                                    ClientChatFormatting.a("Bound " + string + " to " + string2 + ".");
                                    com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().b().b(this);
                                    com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().i().c();
                                }
                            }
                        });
                        break block14;
                    }
                    ClientChatFormatting.a("Invalid module.");
                }
                catch (ConfigCommand b3) {
                    ClientChatFormatting.a("Invalid module.");
                }
            } else if (stringArray.length == 2) {
                String string = stringArray[0];
                String string2 = stringArray[1];
                try {
                    com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase b4 = com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().c(string);
                    if (b4 != null) {
                        if (string2.equalsIgnoreCase("none")) {
                            b4.b(InputConstants.f_84822_.m_84873_());
                            ClientChatFormatting.a("Unbound " + string + ".");
                            com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().i().c();
                        } else {
                            InputConstants.Key key = InputConstants.m_84851_((String)("key.keyboard." + string2.toLowerCase()));
                            if (key != InputConstants.f_84822_) {
                                b4.b(key.m_84873_());
                                ClientChatFormatting.a("Bound " + string + " to " + string2.toUpperCase() + ".");
                                com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().i().c();
                            } else {
                                ClientChatFormatting.a("Invalid key.");
                            }
                        }
                        break block14;
                    }
                    ClientChatFormatting.a("Invalid module.");
                }
                catch (ConfigCommand b5) {
                    ClientChatFormatting.a("Invalid module.");
                }
            } else {
                ClientChatFormatting.a("Usage: .bind <module> [key]");
            }
        }
    }

    @Override
    public String[] b(String[] stringArray) {
        return (String[])com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a().stream().map(com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase::h).filter(string -> string.toLowerCase().startsWith(stringArray.length == 0 ? "" : stringArray[0].toLowerCase())).toArray(String[]::new);
    }
}

