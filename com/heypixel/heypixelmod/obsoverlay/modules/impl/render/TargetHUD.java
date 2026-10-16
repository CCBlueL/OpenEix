/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.render;

import com.heypixel.heypixelmod.obsoverlay.EixClientCore;
import com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventRunTicks;
import com.heypixel.heypixelmod.obsoverlay.events.impl.CrosshairRenderEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.a.KillAura;
import com.heypixel.heypixelmod.obsoverlay.utils.RotationInterpolation;
import com.heypixel.heypixelmod.obsoverlay.utils.e.b.BundledFontRegistry;
import io.github.humbleui.skija.ClipMode;
import io.github.humbleui.skija.Font;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.Path;
import io.github.humbleui.types.RRect;
import io.github.humbleui.types.Rect;
import java.awt.Color;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

@ModuleInfo(a="TargetHUD", b="\u76ee\u6807\u72b6\u6001\u663e\u793a", c="Display target info.", d=com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory.RENDER)
public class TargetHUD
extends ModuleBase {
    private final RotationInterpolation c = new RotationInterpolation(20.0f);
    private final RotationInterpolation d = new RotationInterpolation(255.0f);
    private final RotationInterpolation e = new RotationInterpolation(0.0f);
    private final RotationInterpolation f = new RotationInterpolation(20.0f);
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.b g = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Position").e(500.0f).f(200.0f).a().f();
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a h = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "HurtFlash").a(true).a().b();
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a i = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "ShowAbsorption").a(true).a().b();
    private AbstractClientPlayer j;

    @EventTarget
    public void onRenderSkia(CrosshairRenderEvent s2) {
        float f2;
        if (TargetHUD.a.f_91080_ instanceof com.heypixel.heypixelmod.obsoverlay.c.HudEditorScreen) {
            this.j = TargetHUD.a.f_91074_;
            this.d.c = 255.0f;
        }
        this.d.a(true);
        if (this.d.c < 1.0f) {
            return;
        }
        this.c.a(true);
        this.e.a(true);
        this.f.a(true);
        float f3 = this.g.n();
        float f4 = this.g.o();
        float f5 = 240.0f;
        float f6 = 40.0f;
        Font font = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.c.a(10.0f);
        Font font2 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.c.b(10.0f);
        float f7 = font2.measureTextWidth("\ue87d");
        String string = this.j.m_7755_().getString();
        float f8 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string, font);
        String string2 = String.format("%.1f / 20", Float.valueOf(this.c.c));
        float f9 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string2, font);
        float f10 = f4 + 11.0f;
        float f11 = f8 + 10.0f;
        float f12 = f3 + 44.0f + f11 + f7 + 4.0f + f9;
        float f13 = f2 = 160.0f;
        float f14 = 46.0f + f2;
        float f15 = Math.max(f5, f14 + 10.0f);
        try (Paint paint = new Paint();){
            paint.setAlpha((int)this.d.c);
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().saveLayer(Rect.makeXYWH((float)(f3 - 14.0f), (float)(f4 - 14.0f), (float)(f15 + 28.0f), (float)(f6 + 28.0f)), paint);
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c(f3, f4, f15, f6, 8.0f);
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f3, f4, f15, f6, 8.0f);
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f3, f4, f15, f6, 8.0f, new Color(0, 0, 0, 70));
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(this.j, f3 + 8.0f, f4 + 6.0f, 28.0f, 28.0f, 4.0f);
            if (this.e.c > 1.0f) {
                com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f3 + 8.0f, f4 + 6.0f, 28.0f, 28.0f, 4.0f, new Color(255, 60, 60, (int)this.e.c));
            }
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string, f3 + 44.0f, f10, new Color(255, 255, 255), font);
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a("\ue87d", f3 + 44.0f + f11, f10 - 0.5f, new Color(215, 215, 215), font2);
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string2, f3 + 44.0f + f11 + f7 + 4.0f, f10, new Color(215, 215, 215), font);
            Path path = new Path();
            path.addRRect(RRect.makeXYWH((float)(f3 + 44.0f), (float)(f4 + 24.0f), (float)f2, (float)5.5f, (float)0.8f));
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c();
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().clipPath(path, ClipMode.INTERSECT, true);
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f3 + 44.0f, f4 + 24.0f, f2, 5.5f, new Color(0, 0, 0, 75));
            float f16 = f2 / 20.0f * this.c.c;
            float f17 = Math.min(255, (int)(255.0f - this.c.c / 20.0f * 190.0f));
            float f18 = Math.min(255, (int)(70.0f + this.c.c / 20.0f * 185.0f));
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f3 + 44.0f, f4 + 24.0f, f16, 5.5f, new Color((int)f17, (int)f18, 35, 225));
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.d();
            path.close();
            if (this.i.m() && this.f.c > 0.1f) {
                Path path2 = new Path();
                path2.addRRect(RRect.makeXYWH((float)(f3 + 44.0f), (float)(f4 + 31.0f), (float)f13, (float)4.0f, (float)0.6f));
                com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c();
                com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().clipPath(path2, ClipMode.INTERSECT, true);
                com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f3 + 44.0f, f4 + 31.0f, f13, 4.0f, new Color(0, 0, 0, 65));
                float f19 = f13 / 8.0f * this.f.c;
                com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f3 + 44.0f, f4 + 31.0f, f19, 4.0f, new Color(255, 215, 70, 235));
                com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.d();
                path2.close();
            }
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.d();
        }
        this.g.c(f15);
        this.g.d(f6);
    }

    @EventTarget
    public void onPreTick(EventRunTicks eventRunTicks) {
        EntityHitResult entityHitResult;
        if (TargetHUD.a.f_91074_ == null || eventRunTicks.type() != com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.a) {
            return;
        }
        KillAura h2 = com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a(KillAura.class);
        AbstractClientPlayer abstractClientPlayer = null;
        HitResult hitResult = TargetHUD.a.f_91077_;
        if (hitResult instanceof EntityHitResult && (hitResult = (entityHitResult = (EntityHitResult)hitResult).m_82443_()) instanceof AbstractClientPlayer) {
            AbstractClientPlayer abstractClientPlayer2;
            abstractClientPlayer = abstractClientPlayer2 = (AbstractClientPlayer)hitResult;
        } else if (h2.m() && (hitResult = h2.c) instanceof AbstractClientPlayer) {
            AbstractClientPlayer abstractClientPlayer3;
            abstractClientPlayer = abstractClientPlayer3 = (AbstractClientPlayer)hitResult;
        }
        if (abstractClientPlayer != null) {
            this.j = abstractClientPlayer;
            this.d.a = 255.0f;
            this.c.a = this.j.m_21223_();
            this.f.a = this.j.m_6103_();
            this.e.a = this.h.m() && this.j.f_20916_ > 0 ? 140.0f : 0.0f;
        } else {
            this.d.a = 0.0f;
            this.e.a = 0.0f;
            this.f.a = 0.0f;
            this.j = null;
        }
    }
}

