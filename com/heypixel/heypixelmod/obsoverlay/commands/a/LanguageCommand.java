/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.commands.a;

import com.heypixel.heypixelmod.obsoverlay.EixClientCore;
import com.heypixel.heypixelmod.obsoverlay.commands.CommandInfo;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerMotionEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.LanguageSelectScreen;
import net.minecraft.client.gui.screens.Screen;

@CommandInfo(a="language", b="Open language gui.", c={"lang"})
public class LanguageCommand
extends com.heypixel.heypixelmod.obsoverlay.commands.BaseCommand {
    @Override
    public void a(String[] stringArray) {
        EixClientCore.a().b().a(new Object(){

            @EventTarget
            public void onMotion(PlayerMotionEvent k2) {
                if (k2.b() == com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.a) {
                    Minecraft.m_91087_().m_91152_((Screen)new LanguageSelectScreen(null, Minecraft.m_91087_().f_91066_, Minecraft.m_91087_().m_91102_()));
                    EixClientCore.a().b().b(this);
                }
            }
        });
    }

    @Override
    public String[] b(String[] stringArray) {
        return new String[0];
    }
}

