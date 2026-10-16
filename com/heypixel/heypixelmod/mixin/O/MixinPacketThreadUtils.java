/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.mixin.O;

import com.heypixel.heypixelmod.obsoverlay.utils.PacketSerializationUtils;
import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketUtils;
import net.minecraft.server.RunningOnDifferentThreadException;
import net.minecraft.util.thread.BlockableEventLoop;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={PacketUtils.class})
public class MixinPacketThreadUtils {
    @Shadow
    @Final
    private static Logger f_131354_;

    @Inject(method={"ensureRunningOnSameThread(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketListener;Lnet/minecraft/util/thread/BlockableEventLoop;)V"}, at={@At(value="HEAD")}, cancellable=true)
    private static <T extends PacketListener> void onEnsureRunningOnSameThread(Packet<T> packet, T t2, BlockableEventLoop<?> blockableEventLoop, CallbackInfo callbackInfo) throws RunningOnDifferentThreadException {
        callbackInfo.cancel();
        PacketSerializationUtils.a(f_131354_, packet, t2, blockableEventLoop);
    }
}

