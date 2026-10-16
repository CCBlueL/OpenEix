/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils.renderer;

import com.google.common.collect.ImmutableList;
import com.heypixel.heypixelmod.mixin.O.accessors.BufferUploaderAccessor;
import com.heypixel.heypixelmod.obsoverlay.utils.GenericValueAccessor;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32C;

public class RenderStateManager {
    private static final FloatBuffer b = BufferUtils.createFloatBuffer((int)16);
    private static final GenericValueAccessor c = com.heypixel.heypixelmod.obsoverlay.utils.renderer.RenderStateManager.a("DEPTH");
    private static final GenericValueAccessor d = com.heypixel.heypixelmod.obsoverlay.utils.renderer.RenderStateManager.a("BLEND");
    private static final GenericValueAccessor e = com.heypixel.heypixelmod.obsoverlay.utils.renderer.RenderStateManager.a("CULL");
    private static final GenericValueAccessor f = com.heypixel.heypixelmod.obsoverlay.utils.renderer.RenderStateManager.a("SCISSOR");
    private static final boolean g = true;
    public static int a;
    private static boolean h;
    private static boolean i;
    private static boolean j;
    private static boolean k;
    private static int l;

    public static int a() {
        return GlStateManager._glGenVertexArrays();
    }

    public static int b() {
        return GlStateManager._glGenBuffers();
    }

    public static int c() {
        return GlStateManager._genTexture();
    }

    public static int d() {
        return GlStateManager.glGenFramebuffers();
    }

    public static void e() {
        h = c.get();
        i = d.get();
        j = e.get();
        k = f.get();
    }

    public static void f() {
        c.set(h);
        d.set(i);
        e.set(j);
        f.set(k);
        com.heypixel.heypixelmod.obsoverlay.utils.renderer.RenderStateManager.r();
    }

    public static void a(int n2) {
        GlStateManager._glDeleteBuffers((int)n2);
    }

    public static void b(int n2) {
        GlStateManager._glDeleteVertexArrays((int)n2);
    }

    public static void c(int n2) {
        GlStateManager.glDeleteShader((int)n2);
    }

    public static void d(int n2) {
        GlStateManager._deleteTexture((int)n2);
    }

    public static void e(int n2) {
        GlStateManager._glDeleteFramebuffers((int)n2);
    }

    public static void f(int n2) {
        GlStateManager.glDeleteProgram((int)n2);
    }

    public static void g(int n2) {
        if (n2 >= 0) {
            try {
                GlStateManager._glBindVertexArray((int)n2);
                BufferUploaderAccessor.setCurrentVertexBuffer(null);
            }
            catch (Exception exception) {
                System.err.println("Error binding VAO " + n2 + ": " + exception.getMessage());
            }
        } else {
            System.err.println("WARNING: Attempted to bind invalid VAO: " + n2);
        }
    }

    public static void h(int n2) {
        GlStateManager._glBindBuffer((int)34962, (int)n2);
    }

    public static void i(int n2) {
        if (n2 != 0) {
            l = a;
        }
        try {
            int n3 = n2 != 0 ? n2 : l;
            GlStateManager._glBindBuffer((int)34963, (int)n3);
        }
        catch (Exception exception) {
            System.err.println("Error binding IBO: " + exception.getMessage());
        }
    }

    public static void j(int n2) {
        GlStateManager._glBindFramebuffer((int)36160, (int)n2);
    }

    public static void a(int n2, ByteBuffer byteBuffer, int n3) {
        GlStateManager._glBufferData((int)n2, (ByteBuffer)byteBuffer, (int)n3);
    }

    public static void a(int n2, int n3, int n4) {
        GlStateManager._drawElements((int)n2, (int)n3, (int)n4, (long)0L);
    }

    public static void k(int n2) {
        RenderSystem.assertOnRenderThread();
        GL20.glEnableVertexAttribArray((int)n2);
    }

    public static void a(int n2, int n3, int n4, boolean bl, int n5, long l2) {
        GlStateManager._vertexAttribPointer((int)n2, (int)n3, (int)n4, (boolean)bl, (int)n5, (long)l2);
    }

    public static int l(int n2) {
        return GlStateManager.glCreateShader((int)n2);
    }

    public static void a(int n2, String string) {
        GlStateManager.glShaderSource((int)n2, (List)ImmutableList.of((Object)string));
    }

    public static String m(int n2) {
        GlStateManager.glCompileShader((int)n2);
        return GlStateManager.glGetShaderi((int)n2, (int)35713) == 0 ? GlStateManager.glGetShaderInfoLog((int)n2, (int)512) : null;
    }

    public static int g() {
        return GlStateManager.glCreateProgram();
    }

    public static String b(int n2, int n3, int n4) {
        GlStateManager.glAttachShader((int)n2, (int)n3);
        GlStateManager.glAttachShader((int)n2, (int)n4);
        GlStateManager.glLinkProgram((int)n2);
        return GlStateManager.glGetProgrami((int)n2, (int)35714) == 0 ? GlStateManager.glGetProgramInfoLog((int)n2, (int)512) : null;
    }

    public static void n(int n2) {
        GlStateManager._glUseProgram((int)n2);
    }

    public static void a(int n2, int n3, int n4, int n5) {
        GlStateManager._viewport((int)n2, (int)n3, (int)n4, (int)n5);
    }

    public static int b(int n2, String string) {
        return GlStateManager._glGetUniformLocation((int)n2, (CharSequence)string);
    }

    public static void a(int n2, int n3) {
        GlStateManager._glUniform1i((int)n2, (int)n3);
    }

    public static void a(int n2, float f2) {
        GL32C.glUniform1f((int)n2, (float)f2);
    }

    public static void a(int n2, float f2, float f3) {
        GL32C.glUniform2f((int)n2, (float)f2, (float)f3);
    }

    public static void a(int n2, float f2, float f3, float f4) {
        GL32C.glUniform3f((int)n2, (float)f2, (float)f3, (float)f4);
    }

    public static void a(int n2, float f2, float f3, float f4, float f5) {
        GL32C.glUniform4f((int)n2, (float)f2, (float)f3, (float)f4, (float)f5);
    }

    public static void a(int n2, float[] fArray) {
        GL32C.glUniform3fv((int)n2, (float[])fArray);
    }

    public static void a(int n2, Matrix4f matrix4f) {
        matrix4f.get(b);
        GlStateManager._glUniformMatrix4((int)n2, (boolean)false, (FloatBuffer)b);
    }

    public static void b(int n2, int n3) {
        GlStateManager._pixelStore((int)n2, (int)n3);
    }

    public static void c(int n2, int n3, int n4) {
        GlStateManager._texParameter((int)n2, (int)n3, (int)n4);
    }

    public static void a(int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, ByteBuffer byteBuffer) {
        GL32C.glTexImage2D((int)n2, (int)n3, (int)n4, (int)n5, (int)n6, (int)n7, (int)n8, (int)n9, (ByteBuffer)byteBuffer);
    }

    public static void h() {
        com.heypixel.heypixelmod.obsoverlay.utils.renderer.RenderStateManager.b(3312, 0);
        com.heypixel.heypixelmod.obsoverlay.utils.renderer.RenderStateManager.b(3313, 0);
        com.heypixel.heypixelmod.obsoverlay.utils.renderer.RenderStateManager.b(3314, 0);
        com.heypixel.heypixelmod.obsoverlay.utils.renderer.RenderStateManager.b(32878, 0);
        com.heypixel.heypixelmod.obsoverlay.utils.renderer.RenderStateManager.b(3315, 0);
        com.heypixel.heypixelmod.obsoverlay.utils.renderer.RenderStateManager.b(3316, 0);
        com.heypixel.heypixelmod.obsoverlay.utils.renderer.RenderStateManager.b(32877, 0);
        com.heypixel.heypixelmod.obsoverlay.utils.renderer.RenderStateManager.b(3317, 4);
    }

    public static void o(int n2) {
        GL32C.glGenerateMipmap((int)n2);
    }

    public static void a(int n2, int n3, int n4, int n5, int n6) {
        GlStateManager._glFramebufferTexture2D((int)n2, (int)n3, (int)n4, (int)n5, (int)n6);
    }

    public static void p(int n2) {
        GlStateManager._clearColor((float)0.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        GlStateManager._clear((int)n2, (boolean)false);
    }

    public static void i() {
        GlStateManager._enableDepthTest();
    }

    public static void j() {
        GlStateManager._disableDepthTest();
    }

    public static void k() {
        GlStateManager._enableBlend();
        GlStateManager._blendFunc((int)770, (int)771);
    }

    public static void l() {
        GlStateManager._disableBlend();
    }

    public static void m() {
        GlStateManager._enableCull();
    }

    public static void n() {
        GlStateManager._disableCull();
    }

    public static void o() {
        GlStateManager._enableScissorTest();
    }

    public static void p() {
        GlStateManager._disableScissorTest();
    }

    public static void q() {
        GL32C.glEnable((int)2848);
        GL32C.glLineWidth((float)1.0f);
    }

    public static void r() {
        GL32C.glDisable((int)2848);
    }

    public static void a(ResourceLocation resourceLocation) {
        GlStateManager._activeTexture((int)33984);
        Minecraft.m_91087_().m_91097_().m_174784_(resourceLocation);
    }

    public static void c(int n2, int n3) {
        GlStateManager._activeTexture((int)(33984 + n3));
        GlStateManager._bindTexture((int)n2);
    }

    public static void q(int n2) {
        com.heypixel.heypixelmod.obsoverlay.utils.renderer.RenderStateManager.c(n2, 0);
    }

    public static void s() {
        GlStateManager._activeTexture((int)33984);
    }

    private static GenericValueAccessor a(String string) {
        try {
            Class<GlStateManager> clazz = GlStateManager.class;
            Field field = clazz.getDeclaredField(string);
            field.setAccessible(true);
            Object object = field.get(null);
            String string2 = "com.mojang.blaze3d.platform.GlStateManager$BooleanState";
            Field field2 = null;
            for (Field field3 : object.getClass().getDeclaredFields()) {
                if (!field3.getType().getName().equals(string2)) continue;
                field2 = field3;
                break;
            }
            field2.setAccessible(true);
            return (GenericValueAccessor)field2.get(object);
        }
        catch (IllegalAccessException | NoSuchFieldException reflectiveOperationException) {
            reflectiveOperationException.printStackTrace();
            return null;
        }
    }
}

