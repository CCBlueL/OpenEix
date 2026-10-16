/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.c;

import com.heypixel.heypixelmod.mixin.O.accessors.MinecraftAccessor;
import com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerMotionEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.MovementInputEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;
import com.heypixel.heypixelmod.obsoverlay.utils.EntityTrackingList;
import com.heypixel.heypixelmod.obsoverlay.utils.MathUtils;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.BlockItem;

@ModuleInfo(a="Eagle", b="\u5b89\u5168\u8e72\u642d", c="Legit trick to build faster. Auto-sneak near edges.", d=ModuleCategory.MOVEMENT)
public class Eagle
extends ModuleBase {
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.c c = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "MinDelay (Ticks)").a(2.0f).d(1.0f).b(0.0f).c(10.0f).a().c();
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.c d = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "MaxDelay (Ticks)").a(3.0f).d(1.0f).b(0.0f).c(10.0f).a().c();
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a e = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "OnlyBackwards").a(false).a().b();
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a f = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "OnlyWithBlocks").a(false).a().b();
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a g = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "FastPlace").a(false).a().b();
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.c h = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "CPS").a(10.0f).d(1.0f).b(5.0f).c(20.0f).a(this.g::m).a().c();
    private static final EntityTrackingList i = new EntityTrackingList();
    private float j = 0.0f;
    private boolean k;
    private float l = 0.0f;

    public static boolean a(float f2) {
        return !com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Eagle.a.f_91073_.m_186431_((Entity)com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Eagle.a.f_91074_, com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Eagle.a.f_91074_.m_20191_().m_82386_(0.0, -0.5, 0.0).m_82377_((double)(-f2), 0.0, (double)(-f2))).iterator().hasNext();
    }

    @EventTarget
    public void onMoveInput(MovementInputEvent l2) {
        LocalPlayer localPlayer = com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Eagle.a.f_91074_;
        if (localPlayer == null) {
            return;
        }
        if (l2.a() != 0.0f) {
            this.j = l2.a();
        }
        if (this.e.m() && this.j > 0.0f) {
            this.k = false;
            return;
        }
        if (this.f.m() && (com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Eagle.a.f_91074_.m_21205_().m_41619_() || !(com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Eagle.a.f_91074_.m_21205_().m_41720_() instanceof BlockItem))) {
            this.k = false;
            return;
        }
        this.k = true;
        if (!localPlayer.m_20096_()) {
            return;
        }
        boolean bl = com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Eagle.a(0.3f);
        if (!localPlayer.m_150110_().f_35935_ && bl) {
            i.b();
        }
        if (!i.a(MathUtils.a((int)this.d.q(), (int)this.c.q()))) {
            l2.b(true);
        }
    }

    @EventTarget
    public void onMotion(PlayerMotionEvent k2) {
        if (!this.k && i.a(MathUtils.a((int)this.d.q(), (int)this.c.q()))) {
            return;
        }
        if (k2.b() == com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.a) {
            MinecraftAccessor minecraftAccessor = (MinecraftAccessor)a;
            if (com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Eagle.a.f_91066_.f_92095_.m_90857_() && com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Eagle.a.f_91074_.m_21205_().m_41720_() instanceof BlockItem) {
                this.l += this.h.q() / 20.0f;
                if (this.l >= 1.0f / this.h.q()) {
                    minecraftAccessor.setRightClickDelay(0);
                    this.l -= 1.0f;
                }
            } else {
                this.l = 0.0f;
            }
        }
    }

    @Override
    public void d() {
        this.j = 0.0f;
    }

    @Override
    public void e() {
        this.j = 0.0f;
    }
}

