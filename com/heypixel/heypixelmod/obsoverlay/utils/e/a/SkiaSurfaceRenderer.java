/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils.e.a;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferUploader;
import io.github.humbleui.skija.BackendRenderTarget;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.ColorSpace;
import io.github.humbleui.skija.DirectContext;
import io.github.humbleui.skija.Surface;
import io.github.humbleui.skija.SurfaceColorFormat;
import io.github.humbleui.skija.SurfaceOrigin;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL33;

public class SkiaSurfaceRenderer {
    private static DirectContext a = null;
    private static Surface b;
    private static BackendRenderTarget c;

    public static Canvas a() {
        return b.getCanvas();
    }

    public static void a(int n2, int n3) {
        System.out.println("createSurface");
        if (a == null) {
            a = DirectContext.makeGL();
        }
        if (b != null) {
            b.close();
            b = null;
        }
        if (c != null) {
            c.close();
            c = null;
        }
        c = BackendRenderTarget.makeGL((int)n2, (int)n3, (int)0, (int)8, (int)Minecraft.m_91087_().m_91385_().f_83920_, (int)32856);
        b = Surface.wrapBackendRenderTarget((DirectContext)a, (BackendRenderTarget)c, (SurfaceOrigin)SurfaceOrigin.BOTTOM_LEFT, (SurfaceColorFormat)SurfaceColorFormat.RGBA_8888, (ColorSpace)ColorSpace.getSRGB());
    }

    public static void a(Consumer<Canvas> consumer) {
        RenderSystem.pixelStore((int)3314, (int)0);
        RenderSystem.pixelStore((int)3316, (int)0);
        RenderSystem.pixelStore((int)3315, (int)0);
        RenderSystem.pixelStore((int)3317, (int)4);
        RenderSystem.clearColor((float)0.0f, (float)0.0f, (float)0.0f, (float)0.0f);
        a.resetGLAll();
        Canvas canvas = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a.a();
        consumer.accept(canvas);
        a.flush();
        BufferUploader.m_166835_();
        GL33.glBindSampler((int)0, (int)0);
        RenderSystem.disableBlend();
        GL11.glDisable((int)3042);
        RenderSystem.blendFunc((int)770, (int)1);
        GL11.glBlendFunc((int)770, (int)1);
        RenderSystem.blendEquation((int)32774);
        GL33.glBlendEquation((int)32774);
        RenderSystem.colorMask((boolean)true, (boolean)true, (boolean)true, (boolean)true);
        GL11.glColorMask((boolean)true, (boolean)true, (boolean)true, (boolean)true);
        RenderSystem.depthMask((boolean)true);
        GL11.glDepthMask((boolean)true);
        RenderSystem.disableScissor();
        GL11.glDisable((int)3089);
        GL11.glDisable((int)2960);
        RenderSystem.disableDepthTest();
        GL11.glDisable((int)2929);
        GL13.glActiveTexture((int)33984);
        RenderSystem.activeTexture((int)33984);
        RenderSystem.disableCull();
    }

    public static DirectContext b() {
        return a;
    }
}

