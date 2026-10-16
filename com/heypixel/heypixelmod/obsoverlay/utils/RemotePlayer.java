/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils;

import com.mojang.authlib.GameProfile;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.RemotePlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RemotePlayer
extends RemotePlayer {
    private final AbstractClientPlayer a;

    public RemotePlayer(AbstractClientPlayer abstractClientPlayer) {
        super(Minecraft.m_91087_().f_91073_, new GameProfile(UUID.randomUUID(), "Real Position"));
        this.a = abstractClientPlayer;
        this.m_20359_((Entity)abstractClientPlayer);
        this.f_19794_ = true;
        this.f_19859_ = this.m_146908_();
        this.f_19860_ = this.m_146909_();
        this.f_20885_ = abstractClientPlayer.f_20885_;
        this.f_20883_ = abstractClientPlayer.f_20883_;
        this.f_20886_ = this.f_20885_;
        this.f_20884_ = this.f_20883_;
        Byte by = (Byte)abstractClientPlayer.m_20088_().m_135370_(Player.f_36089_);
        this.f_19804_.m_135381_(Player.f_36089_, (Object)by);
    }

    public boolean a() {
        return this.a.m_108559_();
    }

    @NotNull
    public ResourceLocation b() {
        return this.a.m_108560_();
    }

    public boolean c() {
        return this.a.m_108555_();
    }

    @Nullable
    public ResourceLocation d() {
        return this.a.m_108561_();
    }
}

