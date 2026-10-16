/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.render;

import com.heypixel.heypixelmod.obsoverlay.d.a.NumericSetting;
import com.heypixel.heypixelmod.obsoverlay.d.a.ModeSetting;
import com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerMotionEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PacketInterceptEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.a.KillAura;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.network.protocol.game.ServerboundSwingPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.UseAnim;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@ModuleInfo(a="Animations", b="\u52a8\u753b", c="Customizes item animations and block animations", d=com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory.RENDER)
public class Animations
extends ModuleBase {
    public static boolean c = false;
    public final BedESP d = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Block Mods").a("None", "1.7", "Push").a(1).a().e();
    public final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a e = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Aura Auto Block").a(true).a().b();
    public final AntiNausea f = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Blocking-X").a(0.5f).b(-2.0f).c(2.0f).d(0.05f).a().c();
    public final AntiNausea g = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Blocking-Y").a(-0.5f).b(-2.0f).c(2.0f).d(0.05f).a().c();
    private final Minecraft i = Minecraft.m_91087_();
    public com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a h = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Only Aura").a(false).a().b();
    private boolean j;
    private float k = 0.0f;
    private float l = 0.0f;
    private float m = 0.0f;
    private float n = 0.0f;
    private ItemStack o = ItemStack.f_41583_;
    private ItemStack p = ItemStack.f_41583_;

    @Override
    public void d() {
        super.d();
        MinecraftForge.EVENT_BUS.register((Object)this);
    }

    @Override
    public void e() {
        super.e();
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }

    @SubscribeEvent
    public void a(RenderHandEvent renderHandEvent) {
        boolean bl;
        ItemStack itemStack;
        UseAnim useAnim;
        if (!this.m() || this.d.l().equals("None")) {
            return;
        }
        if (renderHandEvent.getHand() != InteractionHand.MAIN_HAND || !(renderHandEvent.getItemStack().m_41720_() instanceof SwordItem)) {
            return;
        }
        ItemStack itemStack2 = this.i.f_91074_.m_21205_();
        if (!(itemStack2.m_41720_() instanceof SwordItem)) {
            return;
        }
        boolean bl2 = false;
        if (this.i.f_91074_.m_6117_() && this.i.f_91074_.m_7655_() == InteractionHand.OFF_HAND && (useAnim = (itemStack = this.i.f_91074_.m_21206_()).m_41780_()) != UseAnim.BLOCK) {
            bl2 = true;
        }
        boolean bl3 = bl = this.e.m() && this.r() != null;
        if (this.h.m() && !bl) {
            return;
        }
        if (bl2 && !bl) {
            return;
        }
        if (!this.i.f_91066_.f_92095_.m_90857_() && !bl) {
            return;
        }
        renderHandEvent.setCanceled(true);
        this.a((AbstractClientPlayer)this.i.f_91074_, renderHandEvent.getPartialTick(), renderHandEvent.getEquipProgress(), renderHandEvent.getHand(), renderHandEvent.getSwingProgress(), renderHandEvent.getItemStack(), renderHandEvent.getEquipProgress(), renderHandEvent.getPoseStack(), renderHandEvent.getMultiBufferSource(), renderHandEvent.getPackedLight());
    }

    @EventTarget
    public void onPacket(PacketInterceptEvent m2) {
        if (m2.b() == com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.c && m2.c() instanceof ServerboundSwingPacket) {
            this.j = !this.j;
        }
    }

    @EventTarget
    public void onMotion(PlayerMotionEvent k2) {
        if (k2.b() != com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.a || this.i.f_91074_ == null) {
            return;
        }
        this.p();
    }

    private void p() {
        this.m = this.k;
        this.n = this.l;
        LocalPlayer localPlayer = this.i.f_91074_;
        ItemStack itemStack = localPlayer.m_21205_();
        ItemStack itemStack2 = localPlayer.m_21206_();
        boolean bl = this.q();
        if (bl) {
            this.k = 1.0f;
            if (ItemStack.m_41728_((ItemStack)this.o, (ItemStack)itemStack)) {
                this.o = itemStack;
            }
            if (ItemStack.m_41728_((ItemStack)this.p, (ItemStack)itemStack2)) {
                this.p = itemStack2;
            }
            return;
        }
        if (localPlayer.m_108637_()) {
            this.k = Mth.m_14036_((float)(this.k - 0.4f), (float)0.0f, (float)1.0f);
            this.l = Mth.m_14036_((float)(this.l - 0.4f), (float)0.0f, (float)1.0f);
        } else {
            float f2 = localPlayer.m_36403_(1.0f);
            boolean bl2 = ForgeHooksClient.shouldCauseReequipAnimation((ItemStack)this.o, (ItemStack)itemStack, (int)localPlayer.m_150109_().f_35977_);
            boolean bl3 = ForgeHooksClient.shouldCauseReequipAnimation((ItemStack)this.p, (ItemStack)itemStack2, (int)-1);
            if (!bl2 && this.o != itemStack) {
                this.o = itemStack;
            }
            if (!bl3 && this.p != itemStack2) {
                this.p = itemStack2;
            }
            float f3 = !bl2 ? f2 * f2 * f2 : 0.0f;
            float f4 = !bl3 ? 1.0f : 0.0f;
            this.k += Mth.m_14036_((float)(f3 - this.k), (float)-0.2f, (float)0.2f);
            this.l += Mth.m_14036_((float)(f4 - this.l), (float)-0.2f, (float)0.2f);
        }
        if (this.k < 0.1f) {
            this.o = itemStack;
        }
        if (this.l < 0.1f) {
            this.p = itemStack2;
        }
    }

    private boolean q() {
        boolean bl;
        ItemStack itemStack;
        UseAnim useAnim;
        if (!this.m() || this.d.l().equals("None")) {
            return false;
        }
        LocalPlayer localPlayer = this.i.f_91074_;
        if (localPlayer == null) {
            return false;
        }
        ItemStack itemStack2 = localPlayer.m_21205_();
        if (!(itemStack2.m_41720_() instanceof SwordItem)) {
            return false;
        }
        ItemStack itemStack3 = localPlayer.m_21205_();
        if (!(itemStack3.m_41720_() instanceof SwordItem)) {
            return false;
        }
        boolean bl2 = false;
        if (localPlayer.m_6117_() && localPlayer.m_7655_() == InteractionHand.OFF_HAND && (useAnim = (itemStack = localPlayer.m_21206_()).m_41780_()) != UseAnim.BLOCK) {
            bl2 = true;
        }
        boolean bl3 = bl = this.e.m() && this.r() != null;
        if (this.h.m()) {
            return bl;
        }
        if (bl) {
            return true;
        }
        if (bl2) {
            return false;
        }
        return this.i.f_91066_.f_92095_.m_90857_();
    }

    private void a(AbstractClientPlayer abstractClientPlayer, float f2, float f3, InteractionHand interactionHand, float f4, ItemStack itemStack, float f5, PoseStack poseStack, MultiBufferSource multiBufferSource, int n2) {
        if (!abstractClientPlayer.m_150108_()) {
            boolean bl = interactionHand == InteractionHand.MAIN_HAND;
            HumanoidArm humanoidArm = bl ? abstractClientPlayer.m_5737_() : abstractClientPlayer.m_5737_().m_20828_();
            Animations a2 = this;
            poseStack.m_85836_();
            if (itemStack.m_41619_()) {
                if (bl && !abstractClientPlayer.m_20145_()) {
                    this.a(poseStack, multiBufferSource, n2, f5, f4, humanoidArm);
                }
            } else if (itemStack.m_150930_(Items.f_42573_)) {
                if (bl && this.p.m_41619_()) {
                    this.a(poseStack, multiBufferSource, n2, f3, f5, f4);
                } else {
                    this.a(poseStack, multiBufferSource, n2, f5, humanoidArm, f4, itemStack);
                }
            } else {
                int n3;
                boolean bl2 = itemStack.m_150930_(Items.f_42717_) && CrossbowItem.m_40932_((ItemStack)itemStack);
                int n4 = n3 = humanoidArm == HumanoidArm.RIGHT ? 1 : -1;
                if (itemStack.m_150930_(Items.f_42717_)) {
                    if (abstractClientPlayer.m_6117_() && abstractClientPlayer.m_21212_() > 0 && abstractClientPlayer.m_7655_() == interactionHand) {
                        this.a(poseStack, humanoidArm, f5);
                        poseStack.m_85837_((double)((float)n3 * -0.4785682f), (double)-0.094387f, (double)0.0573153f);
                        poseStack.m_252781_(Axis.f_252529_.m_252961_(-0.20830506f));
                        poseStack.m_252781_(Axis.f_252436_.m_252961_((float)n3 * 65.3f * (float)Math.PI / 180.0f));
                        poseStack.m_252781_(Axis.f_252403_.m_252961_((float)n3 * -9.785f * (float)Math.PI / 180.0f));
                        float f6 = (float)itemStack.m_41779_() - ((float)abstractClientPlayer.m_21212_() - f2 + 1.0f);
                        float f7 = f6 / (float)CrossbowItem.m_40939_((ItemStack)itemStack);
                        f7 = Math.min(f7, 1.0f);
                        if (f7 > 0.1f) {
                            float f8 = Mth.m_14031_((float)((f6 - 0.1f) * 1.3f));
                            float f9 = f7 - 0.1f;
                            float f10 = f8 * f9;
                            poseStack.m_85837_((double)(f10 * 0.0f), (double)(f10 * 0.004f), (double)(f10 * 0.0f));
                        }
                        poseStack.m_85837_((double)(f7 * 0.0f), (double)(f7 * 0.0f), (double)(f7 * 0.04f));
                        poseStack.m_85841_(1.0f, 1.0f, 1.0f + f7 * 0.2f);
                        poseStack.m_252781_(Axis.f_252436_.m_252961_((float)n3 * -45.0f * (float)Math.PI / 180.0f));
                    } else {
                        float f11 = -0.4f * Mth.m_14031_((float)(Mth.m_14116_((float)f4) * (float)Math.PI));
                        float f12 = 0.2f * Mth.m_14031_((float)(Mth.m_14116_((float)f4) * ((float)Math.PI * 2)));
                        float f13 = -0.2f * Mth.m_14031_((float)(f4 * (float)Math.PI));
                        poseStack.m_85837_((double)((float)n3 * f11), (double)f12, (double)f13);
                        this.a(poseStack, humanoidArm, f5);
                        this.b(poseStack, humanoidArm, f4);
                        if (bl2 && f4 < 0.001f && bl) {
                            poseStack.m_85837_((double)((float)n3 * -0.641864f), 0.0, 0.0);
                            poseStack.m_252781_(Axis.f_252436_.m_252961_((float)n3 * 10.0f * (float)Math.PI / 180.0f));
                        }
                    }
                    this.a((LivingEntity)abstractClientPlayer, itemStack, n3 == 1 ? ItemDisplayContext.FIRST_PERSON_RIGHT_HAND : ItemDisplayContext.FIRST_PERSON_LEFT_HAND, n3 != 1, poseStack, multiBufferSource, n2);
                } else {
                    boolean bl3;
                    boolean bl4 = bl3 = humanoidArm == HumanoidArm.RIGHT;
                    if (abstractClientPlayer.m_6117_() && abstractClientPlayer.m_21212_() > 0 && abstractClientPlayer.m_7655_() == interactionHand) {
                        switch (itemStack.m_41780_()) {
                            case NONE: 
                            case BLOCK: {
                                this.a(poseStack, humanoidArm, f5);
                                break;
                            }
                            case EAT: 
                            case DRINK: {
                                this.a(poseStack, f2, humanoidArm, itemStack);
                                this.a(poseStack, humanoidArm, f5);
                                break;
                            }
                            case BOW: {
                                this.a(poseStack, humanoidArm, f5);
                                poseStack.m_85837_((double)((float)n3 * -0.2785682f), (double)0.183444f, (double)0.1573153f);
                                poseStack.m_252781_(Axis.f_252529_.m_252961_(-0.24321164f));
                                poseStack.m_252781_(Axis.f_252436_.m_252961_((float)n3 * 35.3f * (float)Math.PI / 180.0f));
                                poseStack.m_252781_(Axis.f_252403_.m_252961_((float)n3 * -9.785f * (float)Math.PI / 180.0f));
                                float f14 = (float)itemStack.m_41779_() - ((float)abstractClientPlayer.m_21212_() - f2 + 1.0f);
                                float f15 = f14 / 20.0f;
                                f15 = (f15 * f15 + f15 * 2.0f) / 3.0f;
                                f15 = Math.min(f15, 1.0f);
                                if (f15 > 0.1f) {
                                    float f16 = Mth.m_14031_((float)((f14 - 0.1f) * 1.3f));
                                    float f17 = f15 - 0.1f;
                                    float f18 = f16 * f17;
                                    poseStack.m_85837_((double)(f18 * 0.0f), (double)(f18 * 0.004f), (double)(f18 * 0.0f));
                                }
                                poseStack.m_85837_((double)(f15 * 0.0f), (double)(f15 * 0.0f), (double)(f15 * 0.04f));
                                poseStack.m_85841_(1.0f, 1.0f, 1.0f + f15 * 0.2f);
                                poseStack.m_252781_(Axis.f_252436_.m_252961_((float)n3 * -45.0f * (float)Math.PI / 180.0f));
                                break;
                            }
                            case SPEAR: {
                                this.a(poseStack, humanoidArm, f5);
                                poseStack.m_85837_((double)((float)n3 * -0.5f), (double)0.7f, (double)0.1f);
                                poseStack.m_252781_(Axis.f_252529_.m_252961_(-0.9599311f));
                                poseStack.m_252781_(Axis.f_252436_.m_252961_((float)n3 * 35.3f * (float)Math.PI / 180.0f));
                                poseStack.m_252781_(Axis.f_252403_.m_252961_((float)n3 * -9.785f * (float)Math.PI / 180.0f));
                                float f19 = (float)itemStack.m_41779_() - ((float)abstractClientPlayer.m_21212_() - f2 + 1.0f);
                                float f20 = f19 / 10.0f;
                                f20 = Math.min(f20, 1.0f);
                                if (f20 > 0.1f) {
                                    float f21 = Mth.m_14031_((float)((f19 - 0.1f) * 1.3f));
                                    float f22 = f20 - 0.1f;
                                    float f23 = f21 * f22;
                                    poseStack.m_85837_((double)(f23 * 0.0f), (double)(f23 * 0.004f), (double)(f23 * 0.0f));
                                }
                                poseStack.m_85837_(0.0, 0.0, (double)(f20 * 0.2f));
                                poseStack.m_85841_(1.0f, 1.0f, 1.0f + f20 * 0.2f);
                                poseStack.m_252781_(Axis.f_252436_.m_252961_((float)n3 * -45.0f * (float)Math.PI / 180.0f));
                            }
                        }
                    } else if ((abstractClientPlayer.m_6117_() || Minecraft.m_91087_().f_91066_.f_92095_.m_90857_() || a2.e.m() && this.r() != null) && abstractClientPlayer.m_21205_().m_41720_() instanceof SwordItem && !a2.d.l().equals("None")) {
                        String string;
                        switch (string = a2.d.l().toLowerCase()) {
                            case "1.7": {
                                poseStack.m_85837_((double)((float)n3 * this.f.q()), (double)this.g.q(), (double)-0.72f);
                                float f24 = Mth.m_14031_((float)(f4 * f4 * (float)Math.PI));
                                float f25 = Mth.m_14031_((float)(Mth.m_14116_((float)f4) * (float)Math.PI));
                                poseStack.m_252781_(Axis.f_252436_.m_252961_((float)n3 * (45.0f + f24 * -20.0f) * (float)Math.PI / 180.0f));
                                poseStack.m_252781_(Axis.f_252403_.m_252961_((float)n3 * f25 * -10.0f * (float)Math.PI / 180.0f));
                                poseStack.m_252781_(Axis.f_252529_.m_252961_(f25 * -80.0f * (float)Math.PI / 180.0f));
                                poseStack.m_252781_(Axis.f_252436_.m_252961_((float)n3 * -45.0f * (float)Math.PI / 180.0f));
                                poseStack.m_85841_(0.9f, 0.9f, 0.9f);
                                poseStack.m_252880_(-0.2f, 0.126f, 0.2f);
                                poseStack.m_252781_(Axis.f_252529_.m_252961_(-1.7845992f));
                                poseStack.m_252781_(Axis.f_252436_.m_252961_((float)n3 * 15.0f * (float)Math.PI / 180.0f));
                                poseStack.m_252781_(Axis.f_252403_.m_252961_((float)n3 * 80.0f * (float)Math.PI / 180.0f));
                                break;
                            }
                            case "push": {
                                poseStack.m_85837_((double)((float)n3 * this.f.q()), (double)this.g.q(), (double)-0.82f);
                                poseStack.m_85837_((double)((float)n3 * -0.1414214f), (double)0.08f, 0.1414213925600052);
                                poseStack.m_252781_(Axis.f_252529_.m_252961_(-1.7845992f));
                                poseStack.m_252781_(Axis.f_252436_.m_252961_((float)n3 * 13.365f * (float)Math.PI / 180.0f));
                                poseStack.m_252781_(Axis.f_252403_.m_252961_((float)n3 * 78.05f * (float)Math.PI / 180.0f));
                                float f26 = Mth.m_14031_((float)(f4 * f4 * (float)Math.PI));
                                float f27 = Mth.m_14031_((float)(Mth.m_14116_((float)f4) * (float)Math.PI));
                                poseStack.m_252781_(Axis.f_252529_.m_252961_(f26 * -10.0f * (float)Math.PI / 180.0f));
                                poseStack.m_252781_(Axis.f_252436_.m_252961_(f26 * -10.0f * (float)Math.PI / 180.0f));
                                poseStack.m_252781_(Axis.f_252403_.m_252961_(f26 * -10.0f * (float)Math.PI / 180.0f));
                                poseStack.m_252781_(Axis.f_252529_.m_252961_(f27 * -10.0f * (float)Math.PI / 180.0f));
                                poseStack.m_252781_(Axis.f_252436_.m_252961_(f27 * -10.0f * (float)Math.PI / 180.0f));
                                poseStack.m_252781_(Axis.f_252403_.m_252961_(f27 * -10.0f * (float)Math.PI / 180.0f));
                            }
                        }
                    } else if (abstractClientPlayer.m_21209_()) {
                        this.a(poseStack, humanoidArm, f5);
                        poseStack.m_85837_((double)((float)n3 * -0.4f), (double)0.8f, (double)0.3f);
                        poseStack.m_252781_(Axis.f_252436_.m_252961_((float)n3 * 65.0f * (float)Math.PI / 180.0f));
                        poseStack.m_252781_(Axis.f_252403_.m_252961_((float)n3 * -85.0f * (float)Math.PI / 180.0f));
                    } else {
                        this.a(poseStack, humanoidArm, f5);
                        if (itemStack.m_41720_() instanceof SwordItem && (this.i.f_91066_.f_92095_.m_90857_() || a2.e.m() && this.r() != null && this.r() instanceof LivingEntity)) {
                            String string;
                            switch (string = a2.d.l().toLowerCase()) {
                                case "1.7": {
                                    poseStack.m_85837_((double)((float)n3 * 0.56f), (double)-0.52f, (double)-0.72f);
                                    float f28 = Mth.m_14031_((float)(f4 * f4 * (float)Math.PI));
                                    float f29 = Mth.m_14031_((float)(Mth.m_14116_((float)f4) * (float)Math.PI));
                                    poseStack.m_252781_(Axis.f_252436_.m_252961_((float)n3 * (45.0f + f28 * -20.0f) * (float)Math.PI / 180.0f));
                                    poseStack.m_252781_(Axis.f_252403_.m_252961_((float)n3 * f29 * -20.0f * (float)Math.PI / 180.0f));
                                    poseStack.m_252781_(Axis.f_252529_.m_252961_(f29 * -80.0f * (float)Math.PI / 180.0f));
                                    poseStack.m_252781_(Axis.f_252436_.m_252961_((float)n3 * -45.0f * (float)Math.PI / 180.0f));
                                    poseStack.m_85841_(0.9f, 0.9f, 0.9f);
                                    poseStack.m_252880_(-0.2f, 0.126f, 0.2f);
                                    poseStack.m_252781_(Axis.f_252529_.m_252961_(-1.7845992f));
                                    poseStack.m_252781_(Axis.f_252436_.m_252961_((float)n3 * 15.0f * (float)Math.PI / 180.0f));
                                    poseStack.m_252781_(Axis.f_252403_.m_252961_((float)n3 * 80.0f * (float)Math.PI / 180.0f));
                                    break;
                                }
                                case "push": {
                                    poseStack.m_85837_((double)((float)n3 * 0.56f), (double)-0.52f, (double)-0.72f);
                                    poseStack.m_85837_((double)((float)n3 * -0.1414214f), (double)0.08f, 0.1414213925600052);
                                    poseStack.m_252781_(Axis.f_252529_.m_252961_(-1.7845992f));
                                    poseStack.m_252781_(Axis.f_252436_.m_252961_((float)n3 * 13.365f * (float)Math.PI / 180.0f));
                                    poseStack.m_252781_(Axis.f_252403_.m_252961_((float)n3 * 78.05f * (float)Math.PI / 180.0f));
                                    float f30 = Mth.m_14031_((float)(f4 * f4 * (float)Math.PI));
                                    float f31 = Mth.m_14031_((float)(Mth.m_14116_((float)f4) * (float)Math.PI));
                                    poseStack.m_252781_(Axis.f_252529_.m_252961_(f30 * -10.0f * (float)Math.PI / 180.0f));
                                    poseStack.m_252781_(Axis.f_252436_.m_252961_(f30 * -10.0f * (float)Math.PI / 180.0f));
                                    poseStack.m_252781_(Axis.f_252403_.m_252961_(f30 * -10.0f * (float)Math.PI / 180.0f));
                                    poseStack.m_252781_(Axis.f_252529_.m_252961_(f31 * -10.0f * (float)Math.PI / 180.0f));
                                    poseStack.m_252781_(Axis.f_252436_.m_252961_(f31 * -10.0f * (float)Math.PI / 180.0f));
                                    poseStack.m_252781_(Axis.f_252403_.m_252961_(f31 * -10.0f * (float)Math.PI / 180.0f));
                                    break;
                                }
                                default: {
                                    this.b(poseStack, humanoidArm, f4);
                                    break;
                                }
                            }
                        } else {
                            this.b(poseStack, humanoidArm, f4);
                        }
                    }
                    this.a((LivingEntity)abstractClientPlayer, itemStack, bl3 ? ItemDisplayContext.FIRST_PERSON_RIGHT_HAND : ItemDisplayContext.FIRST_PERSON_LEFT_HAND, !bl3, poseStack, multiBufferSource, n2);
                }
            }
            poseStack.m_85849_();
        }
    }

    private LivingEntity r() {
        KillAura h2 = com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a(KillAura.class);
        if (h2 != null && h2.m()) {
            try {
                return (LivingEntity)h2.c;
            }
            catch (Exception exception) {
                return null;
            }
        }
        return null;
    }

    private void a(PoseStack poseStack, MultiBufferSource multiBufferSource, int n2, float f2, float f3, HumanoidArm humanoidArm) {
        boolean bl = humanoidArm == HumanoidArm.RIGHT;
        float f4 = bl ? 1.0f : -1.0f;
        float f5 = Mth.m_14116_((float)f3);
        float f6 = -0.3f * Mth.m_14031_((float)(f5 * (float)Math.PI));
        float f7 = 0.4f * Mth.m_14031_((float)(f5 * ((float)Math.PI * 2)));
        float f8 = -0.4f * Mth.m_14031_((float)(f3 * (float)Math.PI));
        poseStack.m_85837_((double)(f4 * (0.644764f + f6)), (double)(0.644764f + f7), (double)(0.644764f + f8));
        poseStack.m_252781_(Axis.f_252529_.m_252961_(-0.3f * Mth.m_14031_((float)(f5 * ((float)Math.PI * 2)))));
        poseStack.m_252781_(Axis.f_252436_.m_252961_(f4 * 0.4f * Mth.m_14031_((float)(f5 * (float)Math.PI))));
        poseStack.m_252781_(Axis.f_252403_.m_252961_(f4 * -0.4f * Mth.m_14031_((float)(f3 * (float)Math.PI))));
        float f9 = Mth.m_14179_((float)f2, (float)this.m, (float)this.k);
        float f10 = Mth.m_14179_((float)f2, (float)this.n, (float)this.l);
        this.a((LivingEntity)this.i.f_91074_, bl ? this.o : this.p, bl ? ItemDisplayContext.FIRST_PERSON_RIGHT_HAND : ItemDisplayContext.FIRST_PERSON_LEFT_HAND, !bl, poseStack, multiBufferSource, n2);
    }

    private void a(PoseStack poseStack, MultiBufferSource multiBufferSource, int n2, float f2, float f3, float f4) {
        float f5 = Mth.m_14116_((float)f4);
        float f6 = -0.2f * Mth.m_14031_((float)(f4 * (float)Math.PI));
        float f7 = -0.4f * Mth.m_14031_((float)(f5 * (float)Math.PI));
        poseStack.m_85837_(0.0, (double)(-f6 / 2.0f), (double)f7);
        float f8 = Mth.m_14179_((float)f3, (float)this.m, (float)this.k);
        float f9 = Mth.m_14179_((float)f3, (float)this.n, (float)this.l);
        this.a((LivingEntity)this.i.f_91074_, this.o, ItemDisplayContext.FIRST_PERSON_RIGHT_HAND, false, poseStack, multiBufferSource, n2);
        this.a((LivingEntity)this.i.f_91074_, this.p, ItemDisplayContext.FIRST_PERSON_LEFT_HAND, true, poseStack, multiBufferSource, n2);
    }

    private void a(PoseStack poseStack, MultiBufferSource multiBufferSource, int n2, float f2, HumanoidArm humanoidArm, float f3, ItemStack itemStack) {
        float f4 = humanoidArm == HumanoidArm.RIGHT ? 1.0f : -1.0f;
        poseStack.m_85837_((double)(f4 * 0.125f), 0.0, 0.0);
        float f5 = Mth.m_14116_((float)f3);
        float f6 = -0.1f * Mth.m_14031_((float)(f5 * (float)Math.PI));
        float f7 = -0.3f * Mth.m_14031_((float)(f5 * ((float)Math.PI * 2)));
        float f8 = -0.4f * Mth.m_14031_((float)(f3 * (float)Math.PI));
        poseStack.m_85837_(0.0, (double)(-f6 / 2.0f), (double)f8);
        poseStack.m_252781_(Axis.f_252529_.m_252961_(f7 * (float)Math.PI / 180.0f));
        poseStack.m_252781_(Axis.f_252436_.m_252961_(f4 * f5 * (float)Math.PI / 180.0f));
        poseStack.m_252781_(Axis.f_252403_.m_252961_(f4 * f6 * (float)Math.PI / 180.0f));
        float f9 = Mth.m_14179_((float)f2, (float)this.m, (float)this.k);
        float f10 = Mth.m_14179_((float)f2, (float)this.n, (float)this.l);
        this.a((LivingEntity)this.i.f_91074_, itemStack, humanoidArm == HumanoidArm.RIGHT ? ItemDisplayContext.FIRST_PERSON_RIGHT_HAND : ItemDisplayContext.FIRST_PERSON_LEFT_HAND, humanoidArm != HumanoidArm.RIGHT, poseStack, multiBufferSource, n2);
    }

    private void a(PoseStack poseStack, HumanoidArm humanoidArm, float f2) {
        int n2 = humanoidArm == HumanoidArm.RIGHT ? 1 : -1;
        float f3 = Mth.m_14179_((float)f2, (float)this.m, (float)this.k);
        float f4 = Mth.m_14179_((float)f2, (float)this.n, (float)this.l);
        poseStack.m_85837_((double)((float)n2 * 0.56f), (double)(-0.52f + f3 * -0.6f), (double)-0.72f);
    }

    private void b(PoseStack poseStack, HumanoidArm humanoidArm, float f2) {
        int n2 = humanoidArm == HumanoidArm.RIGHT ? 1 : -1;
        float f3 = Mth.m_14031_((float)(f2 * f2 * (float)Math.PI));
        float f4 = Mth.m_14031_((float)(Mth.m_14116_((float)f2) * (float)Math.PI));
        poseStack.m_85837_((double)((float)n2 * 0.56f), (double)-0.52f, (double)-0.72f);
        poseStack.m_252781_(Axis.f_252529_.m_252961_(-1.7845992f));
        poseStack.m_252781_(Axis.f_252436_.m_252961_((float)n2 * 13.365f * (float)Math.PI / 180.0f));
        poseStack.m_252781_(Axis.f_252403_.m_252961_((float)n2 * 78.05f * (float)Math.PI / 180.0f));
        float f5 = Mth.m_14036_((float)f2, (float)0.0f, (float)1.0f);
        poseStack.m_252781_(Axis.f_252529_.m_252961_(f3 * -15.0f * f5 * (float)Math.PI / 180.0f));
        poseStack.m_252781_(Axis.f_252436_.m_252961_(f4 * -15.0f * f5 * (float)Math.PI / 180.0f));
        poseStack.m_252781_(Axis.f_252403_.m_252961_(f4 * -70.0f * f5 * (float)Math.PI / 180.0f));
    }

    private void a(PoseStack poseStack, float f2, HumanoidArm humanoidArm, ItemStack itemStack) {
        float f3;
        float f4 = (float)itemStack.m_41779_() - ((float)this.i.f_91074_.m_21212_() - f2 + 1.0f);
        float f5 = f4 / (float)itemStack.m_41779_();
        if (f5 < 0.8f) {
            f3 = Mth.m_14154_((float)(Mth.m_14089_((float)(f4 / 4.0f * (float)Math.PI)) * 0.1f));
            poseStack.m_85837_(0.0, (double)f3, 0.0);
        }
        f3 = 1.0f - (float)Math.pow(1.0f - f5, 27.0);
        int n2 = humanoidArm == HumanoidArm.RIGHT ? 1 : -1;
        poseStack.m_85837_((double)(f3 * 0.6f * (float)n2), (double)(f3 * -0.5f), (double)(f3 * 0.0f));
        poseStack.m_252781_(Axis.f_252436_.m_252961_((float)n2 * f3 * 90.0f * (float)Math.PI / 180.0f));
        poseStack.m_252781_(Axis.f_252529_.m_252961_(f3 * 10.0f * (float)Math.PI / 180.0f));
        poseStack.m_252781_(Axis.f_252403_.m_252961_((float)n2 * f3 * 30.0f * (float)Math.PI / 180.0f));
    }

    private void a(LivingEntity livingEntity, ItemStack itemStack, ItemDisplayContext itemDisplayContext, boolean bl, PoseStack poseStack, MultiBufferSource multiBufferSource, int n2) {
        if (itemStack.m_41619_()) {
            return;
        }
        ItemRenderer itemRenderer = this.i.m_91291_();
        itemRenderer.m_269491_(livingEntity, itemStack, itemDisplayContext, bl, poseStack, multiBufferSource, livingEntity.m_9236_(), n2, 0, 0);
    }
}

