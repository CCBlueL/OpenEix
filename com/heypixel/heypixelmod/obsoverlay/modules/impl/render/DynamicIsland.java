/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.render;

import com.heypixel.heypixelmod.obsoverlay.d.a.ModeSetting;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PacketInterceptEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.CrosshairRenderEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerListTextEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerRespawnEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Scaffold;
import com.heypixel.heypixelmod.obsoverlay.utils.RotationInterpolation;
import com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils;
import io.github.humbleui.skija.ClipMode;
import io.github.humbleui.skija.FilterTileMode;
import io.github.humbleui.skija.Font;
import io.github.humbleui.skija.FontMetrics;
import io.github.humbleui.skija.ImageFilter;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.PaintMode;
import io.github.humbleui.skija.PaintStrokeCap;
import io.github.humbleui.skija.Path;
import io.github.humbleui.types.RRect;
import io.github.humbleui.types.Rect;
import java.awt.Color;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoRemovePacket;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;

@ModuleInfo(a="DynamicIsland", b="\u7075\u52a8\u5c9b", c="", d=com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory.RENDER)
public class DynamicIsland
extends com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase {
    private static final DateTimeFormatter c = DateTimeFormatter.ofPattern("HH:mm");
    private static volatile Component d;
    private static volatile Component e;
    private static volatile List<PlayerInfo> f;
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a g = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Bloom").a(true).a().b();
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a h = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Blur").a(true).a().b();
    private final BedESP i = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Style").a("Island", "Capsule").a(0).a().e();
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.c j = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Radius").a(6.0f).b(0.0f).c(15.0f).d(1.0f).a(() -> this.i.a("Island")).a().c();
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.c k = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "YOffset").a(8.0f).b(0.0f).c(200.0f).d(1.0f).a().c();
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a l = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Music Island").a(true).a().b();
    private final BedESP m = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Music Layout").a("Full", "Lyrics").a(0).a(() -> this.l.m()).a().e();
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.c n = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Music Max Width").a(340.0f).b(140.0f).c(560.0f).d(10.0f).a(() -> this.l.m()).a().c();
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a o = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Lyric Translation").a(true).a(() -> this.l.m()).a().b();
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a p = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Music Progress Bar").a(true).a(() -> this.l.m()).a().b();
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a q = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Next Lyric Line").a(false).a(() -> this.l.m()).a().b();
    private static ToggleInfo r;
    private static final Queue<ToggleInfo> s;
    private static final Set<UUID> t;
    private long u = -1L;
    private long v = -1L;
    private float w = 90.0f;
    private AnimationState x = com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland.AnimationState.a;
    private float y;
    private float z = 1.0f;
    private float A;
    private float B;
    private float C;
    private float D;
    private float E;
    private float F = 65.0f;
    private float G = 65.0f;
    private float H = 19.0f;
    private final RotationInterpolation I = new RotationInterpolation(0.0f, 0.0f, 0.12f);
    private static final float[] J;
    private static final long K = 280L;
    private static final float L = 4.5f;
    private String M = "";
    private String N = "";
    private float O = 1.0f;
    private long P = 0L;
    private String Q = "";
    private float R = -1.0f;
    private boolean S = true;
    private float T = 65.0f;
    private List<PlayerInfo> U;
    private float V;
    private float W;

    public static void a(com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase b2, boolean bl) {
        if (b2 instanceof DynamicIsland) {
            return;
        }
        ToggleCategory d2 = com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland.ToggleCategory.a;
        if (b2 instanceof Scaffold) {
            d2 = com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland.ToggleCategory.b;
        } else if (b2 instanceof com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Blink) {
            d2 = com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland.ToggleCategory.c;
        }
        s.offer(new ToggleInfo(b2.h(), bl, d2));
    }

    public static void a(Player player) {
        if (player == null) {
            return;
        }
        t.add(player.m_20148_());
    }

    private static void b(String string) {
        if (string == null || string.isEmpty()) {
            return;
        }
        s.offer(new ToggleInfo("You killed " + string + " !", true, com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland.ToggleCategory.d));
    }

    private static void p() {
        s.offer(new ToggleInfo("Victory!", true, com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland.ToggleCategory.e));
    }

    @EventTarget
    public void onRenderSkia(CrosshairRenderEvent s2) {
        this.q();
        this.C();
        if (this.A()) {
            this.P();
        }
    }

    @EventTarget
    public void onRenderTab(PlayerListTextEvent t2) {
        if (t2.a() == com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.g) {
            d = t2.b();
        } else if (t2.a() == com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.f) {
            e = t2.b();
        }
    }

    @EventTarget
    public void onPacket(PacketInterceptEvent m2) {
        if (m2.b() != com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.d || a.m_91403_() == null) {
            return;
        }
        Object object = m2.c();
        if (object instanceof ClientboundPlayerInfoRemovePacket) {
            ClientboundPlayerInfoRemovePacket clientboundPlayerInfoRemovePacket = (ClientboundPlayerInfoRemovePacket)object;
            for (UUID uUID : clientboundPlayerInfoRemovePacket.f_244383_()) {
                if (!t.contains(uUID)) continue;
                PlayerInfo playerInfo = a.m_91403_().m_104949_(uUID);
                if (playerInfo != null) {
                    DynamicIsland.b(playerInfo.m_105312_().getName());
                }
                t.remove(uUID);
            }
        } else {
            object = m2.c();
            if (object instanceof ClientboundSetTitleTextPacket) {
                ClientboundSetTitleTextPacket clientboundSetTitleTextPacket = (ClientboundSetTitleTextPacket)object;
                if (DynamicIsland.d(clientboundSetTitleTextPacket.m_179399_().getString())) {
                    DynamicIsland.p();
                }
            } else {
                ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket;
                object = m2.c();
                if (object instanceof ClientboundSetSubtitleTextPacket && DynamicIsland.d((clientboundSetSubtitleTextPacket = (ClientboundSetSubtitleTextPacket)object).m_179385_().getString())) {
                    DynamicIsland.p();
                }
            }
        }
    }

    private static boolean d(String string) {
        if (string == null || string.isEmpty()) {
            return false;
        }
        String string2 = string.toLowerCase(Locale.ROOT);
        return string2.contains("\u80dc\u5229") || string2.contains("\u83b7\u80dc") || string2.contains("victory") || string2.contains("you win");
    }

    @EventTarget
    public void onRespawn(PlayerRespawnEvent u2) {
        t.clear();
        s.clear();
    }

    private void q() {
        this.v();
        this.x();
        try {
            this.r();
        }
        catch (Throwable throwable) {
            this.M = "";
            this.Q = "";
        }
        this.y();
        this.u();
    }

    private void r() {
        if (!this.l.m()) {
            if (!this.M.isEmpty()) {
                this.M = "";
                this.N = "";
                this.O = 1.0f;
                this.Q = "";
            }
            return;
        }
        com.heypixel.heypixelmod.obsoverlay.music.MusicPlayerController e2 = com.heypixel.heypixelmod.obsoverlay.music.MusicPlayerController.a();
        e2.b();
        com.heypixel.heypixelmod.obsoverlay.music.lyric.LyricParser b2 = com.heypixel.heypixelmod.obsoverlay.music.lyric.LyricParser.a();
        if (b2.c() != this.o.m()) {
            b2.b(this.o.m());
        }
        String string = b2.d();
        String string2 = e2.l();
        float f2 = this.n.q();
        boolean bl = this.m.a("Full");
        long l2 = System.currentTimeMillis();
        if (!string.equals(this.M)) {
            this.N = this.M;
            this.P = l2;
            this.O = 0.0f;
            this.M = string;
            this.Q = string2;
            this.R = f2;
            this.S = bl;
            this.T = this.a(string, string2);
        } else if (!string2.equals(this.Q) || f2 != this.R || bl != this.S) {
            this.Q = string2;
            this.R = f2;
            this.S = bl;
            this.T = this.a(string, string2);
        }
        if (this.O < 1.0f) {
            float f3 = (float)(l2 - this.P) / 280.0f;
            this.O = f3 >= 1.0f ? 1.0f : DynamicIsland.a(f3);
        }
    }

    private static float a(float f2) {
        float f3 = 1.0f - f2;
        return 1.0f - f3 * f3 * f3;
    }

    private boolean s() {
        return this.l.m() && com.heypixel.heypixelmod.obsoverlay.music.MusicPlayerController.a().c();
    }

    private float t() {
        if (!this.s()) {
            return 19.0f;
        }
        return this.m.a("Full") ? 36.0f : 19.0f;
    }

    private static Font a(String string, float f2) {
        for (float f3 : J) {
            Font font = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.c.a(f3);
            if (!(com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string, font) <= f2)) continue;
            return font;
        }
        return com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.c.a(8.5f);
    }

    private float a(String string, String string2) {
        String string3;
        float f2 = this.n.q();
        float f3 = f2 - 14.0f - 6.0f;
        String string4 = string3 = string.isEmpty() ? string2 : string;
        if (string3.isEmpty()) {
            return 65.0f;
        }
        Font font = DynamicIsland.a(string3, f3);
        float f4 = Math.min(f3, com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string3, font));
        if (this.m.a("Full") && !string2.isEmpty() && !string2.equals(string3)) {
            Font font2 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.c.a(8.5f);
            f4 = Math.max(f4, Math.min(f3, com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string2, font2)));
        }
        float f5 = f4 + 14.0f + 6.0f;
        return Math.max(65.0f, Math.min(f2, f5));
    }

    private void b(float f2) {
        float f3;
        Font font;
        if (f2 <= 0.05f) {
            return;
        }
        com.heypixel.heypixelmod.obsoverlay.music.MusicPlayerController e2 = com.heypixel.heypixelmod.obsoverlay.music.MusicPlayerController.a();
        com.heypixel.heypixelmod.obsoverlay.music.lyric.LyricParser b2 = com.heypixel.heypixelmod.obsoverlay.music.lyric.LyricParser.a();
        String string = b2.d();
        String string2 = e2.l();
        if (string.isEmpty() && string2.isEmpty()) {
            this.f(f2);
            return;
        }
        boolean bl = this.m.a("Full");
        float f4 = Math.max(10.0f, this.C - 14.0f);
        float f5 = Math.min(f4, this.n.q() - 14.0f - 6.0f);
        int n2 = (int)(255.0f * f2);
        Color color = DynamicIsland.a(new Color(190, 190, 190), (int)(215.0f * f2));
        String string3 = string.isEmpty() ? string2 : string;
        Font font2 = font = DynamicIsland.a(string3, f5);
        String string4 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string3, font, f4);
        String string5 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(this.N, font2, f4);
        boolean bl2 = this.O < 0.999f && !this.N.isEmpty();
        float f6 = bl2 ? (float)n2 * (1.0f - this.O) : 0.0f;
        float f7 = f3 = bl2 ? (float)n2 * this.O : (float)n2;
        if (bl) {
            String string6;
            if (!string2.isEmpty()) {
                Font font3 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.c.a(8.5f);
                string6 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string2, font3, f4);
                float f8 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string6, font3);
                com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string6, this.A + (this.C - f8) / 2.0f, this.B + 4.0f, color, font3);
            }
            float f9 = this.B + 14.0f;
            if (bl2 && !string5.isEmpty()) {
                this.a(string5, font2, f9 - 4.5f * this.O, (int)f6, false);
            }
            if (!string4.isEmpty()) {
                float f10 = f9 + (bl2 ? 4.5f * (1.0f - this.O) : 0.0f);
                this.a(string4, font, f10, (int)f3, true);
            }
            if ((string6 = b2.e()).isEmpty() && this.q.m()) {
                string6 = b2.f();
            }
            if (!string6.isEmpty()) {
                Font font4 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.c.a(8.5f);
                String string7 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string6, font4, f4);
                float f11 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string7, font4);
                int n3 = (int)(215.0f * f2 * (bl2 ? this.O : 1.0f));
                com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string7, this.A + (this.C - f11) / 2.0f, this.B + 25.0f, DynamicIsland.a(new Color(190, 190, 190), n3), font4);
            }
            if (this.p.m()) {
                this.c(f2);
            }
        } else {
            float f12 = this.B + this.D / 2.0f - (this.p.m() ? 1.5f : 0.0f);
            float f13 = DynamicIsland.a(f12, font);
            if (bl2 && !string5.isEmpty()) {
                this.a(string5, font2, f13 - 4.5f * this.O, (int)f6, false);
            }
            if (!string4.isEmpty()) {
                float f14 = f13 + (bl2 ? 4.5f * (1.0f - this.O) : 0.0f);
                this.a(string4, font, f14, (int)f3, true);
            }
            if (this.p.m()) {
                this.c(f2);
            }
        }
    }

    private void a(String string, Font font, float f2, int n2, boolean bl) {
        if (string == null || string.isEmpty() || n2 <= 2) {
            return;
        }
        float f3 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string, font);
        float f4 = this.A + (this.C - f3) / 2.0f;
        if (!bl) {
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string, f4, f2, DynamicIsland.a(Color.WHITE, n2), font);
            return;
        }
        float f5 = com.heypixel.heypixelmod.obsoverlay.music.lyric.LyricParser.a().j();
        if (f5 < 0.0f || f5 >= 0.999f) {
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string, f4, f2, DynamicIsland.a(Color.WHITE, n2), font);
            return;
        }
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string, f4, f2, DynamicIsland.a(Color.WHITE, Math.max(24, (int)((float)n2 * 0.38f))), font);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c();
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.d(f4 - 1.0f, this.B - 2.0f, Math.max(0.5f, f3 * f5 + 1.0f), this.D + 4.0f, 0.0f);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string, f4, f2, DynamicIsland.a(Color.WHITE, n2), font);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.d();
    }

    private void c(float f2) {
        float f3 = com.heypixel.heypixelmod.obsoverlay.music.MusicPlayerController.a().h();
        if (f3 <= 0.0f) {
            return;
        }
        long l2 = com.heypixel.heypixelmod.obsoverlay.music.MusicPlayerController.a().g();
        float f4 = DynamicIsland.m((float)l2 / f3);
        float f5 = this.A + 7.0f;
        float f6 = Math.max(4.0f, this.C - 14.0f);
        float f7 = 2.0f;
        float f8 = this.B + this.D - f7 - 3.0f;
        float f9 = f7 / 2.0f;
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f5, f8, f6, f7, f9, DynamicIsland.a(new Color(255, 255, 255), (int)(55.0f * f2)));
        if (f4 > 0.0f) {
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f5, f8, Math.max(f7, f6 * f4), f7, f9, DynamicIsland.a(new Color(255, 105, 180), (int)(225.0f * f2)));
        }
    }

    private void d(float f2) {
        if (f2 <= 0.05f) {
            return;
        }
        if (this.N()) {
            this.h(f2);
        } else if (this.L()) {
            this.g(f2);
        } else if (this.s()) {
            try {
                this.b(f2);
            }
            catch (Throwable throwable) {
                this.f(f2);
            }
        } else {
            this.f(f2);
        }
    }

    private void u() {
        if (com.heypixel.heypixelmod.obsoverlay.EixClientCore.a() == null || com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g() == null) {
            this.I.a = 0.0f;
            this.I.a(false);
            return;
        }
        com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Blink b2 = com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a(com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Blink.class);
        if (b2 != null && b2.m()) {
            float f2;
            com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Blink b3 = b2;
            float f3 = b3.f.q();
            float f4 = b3.p();
            this.I.a = f2 = DynamicIsland.m(f4 / f3);
            this.I.a(true);
        } else {
            this.I.a = 0.0f;
            this.I.a(false);
        }
    }

    private void v() {
        boolean bl = DynamicIsland.a.f_91066_.f_92099_.m_90857_();
        if (bl && !this.A()) {
            this.v = System.currentTimeMillis();
            this.x = com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland.AnimationState.f;
            this.F = this.C;
            this.w();
        } else if (bl && this.A()) {
            this.w();
        } else if (this.x == com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland.AnimationState.g || this.x == com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland.AnimationState.f) {
            this.v = System.currentTimeMillis();
            this.x = com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland.AnimationState.h;
        }
    }

    private void w() {
        if (a.m_91403_() != null) {
            List<PlayerInfo> list = f.isEmpty() ? List.copyOf(a.m_91403_().m_105142_()) : f;
            this.U = list.stream().sorted(Comparator.comparingInt(playerInfo -> playerInfo.m_105325_() == GameType.SPECTATOR ? 1 : 0).thenComparing(playerInfo -> playerInfo.m_105312_().getName())).limit(80L).collect(Collectors.toList());
            int n2 = this.U.size();
            int n3 = (int)Math.ceil((double)n2 / 1.0);
            this.V = 205.0f;
            float f2 = Math.max(0.0f, this.V - 16.0f);
            Font font = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.c.a(10.0f);
            float f3 = DynamicIsland.a(font);
            int n4 = 1;
            if (d != null && !d.getString().isEmpty()) {
                n4 = DynamicIsland.a(d.getString(), f2, font).size();
            }
            int n5 = 0;
            if (e != null && !e.getString().isEmpty()) {
                n5 = DynamicIsland.a(e.getString(), f2, font).size();
            }
            float f4 = (float)n4 * f3;
            float f5 = (float)n5 * f3;
            float f6 = Math.max(30.0f, 12.0f + f4 + 8.0f);
            this.W = f6 + (float)n3 * 14.0f + 8.0f + (n5 > 0 ? f5 + 8.0f : 0.0f);
            this.W = Math.max(this.W, 38.0f);
        }
    }

    private void x() {
        if (this.A()) {
            return;
        }
        if (!s.isEmpty()) {
            this.a(s.poll());
        } else if (r != null && this.R() >= 1900L) {
            r = null;
            this.u = -1L;
        }
    }

    private void a(ToggleInfo toggleInfo) {
        float f2 = this.b(toggleInfo);
        this.G = this.C;
        this.H = this.D;
        r = toggleInfo;
        this.w = f2;
        this.u = (toggleInfo.type == com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland.ToggleCategory.b || toggleInfo.type == com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland.ToggleCategory.c) && !toggleInfo.enabled ? System.currentTimeMillis() - 1640L : System.currentTimeMillis();
    }

    private void y() {
        long l2 = this.R();
        long l3 = this.S();
        float f2 = this.C;
        float f3 = this.L() || this.N() ? 25.0f : this.t();
        float f4 = this.D;
        if (this.x == com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland.AnimationState.f) {
            if (l3 < 380L) {
                float f5 = DynamicIsland.m((float)l3 / 380.0f);
                float f6 = DynamicIsland.k(f5);
                float f7 = DynamicIsland.k(f5);
                this.E = f6;
                this.a(com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland.AnimationState.f, f7, DynamicIsland.a(f6, this.F, this.V), DynamicIsland.a(f7, f3, this.W), 1.0f);
                f2 = this.C;
                f4 = this.D;
            } else {
                this.E = 1.0f;
                this.a(com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland.AnimationState.g, 1.0f, this.V, this.W, 1.0f);
                f2 = this.V;
                f4 = this.W;
            }
        } else if (this.x == com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland.AnimationState.h) {
            if (l3 < 380L) {
                float f8 = DynamicIsland.m((float)l3 / 380.0f);
                float f9 = DynamicIsland.k(1.0f - f8);
                float f10 = DynamicIsland.k(1.0f - f8);
                this.E = f9;
                this.a(com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland.AnimationState.h, f10, DynamicIsland.a(f9, this.F, this.V), DynamicIsland.a(f10, f3, this.W), 1.0f);
                f2 = this.C;
                f4 = this.D;
            } else {
                this.E = 0.0f;
                this.a(com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland.AnimationState.a, 0.0f, this.F, f3, 1.0f);
                this.v = -1L;
                f2 = this.F;
                f4 = f3;
            }
        } else if (this.x == com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland.AnimationState.g) {
            this.E = 1.0f;
            this.a(com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland.AnimationState.g, 1.0f, this.V, this.W, 1.0f);
            f2 = this.V;
            f4 = this.W;
        } else {
            float f11 = this.N() ? this.O() : (this.L() ? this.M() : (this.s() ? this.T : 65.0f));
            if (r == null && this.u == -1L) {
                this.a(com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland.AnimationState.a, 0.0f, f11, f3, 1.0f);
                f2 = f11;
                f4 = f3;
            } else if (l2 < 220L) {
                float f12 = DynamicIsland.m((float)l2 / 220.0f);
                float f13 = DynamicIsland.j(f12);
                this.a(com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland.AnimationState.b, f13, DynamicIsland.a(f13, this.G, this.w), DynamicIsland.a(f13, this.H, 25.0f), 1.0f);
                f2 = DynamicIsland.a(f13, this.G, this.w);
                f4 = DynamicIsland.a(f13, this.H, 25.0f);
            } else if (l2 < 1420L) {
                float f14 = (float)(l2 - 220L) / 1200.0f;
                this.a(com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland.AnimationState.c, f14, this.C, 25.0f, 1.0f);
                f2 = this.w;
                f4 = 25.0f;
            } else if (l2 < 1640L) {
                float f15 = DynamicIsland.m((float)(l2 - 220L - 1200L) / 220.0f);
                float f16 = DynamicIsland.k(f15);
                this.a(com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland.AnimationState.d, f16, this.w, 25.0f, 1.0f);
                f2 = this.w;
                f4 = 25.0f;
            } else {
                float f17 = DynamicIsland.m((float)(l2 - 220L - 1200L - 220L) / 260.0f);
                float f18 = DynamicIsland.k(f17);
                this.a(com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland.AnimationState.e, f18, DynamicIsland.a(f18, this.w, f11), DynamicIsland.a(f18, 25.0f, f3), 1.0f);
                f2 = DynamicIsland.a(f18, this.w, f11);
                f4 = DynamicIsland.a(f18, 25.0f, f3);
            }
        }
        if (!this.A()) {
            this.C = DynamicIsland.a(0.38f, this.C, f2);
            this.D = DynamicIsland.a(0.28f, this.D, f4);
        }
        this.A = ((float)a.m_91268_().m_85445_() - this.C) / 2.0f;
        this.B = this.k.q();
    }

    private float z() {
        if (this.i.a("Capsule")) {
            return Math.max(0.0f, Math.min(this.D, this.C) / 2.0f);
        }
        return this.j.q();
    }

    private void a(AnimationState a2, float f2, float f3, float f4, float f5) {
        this.x = a2;
        this.y = f2;
        this.C = f3;
        this.D = f4;
        this.z = this.e(f5);
    }

    private boolean A() {
        return this.x == com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland.AnimationState.f || this.x == com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland.AnimationState.g || this.x == com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland.AnimationState.h;
    }

    private float B() {
        if (this.x == com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland.AnimationState.f || this.x == com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland.AnimationState.g || this.x == com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland.AnimationState.h) {
            return this.E;
        }
        return this.y;
    }

    private float e(float f2) {
        float f3 = f2 - this.z;
        float f4 = 0.22f;
        return this.z + f3 * f4;
    }

    private void C() {
        switch (this.x) {
            case a: {
                this.D();
                break;
            }
            case b: {
                this.E();
                break;
            }
            case c: {
                this.F();
                break;
            }
            case d: {
                this.G();
                break;
            }
            case e: {
                this.H();
                break;
            }
            case f: {
                this.I();
                break;
            }
            case g: {
                this.J();
                break;
            }
            case h: {
                this.K();
            }
        }
    }

    private void D() {
        this.a(com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland.StyleConstants.i);
        this.i(1.0f);
        this.d(1.0f);
    }

    private void E() {
        this.a(com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland.StyleConstants.i);
        this.i(1.0f);
        if (r != null && DynamicIsland.r.type == com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland.ToggleCategory.b && DynamicIsland.r.enabled) {
            this.g(this.y);
        } else if (r != null && DynamicIsland.r.type == com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland.ToggleCategory.c && DynamicIsland.r.enabled) {
            this.h(this.y);
        } else if (r != null) {
            this.a(r, DynamicIsland.l(this.y), 0.0f);
        } else {
            this.d(1.0f);
        }
    }

    private void F() {
        this.a(com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland.StyleConstants.i);
        this.i(1.0f);
        if (r != null && DynamicIsland.r.type == com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland.ToggleCategory.b && DynamicIsland.r.enabled) {
            this.g(1.0f);
        } else if (r != null && DynamicIsland.r.type == com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland.ToggleCategory.c && DynamicIsland.r.enabled) {
            this.h(1.0f);
        } else if (r != null) {
            this.a(r, 255, this.y);
        } else {
            this.d(1.0f);
        }
    }

    private void G() {
        this.a(com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland.StyleConstants.i);
        this.i(1.0f);
        if (r != null && DynamicIsland.r.type == com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland.ToggleCategory.b) {
            this.g(1.0f);
        } else if (r != null && DynamicIsland.r.type == com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland.ToggleCategory.c) {
            this.h(1.0f);
        } else if (r != null) {
            this.a(r, DynamicIsland.l(1.0f - this.y), 1.0f);
        }
    }

    private void H() {
        this.a(com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland.StyleConstants.i);
        this.i(1.0f);
        if (r != null && DynamicIsland.r.type == com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland.ToggleCategory.c && !DynamicIsland.r.enabled) {
            this.h(1.0f - this.y);
        } else if (r != null && DynamicIsland.r.type == com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland.ToggleCategory.b && !DynamicIsland.r.enabled) {
            this.g(1.0f - this.y);
        } else {
            this.d(this.y);
        }
    }

    private void I() {
        this.a(com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland.StyleConstants.i);
        float f2 = 1.0f - this.B();
        this.i(1.0f);
        if (r != null && DynamicIsland.r.type == com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland.ToggleCategory.b) {
            this.g(f2);
        } else if (r != null && DynamicIsland.r.type == com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland.ToggleCategory.c) {
            this.h(f2);
        } else if (r != null) {
            this.a(r, (int)(255.0f * f2), 0.0f);
        } else {
            this.d(f2);
        }
    }

    private void J() {
        this.a(com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland.StyleConstants.i);
        this.i(1.0f);
    }

    private void K() {
        this.a(com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland.StyleConstants.i);
        float f2 = 1.0f - this.B();
        this.i(1.0f);
        if (r != null && DynamicIsland.r.type == com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland.ToggleCategory.b) {
            this.g(f2);
        } else if (r != null && DynamicIsland.r.type == com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland.ToggleCategory.c) {
            this.h(f2);
        } else if (r != null) {
            this.a(r, (int)(255.0f * f2), 1.0f);
        } else {
            this.d(f2);
        }
    }

    private void a(Color color) {
        if (this.g.m()) {
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c(this.A, this.B, this.C, this.D, this.z());
        }
        if (this.h.m() && this.z > 0.05f) {
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(this.A, this.B, this.C, this.D, this.z());
        }
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(this.A, this.B, this.C, this.D, this.z(), color);
    }

    private void f(float f2) {
        if (f2 <= 0.05f) {
            return;
        }
        Font font = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.c.d(14.0f);
        String string = "EixClient";
        Color color = DynamicIsland.a(new Color(255, 105, 180), (int)(255.0f * f2));
        Rect rect = font.measureText(string);
        FontMetrics fontMetrics = font.getMetrics();
        float f3 = this.A + this.C / 2.0f;
        float f4 = this.B + this.D / 2.0f;
        float f5 = f3 - rect.getLeft() - rect.getWidth() / 2.0f;
        float f6 = f4 + (fontMetrics.getAscent() - fontMetrics.getDescent()) / 2.0f - fontMetrics.getAscent();
        int n2 = io.github.humbleui.skija.Color.makeARGB((int)color.getAlpha(), (int)color.getRed(), (int)color.getGreen(), (int)color.getBlue());
        try (Paint paint = new Paint().setColor(n2);
             Paint paint2 = paint.makeClone().setImageFilter(ImageFilter.makeBlur((float)2.5f, (float)2.5f, (FilterTileMode)FilterTileMode.DECAL));){
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().drawString(string, f5, f6, font, paint2);
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().drawString(string, f5 - 0.2f, f6, font, paint);
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().drawString(string, f5 + 0.2f, f6, font, paint);
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().drawString(string, f5, f6, font, paint);
        }
    }

    private boolean L() {
        if (com.heypixel.heypixelmod.obsoverlay.EixClientCore.a() == null || com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g() == null) {
            return false;
        }
        Scaffold n2 = com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a(Scaffold.class);
        return n2 != null && n2.m();
    }

    private void g(float f2) {
        if (f2 <= 0.05f) {
            return;
        }
        int n2 = com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.i();
        int n3 = Math.max(1, com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.p());
        float f3 = DynamicIsland.m((float)n2 / (float)n3);
        float f4 = 6.0f;
        float f5 = 10.0f;
        float f6 = 6.0f;
        float f7 = 6.0f;
        float f8 = f7 / 2.0f;
        Font font = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.c.a(10.0f);
        String string = n2 + "/" + n3;
        float f9 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string, font);
        float f10 = this.B + this.D / 2.0f;
        float f11 = this.A + f4;
        float f12 = f10 - f5 / 2.0f;
        this.a(f11, f12, f5, (int)(255.0f * f2));
        float f13 = this.A + this.C - f4 - f9;
        float f14 = DynamicIsland.a(f10, font) - 1.0f;
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string, f13, f14, DynamicIsland.a(Color.WHITE, (int)(255.0f * f2)), font);
        float f15 = f11 + f5 + f6;
        float f16 = f13 - f6 - f15;
        if (f16 > 2.0f) {
            float f17 = f10 - f7 / 2.0f;
            float f18 = f16 * f3;
            Color color = DynamicIsland.a(new Color(210, 210, 210), (int)(160.0f * f2));
            Color color2 = DynamicIsland.a(new Color(196, 128, 224), (int)(220.0f * f2));
            Color color3 = DynamicIsland.a(new Color(196, 128, 224), (int)(120.0f * f2));
            Path path = new Path();
            path.addRRect(RRect.makeXYWH((float)f15, (float)f17, (float)f16, (float)f7, (float)f8));
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c();
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().clipPath(path, ClipMode.INTERSECT, true);
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f15, f17, f16, f7, f8, color);
            if (f18 > 0.5f) {
                com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.b(f15, f17, f18, f7, f8, color3);
                com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f15, f17, f18, f7, f8, color2);
            }
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.d();
            path.close();
        }
    }

    private float M() {
        float f2 = 6.0f;
        float f3 = 10.0f;
        float f4 = 6.0f;
        float f5 = 65.0f;
        Font font = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.c.a(10.0f);
        int n2 = com.heypixel.heypixelmod.obsoverlay.utils.EquipmentActionUtils.i();
        int n3 = Math.max(1, com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager.p());
        String string = n2 + "/" + n3;
        float f6 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string, font);
        float f7 = f2 + f3 + f4 + f5 + f4 + f6 + f2;
        return Math.max(90.0f, f7);
    }

    private void a(float f2, float f3, float f4, int n2) {
        Color color = DynamicIsland.a(new Color(180, 180, 180), n2);
        float f5 = f4;
        float f6 = f5 * 0.25f;
        float f7 = f2;
        float f8 = f3 + f6;
        float f9 = f2 + f5 - f6;
        float f10 = f3 + f6;
        float f11 = f2 + f5 - f6;
        float f12 = f3 + f5;
        float f13 = f2;
        float f14 = f3 + f5;
        float f15 = f2 + f6;
        float f16 = f3;
        float f17 = f2 + f5;
        float f18 = f3;
        float f19 = f2 + f5;
        float f20 = f3 + f5 - f6;
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c(f7, f8, f9, f10, 1.2f, color);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c(f9, f10, f11, f12, 1.2f, color);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c(f11, f12, f13, f14, 1.2f, color);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c(f13, f14, f7, f8, 1.2f, color);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c(f15, f16, f17, f18, 1.2f, color);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c(f17, f18, f19, f20, 1.2f, color);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c(f19, f20, f11, f12, 1.2f, color);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c(f15, f16, f7, f8, 1.2f, color);
    }

    private boolean N() {
        if (com.heypixel.heypixelmod.obsoverlay.EixClientCore.a() == null || com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g() == null) {
            return false;
        }
        com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Blink b2 = com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a(com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Blink.class);
        return b2 != null && b2.m();
    }

    private void h(float f2) {
        if (f2 <= 0.05f) {
            return;
        }
        if (com.heypixel.heypixelmod.obsoverlay.EixClientCore.a() == null || com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g() == null) {
            return;
        }
        com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Blink b2 = com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a(com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Blink.class);
        if (b2 == null) {
            return;
        }
        long l2 = b2.p();
        float f3 = b2.f.q();
        float f4 = this.I.c;
        float f5 = 6.0f;
        float f6 = 10.0f;
        float f7 = 6.0f;
        float f8 = 6.0f;
        float f9 = f8 / 2.0f;
        Font font = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.c.a(10.0f);
        String string = l2 + "/" + (int)f3;
        float f10 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string, font);
        float f11 = this.B + this.D / 2.0f;
        float f12 = this.A + f5;
        float f13 = f11 - f6 / 2.0f;
        this.b(f12, f13, f6, (int)(255.0f * f2));
        float f14 = this.A + this.C - f5 - f10;
        float f15 = DynamicIsland.a(f11, font) - 1.0f;
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string, f14, f15, DynamicIsland.a(Color.WHITE, (int)(255.0f * f2)), font);
        float f16 = f12 + f6 + f7;
        float f17 = f14 - f7 - f16;
        if (f17 > 2.0f) {
            float f18 = f11 - f8 / 2.0f;
            float f19 = f17 * f4;
            Color color = DynamicIsland.a(new Color(210, 210, 210), (int)(160.0f * f2));
            Color color2 = DynamicIsland.a(new Color(196, 128, 224), (int)(220.0f * f2));
            Color color3 = DynamicIsland.a(new Color(196, 128, 224), (int)(120.0f * f2));
            Path path = new Path();
            path.addRRect(RRect.makeXYWH((float)f16, (float)f18, (float)f17, (float)f8, (float)f9));
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c();
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().clipPath(path, ClipMode.INTERSECT, true);
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f16, f18, f17, f8, f9, color);
            if (f19 > 0.5f) {
                com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.b(f16, f18, f19, f8, f9, color3);
                com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(f16, f18, f19, f8, f9, color2);
            }
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.d();
            path.close();
        }
    }

    private float O() {
        float f2 = 6.0f;
        float f3 = 10.0f;
        float f4 = 6.0f;
        float f5 = 65.0f;
        Font font = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.c.a(10.0f);
        if (com.heypixel.heypixelmod.obsoverlay.EixClientCore.a() == null || com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g() == null) {
            return 90.0f;
        }
        com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Blink b2 = com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a(com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Blink.class);
        if (b2 == null) {
            return 90.0f;
        }
        long l2 = b2.p();
        float f6 = b2.f.q();
        String string = l2 + "/" + (int)f6;
        float f7 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string, font);
        float f8 = f2 + f3 + f4 + f5 + f4 + f7 + f2;
        return Math.max(90.0f, f8);
    }

    private void b(float f2, float f3, float f4, int n2) {
        Color color = DynamicIsland.a(new Color(180, 180, 180), n2);
        float f5 = f4;
        float f6 = f2 + f5 / 2.0f;
        float f7 = f3 + f5 - 1.0f;
        float f8 = 1.5f;
        Paint paint = new Paint().setColor(io.github.humbleui.skija.Color.makeARGB((int)color.getAlpha(), (int)color.getRed(), (int)color.getGreen(), (int)color.getBlue()));
        paint.setMode(PaintMode.STROKE);
        paint.setStrokeWidth(f8);
        paint.setStrokeCap(PaintStrokeCap.ROUND);
        Paint paint2 = new Paint().setColor(io.github.humbleui.skija.Color.makeARGB((int)color.getAlpha(), (int)color.getRed(), (int)color.getGreen(), (int)color.getBlue()));
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().drawCircle(f6, f7, 1.2f, paint2);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().drawArc(f6 - 3.5f, f7 - 3.5f, f6 + 3.5f, f7 + 3.5f, 225.0f, 90.0f, false, paint);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().drawArc(f6 - 6.5f, f7 - 6.5f, f6 + 6.5f, f7 + 6.5f, 225.0f, 90.0f, false, paint);
        paint.close();
        paint2.close();
    }

    private void i(float f2) {
        if (f2 <= 0.05f) {
            return;
        }
        Font font = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.c.a(10.0f);
        Font font2 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.c.c(10.0f);
        float f3 = 19.0f;
        float f4 = this.B + f3 / 2.0f;
        float f5 = DynamicIsland.a(f4, font);
        Color color = DynamicIsland.a(Color.WHITE, (int)(255.0f * f2));
        float f6 = 6.0f;
        float f7 = 4.0f;
        String string = LocalTime.now().format(c);
        String string2 = "\ue855";
        Rect rect = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.b(string2, font2);
        Rect rect2 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.b(string, font);
        float f8 = rect.getWidth();
        float f9 = rect2.getWidth();
        float f10 = f8 + f7;
        float f11 = Math.min(rect.getLeft(), f10 + rect2.getLeft());
        float f12 = Math.max(rect.getLeft() + f8, f10 + rect2.getLeft() + f9);
        float f13 = f12 - f11;
        float f14 = Math.max(50.0f, f6 * 2.0f + f13);
        float f15 = this.A - 20.0f - f14;
        float f16 = f5 + (rect2.getTop() + rect2.getBottom()) / 2.0f;
        float f17 = f16 - (rect.getTop() + rect.getBottom()) / 2.0f;
        float f18 = f14 - f6 * 2.0f;
        float f19 = f15 + f14 / 2.0f - f13 / 2.0f - f11;
        float f20 = f15 + f6;
        float f21 = Math.max(0.0f, f18);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c();
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.d(f20, this.B, f21, f3, this.z());
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string2, f19, f17, color, font2);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string, f19 + f10, f5, color, font);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.d();
        String string3 = "FPS:" + a.m_260875_();
        String string4 = "\ue31e";
        Rect rect3 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.b(string4, font2);
        Rect rect4 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.b(string3, font);
        float f22 = rect3.getWidth();
        float f23 = rect4.getWidth();
        float f24 = f22 + f7;
        float f25 = Math.min(rect3.getLeft(), f24 + rect4.getLeft());
        float f26 = Math.max(rect3.getLeft() + f22, f24 + rect4.getLeft() + f23);
        float f27 = f26 - f25;
        float f28 = Math.max(50.0f, f6 * 2.0f + f27);
        float f29 = this.A + this.C + 20.0f;
        float f30 = f5 + (rect4.getTop() + rect4.getBottom()) / 2.0f;
        float f31 = f30 - (rect3.getTop() + rect3.getBottom()) / 2.0f;
        float f32 = f28 - f6 * 2.0f;
        float f33 = f29 + f28 / 2.0f - f27 / 2.0f - f25;
        float f34 = f29 + f6;
        float f35 = Math.max(0.0f, f32);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c();
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.d(f34, this.B, f35, f3, this.z());
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string4, f33, f31, color, font2);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string3, f33 + f24, f5, color, font);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.d();
    }

    private void P() {
        float f2;
        float f3;
        float f4;
        Object object;
        float f5;
        float f6;
        if (this.U == null) {
            return;
        }
        float f7 = this.A + 8.0f;
        float f8 = this.B + 8.0f;
        float f9 = this.A + this.C - 8.0f;
        float f10 = this.B + this.D - 8.0f;
        if (f9 <= f7 || f10 <= f8) {
            return;
        }
        float f11 = f9 - f7;
        Font font = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.c.a(10.0f);
        float f12 = DynamicIsland.a(font);
        float f13 = DynamicIsland.m((this.B() - 0.15f) / 0.85f);
        if (f13 <= 0.0f) {
            return;
        }
        int n2 = (int)(255.0f * f13);
        Color color = DynamicIsland.a(Color.WHITE, n2);
        Color color2 = new Color(160, 160, 160, n2);
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c();
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.d(f7, f8, f11, f10 - f8, 0.0f);
        float f14 = this.B + 12.0f;
        Component component = d;
        if (component == null || component.getString().isEmpty()) {
            component = Component.m_237113_((String)("Players: " + this.U.size()));
        }
        List<String> list = DynamicIsland.a(component.getString(), f11, font);
        for (String string : list) {
            f6 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string, font);
            f5 = this.A + (this.C - f6) / 2.0f;
            float f15 = DynamicIsland.a(f14 + f12 / 2.0f, font);
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string, f5, f15, color, font);
            f14 += f12;
        }
        float f16 = Math.max(this.B + 30.0f, f14 += 8.0f);
        float f17 = 14.0f;
        f6 = 10.0f;
        f5 = Math.max(0.0f, (f17 - f6) / 2.0f);
        int n3 = 0;
        Component component2 = this.U.iterator();
        while (component2.hasNext()) {
            Object object2;
            Player player;
            object = component2.next();
            f4 = f16 + (float)n3 * f17;
            if (f4 + f17 > f10) break;
            float f18 = f7;
            float f19 = f4 + f5;
            f3 = f4 + f17 / 2.0f;
            f2 = DynamicIsland.a(f3, font) - 1.5f;
            ResourceLocation resourceLocation = null;
            Player player2 = player = DynamicIsland.a.f_91073_ == null ? null : DynamicIsland.a.f_91073_.m_46003_(object.m_105312_().getId());
            if (player instanceof AbstractClientPlayer) {
                object2 = (AbstractClientPlayer)player;
                resourceLocation = object2.m_108560_();
            } else {
                resourceLocation = object.m_105337_();
            }
            if (resourceLocation != null) {
                com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(resourceLocation, f18, f19, f6, f6, 2.0f);
            }
            object2 = object.m_105330_() + "ms";
            float f20 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a((String)object2, font);
            float f21 = f9 - f20;
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a((String)object2, f21, f2, color2, font);
            String string = this.e(object.m_105312_().getName());
            float f22 = f18 + f6 + 4.0f;
            float f23 = f21 - 6.0f;
            if (f23 > f22) {
                com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c();
                com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.d(f22, f4, f23 - f22, f17, 0.0f);
                com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string, f22, f2, color, font);
                com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.d();
            }
            ++n3;
        }
        if ((component2 = e) != null && !component2.getString().isEmpty()) {
            object = DynamicIsland.a(component2.getString(), f11, font);
            f4 = f10 - (float)object.size() * f12;
            Iterator iterator = object.iterator();
            while (iterator.hasNext()) {
                String string = (String)iterator.next();
                f3 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string, font);
                f2 = this.A + (this.C - f3) / 2.0f;
                float f24 = DynamicIsland.a(f4 + f12 / 2.0f, font);
                com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string, f2, f24, color, font);
                f4 += f12;
            }
        }
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.d();
    }

    private String e(String string) {
        return "EixClient";
    }

    private void a(ToggleInfo toggleInfo, int n2, float f2) {
        if (toggleInfo == null) {
            return;
        }
        if (toggleInfo.type == com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland.ToggleCategory.d) {
            this.a(toggleInfo, n2);
            return;
        }
        if (toggleInfo.type == com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland.ToggleCategory.e) {
            this.b(toggleInfo, n2);
            return;
        }
        float f3 = 6.0f;
        float f4 = 12.0f;
        float f5 = 5.0f;
        Font font = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.c.a(f4);
        float f6 = this.B + this.D / 2.0f;
        float f7 = f6 - f4 / 2.0f + 1.0f;
        this.a(this.A + f3, f7, f4, toggleInfo.enabled, n2);
        float f8 = this.A + f3 + f4 + f5;
        float f9 = this.A + this.C - f3 - f8;
        float f10 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(toggleInfo.name, font);
        float f11 = f8 + Math.max(0.0f, (f9 - f10) / 2.0f);
        float f12 = DynamicIsland.a(f6, font) - 1.5f;
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(toggleInfo.name, f11, f12, DynamicIsland.a(Color.WHITE, n2), font);
    }

    private void a(ToggleInfo toggleInfo, int n2) {
        float f2 = 6.0f;
        float f3 = 9.0f;
        float f4 = 5.0f;
        Font font = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.c.a(f3);
        float f5 = this.B + this.D / 2.0f;
        float f6 = this.A + f2 + f3 / 2.0f;
        float f7 = f5;
        Color color = DynamicIsland.a(new Color(255, 105, 180), n2);
        int n3 = io.github.humbleui.skija.Color.makeARGB((int)color.getAlpha(), (int)color.getRed(), (int)color.getGreen(), (int)color.getBlue());
        try (Paint paint = new Paint().setColor(n3);
             Paint paint2 = paint.makeClone().setImageFilter(ImageFilter.makeBlur((float)1.6f, (float)1.6f, (FilterTileMode)FilterTileMode.DECAL));){
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().drawCircle(f6, f7, f3 / 2.0f, paint2);
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().drawCircle(f6, f7, f3 / 2.0f, paint);
        }
        float f8 = this.A + f2 + f3 + f4;
        float f9 = this.A + this.C - f2 - f8;
        float f10 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(toggleInfo.name, font);
        float f11 = f8 + Math.max(0.0f, (f9 - f10) / 2.0f);
        float f12 = DynamicIsland.a(f5, font) - 1.0f;
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(toggleInfo.name, f11, f12, DynamicIsland.a(Color.WHITE, n2), font);
    }

    private void b(ToggleInfo toggleInfo, int n2) {
        float f2 = 6.0f;
        float f3 = 12.0f;
        float f4 = 5.0f;
        Font font = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.c.a(f3);
        float f5 = this.B + this.D / 2.0f;
        float f6 = this.A + f2 + f3 / 2.0f;
        float f7 = f5 + 1.0f;
        Color color = DynamicIsland.a(new Color(255, 215, 0), n2);
        int n3 = io.github.humbleui.skija.Color.makeARGB((int)color.getAlpha(), (int)color.getRed(), (int)color.getGreen(), (int)color.getBlue());
        try (Paint paint = new Paint().setColor(n3);
             Paint paint2 = paint.makeClone().setImageFilter(ImageFilter.makeBlur((float)1.6f, (float)1.6f, (FilterTileMode)FilterTileMode.DECAL));){
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().drawCircle(f6, f7, f3 / 2.0f, paint2);
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.e().drawCircle(f6, f7, f3 / 2.0f, paint);
        }
        float f8 = this.A + f2 + f3 + f4;
        float f9 = this.A + this.C - f2 - f8;
        float f10 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(toggleInfo.name, font);
        float f11 = f8 + Math.max(0.0f, (f9 - f10) / 2.0f);
        float f12 = DynamicIsland.a(f5, font) - 1.5f;
        com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(toggleInfo.name, f11, f12, DynamicIsland.a(Color.WHITE, n2), font);
    }

    private void a(float f2, float f3, float f4, boolean bl, int n2) {
        Color color = bl ? new Color(80, 220, 100, n2) : new Color(220, 80, 80, n2);
        float f5 = 2.0f;
        if (bl) {
            float f6 = f2 + f4 * 0.15f;
            float f7 = f3 + f4 * 0.5f;
            float f8 = f2 + f4 * 0.4f;
            float f9 = f3 + f4 * 0.8f;
            float f10 = f2 + f4 * 0.85f;
            float f11 = f3 + f4 * 0.25f;
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c(f6, f7, f8, f9, f5, color);
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c(f8, f9, f10, f11, f5, color);
        } else {
            float f12 = f4 * 0.2f;
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c(f2 + f12, f3 + f12, f2 + f4 - f12, f3 + f4 - f12, f5, color);
            com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.c(f2 + f4 - f12, f3 + f12, f2 + f12, f3 + f4 - f12, f5, color);
        }
    }

    private float Q() {
        return this.b(r);
    }

    private float b(ToggleInfo toggleInfo) {
        if (toggleInfo == null) {
            return 90.0f;
        }
        if (toggleInfo.type == com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland.ToggleCategory.b) {
            return this.M();
        }
        if (toggleInfo.type == com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland.ToggleCategory.c) {
            return this.O();
        }
        float f2 = 6.0f;
        float f3 = toggleInfo.type == com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland.ToggleCategory.d ? 9.0f : 12.0f;
        float f4 = 5.0f;
        Font font = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.b.c.a(f3);
        float f5 = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(toggleInfo.name, font);
        float f6 = f2 + f3 + f4 + f5 + f2 + 6.0f;
        return Math.max(90.0f, f6);
    }

    private long R() {
        return this.u == -1L ? 0L : System.currentTimeMillis() - this.u;
    }

    private long S() {
        return this.v == -1L ? 0L : System.currentTimeMillis() - this.v;
    }

    private static float j(float f2) {
        float f3 = 1.0f - f2;
        return 1.0f - f3 * f3 * f3;
    }

    private static float k(float f2) {
        if (f2 < 0.5f) {
            return 4.0f * f2 * f2 * f2;
        }
        float f3 = -2.0f * f2 + 2.0f;
        return 1.0f - f3 * f3 * f3 / 2.0f;
    }

    private static int l(float f2) {
        return (int)(255.0f * f2);
    }

    private static Color a(Color color, int n2) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.max(0, Math.min(255, n2)));
    }

    private static float m(float f2) {
        if (f2 < 0.0f) {
            return 0.0f;
        }
        if (f2 > 1.0f) {
            return 1.0f;
        }
        return f2;
    }

    private static float a(float f2, float f3, float f4) {
        return f3 + (f4 - f3) * f2;
    }

    private static float a(Font font) {
        FontMetrics fontMetrics = font.getMetrics();
        return fontMetrics.getDescent() - fontMetrics.getAscent();
    }

    private static float a(float f2, Font font) {
        FontMetrics fontMetrics = font.getMetrics();
        return f2 - (fontMetrics.getAscent() + fontMetrics.getDescent()) / 2.0f - 7.0f;
    }

    private static List<String> a(String string, float f2, Font font) {
        ArrayList<String> arrayList = new ArrayList<String>();
        if (string == null || string.isEmpty()) {
            arrayList.add("");
            return arrayList;
        }
        String[] stringArray = string.split(" ");
        StringBuilder stringBuilder = new StringBuilder();
        for (String string2 : stringArray) {
            String string3;
            String string4 = string3 = stringBuilder.length() == 0 ? string2 : String.valueOf(stringBuilder) + " " + string2;
            if (com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string3, font) <= f2) {
                stringBuilder.setLength(0);
                stringBuilder.append(string3);
                continue;
            }
            if (stringBuilder.length() > 0) {
                arrayList.add(stringBuilder.toString());
                stringBuilder.setLength(0);
            }
            if (com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(string2, font) <= f2) {
                stringBuilder.append(string2);
                continue;
            }
            StringBuilder stringBuilder2 = new StringBuilder();
            for (int i2 = 0; i2 < string2.length(); ++i2) {
                stringBuilder2.append(string2.charAt(i2));
                if (!(com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a(stringBuilder2.toString(), font) > f2) || stringBuilder2.length() <= 1) continue;
                stringBuilder2.deleteCharAt(stringBuilder2.length() - 1);
                arrayList.add(stringBuilder2.toString());
                stringBuilder2.setLength(0);
                stringBuilder2.append(string2.charAt(i2));
            }
            if (stringBuilder2.length() <= 0) continue;
            stringBuilder.append((CharSequence)stringBuilder2);
        }
        if (stringBuilder.length() > 0) {
            arrayList.add(stringBuilder.toString());
        }
        return arrayList;
    }

    static {
        f = List.of();
        s = new ConcurrentLinkedQueue<ToggleInfo>();
        t = new CopyOnWriteArraySet<UUID>();
        J = new float[]{11.5f, 11.0f, 10.5f, 10.0f, 9.5f, 9.0f, 8.5f};
    }

    private static final class StyleConstants {
        static final float a = 65.0f;
        static final float b = 19.0f;
        static final float c = 90.0f;
        static final float d = 25.0f;
        static final float e = 20.0f;
        static final float f = 50.0f;
        static final float g = 14.0f;
        static final float h = 10.0f;
        static final Color i = new Color(18, 18, 18, 70);
        static final float j = 14.0f;
        static final float k = 8.0f;
        static final float l = 12.0f;
        static final float m = 30.0f;
        static final int n = 1;
        static final float o = 36.0f;
        static final float p = 7.0f;
        static final float q = 8.5f;
        static final float r = 11.5f;
        static final float s = 8.5f;
        static final float t = 2.0f;

        private StyleConstants() {
        }
    }

    private static enum AnimationState {
        a,
        b,
        c,
        d,
        e,
        f,
        g,
        h;

    }

    private static enum ToggleCategory {
        a,
        b,
        c,
        d,
        e;

    }

    private record ToggleInfo(String name, boolean enabled, ToggleCategory type) {
    }

    private static final class AnimationDurations {
        static final long a = 220L;
        static final long b = 1200L;
        static final long c = 220L;
        static final long d = 260L;
        static final long e = 1900L;
        static final long f = 380L;

        private AnimationDurations() {
        }
    }
}

