/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.mixin.O;

import com.heypixel.heypixelmod.obsoverlay.ClientVersion;
import com.heypixel.heypixelmod.obsoverlay.c.GameMenuScreen;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventRunTicks;
import com.heypixel.heypixelmod.obsoverlay.events.impl.KeybindActionEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.ClientShutdownEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.Glow;
import com.heypixel.heypixelmod.obsoverlay.utils.e.a.SkiaSurfaceRenderer;
import com.mojang.blaze3d.platform.Window;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.main.GameConfig;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.IModFileInfo;
import net.minecraftforge.forgespi.language.IModInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={Minecraft.class})
public class MixinMinecraft {
    @Unique
    private int skipTicks;
    @Unique
    private long naven_Modern$lastFrame;

    @Inject(method={"<init>"}, at={@At(value="RETURN")})
    public void onInit(GameConfig gameConfig, CallbackInfo callbackInfo) {
        System.setProperty("java.awt.headless", "false");
        ModList.get().getMods().removeIf(iModInfo -> iModInfo.getModId().contains("naven"));
        ArrayList<IModFileInfo> arrayList = new ArrayList<IModFileInfo>();
        for (IModFileInfo iModFileInfo : ModList.get().getModFiles()) {
            for (IModInfo iModInfo2 : iModFileInfo.getMods()) {
                if (!iModInfo2.getModId().contains("naven")) continue;
                arrayList.add(iModFileInfo);
            }
        }
        ModList.get().getModFiles().removeAll(arrayList);
    }

    @Inject(method={"close"}, at={@At(value="HEAD")}, remap=false)
    private void shutdown(CallbackInfo callbackInfo) {
        if (com.heypixel.heypixelmod.obsoverlay.EixClientCore.a() != null && com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().b() != null) {
            com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().b().a(new ClientShutdownEvent());
        }
    }

    @Inject(method={"tick"}, at={@At(value="HEAD")})
    private void tickPre(CallbackInfo callbackInfo) {
        if (com.heypixel.heypixelmod.obsoverlay.EixClientCore.a() != null && com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().b() != null) {
            com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().b().a(new EventRunTicks(com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.a));
        }
    }

    @Inject(method={"tick"}, at={@At(value="TAIL")})
    private void tickPost(CallbackInfo callbackInfo) {
        if (com.heypixel.heypixelmod.obsoverlay.EixClientCore.a() != null && com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().b() != null) {
            com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().b().a(new EventRunTicks(com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.b));
        }
    }

    @Inject(method={"shouldEntityAppearGlowing"}, at={@At(value="RETURN")}, cancellable=true)
    private void shouldEntityAppearGlowing(Entity entity, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (Glow.a(entity)) {
            callbackInfoReturnable.setReturnValue((Object)true);
        }
    }

    @Inject(method={"runTick"}, at={@At(value="HEAD")})
    private void runTick(CallbackInfo callbackInfo) {
        long l2 = System.nanoTime() / 1000000L;
        int n2 = (int)(l2 - this.naven_Modern$lastFrame);
        this.naven_Modern$lastFrame = l2;
        com.heypixel.heypixelmod.obsoverlay.utils.SmoothValueInterpolator.a = n2;
    }

    @ModifyArg(method={"runTick"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/renderer/GameRenderer;render(FJZ)V"))
    private float fixSkipTicks(float f2) {
        if (this.skipTicks > 0) {
            f2 = 0.0f;
        }
        return f2;
    }

    @ModifyVariable(method={"setScreen"}, at=@At(value="HEAD"), argsOnly=true)
    private Screen replaceTitleScreen(Screen screen) {
        if (screen instanceof TitleScreen) {
            return new GameMenuScreen();
        }
        return screen;
    }

    @Inject(method={"handleKeybinds"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/player/LocalPlayer;isUsingItem()Z", ordinal=0, shift=At.Shift.BEFORE)}, cancellable=true)
    private void clickEvent(CallbackInfo callbackInfo) {
        if (com.heypixel.heypixelmod.obsoverlay.EixClientCore.a() != null && com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().b() != null) {
            KeybindActionEvent d2 = new KeybindActionEvent();
            com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().b().a(d2);
            if (d2.a()) {
                callbackInfo.cancel();
            }
        }
    }

    @Inject(method={"resizeDisplay"}, at={@At(value="HEAD")})
    private void onResizeDisplay(CallbackInfo callbackInfo) {
        Window window = Minecraft.m_91087_().m_91268_();
        a.a(window.m_85441_(), window.m_85442_());
        com.heypixel.heypixelmod.obsoverlay.utils.RemotePlayer.a.a.a.a();
        com.heypixel.heypixelmod.obsoverlay.utils.RemotePlayer.a.a.b.a();
    }

    @Inject(method={"createTitle"}, at={@At(value="HEAD")}, cancellable=true)
    private void hookCreateTitle(CallbackInfoReturnable<String> callbackInfoReturnable) {
        callbackInfoReturnable.setReturnValue((Object)("EixClient | Eixfun | " + ClientVersion.a()));
    }
}

