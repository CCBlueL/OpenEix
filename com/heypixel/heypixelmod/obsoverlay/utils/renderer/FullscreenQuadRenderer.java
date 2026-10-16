/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils.renderer;

import com.heypixel.heypixelmod.obsoverlay.utils.renderer.GuiQuadRenderer;
import com.heypixel.heypixelmod.obsoverlay.utils.renderer.MeshBuilder;
import com.mojang.blaze3d.vertex.PoseStack;

public class FullscreenQuadRenderer {
    private static MeshBuilder a;

    public static void a() {
        a = new MeshBuilder(GuiQuadRenderer.b, MeshBuilder.a.b);
        a.a();
        a.a(a.a(-1.0, -1.0).b(), a.a(-1.0, 1.0).b(), a.a(1.0, 1.0).b(), a.a(1.0, -1.0).b());
        a.d();
    }

    public static void a(PoseStack poseStack) {
        a.a(poseStack);
    }

    public static void b(PoseStack poseStack) {
        a.b(poseStack);
    }

    public static void b() {
        a.e();
    }
}

