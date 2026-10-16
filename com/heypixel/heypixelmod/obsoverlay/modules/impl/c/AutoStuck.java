/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.c;

import com.heypixel.heypixelmod.obsoverlay.d.a.NumericSetting;
import com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerMotionEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PacketInterceptEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Stuck;
import com.heypixel.heypixelmod.obsoverlay.utils.ElapsedTimer;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

@ModuleInfo(a="AutoStuck", b="\u81ea\u52a8\u5361\u7a7a", c="Automatically enable stuck when you over void", d=com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory.MOVEMENT)
public class AutoStuck
extends ModuleBase {
    private final Eagle c = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Fall Distance").a(10.0f).d(0.1f).b(3.0f).c(15.0f).a().c();
    private final ElapsedTimer d = new ElapsedTimer();

    @EventTarget
    public void onMotion(PlayerMotionEvent k2) {
        int n2;
        if (k2.b() != com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.a || com.heypixel.heypixelmod.obsoverlay.modules.impl.c.AutoStuck.a.f_91074_ == null || com.heypixel.heypixelmod.obsoverlay.modules.impl.c.AutoStuck.a.f_91073_ == null) {
            return;
        }
        Stuck q2 = com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a(Stuck.class);
        if (q2 == null) {
            return;
        }
        if (q2.m()) {
            this.d.a();
        }
        int n3 = -1;
        for (n2 = 0; n2 < 9; ++n2) {
            if (com.heypixel.heypixelmod.obsoverlay.modules.impl.c.AutoStuck.a.f_91074_.m_150109_().m_8020_(n2).m_41619_() || com.heypixel.heypixelmod.obsoverlay.modules.impl.c.AutoStuck.a.f_91074_.m_150109_().m_8020_(n2).m_41720_() != Items.f_42584_) continue;
            n3 = n2;
            break;
        }
        int n4 = n2 = (n3 != -1 && com.heypixel.heypixelmod.obsoverlay.modules.impl.c.AutoStuck.a.f_91074_.f_19789_ > this.c.q() || com.heypixel.heypixelmod.obsoverlay.modules.impl.c.AutoStuck.a.f_91074_.m_20186_() + com.heypixel.heypixelmod.obsoverlay.modules.impl.c.AutoStuck.a.f_91074_.m_20184_().f_82480_ < -50.0) && this.p() && !com.heypixel.heypixelmod.obsoverlay.modules.impl.c.AutoStuck.a.f_91074_.m_20096_() && this.d.a(1000.0) ? 1 : 0;
        if (n2 != 0 && !q2.m()) {
            q2.f();
        }
    }

    @EventTarget
    public void onPacket(PacketInterceptEvent m2) {
        if (m2.c() instanceof ClientboundPlayerPositionPacket) {
            this.d.a();
        }
    }

    private boolean p() {
        Vec3 vec3;
        Vec3 vec32 = com.heypixel.heypixelmod.obsoverlay.modules.impl.c.AutoStuck.a.f_91074_.m_20182_();
        BlockHitResult blockHitResult = com.heypixel.heypixelmod.obsoverlay.modules.impl.c.AutoStuck.a.f_91073_.m_45547_(new ClipContext(vec32, vec3 = new Vec3(vec32.f_82479_, (double)com.heypixel.heypixelmod.obsoverlay.modules.impl.c.AutoStuck.a.f_91073_.m_141937_() - 2.0, vec32.f_82481_), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, (Entity)com.heypixel.heypixelmod.obsoverlay.modules.impl.c.AutoStuck.a.f_91074_));
        return blockHitResult.m_6662_() == HitResult.Type.MISS;
    }
}

