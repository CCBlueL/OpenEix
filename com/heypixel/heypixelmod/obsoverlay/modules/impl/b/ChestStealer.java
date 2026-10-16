/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.b;

import com.heypixel.heypixelmod.obsoverlay.d.a.NumericSetting;
import com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerMotionEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Scaffold;
import com.heypixel.heypixelmod.obsoverlay.utils.EntityTrackingList;
import com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils;
import com.heypixel.heypixelmod.obsoverlay.utils.MathUtils;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;

@ModuleInfo(a="ChestStealer", b="\u7bb1\u5b50\u5c0f\u5077", c="Automatically steals items from chests", d=ModuleCategory.MISC)
public class ChestStealer
extends ModuleBase {
    private static final EntityTrackingList c = new EntityTrackingList();
    private final AutoSoup d = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Min Delay (Ticks)").a(2.0f).d(1.0f).b(0.0f).c(10.0f).a().c();
    private final AutoSoup e = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Max Delay (Ticks)").a(6.0f).d(1.0f).b(0.0f).c(10.0f).a().c();
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a f = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Ender Chest").a(false).a().b();
    private Screen g;

    public static boolean p() {
        return !c.a(3);
    }

    public static boolean a(ItemStack itemStack) {
        if (itemStack.m_41619_()) {
            return false;
        }
        if (EquipmentActionUtils.u(itemStack) || EquipmentActionUtils.b(itemStack)) {
            return true;
        }
        Item item = itemStack.m_41720_();
        if (item instanceof ArmorItem) {
            float f2;
            ArmorItem armorItem = (ArmorItem)item;
            float f3 = EquipmentActionUtils.s(itemStack);
            return !(f3 <= (f2 = EquipmentActionUtils.a(armorItem.m_40402_())));
        }
        if (itemStack.m_41720_() instanceof SwordItem) {
            float f4;
            float f5 = EquipmentActionUtils.r(itemStack);
            return !(f5 <= (f4 = EquipmentActionUtils.e()));
        }
        if (itemStack.m_41720_() instanceof PickaxeItem) {
            float f6;
            float f7 = EquipmentActionUtils.p(itemStack);
            return !(f7 <= (f6 = EquipmentActionUtils.n()));
        }
        if (itemStack.m_41720_() instanceof AxeItem) {
            float f8;
            float f9 = EquipmentActionUtils.p(itemStack);
            return !(f9 <= (f8 = EquipmentActionUtils.p()));
        }
        if (itemStack.m_41720_() instanceof ShovelItem) {
            float f10;
            float f11 = EquipmentActionUtils.p(itemStack);
            return !(f11 <= (f10 = EquipmentActionUtils.s()));
        }
        if (itemStack.m_41720_() instanceof CrossbowItem) {
            float f12;
            float f13 = EquipmentActionUtils.t(itemStack);
            return !(f13 <= (f12 = EquipmentActionUtils.u()));
        }
        if (itemStack.m_41720_() instanceof BowItem && EquipmentActionUtils.l(itemStack)) {
            float f14;
            float f15 = EquipmentActionUtils.n(itemStack);
            return !(f15 <= (f14 = EquipmentActionUtils.w()));
        }
        if (itemStack.m_41720_() instanceof BowItem && EquipmentActionUtils.m(itemStack)) {
            float f16;
            float f17 = EquipmentActionUtils.o(itemStack);
            return !(f17 <= (f16 = EquipmentActionUtils.y()));
        }
        if (itemStack.m_41720_() == Items.f_42522_) {
            return !EquipmentActionUtils.c(itemStack.m_41720_());
        }
        if (itemStack.m_41720_() == Items.f_42447_ && EquipmentActionUtils.d(Items.f_42447_) >= InventoryManager.t()) {
            return false;
        }
        if (itemStack.m_41720_() == Items.f_42448_ && EquipmentActionUtils.d(Items.f_42448_) >= InventoryManager.u()) {
            return false;
        }
        if (itemStack.m_41720_() instanceof BlockItem && Scaffold.a(itemStack) && EquipmentActionUtils.i() + itemStack.m_41613_() >= InventoryManager.p()) {
            return false;
        }
        if (itemStack.m_41720_() == Items.f_42412_ && EquipmentActionUtils.d(Items.f_42412_) + itemStack.m_41613_() >= InventoryManager.s()) {
            return false;
        }
        if (itemStack.m_41720_() instanceof FishingRodItem && EquipmentActionUtils.d(Items.f_42523_) >= 1) {
            return false;
        }
        if (itemStack.m_41720_() != Items.f_42452_ && itemStack.m_41720_() != Items.f_42521_ || EquipmentActionUtils.d(Items.f_42452_) + EquipmentActionUtils.d(Items.f_42521_) + itemStack.m_41613_() < InventoryManager.r() && InventoryManager.q()) {
            return !(itemStack.m_41720_() instanceof ItemNameBlockItem) && EquipmentActionUtils.v(itemStack);
        }
        return false;
    }

    @EventTarget(a=1)
    public void onMotion(PlayerMotionEvent k2) {
        if (k2.b() == com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.a) {
            Screen screen = com.heypixel.heypixelmod.obsoverlay.modules.impl.b.ChestStealer.a.f_91080_;
            if (screen instanceof ContainerScreen) {
                ContainerScreen containerScreen = (ContainerScreen)screen;
                ChestMenu chestMenu = (ChestMenu)containerScreen.m_6262_();
                if (screen != this.g) {
                    c.b();
                } else {
                    String string = containerScreen.m_96636_().getString();
                    String string2 = Component.m_237115_((String)"container.chest").getString();
                    String string3 = Component.m_237115_((String)"container.chestDouble").getString();
                    String string4 = Component.m_237115_((String)"container.enderchest").getString();
                    if (string.equals(string2) || string.equals(string3) || string.equals("Chest") || this.f.m() && string.equals(string4)) {
                        if (this.a(chestMenu) && c.a(MathUtils.b((int)this.d.q(), (int)this.e.q()))) {
                            com.heypixel.heypixelmod.obsoverlay.modules.impl.b.ChestStealer.a.f_91074_.m_6915_();
                        } else {
                            List list = IntStream.range(0, chestMenu.m_39265_() * 9).boxed().collect(Collectors.toList());
                            Collections.shuffle(list);
                            for (Integer n2 : list) {
                                ItemStack itemStack = chestMenu.m_38853_(n2.intValue()).m_7993_();
                                if (!com.heypixel.heypixelmod.obsoverlay.modules.impl.b.ChestStealer.a(itemStack) || !this.a(chestMenu, itemStack) || !c.a(MathUtils.b((int)this.d.q(), (int)this.e.q()))) continue;
                                com.heypixel.heypixelmod.obsoverlay.modules.impl.b.ChestStealer.a.f_91072_.m_171799_(chestMenu.f_38840_, n2.intValue(), 0, ClickType.QUICK_MOVE, (Player)com.heypixel.heypixelmod.obsoverlay.modules.impl.b.ChestStealer.a.f_91074_);
                                c.b();
                                break;
                            }
                        }
                    }
                }
            }
            this.g = screen;
        }
    }

    private boolean a(ChestMenu chestMenu, ItemStack itemStack) {
        if (!EquipmentActionUtils.u(itemStack) && !EquipmentActionUtils.b(itemStack)) {
            for (int i2 = 0; i2 < chestMenu.m_39265_() * 9; ++i2) {
                ItemStack itemStack2 = chestMenu.m_38853_(i2).m_7993_();
                Item item = itemStack.m_41720_();
                if (item instanceof ArmorItem) {
                    ArmorItem armorItem = (ArmorItem)item;
                    item = itemStack2.m_41720_();
                    if (item instanceof ArmorItem) {
                        ArmorItem armorItem2 = (ArmorItem)item;
                        if (armorItem.m_40402_() != armorItem2.m_40402_() || !(EquipmentActionUtils.s(itemStack2) > EquipmentActionUtils.s(itemStack))) continue;
                        return false;
                    }
                }
                if (!(itemStack.m_41720_() instanceof SwordItem && itemStack2.m_41720_() instanceof SwordItem ? EquipmentActionUtils.r(itemStack2) > EquipmentActionUtils.r(itemStack) : (itemStack.m_41720_() instanceof PickaxeItem && itemStack2.m_41720_() instanceof PickaxeItem ? EquipmentActionUtils.p(itemStack2) > EquipmentActionUtils.p(itemStack) : (itemStack.m_41720_() instanceof AxeItem && itemStack2.m_41720_() instanceof AxeItem ? EquipmentActionUtils.p(itemStack2) > EquipmentActionUtils.p(itemStack) : itemStack.m_41720_() instanceof ShovelItem && itemStack2.m_41720_() instanceof ShovelItem && EquipmentActionUtils.p(itemStack2) > EquipmentActionUtils.p(itemStack))))) continue;
                return false;
            }
            return true;
        }
        return true;
    }

    private boolean a(ChestMenu chestMenu) {
        for (int i2 = 0; i2 < chestMenu.m_39265_() * 9; ++i2) {
            ItemStack itemStack = chestMenu.m_38853_(i2).m_7993_();
            if (itemStack.m_41619_() || !com.heypixel.heypixelmod.obsoverlay.modules.impl.b.ChestStealer.a(itemStack) || !this.a(chestMenu, itemStack)) continue;
            return false;
        }
        return true;
    }
}

