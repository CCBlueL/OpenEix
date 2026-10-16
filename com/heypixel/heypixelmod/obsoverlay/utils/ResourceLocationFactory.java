/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils;

import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResourceLocationFactory {
    private static final Logger b = LogManager.getLogger(ResourceLocationFactory.class);
    private static final int c = 0;
    private final String d;
    public boolean a = false;
    private ResourceLocation e;

    public ResourceLocationFactory(String string) {
        this.d = string;
    }

    public String a() {
        return this.d;
    }
}

