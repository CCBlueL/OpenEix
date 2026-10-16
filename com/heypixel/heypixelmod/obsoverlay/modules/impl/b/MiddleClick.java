/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.b;

import com.heypixel.heypixelmod.obsoverlay.EixClientCore;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventMouseClick;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventRunTicks;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;
import com.heypixel.heypixelmod.obsoverlay.utils.EntityNameFilter;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.EntityHitResult;

@ModuleInfo(a="Middle Click", b="\u4e2d\u952e\u70b9\u51fb", c="", d=com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory.MISC)
public class MiddleClick
extends ModuleBase {
    private boolean c;
    private boolean d;
    private int e;

    @EventTarget
    public void onPreTick(EventRunTicks eventRunTicks) {
        if (eventRunTicks.type() != com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.a) {
            return;
        }
        if (this.c) {
            EntityHitResult entityHitResult;
            this.c = false;
            Object object = MiddleClick.a.f_91077_;
            if (object instanceof EntityHitResult && (object = (entityHitResult = (EntityHitResult)object).m_82443_()) instanceof Player) {
                Player player = (Player)object;
                if (EntityNameFilter.a((Entity)player)) {
                    object = new com.heypixel.heypixelmod.obsoverlay.c.MainMenuScreen.a(com.heypixel.heypixelmod.obsoverlay.c.MainMenuScreen.b.d, "Removed " + player.m_7755_().getString() + " from friends!", 3000L);
                    com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().j().a((com.heypixel.heypixelmod.obsoverlay.c.MainMenuScreen.a)object);
                    EntityNameFilter.b(player);
                } else {
                    object = new com.heypixel.heypixelmod.obsoverlay.c.MainMenuScreen.a(com.heypixel.heypixelmod.obsoverlay.c.MainMenuScreen.b.a, "Added " + player.m_7755_().getString() + " as friends!", 3000L);
                    com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().j().a((com.heypixel.heypixelmod.obsoverlay.c.MainMenuScreen.a)object);
                    EntityNameFilter.a(player);
                }
            } else {
                this.e = MiddleClick.a.f_91074_.m_150109_().f_35977_;
                int n2 = -1;
                for (int i2 = 0; i2 < 9; ++i2) {
                    ItemStack itemStack = MiddleClick.a.f_91074_.m_150109_().m_8020_(i2);
                    if (itemStack.m_41720_() != Items.f_42584_) continue;
                    n2 = i2;
                    break;
                }
                if (n2 != -1) {
                    MiddleClick.a.f_91074_.m_150109_().f_35977_ = n2;
                    MiddleClick.a.f_91072_.m_233721_((Player)MiddleClick.a.f_91074_, InteractionHand.MAIN_HAND);
                    MiddleClick.a.f_91074_.m_6674_(InteractionHand.MAIN_HAND);
                    this.d = true;
                } else {
                    com.heypixel.heypixelmod.obsoverlay.c.MainMenuScreen.a a2 = new com.heypixel.heypixelmod.obsoverlay.c.MainMenuScreen.a(com.heypixel.heypixelmod.obsoverlay.c.MainMenuScreen.b.d, "No pearls found.", 3000L);
                    com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().j().a(a2);
                }
            }
        }
        if (this.d) {
            this.d = false;
            MiddleClick.a.f_91074_.m_150109_().f_35977_ = this.e;
        }
    }

    @EventTarget
    public void onMouseKey(EventMouseClick eventMouseClick) {
        if (eventMouseClick.key() == 2 && !eventMouseClick.state()) {
            this.c = true;
        }
    }
}

