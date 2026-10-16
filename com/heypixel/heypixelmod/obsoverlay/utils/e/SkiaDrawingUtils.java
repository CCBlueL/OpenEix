/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils.e;

import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.CrosshairRenderEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.PostProcess;
import com.mojang.blaze3d.platform.Window;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.ClipMode;
import io.github.humbleui.skija.Color;
import io.github.humbleui.skija.FilterTileMode;
import io.github.humbleui.skija.Font;
import io.github.humbleui.skija.FontMetrics;
import io.github.humbleui.skija.Image;
import io.github.humbleui.skija.ImageFilter;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.PaintMode;
import io.github.humbleui.skija.Path;
import io.github.humbleui.skija.Shader;
import io.github.humbleui.skija.SurfaceOrigin;
import io.github.humbleui.types.Point;
import io.github.humbleui.types.RRect;
import io.github.humbleui.types.Rect;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.ForgeRegistries;

public class SkiaDrawingUtils {
    public static final Minecraft a = Minecraft.m_91087_();
    private static final com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.c.a b = new com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.c.a();
    private static final ImageFilter c = ImageFilter.makeBlur((float)1.0f, (float)1.0f, (FilterTileMode)FilterTileMode.DECAL);
    private static final ImageFilter d = ImageFilter.makeBlur((float)2.5f, (float)2.5f, (FilterTileMode)FilterTileMode.DECAL);
    private static final Paint e = new Paint();
    private static final Paint f = new Paint();
    private static final Paint g = new Paint();
    private static final Pattern h = Pattern.compile("\u00a7.");
    private static final List<TextDrawEntry> i = new ArrayList<TextDrawEntry>();
    private static final int[] j = new int[]{-16777216, -16777046, -16733696, -16733526, -5636096, -5635926, -5614336, -5592406, -11184811, -11184641, -11141291, -11141121, -43691, -43521, -171, -1};

    private static Paint b(java.awt.Color color) {
        Paint paint = e;
        paint.setMode(PaintMode.FILL);
        paint.setARGB(color.getAlpha(), color.getRed(), color.getGreen(), color.getBlue());
        return paint;
    }

    private static Paint a(java.awt.Color color, float f2) {
        Paint paint = f;
        paint.setMode(PaintMode.STROKE);
        paint.setStrokeWidth(f2);
        paint.setARGB(color.getAlpha(), color.getRed(), color.getGreen(), color.getBlue());
        return paint;
    }

    private static String a(String string) {
        if (string == null || string.indexOf(167) < 0) {
            return string;
        }
        return h.matcher(string).replaceAll("");
    }

    private static void g() {
        if (i.isEmpty()) {
            return;
        }
        Paint paint = new Paint();
        for (TextDrawEntry b2 : i) {
            paint.setColor(b2.d.getRGB());
            paint.setImageFilter(b2.f > 1.5f ? d : c);
            if (b2.g) {
                com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e(), b2.a, b2.b, b2.c, b2.e, paint);
                continue;
            }
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().drawString(b2.a, b2.b, b2.c, b2.e, paint);
        }
        paint.close();
        i.clear();
    }

    public static void a() {
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.g();
    }

    public static void a(float f2, float f3, float f4, float f5, java.awt.Color color) {
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().drawRect(Rect.makeXYWH((float)f2, (float)f3, (float)f4, (float)f5), com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.b(color));
    }

    public static void a(float f2, float f3, float f4, java.awt.Color color) {
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().drawCircle(f2, f3, f4, com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.b(color));
    }

    public static void b(float f2, float f3, float f4, float f5, java.awt.Color color) {
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().drawCircle(f2, f3, f4, com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(color, f5));
    }

    public static void a(float f2, float f3, float f4, float f5, float f6, java.awt.Color color) {
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().drawRRect(RRect.makeXYWH((float)f2, (float)f3, (float)f4, (float)f5, (float)f6), com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.b(color));
    }

    public static void a(float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, java.awt.Color color) {
        float[] fArray = new float[]{f6, f6, f7, f7, f8, f8, f9, f9};
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().drawRRect(RRect.makeComplexXYWH((float)f2, (float)f3, (float)f4, (float)f5, (float[])fArray), com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.b(color));
    }

    public static void a(float f2, float f3, float f4, float f5) {
        Window window = Minecraft.m_91087_().m_91268_();
        Path path = new Path();
        path.addRect(Rect.makeXYWH((float)f2, (float)f3, (float)f4, (float)f5));
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c();
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().clipPath(path, ClipMode.INTERSECT, true);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(com.heypixel.heypixelmod.obsoverlay.utils.RemotePlayer.a.a.b.b(), 0.0f, 0.0f, (float)window.m_85441_() / (float)window.m_85449_(), (float)window.m_85442_() / (float)window.m_85449_(), 1.0f, SurfaceOrigin.BOTTOM_LEFT);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.d();
    }

    public static void a(float f2, float f3, float f4, float f5, float f6) {
        Window window = Minecraft.m_91087_().m_91268_();
        Path path = new Path();
        path.addRRect(RRect.makeXYWH((float)f2, (float)f3, (float)f4, (float)f5, (float)f6));
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c();
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().clipPath(path, ClipMode.INTERSECT, true);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(com.heypixel.heypixelmod.obsoverlay.utils.RemotePlayer.a.a.b.b(), 0.0f, 0.0f, (float)window.m_85441_() / (float)window.m_85449_(), (float)window.m_85442_() / (float)window.m_85449_(), 1.0f, SurfaceOrigin.BOTTOM_LEFT);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.d();
    }

    public static void b(float f2, float f3, float f4, float f5, float f6) {
        Window window = Minecraft.m_91087_().m_91268_();
        Path path = new Path();
        path.addRRect(RRect.makeXYWH((float)f2, (float)f3, (float)f4, (float)f5, (float)f6));
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c();
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().clipPath(path, ClipMode.INTERSECT, true);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(com.heypixel.heypixelmod.obsoverlay.utils.RemotePlayer.a.a.a.b(), 0.0f, 0.0f, (float)window.m_85441_() / (float)window.m_85449_(), (float)window.m_85442_() / (float)window.m_85449_(), 1.0f, SurfaceOrigin.BOTTOM_LEFT);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.d();
    }

    public static void b() {
        Window window = Minecraft.m_91087_().m_91268_();
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(com.heypixel.heypixelmod.obsoverlay.utils.RemotePlayer.a.a.a.b(), 0.0f, 0.0f, (float)window.m_85441_() / (float)window.m_85449_(), (float)window.m_85442_() / (float)window.m_85449_(), 1.0f, SurfaceOrigin.BOTTOM_LEFT);
    }

    public static void c(float f2, float f3, float f4, float f5, float f6) {
        Paint paint = g;
        paint.setMode(PaintMode.FILL);
        paint.setARGB(120, 0, 0, 0);
        paint.setImageFilter(d);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c();
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f2, f3, f4, f5, f6, ClipMode.DIFFERENCE);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().drawRRect(RRect.makeXYWH((float)f2, (float)f3, (float)f4, (float)f5, (float)f6), paint);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.d();
    }

    public static void b(float f2, float f3, float f4, float f5, float f6, java.awt.Color color) {
        Paint paint = g;
        paint.setMode(PaintMode.FILL);
        paint.setARGB(color.getAlpha(), color.getRed(), color.getGreen(), color.getBlue());
        paint.setImageFilter(d);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c();
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f2, f3, f4, f5, f6, ClipMode.DIFFERENCE);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().drawRRect(RRect.makeXYWH((float)f2, (float)f3, (float)f4, (float)f5, (float)f6), paint);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.d();
    }

    public static void a(float f2, float f3, float f4, float f5, float f6, float f7, java.awt.Color color) {
        float f8 = f7 / 2.0f;
        Path path = new Path();
        path.addRRect(RRect.makeXYWH((float)(f2 + f8), (float)(f3 + f8), (float)(f4 - f7), (float)(f5 - f7), (float)(f6 - f8)));
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().drawPath(path, com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(color, f7));
        path.close();
    }

    public static void a(String object, float f2, float f3, float f4, float f5) {
        if (b.a((String)(object = "/assets/heypixel/VcX6svVqmeT8/" + (String)object))) {
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().drawImageRect(b.b((String)object), Rect.makeXYWH((float)f2, (float)f3, (float)f4, (float)f5));
        }
    }

    public static void a(int n2, float f2, float f3, float f4, float f5, float f6, SurfaceOrigin surfaceOrigin) {
        if (b.a(n2, f4, f5, surfaceOrigin)) {
            Paint paint = new Paint();
            paint.setAlpha((int)(255.0f * f6));
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().drawImageRect(b.a(n2), Rect.makeXYWH((float)f2, (float)f3, (float)f4, (float)f5), paint);
        }
    }

    public static void a(int n2, float f2, float f3, float f4, float f5, float f6) {
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(n2, f2, f3, f4, f5, f6, SurfaceOrigin.TOP_LEFT);
    }

    public static void a(File file, float f2, float f3, float f4, float f5) {
        if (b.a(file)) {
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().drawImageRect(b.b(file.getName()), Rect.makeXYWH((float)f2, (float)f3, (float)f4, (float)f5));
        }
    }

    public static void a(int n2, float f2, float f3, float f4, float f5, SurfaceOrigin surfaceOrigin) {
        if (b.a(n2, f4, f5, surfaceOrigin)) {
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().drawImageRect(b.a(n2), Rect.makeXYWH((float)f2, (float)f3, (float)f4, (float)f5));
        }
    }

    public static void a(int n2, float f2, float f3, float f4, float f5) {
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(n2, f2, f3, f4, f5, SurfaceOrigin.TOP_LEFT);
    }

    public static void b(int n2, float f2, float f3, float f4, float f5, float f6) {
        Path path = new Path();
        path.addRRect(RRect.makeXYWH((float)f2, (float)f3, (float)f4, (float)f5, (float)f6));
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c();
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().clipPath(path, ClipMode.INTERSECT, true);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(n2, f2, f3, f4, f5);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.d();
    }

    public static void a(String string, float f2, float f3, float f4, float f5, float f6) {
        Path path = new Path();
        path.addRRect(RRect.makeXYWH((float)f2, (float)f3, (float)f4, (float)f5, (float)f6));
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c();
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().clipPath(path, ClipMode.INTERSECT, true);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string, f2, f3, f4, f5);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.d();
    }

    public static void a(File file, float f2, float f3, float f4, float f5, float f6) {
        Path path = new Path();
        path.addRRect(RRect.makeXYWH((float)f2, (float)f3, (float)f4, (float)f5, (float)f6));
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c();
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().clipPath(path, ClipMode.INTERSECT, true);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(file, f2, f3, f4, f5);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.d();
    }

    public static void a(int n2, float f2, float f3, float f4, float f5, float f6, float f7, SurfaceOrigin surfaceOrigin) {
        Path path = new Path();
        path.addRRect(RRect.makeXYWH((float)f2, (float)f3, (float)f4, (float)f5, (float)f6));
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c();
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().clipPath(path, ClipMode.INTERSECT, true);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(n2, f2, f3, f4, f5, f7, surfaceOrigin);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.d();
    }

    public static void a(int n2, float f2, float f3, float f4, float f5, float f6, float f7) {
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(n2, f2, f3, f4, f5, f6, f7, SurfaceOrigin.TOP_LEFT);
    }

    public static void a(AbstractClientPlayer abstractClientPlayer, float f2, float f3, float f4, float f5, float f6) {
        if (abstractClientPlayer == null) {
            return;
        }
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(abstractClientPlayer.m_108560_(), f2, f3, f4, f5, f6);
    }

    public static void a(ResourceLocation resourceLocation, float f2, float f3, float f4, float f5, float f6) {
        if (resourceLocation == null) {
            return;
        }
        AbstractTexture abstractTexture = Minecraft.m_91087_().m_91097_().m_118506_(resourceLocation);
        abstractTexture.m_117966_();
        int n2 = abstractTexture.m_117963_();
        if (n2 != -1 && b.a(n2, 64.0f, 64.0f, SurfaceOrigin.TOP_LEFT)) {
            Path path = new Path();
            path.addRRect(RRect.makeXYWH((float)f2, (float)f3, (float)f4, (float)f5, (float)f6));
            Rect rect = Rect.makeXYWH((float)8.0f, (float)8.0f, (float)8.0f, (float)8.0f);
            Rect rect2 = Rect.makeXYWH((float)40.0f, (float)8.0f, (float)8.0f, (float)8.0f);
            Rect rect3 = Rect.makeXYWH((float)f2, (float)f3, (float)f4, (float)f5);
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c();
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().clipPath(path, ClipMode.INTERSECT, true);
            Image image = b.a(n2);
            if (image != null) {
                com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().drawImageRect(image, rect, rect3, null, false);
                com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().drawImageRect(image, rect2, rect3, null, false);
            }
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.d();
        }
    }

    public static void a(AbstractClientPlayer abstractClientPlayer, float f2, float f3, float f4) {
        if (abstractClientPlayer == null) {
            return;
        }
        String string = abstractClientPlayer.m_108560_().m_135815_();
        if (b.a(string)) {
            Rect rect = Rect.makeXYWH((float)8.0f, (float)8.0f, (float)8.0f, (float)8.0f);
            Rect rect2 = Rect.makeXYWH((float)40.0f, (float)8.0f, (float)8.0f, (float)8.0f);
            Rect rect3 = Rect.makeXYWH((float)20.0f, (float)20.0f, (float)8.0f, (float)12.0f);
            Rect rect4 = Rect.makeXYWH((float)20.0f, (float)36.0f, (float)8.0f, (float)12.0f);
            Rect rect5 = Rect.makeXYWH((float)36.0f, (float)52.0f, (float)4.0f, (float)12.0f);
            Rect rect6 = Rect.makeXYWH((float)52.0f, (float)52.0f, (float)4.0f, (float)12.0f);
            Rect rect7 = Rect.makeXYWH((float)44.0f, (float)20.0f, (float)4.0f, (float)12.0f);
            Rect rect8 = Rect.makeXYWH((float)44.0f, (float)36.0f, (float)4.0f, (float)12.0f);
            Rect rect9 = Rect.makeXYWH((float)20.0f, (float)52.0f, (float)4.0f, (float)12.0f);
            Rect rect10 = Rect.makeXYWH((float)4.0f, (float)52.0f, (float)4.0f, (float)12.0f);
            Rect rect11 = Rect.makeXYWH((float)4.0f, (float)20.0f, (float)4.0f, (float)12.0f);
            Rect rect12 = Rect.makeXYWH((float)4.0f, (float)36.0f, (float)4.0f, (float)12.0f);
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c();
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f2, f3, f4);
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().drawImageRect(b.b(string), rect, Rect.makeXYWH((float)(f2 + rect5.getWidth()), (float)f3, (float)rect.getWidth(), (float)rect.getHeight()), null, false);
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().drawImageRect(b.b(string), rect2, Rect.makeXYWH((float)(f2 + rect5.getWidth()), (float)f3, (float)rect2.getWidth(), (float)rect2.getHeight()), null, false);
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().drawImageRect(b.b(string), rect3, Rect.makeXYWH((float)(f2 + rect5.getWidth()), (float)(f3 + rect.getHeight()), (float)rect3.getWidth(), (float)rect3.getHeight()), null, false);
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().drawImageRect(b.b(string), rect4, Rect.makeXYWH((float)(f2 + rect5.getWidth()), (float)(f3 + rect2.getHeight()), (float)rect4.getWidth(), (float)rect4.getHeight()), null, false);
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().drawImageRect(b.b(string), rect5, Rect.makeXYWH((float)f2, (float)(f3 + rect.getHeight()), (float)rect5.getWidth(), (float)rect5.getHeight()), null, false);
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().drawImageRect(b.b(string), rect6, Rect.makeXYWH((float)f2, (float)(f3 + rect2.getHeight()), (float)rect6.getWidth(), (float)rect6.getHeight()), null, false);
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().drawImageRect(b.b(string), rect7, Rect.makeXYWH((float)(f2 + rect5.getWidth() + rect3.getWidth()), (float)(f3 + rect.getHeight()), (float)rect7.getWidth(), (float)rect7.getHeight()), null, false);
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().drawImageRect(b.b(string), rect8, Rect.makeXYWH((float)(f2 + rect6.getWidth() + rect4.getWidth()), (float)(f3 + rect2.getHeight()), (float)rect8.getWidth(), (float)rect8.getHeight()), null, false);
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().drawImageRect(b.b(string), rect9, Rect.makeXYWH((float)(f2 + rect5.getWidth()), (float)(f3 + rect.getHeight() + rect3.getHeight()), (float)rect9.getWidth(), (float)rect9.getHeight()), null, false);
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().drawImageRect(b.b(string), rect10, Rect.makeXYWH((float)(f2 + rect6.getWidth()), (float)(f3 + rect2.getHeight() + rect4.getHeight()), (float)rect10.getWidth(), (float)rect10.getHeight()), null, false);
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().drawImageRect(b.b(string), rect11, Rect.makeXYWH((float)(f2 + rect5.getWidth() + rect9.getWidth()), (float)(f3 + rect.getHeight() + rect3.getHeight()), (float)rect11.getWidth(), (float)rect11.getHeight()), null, false);
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().drawImageRect(b.b(string), rect12, Rect.makeXYWH((float)(f2 + rect6.getWidth() + rect10.getWidth()), (float)(f3 + rect2.getHeight() + rect4.getHeight()), (float)rect12.getWidth(), (float)rect12.getHeight()), null, false);
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.d();
        }
    }

    public static void b(float f2, float f3, float f4, float f5, float f6, float f7, java.awt.Color color) {
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().drawArc(f2 - f4, f3 - f4, f2 + f4, f3 + f4, f5 - 90.0f, f6, false, com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(color, f7));
    }

    public static void c(float f2, float f3, float f4, float f5, float f6, java.awt.Color color) {
        Paint paint = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(color, f6);
        paint.setAntiAlias(true);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().drawLine(f2, f3, f4, f5, paint);
    }

    public static void a(float f2, float f3, float f4, float f5, float f6, java.awt.Color color, java.awt.Color color2) {
        long l2 = System.nanoTime();
        double d2 = 6.0E-10;
        double d3 = (double)l2 * d2 % (Math.PI * 2);
        float f7 = Math.max(f4, f5);
        Path path = new Path();
        path.addRRect(RRect.makeXYWH((float)f2, (float)f3, (float)f4, (float)f5, (float)f6));
        float f8 = f2 + f4 / 2.0f - f7 / 2.0f * (float)Math.cos(d3);
        float f9 = f3 + f5 / 2.0f - f7 / 2.0f * (float)Math.sin(d3);
        float f10 = f2 + f4 / 2.0f + f7 / 2.0f * (float)Math.cos(d3);
        float f11 = f3 + f5 / 2.0f + f7 / 2.0f * (float)Math.sin(d3);
        int n2 = Color.makeARGB((int)color.getAlpha(), (int)color.getRed(), (int)color.getGreen(), (int)color.getBlue());
        int n3 = Color.makeARGB((int)color2.getAlpha(), (int)color2.getRed(), (int)color2.getGreen(), (int)color2.getBlue());
        int n4 = Color.makeARGB((int)color.getAlpha(), (int)((color.getRed() + color2.getRed()) / 2), (int)((color.getGreen() + color2.getGreen()) / 2), (int)((color.getBlue() + color2.getBlue()) / 2));
        Paint paint = new Paint();
        paint.setShader(Shader.makeLinearGradient((Point)new Point(f8, f9), (Point)new Point(f10, f11), (int[])new int[]{n2, n4, n3}, (float[])new float[]{0.0f, 0.5f, 1.0f}));
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().drawPath(path, paint);
    }

    public static void a(Path path, ClipMode clipMode, boolean bl) {
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().clipPath(path, clipMode, bl);
    }

    public static void a(Path path) {
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().clipPath(path, ClipMode.INTERSECT, true);
    }

    public static void a(float f2, float f3, float f4, float f5, float f6, ClipMode clipMode) {
        Path path = new Path();
        path.addRRect(RRect.makeXYWH((float)f2, (float)f3, (float)f4, (float)f5, (float)f6));
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(path, clipMode, true);
    }

    public static void a(float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9) {
        float[] fArray = new float[]{f6, f6, f7, f7, f8, f8, f9, f9};
        Path path = new Path();
        path.addRRect(RRect.makeComplexXYWH((float)f2, (float)f3, (float)f4, (float)f5, (float[])fArray));
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(path, ClipMode.INTERSECT, true);
    }

    public static void d(float f2, float f3, float f4, float f5, float f6) {
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f2, f3, f4, f5, f6, ClipMode.INTERSECT);
    }

    private static int a(char c2) {
        if (c2 >= '0' && c2 <= '9') {
            return c2 - 48;
        }
        if (c2 >= 'a' && c2 <= 'f') {
            return c2 - 97 + 10;
        }
        if (c2 >= 'A' && c2 <= 'F') {
            return c2 - 65 + 10;
        }
        return -1;
    }

    public static float a(String string, Font font) {
        if (string == null || string.isEmpty()) {
            return 0.0f;
        }
        float f2 = 0.0f;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i2 = 0; i2 < string.length(); ++i2) {
            char c2 = string.charAt(i2);
            if (c2 == '\u00a7' && i2 + 1 < string.length()) {
                ++i2;
                continue;
            }
            stringBuilder.append(c2);
        }
        return font.measureTextWidth(stringBuilder.toString());
    }

    private static void a(Canvas canvas, String string, float f2, float f3, Font font, Paint paint) {
        float f4 = f2;
        int n2 = paint.getColor();
        for (int i2 = 0; i2 < string.length(); ++i2) {
            char c2;
            int n3;
            char c3 = string.charAt(i2);
            if (c3 == '\u00a7' && i2 + 1 < string.length()) {
                n3 = string.charAt(i2 + 1);
                int n4 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a((char)n3);
                if (n4 != -1) {
                    paint.setColor(j[n4]);
                } else if (n3 == 114) {
                    paint.setColor(n2);
                }
                ++i2;
                continue;
            }
            StringBuilder stringBuilder = new StringBuilder();
            for (n3 = i2; n3 < string.length() && ((c2 = string.charAt(n3)) != '\u00a7' || n3 + '\u0001' >= string.length()); ++n3) {
                stringBuilder.append(c2);
            }
            String string2 = stringBuilder.toString();
            canvas.drawString(string2, f4, f3, font, paint);
            f4 += font.measureTextWidth(string2);
            i2 = n3 - 1;
        }
        paint.setColor(n2);
    }

    public static void a(String string, float f2, float f3, java.awt.Color color, Font font) {
        if (string == null || string.isEmpty() || font == null) {
            return;
        }
        Rect rect = font.measureText(com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string));
        float f4 = f2 - rect.getLeft();
        float f5 = f3 - rect.getTop();
        if (PostProcess.s()) {
            i.add(new TextDrawEntry(string, f4, f5, color, font, 1.0f, true));
        }
        Paint paint = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(color);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e(), string, f4, f5, font, paint);
        paint.close();
    }

    public static void b(String string, float f2, float f3, java.awt.Color color, Font font) {
        Rect rect = font.measureText(string);
        float f4 = f2 - rect.getLeft();
        float f5 = f3 - rect.getTop();
        if (PostProcess.s()) {
            i.add(new TextDrawEntry(string, f4, f5, color, font, 1.0f, false));
        }
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().drawString(string, f4, f5, font, com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(color));
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().drawString(string, f2 - rect.getLeft() - rect.getWidth() / 2.0f, f3 - rect.getTop(), font, com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(color));
    }

    public static void c(String string, float f2, float f3, java.awt.Color color, Font font) {
        FontMetrics fontMetrics = font.getMetrics();
        Rect rect = font.measureText(string);
        float f4 = f3 + (fontMetrics.getAscent() - fontMetrics.getDescent()) / 2.0f - fontMetrics.getAscent();
        float f5 = f2 - rect.getLeft();
        if (PostProcess.s()) {
            i.add(new TextDrawEntry(string, f5, f4, color, font, 1.0f, false));
        }
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().drawString(string, f5, f4, font, com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(color));
    }

    public static void d(String string, float f2, float f3, java.awt.Color color, Font font) {
        Rect rect = font.measureText(string);
        FontMetrics fontMetrics = font.getMetrics();
        float f4 = f2 - rect.getLeft() - rect.getWidth() / 2.0f;
        float f5 = f3 + (fontMetrics.getAscent() - fontMetrics.getDescent()) / 2.0f - fontMetrics.getAscent();
        if (PostProcess.s()) {
            i.add(new TextDrawEntry(string, f4, f5, color, font, 1.0f, false));
        }
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().drawString(string, f4, f5, font, com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(color));
    }

    public static Rect b(String string, Font font) {
        return font.measureText(string);
    }

    public static void e(String string, float f2, float f3, java.awt.Color color, Font font) {
        Rect rect = font.measureText(string);
        float f4 = f2 - rect.getLeft();
        float f5 = f3 - rect.getTop();
        if (PostProcess.s()) {
            i.add(new TextDrawEntry(string, f4, f5, color, font, 2.5f, false));
        }
    }

    public static String a(String string, Font font, float f2) {
        if (string == null || string.isEmpty()) {
            return "";
        }
        float f3 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.b("...", font).getWidth();
        if (f2 <= f3) {
            return "";
        }
        if (com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.b(string, font).getWidth() <= f2) {
            return string;
        }
        int n2 = 0;
        int n3 = string.length();
        while (n2 < n3) {
            int n4 = n2 + n3 + 1 >>> 1;
            if (com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.b(string.substring(0, n4), font).getWidth() <= f2 - f3) {
                n2 = n4;
                continue;
            }
            n3 = n4 - 1;
        }
        return string.substring(0, n2) + "...";
    }

    public static Paint a(java.awt.Color color) {
        Paint paint = new Paint();
        paint.setARGB(color.getAlpha(), color.getRed(), color.getGreen(), color.getBlue());
        return paint;
    }

    public static void c() {
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().save();
    }

    public static void d() {
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().restore();
    }

    public static void a(float f2) {
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().scale(f2, f2);
    }

    public static void a(float f2, float f3, float f4) {
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().translate(f2, f3);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().scale(f4, f4);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().translate(-f2, -f3);
    }

    public static void e(float f2, float f3, float f4, float f5, float f6) {
        float f7 = f2 + f4 / 2.0f;
        float f8 = f3 + f5 / 2.0f;
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().translate(f7, f8);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().scale(f6, f6);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().translate(-f7, -f8);
    }

    public static void a(float f2, float f3) {
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().translate(f2, f3);
    }

    public static void f(float f2, float f3, float f4, float f5, float f6) {
        float f7 = f2 + f4 / 2.0f;
        float f8 = f3 + f5 / 2.0f;
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().translate(f7, f8);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().rotate(f6);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().translate(-f7, -f8);
    }

    public static void a(int n2) {
        Paint paint = new Paint();
        paint.setAlpha(n2);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().saveLayer(null, paint);
    }

    public static Canvas e() {
        return com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a.a();
    }

    public static com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.c.a f() {
        return b;
    }

    public static void a(TextureAtlasSprite textureAtlasSprite, float f2, float f3, float f4, float f5) {
        if (textureAtlasSprite == null) {
            return;
        }
        ResourceLocation resourceLocation = textureAtlasSprite.m_247685_();
        AbstractTexture abstractTexture = a.m_91097_().m_118506_(resourceLocation);
        int n2 = abstractTexture.m_117963_();
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(n2, f2, f3, f4, f5);
    }

    public static void a(MobEffect mobEffect, float f2, float f3, float f4, float f5) {
        ResourceLocation resourceLocation = ForgeRegistries.MOB_EFFECTS.getKey((Object)mobEffect);
        if (resourceLocation == null) {
            return;
        }
        ResourceLocation resourceLocation2 = ResourceLocation.fromNamespaceAndPath((String)resourceLocation.m_135827_(), (String)("textures/mob_effect/" + resourceLocation.m_135815_() + ".png"));
        AbstractTexture abstractTexture = a.m_91097_().m_118506_(resourceLocation2);
        int n2 = abstractTexture.m_117963_();
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(n2, f2, f3, f4, f5);
    }

    private static class TextDrawEntry {
        String a;
        float b;
        float c;
        java.awt.Color d;
        Font e;
        float f;
        boolean g;

        public TextDrawEntry(String string, float f2, float f3, java.awt.Color color, Font font, float f4, boolean bl) {
            this.a = string;
            this.b = f2;
            this.c = f3;
            this.d = color;
            this.e = font;
            this.f = f4;
            this.g = bl;
        }
    }

    public static class ShaderEventListener {
        @EventTarget(a=1)
        public void onShader(CrosshairRenderEvent s2) {
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.g();
        }
    }
}

