/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.mixin.O;

import com.heypixel.heypixelmod.obsoverlay.EixClientCore;
import com.heypixel.heypixelmod.obsoverlay.events.impl.UseItemRayTraceEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value={Item.class})
public class MixinItem {
    @Redirect(method={"getPlayerPOVHitResult"}, at=@At(value="INVOKE", target="Lnet/minecraft/world/entity/player/Player;getYRot()F"))
    private static float hookRayTraceYRot(Player player) {
        UseItemRayTraceEvent ag2 = new UseItemRayTraceEvent(player.m_146908_(), player.m_146909_());
        EixClientCore.a().b().a(ag2);
        return ag2.a();
    }

    @Redirect(method={"getPlayerPOVHitResult"}, at=@At(value="INVOKE", target="Lnet/minecraft/world/entity/player/Player;getXRot()F"))
    private static float hookRayTraceXRot(Player player) {
        UseItemRayTraceEvent ag2 = new UseItemRayTraceEvent(player.m_146908_(), player.m_146909_());
        EixClientCore.a().b().a(ag2);
        return ag2.b();
    }
}

