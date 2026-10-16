/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.mixin.O;

import com.heypixel.heypixelmod.obsoverlay.EixClientCore;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventDestroyBlock;
import com.heypixel.heypixelmod.obsoverlay.events.impl.UseItemPacketEvent;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={MultiPlayerGameMode.class})
public class MixinMultiPlayerGameMode {
    @Redirect(method={"useItem"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/multiplayer/ClientPacketListener;send(Lnet/minecraft/network/protocol/Packet;)V", ordinal=0))
    public void onSendPacket(ClientPacketListener clientPacketListener, Packet<?> packet) {
        UseItemPacketEvent n2 = new UseItemPacketEvent(packet);
        EixClientCore.a().b().a(n2);
        if (!n2.a()) {
            clientPacketListener.m_104955_(n2.b());
        }
    }

    @Inject(method={"startDestroyBlock"}, at={@At(value="HEAD")})
    public void onStartDestroyBlock(BlockPos blockPos, Direction direction, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        EixClientCore.a().b().a(new EventDestroyBlock(blockPos, direction));
    }
}

