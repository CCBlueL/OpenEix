/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils.renderer;

import com.heypixel.heypixelmod.obsoverlay.utils.renderer.GuiQuadRenderer;
import com.heypixel.heypixelmod.obsoverlay.utils.renderer.RenderStateManager;
import com.heypixel.heypixelmod.obsoverlay.utils.renderer.ShaderProgram;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.awt.Color;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryUtil;

public class MeshBuilder {
    private static final Minecraft c = Minecraft.m_91087_();
    private final GuiQuadRenderer d;
    private final int e;
    private final int f;
    private final int g;
    private final int h;
    public boolean a = false;
    public double b = 1.0;
    private ByteBuffer i;
    private long j;
    private int k;
    private ByteBuffer l;
    private long m;
    private int n;
    private int o;
    private boolean p;
    private boolean q;
    private double r;
    private double s;
    private boolean t;

    public MeshBuilder(GuiQuadRenderer c2, VertexComponentCount ... aArray) {
        int n2;
        int n3 = 0;
        VertexComponentCount[] aArray2 = aArray;
        int n4 = aArray2.length;
        for (n2 = 0; n2 < n4; ++n2) {
            VertexComponentCount a2 = aArray2[n2];
            n3 += a2.e * 4;
        }
        this.d = c2;
        this.e = n3 * c2.c;
        this.i = BufferUtils.createByteBuffer((int)(this.e * 256 * 4));
        this.j = MemoryUtil.memAddress0((Buffer)this.i);
        this.l = BufferUtils.createByteBuffer((int)(c2.c * 512 * 4));
        this.m = MemoryUtil.memAddress0((Buffer)this.l);
        this.f = com.heypixel.heypixelmod.obsoverlay.utils.renderer.RenderStateManager.a();
        com.heypixel.heypixelmod.obsoverlay.utils.renderer.RenderStateManager.g(this.f);
        this.g = com.heypixel.heypixelmod.obsoverlay.utils.renderer.RenderStateManager.b();
        com.heypixel.heypixelmod.obsoverlay.utils.renderer.RenderStateManager.h(this.g);
        this.h = com.heypixel.heypixelmod.obsoverlay.utils.renderer.RenderStateManager.b();
        com.heypixel.heypixelmod.obsoverlay.utils.renderer.RenderStateManager.i(this.h);
        int n5 = 0;
        for (n4 = 0; n4 < aArray.length; ++n4) {
            n2 = aArray[n4].e;
            com.heypixel.heypixelmod.obsoverlay.utils.renderer.RenderStateManager.k(n4);
            com.heypixel.heypixelmod.obsoverlay.utils.renderer.RenderStateManager.a(n4, n2, 5126, false, n3, n5);
            n5 += n2 * 4;
        }
        com.heypixel.heypixelmod.obsoverlay.utils.renderer.RenderStateManager.g(0);
        com.heypixel.heypixelmod.obsoverlay.utils.renderer.RenderStateManager.h(0);
        com.heypixel.heypixelmod.obsoverlay.utils.renderer.RenderStateManager.i(0);
    }

    public void a() {
        if (this.p) {
            throw new IllegalStateException("Mesh.end() called while already building.");
        }
        this.k = 0;
        this.n = 0;
        this.o = 0;
        this.p = true;
        this.r = 0.0;
        this.s = 0.0;
    }

    public MeshBuilder a(double d2, double d3, double d4) {
        long l2 = this.j + (long)this.k * 4L;
        MemoryUtil.memPutFloat((long)l2, (float)((float)(d2 - this.r)));
        MemoryUtil.memPutFloat((long)(l2 + 4L), (float)((float)d3));
        MemoryUtil.memPutFloat((long)(l2 + 8L), (float)((float)(d4 - this.s)));
        this.k += 3;
        return this;
    }

    public MeshBuilder a(double d2, double d3) {
        long l2 = this.j + (long)this.k * 4L;
        MemoryUtil.memPutFloat((long)l2, (float)((float)d2));
        MemoryUtil.memPutFloat((long)(l2 + 4L), (float)((float)d3));
        this.k += 2;
        return this;
    }

    public MeshBuilder a(Color color) {
        long l2 = this.j + (long)this.k * 4L;
        MemoryUtil.memPutFloat((long)l2, (float)((float)color.getRed() / 255.0f));
        MemoryUtil.memPutFloat((long)(l2 + 4L), (float)((float)color.getGreen() / 255.0f));
        MemoryUtil.memPutFloat((long)(l2 + 8L), (float)((float)color.getBlue() / 255.0f));
        MemoryUtil.memPutFloat((long)(l2 + 12L), (float)((float)color.getAlpha() / 255.0f * (float)this.b));
        this.k += 4;
        return this;
    }

    public int b() {
        return this.n++;
    }

    public void a(int n2, int n3) {
        long l2 = this.m + (long)this.o * 4L;
        MemoryUtil.memPutInt((long)l2, (int)n2);
        MemoryUtil.memPutInt((long)(l2 + 4L), (int)n3);
        this.o += 2;
        this.c();
    }

    public void a(int n2, int n3, int n4, int n5) {
        long l2 = this.m + (long)this.o * 4L;
        MemoryUtil.memPutInt((long)l2, (int)n2);
        MemoryUtil.memPutInt((long)(l2 + 4L), (int)n3);
        MemoryUtil.memPutInt((long)(l2 + 8L), (int)n4);
        MemoryUtil.memPutInt((long)(l2 + 12L), (int)n4);
        MemoryUtil.memPutInt((long)(l2 + 16L), (int)n5);
        MemoryUtil.memPutInt((long)(l2 + 20L), (int)n2);
        this.o += 6;
        this.c();
    }

    public void c() {
        ByteBuffer byteBuffer;
        int n2;
        if ((this.n + 1) * this.e >= this.i.capacity()) {
            n2 = this.i.capacity() * 2;
            if (n2 % this.e != 0) {
                n2 += n2 % this.e;
            }
            byteBuffer = BufferUtils.createByteBuffer((int)n2);
            MemoryUtil.memCopy((long)MemoryUtil.memAddress0((Buffer)this.i), (long)MemoryUtil.memAddress0((Buffer)byteBuffer), (long)((long)this.k * 4L));
            this.i = byteBuffer;
            this.j = MemoryUtil.memAddress0((Buffer)this.i);
        }
        if (this.o * 4 >= this.l.capacity()) {
            n2 = this.l.capacity() * 2;
            if (n2 % this.d.c != 0) {
                n2 += n2 % (this.d.c * 4);
            }
            byteBuffer = BufferUtils.createByteBuffer((int)n2);
            MemoryUtil.memCopy((long)MemoryUtil.memAddress0((Buffer)this.l), (long)MemoryUtil.memAddress0((Buffer)byteBuffer), (long)((long)this.o * 4L));
            this.l = byteBuffer;
            this.m = MemoryUtil.memAddress0((Buffer)this.l);
        }
    }

    public void d() {
        if (!this.p) {
            throw new IllegalStateException("Mesh.end() called while not building.");
        }
        if (this.o > 0) {
            com.heypixel.heypixelmod.obsoverlay.utils.renderer.RenderStateManager.h(this.g);
            com.heypixel.heypixelmod.obsoverlay.utils.renderer.RenderStateManager.a(34962, this.i.limit(this.k * 4), 35048);
            com.heypixel.heypixelmod.obsoverlay.utils.renderer.RenderStateManager.h(0);
            com.heypixel.heypixelmod.obsoverlay.utils.renderer.RenderStateManager.i(this.h);
            com.heypixel.heypixelmod.obsoverlay.utils.renderer.RenderStateManager.a(34963, this.l.limit(this.o * 4), 35048);
            com.heypixel.heypixelmod.obsoverlay.utils.renderer.RenderStateManager.i(0);
        }
        this.p = false;
    }

    public void a(PoseStack poseStack) {
        if (this.a) {
            com.heypixel.heypixelmod.obsoverlay.utils.renderer.RenderStateManager.i();
        } else {
            com.heypixel.heypixelmod.obsoverlay.utils.renderer.RenderStateManager.j();
        }
        com.heypixel.heypixelmod.obsoverlay.utils.renderer.RenderStateManager.k();
        com.heypixel.heypixelmod.obsoverlay.utils.renderer.RenderStateManager.n();
        com.heypixel.heypixelmod.obsoverlay.utils.renderer.RenderStateManager.q();
        if (this.q) {
            PoseStack poseStack2 = RenderSystem.getModelViewStack();
            poseStack2.m_85836_();
            if (poseStack != null) {
                poseStack2.m_252931_(poseStack.m_85850_().m_252922_());
            }
            Vec3 vec3 = com.heypixel.heypixelmod.obsoverlay.utils.renderer.MeshBuilder.c.f_91063_.m_109153_().m_90583_();
            poseStack2.m_85837_(0.0, -vec3.f_82480_, 0.0);
        }
        this.t = true;
    }

    public void b(PoseStack poseStack) {
        if (this.p) {
            this.d();
        }
        if (this.o > 0) {
            boolean bl = this.t;
            if (!bl) {
                this.a(poseStack);
            }
            this.f();
            com.heypixel.heypixelmod.obsoverlay.utils.renderer.ShaderProgram.a.b();
            com.heypixel.heypixelmod.obsoverlay.utils.renderer.RenderStateManager.g(this.f);
            com.heypixel.heypixelmod.obsoverlay.utils.renderer.RenderStateManager.a(this.d.a(), this.o, 5125);
            com.heypixel.heypixelmod.obsoverlay.utils.renderer.RenderStateManager.g(0);
            if (!bl) {
                this.e();
            }
        }
    }

    public void e() {
        if (this.q) {
            RenderSystem.getModelViewStack().m_85849_();
        }
        if (this.a) {
            com.heypixel.heypixelmod.obsoverlay.utils.renderer.RenderStateManager.l();
        } else {
            com.heypixel.heypixelmod.obsoverlay.utils.renderer.RenderStateManager.k();
        }
        com.heypixel.heypixelmod.obsoverlay.utils.renderer.RenderStateManager.l();
        com.heypixel.heypixelmod.obsoverlay.utils.renderer.RenderStateManager.m();
        com.heypixel.heypixelmod.obsoverlay.utils.renderer.RenderStateManager.r();
        this.t = false;
    }

    protected void f() {
    }

    public static enum VertexComponentCount {
        a(1),
        b(2),
        c(3),
        d(4);

        public final int e;

        private VertexComponentCount(int n3) {
            this.e = n3;
        }
    }
}

