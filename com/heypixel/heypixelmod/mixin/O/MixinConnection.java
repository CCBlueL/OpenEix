/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.mixin.O;

import com.heypixel.heypixelmod.obsoverlay.EixClientCore;
import com.heypixel.heypixelmod.obsoverlay.events.impl.NetworkPacketEvent;
import com.heypixel.heypixelmod.obsoverlay.utils.HttpRequestUtils;
import com.heypixel.heypixelmod.obsoverlay.utils.PacketUtils;
import io.netty.channel.ChannelFuture;
import io.netty.channel.SimpleChannelInboundHandler;
import java.io.IOException;
import java.net.InetSocketAddress;
import net.minecraft.network.Connection;
import net.minecraft.network.PacketListener;
import net.minecraft.network.PacketSendListener;
import net.minecraft.network.protocol.Packet;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={Connection.class})
public abstract class MixinConnection
extends SimpleChannelInboundHandler<Packet<?>> {
    @Shadow
    @Final
    private static Logger f_129465_;

    @Shadow
    private static <T extends PacketListener> void m_129517_(Packet<T> packet, PacketListener packetListener) {
    }

    @Inject(method={"connectToServer"}, at={@At(value="HEAD")})
    private static void injectHook(InetSocketAddress inetSocketAddress, boolean bl, CallbackInfoReturnable<Connection> callbackInfoReturnable) {
        try {
            HttpRequestUtils.a("http://127.0.0.1:23233/api/setHook?hook=1");
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    @Inject(method={"connect"}, at={@At(value="HEAD")})
    private static void injectHook2(InetSocketAddress inetSocketAddress, boolean bl, Connection connection, CallbackInfoReturnable<ChannelFuture> callbackInfoReturnable) {
        try {
            HttpRequestUtils.a("http://127.0.0.1:23233/api/setHook?hook=1");
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    @Shadow
    protected abstract void m_129520_(Packet<?> var1, @Nullable PacketSendListener var2);

    @Redirect(method={"channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/protocol/Packet;)V"}, at=@At(value="INVOKE", target="Lnet/minecraft/network/Connection;genericsFtw(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketListener;)V"))
    private void onGenericsFtw(Packet<?> packet, PacketListener packetListener) {
        NetworkPacketEvent g2 = new NetworkPacketEvent(com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.d, packet);
        EixClientCore.a().b().a(g2);
        if (!g2.a()) {
            MixinConnection.m_129517_(g2.c(), packetListener);
        }
    }

    @Redirect(method={"send(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketSendListener;)V"}, at=@At(value="INVOKE", target="Lnet/minecraft/network/Connection;sendPacket(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketSendListener;)V"))
    private void onSend(Connection connection, Packet<?> packet, PacketSendListener packetSendListener) {
        if (PacketUtils.b.contains(packet)) {
            PacketUtils.b.remove(packet);
            this.m_129520_(packet, packetSendListener);
        } else {
            NetworkPacketEvent g2 = new NetworkPacketEvent(com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.c, packet);
            EixClientCore.a().b().a(g2);
            if (!g2.a()) {
                this.m_129520_(g2.c(), packetSendListener);
            }
        }
    }
}

