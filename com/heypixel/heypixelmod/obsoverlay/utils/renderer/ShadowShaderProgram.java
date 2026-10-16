/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils.renderer;

import com.heypixel.heypixelmod.obsoverlay.EixClientCore;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventRender2D;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventShader;
import com.heypixel.heypixelmod.obsoverlay.utils.ElapsedTimer;
import com.heypixel.heypixelmod.obsoverlay.utils.renderer.RenderTargetFramebuffer;
import com.heypixel.heypixelmod.obsoverlay.utils.renderer.RenderStateManager;
import com.heypixel.heypixelmod.obsoverlay.utils.renderer.FullscreenQuadRenderer;
import com.heypixel.heypixelmod.obsoverlay.utils.renderer.ShaderProgram;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;

public class ShadowShaderProgram {
    private static final ElapsedTimer a = new ElapsedTimer();
    private static RenderTargetFramebuffer b;
    private static RenderTargetFramebuffer c;
    private static RenderTargetFramebuffer d;
    private static ShaderProgram e;

    public static void a(EventRender2D eventRender2D, float f2) {
        Window window = Minecraft.m_91087_().m_91268_();
        if (e == null) {
            e = new ShaderProgram("shadow.vert", "shadow.frag");
            b = new RenderTargetFramebuffer();
            c = new RenderTargetFramebuffer();
            d = new RenderTargetFramebuffer();
        }
        boolean bl = false;
        if (a.a((double)(1000.0f / f2))) {
            bl = true;
            a.a();
        }
        if (bl) {
            d.d();
            b.d();
            c.d();
            d.a();
            RenderSystem.setShader(GameRenderer::m_172811_);
            com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().b().a(new EventShader(eventRender2D.stack(), com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.k));
            d.c();
        }
        RenderStateManager.k();
        e.a();
        e.a("u_Size", (double)window.m_85441_(), (double)window.m_85442_());
        FullscreenQuadRenderer.a(eventRender2D.stack());
        if (bl) {
            b.a();
            RenderStateManager.q(ShadowShaderProgram.d.a);
            e.a("u_Direction", 1.0, 0.0);
            FullscreenQuadRenderer.b(eventRender2D.stack());
            c.a();
            RenderStateManager.q(ShadowShaderProgram.b.a);
            e.a("u_Direction", 0.0, 1.0);
            FullscreenQuadRenderer.b(eventRender2D.stack());
            b.a();
            RenderStateManager.q(ShadowShaderProgram.c.a);
            e.a("u_Direction", 1.0, 0.0);
            FullscreenQuadRenderer.b(eventRender2D.stack());
            c.c();
        }
        RenderStateManager.q(ShadowShaderProgram.b.a);
        e.a("u_Direction", 0.0, 1.0);
        FullscreenQuadRenderer.b(eventRender2D.stack());
        RenderStateManager.l();
        FullscreenQuadRenderer.b();
    }
}

