/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.music.a;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

public final class MusicApiAddressSelector {
    private static final long COOLDOWN_MS = 60000L;
    private final List<String> custom = new ArrayList<String>();
    private final Map<String, Long> blockedUntil = new ConcurrentHashMap<String, Long>();
    private volatile String preferred;
    private volatile boolean autoRotate = true;

    public void a(String string) {
        if (MusicApiAddressSelector.e(string)) {
            this.preferred = string.trim();
        }
    }

    public String a() {
        return this.preferred;
    }

    public boolean b() {
        return this.autoRotate;
    }

    public void a(boolean bl) {
        this.autoRotate = bl;
    }

    public void b(String string) {
        String string2;
        if (MusicApiAddressSelector.e(string) && !this.custom.contains(string2 = string.trim())) {
            this.custom.add(string2);
        }
    }

    public List<String> c() {
        return List.copyOf(this.custom);
    }

    public void d() {
        this.custom.clear();
    }

    public void c(String string) {
        if (string != null && !string.isBlank()) {
            this.blockedUntil.put(string, System.currentTimeMillis() + 60000L);
        }
    }

    public boolean d(String string) {
        Long l2 = this.blockedUntil.get(string);
        if (l2 == null) {
            return false;
        }
        if (l2 < System.currentTimeMillis()) {
            this.blockedUntil.remove(string);
            return false;
        }
        return true;
    }

    public String e() {
        String string = this.preferred;
        if (this.f(string)) {
            return string;
        }
        for (String string2 : this.custom) {
            if (!this.f(string2)) continue;
            return string2;
        }
        if (this.autoRotate) {
            for (int i2 = 0; i2 < 64; ++i2) {
                String string2;
                string2 = MusicApiAddressSelector.f();
                if (!this.f(string2)) continue;
                return string2;
            }
        }
        return string != null ? string : "1.1.1.1";
    }

    private boolean f(String string) {
        return MusicApiAddressSelector.e(string) && !this.d(string);
    }

    public static boolean e(String string) {
        if (string == null) {
            return false;
        }
        String string2 = string.trim();
        String[] stringArray = string2.split("\\.", -1);
        if (stringArray.length != 4) {
            return false;
        }
        for (String string3 : stringArray) {
            if (string3.isEmpty() || string3.length() > 3) {
                return false;
            }
            for (int i2 = 0; i2 < string3.length(); ++i2) {
                if (Character.isDigit(string3.charAt(i2))) continue;
                return false;
            }
            if (Integer.parseInt(string3) <= 255) continue;
            return false;
        }
        return true;
    }

    public static String f() {
        int n2;
        int n3;
        int n4;
        int n5;
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        do {
            n5 = threadLocalRandom.nextInt(1, 224);
            n4 = threadLocalRandom.nextInt(0, 256);
            n3 = threadLocalRandom.nextInt(0, 256);
            n2 = threadLocalRandom.nextInt(1, 255);
        } while (n5 == 10 || n5 == 127 || n5 == 0 || n5 == 172 && n4 >= 16 && n4 <= 31 || n5 == 192 && n4 == 168 || n5 == 169 && n4 == 254 || n5 == 100 && n4 >= 64 && n4 <= 127);
        return n5 + "." + n4 + "." + n3 + "." + n2;
    }
}

