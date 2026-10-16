/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.render.a.a;

import java.awt.Color;
import java.util.Set;
import net.minecraft.world.entity.Entity;

public class EntityColorRule
implements com.heypixel.heypixelmod.obsoverlay.modules.impl.render.Animations.a {
    private final Color a;
    private final Set<Class<?>> b;

    public EntityColorRule(Set<Class<?>> set) {
        this(set, new Color(255, 255, 255));
    }

    public EntityColorRule(Set<Class<?>> set, Color color) {
        this.b = set;
        this.a = color;
    }

    @Override
    public Color a(Object object) {
        return this.a;
    }

    @Override
    public boolean a(Entity entity) {
        for (Class<?> clazz : this.b) {
            if (!clazz.isInstance(entity)) continue;
            return true;
        }
        return false;
    }
}

