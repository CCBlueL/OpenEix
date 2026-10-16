/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.a;

import com.heypixel.heypixelmod.obsoverlay.d.a.NumericSetting;
import com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerMotionEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;
import com.heypixel.heypixelmod.obsoverlay.utils.ElapsedTimer;
import com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils;
import com.heypixel.heypixelmod.obsoverlay.utils.BlockPredictionUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

@ModuleInfo(a="AutoHeal", b="\u81ea\u52a8\u56de\u8840", c="Automatically heals you when you're low on health.", d=ModuleCategory.COMBAT)
public class AutoHeal
extends ModuleBase {
    private final ElapsedTimer c = new ElapsedTimer();
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a d = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Speed Check").a(true).a().b();
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a e = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Regen Check").a(true).a().b();
    private final CrystalAura f = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Delay").a(500.0f).d(1.0f).b(300.0f).c(1000.0f).a().c();
    private final CrystalAura g = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Health Percent").a(0.5f).d(0.05f).b(0.0f).c(1.0f).a().c();
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.e h = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Mode").a("Soup", "Head").a(0).a().e();
    private boolean i = false;
    private boolean j = false;
    private boolean k = false;

    @EventTarget
    public void onMotion(PlayerMotionEvent k2) {
        block12: {
            if (k2.b() != com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.a) break block12;
            if (this.j) {
                BlockPredictionUtils.a(n2 -> new ServerboundUseItemPacket(InteractionHand.MAIN_HAND, n2));
                this.j = false;
                return;
            }
            if (this.k) {
                a.m_91403_().m_104955_((Packet)new ServerboundPlayerActionPacket(ServerboundPlayerActionPacket.Action.DROP_ITEM, BlockPos.f_121853_, Direction.DOWN));
                this.k = false;
                return;
            }
            if (this.i) {
                a.m_91403_().m_104955_((Packet)new ServerboundSetCarriedItemPacket(com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AutoHeal.a.f_91074_.m_150109_().f_35977_));
                this.i = false;
                return;
            }
            if (!this.c.a((double)this.f.q())) {
                return;
            }
            if (com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AutoHeal.a.f_91074_.m_21023_(MobEffects.f_19596_) && this.d.m()) {
                return;
            }
            if (com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AutoHeal.a.f_91074_.m_21023_(MobEffects.f_19605_) && this.e.m()) {
                return;
            }
            if (com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AutoHeal.a.f_91074_.m_21223_() / com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AutoHeal.a.f_91074_.m_21233_() < this.g.q()) {
                if (this.h.a("Soup")) {
                    for (int i2 = 0; i2 < 9; ++i2) {
                        ItemStack itemStack = (ItemStack)com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AutoHeal.a.f_91074_.m_150109_().f_35974_.get(i2);
                        if (itemStack.m_41720_() != Items.f_42400_) continue;
                        this.a(i2, true);
                        this.i = true;
                        break;
                    }
                } else if (this.h.a("Head")) {
                    for (int i3 = 0; i3 < 9; ++i3) {
                        ItemStack itemStack = (ItemStack)com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AutoHeal.a.f_91074_.m_150109_().f_35974_.get(i3);
                        if (!EquipmentActionUtils.a(itemStack)) continue;
                        this.a(i3, false);
                        this.i = true;
                        break;
                    }
                }
            }
        }
    }

    private void a(int n2, boolean bl) {
        a.m_91403_().m_104955_((Packet)new ServerboundSetCarriedItemPacket(n2));
        this.k = bl;
        this.j = true;
    }
}

