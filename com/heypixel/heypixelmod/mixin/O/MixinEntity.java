/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.mixin.O;

import com.heypixel.heypixelmod.obsoverlay.EixClientCore;
import com.heypixel.heypixelmod.obsoverlay.events.impl.RelativeMovementVectorEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.StuckInBlockEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EntityRotationEvent;
import com.heypixel.heypixelmod.obsoverlay.utils.RemotePlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={Entity.class})
public abstract class MixinEntity {
    @Shadow
    protected Vec3 f_19865_;

    @Shadow
    public abstract float m_5686_(float var1);

    @Shadow
    public abstract float m_5675_(float var1);

    @Shadow
    protected abstract Vec3 m_20171_(float var1, float var2);

    @Overwrite
    public final Vec3 m_20252_(float f2) {
        float f3 = this.m_5686_(f2);
        float f4 = this.m_5675_(f2);
        Entity entity = (Entity)this;
        if (entity == Minecraft.m_91087_().f_91074_) {
            EntityRotationEvent o2 = new EntityRotationEvent(entity, f4, f3);
            EixClientCore.a().b().a(o2);
            f4 = o2.b;
            f3 = o2.c;
        }
        return this.m_20171_(f3, f4);
    }

    @ModifyArg(method={"moveRelative"}, at=@At(value="INVOKE", target="Lnet/minecraft/world/entity/Entity;getInputVector(Lnet/minecraft/world/phys/Vec3;FF)Lnet/minecraft/world/phys/Vec3;", ordinal=0), index=2)
    private float modifyYaw(float f2) {
        Entity entity = (Entity)this;
        if (Minecraft.m_91087_().f_91074_ != entity) {
            return f2;
        }
        RelativeMovementVectorEvent ab2 = new RelativeMovementVectorEvent(f2);
        EixClientCore.a().b().a(ab2);
        return ab2.a();
    }

    @Inject(method={"makeStuckInBlock"}, at={@At(value="RETURN")})
    private void makeStuckInBlock(BlockState blockState, Vec3 vec3, CallbackInfo callbackInfo) {
        Entity entity = (Entity)this;
        if (Minecraft.m_91087_().f_91074_ == entity) {
            StuckInBlockEvent ac2 = new StuckInBlockEvent(blockState, vec3);
            EixClientCore.a().b().a(ac2);
            if (ac2.a()) {
                this.f_19865_ = Vec3.f_82478_;
                return;
            }
            this.f_19865_ = ac2.c();
        }
    }

    @Inject(method={"push(Lnet/minecraft/world/entity/Entity;)V"}, at={@At(value="HEAD")}, cancellable=true)
    public void push(Entity entity, CallbackInfo callbackInfo) {
        if (entity instanceof RemotePlayer) {
            callbackInfo.cancel();
        }
    }
}

