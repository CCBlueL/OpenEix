/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils;

import com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Scaffold;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BookItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ExperienceBottleItem;
import net.minecraft.world.item.FireworkRocketItem;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.PlayerHeadItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SkullBlock;

public class EquipmentActionUtils {
    private static final Minecraft a = Minecraft.m_91087_();

    public static boolean a() {
        return EquipmentActionUtils.d().stream().anyMatch(itemStack -> {
            if (itemStack.m_41619_()) {
                return false;
            }
            String string = itemStack.m_41611_().getString();
            return string.contains("\u957f\u6309\u70b9\u51fb") || string.contains("\u70b9\u51fb\u4f7f\u7528") || string.contains("\u79bb\u5f00\u6e38\u620f") || string.contains("\u9009\u62e9\u4e00\u4e2a\u961f\u4f0d") || string.contains("\u518d\u6765\u4e00\u5c40");
        });
    }

    public static boolean a(ItemStack itemStack) {
        if (itemStack.m_41619_()) {
            return false;
        }
        Item item = itemStack.m_41720_();
        if (item instanceof BlockItem) {
            BlockItem blockItem = (BlockItem)item;
            return blockItem.m_40614_() instanceof SkullBlock;
        }
        return false;
    }

    public static boolean b(ItemStack itemStack) {
        if (itemStack.m_41619_()) {
            return false;
        }
        if (!(itemStack.m_41720_() instanceof AxeItem)) {
            return false;
        }
        int n2 = EnchantmentHelper.m_44843_((Enchantment)Enchantments.f_44977_, (ItemStack)itemStack);
        return n2 >= 8 && n2 < 50;
    }

    public static boolean c(ItemStack itemStack) {
        if (itemStack.m_41619_()) {
            return false;
        }
        return itemStack.m_41720_() == Items.f_42433_ && EnchantmentHelper.m_44843_((Enchantment)Enchantments.f_44977_, (ItemStack)itemStack) > 100;
    }

    public static boolean d(ItemStack itemStack) {
        return !itemStack.m_41619_() && itemStack.m_41720_() == Items.f_42437_;
    }

    public static boolean e(ItemStack itemStack) {
        return !itemStack.m_41619_() && itemStack.m_41720_() == Items.f_42729_;
    }

    public static boolean f(ItemStack itemStack) {
        if (itemStack.m_41619_()) {
            return false;
        }
        return itemStack.m_41720_() == Items.f_42518_ && EnchantmentHelper.m_44843_((Enchantment)Enchantments.f_44980_, (ItemStack)itemStack) > 1;
    }

    public static boolean g(ItemStack itemStack) {
        if (itemStack.m_41619_()) {
            return false;
        }
        return itemStack.m_41720_() == Items.f_42398_ && EnchantmentHelper.m_44843_((Enchantment)Enchantments.f_44980_, (ItemStack)itemStack) > 1;
    }

    public static int b() {
        for (int i2 = 9; i2 < EquipmentActionUtils.a.f_91074_.m_150109_().f_35974_.size(); ++i2) {
            if (!((ItemStack)EquipmentActionUtils.a.f_91074_.m_150109_().f_35974_.get(i2)).m_41619_()) continue;
            return i2;
        }
        return -1;
    }

    public static int c() {
        for (int i2 = 0; i2 < 9; ++i2) {
            if (!((ItemStack)EquipmentActionUtils.a.f_91074_.m_150109_().f_35974_.get(i2)).m_41619_()) continue;
            return i2;
        }
        return -1;
    }

    public static Integer a(Item item) {
        for (int i2 = 0; i2 < 9; ++i2) {
            ItemStack itemStack = (ItemStack)EquipmentActionUtils.a.f_91074_.m_150109_().f_35974_.get(i2);
            if (itemStack.m_41720_() != item) continue;
            return i2;
        }
        return null;
    }

    public static int h(ItemStack itemStack) {
        return EnchantmentHelper.m_44843_((Enchantment)Enchantments.f_44989_, (ItemStack)itemStack);
    }

    public static int i(ItemStack itemStack) {
        return EnchantmentHelper.m_44843_((Enchantment)Enchantments.f_44988_, (ItemStack)itemStack);
    }

    public static List<ItemStack> d() {
        ArrayList<ItemStack> arrayList = new ArrayList<ItemStack>(40);
        arrayList.addAll((Collection<ItemStack>)EquipmentActionUtils.a.f_91074_.m_150109_().f_35974_);
        arrayList.addAll((Collection<ItemStack>)EquipmentActionUtils.a.f_91074_.m_150109_().f_35975_);
        return arrayList;
    }

    public static float a(EquipmentSlot equipmentSlot) {
        return EquipmentActionUtils.d().stream().filter(itemStack -> !itemStack.m_41619_() && itemStack.m_41720_() instanceof ArmorItem && ((ArmorItem)itemStack.m_41720_()).m_40402_() == equipmentSlot).map(EquipmentActionUtils::s).max(Float::compareTo).orElse(Float.valueOf(0.0f)).floatValue();
    }

    public static float b(EquipmentSlot equipmentSlot) {
        if (equipmentSlot == EquipmentSlot.HEAD) {
            return EquipmentActionUtils.s((ItemStack)EquipmentActionUtils.a.f_91074_.m_150109_().f_35975_.get(3));
        }
        if (equipmentSlot == EquipmentSlot.CHEST) {
            return EquipmentActionUtils.s((ItemStack)EquipmentActionUtils.a.f_91074_.m_150109_().f_35975_.get(2));
        }
        if (equipmentSlot == EquipmentSlot.LEGS) {
            return EquipmentActionUtils.s((ItemStack)EquipmentActionUtils.a.f_91074_.m_150109_().f_35975_.get(1));
        }
        return equipmentSlot == EquipmentSlot.FEET ? EquipmentActionUtils.s((ItemStack)EquipmentActionUtils.a.f_91074_.m_150109_().f_35975_.get(0)) : 0.0f;
    }

    public static float e() {
        return EquipmentActionUtils.d().stream().filter(itemStack -> !itemStack.m_41619_() && itemStack.m_41720_() instanceof SwordItem).map(EquipmentActionUtils::r).max(Float::compareTo).orElse(Float.valueOf(0.0f)).floatValue();
    }

    public static ItemStack f() {
        return EquipmentActionUtils.d().stream().filter(itemStack -> !itemStack.m_41619_() && itemStack.m_41720_() instanceof SwordItem).max(Comparator.comparingInt(itemStack -> (int)(EquipmentActionUtils.r(itemStack) * 100.0f))).orElse(null);
    }

    public static int j(ItemStack itemStack) {
        if (itemStack == null) {
            return -1;
        }
        for (int i2 = 0; i2 < EquipmentActionUtils.a.f_91074_.m_150109_().f_35974_.size(); ++i2) {
            if (EquipmentActionUtils.a.f_91074_.m_150109_().f_35974_.get(i2) != itemStack) continue;
            return i2;
        }
        return -1;
    }

    public static boolean k(ItemStack itemStack) {
        if (!itemStack.m_41619_()) {
            if (itemStack.m_41720_() instanceof PlayerHeadItem) {
                return false;
            }
            String string = itemStack.m_41611_().getString();
            if (string.contains("Click")) {
                return false;
            }
            if (string.contains("Right")) {
                return false;
            }
            if (string.contains("\u70b9\u51fb")) {
                return false;
            }
            if (string.contains("Teleport")) {
                return false;
            }
            if (string.contains("\u4f7f\u7528")) {
                return false;
            }
            if (string.contains("\u4f20\u9001")) {
                return false;
            }
            return !string.contains("\u518d\u6765");
        }
        return true;
    }

    public static int b(Item item) {
        for (int i2 = 0; i2 < EquipmentActionUtils.a.f_91074_.m_150109_().f_35974_.size(); ++i2) {
            ItemStack itemStack = (ItemStack)EquipmentActionUtils.a.f_91074_.m_150109_().f_35974_.get(i2);
            if (itemStack.m_41720_() != item) continue;
            return i2;
        }
        return -1;
    }

    public static ItemStack g() {
        return EquipmentActionUtils.d().stream().filter(itemStack -> !itemStack.m_41619_() && (itemStack.m_41720_() == Items.f_42521_ || itemStack.m_41720_() == Items.f_42452_) && EquipmentActionUtils.k(itemStack)).max(Comparator.comparingInt(ItemStack::m_41613_)).orElse(null);
    }

    public static ItemStack h() {
        return EquipmentActionUtils.d().stream().filter(itemStack -> !itemStack.m_41619_() && itemStack.m_41720_() instanceof FishingRodItem && EquipmentActionUtils.k(itemStack)).findAny().orElse(null);
    }

    public static int i() {
        return EquipmentActionUtils.d().stream().filter(itemStack -> !itemStack.m_41619_() && itemStack.m_41720_() instanceof BlockItem && Scaffold.a(itemStack) && EquipmentActionUtils.k(itemStack)).mapToInt(ItemStack::m_41613_).sum();
    }

    public static ItemStack j() {
        return EquipmentActionUtils.d().stream().filter(itemStack -> !itemStack.m_41619_() && (itemStack.m_41720_() == Items.f_42521_ || itemStack.m_41720_() == Items.f_42452_)).min(Comparator.comparingInt(ItemStack::m_41613_)).orElse(null);
    }

    public static ItemStack k() {
        return EquipmentActionUtils.d().stream().filter(itemStack -> !itemStack.m_41619_() && itemStack.m_41720_() instanceof ArrowItem && EquipmentActionUtils.k(itemStack)).min(Comparator.comparingInt(ItemStack::m_41613_)).orElse(null);
    }

    public static ItemStack l() {
        return EquipmentActionUtils.d().stream().filter(itemStack -> !itemStack.m_41619_() && itemStack.m_41720_() instanceof BlockItem && Scaffold.a(itemStack) && EquipmentActionUtils.k(itemStack)).min(Comparator.comparingInt(ItemStack::m_41613_)).orElse(null);
    }

    public static ItemStack m() {
        return EquipmentActionUtils.d().stream().filter(itemStack -> !itemStack.m_41619_() && itemStack.m_41720_() instanceof BlockItem && Scaffold.a(itemStack) && EquipmentActionUtils.k(itemStack)).max(Comparator.comparingInt(ItemStack::m_41613_)).orElse(null);
    }

    public static float n() {
        return EquipmentActionUtils.d().stream().filter(itemStack -> !itemStack.m_41619_() && itemStack.m_41720_() instanceof PickaxeItem && EquipmentActionUtils.k(itemStack)).map(EquipmentActionUtils::p).max(Float::compareTo).orElse(Float.valueOf(0.0f)).floatValue();
    }

    public static ItemStack o() {
        return EquipmentActionUtils.d().stream().filter(itemStack -> !itemStack.m_41619_() && itemStack.m_41720_() instanceof PickaxeItem && EquipmentActionUtils.k(itemStack)).max(Comparator.comparingInt(itemStack -> (int)(EquipmentActionUtils.p(itemStack) * 100.0f))).orElse(null);
    }

    public static float p() {
        return EquipmentActionUtils.d().stream().filter(itemStack -> !itemStack.m_41619_() && itemStack.m_41720_() instanceof AxeItem && !EquipmentActionUtils.b(itemStack) && EquipmentActionUtils.k(itemStack)).map(EquipmentActionUtils::p).max(Float::compareTo).orElse(Float.valueOf(0.0f)).floatValue();
    }

    public static ItemStack q() {
        return EquipmentActionUtils.d().stream().filter(itemStack -> !itemStack.m_41619_() && itemStack.m_41720_() instanceof AxeItem && !EquipmentActionUtils.b(itemStack) && EquipmentActionUtils.k(itemStack)).max(Comparator.comparingInt(itemStack -> (int)(EquipmentActionUtils.p(itemStack) * 100.0f))).orElse(null);
    }

    public static ItemStack r() {
        return EquipmentActionUtils.d().stream().filter(itemStack -> !itemStack.m_41619_() && itemStack.m_41720_() instanceof AxeItem && EquipmentActionUtils.b(itemStack) && EquipmentActionUtils.k(itemStack) && !EquipmentActionUtils.c(itemStack)).max(Comparator.comparingInt(itemStack -> (int)(EquipmentActionUtils.q(itemStack) * 100.0f))).orElse(null);
    }

    public static float s() {
        return EquipmentActionUtils.d().stream().filter(itemStack -> !itemStack.m_41619_() && itemStack.m_41720_() instanceof ShovelItem && EquipmentActionUtils.k(itemStack)).map(EquipmentActionUtils::p).max(Float::compareTo).orElse(Float.valueOf(0.0f)).floatValue();
    }

    public static ItemStack t() {
        return EquipmentActionUtils.d().stream().filter(itemStack -> !itemStack.m_41619_() && itemStack.m_41720_() instanceof ShovelItem && EquipmentActionUtils.k(itemStack)).max(Comparator.comparingInt(itemStack -> (int)(EquipmentActionUtils.p(itemStack) * 100.0f))).orElse(null);
    }

    public static float u() {
        return EquipmentActionUtils.d().stream().filter(itemStack -> !itemStack.m_41619_() && itemStack.m_41720_() instanceof CrossbowItem && EquipmentActionUtils.k(itemStack)).map(EquipmentActionUtils::t).max(Float::compareTo).orElse(Float.valueOf(0.0f)).floatValue();
    }

    public static ItemStack v() {
        return EquipmentActionUtils.d().stream().filter(itemStack -> !itemStack.m_41619_() && itemStack.m_41720_() instanceof CrossbowItem && EquipmentActionUtils.k(itemStack)).max(Comparator.comparingInt(itemStack -> (int)(EquipmentActionUtils.t(itemStack) * 100.0f))).orElse(null);
    }

    public static float w() {
        return EquipmentActionUtils.d().stream().filter(itemStack -> !itemStack.m_41619_() && itemStack.m_41720_() instanceof BowItem && EquipmentActionUtils.k(itemStack)).map(EquipmentActionUtils::n).max(Float::compareTo).orElse(Float.valueOf(0.0f)).floatValue();
    }

    public static ItemStack x() {
        return EquipmentActionUtils.d().stream().filter(itemStack -> !itemStack.m_41619_() && itemStack.m_41720_() instanceof BowItem && EquipmentActionUtils.k(itemStack)).max(Comparator.comparingInt(itemStack -> (int)(EquipmentActionUtils.n(itemStack) * 100.0f))).orElse(null);
    }

    public static float y() {
        return EquipmentActionUtils.d().stream().filter(itemStack -> !itemStack.m_41619_() && itemStack.m_41720_() instanceof BowItem && EquipmentActionUtils.k(itemStack)).map(EquipmentActionUtils::o).max(Float::compareTo).orElse(Float.valueOf(0.0f)).floatValue();
    }

    public static ItemStack z() {
        return EquipmentActionUtils.d().stream().filter(itemStack -> !itemStack.m_41619_() && itemStack.m_41720_() instanceof BowItem && EquipmentActionUtils.k(itemStack)).max(Comparator.comparingInt(itemStack -> (int)(EquipmentActionUtils.o(itemStack) * 100.0f))).orElse(null);
    }

    public static boolean l(ItemStack itemStack) {
        return EquipmentActionUtils.n(itemStack) > 10.0f && EquipmentActionUtils.k(itemStack);
    }

    public static boolean m(ItemStack itemStack) {
        return EquipmentActionUtils.o(itemStack) > 10.0f && EquipmentActionUtils.k(itemStack);
    }

    public static boolean c(Item item) {
        return EquipmentActionUtils.d().stream().anyMatch(itemStack -> !itemStack.m_41619_() && itemStack.m_41720_() == item);
    }

    public static int d(Item item) {
        return EquipmentActionUtils.d().stream().filter(itemStack -> !itemStack.m_41619_() && itemStack.m_41720_() == item).mapToInt(ItemStack::m_41613_).sum();
    }

    public static float n(ItemStack itemStack) {
        if (itemStack == null) {
            return 0.0f;
        }
        if (itemStack.m_41619_()) {
            return 0.0f;
        }
        if (itemStack.m_41720_() instanceof BowItem) {
            float f2 = 10.0f;
            f2 += (float)EnchantmentHelper.m_44843_((Enchantment)Enchantments.f_44989_, (ItemStack)itemStack);
            f2 += (float)EnchantmentHelper.m_44843_((Enchantment)Enchantments.f_44952_, (ItemStack)itemStack);
            f2 += (float)EnchantmentHelper.m_44843_((Enchantment)Enchantments.f_44990_, (ItemStack)itemStack);
            return (f2 += (float)EnchantmentHelper.m_44843_((Enchantment)Enchantments.f_44988_, (ItemStack)itemStack) / 10.0f) + (float)itemStack.m_41773_() / (float)itemStack.m_41776_();
        }
        return 0.0f;
    }

    public static float o(ItemStack itemStack) {
        if (itemStack == null) {
            return 0.0f;
        }
        if (itemStack.m_41619_()) {
            return 0.0f;
        }
        if (itemStack.m_41720_() instanceof BowItem) {
            float f2 = 10.0f;
            f2 += (float)EnchantmentHelper.m_44843_((Enchantment)Enchantments.f_44989_, (ItemStack)itemStack) / 10.0f;
            f2 += (float)EnchantmentHelper.m_44843_((Enchantment)Enchantments.f_44952_, (ItemStack)itemStack);
            f2 += (float)EnchantmentHelper.m_44843_((Enchantment)Enchantments.f_44990_, (ItemStack)itemStack);
            return (f2 += (float)EnchantmentHelper.m_44843_((Enchantment)Enchantments.f_44988_, (ItemStack)itemStack)) + (float)itemStack.m_41773_() / (float)itemStack.m_41776_();
        }
        return 0.0f;
    }

    public static float p(ItemStack itemStack) {
        float f2 = 0.0f;
        if (itemStack == null) {
            return 0.0f;
        }
        if (itemStack.m_41619_()) {
            return 0.0f;
        }
        if (EquipmentActionUtils.u(itemStack)) {
            return 0.0f;
        }
        if (EquipmentActionUtils.b(itemStack)) {
            return 0.0f;
        }
        if (itemStack.m_41720_() instanceof PickaxeItem) {
            f2 += itemStack.m_41691_(Blocks.f_50069_.m_49966_());
        } else if (itemStack.m_41720_() instanceof AxeItem) {
            f2 += itemStack.m_41691_(Blocks.f_49999_.m_49966_());
        } else {
            if (!(itemStack.m_41720_() instanceof ShovelItem)) {
                return 0.0f;
            }
            f2 += itemStack.m_41691_(Blocks.f_50493_.m_49966_());
        }
        int n2 = EnchantmentHelper.m_44843_((Enchantment)Enchantments.f_44984_, (ItemStack)itemStack);
        if (n2 > 0) {
            f2 += (float)n2 * 0.0075f;
        }
        return f2;
    }

    public static float q(ItemStack itemStack) {
        int n2;
        float f2 = 0.0f;
        if (itemStack == null) {
            return 0.0f;
        }
        if (itemStack.m_41619_()) {
            return 0.0f;
        }
        Item item = itemStack.m_41720_();
        if (item instanceof AxeItem) {
            AxeItem axeItem = (AxeItem)item;
            if (EquipmentActionUtils.b(itemStack)) {
                if (axeItem == Items.f_42423_) {
                    f2 += 4.0f;
                } else if (axeItem == Items.f_42428_) {
                    f2 += 5.0f;
                } else if (axeItem == Items.f_42386_) {
                    f2 += 6.0f;
                } else if (axeItem == Items.f_42433_) {
                    f2 += 4.0f;
                } else if (axeItem == Items.f_42391_) {
                    f2 += 7.0f;
                }
            }
        }
        if ((n2 = EnchantmentHelper.m_44843_((Enchantment)Enchantments.f_44977_, (ItemStack)itemStack)) > 0) {
            float f3 = Enchantments.f_44977_.m_7335_(n2, MobType.f_21640_);
            f2 += f3;
        }
        return f2;
    }

    public static float r(ItemStack itemStack) {
        int n2;
        float f2 = 0.0f;
        if (itemStack == null) {
            return 0.0f;
        }
        if (itemStack.m_41619_()) {
            return 0.0f;
        }
        Item item = itemStack.m_41720_();
        if (item instanceof SwordItem) {
            SwordItem swordItem = (SwordItem)item;
            f2 += swordItem.m_43299_() + 1.0f;
        }
        if ((n2 = EnchantmentHelper.m_44843_((Enchantment)Enchantments.f_44977_, (ItemStack)itemStack)) > 0) {
            float f3 = Enchantments.f_44977_.m_7335_(n2, MobType.f_21640_);
            f2 += f3;
        }
        return f2;
    }

    public static float s(ItemStack itemStack) {
        int n2 = 0;
        if (itemStack == null) {
            return 0.0f;
        }
        if (itemStack.m_41619_()) {
            return 0.0f;
        }
        Item item = itemStack.m_41720_();
        if (item instanceof ArmorItem) {
            ArmorItem armorItem = (ArmorItem)item;
            if ((item = armorItem.m_40401_()) == ArmorMaterials.LEATHER) {
                n2 += 100;
            } else if (item == ArmorMaterials.CHAIN) {
                n2 += 200;
            } else if (item == ArmorMaterials.IRON) {
                n2 += 400;
            } else if (item == ArmorMaterials.GOLD) {
                n2 += 300;
            } else if (item == ArmorMaterials.DIAMOND) {
                n2 += 500;
            } else if (item == ArmorMaterials.NETHERITE) {
                n2 += 600;
            }
        }
        return n2 += EnchantmentHelper.m_44843_((Enchantment)Enchantments.f_44965_, (ItemStack)itemStack);
    }

    public static float t(ItemStack itemStack) {
        int n2 = 0;
        if (itemStack == null) {
            return 0.0f;
        }
        if (itemStack.m_41619_()) {
            return 0.0f;
        }
        if (itemStack.m_41720_() instanceof CrossbowItem) {
            n2 += EnchantmentHelper.m_44843_((Enchantment)Enchantments.f_44960_, (ItemStack)itemStack);
            n2 += EnchantmentHelper.m_44843_((Enchantment)Enchantments.f_44959_, (ItemStack)itemStack);
            n2 += EnchantmentHelper.m_44843_((Enchantment)Enchantments.f_44961_, (ItemStack)itemStack);
        }
        return n2;
    }

    public static boolean u(ItemStack itemStack) {
        if (itemStack.m_41619_()) {
            return false;
        }
        if (itemStack.m_41720_() instanceof AxeItem && itemStack.m_41720_() == Items.f_42433_ && EnchantmentHelper.m_44843_((Enchantment)Enchantments.f_44977_, (ItemStack)itemStack) > 100) {
            return true;
        }
        if (itemStack.m_41720_() == Items.f_42518_ && EnchantmentHelper.m_44843_((Enchantment)Enchantments.f_44980_, (ItemStack)itemStack) > 1) {
            return true;
        }
        return itemStack.m_41720_() == Items.f_42747_ || itemStack.m_41720_() == Items.f_42729_;
    }

    public static boolean v(ItemStack itemStack) {
        if (itemStack.m_41619_()) {
            return true;
        }
        Item item = itemStack.m_41720_();
        if (item instanceof BlockItem) {
            BlockItem blockItem = (BlockItem)item;
            if (blockItem.m_40614_() == Blocks.f_50201_) {
                return false;
            }
            return blockItem.m_40614_() != Blocks.f_50033_;
        }
        if (item instanceof BookItem) {
            return false;
        }
        if (item instanceof ExperienceBottleItem) {
            return false;
        }
        if (item instanceof FireworkRocketItem) {
            return false;
        }
        if (item == Items.f_42404_ || item == Items.f_42733_ || item == Items.f_42578_ || item == Items.f_42577_) {
            return false;
        }
        return item != Items.f_42409_;
    }
}

