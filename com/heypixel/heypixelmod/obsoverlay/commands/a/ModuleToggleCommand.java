/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.commands.a;

import com.heypixel.heypixelmod.obsoverlay.commands.CommandInfo;
import com.heypixel.heypixelmod.obsoverlay.commands.BaseCommand;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;

@CommandInfo(a="toggle", b="Toggle a module", c={"t"})
public class ModuleToggleCommand
extends BaseCommand {
    @Override
    public void a(String[] stringArray) {
        if (stringArray.length == 1) {
            String string = stringArray[0];
            try {
                ModuleBase b2 = com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().c(string);
                if (b2 != null) {
                    b2.f();
                } else {
                    com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.a("Invalid module.");
                }
            }
            catch (com.heypixel.heypixelmod.obsoverlay.EixClientCore.b b3) {
                com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.a("Invalid module.");
            }
        }
    }

    @Override
    public String[] b(String[] stringArray) {
        return (String[])com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a().stream().map(ModuleBase::h).filter(string -> string.toLowerCase().startsWith(stringArray.length == 0 ? "" : stringArray[0].toLowerCase())).toArray(String[]::new);
    }
}

