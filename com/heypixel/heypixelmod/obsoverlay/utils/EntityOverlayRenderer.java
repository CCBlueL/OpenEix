/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils;

import com.heypixel.heypixelmod.obsoverlay.utils.BlockChunkPositionUtils;
import com.heypixel.heypixelmod.obsoverlay.utils.MathUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.awt.Color;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.FastColor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

public class EntityOverlayRenderer {
    private static final Minecraft a = Minecraft.m_91087_();
    private static final AABB b = new AABB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);

    public static int a(int n2, float f2) {
        int n3 = MathUtils.a((int)(f2 * 255.0f), 0, 255) << 24;
        n3 |= MathUtils.a(n2 >> 16 & 0xFF, 0, 255) << 16;
        return (n3 |= MathUtils.a(n2 >> 8 & 0xFF, 0, 255) << 8) | MathUtils.a(n2 & 0xFF, 0, 255);
    }

    public static void a(PoseStack poseStack, float f2, float f3, float f4, float f5, float f6, int n2) {
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glEnable((int)2848);
        RenderSystem.setShader(GameRenderer::m_172811_);
        Matrix4f matrix4f = poseStack.m_85850_().m_252922_();
        float f7 = (float)(n2 >> 24 & 0xFF) / 255.0f;
        float f8 = (float)(n2 >> 16 & 0xFF) / 255.0f;
        float f9 = (float)(n2 >> 8 & 0xFF) / 255.0f;
        float f10 = (float)(n2 & 0xFF) / 255.0f;
        BufferBuilder bufferBuilder = Tesselator.m_85913_().m_85915_();
        bufferBuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85815_);
        bufferBuilder.m_252986_(matrix4f, f2, f3, 0.0f).m_85950_(f8, f9, f10, f7).m_5752_();
        bufferBuilder.m_252986_(matrix4f, f2 - f4 / f5, f3 + f4, 0.0f).m_85950_(f8, f9, f10, f7).m_5752_();
        bufferBuilder.m_252986_(matrix4f, f2, f3 + f4 / f6, 0.0f).m_85950_(f8, f9, f10, f7).m_5752_();
        bufferBuilder.m_252986_(matrix4f, f2 + f4 / f5, f3 + f4, 0.0f).m_85950_(f8, f9, f10, f7).m_5752_();
        bufferBuilder.m_252986_(matrix4f, f2, f3, 0.0f).m_85950_(f8, f9, f10, f7).m_5752_();
        Tesselator.m_85913_().m_85914_();
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)2848);
    }

    public static int a(int n2, float f2, float f3, float f4) {
        float f5 = (float)((System.currentTimeMillis() + (long)n2) % (long)((int)f4)) / f4;
        return Color.HSBtoRGB(f5, f2, f3);
    }

    public static Color b(int n2, float f2, float f3, float f4) {
        float f5 = (float)((System.currentTimeMillis() + (long)n2) % (long)((int)f4)) / f4;
        return Color.getHSBColor(f5, f2, f3);
    }

    public static BlockPos a() {
        Camera camera = EntityOverlayRenderer.a.m_167982_().f_112249_;
        return camera.m_90588_();
    }

    public static Vec3 b() {
        Camera camera = EntityOverlayRenderer.a.m_167982_().f_112249_;
        return camera.m_90583_();
    }

    public static BlockChunkPositionUtils c() {
        return BlockChunkPositionUtils.a(EntityOverlayRenderer.a());
    }

    public static void a(PoseStack poseStack) {
        EntityOverlayRenderer.a(poseStack, EntityOverlayRenderer.c());
    }

    public static void a(PoseStack poseStack, BlockChunkPositionUtils ac2) {
        Vec3 vec3 = ac2.b().m_82546_(EntityOverlayRenderer.b());
        poseStack.m_85837_(vec3.f_82479_, vec3.f_82480_, vec3.f_82481_);
    }

    public static void a(PoseStack poseStack, float f2, float f3, float f4, float f5, int n2) {
        EntityOverlayRenderer.a(poseStack.m_85850_().m_252922_(), f2, f3, f4, f5, n2);
    }

    private static void a(Matrix4f matrix4f, float f2, float f3, float f4, float f5, int n2) {
        float f6;
        if (f2 < f4) {
            f6 = f2;
            f2 = f4;
            f4 = f6;
        }
        if (f3 < f5) {
            f6 = f3;
            f3 = f5;
            f5 = f6;
        }
        f6 = (float)(n2 >> 24 & 0xFF) / 255.0f;
        float f7 = (float)(n2 >> 16 & 0xFF) / 255.0f;
        float f8 = (float)(n2 >> 8 & 0xFF) / 255.0f;
        float f9 = (float)(n2 & 0xFF) / 255.0f;
        BufferBuilder bufferBuilder = Tesselator.m_85913_().m_85915_();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderSystem.setShader(GameRenderer::m_172811_);
        bufferBuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85815_);
        bufferBuilder.m_252986_(matrix4f, f2, f5, 0.0f).m_85950_(f7, f8, f9, f6).m_5752_();
        bufferBuilder.m_252986_(matrix4f, f4, f5, 0.0f).m_85950_(f7, f8, f9, f6).m_5752_();
        bufferBuilder.m_252986_(matrix4f, f4, f3, 0.0f).m_85950_(f7, f8, f9, f6).m_5752_();
        bufferBuilder.m_252986_(matrix4f, f2, f3, 0.0f).m_85950_(f7, f8, f9, f6).m_5752_();
        Tesselator.m_85913_().m_85914_();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    public static void b(PoseStack poseStack, float f2, float f3, float f4, float f5, int n2) {
        Tesselator tesselator = Tesselator.m_85913_();
        BufferBuilder bufferBuilder = tesselator.m_85915_();
        Matrix4f matrix4f = poseStack.m_85850_().m_252922_();
        float f6 = (float)(n2 >> 24 & 0xFF) / 255.0f;
        float f7 = (float)(n2 >> 16 & 0xFF) / 255.0f;
        float f8 = (float)(n2 >> 8 & 0xFF) / 255.0f;
        float f9 = (float)(n2 & 0xFF) / 255.0f;
        bufferBuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85815_);
        bufferBuilder.m_252986_(matrix4f, f2, f3 + f5, 0.0f).m_85950_(f7, f8, f9, f6).m_5752_();
        bufferBuilder.m_252986_(matrix4f, f2 + f4, f3 + f5, 0.0f).m_85950_(f7, f8, f9, f6).m_5752_();
        bufferBuilder.m_252986_(matrix4f, f2 + f4, f3, 0.0f).m_85950_(f7, f8, f9, f6).m_5752_();
        bufferBuilder.m_252986_(matrix4f, f2, f3, 0.0f).m_85950_(f7, f8, f9, f6).m_5752_();
        tesselator.m_85914_();
    }

    private static void a(BufferBuilder bufferBuilder, Matrix4f matrix4f, float f2, float f3, int n2) {
        float f4 = (float)(n2 >> 24 & 0xFF) / 255.0f;
        float f5 = (float)(n2 >> 16 & 0xFF) / 255.0f;
        float f6 = (float)(n2 >> 8 & 0xFF) / 255.0f;
        float f7 = (float)(n2 & 0xFF) / 255.0f;
        bufferBuilder.m_252986_(matrix4f, f2, f3, 0.0f).m_85950_(f5, f6, f7, f4).m_5752_();
    }

    public static void a(PoseStack poseStack, float f2, float f3, float f4, float f5, float f6, float f7, int n2) {
        if (f6 < 0.0f) {
            f6 = 0.0f;
        }
        if (f6 > f4 / 2.0f) {
            f6 = f4 / 2.0f;
        }
        if (f6 > f5 / 2.0f) {
            f6 = f5 / 2.0f;
        }
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.lineWidth((float)f7);
        Tesselator tesselator = Tesselator.m_85913_();
        BufferBuilder bufferBuilder = tesselator.m_85915_();
        Matrix4f matrix4f = poseStack.m_85850_().m_252922_();
        RenderSystem.setShader(GameRenderer::m_172811_);
        bufferBuilder.m_166779_(VertexFormat.Mode.DEBUG_LINES, DefaultVertexFormat.f_85815_);
        float f8 = f2 + f6;
        float f9 = f3 + f6;
        float f10 = f2 + f4 - f6;
        float f11 = f3 + f5 - f6;
        int n3 = (int)Math.min(Math.max(f6, 10.0f), 90.0f);
        EntityOverlayRenderer.a(bufferBuilder, matrix4f, f8, f3, f10, f3, n2);
        EntityOverlayRenderer.a(bufferBuilder, matrix4f, f10, f9, f6, 180, 270, n3, n2);
        EntityOverlayRenderer.a(bufferBuilder, matrix4f, f2 + f4, f9, f2 + f4, f11, n2);
        EntityOverlayRenderer.a(bufferBuilder, matrix4f, f10, f11, f6, 90, 180, n3, n2);
        EntityOverlayRenderer.a(bufferBuilder, matrix4f, f10, f3 + f5, f8, f3 + f5, n2);
        EntityOverlayRenderer.a(bufferBuilder, matrix4f, f8, f11, f6, 0, 90, n3, n2);
        EntityOverlayRenderer.a(bufferBuilder, matrix4f, f2, f11, f2, f9, n2);
        EntityOverlayRenderer.a(bufferBuilder, matrix4f, f8, f9, f6, 270, 360, n3, n2);
        tesselator.m_85914_();
        RenderSystem.disableBlend();
    }

    private static void a(BufferBuilder bufferBuilder, Matrix4f matrix4f, float f2, float f3, float f4, float f5, int n2) {
        float f6 = (float)(n2 >> 24 & 0xFF) / 255.0f;
        float f7 = (float)(n2 >> 16 & 0xFF) / 255.0f;
        float f8 = (float)(n2 >> 8 & 0xFF) / 255.0f;
        float f9 = (float)(n2 & 0xFF) / 255.0f;
        bufferBuilder.m_252986_(matrix4f, f2, f3, 0.0f).m_85950_(f7, f8, f9, f6).m_5752_();
        bufferBuilder.m_252986_(matrix4f, f4, f5, 0.0f).m_85950_(f7, f8, f9, f6).m_5752_();
    }

    private static void a(BufferBuilder bufferBuilder, Matrix4f matrix4f, float f2, float f3, float f4, int n2, int n3, int n4, int n5) {
        float f5 = (float)(n5 >> 24 & 0xFF) / 255.0f;
        float f6 = (float)(n5 >> 16 & 0xFF) / 255.0f;
        float f7 = (float)(n5 >> 8 & 0xFF) / 255.0f;
        float f8 = (float)(n5 & 0xFF) / 255.0f;
        double d2 = Math.PI * 2 / (double)(n4 * 4);
        double d3 = Math.toRadians(n2);
        double d4 = Math.toRadians(n3);
        for (int i2 = 0; i2 < n4; ++i2) {
            double d5 = Math.toRadians((double)n2 + (double)(n3 - n2) * (double)i2 / (double)n4);
            double d6 = Math.toRadians((double)n2 + (double)(n3 - n2) * (double)(i2 + 1) / (double)n4);
            float f9 = (float)((double)f2 + Math.sin(d5) * (double)f4);
            float f10 = (float)((double)f3 + Math.cos(d5) * (double)f4);
            float f11 = (float)((double)f2 + Math.sin(d6) * (double)f4);
            float f12 = (float)((double)f3 + Math.cos(d6) * (double)f4);
            bufferBuilder.m_252986_(matrix4f, f9, f10, 0.0f).m_85950_(f6, f7, f8, f5).m_5752_();
            bufferBuilder.m_252986_(matrix4f, f11, f12, 0.0f).m_85950_(f6, f7, f8, f5).m_5752_();
        }
    }

    public static void b(PoseStack poseStack, float f2, float f3, float f4, float f5, float f6, int n2) {
        double d2;
        int n3;
        if (n2 == 0xFFFFFF) {
            n2 = FastColor.ARGB32.m_13660_((int)255, (int)255, (int)255, (int)255);
        }
        if (f6 < 0.0f) {
            f6 = 0.0f;
        }
        if (f6 > f4 / 2.0f) {
            f6 = f4 / 2.0f;
        }
        if (f6 > f5 / 2.0f) {
            f6 = f5 / 2.0f;
        }
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.lineWidth((float)1.0f);
        Tesselator tesselator = Tesselator.m_85913_();
        BufferBuilder bufferBuilder = tesselator.m_85915_();
        Matrix4f matrix4f = poseStack.m_85850_().m_252922_();
        RenderSystem.setShader(GameRenderer::m_172811_);
        bufferBuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85815_);
        float f7 = f2 + f6;
        float f8 = f2 + f4 - f6;
        float f9 = f3 + f6;
        float f10 = f3 + f5 - f6;
        EntityOverlayRenderer.b(bufferBuilder, matrix4f, f7, f9, f8, f10, n2);
        EntityOverlayRenderer.b(bufferBuilder, matrix4f, f7, f3, f8, f9, n2);
        EntityOverlayRenderer.b(bufferBuilder, matrix4f, f7, f10, f8, f3 + f5, n2);
        EntityOverlayRenderer.b(bufferBuilder, matrix4f, f2, f9, f7, f10, n2);
        EntityOverlayRenderer.b(bufferBuilder, matrix4f, f8, f9, f2 + f4, f10, n2);
        tesselator.m_85914_();
        int n4 = (int)Math.min(Math.max(f6, 10.0f), 90.0f);
        bufferBuilder.m_166779_(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.f_85815_);
        float f11 = f2 + f6;
        float f12 = f3 + f6;
        EntityOverlayRenderer.a(bufferBuilder, matrix4f, f11, f12, n2);
        for (n3 = 0; n3 <= n4; ++n3) {
            d2 = Math.PI * 2 * (double)(n3 + 180) / (double)(n4 * 4);
            EntityOverlayRenderer.a(bufferBuilder, matrix4f, (float)((double)f11 + Math.sin(d2) * (double)f6), (float)((double)f12 + Math.cos(d2) * (double)f6), n2);
        }
        tesselator.m_85914_();
        bufferBuilder.m_166779_(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.f_85815_);
        f11 = f2 + f4 - f6;
        f12 = f3 + f6;
        EntityOverlayRenderer.a(bufferBuilder, matrix4f, f11, f12, n2);
        for (n3 = 0; n3 <= n4; ++n3) {
            d2 = Math.PI * 2 * (double)(n3 + 90) / (double)(n4 * 4);
            EntityOverlayRenderer.a(bufferBuilder, matrix4f, (float)((double)f11 + Math.sin(d2) * (double)f6), (float)((double)f12 + Math.cos(d2) * (double)f6), n2);
        }
        tesselator.m_85914_();
        bufferBuilder.m_166779_(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.f_85815_);
        f11 = f2 + f6;
        f12 = f3 + f5 - f6;
        EntityOverlayRenderer.a(bufferBuilder, matrix4f, f11, f12, n2);
        for (n3 = 0; n3 <= n4; ++n3) {
            d2 = Math.PI * 2 * (double)(n3 + 270) / (double)(n4 * 4);
            EntityOverlayRenderer.a(bufferBuilder, matrix4f, (float)((double)f11 + Math.sin(d2) * (double)f6), (float)((double)f12 + Math.cos(d2) * (double)f6), n2);
        }
        tesselator.m_85914_();
        bufferBuilder.m_166779_(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.f_85815_);
        f11 = f2 + f4 - f6;
        f12 = f3 + f5 - f6;
        EntityOverlayRenderer.a(bufferBuilder, matrix4f, f11, f12, n2);
        for (n3 = 0; n3 <= n4; ++n3) {
            d2 = Math.PI * 2 * (double)n3 / (double)(n4 * 4);
            EntityOverlayRenderer.a(bufferBuilder, matrix4f, (float)((double)f11 + Math.sin(d2) * (double)f6), (float)((double)f12 + Math.cos(d2) * (double)f6), n2);
        }
        tesselator.m_85914_();
        RenderSystem.disableBlend();
    }

    private static void b(BufferBuilder bufferBuilder, Matrix4f matrix4f, float f2, float f3, float f4, float f5, int n2) {
        float f6 = (float)(n2 >> 24 & 0xFF) / 255.0f;
        float f7 = (float)(n2 >> 16 & 0xFF) / 255.0f;
        float f8 = (float)(n2 >> 8 & 0xFF) / 255.0f;
        float f9 = (float)(n2 & 0xFF) / 255.0f;
        bufferBuilder.m_252986_(matrix4f, f2, f5, 0.0f).m_85950_(f7, f8, f9, f6).m_5752_();
        bufferBuilder.m_252986_(matrix4f, f4, f5, 0.0f).m_85950_(f7, f8, f9, f6).m_5752_();
        bufferBuilder.m_252986_(matrix4f, f4, f3, 0.0f).m_85950_(f7, f8, f9, f6).m_5752_();
        bufferBuilder.m_252986_(matrix4f, f2, f3, 0.0f).m_85950_(f7, f8, f9, f6).m_5752_();
    }

    public static void b(PoseStack poseStack) {
        EntityOverlayRenderer.a(b, poseStack);
    }

    public static void a(AABB aABB, PoseStack poseStack) {
        Tesselator tesselator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tesselator.m_85915_();
        Matrix4f matrix4f = poseStack.m_85850_().m_252922_();
        bufferBuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85814_);
        bufferBuilder.m_252986_(matrix4f, (float)aABB.f_82288_, (float)aABB.f_82289_, (float)aABB.f_82290_).m_5752_();
        bufferBuilder.m_252986_(matrix4f, (float)aABB.f_82291_, (float)aABB.f_82289_, (float)aABB.f_82290_).m_5752_();
        bufferBuilder.m_252986_(matrix4f, (float)aABB.f_82291_, (float)aABB.f_82289_, (float)aABB.f_82293_).m_5752_();
        bufferBuilder.m_252986_(matrix4f, (float)aABB.f_82288_, (float)aABB.f_82289_, (float)aABB.f_82293_).m_5752_();
        bufferBuilder.m_252986_(matrix4f, (float)aABB.f_82288_, (float)aABB.f_82292_, (float)aABB.f_82290_).m_5752_();
        bufferBuilder.m_252986_(matrix4f, (float)aABB.f_82288_, (float)aABB.f_82292_, (float)aABB.f_82293_).m_5752_();
        bufferBuilder.m_252986_(matrix4f, (float)aABB.f_82291_, (float)aABB.f_82292_, (float)aABB.f_82293_).m_5752_();
        bufferBuilder.m_252986_(matrix4f, (float)aABB.f_82291_, (float)aABB.f_82292_, (float)aABB.f_82290_).m_5752_();
        bufferBuilder.m_252986_(matrix4f, (float)aABB.f_82288_, (float)aABB.f_82289_, (float)aABB.f_82290_).m_5752_();
        bufferBuilder.m_252986_(matrix4f, (float)aABB.f_82288_, (float)aABB.f_82292_, (float)aABB.f_82290_).m_5752_();
        bufferBuilder.m_252986_(matrix4f, (float)aABB.f_82291_, (float)aABB.f_82292_, (float)aABB.f_82290_).m_5752_();
        bufferBuilder.m_252986_(matrix4f, (float)aABB.f_82291_, (float)aABB.f_82289_, (float)aABB.f_82290_).m_5752_();
        bufferBuilder.m_252986_(matrix4f, (float)aABB.f_82291_, (float)aABB.f_82289_, (float)aABB.f_82290_).m_5752_();
        bufferBuilder.m_252986_(matrix4f, (float)aABB.f_82291_, (float)aABB.f_82292_, (float)aABB.f_82290_).m_5752_();
        bufferBuilder.m_252986_(matrix4f, (float)aABB.f_82291_, (float)aABB.f_82292_, (float)aABB.f_82293_).m_5752_();
        bufferBuilder.m_252986_(matrix4f, (float)aABB.f_82291_, (float)aABB.f_82289_, (float)aABB.f_82293_).m_5752_();
        bufferBuilder.m_252986_(matrix4f, (float)aABB.f_82288_, (float)aABB.f_82289_, (float)aABB.f_82293_).m_5752_();
        bufferBuilder.m_252986_(matrix4f, (float)aABB.f_82291_, (float)aABB.f_82289_, (float)aABB.f_82293_).m_5752_();
        bufferBuilder.m_252986_(matrix4f, (float)aABB.f_82291_, (float)aABB.f_82292_, (float)aABB.f_82293_).m_5752_();
        bufferBuilder.m_252986_(matrix4f, (float)aABB.f_82288_, (float)aABB.f_82292_, (float)aABB.f_82293_).m_5752_();
        bufferBuilder.m_252986_(matrix4f, (float)aABB.f_82288_, (float)aABB.f_82289_, (float)aABB.f_82290_).m_5752_();
        bufferBuilder.m_252986_(matrix4f, (float)aABB.f_82288_, (float)aABB.f_82289_, (float)aABB.f_82293_).m_5752_();
        bufferBuilder.m_252986_(matrix4f, (float)aABB.f_82288_, (float)aABB.f_82292_, (float)aABB.f_82293_).m_5752_();
        bufferBuilder.m_252986_(matrix4f, (float)aABB.f_82288_, (float)aABB.f_82292_, (float)aABB.f_82290_).m_5752_();
        BufferUploader.m_231202_((BufferBuilder.RenderedBuffer)bufferBuilder.m_231175_());
    }

    public static void c(PoseStack poseStack) {
        EntityOverlayRenderer.b(b, poseStack);
    }

    public static void b(AABB aABB, PoseStack poseStack) {
        Matrix4f matrix4f = poseStack.m_85850_().m_252922_();
        BufferBuilder bufferBuilder = Tesselator.m_85913_().m_85915_();
        RenderSystem.setShader(GameRenderer::m_172808_);
        bufferBuilder.m_166779_(VertexFormat.Mode.DEBUG_LINES, DefaultVertexFormat.f_85814_);
        bufferBuilder.m_252986_(matrix4f, (float)aABB.f_82288_, (float)aABB.f_82289_, (float)aABB.f_82290_).m_5752_();
        bufferBuilder.m_252986_(matrix4f, (float)aABB.f_82291_, (float)aABB.f_82289_, (float)aABB.f_82290_).m_5752_();
        bufferBuilder.m_252986_(matrix4f, (float)aABB.f_82291_, (float)aABB.f_82289_, (float)aABB.f_82290_).m_5752_();
        bufferBuilder.m_252986_(matrix4f, (float)aABB.f_82291_, (float)aABB.f_82289_, (float)aABB.f_82293_).m_5752_();
        bufferBuilder.m_252986_(matrix4f, (float)aABB.f_82291_, (float)aABB.f_82289_, (float)aABB.f_82293_).m_5752_();
        bufferBuilder.m_252986_(matrix4f, (float)aABB.f_82288_, (float)aABB.f_82289_, (float)aABB.f_82293_).m_5752_();
        bufferBuilder.m_252986_(matrix4f, (float)aABB.f_82288_, (float)aABB.f_82289_, (float)aABB.f_82293_).m_5752_();
        bufferBuilder.m_252986_(matrix4f, (float)aABB.f_82288_, (float)aABB.f_82289_, (float)aABB.f_82290_).m_5752_();
        bufferBuilder.m_252986_(matrix4f, (float)aABB.f_82288_, (float)aABB.f_82289_, (float)aABB.f_82290_).m_5752_();
        bufferBuilder.m_252986_(matrix4f, (float)aABB.f_82288_, (float)aABB.f_82292_, (float)aABB.f_82290_).m_5752_();
        bufferBuilder.m_252986_(matrix4f, (float)aABB.f_82291_, (float)aABB.f_82289_, (float)aABB.f_82290_).m_5752_();
        bufferBuilder.m_252986_(matrix4f, (float)aABB.f_82291_, (float)aABB.f_82292_, (float)aABB.f_82290_).m_5752_();
        bufferBuilder.m_252986_(matrix4f, (float)aABB.f_82291_, (float)aABB.f_82289_, (float)aABB.f_82293_).m_5752_();
        bufferBuilder.m_252986_(matrix4f, (float)aABB.f_82291_, (float)aABB.f_82292_, (float)aABB.f_82293_).m_5752_();
        bufferBuilder.m_252986_(matrix4f, (float)aABB.f_82288_, (float)aABB.f_82289_, (float)aABB.f_82293_).m_5752_();
        bufferBuilder.m_252986_(matrix4f, (float)aABB.f_82288_, (float)aABB.f_82292_, (float)aABB.f_82293_).m_5752_();
        bufferBuilder.m_252986_(matrix4f, (float)aABB.f_82288_, (float)aABB.f_82292_, (float)aABB.f_82290_).m_5752_();
        bufferBuilder.m_252986_(matrix4f, (float)aABB.f_82291_, (float)aABB.f_82292_, (float)aABB.f_82290_).m_5752_();
        bufferBuilder.m_252986_(matrix4f, (float)aABB.f_82291_, (float)aABB.f_82292_, (float)aABB.f_82290_).m_5752_();
        bufferBuilder.m_252986_(matrix4f, (float)aABB.f_82291_, (float)aABB.f_82292_, (float)aABB.f_82293_).m_5752_();
        bufferBuilder.m_252986_(matrix4f, (float)aABB.f_82291_, (float)aABB.f_82292_, (float)aABB.f_82293_).m_5752_();
        bufferBuilder.m_252986_(matrix4f, (float)aABB.f_82288_, (float)aABB.f_82292_, (float)aABB.f_82293_).m_5752_();
        bufferBuilder.m_252986_(matrix4f, (float)aABB.f_82288_, (float)aABB.f_82292_, (float)aABB.f_82293_).m_5752_();
        bufferBuilder.m_252986_(matrix4f, (float)aABB.f_82288_, (float)aABB.f_82292_, (float)aABB.f_82290_).m_5752_();
        BufferUploader.m_231202_((BufferBuilder.RenderedBuffer)bufferBuilder.m_231175_());
    }

    public static void a(AABB aABB, VertexBuffer vertexBuffer) {
        BufferBuilder bufferBuilder = Tesselator.m_85913_().m_85915_();
        RenderSystem.setShader(GameRenderer::m_172808_);
        bufferBuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85814_);
        EntityOverlayRenderer.a(aABB, bufferBuilder);
        BufferUploader.m_166835_();
        vertexBuffer.m_85921_();
        BufferBuilder.RenderedBuffer renderedBuffer = bufferBuilder.m_231175_();
        vertexBuffer.m_231221_(renderedBuffer);
        VertexBuffer.m_85931_();
    }

    public static void a(AABB aABB, BufferBuilder bufferBuilder) {
        bufferBuilder.m_5483_(aABB.f_82288_, aABB.f_82289_, aABB.f_82290_).m_5752_();
        bufferBuilder.m_5483_(aABB.f_82291_, aABB.f_82289_, aABB.f_82290_).m_5752_();
        bufferBuilder.m_5483_(aABB.f_82291_, aABB.f_82289_, aABB.f_82293_).m_5752_();
        bufferBuilder.m_5483_(aABB.f_82288_, aABB.f_82289_, aABB.f_82293_).m_5752_();
        bufferBuilder.m_5483_(aABB.f_82288_, aABB.f_82292_, aABB.f_82290_).m_5752_();
        bufferBuilder.m_5483_(aABB.f_82288_, aABB.f_82292_, aABB.f_82293_).m_5752_();
        bufferBuilder.m_5483_(aABB.f_82291_, aABB.f_82292_, aABB.f_82293_).m_5752_();
        bufferBuilder.m_5483_(aABB.f_82291_, aABB.f_82292_, aABB.f_82290_).m_5752_();
        bufferBuilder.m_5483_(aABB.f_82288_, aABB.f_82289_, aABB.f_82290_).m_5752_();
        bufferBuilder.m_5483_(aABB.f_82288_, aABB.f_82292_, aABB.f_82290_).m_5752_();
        bufferBuilder.m_5483_(aABB.f_82291_, aABB.f_82292_, aABB.f_82290_).m_5752_();
        bufferBuilder.m_5483_(aABB.f_82291_, aABB.f_82289_, aABB.f_82290_).m_5752_();
        bufferBuilder.m_5483_(aABB.f_82291_, aABB.f_82289_, aABB.f_82290_).m_5752_();
        bufferBuilder.m_5483_(aABB.f_82291_, aABB.f_82292_, aABB.f_82290_).m_5752_();
        bufferBuilder.m_5483_(aABB.f_82291_, aABB.f_82292_, aABB.f_82293_).m_5752_();
        bufferBuilder.m_5483_(aABB.f_82291_, aABB.f_82289_, aABB.f_82293_).m_5752_();
        bufferBuilder.m_5483_(aABB.f_82288_, aABB.f_82289_, aABB.f_82293_).m_5752_();
        bufferBuilder.m_5483_(aABB.f_82291_, aABB.f_82289_, aABB.f_82293_).m_5752_();
        bufferBuilder.m_5483_(aABB.f_82291_, aABB.f_82292_, aABB.f_82293_).m_5752_();
        bufferBuilder.m_5483_(aABB.f_82288_, aABB.f_82292_, aABB.f_82293_).m_5752_();
        bufferBuilder.m_5483_(aABB.f_82288_, aABB.f_82289_, aABB.f_82290_).m_5752_();
        bufferBuilder.m_5483_(aABB.f_82288_, aABB.f_82289_, aABB.f_82293_).m_5752_();
        bufferBuilder.m_5483_(aABB.f_82288_, aABB.f_82292_, aABB.f_82293_).m_5752_();
        bufferBuilder.m_5483_(aABB.f_82288_, aABB.f_82292_, aABB.f_82290_).m_5752_();
    }

    public static void b(AABB aABB, VertexBuffer vertexBuffer) {
        BufferBuilder bufferBuilder = Tesselator.m_85913_().m_85915_();
        bufferBuilder.m_166779_(VertexFormat.Mode.DEBUG_LINES, DefaultVertexFormat.f_85814_);
        EntityOverlayRenderer.b(aABB, bufferBuilder);
        vertexBuffer.m_231221_(bufferBuilder.m_231175_());
    }

    public static void b(AABB aABB, BufferBuilder bufferBuilder) {
        bufferBuilder.m_5483_(aABB.f_82288_, aABB.f_82289_, aABB.f_82290_).m_5752_();
        bufferBuilder.m_5483_(aABB.f_82291_, aABB.f_82289_, aABB.f_82290_).m_5752_();
        bufferBuilder.m_5483_(aABB.f_82291_, aABB.f_82289_, aABB.f_82290_).m_5752_();
        bufferBuilder.m_5483_(aABB.f_82291_, aABB.f_82289_, aABB.f_82293_).m_5752_();
        bufferBuilder.m_5483_(aABB.f_82291_, aABB.f_82289_, aABB.f_82293_).m_5752_();
        bufferBuilder.m_5483_(aABB.f_82288_, aABB.f_82289_, aABB.f_82293_).m_5752_();
        bufferBuilder.m_5483_(aABB.f_82288_, aABB.f_82289_, aABB.f_82293_).m_5752_();
        bufferBuilder.m_5483_(aABB.f_82288_, aABB.f_82289_, aABB.f_82290_).m_5752_();
        bufferBuilder.m_5483_(aABB.f_82288_, aABB.f_82289_, aABB.f_82290_).m_5752_();
        bufferBuilder.m_5483_(aABB.f_82288_, aABB.f_82292_, aABB.f_82290_).m_5752_();
        bufferBuilder.m_5483_(aABB.f_82291_, aABB.f_82289_, aABB.f_82290_).m_5752_();
        bufferBuilder.m_5483_(aABB.f_82291_, aABB.f_82292_, aABB.f_82290_).m_5752_();
        bufferBuilder.m_5483_(aABB.f_82291_, aABB.f_82289_, aABB.f_82293_).m_5752_();
        bufferBuilder.m_5483_(aABB.f_82291_, aABB.f_82292_, aABB.f_82293_).m_5752_();
        bufferBuilder.m_5483_(aABB.f_82288_, aABB.f_82289_, aABB.f_82293_).m_5752_();
        bufferBuilder.m_5483_(aABB.f_82288_, aABB.f_82292_, aABB.f_82293_).m_5752_();
        bufferBuilder.m_5483_(aABB.f_82288_, aABB.f_82292_, aABB.f_82290_).m_5752_();
        bufferBuilder.m_5483_(aABB.f_82291_, aABB.f_82292_, aABB.f_82290_).m_5752_();
        bufferBuilder.m_5483_(aABB.f_82291_, aABB.f_82292_, aABB.f_82290_).m_5752_();
        bufferBuilder.m_5483_(aABB.f_82291_, aABB.f_82292_, aABB.f_82293_).m_5752_();
        bufferBuilder.m_5483_(aABB.f_82291_, aABB.f_82292_, aABB.f_82293_).m_5752_();
        bufferBuilder.m_5483_(aABB.f_82288_, aABB.f_82292_, aABB.f_82293_).m_5752_();
        bufferBuilder.m_5483_(aABB.f_82288_, aABB.f_82292_, aABB.f_82293_).m_5752_();
        bufferBuilder.m_5483_(aABB.f_82288_, aABB.f_82292_, aABB.f_82290_).m_5752_();
    }

    public static boolean a(int n2, int n3, float f2, float f3, float f4, float f5) {
        return (float)n2 > f2 && (float)n2 < f4 && (float)n3 > f3 && (float)n3 < f5;
    }

    public static boolean b(int n2, int n3, float f2, float f3, float f4, float f5) {
        return (float)n2 > f2 && (float)n2 < f2 + f4 && (float)n3 > f3 && (float)n3 < f3 + f5;
    }

    public static void c(PoseStack poseStack, float f2, float f3, float f4, float f5, int n2) {
        float f6 = f2 + f4;
        float f7 = f3 + f5;
        EntityOverlayRenderer.a(poseStack, f2, f3, f6, f7, n2);
    }

    public static void a(BufferBuilder bufferBuilder, Matrix4f matrix4f, AABB aABB) {
        float f2 = (float)(aABB.f_82288_ - EntityOverlayRenderer.a.m_91290_().f_114358_.m_90583_().m_7096_());
        float f3 = (float)(aABB.f_82289_ - EntityOverlayRenderer.a.m_91290_().f_114358_.m_90583_().m_7098_());
        float f4 = (float)(aABB.f_82290_ - EntityOverlayRenderer.a.m_91290_().f_114358_.m_90583_().m_7094_());
        float f5 = (float)(aABB.f_82291_ - EntityOverlayRenderer.a.m_91290_().f_114358_.m_90583_().m_7096_());
        float f6 = (float)(aABB.f_82292_ - EntityOverlayRenderer.a.m_91290_().f_114358_.m_90583_().m_7098_());
        float f7 = (float)(aABB.f_82293_ - EntityOverlayRenderer.a.m_91290_().f_114358_.m_90583_().m_7094_());
        bufferBuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85814_);
        bufferBuilder.m_252986_(matrix4f, f2, f3, f4).m_5752_();
        bufferBuilder.m_252986_(matrix4f, f5, f3, f4).m_5752_();
        bufferBuilder.m_252986_(matrix4f, f5, f3, f7).m_5752_();
        bufferBuilder.m_252986_(matrix4f, f2, f3, f7).m_5752_();
        bufferBuilder.m_252986_(matrix4f, f2, f6, f4).m_5752_();
        bufferBuilder.m_252986_(matrix4f, f2, f6, f7).m_5752_();
        bufferBuilder.m_252986_(matrix4f, f5, f6, f7).m_5752_();
        bufferBuilder.m_252986_(matrix4f, f5, f6, f4).m_5752_();
        bufferBuilder.m_252986_(matrix4f, f2, f3, f4).m_5752_();
        bufferBuilder.m_252986_(matrix4f, f2, f6, f4).m_5752_();
        bufferBuilder.m_252986_(matrix4f, f5, f6, f4).m_5752_();
        bufferBuilder.m_252986_(matrix4f, f5, f3, f4).m_5752_();
        bufferBuilder.m_252986_(matrix4f, f5, f3, f4).m_5752_();
        bufferBuilder.m_252986_(matrix4f, f5, f6, f4).m_5752_();
        bufferBuilder.m_252986_(matrix4f, f5, f6, f7).m_5752_();
        bufferBuilder.m_252986_(matrix4f, f5, f3, f7).m_5752_();
        bufferBuilder.m_252986_(matrix4f, f2, f3, f7).m_5752_();
        bufferBuilder.m_252986_(matrix4f, f5, f3, f7).m_5752_();
        bufferBuilder.m_252986_(matrix4f, f5, f6, f7).m_5752_();
        bufferBuilder.m_252986_(matrix4f, f2, f6, f7).m_5752_();
        bufferBuilder.m_252986_(matrix4f, f2, f3, f4).m_5752_();
        bufferBuilder.m_252986_(matrix4f, f2, f3, f7).m_5752_();
        bufferBuilder.m_252986_(matrix4f, f2, f6, f7).m_5752_();
        bufferBuilder.m_252986_(matrix4f, f2, f6, f4).m_5752_();
        BufferUploader.m_231202_((BufferBuilder.RenderedBuffer)bufferBuilder.m_231175_());
    }

    public static void a(PoseStack poseStack, double d2, double d3, double d4, int n2) {
        EntityOverlayRenderer.a(poseStack, d2, d3, d4, 0.6f, 1.8f, n2);
    }

    public static void a(PoseStack poseStack, double d2, double d3, double d4, float f2, float f3, int n2) {
        Vec3 vec3 = EntityOverlayRenderer.b();
        float f4 = (float)(n2 >> 24 & 0xFF) / 255.0f;
        float f5 = (float)(n2 >> 16 & 0xFF) / 255.0f;
        float f6 = (float)(n2 >> 8 & 0xFF) / 255.0f;
        float f7 = (float)(n2 & 0xFF) / 255.0f;
        poseStack.m_85836_();
        poseStack.m_85837_(d2 - vec3.f_82479_, d3 - vec3.f_82480_, d4 - vec3.f_82481_);
        AABB aABB = new AABB((double)(-f2) / 2.0, 0.0, (double)(-f2) / 2.0, (double)f2 / 2.0, (double)f3, (double)f2 / 2.0);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask((boolean)false);
        RenderSystem.setShader(GameRenderer::m_172808_);
        RenderSystem.setShaderColor((float)f5, (float)f6, (float)f7, (float)f4);
        EntityOverlayRenderer.a(aABB, poseStack);
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderSystem.enableDepthTest();
        RenderSystem.depthMask((boolean)true);
        RenderSystem.disableBlend();
        poseStack.m_85849_();
    }
}

