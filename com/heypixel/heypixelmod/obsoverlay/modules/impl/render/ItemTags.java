/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.render;

import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventRender2D;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventShader;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.ChestStealer;
import com.heypixel.heypixelmod.obsoverlay.utils.EntityDistanceUtils;
import com.heypixel.heypixelmod.obsoverlay.utils.EntityOverlayRenderer;
import com.heypixel.heypixelmod.obsoverlay.utils.ArgbColorPalette;
import com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils;
import com.heypixel.heypixelmod.obsoverlay.utils.renderer.MiSansFontRenderer;
import com.heypixel.heypixelmod.obsoverlay.utils.renderer.text.FontManager;
import com.mojang.blaze3d.vertex.PoseStack;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.EggItem;
import net.minecraft.world.item.EnchantedGoldenAppleItem;
import net.minecraft.world.item.EndCrystalItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SnowballItem;
import org.joml.Vector4f;

@ModuleInfo(a="ItemTags", b="\u7269\u54c1\u6807\u7b7e", c="Show item tags.", d=ModuleCategory.RENDER)
public class ItemTags
extends ModuleBase {
    private final int l = com.heypixel.heypixelmod.obsoverlay.utils.ArgbColorPalette.a(0, 0, 0, 40);
    private final ConcurrentHashMap<ItemEntity, com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.b> m = new ConcurrentHashMap();
    private final List<Vector4f> n = new ArrayList<Vector4f>();
    public com.heypixel.heypixelmod.obsoverlay.d.SettingModel.c c = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Scale").a(0.25f).d(0.01f).b(0.1f).c(0.5f).a().c();
    com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a d = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "All Items").a(false).a().b();
    com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a e = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "God Items").a(true).a(() -> !this.d.m()).a().b();
    com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a f = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Diamond").a(true).a(() -> !this.d.m()).a().b();
    com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a g = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Gold").a(true).a(() -> !this.d.m()).a().b();
    com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a h = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Iron").a(true).a(() -> !this.d.m()).a().b();
    com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a i = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Ender Pearl").a(true).a(() -> !this.d.m()).a().b();
    com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a j = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Golden Apple").a(true).a(() -> !this.d.m()).a().b();
    com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a k = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Useful Item").a(true).a(() -> !this.d.m()).a().b();

    private static String a(ItemEntity itemEntity) {
        ItemStack itemStack = itemEntity.m_32055_();
        return itemStack.m_41611_().getString() + " * " + itemStack.m_41613_();
    }

    private boolean a(ItemStack itemStack) {
        if (itemStack == null) {
            return false;
        }
        if (itemStack.m_41619_()) {
            return false;
        }
        if (this.d.m()) {
            return true;
        }
        if (this.e.m()) {
            if (EquipmentActionUtils.f(itemStack)) {
                return true;
            }
            if (itemStack.m_41720_() instanceof EnchantedGoldenAppleItem) {
                return true;
            }
            if (EquipmentActionUtils.c(itemStack)) {
                return true;
            }
        }
        if (this.f.m() && itemStack.m_41720_() == Items.f_42415_) {
            return true;
        }
        if (this.g.m() && itemStack.m_41720_() == Items.f_42417_) {
            return true;
        }
        if (this.h.m() && itemStack.m_41720_() == Items.f_42416_) {
            return true;
        }
        if (this.i.m() && itemStack.m_41720_() == Items.f_42584_) {
            return true;
        }
        if (this.j.m() && itemStack.m_41720_() == Items.f_42436_) {
            return true;
        }
        if (this.k.m()) {
            if (itemStack.m_41720_() instanceof BlockItem && itemStack.m_41613_() < 8) {
                return false;
            }
            if ((itemStack.m_41720_() instanceof SnowballItem || itemStack.m_41720_() instanceof EggItem) && itemStack.m_41613_() < 3) {
                return false;
            }
            return com.heypixel.heypixelmod.obsoverlay.modules.impl.b.ChestStealer.a(itemStack);
        }
        return false;
    }

    private boolean b(ItemStack itemStack) {
        if (EquipmentActionUtils.f(itemStack)) {
            return true;
        }
        if (itemStack.m_41720_() instanceof EnchantedGoldenAppleItem) {
            return true;
        }
        return itemStack.m_41720_() instanceof EndCrystalItem || EquipmentActionUtils.c(itemStack);
    }

    private void a(float f2) {
        this.m.clear();
        for (Entity entity : ItemTags.a.f_91073_.m_104735_()) {
            ItemEntity itemEntity;
            if (!(entity instanceof ItemEntity) || !this.a((itemEntity = (ItemEntity)entity).m_32055_())) continue;
            double d2 = ItemTags.a(f2, entity.f_19854_, entity.m_20185_());
            double d3 = ItemTags.a(f2, entity.f_19855_, entity.m_20186_()) + (double)entity.m_20206_() + 0.5;
            double d4 = ItemTags.a(f2, entity.f_19856_, entity.m_20189_());
            com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.b b2 = EntityDistanceUtils.a(d2, d3, d4, f2);
            b2.b(b2.b() - 2.0f);
            this.m.put(itemEntity, b2);
        }
    }

    @EventTarget
    public void update(com.heypixel.heypixelmod.obsoverlay.events.impl.WorldRenderEvent p2) {
        try {
            this.a(p2.a());
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    @EventTarget
    public void onShader(EventShader eventShader) {
        for (Vector4f vector4f : this.n) {
            EntityOverlayRenderer.a(eventShader.stack(), vector4f.x(), vector4f.y(), vector4f.z(), vector4f.w(), this.l);
        }
    }

    @EventTarget
    public void on2DRender(EventRender2D eventRender2D) {
        try {
            PoseStack poseStack = eventRender2D.stack();
            this.n.clear();
            for (ItemEntity itemEntity : this.m.keySet()) {
                if (itemEntity == null) continue;
                com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.b b2 = this.m.get(itemEntity);
                poseStack.m_85836_();
                FontManager c2 = com.heypixel.heypixelmod.obsoverlay.utils.renderer.MiSansFontRenderer.a;
                String string = ItemTags.a(itemEntity);
                float f2 = c2.a(string, (double)this.c.q()) + 8.0f;
                this.n.add(new Vector4f(b2.a - f2 / 2.0f, b2.b - 14.0f, b2.a + f2 / 2.0f, b2.b));
                if (this.b(itemEntity.m_32055_())) {
                    c2.a(poseStack, string, b2.a - f2 / 2.0f + 4.0f, b2.b - 12.0f, Color.RED, true, this.c.q());
                } else {
                    c2.a(poseStack, string, b2.a - f2 / 2.0f + 4.0f, b2.b - 12.0f, Color.WHITE, true, this.c.q());
                }
                poseStack.m_85849_();
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static float a(float f2, float f3, float f4) {
        return f3 + f2 * (f4 - f3);
    }

    public static double a(double d2, double d3, float f2) {
        return d3 + d2 * ((double)f2 - d3);
    }

    public static double a(float f2, double d2, double d3) {
        return d2 + (double)f2 * (d3 - d2);
    }
}

