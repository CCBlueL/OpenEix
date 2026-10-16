/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.b;

import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerMotionEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.c.LongJump;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Fireball;

@ModuleInfo(a="AntiFireball", b="\u53cd\u706b\u7403", c="Prevents fireballs from damaging you", d=com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory.MISC)
public class AntiFireball
extends ModuleBase {
    @EventTarget
    public void onMotion(PlayerMotionEvent k2) {
        if (!com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a(LongJump.class).m() && k2.b() == com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.a) {
            Stream<Entity> stream = StreamSupport.stream(com.heypixel.heypixelmod.obsoverlay.modules.impl.b.AntiFireball.a.f_91073_.m_104735_().spliterator(), true);
            Optional<Fireball> optional = stream.filter(entity -> entity instanceof Fireball && com.heypixel.heypixelmod.obsoverlay.modules.impl.b.AntiFireball.a.f_91074_.m_20270_(entity) < 6.0f).map(entity -> (Fireball)entity).findFirst();
            if (!optional.isPresent()) {
                return;
            }
            Fireball fireball = optional.get();
            com.heypixel.heypixelmod.obsoverlay.modules.impl.b.AntiFireball.a.f_91072_.m_105223_((Player)com.heypixel.heypixelmod.obsoverlay.modules.impl.b.AntiFireball.a.f_91074_, (Entity)fireball);
            com.heypixel.heypixelmod.obsoverlay.modules.impl.b.AntiFireball.a.f_91074_.m_6674_(InteractionHand.MAIN_HAND);
        }
    }
}

