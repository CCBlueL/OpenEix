/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.c;

import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventRunTicks;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerMotionEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.MovementInputEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.c.SafeWalk;
import com.heypixel.heypixelmod.obsoverlay.utils.EntityOverlayRenderer;
import com.heypixel.heypixelmod.obsoverlay.utils.c.PlayerRotationController;
import com.heypixel.heypixelmod.obsoverlay.utils.c.EntityRotationFactory;
import com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils;
import com.heypixel.heypixelmod.obsoverlay.utils.MathUtils;
import com.heypixel.heypixelmod.obsoverlay.utils.PlayerRotationUtils;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.FungusBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

@ModuleInfo(a="Scaffold", b="\u81ea\u52a8\u642d\u8def", c="Automatically places blocks under you", d=ModuleCategory.MOVEMENT)
public class Scaffold
extends ModuleBase {
    public static final List<Block> c = Arrays.asList(Blocks.f_50016_, Blocks.f_49990_, Blocks.f_49991_, Blocks.f_50201_, Blocks.f_50185_, Blocks.f_50185_, Blocks.f_50183_, Blocks.f_50125_, Blocks.f_49997_, Blocks.f_50089_, Blocks.f_50264_, Blocks.f_50087_, Blocks.f_50325_, Blocks.f_50081_, Blocks.f_50322_, Blocks.f_50325_, Blocks.f_50065_, Blocks.f_50131_, Blocks.f_50077_, Blocks.f_49995_, Blocks.f_49996_, Blocks.f_50059_, Blocks.f_50165_, Blocks.f_50326_, Blocks.f_50327_, Blocks.f_50124_, Blocks.f_50164_, Blocks.f_50359_, Blocks.f_50267_, Blocks.f_50266_, Blocks.f_50156_, Blocks.f_50121_, Blocks.f_50073_, Blocks.f_50072_, Blocks.f_50191_, Blocks.f_50355_, Blocks.f_50155_, Blocks.f_50094_, Blocks.f_49992_, Blocks.f_50128_, Blocks.f_50061_, Blocks.f_50286_, Blocks.f_50091_, Blocks.f_50033_, Blocks.f_50133_, Blocks.f_50274_, Blocks.f_50132_, Blocks.f_50174_, Blocks.f_50276_);
    com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a d = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Telly").a(true).a().b();
    com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a e = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Snap").a(false).a(() -> !this.d.m()).a().b();
    com.heypixel.heypixelmod.obsoverlay.d.SettingModel.c f = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Rotation Speed").a(180.0f).d(1.0f).b(1.0f).c(180.0f).a().c();
    com.heypixel.heypixelmod.obsoverlay.d.SettingModel.c g = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Rotation Back Speed").a(180.0f).d(1.0f).b(1.0f).c(180.0f).a(this.d::m).a().c();
    com.heypixel.heypixelmod.obsoverlay.d.SettingModel.c h = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Telly Ticks").a(1.0f).d(1.0f).b(0.0f).c(6.0f).a(this.d::m).a().c();
    com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a i = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "SafeWalk").a(true).a(() -> !this.d.m()).a().b();
    com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a j = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "ESP").a(true).a().b();
    private int k;
    private int l;
    private BlockPos m;
    private Direction n;
    private int o = -1;

    public static Vec3 a(BlockPos blockPos, Direction direction) {
        double d2 = (double)blockPos.m_123341_() + 0.5;
        double d3 = (double)blockPos.m_123342_() + 0.5;
        double d4 = (double)blockPos.m_123343_() + 0.5;
        if (direction != Direction.UP && direction != Direction.DOWN) {
            d3 += 0.08;
        } else {
            d2 += MathUtils.a(0.3, -0.3);
            d4 += MathUtils.a(0.3, -0.3);
        }
        if (direction == Direction.WEST || direction == Direction.EAST) {
            d4 += MathUtils.a(0.3, -0.3);
        }
        if (direction == Direction.SOUTH || direction == Direction.NORTH) {
            d2 += MathUtils.a(0.3, -0.3);
        }
        return new Vec3(d2, d3, d4);
    }

    public static boolean a(ItemStack itemStack) {
        if (itemStack == null || !(itemStack.m_41720_() instanceof BlockItem) || itemStack.m_41613_() <= 1) {
            return false;
        }
        if (!EquipmentActionUtils.k(itemStack)) {
            return false;
        }
        String string = itemStack.m_41611_().getString();
        if (string.contains("Click") || string.contains("\u70b9\u51fb")) {
            return false;
        }
        if (itemStack.m_41720_() instanceof ItemNameBlockItem) {
            return false;
        }
        Block block = ((BlockItem)itemStack.m_41720_()).m_40614_();
        if (block instanceof FlowerBlock) {
            return false;
        }
        if (block instanceof BushBlock) {
            return false;
        }
        if (block instanceof FungusBlock) {
            return false;
        }
        if (block instanceof CropBlock) {
            return false;
        }
        return !(block instanceof SlabBlock) && !c.contains(block);
    }

    @EventTarget
    public void onPreTick(EventRunTicks eventRunTicks) {
        if (com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Scaffold.a.f_91074_ == null || com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Scaffold.a.f_91073_ == null || eventRunTicks.type() != com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.a) {
            return;
        }
        int n2 = -1;
        for (int i2 = 0; i2 < 9; ++i2) {
            ItemStack itemStack = com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Scaffold.a.f_91074_.m_150109_().m_8020_(i2);
            if (!com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Scaffold.a(itemStack)) continue;
            n2 = i2;
            break;
        }
        if (n2 != -1 && com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Scaffold.a.f_91074_.m_150109_().f_35977_ != n2) {
            com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Scaffold.a.f_91074_.m_150109_().f_35977_ = n2;
        }
        if (com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Scaffold.a.f_91074_.m_20096_()) {
            this.l = (int)Math.floor(com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Scaffold.a.f_91074_.m_20186_()) - 1;
        }
        this.r();
        if (this.d.m()) {
            if (com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Scaffold.a.f_91074_.m_20096_()) {
                this.k = 0;
                this.m = null;
                this.n = null;
                com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.b b2 = new com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.b(com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Scaffold.a.f_91074_.m_146908_(), com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Scaffold.a.f_91074_.m_146909_());
                com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.a(b2, (double)this.g.q());
            } else {
                if ((float)this.k >= this.h.q()) {
                    com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.b b3 = this.b(this.m, this.n);
                    com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.a(b3, (double)this.f.q());
                    this.p();
                }
                ++this.k;
            }
            this.a("Telly");
        } else {
            if (this.m == null) {
                com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.a(new com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.b(Mth.m_14177_((float)(com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Scaffold.a.f_91074_.m_146908_() - 180.0f)), 89.64f), (double)this.f.q());
            }
            if (this.s() || !this.e.m()) {
                com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.b b4 = this.b(this.m, this.n);
                com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.a(b4, (double)this.f.q());
            }
            this.p();
            this.a(this.e.m() ? "Snap" : "Normal");
        }
    }

    public void p() {
        InteractionResult interactionResult;
        if (!this.s()) {
            return;
        }
        boolean bl = com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.a.a(com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.j(), this.m);
        if (bl && (interactionResult = com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Scaffold.a.f_91072_.m_233732_(com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Scaffold.a.f_91074_, InteractionHand.MAIN_HAND, new BlockHitResult(com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Scaffold.a(this.m, this.n), this.n, this.m, false))) == InteractionResult.SUCCESS) {
            com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Scaffold.a.f_91074_.m_6674_(InteractionHand.MAIN_HAND);
        }
    }

    @EventTarget
    public void onMoveInput(MovementInputEvent l2) {
        if (com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Scaffold.a.f_91074_.m_20096_() && !com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Scaffold.a.f_91066_.f_92089_.m_90857_() && PlayerRotationUtils.a() && this.d.m()) {
            l2.a(true);
        }
    }

    public int q() {
        if (!com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Scaffold.a.f_91066_.f_92089_.m_90857_() && PlayerRotationUtils.a() && (double)com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Scaffold.a.f_91074_.f_19789_ <= 0.25 && this.d.m()) {
            return this.l;
        }
        return (int)Math.floor(com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Scaffold.a.f_91074_.m_20186_()) - 1;
    }

    public void r() {
        Vec3 vec3 = com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Scaffold.a.f_91074_.m_146892_();
        BlockPos blockPos = BlockPos.m_274561_((double)vec3.f_82479_, (double)this.q(), (double)vec3.f_82481_);
        int n2 = blockPos.m_123341_();
        int n3 = blockPos.m_123343_();
        if (this.a(com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Scaffold.a.f_91073_.m_8055_(blockPos), (Level)com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Scaffold.a.f_91073_, blockPos)) {
            return;
        }
        if (this.a(vec3, blockPos)) {
            return;
        }
        for (int i2 = 1; i2 <= 6; ++i2) {
            if (this.a(vec3, new BlockPos(n2, this.q() - i2, n3))) {
                return;
            }
            for (int i3 = 0; i3 <= i2; ++i3) {
                for (int i4 = 0; i4 <= i2 - i3; ++i4) {
                    int n4 = i2 - i3 - i4;
                    for (int i5 = 0; i5 <= 1; ++i5) {
                        for (int i6 = 0; i6 <= 1; ++i6) {
                            if (!this.a(vec3, new BlockPos(n2 + (i5 == 0 ? i3 : -i3), this.q() - n4, n3 + (i6 == 0 ? i4 : -i4)))) continue;
                            return;
                        }
                    }
                }
            }
        }
    }

    public boolean a(BlockState blockState, Level level, BlockPos blockPos) {
        boolean bl = !blockState.m_60812_((BlockGetter)level, blockPos).m_83281_();
        boolean bl2 = blockState.m_60750_(level, blockPos) == null;
        return bl && bl2;
    }

    private boolean a(Vec3 vec3, BlockPos blockPos) {
        if (!(com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Scaffold.a.f_91073_.m_8055_(blockPos).m_60734_() instanceof AirBlock) && !(com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Scaffold.a.f_91073_.m_8055_(blockPos).m_60734_() instanceof WaterlilyBlock)) {
            return false;
        }
        if (blockPos.m_123342_() > this.q()) {
            return false;
        }
        Vec3 vec32 = new Vec3((double)blockPos.m_123341_() + 0.5, (double)blockPos.m_123342_(), (double)blockPos.m_123343_() + 0.5);
        for (Direction direction : Direction.values()) {
            Vec3 vec33;
            Vec3 vec34 = vec32.m_82549_(new Vec3((double)direction.m_122436_().m_123341_(), (double)direction.m_122436_().m_123342_(), (double)direction.m_122436_().m_123343_()).m_82490_(0.5));
            BlockPos blockPos2 = blockPos.m_121955_(direction.m_122436_());
            BlockPos blockPos3 = new BlockPos(blockPos2.m_123341_(), blockPos2.m_123342_(), blockPos2.m_123343_());
            if (!this.a(com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Scaffold.a.f_91073_.m_8055_(blockPos3), (Level)com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Scaffold.a.f_91073_, blockPos3) || !((vec33 = vec34.m_82546_(vec3)).m_82556_() <= 20.25) || !(vec33.m_82526_(new Vec3((double)direction.m_122436_().m_123341_(), (double)direction.m_122436_().m_123342_(), (double)direction.m_122436_().m_123343_())) >= 0.0) || direction.m_122424_() == Direction.UP && PlayerRotationUtils.a() && !com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Scaffold.a.f_91066_.f_92089_.m_90857_()) continue;
            this.m = new BlockPos((Vec3i)blockPos2);
            this.n = direction.m_122424_();
            return true;
        }
        return false;
    }

    public BlockPos a(double d2, double d3, double d4) {
        return new BlockPos((int)Math.floor(d2), (int)Math.floor(d3), (int)Math.floor(d4));
    }

    @EventTarget
    public void onMotion(PlayerMotionEvent k2) {
        if (k2.b() == com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.a && this.i.m() && !this.d.m()) {
            com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Scaffold.a.f_91066_.f_92090_.m_7249_(com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Scaffold.a.f_91074_.m_20096_() && com.heypixel.heypixelmod.obsoverlay.modules.impl.c.SafeWalk.a(0.3f));
        }
    }

    @EventTarget
    public void onRender(com.heypixel.heypixelmod.obsoverlay.events.impl.WorldRenderEvent p2) {
        if (this.m != null && this.j.m()) {
            PoseStack poseStack = p2.b();
            poseStack.m_85836_();
            Vec3 vec3 = EntityOverlayRenderer.b();
            poseStack.m_85837_(-vec3.f_82479_, -vec3.f_82480_, -vec3.f_82481_);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask((boolean)false);
            RenderSystem.setShader(GameRenderer::m_172808_);
            RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)0.4f);
            AABB aABB = new AABB(this.m);
            EntityOverlayRenderer.a(aABB, poseStack);
            RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            RenderSystem.depthMask((boolean)true);
            RenderSystem.enableDepthTest();
            RenderSystem.disableBlend();
            poseStack.m_85849_();
        }
    }

    @Override
    public void d() {
        if (com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Scaffold.a.f_91074_ != null) {
            this.o = com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Scaffold.a.f_91074_.m_150109_().f_35977_;
        }
        this.k = 0;
        this.m = null;
        this.n = null;
    }

    @Override
    public void e() {
        boolean bl = InputConstants.m_84830_((long)a.m_91268_().m_85439_(), (int)com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Scaffold.a.f_91066_.f_92090_.getKey().m_84873_());
        com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Scaffold.a.f_91066_.f_92090_.m_7249_(bl);
        if (com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Scaffold.a.f_91074_ != null && this.o != -1) {
            com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Scaffold.a.f_91074_.m_150109_().f_35977_ = this.o;
        }
    }

    public com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.b b(BlockPos blockPos, Direction direction) {
        com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.b b2 = this.s() ? com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.d.a(blockPos, direction) : com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.d.a(blockPos.m_252807_());
        com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.b b3 = new com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.b(Mth.m_14177_((float)(com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Scaffold.a.f_91074_.m_146908_() - 180.0f)), b2.b());
        boolean bl = com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.a.a(b3, blockPos);
        if (bl) {
            return b3;
        }
        return b2;
    }

    private boolean s() {
        Vec3 vec3 = com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Scaffold.a.f_91074_.m_146892_();
        BlockPos blockPos = BlockPos.m_274561_((double)vec3.f_82479_, (double)this.q(), (double)vec3.f_82481_);
        return com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Scaffold.a.f_91073_.m_8055_(blockPos).m_60734_() instanceof AirBlock || com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Scaffold.a.f_91073_.m_8055_(blockPos).m_60734_() instanceof WaterlilyBlock;
    }
}

