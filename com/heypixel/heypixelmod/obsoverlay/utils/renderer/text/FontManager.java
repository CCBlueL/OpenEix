/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils.renderer.text;

import com.heypixel.heypixelmod.obsoverlay.utils.renderer.RenderStateManager;
import com.heypixel.heypixelmod.obsoverlay.utils.renderer.MeshBuilder;
import com.heypixel.heypixelmod.obsoverlay.utils.renderer.CallbackRenderMesh;
import com.heypixel.heypixelmod.obsoverlay.utils.renderer.TextShaderProgram;
import com.heypixel.heypixelmod.obsoverlay.utils.renderer.text.StbGlyphFontRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.BufferUtils;

public class FontManager {
    private static final Logger a = LogManager.getLogger(FontManager.class);
    private static final Color b = new Color(60, 60, 60, 180);
    private final MeshBuilder c = new CallbackRenderMesh(TextShaderProgram.a, com.heypixel.heypixelmod.obsoverlay.utils.renderer.GuiQuadRenderer.b, MeshBuilder.a.b, MeshBuilder.a.b, MeshBuilder.a.d);
    private final StbGlyphFontRenderer d;

    public FontManager(String string, int n2, int n3, int n4, int n5) {
        byte[] byArray;
        Object object;
        InputStream inputStream = this.getClass().getResourceAsStream("/assets/heypixel/VcX6svVqmeT8/fonts/" + string + ".ttf");
        if (inputStream == null) {
            throw new RuntimeException("Font not found: " + string);
        }
        try {
            int n6;
            object = new ByteArrayOutputStream();
            byte[] byArray2 = new byte[1024];
            while ((n6 = inputStream.read(byArray2)) != -1) {
                object.write(byArray2, 0, n6);
            }
            byArray = object.toByteArray();
        }
        catch (IOException iOException) {
            throw new RuntimeException("Failed to read font: " + string, iOException);
        }
        object = BufferUtils.createByteBuffer((int)byArray.length).put(byArray);
        ((Buffer)object).flip();
        long l2 = System.currentTimeMillis();
        this.d = new StbGlyphFontRenderer((ByteBuffer)object, n2, n3, n4, n5);
        a.info("Loaded font {} in {}ms", (Object)string, (Object)(System.currentTimeMillis() - l2));
    }

    public void a(float f2) {
        this.c.b = f2;
    }

    public float a(String string, double d2) {
        return (float)this.a(string, false, d2);
    }

    public double a(String string, boolean bl, double d2) {
        return (this.d.a(string) + (double)(bl ? 0.5f : 0.0f)) * d2;
    }

    public double a(boolean bl, double d2) {
        return (this.d.a() + (double)(bl ? 0.5f : 0.0f)) * d2;
    }

    public double a(PoseStack poseStack, String string, double d2, double d3, Color color, boolean bl, double d4) {
        double d5;
        this.c.a();
        if (bl) {
            d5 = this.d.a(this.c, string, d2 + 0.5, d3 + 0.5, b, d4, true);
            this.d.a(this.c, string, d2, d3, color, d4, false);
        } else {
            d5 = this.d.a(this.c, string, d2, d3, color, d4, false);
        }
        this.c.d();
        RenderStateManager.q(this.d.a.m_117963_());
        this.c.b(poseStack);
        return d5;
    }
}

