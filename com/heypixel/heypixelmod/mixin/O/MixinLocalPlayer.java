/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.mixin.O;

import com.heypixel.heypixelmod.obsoverlay.EixClientCore;
import com.heypixel.heypixelmod.obsoverlay.events.impl.LocalPlayerTickPreEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerMotionEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.ItemUseSlowdownEvent;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={LocalPlayer.class})
public abstract class MixinLocalPlayer
extends AbstractClientPlayer {
    @Shadow
    @Final
    public ClientPacketListener f_108617_;
    @Shadow
    @Final
    protected Minecraft f_108619_;
    @Shadow
    private boolean f_108603_;
    @Shadow
    private boolean f_108602_;
    @Shadow
    private double f_108595_;
    @Shadow
    private double f_108596_;
    @Shadow
    private double f_108597_;
    @Shadow
    private float f_108598_;
    @Shadow
    private float f_108599_;
    @Shadow
    private int f_108604_;
    @Shadow
    private boolean f_108600_;
    @Shadow
    private boolean f_108612_;

    public MixinLocalPlayer(ClientLevel clientLevel, GameProfile gameProfile) {
        super(clientLevel, gameProfile);
    }

    @Shadow
    protected abstract boolean m_108636_();

    @Shadow
    protected abstract void m_254869_();

    @Inject(method={"tick"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/player/AbstractClientPlayer;tick()V", shift=At.Shift.BEFORE)})
    public void injectUpdateEvent(CallbackInfo callbackInfo) {
        EixClientCore.a().b().a(new LocalPlayerTickPreEvent());
    }

    @Overwrite
    private void m_108640_() {
        PlayerMotionEvent k2 = new PlayerMotionEvent(com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.a, this.m_20185_(), this.m_20186_(), this.m_20189_(), this.m_146908_(), this.m_146909_(), this.m_20096_());
        EixClientCore.a().b().a(k2);
        if (k2.a()) {
            EixClientCore.a().b().a(new PlayerMotionEvent(com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.b, k2.f(), k2.g()));
        } else {
            this.m_254869_();
            boolean bl = this.m_6144_();
            if (bl != this.f_108602_) {
                ServerboundPlayerCommandPacket.Action action = bl ? ServerboundPlayerCommandPacket.Action.PRESS_SHIFT_KEY : ServerboundPlayerCommandPacket.Action.RELEASE_SHIFT_KEY;
                this.f_108617_.m_104955_((Packet)new ServerboundPlayerCommandPacket((Entity)this, action));
                this.f_108602_ = bl;
            }
            if (this.m_108636_()) {
                boolean bl2;
                double d2 = k2.c() - this.f_108595_;
                double d3 = k2.d() - this.f_108596_;
                double d4 = k2.e() - this.f_108597_;
                double d5 = k2.f() - this.f_108598_;
                double d6 = k2.g() - this.f_108599_;
                ++this.f_108604_;
                boolean bl3 = Mth.m_211592_((double)d2, (double)d3, (double)d4) > Mth.m_144952_((double)2.0E-4) || this.f_108604_ >= 20;
                boolean bl4 = bl2 = d5 != 0.0 || d6 != 0.0;
                if (this.m_20159_()) {
                    Vec3 vec3 = this.m_20184_();
                    this.f_108617_.m_104955_((Packet)new ServerboundMovePlayerPacket.PosRot(vec3.f_82479_, -999.0, vec3.f_82481_, k2.f(), k2.g(), k2.h()));
                    bl3 = false;
                } else if (bl3 && bl2) {
                    this.f_108617_.m_104955_((Packet)new ServerboundMovePlayerPacket.PosRot(k2.c(), k2.d(), k2.e(), k2.f(), k2.g(), k2.h()));
                } else if (bl3) {
                    this.f_108617_.m_104955_((Packet)new ServerboundMovePlayerPacket.Pos(k2.c(), k2.d(), k2.e(), k2.h()));
                } else if (bl2) {
                    this.f_108617_.m_104955_((Packet)new ServerboundMovePlayerPacket.Rot(k2.f(), k2.g(), k2.h()));
                } else if (this.f_108600_ != k2.h()) {
                    this.f_108617_.m_104955_((Packet)new ServerboundMovePlayerPacket.StatusOnly(k2.h()));
                }
                if (bl3) {
                    this.f_108595_ = k2.c();
                    this.f_108596_ = k2.d();
                    this.f_108597_ = k2.e();
                    this.f_108604_ = 0;
                }
                if (bl2) {
                    this.f_108598_ = k2.f();
                    this.f_108599_ = k2.g();
                }
                this.f_108600_ = k2.h();
                this.f_108612_ = (Boolean)this.f_108619_.f_91066_.m_231812_().m_231551_();
            }
            EixClientCore.a().b().a(new PlayerMotionEvent(com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.b, k2.f(), k2.g()));
        }
    }

    @Redirect(method={"aiStep"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/player/LocalPlayer;isUsingItem()Z", ordinal=0))
    public boolean onSlowdown(LocalPlayer localPlayer) {
        ItemUseSlowdownEvent z2 = new ItemUseSlowdownEvent(localPlayer.m_6117_());
        EixClientCore.a().b().a(z2);
        return z2.a();
    }
}

