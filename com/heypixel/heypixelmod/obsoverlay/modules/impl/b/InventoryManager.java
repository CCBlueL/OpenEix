/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.b;

import com.heypixel.heypixelmod.obsoverlay.EixClientCore;
import com.heypixel.heypixelmod.obsoverlay.d.a.NumericSetting;
import com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerMotionEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PacketInterceptEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.ChestStealer;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Scaffold;
import com.heypixel.heypixelmod.obsoverlay.utils.EntityTrackingList;
import com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils;
import com.heypixel.heypixelmod.obsoverlay.utils.MathUtils;
import com.heypixel.heypixelmod.obsoverlay.utils.PlayerRotationUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerboundContainerClosePacket;
import net.minecraft.network.protocol.game.ServerboundInteractPacket;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemOnPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemPacket;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.AxeItem;
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
import org.apache.commons.lang3.tuple.Pair;

@ModuleInfo(a="InventoryManager", b="\u80cc\u5305\u6574\u7406", c="Automatically manage your inventory", d=com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory.MISC)
public class InventoryManager
extends ModuleBase {
    private static final EntityTrackingList K = new EntityTrackingList();
    private final AutoSoup L = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Min Delay (Ticks)").a(2.0f).d(1.0f).b(0.0f).c(10.0f).a().c();
    private final AutoSoup M = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Max Delay (Ticks)").a(6.0f).d(1.0f).b(0.0f).c(10.0f).a().c();
    com.heypixel.heypixelmod.obsoverlay.d.SettingModel.e c = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Offhand Items").a("None", "Golden Apple", "Projectile", "Fishing Rod", "Block").a().e();
    com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a d = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Auto Armor").a(true).a().b();
    com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a e = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Inventory Only").a(true).a().b();
    com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a f = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Switch Sword").a(true).a().b();
    AutoSoup g = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Sword Slot").a(1.0f).d(1.0f).b(1.0f).c(9.0f).a(() -> this.f.m()).a().c();
    com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a h = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Switch Block").a(() -> !this.c.a("Block")).a(true).a().b();
    AutoSoup i = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Block Slot").a(2.0f).d(1.0f).b(1.0f).c(9.0f).a(() -> this.h.m() && !this.c.a("Block")).a().c();
    AutoSoup j = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Max Block Size").a(256.0f).d(64.0f).b(64.0f).c(512.0f).a(() -> this.h.m()).a().c();
    com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a k = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Switch Pickaxe").a(true).a().b();
    AutoSoup l = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Pickaxe Slot").a(3.0f).d(1.0f).b(1.0f).c(9.0f).a(() -> this.k.m()).a().c();
    com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a m = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Switch Axe").a(true).a().b();
    AutoSoup n = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Axe Slot").a(4.0f).d(1.0f).b(1.0f).c(9.0f).a(() -> this.m.m()).a().c();
    com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a o = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Switch Bow or Crossbow").a(true).a().b();
    AutoSoup p = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Bow Slot").a(5.0f).d(1.0f).b(1.0f).c(9.0f).a(() -> this.o.m()).a().c();
    com.heypixel.heypixelmod.obsoverlay.d.SettingModel.e q = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Bow Priority").a("Crossbow", "Power Bow", "Punch Bow").a(() -> this.o.m()).a().e();
    AutoSoup r = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Max Arrow Size").a(256.0f).d(64.0f).b(64.0f).c(512.0f).a(() -> this.o.m()).a().c();
    com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a s = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Switch Water Bucket").a(true).a().b();
    AutoSoup t = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Water Bucket Slot").a(6.0f).d(1.0f).b(1.0f).c(9.0f).a(() -> this.s.m()).a().c();
    com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a u = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Switch Ender Pearl").a(true).a().b();
    AutoSoup v = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Ender Pearl Slot").a(7.0f).d(1.0f).b(1.0f).c(9.0f).a(() -> this.u.m()).a().c();
    com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a w = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Switch Fireball").a(true).a().b();
    AutoSoup x = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Fireball Slot").a(8.0f).d(1.0f).b(1.0f).c(9.0f).a(() -> this.w.m()).a().c();
    com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a y = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Switch Golden Apple").a(() -> !this.c.a("Golden Apple")).a(true).a().b();
    AutoSoup z = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Golden Apple Slot").a(9.0f).d(1.0f).b(1.0f).c(9.0f).a(() -> this.y.m() && !this.c.a("Golden Apple")).a().c();
    com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a A = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Throw Items").a(true).a().b();
    AutoSoup B = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Keep Water Buckets").a(1.0f).d(1.0f).b(0.0f).c(5.0f).a(() -> this.A.m()).a().c();
    AutoSoup C = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Keep Lava Buckets").a(1.0f).d(1.0f).b(0.0f).c(5.0f).a(() -> this.A.m()).a().c();
    com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a D = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Keep Eggs & Snowballs").a(true).a().b();
    com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a E = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Switch Eggs & Snowballs").a(false).a(() -> this.D.m() && !this.c.a("Projectile")).a().b();
    AutoSoup F = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Eggs & Snowballs Slot").a(9.0f).d(1.0f).b(1.0f).c(9.0f).a(() -> this.E.m() && this.D.m() && !this.c.a("Projectile")).a().c();
    AutoSoup G = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Max Eggs & Snowballs Size").a(64.0f).d(16.0f).b(16.0f).c(256.0f).a(() -> this.D.m()).a().c();
    com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a H = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Switch Rod").a(() -> !this.c.a("Fishing Rod")).a(false).a().b();
    AutoSoup I = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Rod Slot").a(9.0f).d(1.0f).b(1.0f).c(9.0f).a(() -> this.H.m() && !this.c.a("Fishing Rod")).a().c();
    int J = 0;
    private boolean N = false;
    private boolean O = false;

    public static int p() {
        return (int)com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a(InventoryManager.class).j.q();
    }

    public static boolean q() {
        return com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a(InventoryManager.class).D.m();
    }

    public static int r() {
        return (int)com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a(InventoryManager.class).G.q();
    }

    public static int s() {
        return (int)com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a(InventoryManager.class).r.q();
    }

    public static int t() {
        return (int)com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a(InventoryManager.class).B.q();
    }

    public static int u() {
        return (int)com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a(InventoryManager.class).C.q();
    }

    public boolean a(ItemStack itemStack) {
        if (itemStack.m_41619_()) {
            return false;
        }
        if (com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.u(itemStack)) {
            return true;
        }
        if (itemStack.m_41611_().getString().contains("\u70b9\u51fb\u4f7f\u7528")) {
            return true;
        }
        Item item = itemStack.m_41720_();
        if (item instanceof ArmorItem) {
            ArmorItem armorItem = (ArmorItem)item;
            float f2 = com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.s(itemStack);
            if (com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.b(armorItem.m_40402_()) >= f2) {
                return false;
            }
            float f3 = com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.a(armorItem.m_40402_());
            return !(f2 < f3);
        }
        if (itemStack.m_41720_() instanceof SwordItem) {
            return com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.f() == itemStack;
        }
        if (itemStack.m_41720_() instanceof PickaxeItem) {
            return com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.o() == itemStack;
        }
        if (itemStack.m_41720_() instanceof AxeItem && !com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.b(itemStack)) {
            return com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.q() == itemStack;
        }
        if (itemStack.m_41720_() instanceof ShovelItem) {
            return com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.t() == itemStack;
        }
        if (itemStack.m_41720_() instanceof CrossbowItem) {
            return com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.v() == itemStack;
        }
        if (itemStack.m_41720_() instanceof BowItem && com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.l(itemStack)) {
            return com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.x() == itemStack;
        }
        if (itemStack.m_41720_() instanceof BowItem && com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.m(itemStack)) {
            return com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.z() == itemStack;
        }
        if (itemStack.m_41720_() instanceof BowItem && com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.d(Items.f_42411_) > 1) {
            return false;
        }
        if (itemStack.m_41720_() == Items.f_42447_ && com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.d(Items.f_42447_) > com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.t()) {
            return false;
        }
        if (itemStack.m_41720_() == Items.f_42448_ && com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.d(Items.f_42448_) > com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.u()) {
            return false;
        }
        if (itemStack.m_41720_() instanceof FishingRodItem && com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.d(Items.f_42523_) > 1) {
            return false;
        }
        if (!(itemStack.m_41720_() != Items.f_42452_ && itemStack.m_41720_() != Items.f_42521_ || com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.q())) {
            return false;
        }
        return !(itemStack.m_41720_() instanceof ItemNameBlockItem) && com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.v(itemStack);
    }

    @EventTarget
    public void onPacket(PacketInterceptEvent m2) {
        if (m2.b() == com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.c) {
            if (m2.c() instanceof ServerboundContainerClosePacket) {
                this.O = false;
            }
            if (this.O && !this.e.m()) {
                if (m2.c() instanceof ServerboundMovePlayerPacket) {
                    if (com.heypixel.heypixelmod.obsoverlay.utils.PlayerRotationUtils.a()) {
                        a.m_91403_().m_104955_((Packet)new ServerboundContainerClosePacket(com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91074_.f_36095_.f_38840_));
                    }
                } else if (m2.c() instanceof ServerboundUseItemOnPacket || m2.c() instanceof ServerboundUseItemPacket || m2.c() instanceof ServerboundInteractPacket || m2.c() instanceof ServerboundPlayerActionPacket) {
                    a.m_91403_().m_104955_((Packet)new ServerboundContainerClosePacket(com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91074_.f_36095_.f_38840_));
                }
            }
        }
    }

    private boolean v() {
        ArrayList<Pair> arrayList = new ArrayList<Pair>();
        if (!this.D.m()) {
            this.E.a(false);
        }
        arrayList.add(Pair.of((Object)this.f, (Object)this.g));
        arrayList.add(Pair.of((Object)this.k, (Object)this.l));
        arrayList.add(Pair.of((Object)this.m, (Object)this.n));
        arrayList.add(Pair.of((Object)this.o, (Object)this.p));
        arrayList.add(Pair.of((Object)this.s, (Object)this.t));
        arrayList.add(Pair.of((Object)this.u, (Object)this.v));
        arrayList.add(Pair.of((Object)this.w, (Object)this.x));
        if (!this.c.a("Golden Apple")) {
            arrayList.add(Pair.of((Object)this.y, (Object)this.z));
        }
        if (!this.c.a("Projectile")) {
            arrayList.add(Pair.of((Object)this.E, (Object)this.F));
        }
        if (!this.c.a("Fishing Rod")) {
            arrayList.add(Pair.of((Object)this.H, (Object)this.I));
        }
        if (!this.c.a("Block")) {
            arrayList.add(Pair.of((Object)this.h, (Object)this.i));
        }
        HashSet<Integer> hashSet = new HashSet<Integer>();
        for (Pair pair : arrayList) {
            if (!((com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a)pair.getKey()).m()) continue;
            int n2 = (int)(((AutoSoup)pair.getValue()).q() - 1.0f);
            if (hashSet.contains(n2)) {
                return false;
            }
            hashSet.add(n2);
        }
        return true;
    }

    @EventTarget
    public void onMotion(PlayerMotionEvent k2) {
        if (k2.b() == com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.a) {
            ArmorItem armorItem;
            if (!(com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91080_ instanceof com.heypixel.heypixelmod.obsoverlay.c.MainMenuScreen) && !this.v()) {
                com.heypixel.heypixelmod.obsoverlay.c.MainMenuScreen.a a2 = new com.heypixel.heypixelmod.obsoverlay.c.MainMenuScreen.a(com.heypixel.heypixelmod.obsoverlay.c.MainMenuScreen.b.d, "Duplicate slot config in Inventory Manager! Please check your config!", 8000L);
                com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().j().a(a2);
                this.f();
                return;
            }
            if (com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.a()) {
                return;
            }
            this.J = com.heypixel.heypixelmod.obsoverlay.utils.PlayerRotationUtils.a() ? 0 : ++this.J;
            if (com.heypixel.heypixelmod.obsoverlay.modules.impl.b.ChestStealer.p() || com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a(Scaffold.class).m() || (this.e.m() ? !(com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91080_ instanceof InventoryScreen) : this.J <= 1)) {
                this.N = false;
                return;
            }
            Screen screen = com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91080_;
            if (screen instanceof AbstractContainerScreen) {
                AbstractContainerScreen abstractContainerScreen = (AbstractContainerScreen)screen;
                if (abstractContainerScreen.m_6262_().f_38840_ != com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91074_.f_36095_.f_38840_) {
                    return;
                }
            }
            if (this.d.m()) {
                int n2;
                for (n2 = 0; n2 < com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91074_.m_150109_().f_35975_.size(); ++n2) {
                    screen = (ItemStack)com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91074_.m_150109_().f_35975_.get(n2);
                    Item item = screen.m_41720_();
                    if (!(item instanceof ArmorItem)) continue;
                    armorItem = (ArmorItem)item;
                    if (screen.m_41619_() || !K.a(com.heypixel.heypixelmod.obsoverlay.utils.MathUtils.b((int)this.L.q(), (int)this.M.q())) || !(com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.a(armorItem.m_40402_()) > com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.s((ItemStack)screen))) continue;
                    com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91072_.m_171799_(com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91074_.f_36095_.f_38840_, 4 + (4 - n2), 1, ClickType.THROW, (Player)com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91074_);
                    this.O = true;
                    K.b();
                }
                for (n2 = 0; n2 < com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91074_.m_150109_().f_35974_.size(); ++n2) {
                    boolean bl;
                    Item item;
                    screen = (ItemStack)com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91074_.m_150109_().f_35974_.get(n2);
                    if (screen.m_41619_() || !((item = screen.m_41720_()) instanceof ArmorItem)) continue;
                    armorItem = (ArmorItem)item;
                    float f2 = com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.s((ItemStack)screen);
                    boolean bl2 = com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.a(armorItem.m_40402_()) == f2;
                    boolean bl3 = bl = com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.b(armorItem.m_40402_()) < f2;
                    if (!bl2 || !bl || !K.a(com.heypixel.heypixelmod.obsoverlay.utils.MathUtils.b((int)this.L.q(), (int)this.M.q()))) continue;
                    if (n2 < 9) {
                        com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91072_.m_171799_(com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91074_.f_36095_.f_38840_, n2 + 36, 0, ClickType.QUICK_MOVE, (Player)com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91074_);
                    } else {
                        com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91072_.m_171799_(com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91074_.f_36095_.f_38840_, n2, 0, ClickType.QUICK_MOVE, (Player)com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91074_);
                    }
                    this.O = true;
                    K.b();
                }
            }
            if (this.N && K.a(com.heypixel.heypixelmod.obsoverlay.utils.MathUtils.b((int)this.L.q(), (int)this.M.q()))) {
                com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91072_.m_171799_(com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91074_.f_36095_.f_38840_, 45, 0, ClickType.PICKUP, (Player)com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91074_);
                this.O = true;
                this.N = false;
                K.b();
            }
            if (this.c.a("Golden Apple")) {
                ItemStack itemStack = (ItemStack)com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91074_.m_150109_().f_35976_.get(0);
                int n3 = com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.b(Items.f_42436_);
                if (n3 != -1 && K.a(com.heypixel.heypixelmod.obsoverlay.utils.MathUtils.b((int)this.L.q(), (int)this.M.q()))) {
                    if (itemStack.m_41720_() == Items.f_42436_) {
                        armorItem = (ItemStack)com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91074_.m_150109_().f_35974_.get(n3);
                        if (itemStack.m_41613_() + armorItem.m_41613_() <= 64) {
                            if (n3 < 9) {
                                com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91072_.m_171799_(com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91074_.f_36095_.f_38840_, n3 + 36, 0, ClickType.PICKUP, (Player)com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91074_);
                            } else {
                                com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91072_.m_171799_(com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91074_.f_36095_.f_38840_, n3, 0, ClickType.PICKUP, (Player)com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91074_);
                            }
                            this.O = true;
                            this.N = true;
                            K.b();
                        }
                    } else {
                        this.c(n3);
                    }
                }
            } else if (this.c.a("Projectile")) {
                ItemStack itemStack = (ItemStack)com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91074_.m_150109_().f_35976_.get(0);
                screen = com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.g();
                if (screen != null) {
                    int n4 = com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.j((ItemStack)screen);
                    boolean bl = false;
                    if (itemStack.m_41720_() != Items.f_42521_ && itemStack.m_41720_() != Items.f_42452_) {
                        bl = true;
                    } else if (itemStack.m_41613_() < screen.m_41613_()) {
                        bl = true;
                    }
                    if (bl && n4 != -1 && K.a(com.heypixel.heypixelmod.obsoverlay.utils.MathUtils.b((int)this.L.q(), (int)this.M.q()))) {
                        this.c(n4);
                    }
                }
            } else if (this.c.a("Fishing Rod")) {
                ItemStack itemStack = (ItemStack)com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91074_.m_150109_().f_35976_.get(0);
                int n5 = com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.b(Items.f_42523_);
                if (n5 != -1 && K.a(com.heypixel.heypixelmod.obsoverlay.utils.MathUtils.b((int)this.L.q(), (int)this.M.q())) && itemStack.m_41720_() != Items.f_42523_) {
                    this.c(n5);
                }
            } else if (this.c.a("Block")) {
                ItemStack itemStack = (ItemStack)com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91074_.m_150109_().f_35976_.get(0);
                screen = com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.m();
                if (screen != null) {
                    int n6 = com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.j((ItemStack)screen);
                    boolean bl = false;
                    if (com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Scaffold.a(itemStack)) {
                        if (itemStack.m_41613_() < screen.m_41613_()) {
                            bl = true;
                        }
                    } else {
                        bl = true;
                    }
                    if (bl && n6 != -1 && K.a(com.heypixel.heypixelmod.obsoverlay.utils.MathUtils.b((int)this.L.q(), (int)this.M.q()))) {
                        this.c(n6);
                    }
                }
            }
            if (this.y.m() && !this.c.a("Golden Apple")) {
                this.a((int)(this.z.q() - 1.0f), Items.f_42436_);
            }
            if (this.h.m()) {
                int n7 = (int)(this.i.q() - 1.0f);
                ItemStack itemStack = (ItemStack)com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91074_.m_150109_().f_35974_.get(n7);
                ItemStack itemStack2 = com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.m();
                if (!(itemStack2 == null || itemStack2.m_41613_() <= itemStack.m_41613_() && com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Scaffold.a(itemStack) || this.c.a("Block"))) {
                    this.a(n7, itemStack2);
                }
                if ((float)com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.i() > this.j.q()) {
                    ItemStack itemStack3 = com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.l();
                    this.b(itemStack3);
                }
            }
            if (this.f.m()) {
                int n8 = (int)(this.g.q() - 1.0f);
                ItemStack itemStack = (ItemStack)com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91074_.m_150109_().f_35974_.get(n8);
                ItemStack itemStack4 = com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.f();
                ItemStack itemStack5 = com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.r();
                if (com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.q(itemStack5) > com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.r(itemStack4)) {
                    itemStack4 = itemStack5;
                }
                if (itemStack4 != null) {
                    float f3;
                    float f4 = itemStack.m_41720_() instanceof SwordItem ? com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.r(itemStack) : com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.q(itemStack);
                    float f5 = f3 = itemStack4.m_41720_() instanceof SwordItem ? com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.r(itemStack4) : com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.q(itemStack4);
                    if (f3 > f4) {
                        this.a(n8, itemStack4);
                    }
                }
            }
            if (this.k.m()) {
                int n9 = (int)(this.l.q() - 1.0f);
                ItemStack itemStack = com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.o();
                ItemStack itemStack6 = (ItemStack)com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91074_.m_150109_().f_35974_.get(n9);
                if (itemStack != null && itemStack.m_41720_() instanceof PickaxeItem && (com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.p(itemStack) > com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.p(itemStack6) || !(itemStack6.m_41720_() instanceof PickaxeItem))) {
                    this.a(n9, itemStack);
                }
            }
            if (this.m.m()) {
                int n10 = (int)(this.n.q() - 1.0f);
                ItemStack itemStack = com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.q();
                ItemStack itemStack7 = (ItemStack)com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91074_.m_150109_().f_35974_.get(n10);
                if (itemStack != null && itemStack.m_41720_() instanceof AxeItem && (com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.p(itemStack) > com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.p(itemStack7) || !(itemStack7.m_41720_() instanceof AxeItem))) {
                    this.a(n10, itemStack);
                }
            }
            if (this.H.m() && !this.c.a("Fishing Rod")) {
                int n11 = (int)(this.I.q() - 1.0f);
                ItemStack itemStack = com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.h();
                ItemStack itemStack8 = (ItemStack)com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91074_.m_150109_().f_35974_.get(n11);
                if (!(itemStack8.m_41720_() instanceof FishingRodItem)) {
                    this.a(n11, itemStack);
                }
            }
            if (this.o.m()) {
                float f6;
                float f7;
                ItemStack itemStack;
                int n12 = (int)(this.p.q() - 1.0f);
                ItemStack itemStack9 = (ItemStack)com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91074_.m_150109_().f_35974_.get(n12);
                if (this.q.a("Crossbow")) {
                    itemStack = com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.v();
                    f7 = com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.t(itemStack);
                    f6 = com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.t(itemStack9);
                } else if (this.q.a("Power Bow")) {
                    itemStack = com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.z();
                    f7 = com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.o(itemStack);
                    f6 = com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.o(itemStack9);
                } else {
                    itemStack = com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.x();
                    f7 = com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.n(itemStack);
                    f6 = com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.n(itemStack9);
                }
                if (itemStack == null) {
                    itemStack = com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.v();
                    f7 = com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.t(itemStack);
                    f6 = com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.t(itemStack9);
                }
                if (itemStack == null) {
                    itemStack = com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.z();
                    f7 = com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.o(itemStack);
                    f6 = com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.o(itemStack9);
                }
                if (itemStack == null) {
                    itemStack = com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.x();
                    f7 = com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.n(itemStack);
                    f6 = com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.n(itemStack9);
                }
                if (itemStack != null && f7 > f6) {
                    this.a(n12, itemStack);
                }
                if ((float)com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.d(Items.f_42412_) > this.r.q()) {
                    ItemStack itemStack10 = com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.k();
                    this.b(itemStack10);
                }
            }
            if (this.u.m()) {
                this.a((int)(this.v.q() - 1.0f), Items.f_42584_);
            }
            if (this.s.m()) {
                this.a((int)(this.t.q() - 1.0f), Items.f_42447_);
            }
            if (this.w.m()) {
                this.a((int)(this.x.q() - 1.0f), Items.f_42613_);
            }
            if (this.D.m()) {
                if ((float)(com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.d(Items.f_42521_) + com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.d(Items.f_42452_)) > this.G.q()) {
                    ItemStack itemStack = com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.j();
                    this.b(itemStack);
                }
                if (this.E.m() && !this.c.a("Projectile")) {
                    int n13 = (int)(this.F.q() - 1.0f);
                    if (com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.d(Items.f_42521_) > 0) {
                        this.a(n13, Items.f_42521_);
                    } else if (com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.d(Items.f_42452_) > 0) {
                        this.a(n13, Items.f_42452_);
                    }
                }
            }
            if (this.A.m()) {
                List list = IntStream.range(0, com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91074_.m_150109_().f_35974_.size()).boxed().collect(Collectors.toList());
                Collections.shuffle(list);
                for (Integer n14 : list) {
                    ItemStack itemStack = (ItemStack)com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91074_.m_150109_().f_35974_.get(n14.intValue());
                    if (itemStack.m_41619_() || this.a(itemStack)) continue;
                    this.b(itemStack);
                }
            }
        }
    }

    private void c(int n2) {
        if (n2 < 9) {
            com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91072_.m_171799_(com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91074_.f_36095_.f_38840_, n2 + 36, 40, ClickType.SWAP, (Player)com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91074_);
        } else {
            com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91072_.m_171799_(com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91074_.f_36095_.f_38840_, n2, 40, ClickType.SWAP, (Player)com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91074_);
        }
        this.O = true;
        K.b();
    }

    private void b(ItemStack itemStack) {
        int n2;
        if (com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.k(itemStack) && K.a(com.heypixel.heypixelmod.obsoverlay.utils.MathUtils.b((int)this.L.q(), (int)this.M.q())) && (n2 = com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.j(itemStack)) != -1) {
            if (n2 < 9) {
                com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91072_.m_171799_(com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91074_.f_36095_.f_38840_, n2 + 36, 1, ClickType.THROW, (Player)com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91074_);
            } else {
                com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91072_.m_171799_(com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91074_.f_36095_.f_38840_, n2, 1, ClickType.THROW, (Player)com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91074_);
            }
            this.O = true;
            K.b();
        }
    }

    private void a(int n2, ItemStack itemStack) {
        int n3;
        ItemStack itemStack2 = (ItemStack)com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91074_.m_150109_().f_35974_.get(n2);
        if (com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.k(itemStack2) && itemStack != itemStack2 && K.a(com.heypixel.heypixelmod.obsoverlay.utils.MathUtils.b((int)this.L.q(), (int)this.M.q())) && (n3 = com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.j(itemStack)) != -1) {
            if (n3 < 9) {
                com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91072_.m_171799_(com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91074_.f_36095_.f_38840_, n3 + 36, n2, ClickType.SWAP, (Player)com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91074_);
            } else {
                com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91072_.m_171799_(com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91074_.f_36095_.f_38840_, n3, n2, ClickType.SWAP, (Player)com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91074_);
            }
            this.O = true;
            K.b();
        }
    }

    private void a(int n2, Item item) {
        int n3;
        ItemStack itemStack = (ItemStack)com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91074_.m_150109_().f_35974_.get(n2);
        if (com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.k(itemStack) && K.a(com.heypixel.heypixelmod.obsoverlay.utils.MathUtils.b((int)this.L.q(), (int)this.M.q())) && (n3 = com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.b(item)) != -1) {
            ItemStack itemStack2 = (ItemStack)com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91074_.m_150109_().f_35974_.get(n3);
            if (itemStack.m_41720_() != item || itemStack.m_41720_() == item && itemStack.m_41613_() < itemStack2.m_41613_()) {
                if (n3 < 9) {
                    com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91072_.m_171799_(com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91074_.f_36095_.f_38840_, n3 + 36, n2, ClickType.SWAP, (Player)com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91074_);
                } else {
                    com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91072_.m_171799_(com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91074_.f_36095_.f_38840_, n3, n2, ClickType.SWAP, (Player)com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.a.f_91074_);
                }
                this.O = true;
                K.b();
            }
        }
    }
}

