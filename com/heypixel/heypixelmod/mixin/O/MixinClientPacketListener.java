/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.mixin.O;

import com.heypixel.heypixelmod.obsoverlay.EixClientCore;
import com.heypixel.heypixelmod.obsoverlay.events.impl.ServerPositionCorrectionEvent;
import com.heypixel.heypixelmod.obsoverlay.utils.HttpRequestUtils;
import java.io.IOException;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundLoginPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={ClientPacketListener.class})
public class MixinClientPacketListener {
    @Redirect(method={"handleMovePlayer"}, at=@At(value="INVOKE", target="Lnet/minecraft/network/Connection;send(Lnet/minecraft/network/protocol/Packet;)V", ordinal=1))
    public void onSendPacket(Connection connection, Packet<?> packet) {
        ServerPositionCorrectionEvent w2 = new ServerPositionCorrectionEvent(packet);
        EixClientCore.a().b().a(w2);
        connection.m_129512_(w2.a());
    }

    @Inject(method={"handleLogin"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/telemetry/WorldSessionTelemetryManager;onPlayerInfoReceived(Lnet/minecraft/world/level/GameType;Z)V", shift=At.Shift.AFTER)}, cancellable=true)
    private void onLogin(ClientboundLoginPacket clientboundLoginPacket, CallbackInfo callbackInfo) {
        try {
            HttpRequestUtils.a("http://127.0.0.1:23233/api/setHook?hook=0");
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }
}

