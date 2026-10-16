/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.music;

import com.heypixel.heypixelmod.obsoverlay.music.AudioFormatSupport;
import java.io.File;
import java.util.Locale;
import javax.sound.sampled.AudioFileFormat;

public class MusicTrackMetadata {
    private final File file;
    private String title;
    private String artist;
    private long durationMillis = -1L;
    private boolean probed;

    public MusicTrackMetadata(File file) {
        this.file = file;
        this.title = MusicTrackMetadata.c(file.getName());
        this.artist = "";
    }

    public File a() {
        return this.file;
    }

    public String b() {
        return this.title == null || this.title.isEmpty() ? MusicTrackMetadata.c(this.file.getName()) : this.title;
    }

    public void a(String string) {
        this.title = string;
    }

    public String c() {
        return this.artist == null ? "" : this.artist;
    }

    public void b(String string) {
        this.artist = string;
    }

    public long d() {
        return this.durationMillis;
    }

    public void a(long l2) {
        this.durationMillis = l2;
    }

    public boolean e() {
        return this.durationMillis > 0L;
    }

    public String f() {
        return this.file.getAbsolutePath();
    }

    public synchronized void g() {
        String string;
        int n2;
        if (this.probed) {
            return;
        }
        this.probed = true;
        AudioFileFormat audioFileFormat = AudioFormatSupport.b(this.file);
        if (audioFileFormat != null) {
            long l2 = AudioFormatSupport.a(audioFileFormat);
            if (l2 > 0L) {
                this.durationMillis = l2;
            }
            String string2 = MusicTrackMetadata.a(AudioFormatSupport.a(audioFileFormat, "title"), AudioFormatSupport.a(audioFileFormat, "title_sort"));
            String string3 = MusicTrackMetadata.a(AudioFormatSupport.a(audioFileFormat, "author"), AudioFormatSupport.a(audioFileFormat, "artist"), AudioFormatSupport.a(audioFileFormat, "album_artist"));
            if (string2 != null) {
                this.title = string2;
            }
            if (string3 != null) {
                this.artist = string3;
            }
        }
        if ((n2 = (string = MusicTrackMetadata.c(this.file.getName())).indexOf(" - ")) > 0) {
            if (this.artist == null || this.artist.isEmpty()) {
                this.artist = string.substring(0, n2).trim();
            }
            if (this.title == null || this.title.isEmpty() || this.title.equals(string)) {
                this.title = string.substring(n2 + 3).trim();
            }
        }
    }

    private static String a(String ... stringArray) {
        for (String string : stringArray) {
            if (string == null || string.isEmpty()) continue;
            return string;
        }
        return null;
    }

    private static String c(String string) {
        int n2 = string.lastIndexOf(46);
        return n2 > 0 ? string.substring(0, n2) : string;
    }

    public String h() {
        String string = this.c();
        if (!string.isEmpty()) {
            return string + " - " + this.b();
        }
        return this.b();
    }

    public String i() {
        String string = this.file.getName();
        int n2 = string.lastIndexOf(46);
        return n2 >= 0 ? string.substring(n2 + 1).toLowerCase(Locale.ROOT) : "";
    }

    public static String b(long l2) {
        if (l2 < 0L) {
            return "--:--";
        }
        long l3 = l2 / 1000L;
        long l4 = l3 / 60L;
        long l5 = l3 % 60L;
        return String.format(Locale.ROOT, "%d:%02d", l4, l5);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof MusicTrackMetadata)) {
            return false;
        }
        MusicTrackMetadata d2 = (MusicTrackMetadata)object;
        return this.file.getAbsolutePath().equals(d2.file.getAbsolutePath());
    }

    public int hashCode() {
        return this.file.getAbsolutePath().hashCode();
    }

    public String toString() {
        return this.h();
    }
}

