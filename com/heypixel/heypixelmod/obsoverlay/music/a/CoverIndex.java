/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.music.a;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.heypixel.heypixelmod.obsoverlay.music.a.OnlineMusicApiClient;
import com.heypixel.heypixelmod.obsoverlay.music.a.PlaybackTimelineModel;
import com.heypixel.heypixelmod.obsoverlay.music.MusicLibraryManager;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class CoverIndex {
    private static final Logger LOGGER = LogManager.getLogger(CoverIndex.class);
    private static volatile CoverIndex instance;
    private final Map<String, Long> index = new ConcurrentHashMap<String, Long>();
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final File indexFile;
    private final AtomicBoolean resolving = new AtomicBoolean(false);
    private volatile String currentKey = "";
    private volatile File currentFile;
    private volatile long currentSongId = -1L;

    private CoverIndex() {
        File file = com.heypixel.heypixelmod.obsoverlay.ClientVersion.b.clientFolder != null ? com.heypixel.heypixelmod.obsoverlay.ClientVersion.b.clientFolder : new File(System.getProperty("user.home"), "EixClient");
        this.indexFile = new File(file, "cover_index.json");
        this.e();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static CoverIndex a() {
        if (instance != null) return instance;
        Class<CoverIndex> clazz = CoverIndex.class;
        synchronized (CoverIndex.class) {
            if (instance != null) return instance;
            instance = new CoverIndex();
            // ** MonitorExit[var0] (shouldn't be in output)
            return instance;
        }
    }

    public File b() {
        File file = this.currentFile;
        return file != null && file.isFile() ? file : null;
    }

    public long c() {
        return this.currentSongId;
    }

    public void a(String string, String string2, long l2) {
        if (string == null || string.isBlank()) {
            this.d();
            return;
        }
        MusicLibraryManager c2 = MusicLibraryManager.a();
        PlaybackTimelineModel i2 = c2.o();
        if (i2 != null) {
            String string3 = "online:" + i2.a();
            if (!string3.equals(this.currentKey)) {
                this.currentKey = string3;
                this.currentSongId = i2.a();
                this.currentFile = OnlineMusicApiClient.a().c(i2);
                OnlineMusicApiClient.a().a(i2, file -> {
                    this.currentFile = file;
                });
            }
            return;
        }
        String string4 = CoverIndex.a(string, string2);
        if (string4.equals(this.currentKey)) {
            return;
        }
        this.currentKey = string4;
        this.currentFile = null;
        this.currentSongId = l2;
        this.a(string, string2, l2, string4);
    }

    public void d() {
        this.currentKey = "";
        this.currentFile = null;
        this.currentSongId = -1L;
    }

    private void a(String string, String string2, long l2, String string3) {
        if (!this.resolving.compareAndSet(false, true)) {
            return;
        }
        Thread thread = new Thread(() -> {
            try {
                Object object;
                Object object2;
                OnlineMusicApiClient g2 = OnlineMusicApiClient.a();
                long l3 = l2;
                if (l3 <= 0L) {
                    object2 = this.index.get(string3);
                    if (object2 != null) {
                        l3 = (Long)object2;
                    } else {
                        object = g2.a(string, string2, -1L);
                        if (object == null) {
                            return;
                        }
                        l3 = ((PlaybackTimelineModel)object).a();
                        this.index.put(string3, l3);
                        this.f();
                    }
                }
                if (!string3.equals(this.currentKey)) {
                    return;
                }
                object2 = g2.b(l3);
                if (object2 == null || ((PlaybackTimelineModel)object2).e().isBlank()) {
                    return;
                }
                object = g2.c().a("song_" + l3, ((PlaybackTimelineModel)object2).e());
                if (object != null && string3.equals(this.currentKey)) {
                    this.currentFile = object;
                    this.currentSongId = l3;
                }
            }
            catch (Throwable throwable) {
                LOGGER.debug("\u89e3\u6790\u5c01\u9762\u5931\u8d25: {} - {}", (Object)string, (Object)string2, (Object)throwable);
            }
            finally {
                this.resolving.set(false);
            }
        }, "EixClient-CoverResolve");
        thread.setDaemon(true);
        thread.start();
    }

    private static String a(String string, String string2) {
        return (string.trim() + "\u0000" + (string2 == null ? "" : string2.trim())).toLowerCase(Locale.ROOT);
    }

    private void e() {
        if (!this.indexFile.isFile()) {
            return;
        }
        try (BufferedReader bufferedReader = Files.newBufferedReader(this.indexFile.toPath(), StandardCharsets.UTF_8);){
            Type type = new TypeToken<Map<String, Long>>(){}.getType();
            Map map = (Map)this.gson.fromJson((Reader)bufferedReader, type);
            if (map != null) {
                this.index.putAll(map);
            }
        }
        catch (Throwable throwable) {
            LOGGER.debug("\u8bfb\u53d6\u5c01\u9762\u7d22\u5f15\u5931\u8d25", throwable);
        }
    }

    private void f() {
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(this.indexFile.toPath(), StandardCharsets.UTF_8, new OpenOption[0]);){
            this.gson.toJson(this.index, (Appendable)bufferedWriter);
        }
        catch (Throwable throwable) {
            LOGGER.debug("\u4fdd\u5b58\u5c01\u9762\u7d22\u5f15\u5931\u8d25", throwable);
        }
    }
}

