/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.render;

import com.heypixel.heypixelmod.obsoverlay.d.a.NumericSetting;
import com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.CrosshairRenderEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;
import io.github.humbleui.skija.FilterTileMode;
import io.github.humbleui.skija.Font;
import io.github.humbleui.skija.ImageFilter;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.Shader;
import io.github.humbleui.types.Point;
import io.github.humbleui.types.Rect;
import java.awt.Color;

@ModuleInfo(a="Watermark", b="\u5ba2\u6237\u7aef\u6c34\u5370", c="", d=ModuleCategory.RENDER)
public class Watermark
extends ModuleBase {
    public com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a c = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Solid White").a(true).a().b();
    public AntiNausea d = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Speed").b(2.0f).c(30.0f).a(10.0f).d(1.0f).a(() -> !this.c.m()).a().c();
    public AntiNausea e = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Hue Shift").b(0.0f).c(360.0f).a(0.0f).d(1.0f).a(() -> !this.c.m()).a().c();
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.b f = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Position").e(10.0f).f(10.0f).a().f();

    @EventTarget
    public void onRenderSkia(CrosshairRenderEvent s2) {
        Font font = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.c.d(22.0f);
        float f2 = this.f.n();
        float f3 = this.f.o();
        String string = "EixClient";
        float f4 = font.measureTextWidth(string);
        if (this.c.m()) {
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string, f2, f3, new Color(255, 255, 255, 255), font);
        } else {
            float f5 = this.d.q();
            float f6 = this.e.q();
            Color color = Color.getHSBColor(f6 % 360.0f / 360.0f, 0.85f, 1.0f);
            Color color2 = Color.getHSBColor((f6 + 120.0f) % 360.0f / 360.0f, 0.85f, 1.0f);
            Color color3 = com.heypixel.heypixelmod.obsoverlay.utils.renderer.RenderTargetManager.a((int)f5, (int)(-f2), color, color2, false);
            Color color4 = com.heypixel.heypixelmod.obsoverlay.utils.renderer.RenderTargetManager.a((int)f5, (int)(-(f2 + f4)), color, color2, false);
            try (Shader shader = Shader.makeLinearGradient((Point)new Point(f2, f3), (Point)new Point(f2 + f4, f3), (int[])new int[]{io.github.humbleui.skija.Color.makeARGB((int)color3.getAlpha(), (int)color3.getRed(), (int)color3.getGreen(), (int)color3.getBlue()), io.github.humbleui.skija.Color.makeARGB((int)color4.getAlpha(), (int)color4.getRed(), (int)color4.getGreen(), (int)color4.getBlue())}, (float[])new float[]{0.0f, 1.0f});){
                this.a(string, f2, f3, font, shader);
            }
        }
        this.f.d(22.0f);
        this.f.c(f4);
    }

    private void a(String string, float f2, float f3, Font font, Shader shader) {
        try (Paint paint = new Paint().setShader(shader);
             Paint paint2 = paint.makeClone().setImageFilter(ImageFilter.makeBlur((float)2.5f, (float)2.5f, (FilterTileMode)FilterTileMode.DECAL));){
            Rect rect = font.measureText(string);
            float f4 = f2 - rect.getLeft();
            float f5 = f3 - rect.getTop();
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().drawString(string, f4, f5, font, paint2);
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().drawString(string, f4, f5, font, paint);
        }
    }
}

