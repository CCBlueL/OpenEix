/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils;

import com.heypixel.heypixelmod.obsoverlay.EixClientCore;
import com.heypixel.heypixelmod.obsoverlay.events.impl.ClientboundPacketEvent;
import io.netty.handler.codec.DecoderException;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.RunningOnDifferentThreadException;
import net.minecraft.util.thread.BlockableEventLoop;
import org.slf4j.Logger;

public class PacketSerializationUtils {
    public static <T extends PacketListener> void a(Logger logger, Packet<T> packet, T t2, BlockableEventLoop<?> blockableEventLoop) throws RunningOnDifferentThreadException {
        if (!blockableEventLoop.m_18695_()) {
            blockableEventLoop.m_201446_(() -> {
                if (t2.m_6198_()) {
                    try {
                        ClientboundPacketEvent h2 = new ClientboundPacketEvent((Packet<ClientGamePacketListener>)packet);
                        if (blockableEventLoop.m_18695_()) {
                            EixClientCore.a().b().a(h2);
                            if (h2.a()) {
                                return;
                            }
                        }
                        packet.m_5797_(t2);
                    }
                    catch (Exception exception) {
                        if (t2.m_201767_()) {
                            throw exception;
                        }
                        logger.error("Failed to handle packet {}, suppressing error", (Object)packet, (Object)exception);
                    }
                } else {
                    logger.debug("Ignoring packet due to disconnection: {}", (Object)packet);
                }
            });
            throw RunningOnDifferentThreadException.f_136017_;
        }
    }

    public static byte[] a(FriendlyByteBuf friendlyByteBuf, int n2) {
        int n3 = friendlyByteBuf.m_130242_() - 1;
        if (n3 > n2) {
            throw new DecoderException("ByteArray with size " + n3 + " is bigger than allowed " + n2);
        }
        byte[] byArray = new byte[n3];
        friendlyByteBuf.readBytes(byArray);
        return byArray;
    }
}

