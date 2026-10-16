/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.music;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.MusicPlayer;
import com.heypixel.heypixelmod.obsoverlay.music.a.OnlineMusicApiClient;
import com.heypixel.heypixelmod.obsoverlay.music.a.PlaybackTimelineModel;
import com.heypixel.heypixelmod.obsoverlay.music.a.MusicAudioDownloadInfo;
import com.heypixel.heypixelmod.obsoverlay.music.MusicTrackMetadata;
import com.heypixel.heypixelmod.obsoverlay.utils.BackgroundExecutor;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MusicLibraryManager {
    private static final Logger log = LogManager.getLogger(MusicLibraryManager.class);
    private static volatile MusicLibraryManager instance;
    private final com.heypixel.heypixelmod.obsoverlay.music.MusicPlaybackEngine engine = new com.heypixel.heypixelmod.obsoverlay.music.MusicPlaybackEngine();
    private final List<MusicTrackMetadata> playlist = new CopyOnWriteArrayList<MusicTrackMetadata>();
    private final Deque<Integer> history = new ArrayDeque<Integer>();
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final List<i> onlineQueue = new CopyOnWriteArrayList<i>();
    private volatile int onlineIndex = -1;
    private volatile i currentOnlineSong;
    private final File stateFile;
    private volatile File musicFolder;
    private volatile int currentIndex = -1;
    private volatile boolean shuffle = false;
    private volatile LoopMode loopMode = LoopMode.ALL;
    private volatile boolean scanning = false;
    private volatile boolean loaded = false;

    private MusicLibraryManager() {
        File file = com.heypixel.heypixelmod.obsoverlay.ClientVersion.b.clientFolder != null ? com.heypixel.heypixelmod.obsoverlay.ClientVersion.b.clientFolder : new File(System.getProperty("user.home"), "EixClient");
        this.musicFolder = new File(file, "music");
        this.stateFile = new File(file, "music_player.json");
        this.engine.a(this::x);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static MusicLibraryManager a() {
        if (instance != null) return instance;
        Class<MusicLibraryManager> clazz = MusicLibraryManager.class;
        synchronized (MusicLibraryManager.class) {
            if (instance != null) return instance;
            instance = new MusicLibraryManager();
            // ** MonitorExit[var0] (shouldn't be in output)
            return instance;
        }
    }

    public void b() {
        if (this.loaded) {
            return;
        }
        this.loaded = true;
        this.z();
        this.u();
        BackgroundExecutor.a(this::l);
    }

    private void u() {
        try {
            boolean bl;
            if (!this.musicFolder.exists() && (bl = this.musicFolder.mkdirs())) {
                this.v();
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    private void v() {
        File file = new File(this.musicFolder, "\u628a\u97f3\u4e50\u653e\u5728\u8fd9\u91cc.txt");
        if (file.exists()) {
            return;
        }
        String string = "EixClient \u97f3\u4e50\u64ad\u653e\u5668\n====================\n\n\u628a\u97f3\u9891\u6587\u4ef6\u76f4\u63a5\u653e\u8fdb\u8fd9\u4e2a\u6587\u4ef6\u5939\uff08\u652f\u6301\u5b50\u6587\u4ef6\u5939\uff09\u5373\u53ef\uff0c\u652f\u6301\u7684\u683c\u5f0f\uff1a\n  .mp3  .ogg  .wav  .aiff  .aif  .au\n\n\u6e38\u620f\u5185\u64cd\u4f5c\uff1a\n  Insert   \u6253\u5f00\u64ad\u653e\u5217\u8868\u754c\u9762\n  [        \u64ad\u653e / \u6682\u505c\n  ]        \u4e0b\u4e00\u9996\n  \\        \u4e0a\u4e00\u9996\n  = / -    \u97f3\u91cf + / -\n  \u6216\u5728\u804a\u5929\u680f\u8f93\u5165 .music \u67e5\u770b\u547d\u4ee4\u5e2e\u52a9\n\n\u4fee\u6539\u97f3\u4e50\u76ee\u5f55\uff1a\u6e38\u620f\u5185\u6253\u5f00 ClickGUI -> MusicPlayer -> Music Folder\n";
        try {
            Files.writeString(file.toPath(), (CharSequence)string, StandardCharsets.UTF_8, new OpenOption[0]);
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    public com.heypixel.heypixelmod.obsoverlay.music.MusicPlaybackEngine c() {
        return this.engine;
    }

    public File d() {
        return this.musicFolder;
    }

    public void a(File file) {
        if (file == null) {
            return;
        }
        if (this.musicFolder != null && this.musicFolder.getAbsolutePath().equals(file.getAbsolutePath())) {
            return;
        }
        this.musicFolder = file;
        this.u();
        this.y();
        BackgroundExecutor.a(this::l);
    }

    public void a(String string) {
        if (string == null || string.isBlank()) {
            return;
        }
        this.a(new File(string.trim()));
    }

    public List<MusicTrackMetadata> e() {
        return Collections.unmodifiableList(this.playlist);
    }

    public int f() {
        return this.playlist.size();
    }

    public int g() {
        return this.currentIndex;
    }

    public MusicTrackMetadata h() {
        int n2 = this.currentIndex;
        if (n2 < 0 || n2 >= this.playlist.size()) {
            return null;
        }
        return this.playlist.get(n2);
    }

    public boolean i() {
        return this.shuffle;
    }

    public void a(boolean bl) {
        this.shuffle = bl;
        this.y();
    }

    public LoopMode j() {
        return this.loopMode;
    }

    public void a(LoopMode a2) {
        this.loopMode = a2 == null ? LoopMode.ALL : a2;
        this.y();
    }

    public boolean k() {
        return this.scanning;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void l() {
        if (this.scanning) {
            return;
        }
        this.scanning = true;
        try {
            Object object;
            this.u();
            ArrayList arrayList = new ArrayList();
            File file = this.musicFolder;
            if (file != null && file.isDirectory()) {
                try {
                    object = Files.walk(file.toPath(), new FileVisitOption[0]);
                    try {
                        object.filter(path -> Files.isRegularFile(path, new LinkOption[0])).filter(path -> com.heypixel.heypixelmod.obsoverlay.music.AudioFormatSupport.a(path.getFileName().toString())).forEach(path -> arrayList.add(new MusicTrackMetadata(path.toFile())));
                    }
                    finally {
                        if (object != null) {
                            object.close();
                        }
                    }
                }
                catch (Throwable throwable) {
                    log.warn("\u626b\u63cf\u97f3\u4e50\u76ee\u5f55\u5931\u8d25", throwable);
                }
            }
            arrayList.sort((d2, d3) -> d2.a().getName().compareToIgnoreCase(d3.a().getName()));
            object = this.h();
            this.playlist.clear();
            this.playlist.addAll(arrayList);
            if (object != null) {
                int n2;
                this.currentIndex = n2 = this.playlist.indexOf(object);
            } else if (this.currentIndex >= this.playlist.size()) {
                this.currentIndex = this.playlist.isEmpty() ? -1 : this.playlist.size() - 1;
            }
            BackgroundExecutor.a(() -> {
                for (MusicTrackMetadata d2 : this.playlist) {
                    try {
                        d2.g();
                    }
                    catch (Throwable throwable) {}
                }
            });
        }
        finally {
            this.scanning = false;
        }
    }

    public void a(int n2) {
        if (n2 < 0 || n2 >= this.playlist.size()) {
            return;
        }
        this.w();
        MusicTrackMetadata d2 = this.playlist.get(n2);
        int n3 = this.currentIndex;
        if (n3 >= 0 && n3 != n2) {
            this.history.push(n3);
            while (this.history.size() > 64) {
                this.history.removeLast();
            }
        }
        this.currentIndex = n2;
        d2.g();
        this.engine.b();
        this.engine.a(d2.a(), d2.d());
        this.b(d2);
        this.y();
    }

    private void b(MusicTrackMetadata d2) {
        try {
            com.heypixel.heypixelmod.obsoverlay.EixClientCore a2 = com.heypixel.heypixelmod.obsoverlay.EixClientCore.a();
            if (a2 == null || a2.j() == null) {
                return;
            }
            com.heypixel.heypixelmod.obsoverlay.c.MainMenuScreen.a a3 = new com.heypixel.heypixelmod.obsoverlay.c.MainMenuScreen.a(com.heypixel.heypixelmod.obsoverlay.c.MainMenuScreen.b.b, "\u266a " + d2.h(), 2500L);
            a2.j().a(a3);
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    public void a(MusicTrackMetadata d2) {
        int n2 = this.playlist.indexOf(d2);
        if (n2 >= 0) {
            this.a(n2);
        }
    }

    public void m() {
        if (this.engine.d()) {
            this.engine.l();
            return;
        }
        if (this.n() && this.currentOnlineSong != null) {
            this.b(this.onlineIndex);
            return;
        }
        if (this.playlist.isEmpty()) {
            this.l();
        }
        if (this.playlist.isEmpty()) {
            return;
        }
        if (this.currentIndex < 0 || this.currentIndex >= this.playlist.size()) {
            this.a(0);
        } else {
            this.a(this.currentIndex);
        }
    }

    public boolean n() {
        return this.currentOnlineSong != null;
    }

    public i o() {
        return this.currentOnlineSong;
    }

    public List<i> p() {
        return List.copyOf(this.onlineQueue);
    }

    public int q() {
        return this.onlineIndex;
    }

    public void a(List<i> list, int n2) {
        if (list == null || list.isEmpty()) {
            return;
        }
        this.onlineQueue.clear();
        this.onlineQueue.addAll(list);
        this.b(n2);
    }

    public void a(List<i> list) {
        this.onlineQueue.clear();
        if (list != null) {
            this.onlineQueue.addAll(list);
        }
        i i2 = this.currentOnlineSong;
        this.onlineIndex = -1;
        if (i2 != null) {
            for (int i3 = 0; i3 < this.onlineQueue.size(); ++i3) {
                if (this.onlineQueue.get(i3).a() != i2.a()) continue;
                this.onlineIndex = i3;
                break;
            }
        }
    }

    public void b(int n2) {
        if (n2 < 0 || n2 >= this.onlineQueue.size()) {
            return;
        }
        i i2 = this.onlineQueue.get(n2);
        this.onlineIndex = n2;
        this.currentOnlineSong = i2;
        this.a("\u89e3\u6790\u4e2d\u2026", i2.j(), 1500L);
        BackgroundExecutor.a(() -> this.a(i2));
    }

    private void w() {
        this.currentOnlineSong = null;
        this.onlineIndex = -1;
        this.onlineQueue.clear();
    }

    private void a(i i2) {
        g g2 = g.a();
        try {
            j j2;
            j j3 = g2.a(i2);
            if (j3.i()) {
                this.a("\u65e0\u6cd5\u64ad\u653e", i2.j() + "\uff1a\u6ca1\u6709\u53ef\u7528\u7684\u64ad\u653e\u5730\u5740\uff08\u7248\u6743\u53d7\u9650\uff09", 3500L);
                return;
            }
            if (!MusicLibraryManager.b(j3.b()) && !(j2 = g2.b().a(i2.a(), "exhigh", null)).i() && MusicLibraryManager.b(j2.b())) {
                j3 = j2;
            }
            if (!MusicLibraryManager.b(j3.b())) {
                this.a("\u65e0\u6cd5\u64ad\u653e", i2.j() + "\uff1a\u5f53\u524d\u6863\u4f4d\u662f FLAC/AAC\uff0c\u8bf7\u6539\u7528 exhigh \u6216\u5148\u4e0b\u8f7d", 4000L);
                return;
            }
            j2 = j3;
            this.engine.b();
            com.heypixel.heypixelmod.obsoverlay.music.MusicPlaybackEngine.PlaybackState(() -> com.heypixel.heypixelmod.obsoverlay.music.AudioFormatSupport.a(g2.f(j2.b()), j2.b()), i2.j(), i2.f());
            this.b(i2);
        }
        catch (Throwable throwable) {
            String string = throwable.getMessage() == null ? throwable.getClass().getSimpleName() : throwable.getMessage();
            log.warn("\u5728\u7ebf\u64ad\u653e\u5931\u8d25: {}", (Object)i2.j(), (Object)throwable);
            this.a("\u5728\u7ebf\u64ad\u653e\u5931\u8d25", string, 3500L);
        }
    }

    private static boolean b(String string) {
        boolean bl;
        block8: {
            InputStream inputStream = g.a().f(string);
            try {
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, 4096);
                bl = com.heypixel.heypixelmod.obsoverlay.music.AudioFormatSupport.a(com.heypixel.heypixelmod.obsoverlay.music.AudioFormatSupport.c(bufferedInputStream, string));
                if (inputStream == null) break block8;
            }
            catch (Throwable throwable) {
                try {
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
                catch (Throwable throwable3) {
                    return false;
                }
            }
            inputStream.close();
        }
        return bl;
    }

    private void b(i i2) {
        this.a("\u266a \u5728\u7ebf\u64ad\u653e", i2.j(), 2500L);
    }

    private void a(String string, String string2, long l2) {
        try {
            com.heypixel.heypixelmod.obsoverlay.EixClientCore a2 = com.heypixel.heypixelmod.obsoverlay.EixClientCore.a();
            if (a2 == null || a2.j() == null) {
                return;
            }
            a2.j().a(new com.heypixel.heypixelmod.obsoverlay.c.MainMenuScreen.a(com.heypixel.heypixelmod.obsoverlay.c.MainMenuScreen.b.b, string + " " + string2, l2));
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    public void r() {
        this.engine.o();
        this.y();
    }

    public void s() {
        this.b(false);
    }

    private void b(boolean bl) {
        int n2;
        if (this.n()) {
            int n3;
            if (this.onlineQueue.isEmpty()) {
                return;
            }
            if (bl && this.loopMode == LoopMode.ONE) {
                this.b(this.onlineIndex);
                return;
            }
            if (this.shuffle && this.onlineQueue.size() > 1) {
                while ((n3 = (int)(Math.random() * (double)this.onlineQueue.size())) == this.onlineIndex) {
                }
            } else {
                n3 = this.onlineIndex + 1;
            }
            if (n3 >= this.onlineQueue.size()) {
                if (bl && this.loopMode == LoopMode.OFF) {
                    this.engine.o();
                    this.onlineIndex = this.onlineQueue.size() - 1;
                    return;
                }
                n3 = 0;
            }
            this.b(n3);
            return;
        }
        if (this.playlist.isEmpty()) {
            return;
        }
        if (bl && this.loopMode == LoopMode.ONE) {
            this.a(this.currentIndex);
            return;
        }
        if (this.shuffle && this.playlist.size() > 1) {
            while ((n2 = (int)(Math.random() * (double)this.playlist.size())) == this.currentIndex) {
            }
        } else {
            n2 = this.currentIndex + 1;
        }
        if (n2 >= this.playlist.size()) {
            if (bl && this.loopMode == LoopMode.OFF) {
                this.engine.o();
                this.currentIndex = this.playlist.size() - 1;
                return;
            }
            n2 = 0;
        }
        this.a(n2);
    }

    public void t() {
        Integer n2;
        if (this.n()) {
            if (this.onlineQueue.isEmpty()) {
                return;
            }
            int n3 = this.onlineIndex - 1;
            if (n3 < 0) {
                n3 = this.onlineQueue.size() - 1;
            }
            this.b(n3);
            return;
        }
        if (this.playlist.isEmpty()) {
            return;
        }
        if (!this.history.isEmpty() && (n2 = this.history.pop()) != null && n2 >= 0 && n2 < this.playlist.size()) {
            int n4 = n2;
            this.currentIndex = -1;
            this.a(n4);
            return;
        }
        int n5 = this.currentIndex - 1;
        if (n5 < 0) {
            n5 = this.playlist.size() - 1;
        }
        int n6 = n5;
        this.currentIndex = -1;
        this.a(n6);
    }

    private void x() {
        this.b(true);
    }

    public void a(float f2) {
        this.engine.a(f2);
    }

    @EventTarget
    public void onKey(com.heypixel.heypixelmod.obsoverlay.events.impl.KeyboardKeyPressEvent j2) {
        if (!j2.c()) {
            return;
        }
        if (Minecraft.m_91087_().f_91080_ != null) {
            return;
        }
        com.heypixel.heypixelmod.obsoverlay.EixClientCore a2 = com.heypixel.heypixelmod.obsoverlay.EixClientCore.a();
        if (a2 == null || a2.g() == null) {
            return;
        }
        MusicPlayer o2 = a2.g().a(MusicPlayer.class);
        if (o2 == null) {
            return;
        }
        int n2 = j2.b();
        if (n2 == 0) {
            return;
        }
        if (n2 == o2.s.m()) {
            this.m();
        } else if (n2 == o2.t.m()) {
            this.s();
        } else if (n2 == o2.u.m()) {
            this.t();
        } else if (n2 == o2.v.m()) {
            this.engine.a(Math.min(1.0f, this.engine.i() + 0.05f));
            o2.i.a(this.engine.i() * 100.0f);
        } else if (n2 == o2.w.m()) {
            this.engine.a(Math.max(0.0f, this.engine.i() - 0.05f));
            o2.i.a(this.engine.i() * 100.0f);
        } else if (n2 == o2.x.m()) {
            MusicPlayer.p();
        }
    }

    private void y() {
        SavedLibraryState b2 = new SavedLibraryState();
        b2.index = this.currentIndex;
        b2.shuffle = this.shuffle;
        b2.loop = this.loopMode.name();
        b2.volume = this.engine.i();
        b2.folder = this.musicFolder != null ? this.musicFolder.getAbsolutePath() : null;
        MusicTrackMetadata d2 = this.h();
        if (d2 != null) {
            b2.tracks.add(d2.f());
        }
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(this.stateFile.toPath(), StandardCharsets.UTF_8, new OpenOption[0]);){
            this.gson.toJson((Object)b2, (Appendable)bufferedWriter);
        }
        catch (Throwable throwable) {
            log.warn("\u4fdd\u5b58\u97f3\u4e50\u64ad\u653e\u5668\u72b6\u6001\u5931\u8d25", throwable);
        }
    }

    private void z() {
        if (!this.stateFile.isFile()) {
            return;
        }
        try (BufferedReader bufferedReader = Files.newBufferedReader(this.stateFile.toPath(), StandardCharsets.UTF_8);){
            Type type = new TypeToken<SavedLibraryState>(){}.getType();
            SavedLibraryState b2 = (SavedLibraryState)this.gson.fromJson((Reader)bufferedReader, type);
            if (b2 == null) {
                return;
            }
            this.shuffle = b2.shuffle;
            this.engine.a(Math.max(0.0f, Math.min(1.0f, b2.volume)));
            try {
                this.loopMode = LoopMode.valueOf(b2.loop == null ? "ALL" : b2.loop.toUpperCase(Locale.ROOT));
            }
            catch (IllegalArgumentException illegalArgumentException) {
                this.loopMode = LoopMode.ALL;
            }
            if (b2.folder != null && !b2.folder.isBlank()) {
                this.musicFolder = new File(b2.folder);
            }
        }
        catch (Throwable throwable) {
            log.warn("\u8bfb\u53d6\u97f3\u4e50\u64ad\u653e\u5668\u72b6\u6001\u5931\u8d25", throwable);
        }
    }

    public static enum LoopMode {
        OFF("Off", "\u4e0d\u5faa\u73af"),
        ALL("All", "\u5217\u8868\u5faa\u73af"),
        ONE("One", "\u5355\u66f2\u5faa\u73af");

        private final String name;
        private final String cnName;

        private LoopMode(String string2, String string3) {
            this.name = string2;
            this.cnName = string3;
        }

        public String a() {
            return this.name;
        }

        public String b() {
            return this.cnName;
        }
    }

    private static class SavedLibraryState {
        List<String> tracks = new ArrayList<String>();
        int index = -1;
        boolean shuffle = false;
        String loop = "ALL";
        float volume = 0.7f;
        String folder;

        private SavedLibraryState() {
        }
    }
}

