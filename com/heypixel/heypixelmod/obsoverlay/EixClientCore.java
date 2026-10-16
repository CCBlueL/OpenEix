/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay;

import cn.paradisemc.ZKMIndy;
import com.heypixel.heypixelmod.obsoverlay.b.ClientConfigurationManager;
import com.heypixel.heypixelmod.obsoverlay.d.SettingCollection;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventManager;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventRunTicks;
import com.heypixel.heypixelmod.obsoverlay.events.impl.ClientShutdownEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.ClickGUI;
import com.heypixel.heypixelmod.obsoverlay.music.nowplaying.NowPlayingStatusManager;
import com.heypixel.heypixelmod.obsoverlay.utils.ServerLagbackMonitor;
import com.heypixel.heypixelmod.obsoverlay.utils.SoundResourcePaths;
import com.heypixel.heypixelmod.obsoverlay.utils.EntityTrackingList;
import com.heypixel.heypixelmod.obsoverlay.utils.e.SkiaDrawingUtils;
import com.heypixel.heypixelmod.obsoverlay.utils.PlayerEffectTracker;
import com.heypixel.heypixelmod.obsoverlay.utils.ForgeClientEventBridge;
import com.heypixel.heypixelmod.obsoverlay.utils.AntiBotLogWriter;
import com.heypixel.heypixelmod.obsoverlay.utils.renderer.MiSansFontRenderer;
import com.heypixel.heypixelmod.obsoverlay.utils.renderer.FullscreenQuadRenderer;
import com.heypixel.heypixelmod.obsoverlay.utils.renderer.TextShaderProgram;
import com.heypixel.heypixelmod.obsoverlay.utils.PacketUtils;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.FontFormatException;
import java.io.IOException;
import javax.swing.text.html.parser.Entity;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.common.MinecraftForge;

@ZKMIndy
public class EixClientCore {
    public static final String a = "EixClient";
    public static final String b = "EixClient";
    public static float c = 1.0f;
    public static int d;
    private static volatile EixClientCore f;
    public boolean e = false;
    private EventManager g;
    private ForgeClientEventBridge h;
    private SettingCollection i;
    private com.heypixel.heypixelmod.obsoverlay.d.SettingRegistry j;
    private com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c k;
    private com.heypixel.heypixelmod.obsoverlay.modules.ModuleManager l;
    private com.heypixel.heypixelmod.obsoverlay.commands.ChatCommandRouter m;
    private ClientVersion n;
    private com.heypixel.heypixelmod.obsoverlay.c.MainMenuScreen.c o;

    private EixClientCore() {
        this.a(null, null);
    }

    public static EntityHitResult a(Entity entity) {
        RenderSystem.recordRenderCall(() -> {
            try {
                new EixClientCore();
            }
            catch (Exception exception) {
                System.err.println("Failed to load client");
                exception.printStackTrace(System.err);
            }
        });
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static EixClientCore a() {
        EixClientCore a2 = f;
        if (a2 != null) return a2;
        Class<EixClientCore> clazz = EixClientCore.class;
        synchronized (EixClientCore.class) {
            a2 = f;
            if (a2 != null) return a2;
            return new EixClientCore();
        }
    }

    public EntityHitResult a(Player player, Exception exception) {
        f = this;
        this.g = new EventManager();
        Window window = Minecraft.m_91087_().m_91268_();
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a.a(window.m_85441_(), window.m_85442_());
        com.heypixel.heypixelmod.obsoverlay.utils.renderer.TextShaderProgram.a();
        com.heypixel.heypixelmod.obsoverlay.utils.renderer.FullscreenQuadRenderer.a();
        try {
            com.heypixel.heypixelmod.obsoverlay.utils.renderer.MiSansFontRenderer.a();
        }
        catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
        catch (FontFormatException fontFormatException) {
            throw new RuntimeException(fontFormatException);
        }
        this.h = new ForgeClientEventBridge();
        this.i = new SettingCollection();
        this.j = new com.heypixel.heypixelmod.obsoverlay.d.SettingRegistry();
        this.l = com.heypixel.heypixelmod.obsoverlay.modules.ModuleManager.a("\u52a0\u8f7d\u5ba2\u6237\u7aef");
        this.k = new com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c();
        this.m = new com.heypixel.heypixelmod.obsoverlay.commands.ChatCommandRouter();
        this.n = new ClientVersion();
        this.o = new com.heypixel.heypixelmod.obsoverlay.c.MainMenuScreen.c();
        this.n.b();
        this.l.a(ClickGUI.class).a(false);
        this.g.a(com.heypixel.heypixelmod.obsoverlay.EixClientCore.a());
        this.g.a(this.h);
        this.g.a(new com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c());
        this.g.a(new PacketUtils());
        this.g.a(new ServerLagbackMonitor());
        this.g.a(new PlayerEffectTracker());
        this.g.a(new EixClientCore.a());
        this.g.a(com.heypixel.heypixelmod.obsoverlay.music.MusicLibraryManager.a());
        MinecraftForge.EVENT_BUS.register((Object)this.h);
        this.e = true;
        com.heypixel.heypixelmod.obsoverlay.music.MusicLibraryManager.a().b();
        SoundResourcePaths.a("opening.wav", 1.0f);
        return null;
    }

    @EventTarget
    public void onShutdown(ClientShutdownEvent y2) {
        this.n.c();
        try {
            com.heypixel.heypixelmod.obsoverlay.music.nowplaying.NowPlayingStatusManager.a().c();
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        AntiBotLogWriter.a();
    }

    @EventTarget(a=0)
    public void onEarlyTick(EventRunTicks eventRunTicks) {
        if (eventRunTicks.type() == com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.a) {
            EntityTrackingList.a();
        }
    }

    public EventManager b() {
        return this.g;
    }

    public ForgeClientEventBridge c() {
        return this.h;
    }

    public SettingCollection d() {
        return this.i;
    }

    public com.heypixel.heypixelmod.obsoverlay.d.SettingRegistry e() {
        return this.j;
    }

    public com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.c f() {
        return this.k;
    }

    public com.heypixel.heypixelmod.obsoverlay.modules.ModuleManager g() {
        return this.l;
    }

    public com.heypixel.heypixelmod.obsoverlay.commands.ChatCommandRouter h() {
        return this.m;
    }

    public ClientVersion i() {
        return this.n;
    }

    public com.heypixel.heypixelmod.obsoverlay.c.MainMenuScreen.c j() {
        return this.o;
    }
}

