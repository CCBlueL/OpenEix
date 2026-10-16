/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.render;

import com.heypixel.heypixelmod.obsoverlay.EixClientCore;
import com.heypixel.heypixelmod.obsoverlay.d.a.NumericSetting;
import com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.CrosshairRenderEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;
import java.awt.Color;
import java.lang.reflect.Field;
import java.util.Iterator;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

@ModuleInfo(a="HUD", b="\u62ac\u5934\u663e\u793a\u5668", c="Displays information on your screen", d=com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory.RENDER)
public class HUD
extends ModuleBase {
    public static final int c = new Color(150, 45, 45, 255).getRGB();
    public static final int d = new Color(0, 0, 0, 120).getRGB();
    public static final int e = new Color(0, 0, 0, 40).getRGB();
    public com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a f = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Module Toggle Sound").a(true).a().b();
    public AntiNausea g = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Red 1").a(102.0f).d(1.0f).b(0.0f).c(255.0f).a().c();
    public AntiNausea h = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Green 1").a(255.0f).d(1.0f).b(0.0f).c(255.0f).a().c();
    public AntiNausea i = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Blue 1").a(209.0f).d(1.0f).b(0.0f).c(255.0f).a().c();
    public AntiNausea j = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Red 2").a(6.0f).d(1.0f).b(0.0f).c(255.0f).a().c();
    public AntiNausea k = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Green 2").a(149.0f).d(1.0f).b(0.0f).c(255.0f).a().c();
    public AntiNausea l = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Blue 2").a(255.0f).d(1.0f).b(0.0f).c(255.0f).a().c();
    public com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a m = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Chat Background").a(true).a().b();
    public com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a n = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Chat Blur").a(true).a().b();
    public com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a o = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Chat Shadow").a(true).a().b();
    public AntiNausea p = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Chat Radius").a(5.0f).d(1.0f).b(0.0f).c(20.0f).a().c();
    public com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a q = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Chat Input Background").a(true).a().b();
    public com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a r = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Better GUI").a(true).a().b();
    public com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a s = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Better GUI Blur").a(true).a(() -> this.r.m()).a().b();
    public com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a t = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Better GUI Shadow").a(true).a(() -> this.r.m()).a().b();
    public AntiNausea u = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Better GUI Radius").a(10.0f).d(1.0f).b(0.0f).c(30.0f).a(() -> this.r.m()).a().c();
    public AntiNausea v = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Better GUI Opacity").a(110.0f).d(5.0f).b(0.0f).c(255.0f).a(() -> this.r.m()).a().c();
    public AntiNausea w = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Better GUI Slot Opacity").a(35.0f).d(5.0f).b(0.0f).c(255.0f).a(() -> this.r.m()).a().c();
    private float x = Float.MAX_VALUE;
    private float y = Float.MAX_VALUE;
    private float z = Float.MIN_VALUE;
    private float A = Float.MIN_VALUE;
    private boolean B = false;
    private int C = 0;
    private int D = -1;
    private float E = Float.MAX_VALUE;
    private float F = Float.MAX_VALUE;
    private float G = Float.MIN_VALUE;
    private float H = Float.MIN_VALUE;
    private boolean I = false;
    private int J = 0;
    private int K = -1;
    private long L = 0L;
    private long M = 0L;
    private boolean N = false;
    private static Field O;
    private static Field P;
    private static Field Q;
    private static Field R;
    private static Field S;
    private static boolean T;

    public static Color p() {
        HUD m2 = com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a(HUD.class);
        return new Color(m2.g.q() / 255.0f, m2.h.q() / 255.0f, m2.i.q() / 255.0f);
    }

    public static Color q() {
        HUD m2 = com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a(HUD.class);
        return new Color(m2.j.q() / 255.0f, m2.k.q() / 255.0f, m2.l.q() / 255.0f);
    }

    public void r() {
        if (!this.w()) {
            this.B = false;
            return;
        }
        ++this.C;
        this.x = Float.MAX_VALUE;
        this.y = Float.MAX_VALUE;
        this.z = Float.MIN_VALUE;
        this.A = Float.MIN_VALUE;
        this.B = false;
    }

    public void a(int n2, int n3, int n4, int n5) {
        if (!this.w()) {
            return;
        }
        float f2 = Math.min(n2, n4);
        float f3 = Math.min(n3, n5);
        float f4 = Math.max(n2, n4);
        float f5 = Math.max(n3, n5);
        this.x = Math.min(this.x, f2);
        this.y = Math.min(this.y, f3);
        this.z = Math.max(this.z, f4);
        this.A = Math.max(this.A, f5);
        this.B = true;
    }

    public void s() {
        if (!this.x()) {
            this.I = false;
            return;
        }
        this.N = false;
        ++this.J;
        this.E = Float.MAX_VALUE;
        this.F = Float.MAX_VALUE;
        this.G = Float.MIN_VALUE;
        this.H = Float.MIN_VALUE;
        this.I = false;
    }

    public void b(int n2, int n3, int n4, int n5) {
        if (!this.x()) {
            return;
        }
        float f2 = Math.min(n2, n4);
        float f3 = Math.min(n3, n5);
        float f4 = Math.max(n2, n4);
        float f5 = Math.max(n3, n5);
        this.E = Math.min(this.E, f2);
        this.F = Math.min(this.F, f3);
        this.G = Math.max(this.G, f4);
        this.H = Math.max(this.H, f5);
        this.I = true;
    }

    public void t() {
        this.L = System.currentTimeMillis();
        this.M = 0L;
        this.N = false;
        this.K = -1;
        this.I = false;
    }

    public void u() {
        if (!this.x()) {
            return;
        }
        this.N = true;
        this.M = System.currentTimeMillis();
        this.K = -1;
    }

    private void v() {
        if (T || O != null) {
            return;
        }
        try {
            Class<AbstractContainerScreen> clazz = AbstractContainerScreen.class;
            try {
                O = clazz.getDeclaredField("leftPos");
                P = clazz.getDeclaredField("topPos");
                Q = clazz.getDeclaredField("imageWidth");
                R = clazz.getDeclaredField("imageHeight");
            }
            catch (NoSuchFieldException noSuchFieldException) {
                T = true;
                return;
            }
            O.setAccessible(true);
            P.setAccessible(true);
            Q.setAccessible(true);
            R.setAccessible(true);
            try {
                S = clazz.getDeclaredField("hoveredSlot");
                S.setAccessible(true);
            }
            catch (NoSuchFieldException noSuchFieldException) {
                S = null;
            }
        }
        catch (Exception exception) {
            T = true;
        }
    }

    @EventTarget
    public void onRenderSkia(CrosshairRenderEvent s2) {
        float f2;
        float f3;
        float f4;
        float f5;
        float f6;
        boolean bl;
        boolean bl2 = this.w();
        boolean bl3 = this.x();
        boolean bl4 = bl = this.r.m() && com.heypixel.heypixelmod.obsoverlay.modules.impl.render.HUD.a.f_91080_ instanceof AbstractContainerScreen;
        if (!(bl2 || bl3 || bl)) {
            return;
        }
        if (bl) {
            this.v();
            if (!T) {
                try {
                    AbstractContainerScreen abstractContainerScreen = (AbstractContainerScreen)com.heypixel.heypixelmod.obsoverlay.modules.impl.render.HUD.a.f_91080_;
                    int n2 = O.getInt(abstractContainerScreen);
                    int n3 = P.getInt(abstractContainerScreen);
                    int n4 = Q.getInt(abstractContainerScreen);
                    int n5 = R.getInt(abstractContainerScreen);
                    f6 = n2;
                    f5 = n3;
                    f4 = n4;
                    float f7 = n5;
                    f3 = this.u.q();
                    if (this.t.m()) {
                        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c(f6, f5, f4, f7, f3);
                    }
                    if (this.s.m()) {
                        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f6, f5, f4, f7, f3);
                    }
                    com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f6, f5, f4, f7, f3, new Color(18, 18, 18, (int)this.v.q()));
                    AbstractContainerMenu abstractContainerMenu = abstractContainerScreen.m_6262_();
                    if (abstractContainerMenu != null) {
                        Color color = new Color(25, 25, 25, (int)this.w.q());
                        for (Slot slot : abstractContainerMenu.f_38839_) {
                            f2 = f6 + (float)slot.f_40220_;
                            float f8 = f5 + (float)slot.f_40221_;
                            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f2, f8, 16.0f, 16.0f, 4.0f, color);
                        }
                    }
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
        }
        if (bl2 && this.B && this.D != this.C) {
            float f9 = 4.0f;
            float f10 = this.x - f9;
            float f11 = this.y - f9;
            float f12 = this.z + f9;
            float f13 = this.A + f9;
            f6 = f12 - f10;
            f5 = f13 - f11;
            if (f6 > 0.0f && f5 > 0.0f) {
                f4 = this.p.q();
                if (this.o.m()) {
                    com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c(f10, f11, f6, f5, f4);
                }
                if (this.n.m()) {
                    com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f10, f11, f6, f5, f4);
                }
                com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f10, f11, f6, f5, f4, new Color(0, 0, 0, 60));
                this.D = this.C;
            }
        }
        if (bl3) {
            float f14;
            if (!this.I) {
                return;
            }
            if (!this.N && this.K == this.J) {
                return;
            }
            float f15 = 2.0f;
            float f16 = this.E - f15;
            float f17 = this.F - f15;
            float f18 = this.G + f15;
            float f19 = this.H + f15;
            f6 = f18 - f16;
            f5 = f19 - f17;
            if (f6 <= 0.0f || f5 <= 0.0f) {
                return;
            }
            long l2 = System.currentTimeMillis() - (this.N ? this.M : this.L);
            f3 = Math.min(1.0f, (float)l2 / 300.0f);
            float f20 = (float)(1.0 - Math.pow(1.0f - f3, 3.0));
            float f21 = f14 = this.N ? 1.0f - f20 : f20;
            if (this.N && f3 >= 1.0f) {
                this.N = false;
                this.I = false;
                return;
            }
            float f22 = a.m_91268_().m_85446_();
            float f23 = f17 + (f22 - f17) * (1.0f - f14);
            f2 = this.p.q();
            int n6 = Math.min(255, Math.max(0, (int)(70.0f * f14)));
            Color color = new Color(18, 18, 18, n6);
            if (this.o.m()) {
                com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c(f16, f23, f6, f5, f2);
            }
            if (this.n.m()) {
                com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f16, f23, f6, f5, f2);
            }
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f16, f23, f6, f5, f2, color);
            this.K = this.J;
        }
    }

    private boolean w() {
        return this.m() && this.m.m();
    }

    private boolean x() {
        return this.m() && this.q.m();
    }

    public void a(AbstractContainerScreen<?> abstractContainerScreen, GuiGraphics guiGraphics, int n2, int n3) {
        if (!this.r.m()) {
            return;
        }
        this.v();
        if (T) {
            return;
        }
        try {
            int n4 = O.getInt(abstractContainerScreen);
            int n5 = P.getInt(abstractContainerScreen);
            AbstractContainerMenu abstractContainerMenu = abstractContainerScreen.m_6262_();
            if (abstractContainerMenu == null) {
                return;
            }
            Slot slot = null;
            float f2 = n4;
            float f3 = n5;
            for (Slot slot2 : abstractContainerMenu.f_38839_) {
                float f4 = f2 + (float)slot2.f_40220_;
                float f5 = f3 + (float)slot2.f_40221_;
                if ((float)n2 >= f4 && (float)n2 < f4 + 16.0f && (float)n3 >= f5 && (float)n3 < f5 + 16.0f) {
                    slot = slot2;
                }
                if (!slot2.m_6657_()) continue;
                ItemStack itemStack = slot2.m_7993_();
                guiGraphics.m_280480_(itemStack, (int)f4, (int)f5);
                guiGraphics.m_280370_(com.heypixel.heypixelmod.obsoverlay.modules.impl.render.HUD.a.f_91062_, itemStack, (int)f4, (int)f5);
            }
            Iterator iterator = abstractContainerMenu.m_142621_();
            if (!iterator.m_41619_()) {
                int n6 = n2 - 8;
                int n7 = n3 - 8;
                guiGraphics.m_280480_((ItemStack)iterator, n6, n7);
                guiGraphics.m_280370_(com.heypixel.heypixelmod.obsoverlay.modules.impl.render.HUD.a.f_91062_, (ItemStack)iterator, n6, n7);
            }
            a.m_91269_().m_110104_().m_109911_();
            if (S != null) {
                S.set(abstractContainerScreen, slot);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    static {
        T = false;
    }
}

