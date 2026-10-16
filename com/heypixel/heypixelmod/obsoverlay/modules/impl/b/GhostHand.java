/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.b;

import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.KeybindActionEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;
import com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils;
import com.heypixel.heypixelmod.obsoverlay.utils.ChunkBlockEntityUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

@ModuleInfo(a="GhostHand", b="\u9b3c\u5934", c="Allows interacting with blocks through walls", d=ModuleCategory.MISC)
public class GhostHand
extends ModuleBase {
    private static final Set<Block> c = new HashSet<Block>();
    private final Minecraft d = Minecraft.m_91087_();

    @EventTarget
    public void onClick(KeybindActionEvent d2) {
        if (this.d.f_91066_.f_92095_.m_90857_() && this.p()) {
            d2.a(true);
        }
    }

    public boolean p() {
        if (this.d.f_91074_ == null || this.d.f_91073_ == null) {
            return false;
        }
        Vec3 vec3 = this.d.f_91074_.m_20299_(1.0f);
        Vec3 vec32 = this.d.f_91074_.m_20252_(1.0f);
        Vec3 vec33 = vec3.m_82549_(vec32.m_82490_(4.5));
        ChestBlockEntity chestBlockEntity = null;
        BlockHitResult blockHitResult = null;
        double d2 = Double.MAX_VALUE;
        ArrayList arrayList = ChunkBlockEntityUtils.a().collect(Collectors.toCollection(ArrayList::new));
        for (BlockEntity blockEntity : arrayList) {
            double d3;
            Optional optional;
            ChestBlockEntity chestBlockEntity2;
            AABB aABB;
            if (!(blockEntity instanceof ChestBlockEntity) || (aABB = this.a(chestBlockEntity2 = (ChestBlockEntity)blockEntity)) == null || !(optional = aABB.m_82371_(vec3, vec33)).isPresent() || !((d3 = ((Vec3)optional.get()).m_82554_(vec3)) < d2)) continue;
            d2 = d3;
            chestBlockEntity = chestBlockEntity2;
            blockHitResult = new BlockHitResult((Vec3)optional.get(), Direction.UP, chestBlockEntity2.m_58899_(), false);
        }
        if (chestBlockEntity != null && blockHitResult != null) {
            this.d.f_91072_.m_233732_(this.d.f_91074_, InteractionHand.MAIN_HAND, blockHitResult);
            this.d.f_91074_.m_6674_(InteractionHand.MAIN_HAND);
            return true;
        }
        return false;
    }

    private AABB a(ChestBlockEntity chestBlockEntity) {
        BlockPos blockPos;
        BlockState blockState = chestBlockEntity.m_58900_();
        if (!blockState.m_61138_((Property)ChestBlock.f_51479_)) {
            return null;
        }
        ChestType chestType = (ChestType)blockState.m_61143_((Property)ChestBlock.f_51479_);
        if (chestType == ChestType.LEFT) {
            return null;
        }
        BlockPos blockPos2 = chestBlockEntity.m_58899_();
        AABB aABB = WorldBlockUtils.a(blockPos2);
        if (chestType != ChestType.SINGLE && WorldBlockUtils.c(blockPos = blockPos2.m_121945_(ChestBlock.m_51584_((BlockState)blockState)))) {
            AABB aABB2 = WorldBlockUtils.a(blockPos);
            aABB = aABB.m_82367_(aABB2);
        }
        return aABB;
    }

    private void a(BlockPos blockPos, Direction direction) {
        if (this.d.f_91072_ != null) {
            this.d.f_91072_.m_233732_(this.d.f_91074_, InteractionHand.MAIN_HAND, new BlockHitResult(Vec3.m_82512_((Vec3i)blockPos), direction, blockPos, false));
        }
        if (this.d.f_91074_ != null) {
            this.d.f_91074_.m_6674_(InteractionHand.MAIN_HAND);
        }
    }

    static {
        c.add(Blocks.f_50087_);
        c.add(Blocks.f_50265_);
        c.add(Blocks.f_50325_);
        c.add(Blocks.f_50456_);
    }
}

