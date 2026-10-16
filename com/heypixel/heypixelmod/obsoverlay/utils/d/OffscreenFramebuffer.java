/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils.d;

import com.heypixel.heypixelmod.obsoverlay.utils.d.BufferUploaderState;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;

public class OffscreenFramebuffer {
    public int a;
    public double b = 1.0;
    public int c;
    public int d;
    private int e;

    public OffscreenFramebuffer(double d2) {
        this.b = d2;
        this.e();
    }

    public OffscreenFramebuffer() {
        this.e();
    }

    private void e() {
        Window window = Minecraft.m_91087_().m_91268_();
        this.e = GlStateManager.glGenFramebuffers();
        this.a();
        this.a = GlStateManager._genTexture();
        com.heypixel.heypixelmod.obsoverlay.utils.RemotePlayer.d.e(this.a);
        com.heypixel.heypixelmod.obsoverlay.utils.RemotePlayer.d.a();
        com.heypixel.heypixelmod.obsoverlay.utils.RemotePlayer.d.b(3553, 10242, 33071);
        com.heypixel.heypixelmod.obsoverlay.utils.RemotePlayer.d.b(3553, 10243, 33071);
        com.heypixel.heypixelmod.obsoverlay.utils.RemotePlayer.d.b(3553, 10241, 9729);
        com.heypixel.heypixelmod.obsoverlay.utils.RemotePlayer.d.b(3553, 10240, 9729);
        this.c = (int)((double)window.m_85441_() * this.b);
        this.d = (int)((double)window.m_85442_() * this.b);
        com.heypixel.heypixelmod.obsoverlay.utils.RemotePlayer.d.a(3553, 0, 6407, this.c, this.d, 0, 6407, 5121, null);
        com.heypixel.heypixelmod.obsoverlay.utils.RemotePlayer.d.a(36160, 36064, 3553, this.a, 0);
        this.c();
    }

    public void a() {
        GlStateManager._glBindFramebuffer((int)36160, (int)this.e);
    }

    public void b() {
        com.heypixel.heypixelmod.obsoverlay.utils.RemotePlayer.d.a(0, 0, this.c, this.d);
    }

    public void c() {
        Minecraft.m_91087_().m_91385_().m_83947_(false);
    }

    public void d() {
        GlStateManager._glDeleteFramebuffers((int)this.e);
        GlStateManager._deleteTexture((int)this.a);
        this.e();
    }
}

