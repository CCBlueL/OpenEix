/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils.renderer.text;

import com.heypixel.heypixelmod.obsoverlay.utils.renderer.MeshBuilder;
import com.heypixel.heypixelmod.obsoverlay.utils.renderer.text.CharData;
import com.heypixel.heypixelmod.obsoverlay.utils.renderer.text.GlyphTexture;
import java.awt.Color;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.texture.AbstractTexture;
import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.stb.STBTTPackContext;
import org.lwjgl.stb.STBTTPackedchar;
import org.lwjgl.stb.STBTruetype;
import org.lwjgl.system.MemoryStack;

public class StbGlyphFontRenderer {
    private final int b;
    private final float c;
    private final float d;
    private final CharData[] e;
    private final int f;
    private final HashMap<String, Double> g = new HashMap();
    public AbstractTexture a;

    public StbGlyphFontRenderer(ByteBuffer byteBuffer, int n2, int n3, int n4, int n5) {
        this.b = n2;
        this.f = n3;
        STBTTFontinfo sTBTTFontinfo = STBTTFontinfo.create();
        STBTruetype.stbtt_InitFont((STBTTFontinfo)sTBTTFontinfo, (ByteBuffer)byteBuffer);
        this.e = new CharData[n4 + 1 - n3];
        STBTTPackedchar.Buffer buffer = STBTTPackedchar.create((int)this.e.length);
        ByteBuffer byteBuffer2 = BufferUtils.createByteBuffer((int)(n5 * n5));
        STBTTPackContext sTBTTPackContext = STBTTPackContext.create();
        STBTruetype.stbtt_PackBegin((STBTTPackContext)sTBTTPackContext, (ByteBuffer)byteBuffer2, (int)n5, (int)n5, (int)0, (int)1);
        STBTruetype.stbtt_PackSetOversampling((STBTTPackContext)sTBTTPackContext, (int)2, (int)2);
        STBTruetype.stbtt_PackFontRange((STBTTPackContext)sTBTTPackContext, (ByteBuffer)byteBuffer, (int)0, (float)n2, (int)this.f, (STBTTPackedchar.Buffer)buffer);
        STBTruetype.stbtt_PackEnd((STBTTPackContext)sTBTTPackContext);
        this.a = new GlyphTexture(n5, n5, byteBuffer2, b.b.a, b.a.b, b.a.b);
        this.c = STBTruetype.stbtt_ScaleForPixelHeight((STBTTFontinfo)sTBTTFontinfo, (float)n2);
        try (MemoryStack memoryStack = MemoryStack.stackPush();){
            IntBuffer intBuffer = memoryStack.mallocInt(1);
            STBTruetype.stbtt_GetFontVMetrics((STBTTFontinfo)sTBTTFontinfo, (IntBuffer)intBuffer, null, null);
            this.d = intBuffer.get(0);
        }
        for (int i2 = 0; i2 < this.e.length; ++i2) {
            STBTTPackedchar sTBTTPackedchar = (STBTTPackedchar)buffer.get(i2);
            float f2 = 1.0f / (float)n5;
            float f3 = 1.0f / (float)n5;
            this.e[i2] = new CharData(sTBTTPackedchar.xoff(), sTBTTPackedchar.yoff(), sTBTTPackedchar.xoff2(), sTBTTPackedchar.yoff2(), (float)sTBTTPackedchar.x0() * f2, (float)sTBTTPackedchar.y0() * f3, (float)sTBTTPackedchar.x1() * f2, (float)sTBTTPackedchar.y1() * f3, sTBTTPackedchar.xadvance());
        }
    }

    public double a(String string) {
        if (this.g.containsKey(string)) {
            return this.g.get(string);
        }
        double d2 = 0.0;
        for (int i2 = 0; i2 < string.length(); ++i2) {
            int n2 = string.charAt(i2) - this.f;
            if (n2 == 167 && i2 + 1 < string.length()) {
                ++i2;
                continue;
            }
            if (n2 >= this.e.length) {
                n2 = 0;
            }
            CharData charData = this.e[n2];
            d2 += (double)charData.xAdvance();
        }
        this.g.put(string, d2);
        return d2;
    }

    public double a() {
        return this.b;
    }

    public double a(MeshBuilder g2, String string, double d2, double d3, Color color, double d4, boolean bl) {
        Color color2 = color;
        d3 += (double)(this.d * this.c) * d4;
        for (int i2 = 0; i2 < string.length(); ++i2) {
            int n2 = string.charAt(i2) - this.f;
            if (n2 == 167 && i2 + 1 < string.length()) {
                char c2 = string.charAt(i2 + 1);
                ChatFormatting chatFormatting = ChatFormatting.m_126645_((char)c2);
                if (chatFormatting != null && chatFormatting.m_126664_() && !bl) {
                    color2 = new Color(chatFormatting.m_126665_());
                }
                ++i2;
                continue;
            }
            if (n2 >= this.e.length) {
                n2 = 0;
            }
            CharData charData = this.e[n2];
            g2.a(g2.a(d2 + (double)charData.x0() * d4, d3 + (double)charData.y0() * d4).a(charData.u0(), charData.v0()).a(color2).b(), g2.a(d2 + (double)charData.x0() * d4, d3 + (double)charData.y1() * d4).a(charData.u0(), charData.v1()).a(color2).b(), g2.a(d2 + (double)charData.x1() * d4, d3 + (double)charData.y1() * d4).a(charData.u1(), charData.v1()).a(color2).b(), g2.a(d2 + (double)charData.x1() * d4, d3 + (double)charData.y0() * d4).a(charData.u1(), charData.v0()).a(color2).b());
            d2 += (double)charData.xAdvance() * d4;
        }
        return d2;
    }
}

