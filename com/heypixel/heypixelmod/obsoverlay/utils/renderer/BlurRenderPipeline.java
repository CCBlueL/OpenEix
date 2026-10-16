/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils.renderer;

import com.heypixel.heypixelmod.obsoverlay.events.impl.EventRender2D;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventShader;
import com.heypixel.heypixelmod.obsoverlay.utils.TargetOverlayRenderer;
import com.heypixel.heypixelmod.obsoverlay.utils.ElapsedTimer;
import com.heypixel.heypixelmod.obsoverlay.utils.renderer.RenderTargetFramebuffer;
import com.heypixel.heypixelmod.obsoverlay.utils.renderer.RenderStateManager;
import com.heypixel.heypixelmod.obsoverlay.utils.renderer.FullscreenQuadRenderer;
import com.heypixel.heypixelmod.obsoverlay.utils.renderer.ShaderProgram;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import it.unimi.dsi.fastutil.ints.IntDoubleImmutablePair;
import net.minecraft.client.Minecraft;

public class BlurRenderPipeline {
    private static final ElapsedTimer a = new ElapsedTimer();
    private static final RenderTargetFramebuffer[] b = new RenderTargetFramebuffer[6];
    private static final IntDoubleImmutablePair[] c = new IntDoubleImmutablePair[]{IntDoubleImmutablePair.of((int)1, (double)1.25), IntDoubleImmutablePair.of((int)1, (double)2.25), IntDoubleImmutablePair.of((int)2, (double)2.0), IntDoubleImmutablePair.of((int)2, (double)3.0), IntDoubleImmutablePair.of((int)2, (double)4.25), IntDoubleImmutablePair.of((int)3, (double)2.5), IntDoubleImmutablePair.of((int)3, (double)3.25), IntDoubleImmutablePair.of((int)3, (double)4.25), IntDoubleImmutablePair.of((int)3, (double)5.5), IntDoubleImmutablePair.of((int)4, (double)3.25), IntDoubleImmutablePair.of((int)4, (double)4.0), IntDoubleImmutablePair.of((int)4, (double)5.0), IntDoubleImmutablePair.of((int)4, (double)6.0), IntDoubleImmutablePair.of((int)4, (double)7.25), IntDoubleImmutablePair.of((int)4, (double)8.25), IntDoubleImmutablePair.of((int)5, (double)4.5), IntDoubleImmutablePair.of((int)5, (double)5.25), IntDoubleImmutablePair.of((int)5, (double)6.25), IntDoubleImmutablePair.of((int)5, (double)7.25), IntDoubleImmutablePair.of((int)5, (double)8.5)};
    private static ShaderProgram d;
    private static ShaderProgram e;

    public static void a(EventRender2D eventRender2D, float f2, int n2) {
        TargetOverlayRenderer.a(false);
        com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().b().a(new EventShader(eventRender2D.stack(), com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.j));
        TargetOverlayRenderer.b(true);
        if (d == null) {
            d = new ShaderProgram("blur.vert", "blur_down.frag");
            e = new ShaderProgram("blur.vert", "blur_up.frag");
            for (int i2 = 0; i2 < b.length; ++i2) {
                if (b[i2] != null) continue;
                com.heypixel.heypixelmod.obsoverlay.utils.renderer.BlurRenderPipeline.b[i2] = new RenderTargetFramebuffer(1.0 / Math.pow(2.0, i2));
            }
        }
        IntDoubleImmutablePair intDoubleImmutablePair = c[n2];
        int n3 = intDoubleImmutablePair.leftInt();
        double d2 = intDoubleImmutablePair.rightDouble();
        if (a.a((double)(1000.0f / f2))) {
            int n4;
            FullscreenQuadRenderer.a(eventRender2D.stack());
            com.heypixel.heypixelmod.obsoverlay.utils.renderer.BlurRenderPipeline.a(eventRender2D.stack(), b[0], Minecraft.m_91087_().m_91385_().m_83975_(), d, d2);
            for (n4 = 0; n4 < n3; ++n4) {
                com.heypixel.heypixelmod.obsoverlay.utils.renderer.BlurRenderPipeline.a(eventRender2D.stack(), b[n4 + 1], com.heypixel.heypixelmod.obsoverlay.utils.renderer.BlurRenderPipeline.b[n4].a, d, d2);
            }
            for (n4 = n3; n4 >= 1; --n4) {
                com.heypixel.heypixelmod.obsoverlay.utils.renderer.BlurRenderPipeline.a(eventRender2D.stack(), b[n4 - 1], com.heypixel.heypixelmod.obsoverlay.utils.renderer.BlurRenderPipeline.b[n4].a, e, d2);
            }
            Minecraft.m_91087_().m_91385_().m_83947_(true);
            a.a();
            FullscreenQuadRenderer.b(eventRender2D.stack());
            FullscreenQuadRenderer.b();
        }
        RenderSystem.bindTexture((int)com.heypixel.heypixelmod.obsoverlay.utils.renderer.BlurRenderPipeline.b[1].a);
        e.a();
        e.a("uTexture", 0);
        e.a("uHalfTexelSize", 0.5 / (double)com.heypixel.heypixelmod.obsoverlay.utils.renderer.BlurRenderPipeline.b[1].c, 0.5 / (double)com.heypixel.heypixelmod.obsoverlay.utils.renderer.BlurRenderPipeline.b[1].d);
        e.a("uOffset", d2);
        FullscreenQuadRenderer.b(eventRender2D.stack());
        TargetOverlayRenderer.a();
    }

    private static void a(PoseStack poseStack, RenderTargetFramebuffer e2, int n2, ShaderProgram i2, double d2) {
        e2.a();
        e2.b();
        i2.a();
        RenderStateManager.q(n2);
        i2.a("uTexture", 0);
        i2.a("uHalfTexelSize", 0.5 / (double)e2.c, 0.5 / (double)e2.d);
        i2.a("uOffset", d2);
        FullscreenQuadRenderer.b(poseStack);
    }
}

