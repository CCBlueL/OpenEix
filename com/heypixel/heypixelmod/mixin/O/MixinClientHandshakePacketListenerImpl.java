/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.mixin.O;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientHandshakePacketListenerImpl;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.login.ClientboundGameProfilePacket;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={ClientHandshakePacketListenerImpl.class})
public class MixinClientHandshakePacketListenerImpl {
    @Shadow
    @Final
    private Connection f_104522_;
    @Shadow
    @Final
    private Minecraft f_104519_;

    @Inject(method={"handleGameProfile"}, at={@At(value="INVOKE", target="Lnet/minecraft/network/Connection;setProtocol(Lnet/minecraft/network/ConnectionProtocol;)V", shift=At.Shift.AFTER)}, cancellable=true)
    public void onSuccess(ClientboundGameProfilePacket clientboundGameProfilePacket, CallbackInfo callbackInfo) {
        GameProfile gameProfile = clientboundGameProfilePacket.m_134774_();
    }
}

