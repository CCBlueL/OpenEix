/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class MathUtils {
    public static final double a = Math.PI;
    public static final Random b = new Random();
    static final double c = Math.PI * 2;
    static final float d = (float)Math.PI;
    static final float e = (float)Math.PI * 2;
    static final double f = 1.5707963267948966;
    static final float g = 1.5707964f;
    static final double h = 0.7853981633974483;
    static final double i = 0.3183098861837907;

    public static float a(float f2) {
        if (f2 > 90.0f) {
            return 90.0f;
        }
        return f2 < -90.0f ? -90.0f : f2;
    }

    public static float a(float f2, float f3, float f4, float f5, float f6) {
        return (f2 - f3) / (f4 - f3) * (f6 - f5) + f5;
    }

    public static double a(double d2, double d3, double d4) {
        return d2 < d3 ? d3 : Math.min(d2, d4);
    }

    public static int a(int n2, int n3, int n4) {
        return n2 < n3 ? n3 : Math.min(n2, n4);
    }

    public static <T extends Number> T a(T t2, T t3, T t4) {
        if (t2 instanceof Integer) {
            if (t2.intValue() > t4.intValue()) {
                t2 = t4;
            } else if (t2.intValue() < t3.intValue()) {
                t2 = t3;
            }
        } else if (t2 instanceof Float) {
            if (t2.floatValue() > t4.floatValue()) {
                t2 = t4;
            } else if (t2.floatValue() < t3.floatValue()) {
                t2 = t3;
            }
        } else if (t2 instanceof Double) {
            if (t2.doubleValue() > t4.doubleValue()) {
                t2 = t4;
            } else if (t2.doubleValue() < t3.doubleValue()) {
                t2 = t3;
            }
        } else if (t2 instanceof Long) {
            if (t2.longValue() > t4.longValue()) {
                t2 = t4;
            } else if (t2.longValue() < t3.longValue()) {
                t2 = t3;
            }
        } else if (t2 instanceof Short) {
            if (t2.shortValue() > t4.shortValue()) {
                t2 = t4;
            } else if (t2.shortValue() < t3.shortValue()) {
                t2 = t3;
            }
        } else if (t2 instanceof Byte) {
            if (t2.byteValue() > t4.byteValue()) {
                t2 = t4;
            } else if (t2.byteValue() < t3.byteValue()) {
                t2 = t3;
            }
        }
        return t2;
    }

    public static double a(double d2, double d3) {
        return d2 >= d3 ? d2 : b.nextDouble() * (d3 - d2) + d2;
    }

    public static float a(float f2, float f3) {
        return f2 >= f3 ? f2 : b.nextFloat() * (f3 - f2) + f2;
    }

    public static int a(int n2, int n3) {
        return n3 - n2 <= 0 ? n2 : n2 + new Random().nextInt(n3 - n2);
    }

    public static int b(int n2, int n3) {
        if (n3 <= n2) {
            return n2;
        }
        double d2 = (double)(n2 + n3) / 2.0;
        double d3 = (double)(n3 - n2) / 6.0;
        double d4 = d2 + b.nextGaussian() * d3;
        int n4 = (int)Math.round(d4);
        return MathUtils.a(n4, n2, n3);
    }

    public static float b(float f2) {
        float f3 = f2 % 360.0f;
        return f3 < -180.0f ? f3 + 360.0f : (f3 > 180.0f ? f3 - 360.0f : f3);
    }

    public static Double b(double d2, double d3, double d4) {
        return d2 + (d3 - d2) * d4;
    }

    public static float a(float f2, float f3, double d2) {
        return MathUtils.b((double)f2, (double)f3, (double)((float)d2)).floatValue();
    }

    public static int a(int n2, int n3, double d2) {
        return MathUtils.b((double)n2, (double)n3, (double)((float)d2)).intValue();
    }

    public static float a(float f2, float f3, float f4) {
        return f3 + f2 * MathUtils.b(f4 - f3);
    }

    public static double a(double d2, int n2) {
        if (n2 < 0) {
            throw new IllegalArgumentException();
        }
        return new BigDecimal(d2).setScale(n2, RoundingMode.HALF_UP).doubleValue();
    }

    public static <T extends Number> int a(T t2) {
        if (!(t2 instanceof Integer) && !(t2 instanceof Long)) {
            String[] stringArray = t2.toString().split("\\.");
            if (stringArray.length == 2) {
                if (stringArray[1].endsWith("0")) {
                    stringArray[1] = stringArray[1].substring(0, stringArray[1].length() - 1);
                }
                return stringArray[1].length();
            }
            return 0;
        }
        return 0;
    }

    public static float b(float f2, float f3, float f4) {
        return f2 < f3 ? f3 : Math.min(f2, f4);
    }

    public static int b(int n2, int n3, int n4) {
        return n2 < n3 ? n3 : Math.min(n2, n4);
    }

    public static int a(int n2) {
        int n3 = n2 - 1;
        n3 |= n3 >> 1;
        n3 |= n3 >> 2;
        n3 |= n3 >> 4;
        n3 |= n3 >> 8;
        n3 |= n3 >> 16;
        return n3 + 1;
    }

    public static float b(double d2, double d3) {
        return (float)(Math.log(d3) / Math.log(d2));
    }

    public static double b(double d2, int n2) {
        if (n2 < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bigDecimal = new BigDecimal(d2);
        bigDecimal = bigDecimal.setScale(n2, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }

    public static boolean a(float f2, float f3, float f4, float f5, float f6, float f7) {
        return f2 > f4 && f2 < f6 && f3 > f5 && f3 < f7;
    }

    public static float b(float f2, float f3) {
        double d2 = 3.141592653;
        double d3 = 1.0 / Math.sqrt(2.0 * d2 * (double)(f3 * f3));
        return (float)(d3 * Math.exp((double)(-(f2 * f2)) / (2.0 * (double)(f3 * f3))));
    }

    public static double c(double d2, double d3) {
        double d4 = MathUtils.a(1.0 - d2 * d2);
        double d5 = d3 + 1.5707963267948966;
        double d6 = d5 - (double)((int)(d5 / (Math.PI * 2))) * (Math.PI * 2);
        if (d6 < 0.0) {
            d6 += Math.PI * 2;
        }
        return d6 >= Math.PI ? -d4 : d4;
    }

    public static double a(double d2) {
        return Math.sqrt(d2);
    }
}

