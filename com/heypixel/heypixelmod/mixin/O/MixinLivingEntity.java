/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.mixin.O;

import com.heypixel.heypixelmod.obsoverlay.EixClientCore;
import com.heypixel.heypixelmod.obsoverlay.events.impl.LivingEntityPitchEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.LivingEntityYawEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.LivingEntityRotationEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.AntiNausea;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.FullBright;
import net.minecraft.client.Minecraft;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={LivingEntity.class})
public abstract class MixinLivingEntity
extends Entity {
    public MixinLivingEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Redirect(at=@At(value="INVOKE", target="Lnet/minecraft/world/entity/LivingEntity;getYRot()F", opcode=182, ordinal=0), method={"jumpFromGround"})
    private float modifyJumpYaw(LivingEntity livingEntity) {
        if (livingEntity != Minecraft.m_91087_().f_91074_) {
            return livingEntity.m_146908_();
        }
        LivingEntityYawEvent i2 = new LivingEntityYawEvent(livingEntity.m_146908_());
        EixClientCore.a().b().a(i2);
        return i2.a();
    }

    @Redirect(method={"travel"}, at=@At(value="INVOKE", target="Lnet/minecraft/world/entity/LivingEntity;getXRot()F"))
    private float hookModifyFallFlyingPitch(LivingEntity livingEntity) {
        if (livingEntity != Minecraft.m_91087_().f_91074_) {
            return livingEntity.m_146909_();
        }
        LivingEntityPitchEvent f2 = new LivingEntityPitchEvent(livingEntity.m_146909_());
        EixClientCore.a().b().a(f2);
        return f2.a();
    }

    @Inject(method={"hasEffect"}, at={@At(value="HEAD")}, cancellable=true)
    private void hasEffect(MobEffect mobEffect, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        LivingEntity livingEntity = (LivingEntity)this;
        if (livingEntity == Minecraft.m_91087_().f_91074_) {
            FullBright k2 = EixClientCore.a().g().a(FullBright.class);
            if (mobEffect == MobEffects.f_19611_ && k2.m()) {
                callbackInfoReturnable.setReturnValue((Object)true);
                callbackInfoReturnable.cancel();
            }
            AntiNausea c2 = EixClientCore.a().g().a(AntiNausea.class);
            if (mobEffect == MobEffects.f_19604_ && c2.m()) {
                callbackInfoReturnable.setReturnValue((Object)false);
                callbackInfoReturnable.cancel();
            }
        }
    }

    @Redirect(at=@At(value="INVOKE", target="Lnet/minecraft/world/entity/LivingEntity;getYRot()F"), method={"tickHeadTurn"})
    private float modifyHeadYaw(LivingEntity livingEntity) {
        if (livingEntity == Minecraft.m_91087_().f_91074_) {
            LivingEntityRotationEvent v2 = new LivingEntityRotationEvent(livingEntity.m_146908_(), 0.0f, 0.0f, 0.0f);
            EixClientCore.a().b().a(v2);
            return v2.a();
        }
        return livingEntity.m_146908_();
    }
}

