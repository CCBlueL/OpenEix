/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.render.a.a;

import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.a.a.EntityColorRule;
import java.awt.Color;
import java.util.Collections;
import java.util.HashSet;
import net.minecraft.world.entity.projectile.Arrow;

public class ArrowColorRule
extends EntityColorRule {
    public ArrowColorRule() {
        super(new HashSet(Collections.singletonList(Arrow.class)), new Color(255, 0, 0));
    }

    @Override
    public float a() {
        return 0.25f;
    }

    @Override
    public float b() {
        return 0.5f;
    }

    @Override
    public float c() {
        return 0.05f;
    }
}

