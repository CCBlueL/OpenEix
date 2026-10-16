/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils;

import com.heypixel.heypixelmod.mixin.O.accessors.RenderTargetAccessor;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;

public class TargetOverlayRenderer {
    private static final Minecraft a = Minecraft.m_91087_();

    public static void a(boolean bl) {
        TargetOverlayRenderer.b();
        GL11.glClear((int)1024);
        GL11.glEnable((int)2960);
        GL11.glStencilFunc((int)519, (int)1, (int)65535);
        GL11.glStencilOp((int)7680, (int)7680, (int)7681);
        if (!bl) {
            RenderSystem.colorMask((boolean)false, (boolean)false, (boolean)false, (boolean)false);
        }
    }

    public static void b(boolean bl) {
        RenderSystem.colorMask((boolean)true, (boolean)true, (boolean)true, (boolean)true);
        GL11.glStencilFunc((int)(bl ? 514 : 517), (int)1, (int)65535);
        GL11.glStencilOp((int)7680, (int)7680, (int)7681);
    }

    public static void a() {
        GL11.glDisable((int)2960);
    }

    public static void b() {
        if (a.m_91385_().m_83980_() > -1) {
            TargetOverlayRenderer.a(a.m_91385_());
            ((RenderTargetAccessor)a.m_91385_()).setDepthBufferId(-1);
        }
    }

    public static void a(RenderTarget renderTarget) {
        EXTFramebufferObject.glDeleteRenderbuffersEXT((int)renderTarget.m_83980_());
        int n2 = EXTFramebufferObject.glGenRenderbuffersEXT();
        EXTFramebufferObject.glBindRenderbufferEXT((int)36161, (int)n2);
        EXTFramebufferObject.glRenderbufferStorageEXT((int)36161, (int)34041, (int)a.m_91268_().m_85441_(), (int)a.m_91268_().m_85442_());
        EXTFramebufferObject.glFramebufferRenderbufferEXT((int)36160, (int)36128, (int)36161, (int)n2);
        EXTFramebufferObject.glFramebufferRenderbufferEXT((int)36160, (int)36096, (int)36161, (int)n2);
    }
}

