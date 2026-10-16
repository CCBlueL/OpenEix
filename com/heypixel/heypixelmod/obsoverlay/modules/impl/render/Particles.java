/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.render;

import com.heypixel.heypixelmod.obsoverlay.d.a.NumericSetting;
import com.heypixel.heypixelmod.obsoverlay.d.a.ModeSetting;
import com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.WorldRenderEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.HUD;
import com.heypixel.heypixelmod.obsoverlay.utils.EntityOverlayRenderer;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Axis;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

@ModuleInfo(a="Particles", b="\u7c92\u5b50", c="Renders world particles", d=com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory.RENDER)
public class Particles
extends com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase {
    private static final ResourceLocation c = ResourceLocation.fromNamespaceAndPath((String)"heypixel", (String)"vcx6svvqmet8/particles/firefly.png");
    private static final ResourceLocation d = ResourceLocation.fromNamespaceAndPath((String)"heypixel", (String)"vcx6svvqmet8/particles/snowflake.png");
    private static final ResourceLocation e = ResourceLocation.fromNamespaceAndPath((String)"heypixel", (String)"vcx6svvqmet8/particles/star.png");
    private static final ResourceLocation f = ResourceLocation.fromNamespaceAndPath((String)"heypixel", (String)"vcx6svvqmet8/particles/heart.png");
    private static final ResourceLocation g = ResourceLocation.fromNamespaceAndPath((String)"heypixel", (String)"vcx6svvqmet8/particles/dollar.png");
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a h = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "FireFlies").a(true).a().b();
    private final AntiNausea i = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "FFCount").a(30.0f).b(20.0f).c(200.0f).d(1.0f).a(() -> this.h.m()).a().c();
    private final AntiNausea j = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "FFSize").a(1.0f).b(0.1f).c(2.0f).d(0.05f).a(() -> this.h.m()).a().c();
    private final BedESP k = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Mode").a("Off", "SnowFlake", "Stars", "Hearts", "Dollars", "Bloom").a(1).a().e();
    private final AntiNausea l = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Count").a(100.0f).b(20.0f).c(800.0f).d(1.0f).a(() -> !this.k.a("Off")).a().c();
    private final AntiNausea m = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Size").a(1.0f).b(0.1f).c(6.0f).d(0.1f).a(() -> !this.k.a("Off")).a().c();
    private final BedESP n = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Physics").a("Drop", "Fly").a(1).a(() -> !this.k.a("Off")).a().e();
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a o = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Sync Color").a(true).a().b();
    private final AntiNausea p = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Color Speed").a(10.0f).b(2.0f).c(30.0f).d(1.0f).a(() -> this.o.m()).a().c();
    private final Random q = new Random();
    private final ArrayList<Particle> r = new ArrayList();
    private final ArrayList<Particle> s = new ArrayList();

    @Override
    public void d() {
        this.r.clear();
        this.s.clear();
    }

    @Override
    public void e() {
        this.r.clear();
        this.s.clear();
    }

    @EventTarget
    public void onUpdate(com.heypixel.heypixelmod.obsoverlay.events.impl.LocalPlayerTickPreEvent ad2) {
        if (Particles.a.f_91074_ == null || Particles.a.f_91073_ == null) {
            return;
        }
        this.r.removeIf(b::a);
        this.s.removeIf(b::a);
        this.B();
        this.C();
    }

    @EventTarget
    public void onRender3D(WorldRenderEvent p2) {
        ResourceLocation resourceLocation;
        if (Particles.a.f_91074_ == null || Particles.a.f_91073_ == null) {
            return;
        }
        PoseStack poseStack = p2.b();
        float f2 = p2.a();
        if (this.h.m() && !this.r.isEmpty()) {
            this.a(poseStack, f2, c, this.r, this.j.q(), true);
        }
        if (!this.k.a("Off") && !this.s.isEmpty() && (resourceLocation = this.A()) != null) {
            this.a(poseStack, f2, resourceLocation, this.s, this.m.q(), false);
        }
    }

    private void a(PoseStack poseStack, float f2, ResourceLocation resourceLocation, List<Particle> list, float f3, boolean bl) {
        poseStack.m_85836_();
        RenderSystem.enableBlend();
        RenderSystem.disableCull();
        RenderSystem.enableDepthTest();
        RenderSystem.depthMask((boolean)false);
        RenderSystem.setShader(GameRenderer::m_172820_);
        RenderSystem.setShaderTexture((int)0, (ResourceLocation)resourceLocation);
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderSystem.blendFunc((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE);
        BufferBuilder bufferBuilder = Tesselator.m_85913_().m_85915_();
        bufferBuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85819_);
        for (Particle b2 : list) {
            b2.a(poseStack, bufferBuilder, f2, f3, this.c(b2.k * (bl ? 10 : 2)), bl);
        }
        BufferUploader.m_231202_((BufferBuilder.RenderedBuffer)bufferBuilder.m_231175_());
        RenderSystem.depthMask((boolean)true);
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        poseStack.m_85849_();
    }

    private ResourceLocation A() {
        if (this.k.a("Bloom")) {
            return c;
        }
        if (this.k.a("SnowFlake")) {
            return d;
        }
        if (this.k.a("Stars")) {
            return e;
        }
        if (this.k.a("Hearts")) {
            return f;
        }
        if (this.k.a("Dollars")) {
            return g;
        }
        return null;
    }

    private int c(int n2) {
        if (this.o.m()) {
            return com.heypixel.heypixelmod.obsoverlay.utils.renderer.RenderTargetManager.a((int)this.p.q(), -n2, com.heypixel.heypixelmod.obsoverlay.modules.impl.render.HUD.p(), com.heypixel.heypixelmod.obsoverlay.modules.impl.render.HUD.q(), false).getRGB();
        }
        return -1;
    }

    private void B() {
        if (!this.h.m()) {
            return;
        }
        int n2 = (int)this.i.q();
        for (int i2 = this.r.size(); i2 < n2; ++i2) {
            this.r.add(new TrailParticle((float)(Particles.a.f_91074_.m_20185_() + (double)this.a(-25.0f, 25.0f)), (float)(Particles.a.f_91074_.m_20186_() + (double)this.a(2.0f, 15.0f)), (float)(Particles.a.f_91074_.m_20189_() + (double)this.a(-25.0f, 25.0f)), this.a(-0.2f, 0.2f), this.a(-0.1f, 0.1f), this.a(-0.2f, 0.2f)));
        }
    }

    private void C() {
        if (this.k.a("Off")) {
            return;
        }
        int n2 = (int)this.l.q();
        boolean bl = this.n.a("Drop");
        for (int i2 = this.s.size(); i2 < n2; ++i2) {
            this.s.add(new Particle((float)(Particles.a.f_91074_.m_20185_() + (double)this.a(-48.0f, 48.0f)), (float)(Particles.a.f_91074_.m_20186_() + (double)this.a(2.0f, 48.0f)), (float)(Particles.a.f_91074_.m_20189_() + (double)this.a(-48.0f, 48.0f)), bl ? 0.0f : this.a(-0.4f, 0.4f), bl ? this.a(-0.2f, -0.05f) : this.a(-0.1f, 0.1f), bl ? 0.0f : this.a(-0.4f, 0.4f)));
        }
    }

    private float a(float f2, float f3) {
        return f2 + (f3 - f2) * this.q.nextFloat();
    }

    private static int a(int n2, int n3) {
        n3 = Mth.m_14045_((int)n3, (int)0, (int)255);
        return n3 << 24 | n2 & 0xFFFFFF;
    }

    private class Particle {
        protected float b;
        protected float c;
        protected float d;
        protected float e;
        protected float f;
        protected float g;
        protected float h;
        protected float i;
        protected float j;
        protected int k;
        protected int l;

        private Particle(float f2, float f3, float f4, float f5, float f6, float f7) {
            this.e = f2;
            this.f = f3;
            this.g = f4;
            this.b = f2;
            this.c = f3;
            this.d = f4;
            this.h = f5;
            this.i = f6;
            this.j = f7;
            this.l = this.k = (int)Particles.this.a(100.0f, 300.0f);
        }

        public boolean a() {
            double d2;
            double d3;
            if (a.f_91074_ == null) {
                return true;
            }
            double d4 = a.f_91074_.m_20185_() - (double)this.e;
            double d5 = d4 * d4 + (d3 = a.f_91074_.m_20186_() - (double)this.f) * d3 + (d2 = a.f_91074_.m_20189_() - (double)this.g) * d2;
            this.k = d5 > 4096.0 ? (this.k -= 8) : --this.k;
            if (this.k < 0) {
                return true;
            }
            this.b = this.e;
            this.c = this.f;
            this.d = this.g;
            this.e += this.h;
            this.f += this.i;
            this.g += this.j;
            this.h *= 0.9f;
            if (Particles.this.n.a("Fly")) {
                this.i *= 0.9f;
            }
            this.j *= 0.9f;
            this.i -= 0.001f;
            return false;
        }

        public void a(PoseStack poseStack, BufferBuilder bufferBuilder, float f2, float f3, int n2, boolean bl) {
            float f4 = Mth.m_14179_((float)f2, (float)this.b, (float)this.e);
            float f5 = Mth.m_14179_((float)f2, (float)this.c, (float)this.f);
            float f6 = Mth.m_14179_((float)f2, (float)this.d, (float)this.g);
            int n3 = (int)(255.0f * ((float)this.k / (float)this.l));
            this.a(poseStack, bufferBuilder, f2, f4, f5, f6, f3, Particles.a(n2, n3));
        }

        protected void a(PoseStack poseStack, BufferBuilder bufferBuilder, float f2, float f3, float f4, float f5, float f6, int n2) {
            Vec3 vec3 = EntityOverlayRenderer.b();
            Camera camera = a.f_91063_.m_109153_();
            poseStack.m_85836_();
            poseStack.m_85837_((double)f3 - vec3.f_82479_, (double)f4 - vec3.f_82480_, (double)f5 - vec3.f_82481_);
            poseStack.m_252781_(Axis.f_252436_.m_252977_(-camera.m_90590_()));
            poseStack.m_252781_(Axis.f_252529_.m_252977_(camera.m_90589_()));
            float f7 = (float)(n2 >> 24 & 0xFF) / 255.0f;
            float f8 = (float)(n2 >> 16 & 0xFF) / 255.0f;
            float f9 = (float)(n2 >> 8 & 0xFF) / 255.0f;
            float f10 = (float)(n2 & 0xFF) / 255.0f;
            Matrix4f matrix4f = poseStack.m_85850_().m_252922_();
            bufferBuilder.m_252986_(matrix4f, -f6, -f6, 0.0f).m_7421_(0.0f, 1.0f).m_85950_(f8, f9, f10, f7).m_5752_();
            bufferBuilder.m_252986_(matrix4f, f6, -f6, 0.0f).m_7421_(1.0f, 1.0f).m_85950_(f8, f9, f10, f7).m_5752_();
            bufferBuilder.m_252986_(matrix4f, f6, f6, 0.0f).m_7421_(1.0f, 0.0f).m_85950_(f8, f9, f10, f7).m_5752_();
            bufferBuilder.m_252986_(matrix4f, -f6, f6, 0.0f).m_7421_(0.0f, 0.0f).m_85950_(f8, f9, f10, f7).m_5752_();
            poseStack.m_85849_();
        }
    }

    private class TrailParticle
    extends Particle {
        private final ArrayList<TrailPoint> n;

        private TrailParticle(float f2, float f3, float f4, float f5, float f6, float f7) {
            super(f2, f3, f4, f5, f6, f7);
            this.n = new ArrayList();
        }

        @Override
        public boolean a() {
            double d2;
            double d3;
            if (a.f_91074_ == null || a.f_91073_ == null) {
                return true;
            }
            double d4 = a.f_91074_.m_20185_() - (double)this.e;
            double d5 = d4 * d4 + (d3 = a.f_91074_.m_20186_() - (double)this.f) * d3 + (d2 = a.f_91074_.m_20189_() - (double)this.g) * d2;
            this.k = d5 > 100.0 ? (this.k -= 4) : (!a.f_91073_.m_8055_(BlockPos.m_274561_((double)this.e, (double)this.f, (double)this.g)).m_60795_() ? (this.k -= 8) : --this.k);
            if (this.k < 0) {
                return true;
            }
            this.b = this.e;
            this.c = this.f;
            this.d = this.g;
            this.e += this.h;
            this.f += this.i;
            this.g += this.j;
            this.n.add(new TrailPoint(this.e, this.f, this.g, this.k));
            if (this.n.size() > 20) {
                this.n.remove(0);
            }
            this.h *= 0.99f;
            this.i *= 0.99f;
            this.j *= 0.99f;
            return false;
        }

        @Override
        public void a(PoseStack poseStack, BufferBuilder bufferBuilder, float f2, float f3, int n2, boolean bl) {
            int n3 = this.n.size();
            if (n3 > 1) {
                for (int i2 = 0; i2 < n3; ++i2) {
                    TrailPoint trailPoint = this.n.get(i2);
                    float f4 = (float)i2 / (float)n3;
                    int n4 = (int)(255.0f * ((float)this.k / (float)this.l) * f4);
                    this.a(poseStack, bufferBuilder, f2, trailPoint.x, trailPoint.y, trailPoint.z, f3, Particles.a(n2, n4));
                }
            }
            super.a(poseStack, bufferBuilder, f2, f3, n2, true);
        }
    }

    private record TrailPoint(float x, float y, float z, int age) {
    }
}

