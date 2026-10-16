/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.music.a;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.heypixel.heypixelmod.obsoverlay.music.a.MusicApiAddressSelector;
import com.heypixel.heypixelmod.obsoverlay.music.a.MusicApiRateLimitException;
import com.heypixel.heypixelmod.obsoverlay.music.a.TimedLyricsBundle;
import com.heypixel.heypixelmod.obsoverlay.music.a.MusicCollectionResult;
import com.heypixel.heypixelmod.obsoverlay.music.a.PlaybackTimelineModel;
import com.heypixel.heypixelmod.obsoverlay.music.a.MusicAudioDownloadInfo;
import com.heypixel.heypixelmod.obsoverlay.music.a.MusicPlaylistMetadata;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class MusicApiHttpClient {
    private static final Logger LOGGER = LogManager.getLogger(MusicApiHttpClient.class);
    public static final String DEFAULT_BASE_URL = "https://nextmusic.toubiec.cn";
    public static final String DEFAULT_LEVEL = "lossless";
    public static final String[][] LEVELS = new String[][]{{"standard", "\u6807\u51c6\u97f3\u8d28"}, {"exhigh", "\u6781\u9ad8\u97f3\u8d28"}, {"lossless", "\u65e0\u635f\u97f3\u8d28"}, {"hires", "Hi-Res\u97f3\u8d28"}, {"jyeffect", "\u9ad8\u6e05\u73af\u7ed5\u58f0"}, {"sky", "\u6c89\u6d78\u73af\u7ed5\u58f0"}, {"dolby", "\u675c\u6bd4\u5168\u666f\u58f0"}, {"vivid", "\u81fb\u97f3\u5168\u666f\u58f0"}, {"jymaster", "\u8d85\u6e05\u6bcd\u5e26"}};
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36";
    private static final int CONNECT_TIMEOUT_MS = 8000;
    private static final int READ_TIMEOUT_MS = 15000;
    private static final int MAX_IP_RETRIES = 5;
    private final MusicApiAddressSelector ipPool = new MusicApiAddressSelector();
    private volatile String baseUrl = "https://nextmusic.toubiec.cn";

    public MusicApiAddressSelector a() {
        return this.ipPool;
    }

    public String b() {
        return this.baseUrl;
    }

    public void a(String string) {
        if (string != null && !string.isBlank()) {
            String string2 = string.trim();
            while (string2.endsWith("/")) {
                string2 = string2.substring(0, string2.length() - 1);
            }
            this.baseUrl = string2;
        }
    }

    public JsonObject a(String string, JsonObject jsonObject) throws IOException {
        IOException iOException = null;
        for (int i2 = 0; i2 < 5; ++i2) {
            JsonObject jsonObject2;
            String string2 = this.ipPool.e();
            JsonObject jsonObject3 = jsonObject == null ? new JsonObject() : jsonObject.deepCopy();
            jsonObject3.addProperty("ip", string2);
            jsonObject3.addProperty("timestamp", (Number)System.currentTimeMillis());
            try {
                jsonObject2 = this.c(string, jsonObject3);
            }
            catch (IOException iOException2) {
                iOException = iOException2;
                LOGGER.debug("\u63a5\u53e3 {} \u8bf7\u6c42\u5931\u8d25\uff0c\u6362 IP \u91cd\u8bd5", (Object)string, (Object)iOException2);
                continue;
            }
            int n2 = MusicApiHttpClient.a(jsonObject2, "code", -1);
            if (n2 == 200) {
                return jsonObject2;
            }
            String string3 = MusicApiHttpClient.a(jsonObject2, "message", "");
            if (n2 == 429) {
                this.ipPool.c(string2);
                iOException = new MusicApiRateLimitException(429, string3.isBlank() ? "\u8bf7\u6c42\u8fc7\u4e8e\u9891\u7e41" : string3);
                LOGGER.debug("\u63a5\u53e3 {} \u88ab\u9650\u6d41\uff08ip={}\uff09\uff0c\u6362 IP \u91cd\u8bd5\uff1a{}", (Object)string, (Object)string2, (Object)string3);
                continue;
            }
            throw new MusicApiRateLimitException(n2, (String)(string3.isBlank() ? "\u63a5\u53e3\u8fd4\u56de code=" + n2 : string3));
        }
        throw iOException != null ? iOException : new IOException("\u63a5\u53e3 " + string + " \u91cd\u8bd5\u6b21\u6570\u7528\u5c3d");
    }

    public JsonElement b(String string, JsonObject jsonObject) throws IOException {
        JsonObject jsonObject2 = this.a(string, jsonObject);
        JsonElement jsonElement = jsonObject2.get("data");
        return jsonElement == null || jsonElement.isJsonNull() ? null : jsonElement;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private JsonObject c(String string, JsonObject jsonObject) throws IOException {
        HttpURLConnection httpURLConnection = null;
        try {
            String string2;
            InputStream inputStream;
            URL uRL = new URL(this.baseUrl + string);
            httpURLConnection = (HttpURLConnection)uRL.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setConnectTimeout(8000);
            httpURLConnection.setReadTimeout(15000);
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setRequestProperty("Accept", "application/json, text/plain, */*");
            httpURLConnection.setRequestProperty("User-Agent", USER_AGENT);
            httpURLConnection.setRequestProperty("Origin", this.baseUrl);
            httpURLConnection.setRequestProperty("Referer", this.baseUrl + "/");
            byte[] byArray = jsonObject.toString().getBytes(StandardCharsets.UTF_8);
            httpURLConnection.setFixedLengthStreamingMode(byArray.length);
            try (OutputStream outputStream = httpURLConnection.getOutputStream();){
                outputStream.write(byArray);
            }
            int n2 = httpURLConnection.getResponseCode();
            InputStream inputStream2 = inputStream = n2 >= 400 ? httpURLConnection.getErrorStream() : httpURLConnection.getInputStream();
            if (inputStream == null) {
                inputStream = httpURLConnection.getErrorStream();
            }
            String string3 = string2 = inputStream == null ? "" : new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            if (string2.isBlank()) {
                throw new IOException("\u63a5\u53e3 " + string + " \u8fd4\u56de\u7a7a\u54cd\u5e94\uff08HTTP " + n2 + "\uff09");
            }
            JsonElement jsonElement = JsonParser.parseString((String)string2);
            if (!jsonElement.isJsonObject()) {
                throw new IOException("\u63a5\u53e3 " + string + " \u8fd4\u56de\u4e86\u975e JSON \u5185\u5bb9");
            }
            JsonObject jsonObject2 = jsonElement.getAsJsonObject();
            return jsonObject2;
        }
        finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
    }

    public String c() throws IOException {
        JsonObject jsonObject = this.a("/api/ip", new JsonObject());
        JsonElement jsonElement = jsonObject.get("data");
        if (jsonElement == null || !jsonElement.isJsonObject()) {
            return "";
        }
        String string = MusicApiHttpClient.a(jsonElement.getAsJsonObject(), "ip", "");
        if (!string.isBlank()) {
            this.ipPool.a(string);
        }
        return string;
    }

    public void d() {
        if (this.ipPool.a() != null) {
            return;
        }
        try {
            this.c();
        }
        catch (Throwable throwable) {
            LOGGER.debug("\u83b7\u53d6\u63a5\u53e3\u5ba2\u6237\u7aef IP \u5931\u8d25", throwable);
        }
    }

    public List<PlaybackTimelineModel> a(String string, int n2, int n3) throws IOException {
        if (string == null || string.isBlank()) {
            return List.of();
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("keyword", string.trim());
        jsonObject.addProperty("type", (Number)1);
        jsonObject.addProperty("limit", (Number)Math.max(1, Math.min(n2, 100)));
        jsonObject.addProperty("offset", (Number)Math.max(0, n3));
        JsonElement jsonElement = this.b("/api/search", jsonObject);
        if (jsonElement == null || !jsonElement.isJsonObject()) {
            return List.of();
        }
        JsonObject jsonObject2 = jsonElement.getAsJsonObject();
        JsonElement jsonElement2 = jsonObject2.get("songs");
        if (jsonElement2 == null) {
            jsonElement2 = jsonObject2.get("result");
        }
        return MusicApiHttpClient.a(jsonElement2);
    }

    public PlaybackTimelineModel a(long l2) throws IOException {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", String.valueOf(l2));
        JsonElement jsonElement = this.b("/api/getSongInfo", jsonObject);
        if (jsonElement == null || !jsonElement.isJsonObject()) {
            return null;
        }
        return MusicApiHttpClient.a(jsonElement.getAsJsonObject());
    }

    public MusicAudioDownloadInfo a(long l2, String string, String string2) throws IOException {
        JsonElement jsonElement;
        String string3 = string == null || string.isBlank() ? DEFAULT_LEVEL : string.trim();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", String.valueOf(l2));
        jsonObject.addProperty("level", string3);
        if ("sky".equalsIgnoreCase(string3) && string2 != null && !string2.isBlank()) {
            jsonObject.addProperty("immerseType", string2.trim());
        }
        if ((jsonElement = this.b("/api/getSongUrl", jsonObject)) == null || !jsonElement.isJsonObject()) {
            throw new IOException("\u63a5\u53e3\u672a\u8fd4\u56de\u64ad\u653e\u5730\u5740");
        }
        JsonObject jsonObject2 = jsonElement.getAsJsonObject();
        long l3 = MusicApiHttpClient.a(jsonObject2, "id", l2);
        String string4 = MusicApiHttpClient.a(jsonObject2, "url", "");
        String string5 = "";
        JsonElement jsonElement2 = jsonObject2.get("cookie");
        if (jsonElement2 != null && jsonElement2.isJsonObject()) {
            string5 = MusicApiHttpClient.a(jsonElement2.getAsJsonObject(), "label", "");
        }
        return new MusicAudioDownloadInfo(l3, string4, MusicApiHttpClient.a(jsonObject2, "level", string3), MusicApiHttpClient.a(jsonObject2, "br", 0), MusicApiHttpClient.a(jsonObject2, "size", 0L), MusicApiHttpClient.a(jsonObject2, "md5", ""), MusicApiHttpClient.a(jsonObject2, "channelLayout", ""), string5);
    }

    public TimedLyricsBundle b(long l2) throws IOException {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", String.valueOf(l2));
        JsonElement jsonElement = this.b("/api/getSongLyric", jsonObject);
        if (jsonElement == null || !jsonElement.isJsonObject()) {
            return TimedLyricsBundle.EMPTY;
        }
        JsonObject jsonObject2 = jsonElement.getAsJsonObject();
        return new TimedLyricsBundle(MusicApiHttpClient.a(jsonObject2, "lrc", ""), MusicApiHttpClient.a(jsonObject2, "tlyric", ""), MusicApiHttpClient.a(jsonObject2, "yrc", ""));
    }

    public MusicCollectionResult c(long l2) throws IOException {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", String.valueOf(l2));
        JsonElement jsonElement = this.b("/api/playlist_trackall", jsonObject);
        if (jsonElement == null) {
            throw new IOException("\u672a\u627e\u5230\u8be5\u6b4c\u5355");
        }
        if (jsonElement.isJsonArray()) {
            return new MusicCollectionResult(com.heypixel.heypixelmod.obsoverlay.music.a.MusicCollectionResult.CollectionKind.PLAYLIST, l2, "", "", "", "", jsonElement.getAsJsonArray().size(), MusicApiHttpClient.a(jsonElement));
        }
        JsonObject jsonObject2 = jsonElement.getAsJsonObject();
        String string = "";
        JsonElement jsonElement2 = jsonObject2.get("creator");
        if (jsonElement2 != null && jsonElement2.isJsonObject()) {
            string = MusicApiHttpClient.a(jsonElement2.getAsJsonObject(), "name", "");
        }
        List<PlaybackTimelineModel> list = MusicApiHttpClient.a(MusicApiHttpClient.a(jsonObject2, "songs", "tracks", "list"));
        return new MusicCollectionResult(com.heypixel.heypixelmod.obsoverlay.music.a.MusicCollectionResult.CollectionKind.PLAYLIST, MusicApiHttpClient.a(jsonObject2, "id", l2), MusicApiHttpClient.a(jsonObject2, "name", ""), MusicApiHttpClient.b(jsonObject2, "coverImage", "coverImgUrl", "cover", "picUrl"), string, MusicApiHttpClient.a(jsonObject2, "description", ""), MusicApiHttpClient.a(jsonObject2, "songCount", list.size()), list);
    }

    public MusicCollectionResult d(long l2) throws IOException {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", String.valueOf(l2));
        JsonElement jsonElement = this.b("/api/getAlbum", jsonObject);
        if (jsonElement == null) {
            throw new IOException("\u672a\u627e\u5230\u8be5\u4e13\u8f91");
        }
        if (jsonElement.isJsonArray()) {
            return new MusicCollectionResult(com.heypixel.heypixelmod.obsoverlay.music.a.MusicCollectionResult.CollectionKind.ALBUM, l2, "", "", "", "", jsonElement.getAsJsonArray().size(), MusicApiHttpClient.a(jsonElement));
        }
        JsonObject jsonObject2 = jsonElement.getAsJsonObject();
        String string = "";
        JsonElement jsonElement2 = jsonObject2.get("artist");
        if (jsonElement2 != null && jsonElement2.isJsonObject()) {
            string = MusicApiHttpClient.a(jsonElement2.getAsJsonObject(), "name", "");
        }
        List<PlaybackTimelineModel> list = MusicApiHttpClient.a(MusicApiHttpClient.a(jsonObject2, "songs", "tracks", "list"));
        return new MusicCollectionResult(com.heypixel.heypixelmod.obsoverlay.music.a.MusicCollectionResult.CollectionKind.ALBUM, MusicApiHttpClient.a(jsonObject2, "id", l2), MusicApiHttpClient.a(jsonObject2, "name", ""), MusicApiHttpClient.b(jsonObject2, "picUrl", "coverImage", "coverImgUrl", "cover"), string, MusicApiHttpClient.a(jsonObject2, "description", ""), list.size(), list);
    }

    public List<MusicPlaylistMetadata> e() throws IOException {
        JsonElement jsonElement = this.b("/api/toplist", new JsonObject());
        ArrayList<MusicPlaylistMetadata> arrayList = new ArrayList<MusicPlaylistMetadata>();
        if (jsonElement == null || !jsonElement.isJsonArray()) {
            return arrayList;
        }
        for (JsonElement jsonElement2 : jsonElement.getAsJsonArray()) {
            if (!jsonElement2.isJsonObject()) continue;
            JsonObject jsonObject = jsonElement2.getAsJsonObject();
            arrayList.add(new MusicPlaylistMetadata(MusicApiHttpClient.a(jsonObject, "id", -1L), MusicApiHttpClient.a(jsonObject, "name", ""), MusicApiHttpClient.b(jsonObject, "coverImgUrl", "cover", "picUrl", "coverImage"), MusicApiHttpClient.a(jsonObject, "updateFrequency", "")));
        }
        return arrayList;
    }

    public InputStream a(String string, long l2) throws IOException {
        int n2;
        HttpURLConnection httpURLConnection = (HttpURLConnection)new URL(string).openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setConnectTimeout(8000);
        httpURLConnection.setReadTimeout(15000);
        httpURLConnection.setRequestProperty("User-Agent", USER_AGENT);
        httpURLConnection.setInstanceFollowRedirects(true);
        if (l2 > 0L) {
            httpURLConnection.setRequestProperty("Range", "bytes=" + l2 + "-");
        }
        if ((n2 = httpURLConnection.getResponseCode()) != 200 && n2 != 206) {
            httpURLConnection.disconnect();
            throw new IOException("\u97f3\u9891\u76f4\u94fe\u8fd4\u56de HTTP " + n2);
        }
        return httpURLConnection.getInputStream();
    }

    private static List<PlaybackTimelineModel> a(JsonElement jsonElement) {
        JsonObject jsonObject;
        ArrayList<PlaybackTimelineModel> arrayList = new ArrayList<PlaybackTimelineModel>();
        if (jsonElement == null || jsonElement.isJsonNull()) {
            return arrayList;
        }
        if (jsonElement.isJsonObject() && (jsonElement = MusicApiHttpClient.a(jsonObject = jsonElement.getAsJsonObject(), "songs", "tracks", "list", "data")) == null) {
            return arrayList;
        }
        if (!jsonElement.isJsonArray()) {
            return arrayList;
        }
        jsonObject = jsonElement.getAsJsonArray();
        arrayList.ensureCapacity(jsonObject.size());
        for (JsonElement jsonElement2 : jsonObject) {
            PlaybackTimelineModel i2;
            if (!jsonElement2.isJsonObject() || (i2 = MusicApiHttpClient.a(jsonElement2.getAsJsonObject())) == null || i2.a() <= 0L) continue;
            arrayList.add(i2);
        }
        return arrayList;
    }

    private static PlaybackTimelineModel a(JsonObject jsonObject) {
        Object object;
        String string;
        long l2 = MusicApiHttpClient.a(jsonObject, "id", -1L);
        if (l2 <= 0L) {
            return null;
        }
        String string2 = MusicApiHttpClient.a(jsonObject, "singer", "");
        if (string2.isBlank() && (string = MusicApiHttpClient.a(jsonObject, new String[]{"artists", "ar"})) != null && string.isJsonArray()) {
            object = new StringBuilder();
            for (JsonElement jsonElement : string.getAsJsonArray()) {
                String string3;
                if (!jsonElement.isJsonObject() || (string3 = MusicApiHttpClient.a(jsonElement.getAsJsonObject(), "name", "")).isBlank()) continue;
                if (((StringBuilder)object).length() > 0) {
                    ((StringBuilder)object).append('/');
                }
                ((StringBuilder)object).append(string3);
            }
            string2 = ((StringBuilder)object).toString();
        }
        if ((string = MusicApiHttpClient.a(jsonObject, "album", "")).isBlank() && (object = jsonObject.get("al")) != null && object.isJsonObject()) {
            string = MusicApiHttpClient.a(object.getAsJsonObject(), "name", "");
        }
        object = MusicApiHttpClient.b(jsonObject, "picimg", "picUrl", "cover", "coverImgUrl", "pic");
        long l3 = PlaybackTimelineModel.a(MusicApiHttpClient.a(jsonObject, "duration", ""));
        if (l3 <= 0L) {
            l3 = MusicApiHttpClient.a(jsonObject, "dt", -1L);
        }
        return new PlaybackTimelineModel(l2, MusicApiHttpClient.a(jsonObject, "name", ""), string2, string, (String)object, l3, MusicApiHttpClient.a(jsonObject, "free", false), MusicApiHttpClient.a(jsonObject, "copyright", 0));
    }

    private static JsonElement a(JsonObject jsonObject, String ... stringArray) {
        for (String string : stringArray) {
            JsonElement jsonElement = jsonObject.get(string);
            if (jsonElement == null || jsonElement.isJsonNull()) continue;
            return jsonElement;
        }
        return null;
    }

    private static String b(JsonObject jsonObject, String ... stringArray) {
        for (String string : stringArray) {
            String string2;
            JsonElement jsonElement = jsonObject.get(string);
            if (jsonElement == null || !jsonElement.isJsonPrimitive() || (string2 = jsonElement.getAsString()) == null || string2.isBlank()) continue;
            return string2;
        }
        return "";
    }

    private static String a(JsonObject jsonObject, String string, String string2) {
        JsonElement jsonElement = jsonObject.get(string);
        if (jsonElement == null || jsonElement.isJsonNull() || !jsonElement.isJsonPrimitive()) {
            return string2;
        }
        try {
            String string3 = jsonElement.getAsString();
            return string3 == null ? string2 : string3;
        }
        catch (Throwable throwable) {
            return string2;
        }
    }

    private static int a(JsonObject jsonObject, String string, int n2) {
        JsonElement jsonElement = jsonObject.get(string);
        if (jsonElement == null || jsonElement.isJsonNull() || !jsonElement.isJsonPrimitive()) {
            return n2;
        }
        try {
            return jsonElement.getAsInt();
        }
        catch (Throwable throwable) {
            return n2;
        }
    }

    private static long a(JsonObject jsonObject, String string, long l2) {
        JsonElement jsonElement = jsonObject.get(string);
        if (jsonElement == null || jsonElement.isJsonNull() || !jsonElement.isJsonPrimitive()) {
            return l2;
        }
        try {
            return jsonElement.getAsLong();
        }
        catch (Throwable throwable) {
            return l2;
        }
    }

    private static boolean a(JsonObject jsonObject, String string, boolean bl) {
        JsonElement jsonElement = jsonObject.get(string);
        if (jsonElement == null || jsonElement.isJsonNull() || !jsonElement.isJsonPrimitive()) {
            return bl;
        }
        try {
            return jsonElement.getAsBoolean();
        }
        catch (Throwable throwable) {
            return bl;
        }
    }

    public static String[] b(String string) {
        if (string == null || string.isBlank()) {
            return new String[]{"", ""};
        }
        String string2 = string.trim();
        if (string2.matches("\\d{3,}")) {
            return new String[]{"song", string2};
        }
        String string3 = "";
        if (string2.contains("playlist")) {
            string3 = "playlist";
        } else if (string2.contains("album")) {
            string3 = "album";
        } else if (string2.contains("song")) {
            string3 = "song";
        }
        Matcher matcher = Pattern.compile("[?&#/]id=(\\d+)").matcher(string2);
        if (matcher.find()) {
            return new String[]{string3, matcher.group(1)};
        }
        matcher = Pattern.compile("/(song|album|playlist)/(\\d+)").matcher(string2);
        if (matcher.find()) {
            return new String[]{matcher.group(1).toLowerCase(Locale.ROOT), matcher.group(2)};
        }
        return new String[]{string3, ""};
    }
}

