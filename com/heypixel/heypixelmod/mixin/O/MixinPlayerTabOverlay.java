/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.mixin.O;

import com.heypixel.heypixelmod.obsoverlay.EixClientCore;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerListTextEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland;
import java.util.List;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Scoreboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={PlayerTabOverlay.class})
public abstract class MixinPlayerTabOverlay {
    @Shadow
    public abstract Component m_94549_(PlayerInfo var1);

    @Inject(method={"render"}, at={@At(value="HEAD")}, cancellable=true)
    private void cancelRender(GuiGraphics guiGraphics, int n2, Scoreboard scoreboard, Objective objective, CallbackInfo callbackInfo) {
        DynamicIsland dynamicIslandHud = EixClientCore.a().g().a(DynamicIsland.class);
        if (dynamicIslandHud != null && dynamicIslandHud.m()) {
            callbackInfo.cancel();
        }
    }

    @Redirect(method={"render"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/gui/Font;split(Lnet/minecraft/network/chat/FormattedText;I)Ljava/util/List;", ordinal=0))
    public List<FormattedCharSequence> hookHeader(Font font, FormattedText formattedText, int n2) {
        Component component = (Component)formattedText;
        PlayerListTextEvent t2 = new PlayerListTextEvent(com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.g, component);
        EixClientCore.a().b().a(t2);
        return font.m_92923_((FormattedText)t2.b(), n2);
    }

    @Redirect(method={"render"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/gui/Font;split(Lnet/minecraft/network/chat/FormattedText;I)Ljava/util/List;", ordinal=1))
    public List<FormattedCharSequence> hookFooter(Font font, FormattedText formattedText, int n2) {
        Component component = (Component)formattedText;
        PlayerListTextEvent t2 = new PlayerListTextEvent(com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.f, component);
        EixClientCore.a().b().a(t2);
        return font.m_92923_((FormattedText)t2.b(), n2);
    }

    @Redirect(method={"render"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/gui/components/PlayerTabOverlay;getNameForDisplay(Lnet/minecraft/client/multiplayer/PlayerInfo;)Lnet/minecraft/network/chat/Component;"))
    public Component hookName(PlayerTabOverlay playerTabOverlay, PlayerInfo playerInfo) {
        Component component = this.m_94549_(playerInfo);
        PlayerListTextEvent t2 = new PlayerListTextEvent(com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.e, component);
        EixClientCore.a().b().a(t2);
        return t2.b();
    }
}

