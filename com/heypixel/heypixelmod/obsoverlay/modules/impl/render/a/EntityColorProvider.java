/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.render.a;

import java.awt.Color;
import net.minecraft.world.entity.Entity;

public interface EntityColorProvider {
    public Color a(Object var1);

    default public float a() {
        return 0.125f;
    }

    public boolean a(Entity var1);

    default public float b() {
        return 0.25f;
    }

    default public float c() {
        return 0.03f;
    }
}

