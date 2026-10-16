/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.music.a;

import com.heypixel.heypixelmod.obsoverlay.b.ClientConfigurationManager;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class AlbumCoverCache {
    private static final Logger LOGGER = LogManager.getLogger(AlbumCoverCache.class);
    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36";
    private static volatile AlbumCoverCache instance;
    private final File folder;
    private final Map<String, File> ready = new ConcurrentHashMap<String, File>();
    private final Set<String> inFlight = ConcurrentHashMap.newKeySet();

    private AlbumCoverCache() {
        File file = CoverIndex.clientFolder != null ? CoverIndex.clientFolder : new File(System.getProperty("user.home"), "EixClient");
        this.folder = new File(file, "covers");
        try {
            if (!this.folder.exists()) {
                this.folder.mkdirs();
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static AlbumCoverCache a() {
        if (instance != null) return instance;
        Class<AlbumCoverCache> clazz = AlbumCoverCache.class;
        synchronized (AlbumCoverCache.class) {
            if (instance != null) return instance;
            instance = new AlbumCoverCache();
            // ** MonitorExit[var0] (shouldn't be in output)
            return instance;
        }
    }

    public File b() {
        return this.folder;
    }

    public File a(String string) {
        if (string == null || string.isBlank()) {
            return null;
        }
        File file = this.ready.get(string);
        if (file != null && file.isFile()) {
            return file;
        }
        File file2 = this.b(string);
        if (file2.isFile() && file2.length() > 0L) {
            this.ready.put(string, file2);
            return file2;
        }
        return null;
    }

    public void a(String string, String string2, Consumer<File> consumer) {
        if (string == null || string.isBlank() || string2 == null || string2.isBlank()) {
            return;
        }
        File file = this.a(string);
        if (file != null) {
            if (consumer != null) {
                consumer.accept(file);
            }
            return;
        }
        if (!this.inFlight.add(string)) {
            return;
        }
        Thread thread = new Thread(() -> {
            try {
                File file = this.b(string);
                if (this.a(string2, file)) {
                    this.ready.put(string, file);
                    if (consumer != null) {
                        consumer.accept(file);
                    }
                }
            }
            catch (Throwable throwable) {
                LOGGER.debug("\u4e0b\u8f7d\u5c01\u9762\u5931\u8d25: {}", (Object)string2, (Object)throwable);
            }
            finally {
                this.inFlight.remove(string);
            }
        }, "EixClient-Cover");
        thread.setDaemon(true);
        thread.start();
    }

    public File a(String string, String string2) {
        if (string == null || string.isBlank() || string2 == null || string2.isBlank()) {
            return null;
        }
        File file = this.a(string);
        if (file != null) {
            return file;
        }
        File file2 = this.b(string);
        try {
            if (this.a(string2, file2)) {
                this.ready.put(string, file2);
                return file2;
            }
        }
        catch (Throwable throwable) {
            LOGGER.debug("\u4e0b\u8f7d\u5c01\u9762\u5931\u8d25: {}", (Object)string2, (Object)throwable);
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean a(String string, File file) throws Exception {
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpURLConnection)new URL(string).openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(8000);
            httpURLConnection.setReadTimeout(15000);
            httpURLConnection.setRequestProperty("User-Agent", USER_AGENT);
            httpURLConnection.setInstanceFollowRedirects(true);
            if (httpURLConnection.getResponseCode() != 200) {
                boolean bl = false;
                return bl;
            }
            File file2 = new File(file.getParentFile(), file.getName() + ".part");
            try (InputStream inputStream = httpURLConnection.getInputStream();){
                Files.copy(inputStream, file2.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
            if (file2.length() <= 0L) {
                file2.delete();
                boolean bl = false;
                return bl;
            }
            Files.move(file2.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            boolean bl = true;
            return bl;
        }
        finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
    }

    private File b(String string) {
        String string2 = string.replaceAll("[^A-Za-z0-9._-]", "_");
        return new File(this.folder, string2 + ".jpg");
    }
}

