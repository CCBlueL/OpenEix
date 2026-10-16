/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils.renderer;

import com.heypixel.heypixelmod.obsoverlay.utils.MathUtils;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class RenderTargetManager {
    public static Color a(int n2) {
        return RenderTargetManager.a(n2, 1.0f);
    }

    public static Color a(int n2, float f2) {
        f2 = Math.min(1.0f, Math.max(0.0f, f2));
        return new Color(n2, n2, n2, (int)(255.0f * f2));
    }

    public static Color a(float[] fArray, Color[] colorArray, float f2) {
        int n2;
        int[] nArray = new int[2];
        for (n2 = 0; n2 < fArray.length && fArray[n2] <= f2; ++n2) {
        }
        if (n2 >= fArray.length) {
            n2 = fArray.length - 1;
        }
        nArray[0] = n2 - 1;
        nArray[1] = n2;
        float[] fArray2 = new float[]{fArray[nArray[0]], fArray[nArray[1]]};
        Color[] colorArray2 = new Color[]{colorArray[nArray[0]], colorArray[nArray[1]]};
        float f3 = fArray2[1] - fArray2[0];
        float f4 = f2 - fArray2[0];
        float f5 = f4 / f3;
        float f6 = 1.0f - f5;
        float f7 = 1.0f - f6;
        float[] fArray3 = new float[3];
        float[] fArray4 = new float[3];
        colorArray2[0].getColorComponents(fArray3);
        colorArray2[1].getColorComponents(fArray4);
        float f8 = fArray3[0] * f6 + fArray4[0] * f7;
        float f9 = fArray3[1] * f6 + fArray4[1] * f7;
        float f10 = fArray3[2] * f6 + fArray4[2] * f7;
        if (f8 < 0.0f) {
            f8 = 0.0f;
        } else if (f8 > 255.0f) {
            f8 = 255.0f;
        }
        if (f9 < 0.0f) {
            f9 = 0.0f;
        } else if (f9 > 255.0f) {
            f9 = 255.0f;
        }
        if (f10 < 0.0f) {
            f10 = 0.0f;
        } else if (f10 > 255.0f) {
            f10 = 255.0f;
        }
        return new Color(f8, f9, f10);
    }

    public static Color[] a(Color color) {
        Color[] colorArray = new Color[2];
        float[] fArray = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        float f2 = 0.083333336f;
        float f3 = fArray[0] + f2;
        colorArray[0] = new Color(Color.HSBtoRGB(f3, fArray[1], fArray[2]));
        float f4 = fArray[0] - f2;
        colorArray[1] = new Color(Color.HSBtoRGB(f4, fArray[1], fArray[2]));
        return colorArray;
    }

    public static Color a() {
        return new Color(Color.HSBtoRGB((float)Math.random(), (float)(0.5 + Math.random() / 2.0), (float)(0.5 + Math.random() / 2.0)));
    }

    public static Color a(float[] fArray) {
        if (fArray[1] == 0.0f) {
            var3_1 = 1.0f;
            var2_2 = 1.0f;
            var1_3 = 1.0f;
        } else {
            float f2 = (double)fArray[2] < 0.5 ? fArray[2] * (1.0f + fArray[1]) : fArray[2] + fArray[1] - fArray[2] * fArray[1];
            float f3 = 2.0f * fArray[2] - f2;
            var1_3 = RenderTargetManager.a(f3, f2, fArray[0] + 0.33333334f);
            var2_2 = RenderTargetManager.a(f3, f2, fArray[0]);
            var3_1 = RenderTargetManager.a(f3, f2, fArray[0] - 0.33333334f);
        }
        return new Color((int)(var1_3 *= 255.0f), (int)(var2_2 *= 255.0f), (int)(var3_1 *= 255.0f));
    }

    public static float a(float f2, float f3, float f4) {
        float f5 = f4;
        if (f5 < 0.0f) {
            f5 += 1.0f;
        }
        if (f5 > 1.0f) {
            f5 -= 1.0f;
        }
        if (f5 < 0.16666667f) {
            return f2 + (f3 - f2) * 6.0f * f5;
        }
        if (f5 < 0.5f) {
            return f3;
        }
        if (f5 < 0.6666667f) {
            return f2 + (f3 - f2) * (0.6666667f - f5) * 6.0f;
        }
        return f2;
    }

    public static float[] b(Color color) {
        float f2 = (float)color.getRed() / 255.0f;
        float f3 = (float)color.getGreen() / 255.0f;
        float f4 = (float)color.getBlue() / 255.0f;
        float f5 = Math.max(Math.max(f2, f3), f4);
        float f6 = Math.min(Math.min(f2, f3), f4);
        float f7 = (f5 + f6) / 2.0f;
        float[] fArray = new float[]{f7, f7, f7};
        if (f5 == f6) {
            fArray[1] = 0.0f;
            fArray[0] = 0.0f;
        } else {
            float f8 = f5 - f6;
            float f9 = fArray[1] = (double)fArray[2] > 0.5 ? f8 / (2.0f - f5 - f6) : f8 / (f5 + f6);
            if (f5 == f2) {
                fArray[0] = (f3 - f4) / f8 + (float)(f3 < f4 ? 6 : 0);
            } else if (f5 == f4) {
                fArray[0] = (f4 - f2) / f8 + 2.0f;
            } else if (f5 == f3) {
                fArray[0] = (f2 - f3) / f8 + 4.0f;
            }
            fArray[0] = fArray[0] / 6.0f;
        }
        return fArray;
    }

    public static Color a(Color color, Color color2, float f2) {
        return new Color(RenderTargetManager.b(color, color2, 255.0f * f2 / 255.0f));
    }

    public static int b(int n2, float f2) {
        Color color = new Color(n2);
        return RenderTargetManager.a(color, f2).getRGB();
    }

    public static Color a(Color color, float f2) {
        f2 = Math.min(1.0f, Math.max(0.0f, f2));
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), (int)((float)color.getAlpha() * f2));
    }

    public static Color b(Color color, float f2) {
        return new Color(Math.max((int)((float)color.getRed() * f2), 0), Math.max((int)((float)color.getGreen() * f2), 0), Math.max((int)((float)color.getBlue() * f2), 0), color.getAlpha());
    }

    public static Color c(Color color, float f2) {
        int n2 = color.getRed();
        int n3 = color.getGreen();
        int n4 = color.getBlue();
        int n5 = color.getAlpha();
        int n6 = (int)(1.0 / (1.0 - (double)f2));
        if (n2 == 0 && n3 == 0 && n4 == 0) {
            return new Color(n6, n6, n6, n5);
        }
        if (n2 > 0 && n2 < n6) {
            n2 = n6;
        }
        if (n3 > 0 && n3 < n6) {
            n3 = n6;
        }
        if (n4 > 0 && n4 < n6) {
            n4 = n6;
        }
        return new Color(Math.min((int)((float)n2 / f2), 255), Math.min((int)((float)n3 / f2), 255), Math.min((int)((float)n4 / f2), 255), n5);
    }

    public static Color a(BufferedImage bufferedImage, int n2, int n3, int n4) {
        int n5;
        int[] nArray = new int[3];
        for (n5 = 0; n5 < n2; n5 += n4) {
            for (int i2 = 0; i2 < n3; i2 += n4) {
                Color color = new Color(bufferedImage.getRGB(n5, i2));
                nArray[0] = nArray[0] + color.getRed();
                nArray[1] = nArray[1] + color.getGreen();
                nArray[2] = nArray[2] + color.getBlue();
            }
        }
        n5 = n2 * n3 / (n4 * n4);
        return new Color(nArray[0] / n5, nArray[1] / n5, nArray[2] / n5);
    }

    public static Color a(int n2, int n3, float f2, float f3, float f4) {
        int n4 = (int)((System.currentTimeMillis() / (long)n2 + (long)n3) % 360L);
        float f5 = (float)n4 / 360.0f;
        Color color = new Color(Color.HSBtoRGB(f5, f2, f3));
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.max(0, Math.min(255, (int)(f4 * 255.0f))));
    }

    public static Color a(int n2, int n3, Color color, Color color2, boolean bl) {
        int n4 = (int)((System.currentTimeMillis() / (long)n2 + (long)n3) % 360L);
        n4 = (n4 >= 180 ? 360 - n4 : n4) * 2;
        return bl ? RenderTargetManager.d(color, color2, (float)n4 / 360.0f) : RenderTargetManager.c(color, color2, (float)n4 / 360.0f);
    }

    public static int b(Color color, Color color2, float f2) {
        f2 = Math.min(1.0f, Math.max(0.0f, f2));
        return RenderTargetManager.c(color, color2, f2).getRGB();
    }

    public static int a(int n2, int n3, float f2) {
        f2 = Math.min(1.0f, Math.max(0.0f, f2));
        Color color = new Color(n2);
        Color color2 = new Color(n3);
        return RenderTargetManager.c(color, color2, f2).getRGB();
    }

    public static Color c(Color color, Color color2, float f2) {
        f2 = Math.min(1.0f, Math.max(0.0f, f2));
        return new Color(MathUtils.a(color.getRed(), color2.getRed(), (double)f2), MathUtils.a(color.getGreen(), color2.getGreen(), (double)f2), MathUtils.a(color.getBlue(), color2.getBlue(), (double)f2), MathUtils.a(color.getAlpha(), color2.getAlpha(), (double)f2));
    }

    public static Color d(Color color, Color color2, float f2) {
        f2 = Math.min(1.0f, Math.max(0.0f, f2));
        float[] fArray = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        float[] fArray2 = Color.RGBtoHSB(color2.getRed(), color2.getGreen(), color2.getBlue(), null);
        Color color3 = Color.getHSBColor(MathUtils.a(fArray[0], fArray2[0], (double)f2), MathUtils.a(fArray[1], fArray2[1], (double)f2), MathUtils.a(fArray[2], fArray2[2], (double)f2));
        return RenderTargetManager.a(color3, (float)MathUtils.a(color.getAlpha(), color2.getAlpha(), (double)f2) / 255.0f);
    }

    public static Color a(int n2, int n3, Color color, float f2) {
        float[] fArray = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        int n4 = (int)((System.currentTimeMillis() / (long)n2 + (long)n3) % 360L);
        n4 = (n4 > 180 ? 360 - n4 : n4) + 180;
        Color color2 = new Color(Color.HSBtoRGB(fArray[0], fArray[1], (float)n4 / 360.0f));
        return new Color(color2.getRed(), color2.getGreen(), color2.getBlue(), Math.max(0, Math.min(255, (int)(f2 * 255.0f))));
    }

    private static float a(int n2, int n3) {
        int n4 = (int)((System.currentTimeMillis() / (long)n3 + (long)n2) % 360L);
        return (float)((n4 > 180 ? 360 - n4 : n4) + 180) / 360.0f;
    }

    public static int[] b(int n2) {
        return new int[]{RenderTargetManager.b(n2, 16), RenderTargetManager.b(n2, 8), RenderTargetManager.b(n2, 0), RenderTargetManager.b(n2, 24)};
    }

    public static int c(int n2) {
        int n3 = RenderTargetManager.b(n2, 0);
        int n4 = RenderTargetManager.b(n2, 8);
        int n5 = RenderTargetManager.b(n2, 16);
        int n6 = RenderTargetManager.b(n2, 24);
        n3 = 255 - n3;
        n4 = 255 - n4;
        n5 = 255 - n5;
        return n3 + (n4 << 8) + (n5 << 16) + (n6 << 24);
    }

    public static Color c(Color color) {
        return new Color(RenderTargetManager.c(color.getRGB()));
    }

    private static int b(int n2, int n3) {
        return n2 >> n3 & 0xFF;
    }
}

