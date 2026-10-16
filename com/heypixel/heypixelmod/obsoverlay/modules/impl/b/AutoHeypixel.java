/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.b;

import com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PacketInterceptEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory;
import com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.InputConstants;
import java.io.File;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Screenshot;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;

@ModuleInfo(a="AutoHeypixel", b="\u81ea\u52a8\u5c9b\u5409\u5409", c="Auto play Heypixel server.", d=ModuleCategory.MISC)
public class AutoHeypixel
extends com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase {
    public com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a c = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Auto Screenshot").a(true).a().b();
    public com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a d = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Auto Play").a(true).a().b();

    @EventTarget
    public void onPacker(PacketInterceptEvent m2) {
        boolean bl;
        if (com.heypixel.heypixelmod.obsoverlay.modules.impl.b.AutoHeypixel.a.f_91074_ == null || com.heypixel.heypixelmod.obsoverlay.modules.impl.b.AutoHeypixel.a.f_91073_ == null) {
            return;
        }
        Packet<?> packet = m2.c();
        if (packet instanceof ClientboundSetTitleTextPacket && (bl = ((ClientboundSetTitleTextPacket)packet).m_179399_().getString().contains("\u80dc\u5229"))) {
            if (this.c.m()) {
                CompletableFuture.runAsync(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(1L);
                    }
                    catch (InterruptedException interruptedException) {
                        Thread.currentThread().interrupt();
                    }
                }).thenRun(() -> a.execute(() -> {
                    if (com.heypixel.heypixelmod.obsoverlay.modules.impl.b.AutoHeypixel.a.f_91074_ != null && com.heypixel.heypixelmod.obsoverlay.modules.impl.b.AutoHeypixel.a.f_91073_ != null) {
                        Screenshot.m_92289_((File)com.heypixel.heypixelmod.obsoverlay.ClientVersion.b.clientFolder.getParentFile(), (RenderTarget)a.m_91385_(), component -> ClientChatFormatting.a(component.getString()));
                    }
                }));
            }
            if (this.d.m()) {
                CompletableFuture.runAsync(() -> {
                    try {
                        TimeUnit.MILLISECONDS.sleep(500L);
                    }
                    catch (InterruptedException interruptedException) {
                        Thread.currentThread().interrupt();
                    }
                }).thenRun(() -> a.execute(() -> {
                    if (com.heypixel.heypixelmod.obsoverlay.modules.impl.b.AutoHeypixel.a.f_91074_ != null && com.heypixel.heypixelmod.obsoverlay.modules.impl.b.AutoHeypixel.a.f_91073_ != null) {
                        int n2;
                        int n3 = com.heypixel.heypixelmod.obsoverlay.modules.impl.b.AutoHeypixel.a.f_91074_.m_150109_().f_35977_;
                        com.heypixel.heypixelmod.obsoverlay.modules.impl.b.AutoHeypixel.a.f_91074_.m_150109_().f_35977_ = n2 = 4;
                        KeyMapping.m_90835_((InputConstants.Key)com.heypixel.heypixelmod.obsoverlay.modules.impl.b.AutoHeypixel.a.f_91066_.f_92095_.getKey());
                        com.heypixel.heypixelmod.obsoverlay.modules.impl.b.AutoHeypixel.a.f_91074_.m_150109_().f_35977_ = n3;
                    }
                }));
            }
        }
    }
}

