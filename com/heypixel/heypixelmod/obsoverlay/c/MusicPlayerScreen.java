/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.c;

import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.HUD;
import com.heypixel.heypixelmod.obsoverlay.music.a.OnlineMusicApiClient;
import com.heypixel.heypixelmod.obsoverlay.music.a.MusicCollectionResult;
import com.heypixel.heypixelmod.obsoverlay.music.a.PlaybackTimelineModel;
import com.heypixel.heypixelmod.obsoverlay.music.MusicPlaybackEngine;
import com.heypixel.heypixelmod.obsoverlay.music.MusicLibraryManager;
import com.heypixel.heypixelmod.obsoverlay.utils.EntityOverlayRenderer;
import com.heypixel.heypixelmod.obsoverlay.utils.e.b.BundledFontRegistry;
import com.heypixel.heypixelmod.obsoverlay.utils.BackgroundExecutor;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Font;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.Path;
import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class MusicPlayerScreen
extends Screen {
    private static final float a = 440.0f;
    private static final float b = 300.0f;
    private static final float c = 40.0f;
    private static final float d = 62.0f;
    private static final float e = 22.0f;
    private static final float f = 150.0f;
    private final com.heypixel.heypixelmod.obsoverlay.music.MusicLibraryManager g = com.heypixel.heypixelmod.obsoverlay.music.MusicLibraryManager.a();
    private final List<com.heypixel.heypixelmod.obsoverlay.music.MusicTrackMetadata> h = new ArrayList<com.heypixel.heypixelmod.obsoverlay.music.MusicTrackMetadata>();
    private SourceTab i = com.heypixel.heypixelmod.obsoverlay.c.MusicPlayerScreen.SourceTab.a;
    private String j = "";
    private float k = 0.0f;
    private float l = 0.0f;
    private int m = -1;
    private int n = -1;
    private String o = "";
    private final List<i> p = new ArrayList<i>();
    private volatile boolean q = false;
    private volatile String r = "";
    private volatile String s = "";
    private float t = 0.0f;
    private float u = 0.0f;
    private int v = -1;
    private float w = 0.0f;
    private boolean x = false;
    private boolean y = false;
    private float z = -1.0f;
    private float A;
    private float B;
    private float C;

    public MusicPlayerScreen() {
        super((Component)Component.m_237113_((String)"Music Player"));
    }

    protected void a() {
        this.g.b();
        this.d();
    }

    private void d() {
        this.h.clear();
        String string = this.j.toLowerCase(Locale.ROOT).trim();
        for (com.heypixel.heypixelmod.obsoverlay.music.MusicTrackMetadata d2 : this.g.e()) {
            if (!string.isEmpty() && !d2.h().toLowerCase(Locale.ROOT).contains(string)) continue;
            this.h.add(d2);
        }
        this.n = this.g.f();
    }

    private float e() {
        return this.B + 40.0f;
    }

    private float f() {
        return this.e() + this.C + 4.0f;
    }

    private float g() {
        return this.A + 10.0f;
    }

    private float h() {
        return 420.0f;
    }

    public void a(GuiGraphics guiGraphics, int n2, int n3, float f2) {
        this.A = ((float)this.f_96543_ - 440.0f) / 2.0f;
        this.B = ((float)this.f_96544_ - 300.0f) / 2.0f;
        this.C = 194.0f;
        if (this.g.f() != this.n) {
            this.d();
        }
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a.a((Canvas canvas) -> {
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c();
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a((float)Minecraft.m_91087_().m_91268_().m_85449_());
            this.a(n2, n3);
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.d();
        });
        super.m_88315_(guiGraphics, n2, n3, f2);
    }

    private void a(int n2, int n3) {
        com.heypixel.heypixelmod.obsoverlay.music.MusicPlaybackEngine b2 = this.g.c();
        Color color = com.heypixel.heypixelmod.obsoverlay.c.MusicPlayerScreen.m();
        Font font = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.c.a(14.0f);
        Font font2 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.c.a(11.0f);
        Font font3 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.c.a(9.0f);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c(this.A, this.B, 440.0f, 300.0f, 8.0f);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(this.A, this.B, 440.0f, 300.0f, 8.0f);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(this.A, this.B, 440.0f, 300.0f, 8.0f, new Color(14, 14, 16, 228));
        float f2 = 24.0f;
        this.i();
        File file = com.heypixel.heypixelmod.obsoverlay.music.AudioFormatSupport.b.a().b();
        if (file != null) {
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(file, this.A + 10.0f, this.B + 7.0f, f2, f2, 5.0f);
        } else {
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(this.A + 10.0f, this.B + 7.0f, f2, f2, 5.0f, new Color(255, 255, 255, 20));
        }
        float f3 = this.A + 10.0f + f2 + 8.0f;
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a("\u97f3\u4e50\u64ad\u653e\u5668", f3, this.B + 8.0f, Color.WHITE, font);
        float f4 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a("\u97f3\u4e50\u64ad\u653e\u5668", font);
        String string = this.i == com.heypixel.heypixelmod.obsoverlay.c.MusicPlayerScreen.SourceTab.b ? (this.r.isEmpty() ? "\u5728\u7ebf\u89e3\u6790" : this.r) : this.h.size() + " / " + this.g.f() + " \u9996";
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string, font3, 150.0f), f3 + 6.0f + f4, this.B + 12.0f, new Color(165, 165, 165), font3);
        float f5 = this.A + 440.0f - 12.0f - 150.0f;
        this.a(f5 - 48.0f, this.B + 9.0f, font2, font3, color);
        this.a(f5, this.B + 9.0f, 150.0f, font2);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(this.A + 10.0f, this.B + 40.0f - 6.0f, 420.0f, 1.0f, new Color(255, 255, 255, 25));
        this.m = -1;
        this.v = -1;
        if (this.i == com.heypixel.heypixelmod.obsoverlay.c.MusicPlayerScreen.SourceTab.b) {
            this.k();
            this.b(n2, n3, font2, font3, color);
        } else {
            this.j();
            this.a(n2, n3, font2, font3, color);
        }
        this.a(n2, n3, font2, font3, color, b2);
    }

    private void a(float f2, float f3, Font font, Font font2, Color color) {
        boolean bl = this.i == com.heypixel.heypixelmod.obsoverlay.c.MusicPlayerScreen.SourceTab.b;
        float f4 = 44.0f;
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f2, f3, f4, 16.0f, 8.0f, bl ? new Color(color.getRed(), color.getGreen(), color.getBlue(), 150) : new Color(255, 255, 255, 24));
        String string = bl ? "\u5728\u7ebf" : "\u672c\u5730";
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string, f2 + (f4 - com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string, font2)) / 2.0f, f3 + 4.0f, Color.WHITE, font2);
    }

    private boolean a(double d2, double d3) {
        float f2 = this.A + 440.0f - 12.0f - 150.0f;
        return EntityOverlayRenderer.a((int)d2, (int)d3, f2 - 48.0f, this.B + 9.0f, f2 - 4.0f, this.B + 25.0f);
    }

    private void i() {
        i i2 = this.g.o();
        if (i2 != null) {
            com.heypixel.heypixelmod.obsoverlay.music.AudioFormatSupport.b.a().a(i2.b(), i2.c(), i2.a());
            return;
        }
        com.heypixel.heypixelmod.obsoverlay.music.MusicTrackMetadata d2 = this.g.h();
        if (d2 == null) {
            com.heypixel.heypixelmod.obsoverlay.music.AudioFormatSupport.b.a().d();
            return;
        }
        com.heypixel.heypixelmod.obsoverlay.music.AudioFormatSupport.b.a().a(d2.b(), d2.c(), -1L);
    }

    private void j() {
        float f2 = (float)this.h.size() * 22.0f;
        float f3 = Math.max(0.0f, f2 - this.C);
        if (this.l > f3) {
            this.l = f3;
        }
        if (this.l < 0.0f) {
            this.l = 0.0f;
        }
        this.k += (this.l - this.k) * 0.35f;
    }

    private void a(int n2, int n3, Font font, Font font2, Color color) {
        float f2;
        float f3 = this.g();
        float f4 = this.e();
        float f5 = this.h();
        if (this.h.isEmpty()) {
            String string = this.g.k() ? "\u6b63\u5728\u626b\u63cf\u97f3\u4e50\u6587\u4ef6\u5939..." : "\u6ca1\u6709\u627e\u5230\u97f3\u4e50\u6587\u4ef6";
            String string2 = this.g.k() ? "" : "\u628a\u97f3\u9891\u653e\u5165 " + this.g.d().getAbsolutePath();
            float f6 = f3 + f5 / 2.0f;
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string, f6 - com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string, font) / 2.0f, f4 + this.C / 2.0f - 12.0f, new Color(190, 190, 190), font);
            if (!string2.isEmpty()) {
                com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string2, font2, f5 - 20.0f), f6 - com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string2, font2, f5 - 20.0f), font2) / 2.0f, f4 + this.C / 2.0f + 6.0f, new Color(140, 140, 140), font2);
            }
            return;
        }
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c();
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.d(f3, f4, f5, this.C, 4.0f);
        com.heypixel.heypixelmod.obsoverlay.music.MusicTrackMetadata d2 = this.g.h();
        com.heypixel.heypixelmod.obsoverlay.music.MusicPlaybackEngine b2 = this.g.c();
        boolean bl = b2.c() == b.a.PLAYING;
        for (int i2 = 0; i2 < this.h.size(); ++i2) {
            boolean bl2;
            f2 = f4 + (float)i2 * 22.0f - this.k;
            if (f2 + 22.0f < f4 || f2 > f4 + this.C) continue;
            com.heypixel.heypixelmod.obsoverlay.music.MusicTrackMetadata d3 = this.h.get(i2);
            boolean bl3 = !this.g.n() && d2 != null && d3.equals(d2);
            boolean bl4 = bl2 = (float)n2 >= f3 && (float)n2 <= f3 + f5 && (float)n3 >= f2 && (float)n3 <= f2 + 22.0f;
            if (bl2) {
                this.m = i2;
            }
            if (bl3) {
                com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f3, f2, f5, 20.0f, 4.0f, new Color(color.getRed(), color.getGreen(), color.getBlue(), 55));
            } else if (bl2) {
                com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f3, f2, f5, 20.0f, 4.0f, new Color(255, 255, 255, 18));
            }
            String string = String.format(Locale.ROOT, "%02d", i2 + 1);
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string, f3 + 6.0f, f2 + 6.0f, new Color(150, 150, 150), font2);
            float f7 = f3 + 26.0f;
            float f8 = 42.0f;
            float f9 = f5 - (f7 - f3) - f8 - 10.0f;
            String string3 = (bl3 && bl ? "> " : "") + d3.h();
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string3, font, f9), f7, f2 + 4.0f, bl3 ? Color.WHITE : new Color(225, 225, 225), font);
            String string4 = d3.e() ? com.heypixel.heypixelmod.obsoverlay.music.MusicTrackMetadata.b(d3.d()) : "--:--";
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string4, f3 + f5 - 8.0f - com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string4, font2), f2 + 6.0f, new Color(150, 150, 150), font2);
        }
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.d();
        float f10 = (float)this.h.size() * 22.0f;
        if (f10 > this.C) {
            f2 = this.C / f10;
            float f11 = Math.max(20.0f, this.C * f2);
            float f12 = f10 - this.C;
            float f13 = f12 > 0.0f ? this.k / f12 * (this.C - f11) : 0.0f;
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f3 + f5 - 3.0f, f4 + f13, 3.0f, f11, 1.5f, new Color(255, 255, 255, 60));
        }
    }

    private void a(int n2, int n3, Font font, Font font2, Color color, com.heypixel.heypixelmod.obsoverlay.music.MusicPlaybackEngine b2) {
        float f4 = this.g();
        float f5 = this.f();
        float f6 = this.h();
        long l2 = b2.j();
        long l3 = this.z >= 0.0f && l2 > 0L ? (long)(this.z * (float)l2) : b2.k();
        float f7 = l2 > 0L ? Math.max(0.0f, Math.min(1.0f, (float)l3 / (float)l2)) : 0.0f;
        float f8 = f5 + 6.0f;
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f4, f8, f6, 4.0f, 2.0f, new Color(255, 255, 255, 40));
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f4, f8, Math.max(4.0f, f6 * f7), 4.0f, 2.0f, color);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f4 + f6 * f7, f8 + 2.0f, 4.0f, Color.WHITE);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(com.heypixel.heypixelmod.obsoverlay.music.MusicTrackMetadata.b(l3), f4, f8 + 11.0f, new Color(190, 190, 190), font2);
        String string = com.heypixel.heypixelmod.obsoverlay.music.MusicTrackMetadata.b(l2);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string, f4 + f6 - com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string, font2), f8 + 11.0f, new Color(190, 190, 190), font2);
        float f9 = f5 + 28.0f;
        float f10 = f4 + f6 / 2.0f;
        boolean bl = b2.c() == b.a.PLAYING;
        this.a(f10 - 48.0f, f9, 24.0f, n2, n3, color, this::b);
        this.a(f10 - 15.0f, f9, 30.0f, n2, n3, color, (float f2, float f3) -> {
            if (bl) {
                this.a(f2, f3, Color.WHITE);
            } else {
                this.a(f2, f3);
            }
        });
        this.a(f10 + 24.0f, f9, 24.0f, n2, n3, color, this::c);
        String string2 = "\u5faa\u73af: " + this.g.j().a();
        float f11 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string2, font2) + 16.0f;
        this.a(f4, f9 + 4.0f, f11, this.g.j() != c.a.OFF ? color : new Color(255, 255, 255, 30), string2, font2);
        String string3 = "\u968f\u673a";
        float f12 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string3, font2) + 16.0f;
        this.a(f4 + f11 + 6.0f, f9 + 4.0f, f12, this.g.i() ? color : new Color(255, 255, 255, 30), string3, font2);
        float f13 = b2.i();
        float f14 = f4 + f6 - 98.0f;
        float f15 = 70.0f;
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a("\u97f3\u91cf", f14 - 26.0f, f9 + 3.0f, new Color(190, 190, 190), font2);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f14, f9 + 8.0f, f15, 4.0f, 2.0f, new Color(255, 255, 255, 40));
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f14, f9 + 8.0f, Math.max(2.0f, f15 * f13), 4.0f, 2.0f, color);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f14 + f15 * f13, f9 + 10.0f, 4.0f, Color.WHITE);
        String string4 = Math.round(f13 * 100.0f) + "%";
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string4, f4 + f6 - com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string4, font2), f9 + 3.0f, new Color(190, 190, 190), font2);
    }

    private void a(float f2, float f3, float f4, Font font) {
        Color color;
        String string;
        boolean bl = this.i == com.heypixel.heypixelmod.obsoverlay.c.MusicPlayerScreen.SourceTab.b;
        String string2 = bl ? this.o : this.j;
        boolean bl2 = bl || !this.j.isEmpty();
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f2, f3, f4, 16.0f, 4.0f, new Color(255, 255, 255, bl ? 26 : 16));
        if (bl && System.currentTimeMillis() % 1000L < 500L) {
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f2 + f4 - 2.0f, f3 + 2.0f, 1.0f, 12.0f, new Color(255, 255, 255, 160));
        }
        if (string2.isEmpty()) {
            string = bl ? "\u7c98\u8d34\u94fe\u63a5/ID/\u6b4c\u540d\uff0c\u56de\u8f66" : "\u641c\u7d22... (F5 \u91cd\u65b0\u626b\u63cf)";
            color = new Color(140, 140, 140);
        } else {
            string = string2;
            color = Color.WHITE;
        }
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string, font, f4 - 12.0f), f2 + 6.0f, f3 + 4.0f, color, font);
        if (bl2 && !string2.isEmpty()) {
            float f5 = f2 + 6.0f + com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string, font, f4 - 12.0f), font);
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(Math.min(f5, f2 + f4 - 5.0f), f3 + 3.0f, 1.0f, 10.0f, new Color(255, 255, 255, 170));
        }
    }

    private void k() {
        float f2 = (float)this.p.size() * 22.0f;
        float f3 = Math.max(0.0f, f2 - this.C);
        if (this.u > f3) {
            this.u = f3;
        }
        if (this.u < 0.0f) {
            this.u = 0.0f;
        }
        this.t += (this.u - this.t) * 0.35f;
        this.w += 1.0f;
    }

    private void l() {
        String string = this.o.trim();
        if (string.isEmpty() || this.q) {
            return;
        }
        this.q = true;
        this.s = "\u89e3\u6790\u4e2d\u2026";
        this.r = "";
        this.p.clear();
        this.u = 0.0f;
        com.heypixel.heypixelmod.obsoverlay.utils.BackgroundExecutor.a(() -> {
            try {
                Object object;
                List<Object> list;
                Object object2;
                g g2 = com.heypixel.heypixelmod.obsoverlay.music.AudioFormatSupport.g.a();
                String[] stringArray = com.heypixel.heypixelmod.obsoverlay.music.AudioFormatSupport.d.b(string);
                if (!stringArray[1].isEmpty() && !"song".equals(stringArray[0])) {
                    object2 = g2.e(string);
                    list = ((h)object2).h();
                    object = ((h)object2).a().a() + "\uff1a" + ((h)object2).c();
                } else if (!stringArray[1].isEmpty()) {
                    object2 = g2.b(Long.parseLong(stringArray[1]));
                    list = object2 == null ? List.of() : List.of(object2);
                    object = list.isEmpty() ? "\u5355\u66f2" : "\u5355\u66f2\uff1a" + ((i)object2).b();
                } else {
                    list = g2.d(string);
                    object = "\u641c\u7d22\uff1a" + string;
                }
                object2 = new ArrayList<i>(list);
                com.heypixel.heypixelmod.obsoverlay.c.MusicPlayerScreen.a(() -> com.heypixel.heypixelmod.obsoverlay.c.MusicPlayerScreen.TrackRowPainter(object2, (String)object));
            }
            catch (Throwable throwable) {
                String string2 = throwable.getMessage();
                String string3 = string2 == null || string2.isBlank() ? throwable.getClass().getSimpleName() : string2;
                com.heypixel.heypixelmod.obsoverlay.c.MusicPlayerScreen.a(() -> {
                    this.q = false;
                    this.s = "\u89e3\u6790\u5931\u8d25\uff1a" + string3;
                });
            }
        });
    }

    private static void a(Runnable runnable) {
        try {
            Minecraft minecraft = Minecraft.m_91087_();
            if (minecraft != null) {
                minecraft.execute(runnable);
                return;
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        runnable.run();
    }

    private void a(i i2) {
        this.s = "\u5f00\u59cb\u4e0b\u8f7d\uff1a" + i2.b();
        com.heypixel.heypixelmod.obsoverlay.utils.BackgroundExecutor.a(() -> {
            try {
                File file = com.heypixel.heypixelmod.obsoverlay.music.AudioFormatSupport.g.a().a(i2, (g.b)null);
                com.heypixel.heypixelmod.obsoverlay.c.MusicPlayerScreen.a(() -> {
                    this.s = "\u5df2\u4e0b\u8f7d\uff1a" + file.getName();
                    this.g.l();
                });
            }
            catch (Throwable throwable) {
                String string = throwable.getMessage();
                String string2 = string == null || string.isBlank() ? throwable.getClass().getSimpleName() : string;
                com.heypixel.heypixelmod.obsoverlay.c.MusicPlayerScreen.a(() -> {
                    this.s = "\u4e0b\u8f7d\u5931\u8d25\uff1a" + string2;
                });
            }
        });
    }

    private void b(int n2, int n3, Font font, Font font2, Color color) {
        float f2;
        float f3 = this.g();
        float f4 = this.e();
        float f5 = this.h();
        if (this.p.isEmpty()) {
            String string;
            float f6 = f3 + f5 / 2.0f;
            String string2 = this.q ? "\u6b63\u5728\u89e3\u6790\u2026" : (string = this.s.isEmpty() ? "\u7c98\u8d34\u6b4c\u66f2 / \u6b4c\u5355 / \u4e13\u8f91\u94fe\u63a5\u6216 ID" : this.s);
            String string3 = this.q ? "" : (this.s.isEmpty() ? "\u4e5f\u53ef\u4ee5\u76f4\u63a5\u8f93\u5165\u6b4c\u540d\u641c\u7d22\uff0c\u56de\u8f66\u6267\u884c" : "");
            Color color2 = this.s.startsWith("\u89e3\u6790\u5931\u8d25") || this.s.startsWith("\u6ca1\u6709\u627e\u5230") ? new Color(240, 130, 130) : new Color(190, 190, 190);
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string, f6 - com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string, font) / 2.0f, f4 + this.C / 2.0f - 12.0f, color2, font);
            if (!string3.isEmpty()) {
                com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string3, f6 - com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string3, font2) / 2.0f, f4 + this.C / 2.0f + 6.0f, new Color(140, 140, 140), font2);
            }
            if (this.q) {
                int n4 = (int)(this.w / 12.0f % 3.0f);
                for (int i2 = 0; i2 < 3; ++i2) {
                    float f7 = f6 - 12.0f + (float)i2 * 12.0f;
                    float f8 = f4 + this.C / 2.0f + 26.0f;
                    int n5 = i2 == n4 ? 220 : 70;
                    com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f7, f8, 2.2f, new Color(255, 255, 255, n5));
                }
            }
            return;
        }
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c();
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.d(f3, f4, f5, this.C, 4.0f);
        i i3 = this.g.o();
        for (int i4 = 0; i4 < this.p.size(); ++i4) {
            String string;
            boolean bl;
            f2 = f4 + (float)i4 * 22.0f - this.t;
            if (f2 + 22.0f < f4 || f2 > f4 + this.C) continue;
            i i5 = this.p.get(i4);
            boolean bl2 = i3 != null && i3.a() == i5.a();
            boolean bl3 = bl = (float)n2 >= f3 && (float)n2 <= f3 + f5 && (float)n3 >= f2 && (float)n3 <= f2 + 22.0f;
            if (bl) {
                this.v = i4;
            }
            if (bl2) {
                com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f3, f2, f5, 20.0f, 4.0f, new Color(color.getRed(), color.getGreen(), color.getBlue(), 55));
            } else if (bl) {
                com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f3, f2, f5, 20.0f, 4.0f, new Color(255, 255, 255, 18));
            }
            String string4 = String.format(Locale.ROOT, "%02d", i4 + 1);
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string4, f3 + 6.0f, f2 + 6.0f, new Color(150, 150, 150), font2);
            float f9 = f3 + 26.0f;
            float f10 = 78.0f;
            float f11 = f5 - (f9 - f3) - f10 - 10.0f;
            String string5 = (bl2 ? "> " : "") + i5.b() + " - " + i5.c();
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string5, font, f11), f9, f2 + 4.0f, bl2 ? Color.WHITE : new Color(225, 225, 225), font);
            String string6 = i5.k();
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string6, f3 + f5 - 8.0f - com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string6, font2), f2 + 6.0f, new Color(150, 150, 150), font2);
            String string7 = i5.i() ? "\u7248\u6743" : (string = i5.g() ? "\u514d\u8d39" : "VIP");
            Color color3 = i5.i() ? new Color(230, 120, 120) : (i5.g() ? new Color(130, 200, 150) : new Color(220, 190, 120));
            float f12 = f3 + f5 - 12.0f - com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string6, font2) - 26.0f;
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string, f12, f2 + 6.0f, color3, font2);
        }
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.d();
        float f13 = (float)this.p.size() * 22.0f;
        if (f13 > this.C) {
            f2 = this.C / f13;
            float f14 = Math.max(20.0f, this.C * f2);
            float f15 = f13 - this.C;
            float f16 = f15 > 0.0f ? this.t / f15 * (this.C - f14) : 0.0f;
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f3 + f5 - 3.0f, f4 + f16, 3.0f, f14, 1.5f, new Color(255, 255, 255, 60));
        }
    }

    private void a(float f2, float f3, float f4, int n2, int n3, Color color, TrackRowPainter a2) {
        boolean bl = EntityOverlayRenderer.a(n2, n3, f2, f3, f2 + f4, f3 + f4);
        Color color2 = bl ? new Color(color.getRed(), color.getGreen(), color.getBlue(), 90) : new Color(255, 255, 255, 22);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f2, f3, f4, f4, 6.0f, color2);
        a2.paint(f2 + f4 / 2.0f, f3 + f4 / 2.0f);
    }

    private void a(float f2, float f3, float f4, Color color, String string, Font font) {
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f2, f3, f4, 16.0f, 8.0f, color);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string, f2 + 8.0f, f3 + 4.0f, Color.WHITE, font);
    }

    private void a(float f2, float f3) {
        this.a(f2 - 4.0f, f3 - 6.0f, f2 - 4.0f, f3 + 6.0f, f2 + 6.0f, f3, Color.WHITE);
    }

    private void a(float f2, float f3, Color color) {
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f2 - 5.0f, f3 - 6.0f, 3.5f, 12.0f, 1.0f, color);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f2 + 1.5f, f3 - 6.0f, 3.5f, 12.0f, 1.0f, color);
    }

    private void b(float f2, float f3) {
        this.a(f2 - 2.0f, f3 - 6.0f, f2 - 2.0f, f3 + 6.0f, f2 + 5.0f, f3, Color.WHITE);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f2 - 6.0f, f3 - 6.0f, 2.5f, 12.0f, Color.WHITE);
    }

    private void c(float f2, float f3) {
        this.a(f2 + 2.0f, f3 - 6.0f, f2 + 2.0f, f3 + 6.0f, f2 - 5.0f, f3, Color.WHITE);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f2 + 3.5f, f3 - 6.0f, 2.5f, 12.0f, Color.WHITE);
    }

    private void a(float f2, float f3, float f4, float f5, float f6, float f7, Color color) {
        try (Path path = new Path();
             Paint paint = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(color);){
            path.moveTo(f2, f3);
            path.lineTo(f4, f5);
            path.lineTo(f6, f7);
            path.closePath();
            paint.setAntiAlias(true);
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().drawPath(path, paint);
        }
    }

    public boolean a(double d2, double d3, int n2) {
        if (this.a(d2, d3)) {
            this.i = this.i == com.heypixel.heypixelmod.obsoverlay.c.MusicPlayerScreen.SourceTab.a ? com.heypixel.heypixelmod.obsoverlay.c.MusicPlayerScreen.SourceTab.b : com.heypixel.heypixelmod.obsoverlay.c.MusicPlayerScreen.SourceTab.a;
            this.l = 0.0f;
            this.u = 0.0f;
            return true;
        }
        float f2 = this.g();
        float f3 = this.e();
        float f4 = this.h();
        if (d2 >= (double)f2 && d2 <= (double)(f2 + f4) && d3 >= (double)f3 && d3 <= (double)(f3 + this.C)) {
            if (this.i == com.heypixel.heypixelmod.obsoverlay.c.MusicPlayerScreen.SourceTab.b) {
                if (this.v >= 0 && this.v < this.p.size()) {
                    i i2 = this.p.get(this.v);
                    if (n2 == 1) {
                        this.a(i2);
                    } else {
                        this.g.b(this.v);
                    }
                    return true;
                }
                return true;
            }
            if (n2 == 0 && this.m >= 0 && this.m < this.h.size()) {
                this.g.a(this.h.get(this.m));
            }
            return true;
        }
        if (n2 != 0) {
            return super.m_6375_(d2, d3, n2);
        }
        float f5 = this.f();
        float f6 = f2 + f4 / 2.0f;
        float f7 = f5 + 28.0f;
        if (EntityOverlayRenderer.a((int)d2, (int)d3, f2, f5, f2 + f4, f5 + 14.0f)) {
            this.x = true;
            this.a(d2, f2, f4);
            return true;
        }
        if (EntityOverlayRenderer.a((int)d2, (int)d3, f6 - 48.0f, f7, f6 - 24.0f, f7 + 24.0f)) {
            this.g.t();
            return true;
        }
        if (EntityOverlayRenderer.a((int)d2, (int)d3, f6 - 15.0f, f7, f6 + 15.0f, f7 + 30.0f)) {
            this.g.m();
            return true;
        }
        if (EntityOverlayRenderer.a((int)d2, (int)d3, f6 + 24.0f, f7, f6 + 48.0f, f7 + 24.0f)) {
            this.g.s();
            return true;
        }
        Font font = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.c.a(9.0f);
        float f8 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a("\u5faa\u73af: " + this.g.j().a(), font) + 16.0f;
        if (EntityOverlayRenderer.a((int)d2, (int)d3, f2, f7 + 4.0f, f2 + f8, f7 + 20.0f)) {
            com.heypixel.heypixelmod.obsoverlay.music.MusicLibraryManager.LoopMode[] aArray = c.a.values();
            this.g.a(aArray[(this.g.j().ordinal() + 1) % aArray.length]);
            return true;
        }
        float f9 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a("\u968f\u673a", font) + 16.0f;
        if (EntityOverlayRenderer.a((int)d2, (int)d3, f2 + f8 + 6.0f, f7 + 4.0f, f2 + f8 + 6.0f + f9, f7 + 20.0f)) {
            this.g.a(!this.g.i());
            return true;
        }
        float f10 = f2 + f4 - 98.0f;
        if (EntityOverlayRenderer.a((int)d2, (int)d3, f10 - 6.0f, f7, f10 + 74.0f, f7 + 20.0f)) {
            this.y = true;
            this.a(d2, f10);
            return true;
        }
        return super.m_6375_(d2, d3, n2);
    }

    public boolean a(double d2, double d3, int n2, double d4, double d5) {
        float f2 = this.g();
        float f3 = this.h();
        if (this.x) {
            this.a(d2, f2, f3);
            return true;
        }
        if (this.y) {
            this.a(d2, f2 + f3 - 98.0f);
            return true;
        }
        return super.m_7979_(d2, d3, n2, d4, d5);
    }

    public boolean b(double d2, double d3, int n2) {
        if (this.x) {
            this.x = false;
            long l2 = this.g.c().j();
            if (l2 > 0L && this.z >= 0.0f) {
                this.g.c().a((long)(this.z * (float)l2));
            }
            this.z = -1.0f;
            return true;
        }
        if (this.y) {
            this.y = false;
            return true;
        }
        return super.m_6348_(d2, d3, n2);
    }

    private void a(double d2, float f2, float f3) {
        float f4 = (float)((d2 - (double)f2) / (double)f3);
        this.z = Math.max(0.0f, Math.min(1.0f, f4));
    }

    private void a(double d2, float f2) {
        float f3 = (float)((d2 - (double)f2) / 70.0);
        com.heypixel.heypixelmod.obsoverlay.music.MusicLibraryManager.LoopMode(Math.max(0.0f, Math.min(1.0f, f3)));
    }

    public boolean a(double d2, double d3, double d4) {
        if (this.i == com.heypixel.heypixelmod.obsoverlay.c.MusicPlayerScreen.SourceTab.b) {
            this.u -= (float)d4 * 24.0f;
            float f2 = Math.max(0.0f, (float)this.p.size() * 22.0f - this.C);
            this.u = Math.max(0.0f, Math.min(f2, this.u));
            return true;
        }
        this.l -= (float)d4 * 24.0f;
        float f3 = Math.max(0.0f, (float)this.h.size() * 22.0f - this.C);
        this.l = Math.max(0.0f, Math.min(f3, this.l));
        return true;
    }

    public boolean a(int n2, int n3, int n4) {
        if (n2 == 256) {
            this.b();
            return true;
        }
        if (n2 == 258) {
            this.i = this.i == com.heypixel.heypixelmod.obsoverlay.c.MusicPlayerScreen.SourceTab.a ? com.heypixel.heypixelmod.obsoverlay.c.MusicPlayerScreen.SourceTab.b : com.heypixel.heypixelmod.obsoverlay.c.MusicPlayerScreen.SourceTab.a;
            return true;
        }
        if (n2 == 294) {
            this.g.l();
            this.d();
            return true;
        }
        if (n2 == 259) {
            if (this.i == com.heypixel.heypixelmod.obsoverlay.c.MusicPlayerScreen.SourceTab.b) {
                if (!this.o.isEmpty()) {
                    this.o = this.o.substring(0, this.o.length() - 1);
                }
            } else if (!this.j.isEmpty()) {
                this.j = this.j.substring(0, this.j.length() - 1);
                this.d();
            }
            return true;
        }
        if (n2 == 257 || n2 == 335) {
            if (this.i == com.heypixel.heypixelmod.obsoverlay.c.MusicPlayerScreen.SourceTab.b) {
                this.l();
            } else {
                this.g.m();
            }
            return true;
        }
        if (n2 == 32) {
            this.g.m();
            return true;
        }
        if (n2 == 265) {
            this.l -= 22.0f;
            this.u -= 22.0f;
            return true;
        }
        if (n2 == 264) {
            this.l += 22.0f;
            this.u += 22.0f;
            return true;
        }
        if (n2 == 86 && (n4 & 2) != 0 && this.i == com.heypixel.heypixelmod.obsoverlay.c.MusicPlayerScreen.SourceTab.b) {
            try {
                String string = Minecraft.m_91087_().f_91068_.m_90876_();
                if (string != null && !string.isBlank()) {
                    this.o = this.o + string.trim();
                }
            }
            catch (Throwable throwable) {
                // empty catch block
            }
            return true;
        }
        return super.m_7933_(n2, n3, n4);
    }

    public boolean a(char c2, int n2) {
        if (c2 >= ' ' && c2 != '\u007f') {
            if (this.i == com.heypixel.heypixelmod.obsoverlay.c.MusicPlayerScreen.SourceTab.b) {
                if (this.o.length() < 200) {
                    this.o = this.o + c2;
                }
            } else {
                this.j = this.j + c2;
                this.d();
            }
            return true;
        }
        return super.m_5534_(c2, n2);
    }

    public void b() {
        this.g.l();
        super.m_7379_();
    }

    public boolean c() {
        return false;
    }

    private static Color m() {
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

    private /* synthetic */ void a(List list, String string) {
        this.p.clear();
        this.p.addAll(list);
        this.r = string + " (" + list.size() + ")";
        this.s = list.isEmpty() ? "\u6ca1\u6709\u627e\u5230\u7ed3\u679c" : "";
        this.q = false;
        if (!list.isEmpty()) {
            this.g.a(list);
        }
    }

    private static enum SourceTab {
        a,
        b;

    }

    private static interface TrackRowPainter {
        public void paint(float var1, float var2);
    }
}

