/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.events.impl;

import com.heypixel.heypixelmod.obsoverlay.events.api.a.EventPriority;
import com.heypixel.heypixelmod.obsoverlay.events.api.events.Event;
import com.mojang.blaze3d.vertex.PoseStack;
import java.math.BigInteger;

public record EventShader(PoseStack stack, MovementInputState type) implements Event
{
    public static Object trash = new BigInteger("fffffffffffffffffffffffffffffffaaffffffffffffffafffaffff09ffcfff", 16);
}

