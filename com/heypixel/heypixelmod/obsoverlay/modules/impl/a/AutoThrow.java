/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.a;

import com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerMotionEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AntiBots;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.Target;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.Teams;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Scaffold;
import com.heypixel.heypixelmod.obsoverlay.utils.ElapsedTimer;
import com.heypixel.heypixelmod.obsoverlay.utils.c.PlayerRotationController;
import com.heypixel.heypixelmod.obsoverlay.utils.EntityNameFilter;
import com.heypixel.heypixelmod.obsoverlay.utils.BlockPredictionUtils;
import java.util.Comparator;
import java.util.Optional;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.network.protocol.game.ServerboundSwingPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.EnderpearlItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.LingeringPotionItem;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.SplashPotionItem;
import net.minecraft.world.phys.Vec3;

@ModuleInfo(a="AutoThrow", b="\u81ea\u52a8\u6295\u63b7", c="Automatically throw snowballs and eggs.", d=com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory.COMBAT)
public class AutoThrow
extends com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase {
    private static final double c = 0.6;
    private static final double d = 0.006;
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.c e = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Min Distance").a(5.0f).d(1.0f).b(3.0f).c(30.0f).a().c();
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.c f = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Max Distance").a(10.0f).d(1.0f).b(3.0f).c(30.0f).a().c();
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.c g = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Delay").a(500.0f).d(50.0f).b(50.0f).c(2000.0f).a().c();
    private final ElapsedTimer h = new ElapsedTimer();
    private com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.b i;
    private int j;
    private int k = -1;
    private ItemUseTarget l;

    @EventTarget
    public void onMotion(PlayerMotionEvent k2) {
        if (k2.b() != com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.a) {
            if (this.k != -1) {
                a.m_91403_().m_104955_((Packet)new ServerboundSetCarriedItemPacket(this.k));
                this.k = -1;
            }
            return;
        }
        if (com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AutoThrow.a.f_91074_ == null || com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AutoThrow.a.f_91073_ == null) {
            return;
        }
        if (com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a(Scaffold.class).m() || com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a(com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Stuck.class).m() || com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a(com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Blink.class).m()) {
            this.j = 0;
            this.l = null;
            return;
        }
        this.i = null;
        ItemUseTarget a2 = this.p();
        if (a2 == null) {
            return;
        }
        if (this.j > 0) {
            --this.j;
            if (this.j == 0 && this.l != null) {
                this.a(this.l);
                this.l = null;
            }
            return;
        }
        Optional<AbstractClientPlayer> optional = this.q();
        if (optional.isPresent() && this.h.a((double)this.g.q()) && this.a(a2.a)) {
            this.i = this.a((LivingEntity)optional.get());
            com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c.a(this.i, 180.0);
            this.j = 2;
            this.l = a2;
            this.h.a();
        }
    }

    private void a(ItemUseTarget a2) {
        if (a2.a == InteractionHand.MAIN_HAND) {
            boolean bl;
            int n3 = com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AutoThrow.a.f_91074_.m_150109_().f_35977_;
            boolean bl2 = bl = n3 != a2.b;
            if (bl) {
                a.m_91403_().m_104955_((Packet)new ServerboundSetCarriedItemPacket(a2.b));
                this.k = n3;
            }
        }
        BlockPredictionUtils.a(n2 -> new ServerboundUseItemPacket(a2.a, n2));
        a.m_91403_().m_104955_((Packet)new ServerboundSwingPacket(a2.a));
    }

    private ItemUseTarget p() {
        ItemStack itemStack = com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AutoThrow.a.f_91074_.m_21206_();
        if (this.a(itemStack)) {
            return new ItemUseTarget(InteractionHand.OFF_HAND, -1);
        }
        int n2 = com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AutoThrow.a.f_91074_.m_150109_().f_35977_;
        ItemStack itemStack2 = (ItemStack)com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AutoThrow.a.f_91074_.m_150109_().f_35974_.get(n2);
        if (this.a(itemStack2)) {
            return new ItemUseTarget(InteractionHand.MAIN_HAND, n2);
        }
        for (int i2 = 0; i2 < 9; ++i2) {
            ItemStack itemStack3 = (ItemStack)com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AutoThrow.a.f_91074_.m_150109_().f_35974_.get(i2);
            if (!this.a(itemStack3)) continue;
            return new ItemUseTarget(InteractionHand.MAIN_HAND, i2);
        }
        return null;
    }

    private boolean a(InteractionHand interactionHand) {
        ItemStack itemStack;
        if (com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AutoThrow.a.f_91074_.m_6117_()) {
            return false;
        }
        ItemStack itemStack2 = itemStack = interactionHand == InteractionHand.MAIN_HAND ? com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AutoThrow.a.f_91074_.m_21205_() : com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AutoThrow.a.f_91074_.m_21206_();
        if (itemStack.m_41619_()) {
            return true;
        }
        Item item = itemStack.m_41720_();
        if (item instanceof EnderpearlItem) {
            return false;
        }
        if (item instanceof BowItem) {
            return false;
        }
        if (item instanceof PotionItem || item instanceof SplashPotionItem || item instanceof LingeringPotionItem) {
            return false;
        }
        return !item.m_41472_();
    }

    private com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.b a(LivingEntity livingEntity) {
        Vec3 vec3 = livingEntity.m_20184_();
        double d2 = livingEntity.m_20185_();
        double d3 = livingEntity.m_20186_() + (double)livingEntity.m_20206_() * 0.6;
        double d4 = livingEntity.m_20189_();
        double d5 = 0.0;
        for (int i2 = 0; i2 < 3; ++i2) {
            double d6 = d2 + vec3.f_82479_ * d5;
            double d7 = d4 + vec3.f_82481_ * d5;
            double d8 = d6 - com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AutoThrow.a.f_91074_.m_20185_();
            double d9 = d7 - com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AutoThrow.a.f_91074_.m_20189_();
            double d10 = Math.sqrt(d8 * d8 + d9 * d9);
            d5 = d10 / 0.6;
        }
        double d11 = d2 + vec3.f_82479_ * d5;
        double d12 = d3 + vec3.f_82480_ * d5;
        double d13 = d4 + vec3.f_82481_ * d5;
        double d14 = d11 - com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AutoThrow.a.f_91074_.m_20185_();
        double d15 = d13 - com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AutoThrow.a.f_91074_.m_20189_();
        double d16 = d12 - (com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AutoThrow.a.f_91074_.m_20186_() + (double)com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AutoThrow.a.f_91074_.m_20192_());
        double d17 = Math.sqrt(d14 * d14 + d15 * d15);
        float f2 = (float)(Math.toDegrees(Math.atan2(d15, d14)) - 90.0);
        float f3 = -this.a((float)d17, (float)d16, 0.6f, 0.006f);
        return new com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.b(f2, Mth.m_14036_((float)f3, (float)-90.0f, (float)90.0f));
    }

    private float a(float f2, float f3, float f4, float f5) {
        float f6 = f4 * f4;
        float f7 = f6 * f6 - f5 * (f5 * f2 * f2 + 2.0f * f3 * f6);
        if (f7 <= 0.0f) {
            return (float)Math.toDegrees(Math.atan2(f3, f2));
        }
        return (float)Math.toDegrees(Math.atan(((double)f6 - Math.sqrt(f7)) / (double)(f5 * f2)));
    }

    private Optional<AbstractClientPlayer> q() {
        Target p2 = com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a(Target.class);
        return com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AutoThrow.a.f_91073_.m_6907_().stream().filter(abstractClientPlayer -> abstractClientPlayer != com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AutoThrow.a.f_91074_).filter(LivingEntity::m_6084_).filter(abstractClientPlayer -> !abstractClientPlayer.m_5833_()).filter(abstractClientPlayer -> !com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AntiBots.b((Entity)abstractClientPlayer)).filter(abstractClientPlayer -> p2 == null || p2.a((Entity)abstractClientPlayer)).filter(abstractClientPlayer -> !Teams.a((Entity)abstractClientPlayer)).filter(abstractClientPlayer -> !EntityNameFilter.a((Entity)abstractClientPlayer)).filter(abstractClientPlayer -> !com.heypixel.heypixelmod.obsoverlay.modules.impl.b.ClientFriend.a((Entity)abstractClientPlayer)).filter(arg_0 -> ((LocalPlayer)com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AutoThrow.a.f_91074_).m_142582_(arg_0)).filter(abstractClientPlayer -> !abstractClientPlayer.m_20177_((Player)com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AutoThrow.a.f_91074_)).filter(abstractClientPlayer -> {
            double d2 = this.b((LivingEntity)abstractClientPlayer);
            return d2 <= (double)this.f.q() && d2 >= (double)this.e.q();
        }).min(Comparator.comparingDouble(abstractClientPlayer -> com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AutoThrow.a.f_91074_.m_20270_((Entity)abstractClientPlayer)));
    }

    private double b(LivingEntity livingEntity) {
        double d2 = livingEntity.m_20185_() - com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AutoThrow.a.f_91074_.m_20185_();
        double d3 = livingEntity.m_20189_() - com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AutoThrow.a.f_91074_.m_20189_();
        return Math.sqrt(d2 * d2 + d3 * d3);
    }

    private boolean a(ItemStack itemStack) {
        return !itemStack.m_41619_() && (itemStack.m_41720_() == Items.f_42521_ || itemStack.m_41720_() == Items.f_42452_);
    }

    private static class ItemUseTarget {
        private final InteractionHand a;
        private final int b;

        private ItemUseTarget(InteractionHand interactionHand, int n2) {
            this.a = interactionHand;
            this.b = n2;
        }
    }
}

