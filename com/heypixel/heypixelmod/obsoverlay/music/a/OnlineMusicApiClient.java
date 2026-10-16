/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.music.a;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.heypixel.heypixelmod.obsoverlay.music.a.MusicApiHttpClient;
import com.heypixel.heypixelmod.obsoverlay.music.a.TimedLyricsBundle;
import com.heypixel.heypixelmod.obsoverlay.music.a.MusicCollectionResult;
import com.heypixel.heypixelmod.obsoverlay.music.a.PlaybackTimelineModel;
import com.heypixel.heypixelmod.obsoverlay.music.a.MusicAudioDownloadInfo;
import com.heypixel.heypixelmod.obsoverlay.music.a.MusicPlaylistMetadata;
import com.heypixel.heypixelmod.obsoverlay.music.MusicLibraryManager;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class OnlineMusicApiClient {
    private static final Logger LOGGER = LogManager.getLogger(OnlineMusicApiClient.class);
    private static volatile OnlineMusicApiClient instance;
    private final MusicApiHttpClient api = new MusicApiHttpClient();
    private final com.heypixel.heypixelmod.obsoverlay.music.AudioFormatSupport.AudioFormat coverCache = com.heypixel.heypixelmod.obsoverlay.music.AudioFormatSupport.AudioFormat.a();
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final File configFile;
    private volatile String level = "lossless";
    private volatile String immerseType = "";
    private volatile boolean preferLocalIp = true;
    private final List<PlaybackTimelineModel> lastResult = new ArrayList<PlaybackTimelineModel>();
    private volatile String lastResultTitle = "";

    private OnlineMusicApiClient() {
        File file = com.heypixel.heypixelmod.obsoverlay.ClientVersion.b.clientFolder != null ? com.heypixel.heypixelmod.obsoverlay.ClientVersion.b.clientFolder : new File(System.getProperty("user.home"), "EixClient");
        this.configFile = new File(file, "music_api.json");
        this.l();
        this.api.a("https://nextmusic.toubiec.cn");
        this.api.a().a(true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static OnlineMusicApiClient a() {
        if (instance != null) return instance;
        Class<OnlineMusicApiClient> clazz = OnlineMusicApiClient.class;
        synchronized (OnlineMusicApiClient.class) {
            if (instance != null) return instance;
            instance = new OnlineMusicApiClient();
            // ** MonitorExit[var0] (shouldn't be in output)
            return instance;
        }
    }

    public MusicApiHttpClient b() {
        return this.api;
    }

    public com.heypixel.heypixelmod.obsoverlay.music.AudioFormatSupport.AudioFormat c() {
        return this.coverCache;
    }

    private void l() {
        if (!this.configFile.isFile()) {
            return;
        }
        try (BufferedReader bufferedReader = Files.newBufferedReader(this.configFile.toPath(), StandardCharsets.UTF_8);){
            ApiConfig a2 = (ApiConfig)this.gson.fromJson((Reader)bufferedReader, ApiConfig.class);
            if (a2 == null) {
                return;
            }
            if (a2.baseUrl != null && !a2.baseUrl.isBlank()) {
                this.api.a(a2.baseUrl);
            }
            if (a2.level != null && !a2.level.isBlank()) {
                this.level = a2.level;
            }
            if (a2.immerseType != null) {
                this.immerseType = a2.immerseType;
            }
            this.preferLocalIp = a2.preferLocalIp;
            if (a2.ipPool != null) {
                for (String string : a2.ipPool) {
                    this.api.a().b(string);
                }
            }
        }
        catch (Throwable throwable) {
            LOGGER.warn("\u8bfb\u53d6\u97f3\u4e50\u63a5\u53e3\u914d\u7f6e\u5931\u8d25", throwable);
        }
    }

    private void m() {
        try {
            ApiConfig a2 = new ApiConfig();
            a2.baseUrl = this.api.b();
            a2.level = this.level;
            a2.immerseType = this.immerseType;
            a2.preferLocalIp = this.preferLocalIp;
            a2.ipPool = this.api.a().c();
            try (BufferedWriter bufferedWriter = Files.newBufferedWriter(this.configFile.toPath(), StandardCharsets.UTF_8, new OpenOption[0]);){
                this.gson.toJson((Object)a2, (Appendable)bufferedWriter);
            }
        }
        catch (Throwable throwable) {
            LOGGER.warn("\u4fdd\u5b58\u97f3\u4e50\u63a5\u53e3\u914d\u7f6e\u5931\u8d25", throwable);
        }
    }

    public String d() {
        return this.level;
    }

    public String e() {
        for (String[] stringArray : MusicApiHttpClient.LEVELS) {
            if (!stringArray[0].equalsIgnoreCase(this.level)) continue;
            return stringArray[1];
        }
        return this.level;
    }

    public boolean a(String string) {
        if (string == null) {
            return false;
        }
        String string2 = string.trim().toLowerCase(Locale.ROOT);
        for (String[] stringArray : MusicApiHttpClient.LEVELS) {
            if (!stringArray[0].equalsIgnoreCase(string2) && !stringArray[1].contains(string.trim())) continue;
            this.level = stringArray[0];
            this.m();
            return true;
        }
        return false;
    }

    public String f() {
        return this.immerseType;
    }

    public void b(String string) {
        this.immerseType = string == null ? "" : string.trim();
        this.m();
    }

    public boolean g() {
        return this.preferLocalIp;
    }

    public void a(boolean bl) {
        this.preferLocalIp = bl;
        if (bl) {
            this.api.a().a(null);
        }
        this.m();
    }

    public void c(String string) {
        this.api.a().b(string);
        this.m();
    }

    public void h() {
        this.api.a().d();
        this.m();
    }

    public List<PlaybackTimelineModel> d(String string) throws Exception {
        this.n();
        List<PlaybackTimelineModel> list = this.api.a(string, 30, 0);
        this.a("\u641c\u7d22\uff1a" + string, list);
        return list;
    }

    public MusicCollectionResult e(String string) throws Exception {
        MusicCollectionResult h2;
        this.n();
        String[] stringArray = MusicApiHttpClient.b(string);
        String string2 = stringArray[0];
        String string3 = stringArray[1];
        if (string3.isBlank()) {
            throw new IllegalArgumentException("\u65e0\u6cd5\u4ece\u8fd9\u6bb5\u5185\u5bb9\u91cc\u8bc6\u522b\u51fa\u6b4c\u5355/\u4e13\u8f91 ID\uff1a" + string);
        }
        long l2 = Long.parseLong(string3);
        if ("album".equals(string2)) {
            h2 = this.api.d(l2);
        } else if ("song".equals(string2)) {
            PlaybackTimelineModel i2 = this.api.a(l2);
            List<Object> list = i2 == null ? List.of() : List.of(i2);
            h2 = new MusicCollectionResult(com.heypixel.heypixelmod.obsoverlay.music.a.MusicCollectionResult.CollectionKind.PLAYLIST, l2, i2 == null ? "" : i2.b(), i2 == null ? "" : i2.e(), i2 == null ? "" : i2.c(), "", list.size(), list);
            string2 = "playlist";
        } else {
            h2 = this.api.c(l2);
        }
        this.a(h2.a().a() + "\uff1a" + h2.c(), h2.h());
        return h2;
    }

    public List<MusicPlaylistMetadata> i() throws Exception {
        this.n();
        return this.api.e();
    }

    public MusicCollectionResult a(long l2) throws Exception {
        this.n();
        MusicCollectionResult h2 = this.api.c(l2);
        this.a("\u699c\u5355\uff1a" + h2.c(), h2.h());
        return h2;
    }

    public PlaybackTimelineModel b(long l2) throws Exception {
        this.n();
        return this.api.a(l2);
    }

    public MusicAudioDownloadInfo a(PlaybackTimelineModel i2) throws Exception {
        this.n();
        return this.api.a(i2.a(), this.level, this.immerseType);
    }

    public MusicAudioDownloadInfo c(long l2) throws Exception {
        this.n();
        return this.api.a(l2, this.level, this.immerseType);
    }

    public InputStream f(String string) throws Exception {
        return this.api.a(string, 0L);
    }

    public static String b(PlaybackTimelineModel i2) {
        return i2 == null ? "" : "song_" + i2.a();
    }

    public File c(PlaybackTimelineModel i2) {
        if (i2 == null) {
            return null;
        }
        String string = OnlineMusicApiClient.b(i2);
        File file = this.coverCache.a(string);
        if (file != null) {
            return file;
        }
        if (!i2.e().isBlank()) {
            this.coverCache.a(string, i2.e(), null);
        }
        return null;
    }

    public void a(PlaybackTimelineModel i2, Consumer<File> consumer) {
        if (i2 == null || i2.e().isBlank()) {
            return;
        }
        this.coverCache.a(OnlineMusicApiClient.b(i2), i2.e(), consumer);
    }

    public File a(PlaybackTimelineModel i2, DownloadListener b2) throws Exception {
        Object object;
        MusicAudioDownloadInfo j2 = this.a(i2);
        if (j2.i()) {
            throw new IllegalStateException("\u8be5\u6b4c\u66f2\u6ca1\u6709\u53ef\u7528\u7684\u64ad\u653e\u5730\u5740\uff08\u7248\u6743\u53d7\u9650\uff09");
        }
        MusicLibraryManager c2 = MusicLibraryManager.a();
        File file = c2.d();
        if (file == null) {
            object = com.heypixel.heypixelmod.obsoverlay.ClientVersion.b.clientFolder != null ? com.heypixel.heypixelmod.obsoverlay.ClientVersion.b.clientFolder : new File(System.getProperty("user.home"), "EixClient");
            file = new File((File)object, "music");
        }
        if (!file.exists()) {
            file.mkdirs();
        }
        object = OnlineMusicApiClient.h(i2.c() + " - " + i2.b()) + "." + j2.j();
        File file2 = new File(file, (String)object);
        File file3 = new File(file, (String)object + ".part");
        long l2 = j2.e() > 0L ? j2.e() : -1L;
        long l3 = 0L;
        try (InputStream inputStream = this.api.a(j2.b(), 0L);
             OutputStream outputStream = Files.newOutputStream(file3.toPath(), new OpenOption[0]);){
            int n2;
            byte[] byArray = new byte[65536];
            while ((n2 = inputStream.read(byArray)) > 0) {
                outputStream.write(byArray, 0, n2);
                l3 += (long)n2;
                if (b2 == null) continue;
                b2.a(l3, l2);
            }
        }
        catch (Throwable throwable) {
            file3.delete();
            if (b2 != null) {
                b2.a(throwable.getMessage() == null ? "\u4e0b\u8f7d\u5931\u8d25" : throwable.getMessage());
            }
            throw throwable;
        }
        if (l3 <= 0L) {
            file3.delete();
            throw new IllegalStateException("\u4e0b\u8f7d\u5185\u5bb9\u4e3a\u7a7a");
        }
        Files.move(file3.toPath(), file2.toPath(), StandardCopyOption.REPLACE_EXISTING);
        this.a(i2, (Consumer<File>)null);
        if (b2 != null) {
            b2.a(file2);
        }
        return file2;
    }

    public PlaybackTimelineModel a(String string, String string2, long l2) {
        if (string == null || string.isBlank()) {
            return null;
        }
        try {
            this.n();
            String string3 = string2 == null || string2.isBlank() ? string : string + " " + string2;
            List<PlaybackTimelineModel> list = this.api.a(string3, 20, 0);
            return OnlineMusicApiClient.a(list, string, string2, l2);
        }
        catch (Throwable throwable) {
            LOGGER.debug("\u6309\u6b4c\u540d\u5339\u914d\u6b4c\u66f2\u5931\u8d25: {} - {}", (Object)string, (Object)string2, (Object)throwable);
            return null;
        }
    }

    public TimedLyricsBundle b(String string, String string2, long l2) {
        try {
            PlaybackTimelineModel i2 = this.a(string, string2, l2);
            if (i2 == null) {
                return null;
            }
            TimedLyricsBundle f2 = this.api.b(i2.a());
            return f2.d() ? null : f2;
        }
        catch (Throwable throwable) {
            LOGGER.debug("\u6309\u6b4c\u540d\u83b7\u53d6\u6b4c\u8bcd\u5931\u8d25: {} - {}", (Object)string, (Object)string2, (Object)throwable);
            return null;
        }
    }

    public TimedLyricsBundle d(long l2) {
        try {
            this.n();
            TimedLyricsBundle f2 = this.api.b(l2);
            return f2.d() ? null : f2;
        }
        catch (Throwable throwable) {
            LOGGER.debug("\u6309 ID \u83b7\u53d6\u6b4c\u8bcd\u5931\u8d25: {}", (Object)l2, (Object)throwable);
            return null;
        }
    }

    private static PlaybackTimelineModel a(List<PlaybackTimelineModel> list, String string, String string2, long l2) {
        PlaybackTimelineModel i2 = null;
        int n2 = Integer.MIN_VALUE;
        String string3 = OnlineMusicApiClient.g(string);
        String string4 = string2 == null ? "" : string2.toLowerCase(Locale.ROOT);
        for (PlaybackTimelineModel i3 : list) {
            int n3 = 0;
            String string5 = OnlineMusicApiClient.g(i3.b());
            if (string5.equals(string3)) {
                n3 += 100;
            } else if (string5.contains(string3) || string3.contains(string5)) {
                n3 += 50;
            }
            if (!string4.isBlank()) {
                String[] stringArray = i3.c().toLowerCase(Locale.ROOT);
                int n4 = 0;
                for (String string6 : string4.split("[/\u3001,\uff0c&;\uff1b]")) {
                    String string7 = string6.trim();
                    if (string7.isEmpty() || !stringArray.contains(string7)) continue;
                    n4 = 1;
                    break;
                }
                n3 += n4 != 0 ? 50 : -30;
            }
            for (String string8 : new String[]{"\u7ffb\u5531", "cover", "\u94a2\u7434\u7248", "\u7eaf\u97f3\u4e50", "\u4f34\u594f", "remix", "\u7247\u6bb5", "\u526a\u8f91"}) {
                if (!string5.contains(string8)) continue;
                n3 -= 35;
            }
            if (l2 > 0L && i3.f() > 0L && Math.abs(l2 - i3.f()) <= 3000L) {
                n3 += 30;
            }
            if (n3 <= n2) continue;
            n2 = n3;
            i2 = i3;
        }
        return n2 >= 40 ? i2 : null;
    }

    private static String g(String string) {
        if (string == null) {
            return "";
        }
        return string.toLowerCase(Locale.ROOT).replaceAll("[\uff08(\u3010\\[][^\uff09)\u3011\\]]*[\uff09)\u3011\\]]", "").trim();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void a(String string, List<PlaybackTimelineModel> list) {
        List<PlaybackTimelineModel> list2 = this.lastResult;
        synchronized (list2) {
            this.lastResult.clear();
            if (list != null) {
                this.lastResult.addAll(list);
            }
        }
        this.lastResultTitle = string == null ? "" : string;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<PlaybackTimelineModel> j() {
        List<PlaybackTimelineModel> list = this.lastResult;
        synchronized (list) {
            return List.copyOf(this.lastResult);
        }
    }

    public String k() {
        return this.lastResultTitle;
    }

    private void n() {
        if (this.preferLocalIp) {
            this.api.d();
        }
    }

    private static String h(String string) {
        String string2 = string == null ? "" : string;
        if ((string2 = string2.replaceAll("[\\\\/:*?\"<>|]", "_").trim()).isEmpty()) {
            string2 = "unknown";
        }
        return string2.length() > 120 ? string2.substring(0, 120) : string2;
    }

    private static class ApiConfig {
        String baseUrl = "https://nextmusic.toubiec.cn";
        String level = "lossless";
        String immerseType = "";
        boolean preferLocalIp = true;
        List<String> ipPool = new ArrayList<String>();

        private ApiConfig() {
        }
    }

    public static interface DownloadListener {
        public void a(long var1, long var3);

        default public void a(File file) {
        }

        default public void a(String string) {
        }
    }
}

