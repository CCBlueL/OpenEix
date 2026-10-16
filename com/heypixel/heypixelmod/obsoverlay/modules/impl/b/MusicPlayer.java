/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.b;

import com.heypixel.heypixelmod.obsoverlay.EixClientCore;
import com.heypixel.heypixelmod.obsoverlay.d.a.ModeSetting;
import com.heypixel.heypixelmod.obsoverlay.d.a.StringSetting;
import com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.CrosshairRenderEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.HUD;
import com.heypixel.heypixelmod.obsoverlay.music.MusicPlaybackEngine;
import com.heypixel.heypixelmod.obsoverlay.music.MusicLibraryManager;
import com.heypixel.heypixelmod.obsoverlay.music.nowplaying.NowPlayingStatusManager;
import com.heypixel.heypixelmod.obsoverlay.utils.BackgroundExecutor;
import io.github.humbleui.skija.Font;
import java.awt.Color;
import java.io.File;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

@ModuleInfo(a="MusicPlayer", b="\u97f3\u4e50\u64ad\u653e\u5668", c="\u64ad\u653e\u672c\u5730\u97f3\u4e50\u6587\u4ef6 (MP3 / OGG / WAV / AIFF)", d=com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory.MISC)
public class MusicPlayer
extends b {
    public com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a c = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Show HUD").a(true).a().b();
    public ChestStealer d = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "HUD Style").a("Full", "Compact").a(0).a(() -> this.c.m()).a().e();
    public com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a e = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Background").a(true).a(() -> this.c.m()).a().b();
    public com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a f = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Progress Bar").a(true).a(() -> this.c.m()).a().b();
    public com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a g = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Show Time").a(true).a(() -> this.c.m()).a().b();
    public com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a h = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Equalizer Icon").a(true).a(() -> this.c.m()).a().b();
    public com.heypixel.heypixelmod.obsoverlay.d.SettingModel.c i = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Volume").a(70.0f).d(1.0f).b(0.0f).c(100.0f).a((com.heypixel.heypixelmod.obsoverlay.d.SettingValue c2) -> com.heypixel.heypixelmod.obsoverlay.music.MusicLibraryManager.a().a(c2.c().q() / 100.0f)).a().c();
    public ChestStealer j = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Loop Mode").a("Off", "All", "One").a(1).a((com.heypixel.heypixelmod.obsoverlay.d.SettingValue c2) -> com.heypixel.heypixelmod.obsoverlay.music.MusicLibraryManager.a().a(c.a.values()[c2.e().o()])).a().e();
    public com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a k = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Shuffle").a(false).a((com.heypixel.heypixelmod.obsoverlay.d.SettingValue c2) -> com.heypixel.heypixelmod.obsoverlay.music.MusicLibraryManager.a().a(c2.b().m())).a().b();
    public ClientFriend l = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Music Folder").a(com.heypixel.heypixelmod.obsoverlay.modules.impl.b.MusicPlayer.r()).a((com.heypixel.heypixelmod.obsoverlay.d.SettingValue c2) -> com.heypixel.heypixelmod.obsoverlay.music.MusicLibraryManager.a().a(c2.d().n())).a().d();
    public com.heypixel.heypixelmod.obsoverlay.d.SettingModel.b m = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Position").e(10.0f).f(220.0f).a().f();
    public com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a n = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Netease Sync").a(true).a((com.heypixel.heypixelmod.obsoverlay.d.SettingValue c2) -> this.q()).a().b();
    public com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a o = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Other Players").a(true).a((com.heypixel.heypixelmod.obsoverlay.d.SettingValue c2) -> this.q()).a().b();
    public com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a p = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "SMTC Sync").a(false).a((com.heypixel.heypixelmod.obsoverlay.d.SettingValue c2) -> this.q()).a().b();
    public com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a q = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Fetch Lyrics").a(true).a((com.heypixel.heypixelmod.obsoverlay.d.SettingValue c2) -> com.heypixel.heypixelmod.obsoverlay.music.lyric.LyricParser.a().a(c2.b().m())).a().b();
    public com.heypixel.heypixelmod.obsoverlay.d.SettingModel.c r = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Lyric Offset (ms)").a(0.0f).d(50.0f).b(-5000.0f).c(5000.0f).a(() -> this.q.m()).a((com.heypixel.heypixelmod.obsoverlay.d.SettingValue c2) -> com.heypixel.heypixelmod.obsoverlay.music.lyric.LyricParser.a().a((long)c2.c().q())).a().c();
    public com.heypixel.heypixelmod.obsoverlay.d.SettingModel.d s = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Play / Pause Key").b(91).a().g();
    public com.heypixel.heypixelmod.obsoverlay.d.SettingModel.d t = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Next Key").b(93).a().g();
    public com.heypixel.heypixelmod.obsoverlay.d.SettingModel.d u = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Previous Key").b(92).a().g();
    public com.heypixel.heypixelmod.obsoverlay.d.SettingModel.d v = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Volume Up Key").b(61).a().g();
    public com.heypixel.heypixelmod.obsoverlay.d.SettingModel.d w = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Volume Down Key").b(45).a().g();
    public com.heypixel.heypixelmod.obsoverlay.d.SettingModel.d x = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Playlist Key").b(260).a().g();

    private static String r() {
        String string = System.getenv("APPDATA");
        File file = string != null && !string.isBlank() ? new File(string, "EixClient") : new File(System.getProperty("user.home"), "EixClient");
        return new File(file, "music").getAbsolutePath();
    }

    public static void p() {
        Minecraft.m_91087_().m_91152_((Screen)new com.heypixel.heypixelmod.obsoverlay.c.MusicPlayerScreen());
    }

    @Override
    protected void c() {
        super.c();
        this.b(0);
    }

    @Override
    public void d() {
        try {
            com.heypixel.heypixelmod.obsoverlay.music.MusicLibraryManager c2 = com.heypixel.heypixelmod.obsoverlay.music.MusicLibraryManager.a();
            c2.b();
            com.heypixel.heypixelmod.obsoverlay.utils.BackgroundExecutor.a(c2::l);
            this.q();
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    @Override
    public void e() {
        com.heypixel.heypixelmod.obsoverlay.music.nowplaying.NowPlayingStatusManager.a().c();
    }

    public void q() {
        try {
            boolean bl;
            c c2 = com.heypixel.heypixelmod.obsoverlay.music.nowplaying.NowPlayingStatusManager.a();
            c2.b(this.n.m());
            c2.c(this.o.m());
            c2.a(this.p.m());
            com.heypixel.heypixelmod.obsoverlay.music.lyric.LyricParser.a().a(this.q.m());
            com.heypixel.heypixelmod.obsoverlay.music.lyric.LyricParser.a().a((long)this.r.q());
            boolean bl2 = bl = this.n.m() || this.o.m() || this.p.m();
            if (this.m() && bl) {
                c2.e();
            } else {
                c2.c();
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    @EventTarget
    public void onRenderSkia(CrosshairRenderEvent s2) {
        if (!this.c.m()) {
            return;
        }
        try {
            this.s();
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    private void s() {
        com.heypixel.heypixelmod.obsoverlay.music.MusicLibraryManager c2 = com.heypixel.heypixelmod.obsoverlay.music.MusicLibraryManager.a();
        com.heypixel.heypixelmod.obsoverlay.music.MusicPlaybackEngine b2 = c2.c();
        com.heypixel.heypixelmod.obsoverlay.music.MusicTrackMetadata d2 = c2.h();
        float f2 = this.m.n();
        float f3 = this.m.o();
        boolean bl = this.d.a("Compact");
        float f4 = bl ? this.b(f2, f3, c2, b2, d2) : this.a(f2, f3, c2, b2, d2);
        this.m.c(f4);
        this.m.d(bl ? 20.0f : 54.0f);
    }

    private float a(float f2, float f3, com.heypixel.heypixelmod.obsoverlay.music.MusicLibraryManager c2, com.heypixel.heypixelmod.obsoverlay.music.MusicPlaybackEngine b2, com.heypixel.heypixelmod.obsoverlay.music.MusicTrackMetadata d2) {
        boolean bl;
        float f4 = 190.0f;
        float f5 = 54.0f;
        float f6 = 10.0f;
        Color color = com.heypixel.heypixelmod.obsoverlay.modules.impl.b.MusicPlayer.t();
        boolean bl2 = bl = b2.c() == b.a.PLAYING;
        if (this.e.m()) {
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c(f2, f3, f4, f5, 6.0f);
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f2, f3, f4, f5, 6.0f);
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f2, f3, f4, f5, 6.0f, new Color(0, 0, 0, 120));
        }
        float f7 = f2 + f6;
        if (this.h.m()) {
            this.a(f2 + f6, f3 + 8.0f, 14.0f, color, bl);
            f7 = f2 + f6 + 20.0f;
        }
        float f8 = this.g.m() ? 62.0f : 6.0f;
        float f9 = f4 - (f7 - f2) - f6 - f8;
        Font font = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.c.a(12.0f);
        Font font2 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.c.a(9.0f);
        if (d2 == null) {
            String string = c2.k() ? "\u626b\u63cf\u97f3\u4e50\u4e2d..." : "\u6ca1\u6709\u97f3\u4e50\u6587\u4ef6";
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string, font, f9), f7, f3 + 9.0f, new Color(255, 255, 255, 190), font);
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a("\u653e\u5165 " + com.heypixel.heypixelmod.obsoverlay.modules.impl.b.MusicPlayer.a(c2.d()), f7, f3 + 26.0f, new Color(170, 170, 170), font2);
            return f4;
        }
        String string = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(d2.b(), font, f9);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string, f7, f3 + 9.0f, Color.WHITE, font);
        String string2 = d2.c().isEmpty() ? "\u672a\u77e5\u827a\u672f\u5bb6" : d2.c();
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string2, font2, f9), f7, f3 + 27.0f, new Color(175, 175, 175), font2);
        if (this.g.m()) {
            String string3 = com.heypixel.heypixelmod.obsoverlay.music.MusicTrackMetadata.b(b2.k()) + " / " + com.heypixel.heypixelmod.obsoverlay.music.MusicTrackMetadata.b(b2.j());
            float f10 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string3, font2);
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string3, f2 + f4 - f6 - f10, f3 + 27.0f, new Color(200, 200, 200), font2);
        }
        if (this.f.m()) {
            this.a(f2 + f6, f3 + 42.0f, f4 - f6 * 2.0f, 3.0f, b2, color);
        }
        return f4;
    }

    private float b(float f2, float f3, com.heypixel.heypixelmod.obsoverlay.music.MusicLibraryManager c2, com.heypixel.heypixelmod.obsoverlay.music.MusicPlaybackEngine b2, com.heypixel.heypixelmod.obsoverlay.music.MusicTrackMetadata d2) {
        Object object;
        Object object2;
        float f4 = 20.0f;
        float f5 = 6.0f;
        Color color = com.heypixel.heypixelmod.obsoverlay.modules.impl.b.MusicPlayer.t();
        boolean bl = b2.c() == b.a.PLAYING;
        Font font = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.c.a(11.0f);
        if (d2 == null) {
            object2 = c2.k() ? "\u626b\u63cf\u97f3\u4e50\u4e2d..." : "\u6ca1\u6709\u97f3\u4e50\u6587\u4ef6";
        } else {
            object = d2.c().isEmpty() ? "" : d2.c() + " - ";
            object2 = (String)object + d2.b();
        }
        if (b2.c() == b.a.PAUSED) {
            object2 = "[\u6682\u505c] " + (String)object2;
        }
        object = "";
        if (this.g.m() && d2 != null) {
            object = com.heypixel.heypixelmod.obsoverlay.music.MusicTrackMetadata.b(b2.k()) + "/" + com.heypixel.heypixelmod.obsoverlay.music.MusicTrackMetadata.b(b2.j());
        }
        float f6 = this.h.m() ? 16.0f : 0.0f;
        float f7 = ((String)object).isEmpty() ? 0.0f : com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a((String)object, font) + 6.0f;
        float f8 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a((String)object2, font);
        float f9 = f5 + f6 + f8 + f7 + f5;
        f9 = Math.max(f9, 90.0f);
        if (this.e.m()) {
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f2, f3, f9, f4, 4.0f);
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f2, f3, f9, f4, 4.0f, new Color(0, 0, 0, 120));
        }
        float f10 = f2 + f5;
        if (this.h.m()) {
            this.a(f10, f3 + 4.0f, 12.0f, color, bl);
            f10 += f6;
        }
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a((String)object2, f10, f3 + 5.0f, Color.WHITE, font);
        if (!((String)object).isEmpty()) {
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a((String)object, f2 + f9 - f5 - com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a((String)object, font), f3 + 5.0f, new Color(190, 190, 190), font);
        }
        if (this.f.m() && d2 != null) {
            this.a(f2 + f5, f3 + f4 - 2.5f, f9 - f5 * 2.0f, 1.5f, b2, color);
        }
        return f9;
    }

    private void a(float f2, float f3, float f4, float f5, com.heypixel.heypixelmod.obsoverlay.music.MusicPlaybackEngine b2, Color color) {
        long l2 = b2.j();
        long l3 = b2.k();
        float f6 = l2 > 0L ? Math.max(0.0f, Math.min(1.0f, (float)l3 / (float)l2)) : 0.0f;
        float f7 = f5 / 2.0f;
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f2, f3, f4, f5, f7, new Color(255, 255, 255, 45));
        if (f6 > 0.0f) {
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f2, f3, Math.max(f5, f4 * f6), f5, f7, color);
            float f8 = f2 + f4 * f6;
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f8, f3 + f7, f7 + 1.0f, Color.WHITE);
        }
    }

    private void a(float f2, float f3, float f4, Color color, boolean bl) {
        int n2 = 4;
        float f5 = f4 * 0.16f;
        float f6 = (f4 - f5 * (float)(n2 - 1)) / (float)n2;
        double d2 = (double)System.currentTimeMillis() / 220.0;
        for (int i2 = 0; i2 < n2; ++i2) {
            float f7 = bl ? (float)(0.35 + 0.65 * Math.abs(Math.sin(d2 + (double)i2 * 0.9))) : 0.25f + (float)i2 * 0.05f;
            float f8 = Math.max(2.0f, f4 * f7);
            float f9 = f2 + (float)i2 * (f6 + f5);
            float f10 = f3 + (f4 - f8) / 2.0f;
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f9, f10, f6, f8, f6 / 2.0f, color);
        }
    }

    private static Color t() {
        try {
            HUD m2 = com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a(HUD.class);
            if (m2 != null) {
                return com.heypixel.heypixelmod.obsoverlay.modules.impl.render.HUD.p();
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        return new Color(102, 255, 209);
    }

    private static String a(File file) {
        if (file == null) {
            return "";
        }
        String string = file.getAbsolutePath();
        return string.length() > 42 ? "..." + string.substring(string.length() - 39) : string;
    }
}

