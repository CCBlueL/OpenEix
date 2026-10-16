/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.a;

import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventRunTicks;
import com.heypixel.heypixelmod.obsoverlay.events.impl.KeybindActionEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;
import java.util.List;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerboundInteractPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

@ModuleInfo(a="CrystalAura", b="\u6c34\u6676\u5149\u73af", d=ModuleCategory.COMBAT, c="Automatically attacks end crystals")
public class CrystalAura
extends ModuleBase {
    public Entity c;

    @EventTarget
    public void onEarlyTick(EventRunTicks eventRunTicks) {
        if (eventRunTicks.type() == com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.a && com.heypixel.heypixelmod.obsoverlay.modules.impl.a.CrystalAura.a.f_91074_ != null && com.heypixel.heypixelmod.obsoverlay.modules.impl.a.CrystalAura.a.f_91073_ != null) {
            EntityHitResult entityHitResult;
            EndCrystal endCrystal;
            double d2 = 4.0;
            double d3 = d2 * d2;
            Entity entity = this.c;
            this.c = null;
            EndCrystal endCrystal2 = null;
            double d4 = Double.MAX_VALUE;
            AABB aABB = com.heypixel.heypixelmod.obsoverlay.modules.impl.a.CrystalAura.a.f_91074_.m_20191_().m_82400_(d2);
            List list = com.heypixel.heypixelmod.obsoverlay.modules.impl.a.CrystalAura.a.f_91073_.m_45976_(EndCrystal.class, aABB);
            Object object = list.iterator();
            while (object.hasNext()) {
                double d5;
                endCrystal = (EndCrystal)object.next();
                if (!endCrystal.m_6084_() || !((d5 = com.heypixel.heypixelmod.obsoverlay.modules.impl.a.CrystalAura.a.f_91074_.m_20280_((Entity)endCrystal)) < d4) || !(d5 <= d3)) continue;
                d4 = d5;
                endCrystal2 = endCrystal;
            }
            if (endCrystal2 != null && (endCrystal = com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.a.a((com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.b)(object = com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.d.a(endCrystal2)), 3.0)) instanceof EntityHitResult && (entityHitResult = (EntityHitResult)endCrystal).m_82443_().equals(endCrystal2)) {
                com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.a((com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.b)object, 180.0);
                this.c = endCrystal2;
            }
            if (entity != null && this.c == null && com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.k()) {
                com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.b(false);
            }
        }
    }

    @EventTarget
    public void onClick(KeybindActionEvent d2) {
        EntityHitResult entityHitResult;
        HitResult hitResult;
        if (this.c != null && (hitResult = com.heypixel.heypixelmod.obsoverlay.modules.impl.a.CrystalAura.a.f_91077_) instanceof EntityHitResult && (entityHitResult = (EntityHitResult)hitResult).m_82443_().equals((Object)this.c)) {
            a.m_91403_().m_104955_((Packet)ServerboundInteractPacket.m_179605_((Entity)this.c, (boolean)false));
            com.heypixel.heypixelmod.obsoverlay.modules.impl.a.CrystalAura.a.f_91074_.m_6674_(InteractionHand.MAIN_HAND);
            this.c = null;
        }
    }
}

