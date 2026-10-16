/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.mixin.O;

import com.heypixel.heypixelmod.obsoverlay.events.impl.ScoreboardTextEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.CrosshairRenderEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.NoRender;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.PostProcess;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.Scoreboard;
import com.heypixel.heypixelmod.obsoverlay.utils.e.SkiaDrawingUtils;
import com.heypixel.heypixelmod.obsoverlay.utils.e.b.BundledFontRegistry;
import io.github.humbleui.skija.Font;
import io.github.humbleui.skija.FontMetrics;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Team;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={Gui.class}, priority=100)
public class MixinGui {
    @Shadow
    @Nullable
    protected Component f_93001_;
    @Shadow
    protected int f_93000_;
    @Shadow
    protected int f_92970_;
    @Shadow
    protected int f_92971_;
    @Shadow
    protected int f_92972_;
    @Shadow
    @Nullable
    protected Component f_93002_;

    @Inject(method={"displayScoreboardSidebar"}, at={@At(value="HEAD")})
    public void hookScoreboardHead(GuiGraphics guiGraphics, Objective objective, CallbackInfo callbackInfo) {
        guiGraphics.m_280168_().m_85836_();
        Scoreboard x2 = com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a(Scoreboard.class);
        x2.p();
        if (x2.m()) {
            guiGraphics.m_280168_().m_252880_(x2.e.q(), x2.f.q(), 0.0f);
        }
    }

    @Inject(method={"displayScoreboardSidebar"}, at={@At(value="RETURN")})
    public void hookScoreboardReturn(GuiGraphics guiGraphics, Objective objective, CallbackInfo callbackInfo) {
        guiGraphics.m_280168_().m_85849_();
    }

    @Redirect(method={"displayScoreboardSidebar"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;IIIZ)I"))
    public int hookRenderScore(GuiGraphics guiGraphics, net.minecraft.client.gui.Font font, String string, int n2, int n3, int n4, boolean bl) {
        boolean bl2;
        Scoreboard x2 = com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a(Scoreboard.class);
        boolean bl3 = x2.m() && x2.c.m();
        boolean bl4 = x2.m() && x2.d.m();
        int n5 = n4;
        boolean bl5 = bl2 = bl3 && MixinGui.isRedColor(n5);
        if (bl2) {
            return 0;
        }
        Objects.requireNonNull(font);
        float f2 = 9.0f;
        if (bl4) {
            Font font2 = c.a(f2);
            float f3 = a.a(string, font2);
            FontMetrics fontMetrics = font2.getMetrics();
            float f4 = fontMetrics.getDescent() - fontMetrics.getAscent();
            x2.a(string, n2, n3, n5, f2);
            x2.a(f3, f4, n2, n3);
            return 0;
        }
        float f5 = font.m_92895_(string);
        Objects.requireNonNull(font);
        float f6 = 9.0f;
        x2.a(f5, f6, n2, n3);
        return guiGraphics.m_280488_(font, string, n2, n3, n5);
    }

    @Redirect(method={"displayScoreboardSidebar"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;IIIZ)I"), require=0)
    public int hookRenderScoreComponent(GuiGraphics guiGraphics, net.minecraft.client.gui.Font font, Component component, int n2, int n3, int n4, boolean bl) {
        boolean bl2;
        Scoreboard x2 = com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a(Scoreboard.class);
        boolean bl3 = x2.m() && x2.c.m();
        boolean bl4 = x2.m() && x2.d.m();
        String string = component.getString();
        int n5 = MixinGui.resolveColorFromStyle(component.m_7383_(), n4);
        boolean bl5 = bl2 = bl3 && MixinGui.isRedColor(n5);
        if (bl2) {
            return 0;
        }
        Objects.requireNonNull(font);
        float f2 = 9.0f;
        if (bl4) {
            Font font2 = c.a(f2);
            float f3 = a.a(string, font2);
            FontMetrics fontMetrics = font2.getMetrics();
            float f4 = fontMetrics.getDescent() - fontMetrics.getAscent();
            x2.a(string, n2, n3, n5, f2);
            x2.a(f3, f4, n2, n3);
            return 0;
        }
        float f5 = font.m_92852_((FormattedText)component);
        Objects.requireNonNull(font);
        float f6 = 9.0f;
        x2.a(f5, f6, n2, n3);
        return guiGraphics.m_280614_(font, component, n2, n3, n5, bl);
    }

    @Redirect(method={"displayScoreboardSidebar"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/util/FormattedCharSequence;IIIZ)I"), require=0)
    public int hookRenderScoreFormatted(GuiGraphics guiGraphics, net.minecraft.client.gui.Font font, FormattedCharSequence formattedCharSequence, int n2, int n3, int n4, boolean bl) {
        boolean bl2;
        Scoreboard x2 = com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a(Scoreboard.class);
        boolean bl3 = x2.m() && x2.c.m();
        boolean bl4 = x2.m() && x2.d.m();
        String string = MixinGui.flattenFormatted(formattedCharSequence);
        int n5 = MixinGui.resolveColorFromSequence(formattedCharSequence, n4);
        boolean bl5 = bl2 = bl3 && MixinGui.isRedColor(n5);
        if (bl2) {
            return 0;
        }
        Objects.requireNonNull(font);
        float f2 = 9.0f;
        if (bl4) {
            Font font2 = c.a(f2);
            float f3 = a.a(string, font2);
            FontMetrics fontMetrics = font2.getMetrics();
            float f4 = fontMetrics.getDescent() - fontMetrics.getAscent();
            x2.a(string, n2, n3, n5, f2);
            x2.a(f3, f4, n2, n3);
            return 0;
        }
        float f5 = font.m_92724_(formattedCharSequence);
        Objects.requireNonNull(font);
        float f6 = 9.0f;
        x2.a(f5, f6, n2, n3);
        return guiGraphics.m_280649_(font, formattedCharSequence, n2, n3, n5, bl);
    }

    private static String flattenFormatted(FormattedCharSequence formattedCharSequence) {
        StringBuilder stringBuilder = new StringBuilder();
        formattedCharSequence.m_13731_((n2, style, n3) -> {
            stringBuilder.appendCodePoint(n3);
            return true;
        });
        return stringBuilder.toString();
    }

    private static int resolveColorFromSequence(FormattedCharSequence formattedCharSequence, int n2) {
        int[] nArray = new int[]{n2};
        formattedCharSequence.m_13731_((n3, style, n4) -> {
            if (style != null && style.m_131135_() != null) {
                nArray[0] = MixinGui.resolveColorFromStyle(style, n2);
                return false;
            }
            return true;
        });
        return nArray[0];
    }

    private static int resolveColorFromStyle(Style style, int n2) {
        if (style == null) {
            return n2;
        }
        TextColor textColor = style.m_131135_();
        if (textColor == null) {
            return n2;
        }
        int n3 = n2 >> 24 & 0xFF;
        return n3 << 24 | textColor.m_131265_() & 0xFFFFFF;
    }

    private static boolean isRedColor(int n2) {
        int n3 = n2 & 0xFFFFFF;
        Integer n4 = ChatFormatting.RED.m_126665_();
        if (n4 != null && n3 == (n4 & 0xFFFFFF)) {
            return true;
        }
        Integer n5 = ChatFormatting.DARK_RED.m_126665_();
        if (n5 != null && n3 == (n5 & 0xFFFFFF)) {
            return true;
        }
        int n6 = n3 >> 16 & 0xFF;
        int n7 = n3 >> 8 & 0xFF;
        int n8 = n3 & 0xFF;
        return n6 > 160 && n7 < 80 && n8 < 80;
    }

    @Redirect(method={"displayScoreboardSidebar"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/gui/GuiGraphics;fill(IIIII)V"), require=0)
    public void hookScoreboardBackground(GuiGraphics guiGraphics, int n2, int n3, int n4, int n5, int n6) {
        Scoreboard x2 = com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a(Scoreboard.class);
        x2.a(n2, n3, n4, n5);
        if (!x2.m()) {
            guiGraphics.m_280509_(n2, n3, n4, n5, n6);
        }
    }

    @Redirect(method={"displayScoreboardSidebar"}, at=@At(value="INVOKE", target="Lnet/minecraft/world/scores/PlayerTeam;formatNameForTeam(Lnet/minecraft/world/scores/Team;Lnet/minecraft/network/chat/Component;)Lnet/minecraft/network/chat/MutableComponent;"))
    public MutableComponent hookScoreboardName(Team team, Component component) {
        MutableComponent mutableComponent = PlayerTeam.m_83348_((Team)team, (Component)component);
        ScoreboardTextEvent r2 = new ScoreboardTextEvent((Component)mutableComponent);
        com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().b().a(r2);
        return (MutableComponent)r2.a();
    }

    @Redirect(method={"displayScoreboardSidebar"}, at=@At(value="INVOKE", target="Lnet/minecraft/world/scores/Objective;getDisplayName()Lnet/minecraft/network/chat/Component;"))
    public Component hookScoreboardTitle(Objective objective) {
        Component component = objective.m_83322_();
        ScoreboardTextEvent r2 = new ScoreboardTextEvent(component);
        com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().b().a(r2);
        return r2.a();
    }

    @Inject(method={"setTitle"}, at={@At(value="HEAD")}, cancellable=true)
    public void hookTitle(Component component, CallbackInfo callbackInfo) {
        com.heypixel.heypixelmod.obsoverlay.events.impl.ChatComponentEvent x2 = new com.heypixel.heypixelmod.obsoverlay.events.impl.ChatComponentEvent(com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.h, component);
        com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().b().a(x2);
        if (!x2.a()) {
            this.f_93001_ = x2.c();
            this.f_93000_ = this.f_92970_ + this.f_92971_ + this.f_92972_;
            callbackInfo.cancel();
        }
    }

    @Inject(method={"setSubtitle"}, at={@At(value="RETURN")}, cancellable=true)
    public void hookSubtitle(Component component, CallbackInfo callbackInfo) {
        com.heypixel.heypixelmod.obsoverlay.events.impl.ChatComponentEvent x2 = new com.heypixel.heypixelmod.obsoverlay.events.impl.ChatComponentEvent(com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.i, component);
        com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().b().a(x2);
        if (!x2.a()) {
            this.f_93002_ = x2.c();
            callbackInfo.cancel();
        }
    }

    @Inject(method={"renderEffects"}, at={@At(value="HEAD")}, cancellable=true)
    public void hookRenderEffects(GuiGraphics guiGraphics, CallbackInfo callbackInfo) {
        NoRender u2 = com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a(NoRender.class);
        if (u2.m() && u2.c.m()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"renderCrosshair"}, at={@At(value="RETURN")})
    public void renderCrosshair(GuiGraphics guiGraphics, CallbackInfo callbackInfo) {
        try {
            com.heypixel.heypixelmod.obsoverlay.utils.RemotePlayer.a.a.b.a(com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a(PostProcess.class).p());
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a.a(canvas -> {
                a.c();
                a.a((float)Minecraft.m_91087_().m_91268_().m_85449_());
                com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().b().a(new CrosshairRenderEvent());
                a.d();
            });
        }
        catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}

