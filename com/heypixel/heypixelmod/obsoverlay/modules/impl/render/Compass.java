/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.render;

import com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventRender2D;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerMotionEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.WorldRenderEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;
import com.heypixel.heypixelmod.obsoverlay.utils.EntityOverlayRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;

@ModuleInfo(a="Compass", b="\u6307\u5357\u9488", c="Shows a compass", d=ModuleCategory.RENDER)
public class Compass
extends ModuleBase {
    public com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a c = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Compass Only").a(true).a().b();
    public com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a d = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "No Player Only").a(true).a().b();
    private boolean e = false;
    private BlockPos f;
    private float g;
    private double h;
    private double i;

    private BlockPos a(ClientLevel clientLevel) {
        return clientLevel.m_6042_().f_63858_() ? clientLevel.m_220360_() : null;
    }

    private boolean p() {
        for (Entity entity : com.heypixel.heypixelmod.obsoverlay.modules.impl.render.Compass.a.f_91073_.m_104735_()) {
            if (entity == com.heypixel.heypixelmod.obsoverlay.modules.impl.render.Compass.a.f_91074_ || entity instanceof com.heypixel.heypixelmod.obsoverlay.utils.RemotePlayer || !(entity instanceof Player)) continue;
            return true;
        }
        return false;
    }

    @EventTarget
    public void onMotion(PlayerMotionEvent k2) {
        if (k2.b() == com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.a) {
            this.e = com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.c(Items.f_42522_);
            this.f = this.a(com.heypixel.heypixelmod.obsoverlay.modules.impl.render.Compass.a.f_91073_);
        }
    }

    @EventTarget
    public void onRender(WorldRenderEvent p2) {
        this.h = Mth.m_14139_((double)p2.a(), (double)com.heypixel.heypixelmod.obsoverlay.modules.impl.render.Compass.a.f_91074_.f_19790_, (double)com.heypixel.heypixelmod.obsoverlay.modules.impl.render.Compass.a.f_91074_.m_20185_());
        this.i = Mth.m_14139_((double)p2.a(), (double)com.heypixel.heypixelmod.obsoverlay.modules.impl.render.Compass.a.f_91074_.f_19792_, (double)com.heypixel.heypixelmod.obsoverlay.modules.impl.render.Compass.a.f_91074_.m_20189_());
        this.g = Mth.m_14179_((float)p2.a(), (float)com.heypixel.heypixelmod.obsoverlay.modules.impl.render.Compass.a.f_91074_.f_19859_, (float)com.heypixel.heypixelmod.obsoverlay.modules.impl.render.Compass.a.f_91074_.m_146908_());
    }

    @EventTarget(a=4)
    public void onRender2D(EventRender2D eventRender2D) {
        this.a(eventRender2D.stack());
    }

    private void a(PoseStack poseStack) {
        if (!(!this.e && this.c.m() || this.p() && this.d.m() || this.f == null)) {
            float f2 = (float)(Math.toDegrees(Math.atan2((double)this.f.m_123343_() - this.i, (double)this.f.m_123341_() - this.h)) - 90.0 - (double)this.g);
            float f3 = (float)a.m_91268_().m_85445_() / 2.0f;
            float f4 = (float)a.m_91268_().m_85446_() / 2.0f;
            poseStack.m_85836_();
            poseStack.m_252880_(f3, f4, 0.0f);
            poseStack.m_252781_(Axis.f_252403_.m_252977_(f2));
            poseStack.m_252880_(-f3, -f4, 0.0f);
            EntityOverlayRenderer.a(poseStack, f3, f4 - 45.0f, 10.0f, 2.0f, 1.0f, -1);
            poseStack.m_252880_(f3, f4, 0.0f);
            poseStack.m_252781_(Axis.f_252403_.m_252977_(-f2));
            poseStack.m_252880_(-f3, -f4, 0.0f);
            poseStack.m_85849_();
        }
    }
}

