/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules;

import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventMouseClick;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AntiBots;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.AutoTools;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.ChestStealer;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.ClientFriend;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.FastPlace;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.GhostBedMine;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.GhostChestOpen;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.GhostHand;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.Helper;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.EffectTags;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.MiddleClick;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.MusicPlayer;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.Target;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.Teams;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.c.AutoStuck;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.Projectiles;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.TargetHUD;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.TimeChanger;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.ViewClip;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.Watermark;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.Weather;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.NameProtect;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.NameTags;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.NoHurtCam;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.NoRender;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.Notification;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.PostProcess;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.Scoreboard;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.ServerNameSpoof;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.TargetESP;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModuleManager {
    private static final Logger a = LogManager.getLogger(ModuleManager.class);
    private final List<com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase> b = new ArrayList<com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase>();
    private final Map<Class<? extends com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase>, com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase> c = new HashMap<Class<? extends com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase>, com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase>();
    private final Map<String, com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase> d = new HashMap<String, com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase>();

    public ModuleManager() {
        try {
            com.heypixel.heypixelmod.obsoverlay.EixClientCore a2 = com.heypixel.heypixelmod.obsoverlay.EixClientCore.a();
            if (a2 != null && a2.b() != null) {
                a2.b().a(this);
            }
            this.b();
            this.b.sort((b2, b3) -> b2.b().compareToIgnoreCase(b3.b()));
        }
        catch (Exception exception) {
            a.error("Failed to initialize modules", (Throwable)exception);
            throw new RuntimeException(exception);
        }
    }

    public static ModuleManager a(String string) {
        return new ModuleManager();
    }

    public static String b(String string) {
        return string;
    }

    private void b() {
        this.a(new com.heypixel.heypixelmod.obsoverlay.modules.impl.a.KillAura(), new com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AutoThrow(), new com.heypixel.heypixelmod.obsoverlay.modules.impl.render.HUD(), new DynamicIsland(), new com.heypixel.heypixelmod.obsoverlay.modules.impl.a.Velocity(), new com.heypixel.heypixelmod.obsoverlay.modules.impl.a.TickBase(), new NameTags(), new TargetESP(), new ChestStealer(), new InventoryManager(), new com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Scaffold(), new AntiBots(), new com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Sprint(), new com.heypixel.heypixelmod.obsoverlay.modules.impl.render.ChestESP(), new com.heypixel.heypixelmod.obsoverlay.modules.impl.render.BedESP(), new com.heypixel.heypixelmod.obsoverlay.modules.impl.render.ClickGUI(), new Teams(), new com.heypixel.heypixelmod.obsoverlay.modules.impl.render.Glow(), new EffectTags(), new ClientFriend(), new GhostBedMine(), new GhostChestOpen(), new com.heypixel.heypixelmod.obsoverlay.modules.impl.c.NoJumpDelay(), new FastPlace(), new com.heypixel.heypixelmod.obsoverlay.modules.impl.b.AntiFireball(), new com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Stuck(), new AutoStuck(), new ServerNameSpoof(), new com.heypixel.heypixelmod.obsoverlay.modules.impl.b.AutoSoup(), new AutoTools(), new ViewClip(), new Projectiles(), new TimeChanger(), new Weather(), new com.heypixel.heypixelmod.obsoverlay.modules.impl.render.FullBright(), new NameProtect(), new NoHurtCam(), new com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AutoClicker(), new com.heypixel.heypixelmod.obsoverlay.modules.impl.render.AntiBlindness(), new com.heypixel.heypixelmod.obsoverlay.modules.impl.render.AntiNausea(), new Scoreboard(), new com.heypixel.heypixelmod.obsoverlay.modules.impl.render.Compass(), new com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Blink(), new com.heypixel.heypixelmod.obsoverlay.modules.impl.c.GrimFly(), new com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Fly(), new com.heypixel.heypixelmod.obsoverlay.modules.impl.c.FastWeb(), new PostProcess(), new com.heypixel.heypixelmod.obsoverlay.modules.impl.a.CrystalAura(), new com.heypixel.heypixelmod.obsoverlay.modules.impl.render.EffectHUD(), new NoRender(), new com.heypixel.heypixelmod.obsoverlay.modules.impl.render.ItemTags(), new com.heypixel.heypixelmod.obsoverlay.modules.impl.c.SafeWalk(), new com.heypixel.heypixelmod.obsoverlay.modules.impl.render.MotionBlur(), new Helper(), new com.heypixel.heypixelmod.obsoverlay.modules.impl.c.NoSlow(), new com.heypixel.heypixelmod.obsoverlay.modules.impl.c.LongJump(), new Target(), new com.heypixel.heypixelmod.obsoverlay.modules.impl.a.KeepSprint(), new com.heypixel.heypixelmod.obsoverlay.modules.impl.render.Animations(), new com.heypixel.heypixelmod.obsoverlay.modules.impl.render.Fov(), new GhostHand(), new com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Eagle(), new com.heypixel.heypixelmod.obsoverlay.modules.impl.render.HUDEditor(), new Watermark(), new TargetHUD(), new Notification(), new com.heypixel.heypixelmod.obsoverlay.modules.impl.b.AutoHeypixel(), new com.heypixel.heypixelmod.obsoverlay.modules.impl.render.KeyBinds(), new com.heypixel.heypixelmod.obsoverlay.modules.impl.render.ArrayList(), new com.heypixel.heypixelmod.obsoverlay.modules.impl.c.GrimLowHop(), new com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AimAssist(), new MiddleClick(), new MusicPlayer());
    }

    private void a(com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase ... bArray) {
        for (com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase b2 : bArray) {
            this.a(b2);
        }
    }

    private void a(com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase b2) {
        b2.c();
        this.b.add(b2);
        this.c.put(b2.getClass(), b2);
        this.d.put(b2.h().toLowerCase(), b2);
    }

    public List<com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase> a(com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory a2) {
        ArrayList<com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase> arrayList = new ArrayList<com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase>();
        for (com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase b2 : this.b) {
            if (b2.l() != a2) continue;
            arrayList.add(b2);
        }
        return arrayList;
    }

    public <T extends com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase> T a(Class<T> clazz) {
        com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase b2 = this.c.get(clazz);
        if (b2 == null) {
            return null;
        }
        return (T)((com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase)clazz.cast(b2));
    }

    public com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase c(String string) {
        com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase b2 = this.d.get(string.toLowerCase());
        if (b2 == null) {
            throw new com.heypixel.heypixelmod.obsoverlay.EixClientCore.b();
        }
        return b2;
    }

    public com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase d(String string) {
        if (string == null) {
            return null;
        }
        return this.d.get(string.toLowerCase());
    }

    @EventTarget
    public void onKey(com.heypixel.heypixelmod.obsoverlay.events.impl.KeyboardKeyPressEvent j2) {
        if (j2.c() && Minecraft.m_91087_().f_91080_ == null) {
            for (com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase b2 : this.b) {
                if (b2.o() != j2.b()) continue;
                b2.f();
            }
        }
    }

    @EventTarget
    public void onKey(EventMouseClick eventMouseClick) {
        if (!(eventMouseClick.state() || eventMouseClick.key() != 3 && eventMouseClick.key() != 4)) {
            for (com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase b2 : this.b) {
                if (b2.o() != -eventMouseClick.key()) continue;
                b2.f();
            }
        }
    }

    public List<com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase> a() {
        return this.b;
    }
}

