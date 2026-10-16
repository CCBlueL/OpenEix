/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.render;

import com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.WorldRenderEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.a.a.EntityColorRule;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.a.a.ArrowColorRule;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.a.a.PotionColorRule;
import com.heypixel.heypixelmod.obsoverlay.utils.EntityRayTraceUtils;
import com.heypixel.heypixelmod.obsoverlay.utils.EntityOverlayRenderer;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.EggItem;
import net.minecraft.world.item.EnderpearlItem;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.LingeringPotionItem;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.SnowballItem;
import net.minecraft.world.item.SplashPotionItem;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

@ModuleInfo(a="Projectiles", b="\u6295\u63b7\u7269\u843d\u70b9\u663e\u793a", c="Renders projectiles", d=com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory.RENDER)
public class Projectiles
extends com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase {
    private final AntiBlindness h = new AntiBlindness();
    private final AntiNausea i = new AntiNausea();
    private final Animations j = new Animations(Collections.singleton(ThrownEnderpearl.class), new Color(173, 12, 255));
    private final Animations k = new Animations(Collections.singleton(ThrownEgg.class), new Color(255, 238, 154));
    private final Animations l = new Animations(Collections.singleton(Snowball.class), new Color(255, 255, 255));
    public com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a c = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Show Arrows").a(true).a().b();
    public com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a d = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Show Pearls").a(true).a().b();
    public com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a e = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Show Potions").a(false).a().b();
    public com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a f = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Show Eggs").a(false).a().b();
    public com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a g = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Show Snowballs").a(false).a().b();

    @EventTarget
    public void onRender3D(WorldRenderEvent p2) {
        for (Entity entity : Projectiles.a.f_91073_.m_104735_()) {
            com.heypixel.heypixelmod.obsoverlay.modules.impl.render.Animations.a a2;
            if (!(entity instanceof net.minecraft.world.entity.projectile.Projectile) || (a2 = this.a(entity)) == null) continue;
            PoseStack poseStack = p2.b();
            poseStack.m_85836_();
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glDisable((int)2929);
            GL11.glDepthMask((boolean)false);
            GL11.glEnable((int)2848);
            RenderSystem.setShader(GameRenderer::m_172808_);
            Color color = a2.a((Object)entity);
            RenderSystem.setShaderColor((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)1.0f);
            this.a(poseStack, entity, a2);
            RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glDisable((int)3042);
            GL11.glEnable((int)2929);
            GL11.glDepthMask((boolean)true);
            GL11.glDisable((int)2848);
            poseStack.m_85849_();
        }
    }

    @EventTarget
    private void onRender(WorldRenderEvent p2) {
        List<Vec3> list;
        Path path = this.a(p2.a());
        if (path != null && (list = path.path()).size() >= 2) {
            PoseStack poseStack = p2.b();
            poseStack.m_85836_();
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glDisable((int)2929);
            GL11.glDepthMask((boolean)false);
            GL11.glEnable((int)2848);
            RenderSystem.setShader(GameRenderer::m_172808_);
            Vec3 vec3 = list.get(0);
            this.a(poseStack, list, vec3);
            if (!list.isEmpty()) {
                Vec3 vec32 = list.get(list.size() - 1);
                this.a(poseStack, vec32, vec3, path.result, p2.a());
            }
            RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glDisable((int)3042);
            GL11.glEnable((int)2929);
            GL11.glDepthMask((boolean)true);
            GL11.glDisable((int)2848);
            poseStack.m_85849_();
        }
    }

    private void a(PoseStack poseStack, List<Vec3> list, Vec3 vec3) {
        Matrix4f matrix4f = poseStack.m_85850_().m_252922_();
        BufferBuilder bufferBuilder = Tesselator.m_85913_().m_85915_();
        RenderSystem.setShader(GameRenderer::m_172808_);
        bufferBuilder.m_166779_(VertexFormat.Mode.DEBUG_LINE_STRIP, DefaultVertexFormat.f_85814_);
        float[] fArray = new float[]{1.0f, 1.0f, 1.0f};
        RenderSystem.setShaderColor((float)fArray[0], (float)fArray[1], (float)fArray[2], (float)1.0f);
        for (Vec3 vec32 : list) {
            bufferBuilder.m_252986_(matrix4f, (float)(vec32.f_82479_ - vec3.f_82479_), (float)(vec32.f_82480_ - vec3.f_82480_), (float)(vec32.f_82481_ - vec3.f_82481_)).m_5752_();
        }
        BufferUploader.m_231202_((BufferBuilder.RenderedBuffer)bufferBuilder.m_231175_());
    }

    private void a(PoseStack poseStack, Vec3 vec3, Vec3 vec32, HitResult hitResult, float f2) {
        double d2;
        double d3;
        AABB aABB = new AABB(0.15, 0.15, 0.15, 0.35, 0.35, 0.35);
        float[] fArray = new float[]{1.0f, 1.0f, 1.0f};
        if (hitResult != null) {
            if (hitResult.m_6662_() == HitResult.Type.BLOCK) {
                var8_8 = (BlockHitResult)hitResult;
                Direction direction = var8_8.m_82434_();
                if (direction == Direction.SOUTH) {
                    aABB = new AABB(0.0, 0.0, 0.0, 0.5, 0.5, 0.1);
                } else if (direction == Direction.NORTH) {
                    aABB = new AABB(0.0, 0.0, 0.4, 0.5, 0.5, 0.5);
                } else if (direction == Direction.EAST) {
                    aABB = new AABB(0.0, 0.0, 0.0, 0.1, 0.5, 0.5);
                } else if (direction == Direction.WEST) {
                    aABB = new AABB(0.4, 0.0, 0.0, 0.5, 0.5, 0.5);
                } else if (direction == Direction.UP) {
                    fArray = new float[]{0.0f, 1.0f, 0.0f};
                    aABB = new AABB(0.0, 0.0, 0.0, 0.5, 0.1, 0.5);
                } else if (direction == Direction.DOWN) {
                    aABB = new AABB(0.0, 0.4, 0.0, 0.5, 0.5, 0.5);
                }
            } else if (hitResult.m_6662_() == HitResult.Type.ENTITY) {
                var8_8 = (EntityHitResult)hitResult;
                fArray = new float[]{1.0f, 0.0f, 0.0f};
                RenderSystem.setShaderColor((float)fArray[0], (float)fArray[1], (float)fArray[2], (float)0.5f);
                Entity entity = var8_8.m_82443_();
                d3 = entity.m_20185_() - entity.f_19854_;
                d2 = entity.m_20186_() - entity.f_19855_;
                double d4 = entity.m_20189_() - entity.f_19856_;
                Vec3 vec33 = EntityOverlayRenderer.b();
                AABB aABB2 = entity.m_20191_().m_82386_(-vec33.f_82479_, -vec33.f_82480_, -vec33.f_82481_).m_82386_(-d3, -d2, -d4).m_82386_((double)f2 * d3, (double)f2 * d2, (double)f2 * d4).m_82400_(0.1);
                EntityOverlayRenderer.a(aABB2, poseStack);
            }
        }
        double d5 = vec3.f_82479_ - vec32.f_82479_;
        d3 = vec3.f_82480_ - vec32.f_82480_;
        d2 = vec3.f_82481_ - vec32.f_82481_;
        poseStack.m_85836_();
        poseStack.m_85837_(d5 - 0.25, d3 - 0.25, d2 - 0.25);
        RenderSystem.setShaderColor((float)fArray[0], (float)fArray[1], (float)fArray[2], (float)0.25f);
        EntityOverlayRenderer.a(aABB, poseStack);
        RenderSystem.setShaderColor((float)fArray[0], (float)fArray[1], (float)fArray[2], (float)0.75f);
        EntityOverlayRenderer.b(aABB, poseStack);
        poseStack.m_85849_();
    }

    private Path a(float f2) {
        LocalPlayer localPlayer = Projectiles.a.f_91074_;
        ArrayList<Vec3> arrayList = new ArrayList<Vec3>();
        ItemStack itemStack = localPlayer.m_21205_();
        Item item = itemStack.m_41720_();
        if (!itemStack.m_41619_() && this.b(item)) {
            double d2;
            double d3;
            double d4;
            double d5 = localPlayer.f_19790_ + (localPlayer.m_20185_() - localPlayer.f_19790_) * (double)f2;
            double d6 = localPlayer.f_19791_ + (localPlayer.m_20186_() - localPlayer.f_19791_) * (double)f2 + (double)localPlayer.m_20192_() - 0.1;
            double d7 = localPlayer.f_19792_ + (localPlayer.m_20189_() - localPlayer.f_19792_) * (double)f2;
            double d8 = d4 = item instanceof ProjectileWeaponItem ? 1.0 : 0.4;
            if (com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.k()) {
                if (com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.e == null || com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.d == null) {
                    return new Path(arrayList, null);
                }
                d3 = Math.toRadians(Mth.m_14179_((float)f2, (float)com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.e.a(), (float)com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.d.a()));
                d2 = Math.toRadians(Mth.m_14179_((float)f2, (float)com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.e.b(), (float)com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.d.b()));
            } else {
                d3 = Math.toRadians(Mth.m_14179_((float)f2, (float)localPlayer.f_19859_, (float)localPlayer.m_146908_()));
                d2 = Math.toRadians(Mth.m_14179_((float)f2, (float)localPlayer.f_19860_, (float)localPlayer.m_146909_()));
            }
            double d9 = -Math.sin(d3) * Math.cos(d2) * d4;
            double d10 = -Math.sin(d2) * d4;
            double d11 = Math.cos(d3) * Math.cos(d2) * d4;
            double d12 = Math.sqrt(d9 * d9 + d10 * d10 + d11 * d11);
            d9 /= d12;
            d10 /= d12;
            d11 /= d12;
            if (item instanceof ProjectileWeaponItem) {
                float f3 = (float)(72000 - localPlayer.m_21212_()) / 20.0f;
                if ((f3 = (f3 * f3 + f3 * 2.0f) / 3.0f) > 1.0f || f3 <= 0.1f) {
                    f3 = 1.0f;
                }
                d9 *= (double)(f3 *= 3.0f);
                d10 *= (double)f3;
                d11 *= (double)f3;
            } else {
                d9 *= 1.5;
                d10 *= 1.5;
                d11 *= 1.5;
            }
            double d13 = this.a(item);
            for (int i2 = 0; i2 < 1000; ++i2) {
                Vec3 vec3 = new Vec3(d5, d6, d7);
                Vec3 vec32 = new Vec3(d5 + d9, d6 + d10, d7 + d11);
                arrayList.add(vec3);
                ClipContext clipContext = new ClipContext(vec3, vec32, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, (Entity)Projectiles.a.f_91074_);
                BlockHitResult blockHitResult = Projectiles.a.f_91073_.m_45547_(clipContext);
                if (blockHitResult.m_6662_() != HitResult.Type.MISS) {
                    return new Path(arrayList, (HitResult)blockHitResult);
                }
                Arrow arrow = new Arrow((Level)Projectiles.a.f_91073_, d5, d6, d7);
                EntityHitResult entityHitResult = ProjectileUtil.m_37304_((Level)Projectiles.a.f_91073_, (Entity)arrow, (Vec3)vec3, (Vec3)vec32, (AABB)arrow.m_20191_().m_82369_(new Vec3(d9, d10, d11)).m_82400_(1.0), arg_0 -> Projectiles.a((Player)localPlayer, arg_0));
                if (entityHitResult != null && entityHitResult.m_6662_() == HitResult.Type.ENTITY) {
                    return new Path(arrayList, (HitResult)entityHitResult);
                }
                d5 += d9;
                d6 += d10;
                d7 += d11;
                d9 *= 0.99;
                d10 *= 0.99;
                d11 *= 0.99;
                d10 -= d13;
            }
            return new Path(arrayList, null);
        }
        return null;
    }

    private double a(Item item) {
        if (item instanceof BowItem || item instanceof CrossbowItem) {
            return 0.05;
        }
        if (item instanceof PotionItem) {
            return 0.4;
        }
        if (item instanceof FishingRodItem) {
            return 0.15;
        }
        return item instanceof TridentItem ? 0.015 : 0.03;
    }

    private boolean b(Item item) {
        return item instanceof BowItem || item instanceof CrossbowItem || item instanceof SnowballItem || item instanceof EggItem || item instanceof EnderpearlItem || item instanceof SplashPotionItem || item instanceof LingeringPotionItem || item instanceof FishingRodItem || item instanceof TridentItem;
    }

    private void a(PoseStack poseStack, Entity entity, com.heypixel.heypixelmod.obsoverlay.modules.impl.render.Animations.a a2) {
        if (entity != null) {
            LocalPlayer localPlayer = Projectiles.a.f_91074_;
            ClientLevel clientLevel = Projectiles.a.f_91073_;
            Color color = a2.a((Object)entity);
            if (color == null) {
                color = new Color(255, 255, 255);
            }
            Tesselator tesselator = Tesselator.m_85913_();
            BufferBuilder bufferBuilder = tesselator.m_85915_();
            bufferBuilder.m_166779_(VertexFormat.Mode.DEBUG_LINE_STRIP, DefaultVertexFormat.f_85815_);
            double d2 = entity.m_20185_();
            double d3 = entity.m_20186_();
            double d4 = entity.m_20189_();
            double d5 = entity.m_20184_().f_82479_;
            double d6 = entity.m_20184_().f_82480_;
            double d7 = entity.m_20184_().f_82481_;
            this.a(color, bufferBuilder, poseStack, d2, d3, d4);
            while (true) {
                float f2 = a2.a();
                float f3 = a2.b();
                AABB aABB = new AABB(d2 - (double)f2, d3, d4 - (double)f2, d2 + (double)f2, d3 + (double)f3, d4 + (double)f2);
                Vec3 vec3 = new Vec3(d2, d3, d4);
                Vec3 vec32 = new Vec3(d2 + d5, d3 + d6, d4 + d7);
                HitResult hitResult = EntityRayTraceUtils.a(vec3, vec32, false, entity instanceof Arrow, false, entity);
                if (!hitResult.m_6662_().equals((Object)HitResult.Type.MISS)) {
                    vec32 = new Vec3(hitResult.m_82450_().m_7096_(), hitResult.m_82450_().m_7098_(), hitResult.m_82450_().m_7094_());
                }
                List list = clientLevel.m_45933_((Entity)localPlayer, aABB.m_82310_(d5, d6, d7).m_82363_(1.0, 1.0, 1.0));
                double d8 = 0.0;
                for (Entity entity2 : list) {
                    double d9;
                    EntityHitResult entityHitResult;
                    if (!(entity2 instanceof LivingEntity) || entity2 instanceof EnderMan || !entity2.m_5829_() || entity2.equals((Object)localPlayer) || (entityHitResult = EntityRayTraceUtils.a(aABB = entity2.m_20191_().m_82363_(0.3, 0.3, 0.3), vec3, vec32)) == null || !((d9 = vec3.m_82554_(entityHitResult.m_82450_())) < d8) && d8 != 0.0) continue;
                    d8 = d9;
                    hitResult = entityHitResult;
                }
                d2 += d5;
                d3 += d6;
                d4 += d7;
                if (!hitResult.m_6662_().equals((Object)HitResult.Type.MISS)) {
                    d2 = hitResult.m_82450_().m_7096_();
                    d3 = hitResult.m_82450_().m_7098_();
                    d4 = hitResult.m_82450_().m_7094_();
                    break;
                }
                if (d3 < -128.0) break;
                double d10 = d6 * (entity.m_20069_() ? 0.8 : 0.99);
                double d11 = entity.m_20069_() ? 0.8 : 0.99;
                d6 = d10 - (double)a2.c();
                this.a(color, bufferBuilder, poseStack, d2 + (d5 *= entity.m_20069_() ? 0.8 : 0.99), d3 + d6, d4 + (d7 *= d11));
            }
            tesselator.m_85914_();
        }
    }

    private void a(Color color, BufferBuilder bufferBuilder, PoseStack poseStack, double d2, double d3, double d4) {
        Entity entity = a.m_91288_();
        double d5 = entity.f_19790_ + (entity.m_20185_() - entity.f_19790_) * (double)a.m_91296_();
        double d6 = entity.f_19791_ + (entity.m_20186_() - entity.f_19791_) * (double)a.m_91296_();
        double d7 = entity.f_19792_ + (entity.m_20189_() - entity.f_19792_) * (double)a.m_91296_();
        bufferBuilder.m_252986_(poseStack.m_85850_().m_252922_(), (float)(d2 - d5), (float)(d3 - d6) - 1.5f, (float)(d4 - d7)).m_193479_(color.getRGB()).m_5752_();
    }

    private com.heypixel.heypixelmod.obsoverlay.modules.impl.render.Animations.a a(Entity entity) {
        if (entity.m_20096_()) {
            return null;
        }
        if (entity.m_20185_() == entity.f_19790_ && entity.m_20189_() == entity.f_19792_) {
            return null;
        }
        for (com.heypixel.heypixelmod.obsoverlay.modules.impl.render.Animations.a a2 : this.p()) {
            if (!a2.a(entity)) continue;
            return a2;
        }
        return null;
    }

    private List<com.heypixel.heypixelmod.obsoverlay.modules.impl.render.Animations.a> p() {
        ArrayList<com.heypixel.heypixelmod.obsoverlay.modules.impl.render.Animations.a> arrayList = new ArrayList<com.heypixel.heypixelmod.obsoverlay.modules.impl.render.Animations.a>();
        if (this.c.m()) {
            arrayList.add(this.h);
        }
        if (this.e.m()) {
            arrayList.add(this.i);
        }
        if (this.d.m()) {
            arrayList.add(this.j);
        }
        if (this.f.m()) {
            arrayList.add(this.k);
        }
        if (this.g.m()) {
            arrayList.add(this.l);
        }
        return arrayList;
    }

    private static /* synthetic */ boolean a(Player player, Entity entity) {
        return entity != player && entity instanceof LivingEntity;
    }

    public record Path(List<Vec3> path, HitResult result) {
        @Override
        public boolean equals(Object object) {
            List<Vec3> list;
            if (object == this) {
                return true;
            }
            if (!(object instanceof Path)) {
                return false;
            }
            Path path = (Path)object;
            if (!path.canEqual(this)) {
                return false;
            }
            List<Vec3> list2 = this.path();
            if (Objects.equals(list2, list = path.path())) {
                HitResult hitResult = this.result();
                HitResult hitResult2 = path.result();
                return Objects.equals(hitResult, hitResult2);
            }
            return false;
        }

        private boolean canEqual(Object object) {
            return object instanceof Path;
        }

        @Override
        public int hashCode() {
            int n2 = 59;
            int n3 = 1;
            List<Vec3> list = this.path();
            n3 = n3 * 59 + (list == null ? 43 : ((Object)list).hashCode());
            HitResult hitResult = this.result();
            return n3 * 59 + (hitResult == null ? 43 : hitResult.hashCode());
        }

        @Override
        public String toString() {
            return "Projectile.Path(path=" + String.valueOf(this.path()) + ", result=" + String.valueOf(this.result()) + ")";
        }
    }
}

