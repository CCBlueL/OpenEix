/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.commands.a;

import com.heypixel.heypixelmod.obsoverlay.EixClientCore;
import com.heypixel.heypixelmod.obsoverlay.commands.CommandInfo;
import com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import net.minecraft.client.Minecraft;

@CommandInfo(a="config", b="Open client config folder or manage cloud configs.", c={"conf"})
public class ConfigCommand
extends com.heypixel.heypixelmod.obsoverlay.commands.BaseCommand {
    @Override
    public void a(String[] stringArray) {
        String string;
        if (stringArray.length == 0) {
            this.e();
            return;
        }
        switch (string = stringArray[0].toLowerCase()) {
            case "open": 
            case "folder": {
                this.f();
                break;
            }
            case "save": {
                this.a(stringArray.length > 1 ? stringArray[1] : "default");
                break;
            }
            case "load": {
                this.b(stringArray.length > 1 ? stringArray[1] : "default");
                break;
            }
            case "list": {
                this.g();
                break;
            }
            default: {
                ClientChatFormatting.a("\u00a7c\u672a\u77e5\u7684\u5b50\u547d\u4ee4: " + string);
                this.e();
            }
        }
    }

    @Override
    public String[] b(String[] stringArray) {
        if (stringArray.length == 1) {
            return new String[]{"open", "folder", "save", "load", "list"};
        }
        if (stringArray.length == 2 && (stringArray[0].equals("save") || stringArray[0].equals("load"))) {
            return this.h();
        }
        return new String[0];
    }

    private void e() {
        ClientChatFormatting.a("\u00a7b=== \u914d\u7f6e\u547d\u4ee4\u5e2e\u52a9 ===");
        ClientChatFormatting.a("\u00a77/config folder \u00a7f- \u6253\u5f00\u914d\u7f6e\u6587\u4ef6\u5939");
        ClientChatFormatting.a("\u00a77/config save [\u540d\u79f0] \u00a7f- \u4fdd\u5b58\u5f53\u524d\u914d\u7f6e");
        ClientChatFormatting.a("\u00a77/config load [\u540d\u79f0] \u00a7f- \u52a0\u8f7d\u6307\u5b9a\u914d\u7f6e");
        ClientChatFormatting.a("\u00a77/config list \u00a7f- \u5217\u51fa\u6240\u6709\u914d\u7f6e");
    }

    private void f() {
        try {
            File file = new File(Minecraft.m_91087_().f_91069_, "config/Naven");
            if (!file.exists()) {
                file.mkdirs();
            }
            Desktop.getDesktop().browse(file.toURI());
            ClientChatFormatting.a("\u00a7a\u5df2\u6253\u5f00\u914d\u7f6e\u6587\u4ef6\u5939");
        }
        catch (IOException iOException) {
            ClientChatFormatting.a("\u00a7c\u65e0\u6cd5\u6253\u5f00\u914d\u7f6e\u6587\u4ef6\u5939: " + iOException.getMessage());
        }
    }

    private void a(String string) {
        try {
            com.heypixel.heypixelmod.obsoverlay.ClientVersion.b b2 = EixClientCore.a().i();
            b2.b(string);
            ClientChatFormatting.a("\u00a7a\u914d\u7f6e\u5df2\u4fdd\u5b58\u4e3a: " + string);
        }
        catch (Exception exception) {
            ClientChatFormatting.a("\u00a7c\u4fdd\u5b58\u914d\u7f6e\u5931\u8d25: " + exception.getMessage());
        }
    }

    private void b(String string) {
        try {
            com.heypixel.heypixelmod.obsoverlay.ClientVersion.b b2 = EixClientCore.a().i();
            b2.c(string);
            ClientChatFormatting.a("\u00a7a\u914d\u7f6e\u5df2\u52a0\u8f7d: " + string);
        }
        catch (Exception exception) {
            ClientChatFormatting.a("\u00a7c\u52a0\u8f7d\u914d\u7f6e\u5931\u8d25: " + exception.getMessage());
        }
    }

    private void g() {
        try {
            File file2 = new File(Minecraft.m_91087_().f_91069_, "config/Naven");
            if (!file2.exists()) {
                ClientChatFormatting.a("\u00a77\u6682\u65e0\u914d\u7f6e\u6587\u4ef6");
                return;
            }
            File[] fileArray = file2.listFiles((file, string) -> string.endsWith(".json"));
            if (fileArray == null || fileArray.length == 0) {
                ClientChatFormatting.a("\u00a77\u6682\u65e0\u914d\u7f6e\u6587\u4ef6");
                return;
            }
            ClientChatFormatting.a("\u00a7b\u53ef\u7528\u7684\u914d\u7f6e\u6587\u4ef6:");
            for (File file3 : fileArray) {
                String string2 = file3.getName().replace(".json", "");
                ClientChatFormatting.a("\u00a77- " + string2);
            }
        }
        catch (Exception exception) {
            ClientChatFormatting.a("\u00a7c\u5217\u51fa\u914d\u7f6e\u6587\u4ef6\u5931\u8d25: " + exception.getMessage());
        }
    }

    private String[] h() {
        try {
            File file2 = new File(Minecraft.m_91087_().f_91069_, "config/Naven");
            if (!file2.exists()) {
                return new String[0];
            }
            File[] fileArray = file2.listFiles((file, string) -> string.endsWith(".json"));
            if (fileArray == null) {
                return new String[0];
            }
            String[] stringArray = new String[fileArray.length];
            for (int i2 = 0; i2 < fileArray.length; ++i2) {
                stringArray[i2] = fileArray[i2].getName().replace(".json", "");
            }
            return stringArray;
        }
        catch (Exception exception) {
            return new String[0];
        }
    }
}

