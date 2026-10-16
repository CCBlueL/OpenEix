/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.c;

import com.heypixel.heypixelmod.obsoverlay.EixClientCore;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerMotionEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.MovementInputEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PacketInterceptEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerRespawnEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Scaffold;
import com.heypixel.heypixelmod.obsoverlay.utils.c.PlayerRotationController;
import com.heypixel.heypixelmod.obsoverlay.utils.PacketUtils;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.network.protocol.game.ServerboundPongPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemPacket;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.BowlFoodItem;
import net.minecraft.world.item.ItemStack;

@ModuleInfo(a="Stuck", b="\u5361\u7a7a", c="Stuck in air!", d=com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory.MOVEMENT)
public class Stuck
extends ModuleBase {
    int c = 0;
    Packet<?> d;
    float e;
    float f;
    boolean g = false;
    Queue<ServerboundPongPacket> h = new ConcurrentLinkedQueue<ServerboundPongPacket>();

    @Override
    public void d() {
        this.c = 0;
        this.d = null;
        this.e = com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.a.a();
        this.f = com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.a.b();
        this.g = false;
    }

    @Override
    public void a(boolean bl) {
        if (Stuck.a.f_91074_ != null) {
            if (bl) {
                super.a(true);
            } else if (this.c == 3) {
                super.a(false);
            } else {
                this.g = true;
            }
        }
    }

    @EventTarget
    public void onMotion(PlayerMotionEvent k2) {
        Scaffold n2 = com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a(Scaffold.class);
        if (n2.m()) {
            n2.f();
        } else if (k2.b() == com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.a) {
            Stuck.a.f_91074_.m_20334_(0.0, 0.0, 0.0);
            if (this.c == 1) {
                this.c = 2;
                float f2 = Stuck.a.f_91074_.m_146908_();
                float f3 = Stuck.a.f_91074_.m_146909_();
                if (this.p() && (this.e != f2 || this.f != f3)) {
                    PacketUtils.a(new ServerboundMovePlayerPacket.Rot(f2, f3, Stuck.a.f_91074_.m_20096_()));
                    while (!this.h.isEmpty()) {
                        PacketUtils.a((Packet)this.h.poll());
                    }
                    this.e = f2;
                    this.f = f3;
                }
                PacketUtils.a(this.d);
            }
            if (this.g) {
                PacketUtils.a(new ServerboundMovePlayerPacket.Pos(Stuck.a.f_91074_.m_20185_() + 1337.0, Stuck.a.f_91074_.m_20186_(), Stuck.a.f_91074_.m_20189_() + 1337.0, Stuck.a.f_91074_.m_20096_()));
                while (!this.h.isEmpty()) {
                    PacketUtils.a((Packet)this.h.poll());
                }
                this.g = false;
            }
        }
    }

    private boolean p() {
        ItemStack itemStack = this.d;
        if (itemStack instanceof ServerboundUseItemPacket) {
            ServerboundUseItemPacket serverboundUseItemPacket = (ServerboundUseItemPacket)itemStack;
            return !((itemStack = Stuck.a.f_91074_.m_21120_(serverboundUseItemPacket.m_134717_())).m_41720_() instanceof BowlFoodItem) && !(itemStack.m_41720_() instanceof BowItem);
        }
        Packet<?> packet = this.d;
        return packet instanceof ServerboundPlayerActionPacket && (itemStack = (ServerboundPlayerActionPacket)packet).m_134285_() == ServerboundPlayerActionPacket.Action.RELEASE_USE_ITEM && Stuck.a.f_91074_.m_21211_().m_41720_() instanceof BowItem;
    }

    @EventTarget
    public void onMoveInput(MovementInputEvent l2) {
        l2.a(0.0f);
        l2.b(0.0f);
        l2.a(false);
        l2.b(false);
    }

    @EventTarget
    public void onRespawn(PlayerRespawnEvent u2) {
        this.c = 3;
        this.d = null;
        this.f();
    }

    @EventTarget(a=1)
    public void onPacket(PacketInterceptEvent m2) {
        if (m2.c() instanceof ServerboundMovePlayerPacket) {
            if (this.c == 1) {
                m2.a(true);
            }
        } else if (m2.c() instanceof ServerboundPongPacket) {
            this.h.offer((ServerboundPongPacket)m2.c());
            m2.a(true);
        } else if (m2.c() instanceof ServerboundUseItemPacket || m2.c() instanceof ServerboundPlayerActionPacket) {
            this.d = m2.c();
            this.c = 1;
            m2.a(true);
        } else if (m2.c() instanceof ClientboundPlayerPositionPacket) {
            while (!this.h.isEmpty()) {
                PacketUtils.a((Packet)this.h.poll());
            }
            this.c = 3;
            this.f();
        }
    }
}

