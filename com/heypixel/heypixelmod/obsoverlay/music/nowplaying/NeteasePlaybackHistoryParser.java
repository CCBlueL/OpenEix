/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.music.nowplaying;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class NeteasePlaybackHistoryParser {
    private static final Logger LOGGER = LogManager.getLogger(QueueTrack.class);
    private static final String[] CANDIDATE_DIRS = new String[]{"Netease/CloudMusic/webdata/file", "Netease/CloudMusic/webdata", "Netease/CloudMusic"};
    private static final String[] QUEUE_FILE_NAMES = new String[]{"playingList", "playinglist", "PlayingList", "playing", "currentPlaying", "playlist", "playlist.json", "queue"};
    private static final long CACHE_MILLIS = 2500L;
    private static final int MAX_SNIFF_FILES = 40;
    private static final long MAX_FILE_BYTES = 0x800000L;
    private static final String DIR_OVERRIDE_PROPERTY = "eixclient.netease.webdata";
    private static volatile List<NeteasePlaybackHistoryParser> cachedQueue = List.of();
    private static volatile long lastReadAt;
    private static volatile boolean available;

    private NeteasePlaybackHistoryParser() {
    }

    public static boolean a() {
        return available && QueueTrack.d() != null;
    }

    private static File d() {
        String string = System.getProperty(DIR_OVERRIDE_PROPERTY);
        if (string != null && !string.isBlank()) {
            File file = new File(string);
            return file.isDirectory() ? file : null;
        }
        String string2 = System.getenv("LOCALAPPDATA");
        if (string2 == null || string2.isBlank()) {
            return null;
        }
        for (String string3 : CANDIDATE_DIRS) {
            File file = new File(string2, string3);
            if (!file.isDirectory()) continue;
            return file;
        }
        return null;
    }

    public static List<NeteasePlaybackHistoryParser> b() {
        long l2 = System.currentTimeMillis();
        if (l2 - lastReadAt < 2500L) {
            return cachedQueue;
        }
        lastReadAt = l2;
        File file = QueueTrack.d();
        if (file == null) {
            available = false;
            cachedQueue = List.of();
            return cachedQueue;
        }
        available = true;
        List<QueueTrack> list = QueueTrack.a(file);
        if (list.isEmpty()) {
            list = QueueTrack.b(file);
        }
        cachedQueue = list;
        return list;
    }

    private static List<NeteasePlaybackHistoryParser> a(File file) {
        for (String string : QUEUE_FILE_NAMES) {
            List<QueueTrack> list;
            File file2 = new File(file, string);
            if (!file2.isFile() || (list = QueueTrack.c(file2)).isEmpty()) continue;
            return list;
        }
        return List.of();
    }

    private static List<NeteasePlaybackHistoryParser> b(File file) {
        File[] fileArray = file.listFiles();
        if (fileArray == null) {
            return List.of();
        }
        ArrayList<File> arrayList = new ArrayList<File>();
        for (File list : fileArray) {
            if (!list.isFile() || list.length() <= 0L || list.length() > 0x800000L) continue;
            arrayList.add(list);
        }
        arrayList.sort(Comparator.comparingLong(File::lastModified).reversed());
        int n2 = 0;
        for (File file2 : arrayList) {
            if (n2++ >= 40) break;
            List<QueueTrack> list = QueueTrack.c(file2);
            if (list.isEmpty()) continue;
            LOGGER.debug("\u7f51\u6613\u4e91\u64ad\u653e\u961f\u5217\u547d\u4e2d\u6587\u4ef6: {}", (Object)file2.getName());
            return list;
        }
        return List.of();
    }

    private static List<NeteasePlaybackHistoryParser> c(File file) {
        try {
            Object object;
            String string = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
            if (string.isBlank()) {
                return List.of();
            }
            if (string.charAt(0) == '\ufeff') {
                string = string.substring(1);
            }
            if (!(string.contains("\"track\"") || string.contains("\"name\"") || string.contains("\"id\""))) {
                return List.of();
            }
            JsonElement jsonElement = JsonParser.parseString((String)string);
            if (!jsonElement.isJsonObject()) {
                return List.of();
            }
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonArray jsonArray = null;
            JsonObject jsonObject2 = new String[]{"list", "tracks", "songs", "data", "queue"};
            int n2 = ((String[])jsonObject2).length;
            for (int i2 = 0; i2 < n2; ++i2) {
                object = jsonObject2[i2];
                if (!jsonObject.has((String)object) || !jsonObject.get((String)object).isJsonArray()) continue;
                jsonArray = jsonObject.getAsJsonArray((String)object);
                break;
            }
            if (jsonArray == null && jsonObject.has("playlist") && jsonObject.get("playlist").isJsonObject() && (jsonObject2 = jsonObject.getAsJsonObject("playlist")).has("tracks") && jsonObject2.get("tracks").isJsonArray()) {
                jsonArray = jsonObject2.getAsJsonArray("tracks");
            }
            if (jsonArray == null) {
                return List.of();
            }
            jsonObject2 = new ArrayList(jsonArray.size());
            for (JsonElement jsonElement2 : jsonArray) {
                object = QueueTrack.a(jsonElement2);
                if (object == null) continue;
                jsonObject2.add(object);
            }
            return jsonObject2;
        }
        catch (Throwable throwable) {
            return List.of();
        }
    }

    private static NeteasePlaybackHistoryParser a(JsonElement jsonElement) {
        if (jsonElement == null || !jsonElement.isJsonObject()) {
            return null;
        }
        try {
            JsonObject jsonObject;
            JsonObject jsonObject2 = jsonElement.getAsJsonObject();
            JsonObject jsonObject3 = jsonObject = jsonObject2.has("track") && jsonObject2.get("track").isJsonObject() ? jsonObject2.getAsJsonObject("track") : jsonObject2;
            if (!jsonObject.has("id") || !jsonObject.get("id").isJsonPrimitive()) {
                return null;
            }
            long l2 = jsonObject.get("id").getAsLong();
            if (l2 <= 0L) {
                return null;
            }
            String string = jsonObject.has("name") && jsonObject.get("name").isJsonPrimitive() ? jsonObject.get("name").getAsString() : "";
            long l3 = -1L;
            for (String string2 : new String[]{"duration", "dt", "durationMs"}) {
                if (!jsonObject.has(string2) || !jsonObject.get(string2).isJsonPrimitive()) continue;
                try {
                    l3 = jsonObject.get(string2).getAsLong();
                    break;
                }
                catch (Throwable throwable) {
                    // empty catch block
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            for (String string3 : new String[]{"artists", "ar"}) {
                if (!jsonObject.has(string3) || !jsonObject.get(string3).isJsonArray()) continue;
                for (JsonElement jsonElement2 : jsonObject.getAsJsonArray(string3)) {
                    JsonObject jsonObject4;
                    if (!jsonElement2.isJsonObject() || !(jsonObject4 = jsonElement2.getAsJsonObject()).has("name") || !jsonObject4.get("name").isJsonPrimitive()) continue;
                    if (stringBuilder.length() > 0) {
                        stringBuilder.append('/');
                    }
                    stringBuilder.append(jsonObject4.get("name").getAsString());
                }
                if (stringBuilder.length() > 0) break;
            }
            return new QueueTrack(l2, string, stringBuilder.toString(), l3);
        }
        catch (Throwable throwable) {
            return null;
        }
    }

    public static NeteasePlaybackHistoryParser a(String string, String string2) {
        if (string == null || string.isBlank()) {
            return null;
        }
        List<QueueTrack> list = QueueTrack.b();
        if (list.isEmpty()) {
            return null;
        }
        String string3 = QueueTrack.a(string);
        String string4 = QueueTrack.a(string2);
        if (!string4.isEmpty()) {
            for (QueueTrack a2 : list) {
                if (!QueueTrack.a(a2.name).equals(string3) || !QueueTrack.a(a2.artist).contains(string4)) continue;
                return a2;
            }
        }
        for (QueueTrack a2 : list) {
            if (!QueueTrack.a(a2.name).equals(string3)) continue;
            return a2;
        }
        for (QueueTrack a2 : list) {
            String string5 = QueueTrack.a(a2.name);
            if (string5.isEmpty() || !string5.contains(string3) && !string3.contains(string5) || Math.abs(string5.length() - string3.length()) > 12) continue;
            return a2;
        }
        return null;
    }

    private static String a(String string) {
        if (string == null) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder(string.length());
        for (char c2 : string.toCharArray()) {
            if (c2 >= '\uff01' && c2 <= '\uff5e') {
                c2 = (char)(c2 - 65248);
            }
            if (Character.isWhitespace(c2) || c2 == '-' || c2 == '_' || c2 == '(' || c2 == ')' || c2 == '[' || c2 == ']' || c2 == '\uff08' || c2 == '\uff09' || c2 == '\u3010' || c2 == '\u3011' || c2 == '\u00b7' || c2 == '\'' || c2 == '\"' || c2 == ',' || c2 == '.' || c2 == '\uff0c' || c2 == '\u3002') continue;
            stringBuilder.append(Character.toLowerCase(c2));
        }
        return stringBuilder.toString().toLowerCase(Locale.ROOT);
    }

    public static int c() {
        return QueueTrack.b().size();
    }

    static {
        available = true;
    }

    public static final class QueueTrack {
        private final long id;
        private final String name;
        private final String artist;
        private final long durationMs;

        QueueTrack(long l2, String string, String string2, long l3) {
            this.id = l2;
            this.name = string == null ? "" : string;
            this.artist = string2 == null ? "" : string2;
            this.durationMs = l3;
        }

        public long a() {
            return this.id;
        }

        public String b() {
            return this.name;
        }

        public String c() {
            return this.artist;
        }

        public long d() {
            return this.durationMs;
        }

        public String toString() {
            return this.id + " " + this.name + " - " + this.artist;
        }
    }
}

