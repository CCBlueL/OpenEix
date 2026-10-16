/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils.renderer;

import com.heypixel.heypixelmod.obsoverlay.utils.renderer.RenderStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;
import org.joml.Matrix4f;

public class ShaderProgram {
    public static ShaderProgram a;
    private final int b;
    private final Object2IntMap<String> c = new Object2IntOpenHashMap();

    public ShaderProgram(String string, String string2) {
        int n2 = RenderStateManager.l(35633);
        RenderStateManager.a(n2, this.a(string));
        String string3 = RenderStateManager.m(n2);
        if (string3 != null) {
            throw new RuntimeException("Failed to compile vertex shader (" + string + "): " + string3);
        }
        int n3 = RenderStateManager.l(35632);
        RenderStateManager.a(n3, this.a(string2));
        String string4 = RenderStateManager.m(n3);
        if (string4 != null) {
            throw new RuntimeException("Failed to compile fragment shader (" + string2 + "): " + string4);
        }
        this.b = RenderStateManager.g();
        String string5 = RenderStateManager.b(this.b, n2, n3);
        if (string5 != null) {
            throw new RuntimeException("Failed to link program: " + string5);
        }
        RenderStateManager.c(n2);
        RenderStateManager.c(n3);
    }

    private String a(String string) {
        try {
            return IOUtils.toString((InputStream)this.getClass().getResourceAsStream("/assets/heypixel/VcX6svVqmeT8/shader/" + string), (Charset)StandardCharsets.UTF_8);
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            return "";
        }
    }

    public void a() {
        RenderStateManager.n(this.b);
        a = this;
    }

    private int b(String string) {
        if (this.c.containsKey((Object)string)) {
            return this.c.getInt((Object)string);
        }
        int n2 = RenderStateManager.b(this.b, string);
        this.c.put((Object)string, n2);
        return n2;
    }

    public void a(String string, boolean bl) {
        RenderStateManager.a(this.b(string), bl ? 1 : 0);
    }

    public void a(String string, int n2) {
        RenderStateManager.a(this.b(string), n2);
    }

    public void a(String string, double d2) {
        RenderStateManager.a(this.b(string), (float)d2);
    }

    public void a(String string, double d2, double d3) {
        RenderStateManager.a(this.b(string), (float)d2, (float)d3);
    }

    public void a(String string, Matrix4f matrix4f) {
        RenderStateManager.a(this.b(string), matrix4f);
    }

    public void a(String string, float ... fArray) {
        int n2 = this.b(string);
        if (fArray.length == 1) {
            RenderStateManager.a(n2, fArray[0]);
        } else if (fArray.length == 2) {
            RenderStateManager.a(n2, fArray[0], fArray[1]);
        } else if (fArray.length == 3) {
            RenderStateManager.a(n2, fArray[0], fArray[1], fArray[2]);
        } else {
            if (fArray.length != 4) {
                throw new IllegalArgumentException("Invalid number of arguments for uniform '" + string + "'");
            }
            RenderStateManager.a(n2, fArray[0], fArray[1], fArray[2], fArray[3]);
        }
    }

    public void b() {
        this.a("u_Proj", RenderSystem.getProjectionMatrix());
        this.a("u_ModelView", RenderSystem.getModelViewStack().m_85850_().m_252922_());
    }
}

