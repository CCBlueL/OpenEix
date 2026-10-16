/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.b;

import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.ClientboundPacketEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerRespawnEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;
import com.heypixel.heypixelmod.obsoverlay.utils.ElapsedTimer;
import com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;

@ModuleInfo(a="Helper", b="\u52a9\u624b", c="Bed wars Info helper", d=ModuleCategory.MISC)
public class Helper
extends ModuleBase {
    ElapsedTimer c = new ElapsedTimer();
    ElapsedTimer d = new ElapsedTimer();
    private int e;
    private int f = 1;
    private boolean g = false;

    @EventTarget
    public void onMotion(com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerMotionEvent k2) {
        if (k2.b() == com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.a) {
            if (this.c.a(30000.0) && this.g) {
                ++this.e;
                com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.a("\u7b2c" + this.e + "\u6ce2\u94bb\u77f3\u5237\u65b0");
                this.c.a();
            }
            if (this.d.a(60000.0) && this.g) {
                ++this.f;
                com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.a("\u7b2c" + this.f + "\u6ce2\u7eff\u5b9d\u77f3\u5237\u65b0");
                this.d.a();
            }
        }
    }

    @EventTarget
    public void onRespawn(PlayerRespawnEvent u2) {
        this.e = 0;
        this.f = 0;
    }

    @EventTarget(a=0)
    public void onPacket(ClientboundPacketEvent h2) {
        try {
            Object object;
            if (Helper.a.f_91074_ != null && !h2.a() && (object = h2.b()) instanceof ClientboundSystemChatPacket) {
                ClientboundSystemChatPacket clientboundSystemChatPacket = (ClientboundSystemChatPacket)object;
                if (((String)(object = clientboundSystemChatPacket.f_237849_().getString())).contains("\u6e38\u620f\u51c6\u5907\u5f00\u59cb")) {
                    this.c.a();
                    this.d.a();
                    this.g = true;
                }
                if (((String)object).contains("\u6e38\u620f\u7ed3\u675f")) {
                    this.g = false;
                }
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}

