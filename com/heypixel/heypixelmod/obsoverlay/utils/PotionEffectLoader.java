/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;
import net.minecraft.world.effect.MobEffect;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PotionEffectLoader {
    private static final Logger a = LogManager.getLogger(PotionEffectLoader.class);
    private static final Map<Integer, List<MobEffect>> b = new HashMap<Integer, List<MobEffect>>();

    public static List<MobEffect> a(int n2) {
        if (b.containsKey(n2)) {
            return b.get(n2);
        }
        if (b.containsKey(n2 + 1)) {
            return b.get(n2 + 1);
        }
        return b.containsKey(n2 - 1) ? b.get(n2 - 1) : Collections.emptyList();
    }

    static {
        InputStream inputStream = PotionEffectLoader.class.getResourceAsStream("/assets/heypixel/VcX6svVqmeT8/potion_effects.dat");
        if (inputStream != null) {
            try {
                GZIPInputStream gZIPInputStream = new GZIPInputStream(inputStream);
                for (String string : IOUtils.readLines((InputStream)gZIPInputStream)) {
                    String[] stringArray = string.split(":");
                    if (stringArray.length != 2) continue;
                    int n2 = Integer.parseInt(stringArray[0]);
                    String string2 = stringArray[1];
                    String[] stringArray2 = string2.split("\\+");
                    List list = Arrays.stream(stringArray2).map(Integer::parseInt).map(MobEffect::m_19453_).collect(Collectors.toList());
                    b.put(n2, list);
                }
            }
            catch (Exception exception) {
                a.error("Failed to load potion effects", (Throwable)exception);
            }
        }
    }
}

