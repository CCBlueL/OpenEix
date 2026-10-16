/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils.renderer.text;

import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.server.packs.resources.ResourceManager;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL30C;

public class GlyphTexture
extends AbstractTexture {
    public GlyphTexture(int n2, int n3, byte[] byArray, GlyphTexture b2, FontMetricsAdapter a2, FontMetricsAdapter a3) {
        if (!RenderSystem.isOnRenderThread()) {
            RenderSystem.recordRenderCall(() -> this.a(n2, n3, byArray, b2, a2, a3));
        } else {
            this.a(n2, n3, byArray, b2, a2, a3);
        }
    }

    public GlyphTexture(int n2, int n3, ByteBuffer byteBuffer, GlyphTexture b2, FontMetricsAdapter a2, FontMetricsAdapter a3) {
        if (!RenderSystem.isOnRenderThread()) {
            RenderSystem.recordRenderCall(() -> this.a(n2, n3, byteBuffer, b2, a2, a3));
        } else {
            this.a(n2, n3, byteBuffer, b2, a2, a3);
        }
    }

    private void a(int n2, int n3, byte[] byArray, GlyphTexture b2, FontMetricsAdapter a2, FontMetricsAdapter a3) {
        ByteBuffer byteBuffer = BufferUtils.createByteBuffer((int)byArray.length).put(byArray);
        this.a(n2, n3, byteBuffer, b2, a2, a3);
    }

    private void a(int n2, int n3, ByteBuffer byteBuffer, GlyphTexture b2, FontMetricsAdapter a2, FontMetricsAdapter a3) {
        this.m_117966_();
        GL30C.glPixelStorei((int)3312, (int)0);
        GL30C.glPixelStorei((int)3313, (int)0);
        GL30C.glPixelStorei((int)3314, (int)0);
        GL30C.glPixelStorei((int)32878, (int)0);
        GL30C.glPixelStorei((int)3315, (int)0);
        GL30C.glPixelStorei((int)3316, (int)0);
        GL30C.glPixelStorei((int)32877, (int)0);
        GL30C.glPixelStorei((int)3317, (int)4);
        GL30C.glTexParameteri((int)3553, (int)10242, (int)10497);
        GL30C.glTexParameteri((int)3553, (int)10243, (int)10497);
        GL30C.glTexParameteri((int)3553, (int)10241, (int)a2.a());
        GL30C.glTexParameteri((int)3553, (int)10240, (int)a3.a());
        ((Buffer)byteBuffer).rewind();
        GL30C.glTexImage2D((int)3553, (int)0, (int)com.heypixel.heypixelmod.obsoverlay.utils.renderer.text.GlyphTexture.TextureFilter(), (int)n2, (int)n3, (int)0, (int)com.heypixel.heypixelmod.obsoverlay.utils.renderer.text.GlyphTexture.TextureFilter(), (int)5121, (ByteBuffer)byteBuffer);
    }

    public void a(ResourceManager resourceManager) throws IOException {
    }

    public static enum PixelFormat {
        a,
        b,
        c;


        public int a() {
            if (this == a) {
                return 6403;
            }
            if (this == b) {
                return 6407;
            }
            return this == c ? 6408 : 0;
        }
    }

    public static enum TextureFilter {
        a,
        b;


        public int a() {
            return this == a ? 9728 : 9729;
        }
    }
}

