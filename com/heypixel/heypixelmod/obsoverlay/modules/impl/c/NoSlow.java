/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.c;

import com.heypixel.heypixelmod.obsoverlay.d.a.ModeSetting;
import com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.LocalPlayerTickPreEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.ItemUseSlowdownEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

@ModuleInfo(a="NoSlow", b="\u65e0\u51cf\u901f", c="NoSlowDown", d=ModuleCategory.MOVEMENT)
public class NoSlow
extends ModuleBase {
    public Fly c = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Mode").a(0).a("None", "Heypixel 2/3", "Grim50%", "Grim 1/3", "Jump").a().e();
    public com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a d = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Food").a(true).a().b();
    public com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a e = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Bow").a(true).a().b();
    public com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a f = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Crossbow").a(true).a().b();
    private int g = 0;

    @EventTarget
    public void onSlow(ItemUseSlowdownEvent z2) {
        if (NoSlow.a.f_91074_ == null || this.p() && NoSlow.a.f_91074_.m_21212_() > 30) {
            return;
        }
        if (!this.d.m() && this.p()) {
            return;
        }
        if (!this.e.m() && this.a(Items.f_42411_)) {
            return;
        }
        if (!this.f.m() && this.a(Items.f_42717_)) {
            return;
        }
        switch (this.c.l()) {
            case "Jump": {
                this.a(z2);
                break;
            }
            case "Grim50%": {
                this.b(z2);
                break;
            }
            case "None": {
                this.c(z2);
                break;
            }
            case "Grim 1/3": {
                this.d(z2);
                break;
            }
            case "Heypixel 2/3": {
                this.e(z2);
            }
        }
    }

    private void a(ItemUseSlowdownEvent z2) {
        if (this.g == 1 && NoSlow.a.f_91074_.m_21212_() <= 30) {
            z2.a(false);
            if (!NoSlow.a.f_91074_.m_20142_()) {
                NoSlow.a.f_91074_.m_6858_(true);
            }
        }
    }

    private void b(ItemUseSlowdownEvent z2) {
        if (NoSlow.a.f_91074_.m_21212_() % 2 == 0 && NoSlow.a.f_91074_.m_21212_() <= 30) {
            z2.a(false);
            if (!NoSlow.a.f_91074_.m_20142_()) {
                NoSlow.a.f_91074_.m_6858_(true);
            }
        }
    }

    private void c(ItemUseSlowdownEvent z2) {
        z2.a(false);
        if (!NoSlow.a.f_91074_.m_20142_()) {
            NoSlow.a.f_91074_.m_6858_(true);
        }
    }

    private void d(ItemUseSlowdownEvent z2) {
        if (!(NoSlow.a.f_91074_.m_21212_() % 3 != 0 || this.p() && NoSlow.a.f_91074_.m_21212_() > 30)) {
            z2.a(false);
            if (!NoSlow.a.f_91074_.m_20142_()) {
                NoSlow.a.f_91074_.m_6858_(true);
            }
        }
    }

    private void e(ItemUseSlowdownEvent z2) {
        if (!(NoSlow.a.f_91074_.m_21212_() % 3 == 0 || this.p() && NoSlow.a.f_91074_.m_21212_() > 30)) {
            z2.a(false);
            if (!NoSlow.a.f_91074_.m_20142_()) {
                NoSlow.a.f_91074_.m_6858_(true);
            }
        }
    }

    @EventTarget
    public void onUpdate(LocalPlayerTickPreEvent ad2) {
        this.a(this.c.l());
        this.g = NoSlow.a.f_91074_.m_20096_() ? ++this.g : 0;
    }

    @Override
    public void d() {
        this.g = 0;
    }

    @Override
    public void e() {
        this.g = 0;
    }

    private boolean p() {
        ItemStack itemStack = NoSlow.a.f_91074_.m_21205_();
        ItemStack itemStack2 = NoSlow.a.f_91074_.m_21206_();
        return itemStack.m_150930_(Items.f_42436_) || itemStack2.m_150930_(Items.f_42436_) || itemStack.m_150930_(Items.f_42437_) || itemStack2.m_150930_(Items.f_42437_) || itemStack.m_150930_(Items.f_42589_) || itemStack2.m_150930_(Items.f_42589_);
    }

    private boolean a(Item item) {
        ItemStack itemStack = NoSlow.a.f_91074_.m_21205_();
        ItemStack itemStack2 = NoSlow.a.f_91074_.m_21206_();
        return itemStack.m_150930_(item) || itemStack2.m_150930_(item);
    }

    @EventTarget
    public void onMoveInput(com.heypixel.heypixelmod.obsoverlay.events.impl.MovementInputEvent l2) {
        if (NoSlow.a.f_91074_.m_20096_() && NoSlow.a.f_91074_.m_6117_() && (l2.a() != 0.0f || l2.b() != 0.0f) && this.c.a("Jump")) {
            l2.a(true);
        }
    }
}

