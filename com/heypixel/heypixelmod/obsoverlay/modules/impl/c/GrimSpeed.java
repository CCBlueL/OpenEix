/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.c;

import com.heypixel.heypixelmod.obsoverlay.EixClientCore;
import com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventRunTicks;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerMotionEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.MovementInputEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Scaffold;
import com.heypixel.heypixelmod.obsoverlay.utils.c.PlayerRotationController;
import com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting;
import com.heypixel.heypixelmod.obsoverlay.utils.MathUtils;
import com.heypixel.heypixelmod.obsoverlay.utils.PlayerRotationUtils;
import com.heypixel.heypixelmod.obsoverlay.utils.PacketUtils;
import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

@ModuleInfo(a="GrimSpeed", b="\u4e25\u5cfb\u7684\u563b\u563b\u54c8\u54c8", c="Movement speed adjustments with Grim modes", d=com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory.MOVEMENT)
public class GrimSpeed
extends ModuleBase {
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a c = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Logging").a(false).a().b();
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a d = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Fast Fall").a(true).a().b();
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a e = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Fast Fall On 1 Tick").a(false).a(this.d::m).a().b();
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a f = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Fast Fall On 2 Tick").a(false).a(this.d::m).a().b();
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a g = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Fast Fall On 3 Tick").a(false).a(this.d::m).a().b();
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a h = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Fast Fall On 4 Tick").a(false).a(this.d::m).a().b();
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a i = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Fast Fall On 5 Tick").a(false).a(this.d::m).a().b();
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a j = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Rotation").a(true).a().b();
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.c k = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Speed").a(0.1f).b(0.0f).c(10.0f).d(0.01f).a().c();
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.c l = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Start Fast Fall").a(2.0f).b(0.0f).c(10.0f).d(1.0f).a(this.d::m).a().c();
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.c m = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Packet Count").a(1.0f).b(1.0f).c(10.0f).d(1.0f).a(this.d::m).a().c();
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.c n = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Skip Ticks").a(2.0f).b(1.0f).c(10.0f).d(1.0f).a(this.d::m).a().c();
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.c o = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Ticks").a(3.0f).b(1.0f).c(10.0f).d(1.0f).a(this.d::m).a().c();
    private int p;

    private void b(String string) {
        if (this.c.m()) {
            com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.a(string);
        }
    }

    @Override
    public void d() {
        this.p = 0;
    }

    @Override
    public void e() {
        this.p = 0;
    }

    private double p() {
        float f2 = com.heypixel.heypixelmod.obsoverlay.modules.impl.c.GrimSpeed.a.f_91074_.f_108618_.f_108567_;
        float f3 = com.heypixel.heypixelmod.obsoverlay.modules.impl.c.GrimSpeed.a.f_91074_.f_108618_.f_108566_;
        float f4 = com.heypixel.heypixelmod.obsoverlay.modules.impl.c.GrimSpeed.a.f_91074_.m_146908_();
        if (f2 < 0.0f) {
            f4 += 180.0f;
        }
        float f5 = 1.0f;
        if (f2 < 0.0f) {
            f5 = -0.5f;
        } else if (f2 > 0.0f) {
            f5 = 0.5f;
        }
        if (f3 > 0.0f) {
            f4 -= 90.0f * f5;
        }
        if (f3 < 0.0f) {
            f4 += 90.0f * f5;
        }
        return Math.toRadians(f4);
    }

    private void a(double d2) {
        if (!PlayerRotationUtils.a()) {
            return;
        }
        double d3 = this.p();
        Vec3 vec3 = com.heypixel.heypixelmod.obsoverlay.modules.impl.c.GrimSpeed.a.f_91074_.m_20184_();
        com.heypixel.heypixelmod.obsoverlay.modules.impl.c.GrimSpeed.a.f_91074_.m_20334_(vec3.f_82479_ + -Math.sin(d3) * d2, vec3.f_82480_, vec3.f_82481_ + Math.cos(d3) * d2);
    }

    @EventTarget
    public void onMotion(PlayerMotionEvent k2) {
        if (com.heypixel.heypixelmod.obsoverlay.modules.impl.c.GrimSpeed.a.f_91074_ == null) {
            return;
        }
        if (!this.d.m()) {
            return;
        }
        if (k2.b() == com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.a) {
            this.p = com.heypixel.heypixelmod.obsoverlay.modules.impl.c.GrimSpeed.a.f_91074_.m_20096_() ? 0 : ++this.p;
        } else if (k2.b() == com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.b && !com.heypixel.heypixelmod.obsoverlay.modules.impl.c.GrimSpeed.a.f_91074_.m_20096_() && (float)this.p > this.l.q()) {
            this.b("Air ticks: " + this.p);
            switch (this.p - (int)this.l.q()) {
                case 1: {
                    if (!this.e.m()) break;
                    this.q();
                    break;
                }
                case 2: {
                    if (!this.f.m()) break;
                    this.q();
                    break;
                }
                case 3: {
                    if (!this.g.m()) break;
                    this.q();
                    break;
                }
                case 4: {
                    if (!this.h.m()) break;
                    this.q();
                    break;
                }
                case 5: {
                    if (!this.i.m()) break;
                }
            }
        }
    }

    private void q() {
        int n2;
        for (n2 = 0; n2 < (int)this.m.q(); ++n2) {
            PacketUtils.a(new ServerboundPlayerCommandPacket((Entity)com.heypixel.heypixelmod.obsoverlay.modules.impl.c.GrimSpeed.a.f_91074_, ServerboundPlayerCommandPacket.Action.START_FALL_FLYING));
        }
        com.heypixel.heypixelmod.obsoverlay.modules.impl.c.GrimSpeed.a.f_91066_.f_92090_.m_7249_(false);
        com.heypixel.heypixelmod.obsoverlay.modules.impl.c.GrimSpeed.a.f_91074_.m_6135_();
        this.a((double)this.k.q() / 10.0);
        for (n2 = 0; n2 < (int)this.o.q(); ++n2) {
            com.heypixel.heypixelmod.obsoverlay.modules.impl.c.GrimSpeed.a.f_91074_.m_8119_();
        }
        com.heypixel.heypixelmod.obsoverlay.EixClientCore.d = (int)((float)com.heypixel.heypixelmod.obsoverlay.EixClientCore.d + this.n.q());
        this.b("fall");
    }

    @EventTarget
    public void onPreTick(EventRunTicks eventRunTicks) {
        if (eventRunTicks.type() != com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.a || !this.j.m()) {
            return;
        }
        Scaffold n2 = com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a(Scaffold.class);
        com.heypixel.heypixelmod.obsoverlay.modules.impl.a.KillAura h2 = com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a(com.heypixel.heypixelmod.obsoverlay.modules.impl.a.KillAura.class);
        float f2 = MathUtils.a(-1.0f, 1.0f);
        if (!com.heypixel.heypixelmod.obsoverlay.modules.impl.c.GrimSpeed.a.f_91074_.m_20096_() && !n2.m() && h2.c == null) {
            com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.a(new com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.b(Mth.m_14177_((float)(com.heypixel.heypixelmod.obsoverlay.modules.impl.c.GrimSpeed.a.f_91074_.m_146908_() - 45.0f)) + f2, com.heypixel.heypixelmod.obsoverlay.modules.impl.c.GrimSpeed.a.f_91074_.m_146909_() + f2), 360.0);
        }
    }

    @EventTarget
    public void onMoveInput(MovementInputEvent l2) {
        if (com.heypixel.heypixelmod.obsoverlay.modules.impl.c.GrimSpeed.a.f_91074_ == null || com.heypixel.heypixelmod.obsoverlay.modules.impl.c.GrimSpeed.a.f_91073_ == null) {
            return;
        }
        l2.a(true);
    }
}

