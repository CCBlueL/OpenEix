/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils.d;

import com.heypixel.heypixelmod.obsoverlay.utils.d.ShaderCompiler;
import com.heypixel.heypixelmod.obsoverlay.utils.d.BufferUploaderState;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.PoseStack;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryUtil;

public class QuadMeshRenderer {
    private static final IndexedQuadBuffer a = new IndexedQuadBuffer();
    private static final PoseStack b = new PoseStack();

    public static void a() {
        a.a(b);
    }

    public static void b() {
        a.b(b);
    }

    public static void c() {
        a.d();
    }

    static {
        a.a();
        a.a(a.a(-1.0, -1.0).b(), a.a(-1.0, 1.0).b(), a.a(1.0, 1.0).b(), a.a(1.0, -1.0).b());
        a.c();
    }

    private static class IndexedQuadBuffer {
        private final int a;
        private final int b;
        private final int c;
        private final int d;
        private final ByteBuffer e;
        private final long f;
        private final ByteBuffer g;
        private final long h;
        private long i;
        private int j;
        private int k;
        private boolean l;
        private boolean m;

        public IndexedQuadBuffer() {
            int n2 = 3;
            int n3 = 8;
            this.a = n3 * n2;
            this.e = BufferUtils.createByteBuffer((int)(this.a * 256 * 4));
            this.f = MemoryUtil.memAddress0((Buffer)this.e);
            this.g = BufferUtils.createByteBuffer((int)(n2 * 512 * 4));
            this.h = MemoryUtil.memAddress0((Buffer)this.g);
            this.b = GlStateManager._glGenVertexArrays();
            com.heypixel.heypixelmod.obsoverlay.utils.RemotePlayer.d.a(this.b);
            this.c = GlStateManager._glGenBuffers();
            GlStateManager._glBindBuffer((int)34962, (int)this.c);
            this.d = GlStateManager._glGenBuffers();
            com.heypixel.heypixelmod.obsoverlay.utils.RemotePlayer.d.b(this.d);
            GlStateManager._enableVertexAttribArray((int)0);
            GlStateManager._vertexAttribPointer((int)0, (int)2, (int)5126, (boolean)false, (int)n3, (long)0L);
            com.heypixel.heypixelmod.obsoverlay.utils.RemotePlayer.d.a(0);
            GlStateManager._glBindBuffer((int)34962, (int)0);
            com.heypixel.heypixelmod.obsoverlay.utils.RemotePlayer.d.b(0);
        }

        public void a() {
            if (this.l) {
                return;
            }
            this.i = this.f;
            this.j = 0;
            this.k = 0;
            this.l = true;
        }

        public IndexedQuadBuffer a(double d2, double d3) {
            long l2 = this.i;
            MemoryUtil.memPutFloat((long)l2, (float)((float)d2));
            MemoryUtil.memPutFloat((long)(l2 + 4L), (float)((float)d3));
            this.i += 8L;
            return this;
        }

        public int b() {
            return this.j++;
        }

        public void a(int n2, int n3, int n4, int n5) {
            long l2 = this.h + (long)this.k * 4L;
            MemoryUtil.memPutInt((long)l2, (int)n2);
            MemoryUtil.memPutInt((long)(l2 + 4L), (int)n3);
            MemoryUtil.memPutInt((long)(l2 + 8L), (int)n4);
            MemoryUtil.memPutInt((long)(l2 + 12L), (int)n4);
            MemoryUtil.memPutInt((long)(l2 + 16L), (int)n5);
            MemoryUtil.memPutInt((long)(l2 + 20L), (int)n2);
            this.k += 6;
        }

        public void c() {
            if (!this.l) {
                return;
            }
            if (this.k > 0) {
                GlStateManager._glBindBuffer((int)34962, (int)this.c);
                GlStateManager._glBufferData((int)34962, (ByteBuffer)this.e.limit(this.e()), (int)35048);
                GlStateManager._glBindBuffer((int)34962, (int)0);
                com.heypixel.heypixelmod.obsoverlay.utils.RemotePlayer.d.b(this.d);
                GlStateManager._glBufferData((int)34963, (ByteBuffer)this.g.limit(this.k * 4), (int)35048);
                com.heypixel.heypixelmod.obsoverlay.utils.RemotePlayer.d.b(0);
            }
            this.l = false;
        }

        public void a(PoseStack poseStack) {
            com.heypixel.heypixelmod.obsoverlay.utils.RemotePlayer.d.g();
            this.m = true;
        }

        public void b(PoseStack poseStack) {
            if (this.l) {
                this.c();
            }
            if (this.k > 0) {
                boolean bl = this.m;
                if (!bl) {
                    this.a(poseStack);
                }
                com.heypixel.heypixelmod.obsoverlay.utils.RemotePlayer.c.a.b();
                com.heypixel.heypixelmod.obsoverlay.utils.RemotePlayer.d.a(this.b);
                GlStateManager._drawElements((int)4, (int)this.k, (int)5125, (long)0L);
                com.heypixel.heypixelmod.obsoverlay.utils.RemotePlayer.d.a(0);
                if (!bl) {
                    this.d();
                }
            }
        }

        public void d() {
            this.m = false;
        }

        private int e() {
            return (int)(this.i - this.f);
        }
    }
}

