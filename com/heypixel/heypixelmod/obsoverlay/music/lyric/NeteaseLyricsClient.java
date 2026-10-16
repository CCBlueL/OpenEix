/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.music.lyric;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.heypixel.heypixelmod.obsoverlay.b.ClientConfigurationManager;
import com.heypixel.heypixelmod.obsoverlay.music.a.TimedLyricsBundle;
import com.heypixel.heypixelmod.obsoverlay.music.a.OnlineMusicApiClient;
import com.heypixel.heypixelmod.obsoverlay.music.a.PlaybackTimelineModel;
import com.heypixel.heypixelmod.obsoverlay.music.lyric.LrcParser;
import com.heypixel.heypixelmod.obsoverlay.music.lyric.LyricLine;
import com.heypixel.heypixelmod.obsoverlay.music.lyric.LyricDocument;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class NeteaseLyricsClient {
    private static final Logger LOGGER = LogManager.getLogger(NeteaseLyricsClient.class);
    private static final String SEARCH_URL = "https://music.163.com/api/search/get/web?s=%s&type=%d&offset=0&limit=%d";
    private static final String ARTIST_SONGS_URL = "https://music.163.com/api/v1/artist/songs?id=%d&limit=100&offset=0&order=hot";
    private static final String LYRIC_URL = "https://music.163.com/api/song/lyric?id=%d&lv=1&tv=-1";
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36";
    private static final String REFERER = "https://music.163.com";
    private static final int MEMORY_CACHE_LIMIT = 64;
    private static final long MIN_REQUEST_INTERVAL_MS = 300L;
    private static final int SEARCH_LIMIT = 15;
    private static final int MIN_ACCEPT_SCORE = 40;
    private static final int MAX_LYRIC_ATTEMPTS = 4;
    private static final String[] VERSION_KEYWORDS = new String[]{"\u7ffb\u5531", "cover", "\u94a2\u7434\u7248", "\u5409\u4ed6\u7248", "\u7eaf\u97f3\u4e50", "\u4f34\u594f", "remix", "\u5973\u58f0\u7248", "\u7537\u58f0\u7248", "\u7ae5\u58f0\u7248", "\u7247\u6bb5", "\u526a\u8f91", "\u4e32\u70e7", "\u6e05\u5531", "\u5408\u5531\u7248", "\u7535\u97f3", "\u52a0\u901f\u7248", "\u6162\u901f\u7248", "\u73b0\u573a\u7248", "dj\u7248", "instrumental", "karaoke", "8d", "beat", "\u6539\u5927\u8c03", "\u6539\u7f16"};
    private final Map<String, LyricDocument> memoryCache = new ConcurrentHashMap<String, LyricDocument>();
    private final Map<String, Long> negativeCache = new ConcurrentHashMap<String, Long>();
    private final File cacheFolder;
    private volatile long lastRequestAt;

    public NeteaseLyricsClient() {
        File file = LyricParser.clientFolder != null ? LyricParser.clientFolder : new File(System.getProperty("user.home"), "EixClient");
        this.cacheFolder = new File(file, "lyrics");
        try {
            if (!this.cacheFolder.exists()) {
                this.cacheFolder.mkdirs();
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    public LyricDocument a(String string, String string2, long l2) {
        String string3 = LrcParser.b(string);
        if (string3.isEmpty()) {
            return LyricDocument.EMPTY;
        }
        String string4 = string2 == null ? "" : string2.trim();
        String string5 = (string3 + "\u0000" + string4).toLowerCase(Locale.ROOT);
        LyricDocument c2 = this.memoryCache.get(string5);
        if (c2 != null) {
            return c2;
        }
        Long l3 = this.negativeCache.get(string5);
        if (l3 != null && System.currentTimeMillis() - l3 < 120000L) {
            return LyricDocument.EMPTY;
        }
        LyricDocument c3 = this.d(string5);
        if (c3 != null) {
            this.a(string5, c3);
            return c3;
        }
        try {
            LyricDocument c4 = this.b(string3, string4, l2);
            if (c4.d()) {
                this.negativeCache.put(string5, System.currentTimeMillis());
                return LyricDocument.EMPTY;
            }
            this.a(string5, c4);
            this.b(string5, c4);
            return c4;
        }
        catch (Throwable throwable) {
            LOGGER.debug("\u83b7\u53d6\u6b4c\u8bcd\u5931\u8d25: {} - {}", (Object)string3, (Object)string4, (Object)throwable);
            this.negativeCache.put(string5, System.currentTimeMillis());
            return LyricDocument.EMPTY;
        }
    }

    private void a(String string, LyricDocument c2) {
        if (this.memoryCache.size() > 64) {
            this.memoryCache.clear();
        }
        this.memoryCache.put(string, c2);
    }

    public LyricDocument a(long l2, String string, String string2) {
        if (l2 <= 0L) {
            return LyricDocument.EMPTY;
        }
        String string3 = "id:" + l2;
        LyricDocument c2 = this.memoryCache.get(string3);
        if (c2 != null) {
            return c2;
        }
        LyricDocument c3 = this.d(string3);
        if (c3 != null) {
            this.a(string3, c3);
            return c3;
        }
        Long l3 = this.negativeCache.get(string3);
        if (l3 != null && System.currentTimeMillis() - l3 < 120000L) {
            return LyricDocument.EMPTY;
        }
        try {
            SongCandidate songCandidate = new SongCandidate(l2, string == null ? "" : string, string2 == null ? "" : string2, "", -1L);
            LyricDocument c4 = this.a(songCandidate);
            if (c4.d()) {
                this.negativeCache.put(string3, System.currentTimeMillis());
                return LyricDocument.EMPTY;
            }
            this.a(string3, c4);
            this.b(string3, c4);
            return c4;
        }
        catch (Throwable throwable) {
            LOGGER.debug("\u6309 ID \u83b7\u53d6\u6b4c\u8bcd\u5931\u8d25: {}", (Object)l2, (Object)throwable);
            this.negativeCache.put(string3, System.currentTimeMillis());
            return LyricDocument.EMPTY;
        }
    }

    private LyricDocument b(String string, String string2, long l2) throws Exception {
        LyricDocument c2;
        SongCandidate songCandidate3;
        Object object;
        boolean bl = !string2.isBlank();
        LinkedHashMap<Long, SongCandidate> linkedHashMap = new LinkedHashMap<Long, SongCandidate>();
        if (bl) {
            LyricDocument c3;
            NeteaseLyricsClient.a(linkedHashMap, this.a(string + " " + string2, 1, 15));
            object = NeteaseLyricsClient.a(linkedHashMap, string, string2, l2);
            if (NeteaseLyricsClient.a((SongCandidate)object, string2) && !(c3 = this.a((SongCandidate)object)).d()) {
                return c3;
            }
        }
        NeteaseLyricsClient.a(linkedHashMap, this.a(string, 1, 15));
        if (bl && !NeteaseLyricsClient.a(NeteaseLyricsClient.a(linkedHashMap, string, string2, l2), string2)) {
            NeteaseLyricsClient.a(linkedHashMap, this.a(string2));
        }
        object = new ArrayList(linkedHashMap.values());
        object.sort((songCandidate, songCandidate2) -> Integer.compare(NeteaseLyricsClient.a(songCandidate2, string, string2, l2), NeteaseLyricsClient.a(songCandidate, string, string2, l2)));
        for (int i2 = 0; i2 < Math.min(4, object.size()) && NeteaseLyricsClient.a(songCandidate3 = (SongCandidate)object.get(i2), string, string2, l2) >= 40; ++i2) {
            LyricDocument c4 = this.a(songCandidate3);
            if (c4.d()) continue;
            return c4;
        }
        if (!object.isEmpty() && !(c2 = this.a((SongCandidate)object.get(0))).d()) {
            return c2;
        }
        return LyricDocument.EMPTY;
    }

    private static void a(Map<Long, SongCandidate> map, List<SongCandidate> list) {
        for (SongCandidate songCandidate : list) {
            map.putIfAbsent(songCandidate.id, songCandidate);
        }
    }

    private static SongCandidate a(Map<Long, SongCandidate> map, String string, String string2, long l2) {
        SongCandidate songCandidate = null;
        int n2 = Integer.MIN_VALUE;
        for (SongCandidate songCandidate2 : map.values()) {
            int n3 = NeteaseLyricsClient.a(songCandidate2, string, string2, l2);
            if (n3 <= n2) continue;
            n2 = n3;
            songCandidate = songCandidate2;
        }
        return songCandidate;
    }

    private static boolean a(SongCandidate songCandidate, String string) {
        if (songCandidate == null || string == null || string.isBlank()) {
            return false;
        }
        String string2 = songCandidate.artist.toLowerCase(Locale.ROOT);
        for (String string3 : string.toLowerCase(Locale.ROOT).split("[/\u3001,\uff0c&;\uff1b]")) {
            String string4 = string3.trim();
            if (string4.isEmpty() || !string2.contains(string4)) continue;
            return true;
        }
        return false;
    }

    private List<SongCandidate> a(String string, int n2, int n3) throws Exception {
        if (string == null || string.isBlank()) {
            return List.of();
        }
        try {
            List<SongCandidate> list = this.a(string, n3);
            if (!list.isEmpty()) {
                return list;
            }
        }
        catch (Throwable throwable) {
            LOGGER.debug("\u89e3\u6790\u63a5\u53e3\u641c\u7d22\u5931\u8d25\uff0c\u56de\u9000\u516c\u6709\u63a5\u53e3: {}", (Object)throwable.getMessage());
        }
        return this.b(string, n2, n3);
    }

    private List<SongCandidate> a(String string, int n2) throws Exception {
        List<i> list = g.a().b().a(string, Math.max(1, Math.min(n2, 50)), 0);
        ArrayList<SongCandidate> arrayList = new ArrayList<SongCandidate>(list.size());
        for (i i2 : list) {
            if (i2.a() <= 0L || i2.b().isBlank()) continue;
            arrayList.add(new SongCandidate(i2.a(), i2.b(), i2.c(), i2.d(), i2.f()));
        }
        return arrayList;
    }

    private List<SongCandidate> b(String string, int n2, int n3) throws Exception {
        String string2 = String.format(SEARCH_URL, URLEncoder.encode(string, StandardCharsets.UTF_8), n2, n3);
        String string3 = this.b(string2);
        if (string3 == null || string3.isBlank()) {
            return List.of();
        }
        JsonObject jsonObject = JsonParser.parseString((String)string3).getAsJsonObject();
        if (!jsonObject.has("result") || jsonObject.get("result").isJsonNull()) {
            return List.of();
        }
        JsonObject jsonObject2 = jsonObject.getAsJsonObject("result");
        if (!jsonObject2.has("songs") || !jsonObject2.get("songs").isJsonArray()) {
            return List.of();
        }
        return NeteaseLyricsClient.a(jsonObject2.getAsJsonArray("songs"));
    }

    private List<SongCandidate> a(String string) throws Exception {
        JsonElement jsonElement2;
        Object object;
        try {
            object = this.a(string, 50);
            if (!object.isEmpty()) {
                return object;
            }
        }
        catch (Throwable throwable) {
            LOGGER.debug("\u89e3\u6790\u63a5\u53e3\u6309\u6b4c\u624b\u641c\u7d22\u5931\u8d25\uff0c\u56de\u9000\u516c\u6709\u63a5\u53e3: {}", (Object)throwable.getMessage());
        }
        object = String.format(SEARCH_URL, URLEncoder.encode(string, StandardCharsets.UTF_8), 100, 3);
        String string2 = this.b((String)object);
        if (string2 == null || string2.isBlank()) {
            return List.of();
        }
        JsonObject jsonObject = JsonParser.parseString((String)string2).getAsJsonObject();
        if (!jsonObject.has("result") || jsonObject.get("result").isJsonNull()) {
            return List.of();
        }
        JsonObject jsonObject2 = jsonObject.getAsJsonObject("result");
        if (!jsonObject2.has("artists") || !jsonObject2.get("artists").isJsonArray()) {
            return List.of();
        }
        long l2 = -1L;
        String string3 = string.toLowerCase(Locale.ROOT).trim();
        for (JsonElement jsonElement2 : jsonObject2.getAsJsonArray("artists")) {
            JsonObject jsonObject3 = jsonElement2.getAsJsonObject();
            String string4 = jsonObject3.has("name") ? jsonObject3.get("name").getAsString() : "";
            if (!string4.toLowerCase(Locale.ROOT).trim().equals(string3)) continue;
            l2 = jsonObject3.get("id").getAsLong();
            break;
        }
        if (l2 < 0L) {
            return List.of();
        }
        String string5 = this.b(String.format(ARTIST_SONGS_URL, l2));
        if (string5 == null || string5.isBlank()) {
            return List.of();
        }
        jsonElement2 = JsonParser.parseString((String)string5).getAsJsonObject();
        if (!jsonElement2.has("songs") || !jsonElement2.get("songs").isJsonArray()) {
            return List.of();
        }
        return NeteaseLyricsClient.a(jsonElement2.getAsJsonArray("songs"));
    }

    private static List<SongCandidate> a(JsonArray jsonArray) {
        ArrayList<SongCandidate> arrayList = new ArrayList<SongCandidate>(jsonArray.size());
        for (JsonElement jsonElement : jsonArray) {
            try {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                long l2 = jsonObject.get("id").getAsLong();
                String string = jsonObject.has("name") ? jsonObject.get("name").getAsString() : "";
                long l3 = jsonObject.has("duration") ? jsonObject.get("duration").getAsLong() : -1L;
                StringBuilder stringBuilder = new StringBuilder();
                if (jsonObject.has("artists") && jsonObject.get("artists").isJsonArray()) {
                    for (JsonObject jsonObject2 : jsonObject.getAsJsonArray("artists")) {
                        JsonObject jsonObject3 = jsonObject2.getAsJsonObject();
                        if (stringBuilder.length() > 0) {
                            stringBuilder.append('/');
                        }
                        stringBuilder.append(jsonObject3.has("name") ? jsonObject3.get("name").getAsString() : "");
                    }
                }
                Object object = "";
                if (jsonObject.has("album") && jsonObject.get("album").isJsonObject()) {
                    JsonObject jsonObject2;
                    jsonObject2 = jsonObject.getAsJsonObject("album");
                    Object object2 = object = jsonObject2.has("name") ? jsonObject2.get("name").getAsString() : "";
                }
                if (string.isEmpty()) continue;
                arrayList.add(new SongCandidate(l2, string, stringBuilder.toString(), (String)object, l3));
            }
            catch (Throwable throwable) {}
        }
        return arrayList;
    }

    private LyricDocument a(SongCandidate songCandidate) throws Exception {
        LyricDocument c2;
        Object object;
        try {
            object = g.a().d(songCandidate.id);
            if (object != null && !((f)object).d() && !(c2 = LrcParser.a(((f)object).a(), ((f)object).b(), songCandidate.name, songCandidate.artist)).d()) {
                return c2;
            }
        }
        catch (Throwable throwable) {
            LOGGER.debug("\u89e3\u6790\u63a5\u53e3\u53d6\u6b4c\u8bcd\u5931\u8d25\uff0c\u56de\u9000\u516c\u6709\u63a5\u53e3: {}", (Object)throwable.getMessage());
        }
        object = this.b(String.format(LYRIC_URL, songCandidate.id));
        if (object == null || ((String)object).isBlank()) {
            return LyricDocument.EMPTY;
        }
        c2 = JsonParser.parseString((String)object).getAsJsonObject();
        String string = NeteaseLyricsClient.a((JsonObject)c2, "lrc");
        String string2 = NeteaseLyricsClient.a((JsonObject)c2, "tlyric");
        if (string.isBlank()) {
            string = NeteaseLyricsClient.a((JsonObject)c2, "klyric");
        }
        if (string.isBlank()) {
            return LyricDocument.EMPTY;
        }
        return LrcParser.a(string, string2, songCandidate.name, songCandidate.artist);
    }

    private static String a(JsonObject jsonObject, String string) {
        if (!jsonObject.has(string) || !jsonObject.get(string).isJsonObject()) {
            return "";
        }
        JsonObject jsonObject2 = jsonObject.getAsJsonObject(string);
        if (!jsonObject2.has("lyric") || jsonObject2.get("lyric").isJsonNull()) {
            return "";
        }
        return jsonObject2.get("lyric").getAsString();
    }

    private static int a(SongCandidate songCandidate, String string, String string2, long l2) {
        boolean bl;
        int n2 = 0;
        String string3 = songCandidate.name.toLowerCase(Locale.ROOT).trim();
        String string4 = string.toLowerCase(Locale.ROOT).trim();
        String string5 = string3.replaceAll("[\uff08(\u3010\\[][^\uff09)\u3011\\]]*[\uff09)\u3011\\]]", "").trim();
        n2 = string3.equals(string4) || string5.equals(string4) ? (n2 += 100) : (string5.contains(string4) || string4.contains(string5) ? (n2 += 55) : (string3.contains(string4) ? (n2 += 45) : (n2 += 10)));
        boolean bl2 = bl = string2 != null && !string2.isBlank();
        if (bl) {
            n2 = NeteaseLyricsClient.a(songCandidate, string2) ? (n2 += 50) : (n2 -= 30);
        }
        int n3 = 0;
        for (String string6 : VERSION_KEYWORDS) {
            if (!string3.contains(string6)) continue;
            n3 += 35;
        }
        n2 -= Math.min(70, n3);
        if (l2 > 0L && songCandidate.duration > 0L) {
            long l3 = Math.abs(l2 - songCandidate.duration);
            if (l3 <= 3000L) {
                n2 += 35;
            } else if (l3 <= 8000L) {
                n2 += 18;
            } else if (l3 > 30000L) {
                n2 -= 25;
            }
        }
        return n2;
    }

    private String b(String string) throws Exception {
        this.b();
        HttpURLConnection httpURLConnection = null;
        try {
            String string2;
            block12: {
                httpURLConnection = (HttpURLConnection)new URL(string).openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setConnectTimeout(6000);
                httpURLConnection.setReadTimeout(9000);
                httpURLConnection.setRequestProperty("User-Agent", USER_AGENT);
                httpURLConnection.setRequestProperty("Referer", REFERER);
                httpURLConnection.setRequestProperty("Accept", "application/json, text/plain, */*");
                httpURLConnection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
                httpURLConnection.setInstanceFollowRedirects(true);
                int n2 = httpURLConnection.getResponseCode();
                if (n2 != 200) {
                    LOGGER.debug("\u7f51\u6613\u4e91\u63a5\u53e3\u8fd4\u56de {}", (Object)n2);
                    String string3 = null;
                    return string3;
                }
                InputStream inputStream = httpURLConnection.getInputStream();
                try {
                    string2 = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                    if (inputStream == null) break block12;
                }
                catch (Throwable throwable) {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                inputStream.close();
            }
            return string2;
        }
        finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
    }

    private synchronized void b() {
        long l2 = System.currentTimeMillis();
        long l3 = 300L - (l2 - this.lastRequestAt);
        if (l3 > 0L) {
            try {
                Thread.sleep(l3);
            }
            catch (InterruptedException interruptedException) {
                Thread.currentThread().interrupt();
            }
        }
        this.lastRequestAt = System.currentTimeMillis();
    }

    private File c(String string) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] byArray = messageDigest.digest(string.getBytes(StandardCharsets.UTF_8));
            StringBuilder stringBuilder = new StringBuilder(byArray.length * 2);
            for (byte by : byArray) {
                stringBuilder.append(Character.forDigit(by >> 4 & 0xF, 16));
                stringBuilder.append(Character.forDigit(by & 0xF, 16));
            }
            return new File(this.cacheFolder, String.valueOf(stringBuilder) + ".json");
        }
        catch (Throwable throwable) {
            return new File(this.cacheFolder, Integer.toHexString(string.hashCode()) + ".json");
        }
    }

    private LyricDocument d(String string) {
        File file = this.c(string);
        if (!file.isFile()) {
            return null;
        }
        try {
            String string2;
            String string3 = Files.readString(file.toPath(), StandardCharsets.UTF_8);
            JsonObject jsonObject = JsonParser.parseString((String)string3).getAsJsonObject();
            String string4 = jsonObject.has("lrc") ? jsonObject.get("lrc").getAsString() : "";
            String string5 = jsonObject.has("tlyric") ? jsonObject.get("tlyric").getAsString() : "";
            String string6 = jsonObject.has("name") ? jsonObject.get("name").getAsString() : "";
            String string7 = string2 = jsonObject.has("artist") ? jsonObject.get("artist").getAsString() : "";
            if (string4.isBlank()) {
                return null;
            }
            LyricDocument c2 = LrcParser.a(string4, string5, string6, string2);
            return c2.d() ? null : c2;
        }
        catch (Throwable throwable) {
            return null;
        }
    }

    private void b(String string, LyricDocument c2) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            StringBuilder stringBuilder2 = new StringBuilder();
            for (LyricLine object2 : c2.a()) {
                long l2 = object2.a();
                String string2 = String.format(Locale.ROOT, "[%02d:%02d.%03d]", l2 / 60000L, l2 / 1000L % 60L, l2 % 1000L);
                stringBuilder.append(string2).append(object2.b()).append('\n');
                if (!object2.d()) continue;
                stringBuilder2.append(string2).append(object2.c()).append('\n');
            }
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("lrc", stringBuilder.toString());
            jsonObject.addProperty("tlyric", stringBuilder2.toString());
            jsonObject.addProperty("name", c2.b());
            jsonObject.addProperty("artist", c2.c());
            jsonObject.addProperty("savedAt", (Number)System.currentTimeMillis());
            try (OutputStream outputStream = Files.newOutputStream(this.c(string).toPath(), new OpenOption[0]);){
                outputStream.write(jsonObject.toString().getBytes(StandardCharsets.UTF_8));
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    public void a() {
        this.memoryCache.clear();
        this.negativeCache.clear();
    }

    private record SongCandidate(long id, String name, String artist, String album, long duration) {
    }
}

