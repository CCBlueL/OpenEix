/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils.renderer.text;

import com.heypixel.heypixelmod.obsoverlay.utils.renderer.text.Glyph;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.chars.Char2ObjectArrayMap;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.lang.reflect.Field;
import java.nio.IntBuffer;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

class NativeImagePixelReader {
    private static final Minecraft i = Minecraft.m_91087_();
    final char a;
    final char b;
    final Font c;
    final ResourceLocation d;
    final int e;
    private final Char2ObjectArrayMap<Glyph> j = new Char2ObjectArrayMap();
    int f;
    int g;
    boolean h = false;

    public NativeImagePixelReader(char c2, char c3, Font font, ResourceLocation resourceLocation, int n2) {
        this.a = c2;
        this.b = c3;
        this.c = font;
        this.d = resourceLocation;
        this.e = n2;
    }

    public static long a(NativeImage nativeImage) {
        try {
            Field field = NativeImage.class.getDeclaredField("pixels");
            field.setAccessible(true);
            return (Long)field.get(nativeImage);
        }
        catch (IllegalAccessException | NoSuchFieldException reflectiveOperationException) {
            throw new RuntimeException("Cannot access pixels in NativeImage", reflectiveOperationException);
        }
    }

    public static long b(NativeImage nativeImage) {
        try {
            Field field = NativeImage.class.getDeclaredField("f_84964_");
            field.setAccessible(true);
            return (Long)field.get(nativeImage);
        }
        catch (IllegalAccessException | NoSuchFieldException reflectiveOperationException) {
            throw new RuntimeException("Cannot access pixels in NativeImage", reflectiveOperationException);
        }
    }

    public static void a(ResourceLocation resourceLocation, BufferedImage bufferedImage) {
        try {
            int n2 = bufferedImage.getWidth();
            int n3 = bufferedImage.getHeight();
            NativeImage nativeImage = new NativeImage(NativeImage.Format.RGBA, n2, n3, false);
            long l2 = com.heypixel.heypixelmod.obsoverlay.utils.renderer.text.NativeImagePixelReader.a(nativeImage);
            try {
                Class.forName("net.minecraft.client.Minecraft").getDeclaredField("instance");
            }
            catch (ClassNotFoundException | NoSuchFieldException reflectiveOperationException) {
                l2 = com.heypixel.heypixelmod.obsoverlay.utils.renderer.text.NativeImagePixelReader.b(nativeImage);
            }
            IntBuffer intBuffer = MemoryUtil.memIntBuffer((long)l2, (int)(nativeImage.m_84982_() * nativeImage.m_85084_()));
            boolean bl = false;
            WritableRaster writableRaster = bufferedImage.getRaster();
            ColorModel colorModel = bufferedImage.getColorModel();
            int n4 = writableRaster.getNumBands();
            int n5 = writableRaster.getDataBuffer().getDataType();
            Object[] objectArray = switch (n5) {
                case 0 -> new byte[n4];
                case 1 -> (Object[])new short[n4];
                case 3 -> (Object[])new int[n4];
                case 4 -> (Object[])new float[n4];
                case 5 -> (Object[])new double[n4];
                default -> throw new IllegalArgumentException("Unknown data buffer type: " + n5);
            };
            for (int i2 = 0; i2 < n3; ++i2) {
                for (int i3 = 0; i3 < n2; ++i3) {
                    writableRaster.getDataElements(i3, i2, objectArray);
                    int n6 = colorModel.getAlpha(objectArray);
                    int n7 = colorModel.getRed(objectArray);
                    int n8 = colorModel.getGreen(objectArray);
                    int n9 = colorModel.getBlue(objectArray);
                    int n10 = n6 << 24 | n9 << 16 | n8 << 8 | n7;
                    intBuffer.put(n10);
                }
            }
            DynamicTexture dynamicTexture = new DynamicTexture(nativeImage);
            dynamicTexture.m_117985_();
            RenderSystem.bindTexture((int)dynamicTexture.m_117963_());
            GL11.glTexParameteri((int)3553, (int)10241, (int)9729);
            GL11.glTexParameteri((int)3553, (int)10240, (int)9729);
            if (RenderSystem.isOnRenderThread()) {
                i.m_91097_().m_118495_(resourceLocation, (AbstractTexture)dynamicTexture);
            } else {
                RenderSystem.recordRenderCall(() -> i.m_91097_().m_118495_(resourceLocation, (AbstractTexture)dynamicTexture));
            }
        }
        catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    public Glyph a(char c2) {
        if (!this.h) {
            this.b();
        }
        return (Glyph)this.j.get(c2);
    }

    public void a() {
        i.m_91097_().m_118513_(this.d);
        this.j.clear();
        this.f = -1;
        this.g = -1;
        this.h = false;
    }

    public boolean b(char c2) {
        return c2 >= this.a && c2 < this.b;
    }

    private Font c(char c2) {
        return this.c;
    }

    public void b() {
        if (!this.h) {
            int n2 = this.b - this.a - 1;
            int n3 = (int)(Math.ceil(Math.sqrt(n2)) * 1.5);
            this.j.clear();
            int n4 = 0;
            int n5 = 0;
            int n6 = 0;
            int n7 = 0;
            int n8 = 0;
            int n9 = 0;
            int n10 = 0;
            ArrayList<Glyph> arrayList = new ArrayList<Glyph>();
            AffineTransform affineTransform = new AffineTransform();
            Object object = new FontRenderContext(affineTransform, true, false);
            while (n4 <= n2) {
                char c2 = (char)(this.a + n4);
                Font font = this.c(c2);
                Rectangle2D object2 = font.getStringBounds(String.valueOf(c2), (FontRenderContext)object);
                int n11 = (int)Math.ceil(object2.getWidth());
                int n12 = (int)Math.ceil(object2.getHeight());
                ++n4;
                n6 = Math.max(n6, n8 + n11);
                n7 = Math.max(n7, n9 + n12);
                if (n5 >= n3) {
                    n8 = 0;
                    n9 += n10 + this.e;
                    n5 = 0;
                    n10 = 0;
                }
                n10 = Math.max(n10, n12);
                arrayList.add(new Glyph(n8, n9, n11, n12, c2, this));
                n8 += n11 + this.e;
                ++n5;
            }
            object = new BufferedImage(Math.max(n6 + this.e, 1), Math.max(n7 + this.e, 1), 2);
            this.f = ((BufferedImage)object).getWidth();
            this.g = ((BufferedImage)object).getHeight();
            Graphics2D graphics2D = ((BufferedImage)object).createGraphics();
            graphics2D.setColor(new Color(255, 255, 255, 1));
            graphics2D.fillRect(0, 0, this.f, this.g);
            graphics2D.setColor(Color.WHITE);
            graphics2D.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            for (Glyph glyph : arrayList) {
                graphics2D.setFont(this.c(glyph.value()));
                FontMetrics fontMetrics = graphics2D.getFontMetrics();
                graphics2D.drawString(String.valueOf(glyph.value()), glyph.u(), glyph.v() + fontMetrics.getAscent());
                this.j.put(glyph.value(), (Object)glyph);
            }
            com.heypixel.heypixelmod.obsoverlay.utils.renderer.text.NativeImagePixelReader.a(this.d, (BufferedImage)object);
            this.h = true;
        }
    }
}

