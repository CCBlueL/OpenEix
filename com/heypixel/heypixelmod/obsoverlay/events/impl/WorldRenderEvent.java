/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.events.impl;

import com.heypixel.heypixelmod.obsoverlay.events.api.events.Event;
import com.mojang.blaze3d.vertex.PoseStack;

public class WorldRenderEvent
implements Event {
    private final float a;
    private final PoseStack b;

    public WorldRenderEvent(float f2, PoseStack poseStack) {
        this.a = f2;
        this.b = poseStack;
    }

    public float a() {
        return this.a;
    }

    public PoseStack b() {
        return this.b;
    }
}

