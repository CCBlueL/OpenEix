/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.render.a.a;

import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.a.a.EntityColorRule;
import java.awt.Color;
import java.util.Collections;
import java.util.HashSet;
import net.minecraft.world.entity.projectile.ThrownPotion;

public class PotionColorRule
extends EntityColorRule {
    public PotionColorRule() {
        super(new HashSet(Collections.singleton(ThrownPotion.class)), new Color(255, 66, 249));
    }

    @Override
    public float c() {
        return 0.05f;
    }
}

