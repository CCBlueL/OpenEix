/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils.d;

import com.heypixel.heypixelmod.obsoverlay.utils.d.BufferUploaderState;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import org.apache.commons.io.IOUtils;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

public class ShaderCompiler {
    private static final FloatBuffer b = BufferUtils.createFloatBuffer((int)16);
    public static ShaderCompiler a;
    private final int c;
    private final Object2IntMap<String> d = new Object2IntOpenHashMap();

    public ShaderCompiler(String string, String string2) {
        int n2 = GlStateManager.glCreateShader((int)35633);
        GlStateManager.glShaderSource((int)n2, Collections.singletonList(this.a(string)));
        String string3 = com.heypixel.heypixelmod.obsoverlay.utils.RemotePlayer.d.c(n2);
        if (string3 != null) {
            throw new RuntimeException("Failed to compile vertex shader: " + string3);
        }
        int n3 = GlStateManager.glCreateShader((int)35632);
        GlStateManager.glShaderSource((int)n3, Collections.singletonList(this.a(string2)));
        String string4 = com.heypixel.heypixelmod.obsoverlay.utils.RemotePlayer.d.c(n3);
        if (string4 != null) {
            throw new RuntimeException("Failed to compile fragment shader: " + string4);
        }
        this.c = GlStateManager.glCreateProgram();
        String string5 = com.heypixel.heypixelmod.obsoverlay.utils.RemotePlayer.d.a(this.c, n2, n3);
        if (string5 != null) {
            throw new RuntimeException("Failed to link program: " + string5);
        }
        GlStateManager.glDeleteShader((int)n2);
        GlStateManager.glDeleteShader((int)n3);
    }

    private String a(String string) {
        try {
            return IOUtils.toString((InputStream)ShaderCompiler.class.getResourceAsStream("/assets/heypixel/VcX6svVqmeT8/shaders/" + string), (Charset)StandardCharsets.UTF_8);
        }
        catch (Exception exception) {
            throw new IllegalStateException("Could not read shader '" + string + "'", exception);
        }
    }

    public void a() {
        com.heypixel.heypixelmod.obsoverlay.utils.RemotePlayer.d.d(this.c);
        a = this;
    }

    private int b(String string) {
        if (this.d.containsKey((Object)string)) {
            return this.d.getInt((Object)string);
        }
        int n2 = com.heypixel.heypixelmod.obsoverlay.utils.RemotePlayer.d.a(this.c, string);
        this.d.put((Object)string, n2);
        return n2;
    }

    public void a(String string, boolean bl) {
        com.heypixel.heypixelmod.obsoverlay.utils.RemotePlayer.d.a(this.b(string), bl ? 1 : 0);
    }

    public void a(String string, int n2) {
        com.heypixel.heypixelmod.obsoverlay.utils.RemotePlayer.d.a(this.b(string), n2);
    }

    public void a(String string, double d2) {
        com.heypixel.heypixelmod.obsoverlay.utils.RemotePlayer.d.a(this.b(string), (float)d2);
    }

    public void a(String string, double d2, double d3) {
        com.heypixel.heypixelmod.obsoverlay.utils.RemotePlayer.d.a(this.b(string), (float)d2, (float)d3);
    }

    public void a(String string, Matrix4f matrix4f) {
        matrix4f.get(b);
        GlStateManager._glUniformMatrix4((int)this.b(string), (boolean)false, (FloatBuffer)b);
    }

    public void b() {
        this.a("u_Proj", RenderSystem.getProjectionMatrix());
        this.a("u_ModelView", RenderSystem.getModelViewMatrix());
    }
}

