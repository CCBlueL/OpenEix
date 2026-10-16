/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils.renderer;

import com.heypixel.heypixelmod.obsoverlay.utils.renderer.RenderStateManager;
import net.minecraft.client.Minecraft;

public class RenderTargetFramebuffer {
    public int a;
    public double b = 1.0;
    public int c;
    public int d;
    private int e;

    public RenderTargetFramebuffer(double d2) {
        this.b = d2;
        this.e();
    }

    public RenderTargetFramebuffer() {
        this.e();
    }

    private void e() {
        this.e = RenderStateManager.d();
        this.a();
        this.a = RenderStateManager.c();
        RenderStateManager.q(this.a);
        RenderStateManager.h();
        RenderStateManager.c(3553, 10242, 33071);
        RenderStateManager.c(3553, 10243, 33071);
        RenderStateManager.c(3553, 10241, 9729);
        RenderStateManager.c(3553, 10240, 9729);
        Minecraft minecraft = Minecraft.m_91087_();
        this.c = (int)((double)minecraft.m_91268_().m_85441_() * this.b);
        this.d = (int)((double)minecraft.m_91268_().m_85442_() * this.b);
        RenderStateManager.a(3553, 0, 6408, this.c, this.d, 0, 6408, 5121, null);
        RenderStateManager.a(36160, 36064, 3553, this.a, 0);
        this.c();
    }

    public void a() {
        RenderStateManager.j(this.e);
    }

    public void b() {
        RenderStateManager.a(0, 0, this.c, this.d);
    }

    public void c() {
        Minecraft.m_91087_().m_91385_().m_83947_(false);
    }

    public void d() {
        RenderStateManager.e(this.e);
        RenderStateManager.d(this.a);
        this.e();
    }
}

