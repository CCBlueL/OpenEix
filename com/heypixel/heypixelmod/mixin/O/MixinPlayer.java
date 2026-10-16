/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.mixin.O;

import com.heypixel.heypixelmod.obsoverlay.EixClientCore;
import com.heypixel.heypixelmod.obsoverlay.events.impl.StayingOnGroundSurfaceEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerAttackMotionEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerAttackRotationEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={Player.class})
public abstract class MixinPlayer
extends LivingEntity {
    protected MixinPlayer(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Redirect(method={"attack"}, at=@At(value="INVOKE", target="Lnet/minecraft/world/entity/player/Player;getYRot()F"))
    private float hookFixRotation(Player player) {
        PlayerAttackRotationEvent c2 = new PlayerAttackRotationEvent(player.m_146908_());
        EixClientCore.a().b().a(c2);
        return c2.a();
    }

    @Redirect(method={"attack"}, at=@At(value="INVOKE", target="Lnet/minecraft/world/entity/player/Player;setDeltaMovement(Lnet/minecraft/world/phys/Vec3;)V"))
    private void hookSetDeltaMovement(Player player, Vec3 vec3) {
        PlayerAttackMotionEvent b2 = new PlayerAttackMotionEvent();
        EixClientCore.a().b().a(b2);
        if (!b2.a()) {
            player.m_20256_(vec3);
        }
    }

    @Redirect(method={"attack"}, at=@At(value="INVOKE", target="Lnet/minecraft/world/entity/player/Player;setSprinting(Z)V"))
    private void hookSetSprinting(Player player, boolean bl) {
        PlayerAttackMotionEvent b2 = new PlayerAttackMotionEvent();
        EixClientCore.a().b().a(b2);
        if (!b2.a()) {
            player.m_6858_(bl);
        }
    }

    @Inject(method={"isStayingOnGroundSurface"}, at={@At(value="RETURN")}, cancellable=true)
    private void isStayingOnGroundSurface(CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        StayingOnGroundSurfaceEvent aa2 = new StayingOnGroundSurfaceEvent((Boolean)callbackInfoReturnable.getReturnValue());
        EixClientCore.a().b().a(aa2);
        callbackInfoReturnable.setReturnValue((Object)aa2.a());
    }
}

