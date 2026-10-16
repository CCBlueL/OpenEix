/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils.d;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.BufferUploader;
import java.nio.ByteBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.opengl.GL20C;

public class BufferUploaderState {
    public static int a;
    private static int b;

    private BufferUploaderState() {
    }

    public static void a(int n2) {
        GlStateManager._glBindVertexArray((int)n2);
        try {
            BufferUploader.class.getDeclaredField("lastImmediateBuffer").set(null, null);
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        try {
            BufferUploader.class.getDeclaredField("field_38982").set(null, null);
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    public static void b(int n2) {
        if (n2 != 0) {
            b = a;
        }
        GlStateManager._glBindBuffer((int)34963, (int)(n2 != 0 ? n2 : b));
    }

    public static String c(int n2) {
        GlStateManager.glCompileShader((int)n2);
        if (GlStateManager.glGetShaderi((int)n2, (int)35713) == 0) {
            return GlStateManager.glGetShaderInfoLog((int)n2, (int)512);
        }
        return null;
    }

    public static String a(int n2, int n3, int n4) {
        GlStateManager.glAttachShader((int)n2, (int)n3);
        GlStateManager.glAttachShader((int)n2, (int)n4);
        GlStateManager.glLinkProgram((int)n2);
        if (GlStateManager.glGetProgrami((int)n2, (int)35714) == 0) {
            return GlStateManager.glGetProgramInfoLog((int)n2, (int)512);
        }
        return null;
    }

    public static void d(int n2) {
        GlStateManager._glUseProgram((int)n2);
    }

    public static void a(int n2, int n3, int n4, int n5) {
        GlStateManager._viewport((int)n2, (int)n3, (int)n4, (int)n5);
    }

    public static int a(int n2, String string) {
        return GlStateManager._glGetUniformLocation((int)n2, (CharSequence)string);
    }

    public static void a(int n2, int n3) {
        GlStateManager._glUniform1i((int)n2, (int)n3);
    }

    public static void a(int n2, float f2) {
        GL20C.glUniform1f((int)n2, (float)f2);
    }

    public static void a(int n2, float f2, float f3) {
        GL20C.glUniform2f((int)n2, (float)f2, (float)f3);
    }

    public static void a(int n2, float f2, float f3, float f4) {
        GL20C.glUniform3f((int)n2, (float)f2, (float)f3, (float)f4);
    }

    public static void a(int n2, float f2, float f3, float f4, float f5) {
        GL20C.glUniform4f((int)n2, (float)f2, (float)f3, (float)f4, (float)f5);
    }

    public static void a(int n2, float[] fArray) {
        GL20C.glUniform3fv((int)n2, (float[])fArray);
    }

    public static void b(int n2, int n3) {
        GlStateManager._pixelStore((int)n2, (int)n3);
    }

    public static void b(int n2, int n3, int n4) {
        GlStateManager._texParameter((int)n2, (int)n3, (int)n4);
    }

    public static void a(int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, ByteBuffer byteBuffer) {
        GL20C.glTexImage2D((int)n2, (int)n3, (int)n4, (int)n5, (int)n6, (int)n7, (int)n8, (int)n9, (ByteBuffer)byteBuffer);
    }

    public static void a() {
        BufferUploaderState.b(3312, 0);
        BufferUploaderState.b(3313, 0);
        BufferUploaderState.b(3314, 0);
        BufferUploaderState.b(32878, 0);
        BufferUploaderState.b(3315, 0);
        BufferUploaderState.b(3316, 0);
        BufferUploaderState.b(32877, 0);
        BufferUploaderState.b(3317, 4);
    }

    public static void a(int n2, int n3, int n4, int n5, int n6) {
        GlStateManager._glFramebufferTexture2D((int)n2, (int)n3, (int)n4, (int)n5, (int)n6);
    }

    public static void b() {
        GlStateManager._enableDepthTest();
    }

    public static void c() {
        GlStateManager._disableDepthTest();
    }

    public static void d() {
        GlStateManager._enableBlend();
        GlStateManager._blendFunc((int)770, (int)771);
    }

    public static void e() {
        GlStateManager._disableBlend();
    }

    public static void f() {
        GlStateManager._enableCull();
    }

    public static void g() {
        GlStateManager._disableCull();
    }

    public static void h() {
        GL20C.glEnable((int)2848);
        GL20C.glLineWidth((float)1.0f);
    }

    public static void i() {
        GL20C.glDisable((int)2848);
    }

    public static void a(ResourceLocation resourceLocation) {
        AbstractTexture abstractTexture = Minecraft.m_91087_().m_91097_().m_118506_(resourceLocation);
        BufferUploaderState.c(abstractTexture.m_117963_(), 0);
    }

    public static void c(int n2, int n3) {
        GlStateManager._activeTexture((int)(33984 + n3));
        GlStateManager._bindTexture((int)n2);
    }

    public static void e(int n2) {
        BufferUploaderState.c(n2, 0);
    }

    public static void j() {
        GlStateManager._activeTexture((int)33984);
    }
}

