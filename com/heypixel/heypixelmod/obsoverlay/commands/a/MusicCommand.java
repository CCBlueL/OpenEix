/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.commands.a;

import com.heypixel.heypixelmod.obsoverlay.EixClientCore;
import com.heypixel.heypixelmod.obsoverlay.commands.CommandInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.MusicPlayer;
import com.heypixel.heypixelmod.obsoverlay.music.a.OnlineMusicApiClient;
import com.heypixel.heypixelmod.obsoverlay.music.a.MusicCollectionResult;
import com.heypixel.heypixelmod.obsoverlay.music.a.PlaybackTimelineModel;
import com.heypixel.heypixelmod.obsoverlay.music.a.MusicAudioDownloadInfo;
import com.heypixel.heypixelmod.obsoverlay.music.a.MusicPlaylistMetadata;
import com.heypixel.heypixelmod.obsoverlay.music.MusicPlaybackEngine;
import com.heypixel.heypixelmod.obsoverlay.music.nowplaying.NowPlayingInfo;
import com.heypixel.heypixelmod.obsoverlay.music.nowplaying.NowPlayingStatusManager;
import com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting;
import com.heypixel.heypixelmod.obsoverlay.utils.BackgroundExecutor;
import java.io.File;
import java.util.List;
import java.util.Locale;
import net.minecraft.client.Minecraft;

@CommandInfo(a="music", b="Control the built-in music player", c={"m", "bgm"})
public class MusicCommand
extends com.heypixel.heypixelmod.obsoverlay.commands.BaseCommand {
    @Override
    public void a(String[] stringArray) {
        String string;
        com.heypixel.heypixelmod.obsoverlay.music.MusicLibraryManager c2 = com.heypixel.heypixelmod.obsoverlay.music.MusicLibraryManager.a();
        c2.b();
        if (stringArray.length == 0) {
            MusicCommand.e();
            return;
        }
        switch (string = stringArray[0].toLowerCase(Locale.ROOT)) {
            case "play": {
                if (stringArray.length >= 2) {
                    Integer n2 = MusicCommand.c(stringArray[1]);
                    if (n2 != null && !c2.p().isEmpty()) {
                        c2.b(n2 - 1);
                        MusicCommand.a(c2);
                        return;
                    }
                    String string2 = MusicCommand.a(stringArray, 1);
                    com.heypixel.heypixelmod.obsoverlay.music.MusicTrackMetadata d2 = null;
                    for (com.heypixel.heypixelmod.obsoverlay.music.MusicTrackMetadata d3 : c2.e()) {
                        if (!d3.h().toLowerCase(Locale.ROOT).contains(string2.toLowerCase(Locale.ROOT))) continue;
                        d2 = d3;
                        break;
                    }
                    if (d2 == null) {
                        ClientChatFormatting.a("\u00a7c\u6ca1\u6709\u627e\u5230\u5339\u914d\u7684\u97f3\u4e50: " + string2);
                        return;
                    }
                    c2.a(d2);
                } else {
                    c2.m();
                }
                MusicCommand.a(c2);
                break;
            }
            case "pause": 
            case "resume": 
            case "toggle": {
                c2.m();
                MusicCommand.a(c2);
                break;
            }
            case "next": 
            case "skip": {
                c2.s();
                MusicCommand.a(c2);
                break;
            }
            case "prev": 
            case "previous": 
            case "back": {
                c2.t();
                MusicCommand.a(c2);
                break;
            }
            case "stop": {
                c2.r();
                ClientChatFormatting.a("\u00a77\u5df2\u505c\u6b62\u64ad\u653e");
                break;
            }
            case "volume": 
            case "vol": {
                if (stringArray.length < 2) {
                    ClientChatFormatting.a("\u00a77\u5f53\u524d\u97f3\u91cf: \u00a7b" + Math.round(c2.c().i() * 100.0f) + "%");
                    return;
                }
                try {
                    float f2 = Float.parseFloat(stringArray[1]);
                    f2 = Math.max(0.0f, Math.min(1.0f, f2 > 1.0f ? f2 / 100.0f : f2));
                    com.heypixel.heypixelmod.obsoverlay.music.MusicLibraryManager.LoopMode(f2);
                    MusicPlayer o2 = MusicCommand.g();
                    if (o2 != null) {
                        o2.i.a(f2 * 100.0f);
                    }
                    ClientChatFormatting.a("\u00a77\u97f3\u91cf\u5df2\u8bbe\u7f6e\u4e3a \u00a7b" + Math.round(f2 * 100.0f) + "%");
                }
                catch (NumberFormatException numberFormatException) {
                    ClientChatFormatting.a("\u00a7c\u97f3\u91cf\u5fc5\u987b\u662f\u6570\u5b57 (0-100 \u6216 0.0-1.0)");
                }
                break;
            }
            case "list": {
                if (stringArray.length >= 2) {
                    MusicCommand.a(stringArray[1]);
                    return;
                }
                if (c2.e().isEmpty()) {
                    ClientChatFormatting.a("\u00a77\u64ad\u653e\u5217\u8868\u4e3a\u7a7a\uff0c\u97f3\u4e50\u76ee\u5f55: \u00a7b" + c2.d().getAbsolutePath());
                    return;
                }
                ClientChatFormatting.a("\u00a77\u64ad\u653e\u5217\u8868 \u00a7b(" + c2.f() + ")\u00a77:");
                int n3 = Math.min(c2.f(), 10);
                for (int i2 = 0; i2 < n3; ++i2) {
                    com.heypixel.heypixelmod.obsoverlay.music.MusicTrackMetadata d4 = c2.e().get(i2);
                    String string3 = i2 == c2.g() ? "\u00a7b> " : "\u00a77  ";
                    ClientChatFormatting.a(false, string3 + (i2 + 1) + ". " + d4.h());
                }
                if (c2.f() <= n3) break;
                ClientChatFormatting.a("\u00a77... \u4ee5\u53ca\u53e6\u5916 " + (c2.f() - n3) + " \u9996");
                break;
            }
            case "scan": 
            case "reload": {
                c2.l();
                ClientChatFormatting.a("\u00a77\u6b63\u5728\u91cd\u65b0\u626b\u63cf\u97f3\u4e50\u76ee\u5f55...");
                break;
            }
            case "open": 
            case "gui": {
                MusicPlayer.p();
                break;
            }
            case "folder": 
            case "dir": {
                ClientChatFormatting.a("\u00a77\u97f3\u4e50\u76ee\u5f55: \u00a7b" + c2.d().getAbsolutePath());
                break;
            }
            case "status": 
            case "info": {
                MusicCommand.f();
                break;
            }
            case "lyric": 
            case "lrc": {
                if (stringArray.length >= 2 && stringArray[1].equalsIgnoreCase("clear")) {
                    com.heypixel.heypixelmod.obsoverlay.music.lyric.LyricParser.a().q();
                    ClientChatFormatting.a("\u00a77\u6b4c\u8bcd\u7f13\u5b58\u5df2\u6e05\u7a7a");
                    break;
                }
                com.heypixel.heypixelmod.obsoverlay.music.lyric.LyricParser b2 = com.heypixel.heypixelmod.obsoverlay.music.lyric.LyricParser.a();
                ClientChatFormatting.a("\u00a77\u6b4c\u8bcd\u72b6\u6001: \u00a7b" + (String)(b2.l() ? b2.k().e() + " \u884c" : (b2.m() ? "\u52a0\u8f7d\u4e2d..." : "\u65e0")));
                if (b2.d().isEmpty()) break;
                ClientChatFormatting.a(false, "\u00a7b\u5f53\u524d: \u00a7f" + b2.d());
                break;
            }
            case "search": 
            case "s": 
            case "find": {
                if (stringArray.length < 2) {
                    ClientChatFormatting.a("\u00a7c\u7528\u6cd5: .music search <\u5173\u952e\u8bcd>");
                    return;
                }
                String string4 = MusicCommand.a(stringArray, 1);
                ClientChatFormatting.a("\u00a77\u6b63\u5728\u89e3\u6790\u63a5\u53e3\u641c\u7d22: \u00a7b" + string4 + "\u00a77...");
                MusicCommand.a(() -> {
                    try {
                        List<i> list = g.a().d(string4);
                        c2.a(list);
                        MusicCommand.b(() -> {
                            MusicCommand.a("\u641c\u7d22\u300c" + string4 + "\u300d", list);
                            if (!list.isEmpty()) {
                                ClientChatFormatting.a("\u00a77\u7528 \u00a7b.music play <\u5e8f\u53f7> \u00a77\u64ad\u653e\uff0c\u00a7b.music download <\u5e8f\u53f7> \u00a77\u4e0b\u8f7d");
                            }
                        });
                    }
                    catch (Throwable throwable) {
                        MusicCommand.b(() -> ClientChatFormatting.a("\u00a7c\u641c\u7d22\u5931\u8d25: " + MusicCommand.a(throwable)));
                    }
                });
                break;
            }
            case "toplist": 
            case "tops": 
            case "ranklist": {
                ClientChatFormatting.a("\u00a77\u6b63\u5728\u83b7\u53d6\u699c\u5355\u5217\u8868...");
                MusicCommand.a(() -> {
                    try {
                        List<k> list = g.a().i();
                        MusicCommand.b(() -> {
                            if (list.isEmpty()) {
                                ClientChatFormatting.a("\u00a77\u6ca1\u6709\u53d6\u5230\u699c\u5355");
                                return;
                            }
                            ClientChatFormatting.a("\u00a77\u699c\u5355 \u00a7b(" + list.size() + ")\u00a77:");
                            int n2 = Math.min(list.size(), 12);
                            for (int i2 = 0; i2 < n2; ++i2) {
                                k k2 = (k)list.get(i2);
                                ClientChatFormatting.a(false, "\u00a7b  " + k2.a() + " \u00a77" + k2.b() + " \u00a78" + k2.d());
                            }
                            ClientChatFormatting.a("\u00a77\u7528 \u00a7b.music rank <\u699c\u5355ID> \u00a77\u8f7d\u5165\u699c\u5355");
                        });
                    }
                    catch (Throwable throwable) {
                        MusicCommand.b(() -> ClientChatFormatting.a("\u00a7c\u83b7\u53d6\u699c\u5355\u5931\u8d25: " + MusicCommand.a(throwable)));
                    }
                });
                break;
            }
            case "rank": 
            case "top": {
                if (stringArray.length < 2) {
                    ClientChatFormatting.a("\u00a7c\u7528\u6cd5: .music rank <\u699c\u5355ID>\uff08\u5148 .music toplist \u67e5\u770b\uff09");
                    return;
                }
                MusicCommand.b(stringArray[1]);
                break;
            }
            case "album": 
            case "playlist": 
            case "pl": {
                if (stringArray.length < 2) {
                    ClientChatFormatting.a("\u00a7c\u7528\u6cd5: .music " + string + " <\u6b4c\u5355/\u4e13\u8f91ID \u6216 \u5206\u4eab\u94fe\u63a5>");
                    return;
                }
                MusicCommand.a(stringArray[1]);
                break;
            }
            case "online": 
            case "stream": {
                if (stringArray.length < 2) {
                    ClientChatFormatting.a("\u00a7c\u7528\u6cd5: .music online <\u5e8f\u53f7>\uff08\u5148\u7528 search / list / rank \u8f7d\u5165\u5217\u8868\uff09");
                    return;
                }
                Integer n4 = MusicCommand.c(stringArray[1]);
                if (n4 == null || n4 < 1 || n4 > c2.p().size()) {
                    ClientChatFormatting.a("\u00a7c\u5e8f\u53f7\u65e0\u6548\uff0c\u5f53\u524d\u5728\u7ebf\u5217\u8868\u5171 \u00a7b" + c2.p().size() + " \u00a7c\u9996");
                    return;
                }
                c2.b(n4 - 1);
                break;
            }
            case "download": 
            case "dl": 
            case "save": {
                if (stringArray.length < 2) {
                    ClientChatFormatting.a("\u00a7c\u7528\u6cd5: .music download <\u5e8f\u53f7>");
                    return;
                }
                Integer n5 = MusicCommand.c(stringArray[1]);
                List<i> list = c2.p();
                if (n5 == null || n5 < 1 || n5 > list.size()) {
                    ClientChatFormatting.a("\u00a7c\u5e8f\u53f7\u65e0\u6548\uff0c\u5f53\u524d\u5728\u7ebf\u5217\u8868\u5171 \u00a7b" + list.size() + " \u00a7c\u9996");
                    return;
                }
                MusicCommand.a(list.get(n5 - 1));
                break;
            }
            case "cover": 
            case "pic": {
                i i3 = c2.o();
                if (i3 == null) {
                    ClientChatFormatting.a("\u00a77\u5f53\u524d\u6ca1\u6709\u5728\u64ad\u7684\u5728\u7ebf\u6b4c\u66f2");
                    return;
                }
                MusicCommand.a(() -> {
                    File file = g.a().c().a(g.b(i3), i3.e());
                    MusicCommand.b(() -> ClientChatFormatting.a((String)(file == null ? "\u00a7c\u5c01\u9762\u4e0b\u8f7d\u5931\u8d25" : "\u00a77\u5c01\u9762\u5df2\u7f13\u5b58: \u00a7b" + file.getAbsolutePath())));
                });
                break;
            }
            case "url": 
            case "link": {
                i i4 = c2.o();
                if (i4 == null) {
                    ClientChatFormatting.a("\u00a77\u5f53\u524d\u6ca1\u6709\u5728\u64ad\u7684\u5728\u7ebf\u6b4c\u66f2");
                    return;
                }
                MusicCommand.a(() -> {
                    try {
                        j j2 = g.a().a(i4);
                        MusicCommand.b(() -> ClientChatFormatting.a((String)(j2.i() ? "\u00a7c\u8fd9\u9996\u6ca1\u6709\u53ef\u7528\u7684\u64ad\u653e\u5730\u5740\uff08\u7248\u6743\u53d7\u9650\uff09" : "\u00a77" + j2.c() + " \u00a78" + j2.d() / 1000 + "kbps \u00a7b" + j2.b())));
                    }
                    catch (Throwable throwable) {
                        MusicCommand.b(() -> ClientChatFormatting.a("\u00a7c\u53d6\u76f4\u94fe\u5931\u8d25: " + MusicCommand.a(throwable)));
                    }
                });
                break;
            }
            case "level": 
            case "quality": {
                g g2 = g.a();
                if (stringArray.length < 2) {
                    ClientChatFormatting.a("\u00a77\u5f53\u524d\u97f3\u8d28: \u00a7b" + g2.e() + " \u00a78(" + g2.d() + ")");
                    StringBuilder stringBuilder = new StringBuilder("\u00a77\u53ef\u9009: ");
                    for (String[] stringArray2 : com.heypixel.heypixelmod.obsoverlay.music.AudioFormatSupport.d.LEVELS) {
                        stringBuilder.append("\u00a7b").append(stringArray2[0]).append("\u00a77(").append(stringArray2[1]).append(") ");
                    }
                    ClientChatFormatting.a(false, stringBuilder.toString());
                    ClientChatFormatting.a("\u00a78\u63d0\u793a: \u5728\u7ebf\u64ad\u653e\u53ea\u652f\u6301 mp3 \u6863\u4f4d(standard/exhigh)\uff0cFLAC \u6863\u4f4d\u8bf7\u4e0b\u8f7d");
                    return;
                }
                if (g2.a(stringArray[1])) {
                    ClientChatFormatting.a("\u00a77\u97f3\u8d28\u5df2\u8bbe\u7f6e\u4e3a \u00a7b" + g2.e());
                    break;
                }
                ClientChatFormatting.a("\u00a7c\u672a\u77e5\u97f3\u8d28\u6863\u4f4d: " + stringArray[1]);
                break;
            }
            case "ip": {
                g g3 = g.a();
                if (stringArray.length < 2) {
                    ClientChatFormatting.a("\u00a77\u63a5\u53e3\u914d\u989d\u6309\u8bf7\u6c42\u91cc\u7684 \u00a7bip\u00a77 \u53c2\u6570\u7edf\u8ba1\uff08\u6bcf\u63a5\u53e3 10 \u6b21/\u5206\u949f\uff09");
                    ClientChatFormatting.a(false, "\u00a77\u672c\u673aIP: \u00a7b" + String.valueOf(g3.b().a().a()));
                    ClientChatFormatting.a(false, "\u00a77\u81ea\u5b9a\u4e49IP: \u00a7b" + String.valueOf(g3.b().a().c()));
                    ClientChatFormatting.a(false, "\u00a77\u81ea\u52a8\u8f6e\u6362: \u00a7b" + g3.b().a().b() + " \u00a77/ \u4f18\u5148\u672c\u673aIP: \u00a7b" + g3.g());
                    ClientChatFormatting.a("\u00a77\u7528\u6cd5: \u00a7b.music ip add <IP> \u00a77| \u00a7b.music ip clear \u00a77| \u00a7b.music ip auto <on|off>");
                    return;
                }
                String string5 = stringArray[1].toLowerCase(Locale.ROOT);
                if (string5.equals("add") && stringArray.length >= 3) {
                    String string6 = stringArray[2].trim();
                    if (!com.heypixel.heypixelmod.obsoverlay.music.AudioFormatSupport.c.e(string6)) {
                        ClientChatFormatting.a("\u00a7c\u4e0d\u662f\u5408\u6cd5\u7684 IPv4 \u5730\u5740: " + string6);
                        return;
                    }
                    g3.c(string6);
                    ClientChatFormatting.a("\u00a77\u5df2\u52a0\u5165 IP \u6c60: \u00a7b" + string6);
                    break;
                }
                if (string5.equals("clear")) {
                    g3.h();
                    ClientChatFormatting.a("\u00a77\u81ea\u5b9a\u4e49 IP \u6c60\u5df2\u6e05\u7a7a");
                    break;
                }
                if (string5.equals("auto") && stringArray.length >= 3) {
                    boolean bl = stringArray[2].equalsIgnoreCase("on") || stringArray[2].equalsIgnoreCase("true");
                    g3.b().a().a(bl);
                    ClientChatFormatting.a("\u00a77\u81ea\u52a8\u8f6e\u6362 IP: \u00a7b" + bl);
                    break;
                }
                ClientChatFormatting.a("\u00a7c\u7528\u6cd5: .music ip add <IP> | clear | auto <on|off>");
                break;
            }
            case "api": {
                g g4 = g.a();
                ClientChatFormatting.a("\u00a77\u2014\u2014 \u7f51\u6613\u4e91\u89e3\u6790\u63a5\u53e3 \u2014\u2014");
                ClientChatFormatting.a(false, "\u00a77\u5730\u5740: \u00a7b" + g4.b().b());
                ClientChatFormatting.a(false, "\u00a77\u97f3\u8d28: \u00a7b" + g4.e());
                ClientChatFormatting.a(false, "\u00a77\u5728\u7ebf\u66f2\u76ee: \u00a7b" + c2.p().size() + " \u00a77\u9996");
                i i5 = c2.o();
                if (i5 != null) {
                    ClientChatFormatting.a(false, "\u00a77\u5728\u64ad: \u00a7b" + i5.j() + " \u00a78" + i5.k());
                }
                ClientChatFormatting.a(false, "\u00a77\u5c01\u9762\u7f13\u5b58\u76ee\u5f55: \u00a7b" + g4.c().b().getAbsolutePath());
                ClientChatFormatting.a("\u00a77\u91cd\u7f6e\u672c\u673aIP: \u00a7b.music api refresh");
                if (stringArray.length < 2 || !stringArray[1].equalsIgnoreCase("refresh")) break;
                MusicCommand.a(() -> {
                    try {
                        String string = g4.b().c();
                        MusicCommand.b(() -> ClientChatFormatting.a("\u00a77\u63a5\u53e3\u8bc6\u522b\u5230\u672c\u673a IP: \u00a7b" + string));
                    }
                    catch (Throwable throwable) {
                        MusicCommand.b(() -> ClientChatFormatting.a("\u00a7c\u83b7\u53d6\u5931\u8d25: " + MusicCommand.a(throwable)));
                    }
                });
                break;
            }
            default: {
                MusicCommand.e();
            }
        }
    }

    private static void a(String string) {
        ClientChatFormatting.a("\u00a77\u6b63\u5728\u89e3\u6790: \u00a7b" + string);
        MusicCommand.a(() -> {
            try {
                h h2 = g.a().e(string);
                com.heypixel.heypixelmod.obsoverlay.music.MusicLibraryManager.a().a(h2.h());
                MusicCommand.b(() -> {
                    MusicCommand.a(h2.a().a() + "\u300c" + h2.c() + "\u300d" + (String)(h2.e().isEmpty() ? "" : " \u00a78by " + h2.e()), h2.h());
                    if (!h2.h().isEmpty()) {
                        ClientChatFormatting.a("\u00a77\u7528 \u00a7b.music play <\u5e8f\u53f7> \u00a77\u64ad\u653e\uff0c\u00a7b.music download <\u5e8f\u53f7> \u00a77\u4e0b\u8f7d");
                    }
                });
            }
            catch (Throwable throwable) {
                MusicCommand.b(() -> ClientChatFormatting.a("\u00a7c\u89e3\u6790\u5931\u8d25: " + MusicCommand.a(throwable)));
            }
        });
    }

    private static void b(String string) {
        long l2;
        try {
            l2 = Long.parseLong(string.trim());
        }
        catch (NumberFormatException numberFormatException) {
            ClientChatFormatting.a("\u00a7c\u699c\u5355 ID \u5fc5\u987b\u662f\u6570\u5b57");
            return;
        }
        ClientChatFormatting.a("\u00a77\u6b63\u5728\u8f7d\u5165\u699c\u5355 \u00a7b" + l2 + "\u00a77...");
        MusicCommand.a(() -> {
            try {
                h h2 = g.a().a(l2);
                com.heypixel.heypixelmod.obsoverlay.music.MusicLibraryManager.a().a(h2.h());
                MusicCommand.b(() -> {
                    MusicCommand.a("\u699c\u5355\u300c" + h2.c() + "\u300d", h2.h());
                    if (!h2.h().isEmpty()) {
                        ClientChatFormatting.a("\u00a77\u7528 \u00a7b.music play <\u5e8f\u53f7> \u00a77\u64ad\u653e\uff0c\u00a7b.music download <\u5e8f\u53f7> \u00a77\u4e0b\u8f7d");
                    }
                });
            }
            catch (Throwable throwable) {
                MusicCommand.b(() -> ClientChatFormatting.a("\u00a7c\u8f7d\u5165\u699c\u5355\u5931\u8d25: " + MusicCommand.a(throwable)));
            }
        });
    }

    private static void a(i i2) {
        ClientChatFormatting.a("\u00a77\u5f00\u59cb\u4e0b\u8f7d: \u00a7b" + i2.j() + " \u00a78(" + i2.k() + ")");
        MusicCommand.a(() -> {
            final int[] nArray = new int[]{-10};
            try {
                File file = g.a().a(i2, new g.b(){

                    @Override
                    public void a(long l2, long l3) {
                        if (l3 <= 0L) {
                            return;
                        }
                        int n2 = (int)(l2 * 100L / l3);
                        if (n2 >= nArray[0] + 20) {
                            nArray[0] = n2;
                            MusicCommand.b(() -> ClientChatFormatting.a("\u00a77\u4e0b\u8f7d\u4e2d... \u00a7b" + n2 + "%"));
                        }
                    }
                });
                MusicCommand.b(() -> {
                    ClientChatFormatting.a("\u00a7a\u4e0b\u8f7d\u5b8c\u6210: \u00a7b" + file.getName());
                    com.heypixel.heypixelmod.obsoverlay.music.MusicLibraryManager.a().l();
                });
            }
            catch (Throwable throwable) {
                MusicCommand.b(() -> ClientChatFormatting.a("\u00a7c\u4e0b\u8f7d\u5931\u8d25: " + MusicCommand.a(throwable)));
            }
        });
    }

    private static void a(String string, List<i> list) {
        if (list == null || list.isEmpty()) {
            ClientChatFormatting.a("\u00a77" + string + ": \u6ca1\u6709\u7ed3\u679c");
            return;
        }
        ClientChatFormatting.a("\u00a77" + string + " \u00a7b(" + list.size() + ")\u00a77:");
        int n2 = Math.min(list.size(), 12);
        for (int i2 = 0; i2 < n2; ++i2) {
            i i3 = list.get(i2);
            String string2 = i3.i() ? "\u00a78[\u7248\u6743]" : (i3.g() ? "\u00a78[\u514d\u8d39]" : "\u00a78[VIP]");
            ClientChatFormatting.a(false, "\u00a7b  " + (i2 + 1) + ". \u00a7f" + i3.b() + " \u00a77- " + i3.c() + " \u00a78" + i3.k() + " " + string2);
        }
        if (list.size() > n2) {
            ClientChatFormatting.a("\u00a77... \u4ee5\u53ca\u53e6\u5916 " + (list.size() - n2) + " \u9996");
        }
    }

    private static void a(com.heypixel.heypixelmod.obsoverlay.music.MusicLibraryManager c2) {
        String string;
        Object object;
        if (c2.n()) {
            object = c2.o();
            switch (c2.c().c()) {
                case PLAYING: {
                    String string2 = "\u64ad\u653e\u4e2d";
                    break;
                }
                case PAUSED: {
                    String string2 = "\u5df2\u6682\u505c";
                    break;
                }
                default: {
                    String string2 = string = "\u5df2\u505c\u6b62";
                }
            }
            if (object != null) {
                ClientChatFormatting.a("\u00a77" + string + " \u00a78(\u5728\u7ebf): \u00a7b" + ((i)object).j());
                return;
            }
        }
        if ((object = c2.h()) == null) {
            ClientChatFormatting.a("\u00a77\u6ca1\u6709\u6b63\u5728\u64ad\u653e\u7684\u97f3\u4e50");
            return;
        }
        string = switch (c2.c().c()) {
            case b.a.PLAYING -> "\u64ad\u653e\u4e2d";
            case b.a.PAUSED -> "\u5df2\u6682\u505c";
            default -> "\u5df2\u505c\u6b62";
        };
        ClientChatFormatting.a("\u00a77" + string + ": \u00a7b" + ((com.heypixel.heypixelmod.obsoverlay.music.MusicTrackMetadata)object).h());
    }

    private static void e() {
        ClientChatFormatting.a("\u00a77\u00a7l\u672c\u5730\u00a7r \u00a77.music \u00a7bplay [\u5173\u952e\u5b57|\u5e8f\u53f7] \u00a77/ \u00a7bpause \u00a77/ \u00a7bnext \u00a77/ \u00a7bprev \u00a77/ \u00a7bstop");
        ClientChatFormatting.a("\u00a77.music \u00a7bvolume <0-100> \u00a77/ \u00a7blist \u00a77/ \u00a7bscan \u00a77/ \u00a7bopen \u00a77/ \u00a7bfolder \u00a77/ \u00a7bstatus \u00a77/ \u00a7blyric");
        ClientChatFormatting.a("\u00a77\u00a7l\u5728\u7ebf\uff08\u89e3\u6790\u63a5\u53e3\uff09\u00a7r");
        ClientChatFormatting.a("\u00a77.music \u00a7bsearch <\u5173\u952e\u8bcd> \u00a77\u2014\u2014 \u641c\u6b4c\uff08\u4f1a\u5458\u6b4c\u66f2\u53ef\u64ad/\u53ef\u4e0b\uff09");
        ClientChatFormatting.a("\u00a77.music \u00a7btoplist \u00a77\u2014\u2014 \u699c\u5355\u5217\u8868\uff1b \u00a7brank <ID> \u00a77\u8f7d\u5165\u699c\u5355");
        ClientChatFormatting.a("\u00a77.music \u00a7blist <\u6b4c\u5355/\u4e13\u8f91ID\u6216\u94fe\u63a5> \u00a77\u2014\u2014 \u6b4c\u5355\u3001\u4e13\u8f91\u89e3\u6790");
        ClientChatFormatting.a("\u00a77.music \u00a7bonline <\u5e8f\u53f7> \u00a77\u64ad\u653e / \u00a7bdownload <\u5e8f\u53f7> \u00a77\u4e0b\u8f7d\u5230\u97f3\u4e50\u76ee\u5f55");
        ClientChatFormatting.a("\u00a77.music \u00a7blevel [\u6863\u4f4d] \u00a77/ \u00a7bip \u00a77/ \u00a7bcover \u00a77/ \u00a7burl \u00a77/ \u00a7bapi");
    }

    private static void f() {
        NowPlayingStatusManager c2 = NowPlayingStatusManager.a();
        com.heypixel.heypixelmod.obsoverlay.music.lyric.LyricParser b2 = com.heypixel.heypixelmod.obsoverlay.music.lyric.LyricParser.a();
        ClientChatFormatting.a("\u00a77\u2014\u2014 \u97f3\u4e50\u64ad\u653e\u5668\u72b6\u6001 \u2014\u2014");
        for (String string : c2.j().split("\n")) {
            ClientChatFormatting.a(false, "\u00a77" + string);
        }
        b b3 = c2.i();
        if (!b3.k()) {
            ClientChatFormatting.a(false, "\u00a77\u8fdb\u5ea6: \u00a7f" + com.heypixel.heypixelmod.obsoverlay.music.MusicTrackMetadata.b(b3.m()) + " / " + com.heypixel.heypixelmod.obsoverlay.music.MusicTrackMetadata.b(b3.f()));
        }
        ClientChatFormatting.a(false, "\u00a77\u6b4c\u8bcd: \u00a7b" + (String)(b2.l() ? b2.k().e() + " \u884c" : (b2.m() ? "\u52a0\u8f7d\u4e2d..." : "\u65e0")) + (String)(b2.n().isEmpty() ? "" : " \u00a77(" + b2.n() + ")"));
        if (b3.k()) {
            ClientChatFormatting.a("\u00a77\u63d0\u793a: \u9700\u8981\u5728 ClickGUI \u91cc\u5f00\u542f \u00a7bMusicPlayer\u00a77 \u6a21\u5757\uff1b");
            ClientChatFormatting.a("\u00a77\u7f51\u6613\u4e91\u8bc6\u522b\u4f9d\u8d56\u7a97\u53e3\u6807\u9898\uff0c\u8bf7\u786e\u4fdd\u7f51\u6613\u4e91\u5ba2\u6237\u7aef\u7a97\u53e3\u6ca1\u6709\u88ab\u6700\u5c0f\u5316\u5230\u6258\u76d8\u3002");
        }
    }

    private static MusicPlayer g() {
        try {
            return EixClientCore.a().g().a(MusicPlayer.class);
        }
        catch (Throwable throwable) {
            return null;
        }
    }

    private static void a(Runnable runnable) {
        BackgroundExecutor.a(runnable);
    }

    private static void b(Runnable runnable) {
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

    private static Integer c(String string) {
        try {
            return Integer.parseInt(string.trim());
        }
        catch (NumberFormatException numberFormatException) {
            return null;
        }
    }

    private static String a(Throwable throwable) {
        String string = throwable.getMessage();
        return string == null || string.isBlank() ? throwable.getClass().getSimpleName() : string;
    }

    private static String a(String[] stringArray, int n2) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i2 = n2; i2 < stringArray.length; ++i2) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(' ');
            }
            stringBuilder.append(stringArray[i2]);
        }
        return stringBuilder.toString();
    }

    @Override
    public String[] b(String[] stringArray) {
        return new String[]{"play", "pause", "next", "prev", "stop", "volume", "list", "scan", "open", "folder", "status", "lyric", "search", "toplist", "rank", "album", "online", "download", "level", "ip", "cover", "url", "api"};
    }
}

