/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.b;

import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.HeldItemUpdateEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerMotionEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;
import com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.RedStoneOreBlock;
import net.minecraft.world.level.block.WebBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

@ModuleInfo(a="AutoTools", b="\u81ea\u52a8\u5de5\u5177", c="Automatically switches to the best tool for the job", d=ModuleCategory.MISC)
public class AutoTools
extends ModuleBase {
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a c = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Check Sword").a(false).a().b();
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a d = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Switch Back").a(true).a().b();
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a e = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Silent").a(this.d::m).a(true).a().b();
    private int f = -1;

    @EventTarget
    public void onUpdateHeldItem(HeldItemUpdateEvent af2) {
        if (this.d.m() && this.e.m() && af2.a() == InteractionHand.MAIN_HAND && this.f != -1) {
            af2.a(com.heypixel.heypixelmod.obsoverlay.modules.impl.b.AutoTools.a.f_91074_.m_150109_().m_8020_(this.f));
        }
    }

    @EventTarget
    public void onMotion(PlayerMotionEvent k2) {
        if (k2.b() == com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.a) {
            if (com.heypixel.heypixelmod.obsoverlay.modules.impl.b.AutoTools.a.f_91072_.m_105296_()) {
                int n2;
                ItemStack itemStack;
                if (this.c.m() && (itemStack = com.heypixel.heypixelmod.obsoverlay.modules.impl.b.AutoTools.a.f_91074_.m_21205_()).m_41720_() instanceof SwordItem) {
                    return;
                }
                if (com.heypixel.heypixelmod.obsoverlay.modules.impl.b.AutoTools.a.f_91077_.m_6662_() == HitResult.Type.BLOCK && (n2 = this.a((itemStack = (BlockHitResult)com.heypixel.heypixelmod.obsoverlay.modules.impl.b.AutoTools.a.f_91077_).m_82425_())) != -1 && n2 != com.heypixel.heypixelmod.obsoverlay.modules.impl.b.AutoTools.a.f_91074_.m_150109_().f_35977_) {
                    this.f = com.heypixel.heypixelmod.obsoverlay.modules.impl.b.AutoTools.a.f_91074_.m_150109_().f_35977_;
                    com.heypixel.heypixelmod.obsoverlay.modules.impl.b.AutoTools.a.f_91074_.m_150109_().f_35977_ = n2;
                }
            }
        } else if (!com.heypixel.heypixelmod.obsoverlay.modules.impl.b.AutoTools.a.f_91072_.m_105296_() && this.d.m() && this.f != -1) {
            com.heypixel.heypixelmod.obsoverlay.modules.impl.b.AutoTools.a.f_91074_.m_150109_().f_35977_ = this.f;
            this.f = -1;
        }
    }

    private int a(BlockPos blockPos) {
        BlockState blockState = com.heypixel.heypixelmod.obsoverlay.modules.impl.b.AutoTools.a.f_91073_.m_8055_(blockPos);
        Block block = blockState.m_60734_();
        int n2 = 0;
        float f2 = 1.0f;
        for (int i2 = 0; i2 < 9; ++i2) {
            int n3;
            ItemStack itemStack = com.heypixel.heypixelmod.obsoverlay.modules.impl.b.AutoTools.a.f_91074_.m_150109_().m_8020_(i2);
            if (EquipmentActionUtils.u(itemStack) || itemStack.m_41619_() || blockState.m_60795_() || itemStack.m_41720_() instanceof SwordItem && !(block instanceof WebBlock)) continue;
            float f3 = itemStack.m_41720_().m_8102_(itemStack, blockState);
            if (f3 > 1.0f && !(block instanceof DropExperienceBlock) && !(block instanceof RedStoneOreBlock) && (n3 = EnchantmentHelper.m_44843_((Enchantment)Enchantments.f_44984_, (ItemStack)itemStack)) > 0) {
                f3 += (float)(n3 * n3 + 1);
            }
            if (!(f3 > f2)) continue;
            n2 = i2;
            f2 = f3;
        }
        return f2 > 1.0f ? n2 : -1;
    }
}

