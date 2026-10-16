/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.music.nowplaying;

import com.sun.jna.Callback;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class WindowTitleBridge {
    private static final String[] KNOWN_PROCESSES = new String[]{"cloudmusic.exe", "qqmusic.exe", "kugou.exe", "kwmusic.exe", "kuwo.exe", "spotify.exe", "foobar2000.exe", "aimp.exe", "music.ui.exe", "musicbee.exe", "potplayer.exe", "vlc.exe"};
    private static final String[] TITLE_SUFFIXES = new String[]{" - \u7f51\u6613\u4e91\u97f3\u4e50", " - QQ\u97f3\u4e50", " - \u9177\u72d7\u97f3\u4e50", " - \u9177\u6211\u97f3\u4e50", " - Spotify", " - foobar2000", " - AIMP", " - MusicBee", " - PotPlayer", " - VLC media player"};
    private static final String[] IDLE_TITLES = new String[]{"\u7f51\u6613\u4e91\u97f3\u4e50", "qq\u97f3\u4e50", "\u9177\u72d7\u97f3\u4e50", "\u9177\u6211\u97f3\u4e50", "spotify", "musicbee", "aimp", "foobar2000"};
    private static Boolean available;
    private static long lastPollAt;
    private static List<DetectedTrack> lastResult;
    private static final Map<Integer, String> processNameCache;

    private WindowTitleBridge() {
    }

    public static boolean a() {
        if (available == null) {
            try {
                available = User32Api.INSTANCE != null && Kernel32Api.INSTANCE != null;
            }
            catch (Throwable throwable) {
                available = false;
            }
        }
        return available;
    }

    public static List<DetectedTrack> b() {
        long l2 = System.currentTimeMillis();
        if (l2 - lastPollAt < 1000L) {
            return lastResult;
        }
        lastPollAt = l2;
        if (!WindowTitleBridge.a()) {
            lastResult = List.of();
            return lastResult;
        }
        try {
            List<WindowInfo> list = WindowTitleBridge.d();
            ArrayList<DetectedTrack> arrayList = new ArrayList<DetectedTrack>(2);
            ArrayList<DetectedTrack> arrayList2 = new ArrayList<DetectedTrack>(4);
            for (WindowInfo windowInfo : list) {
                DetectedTrack b2 = WindowTitleBridge.a(windowInfo.title, windowInfo.processName);
                if (b2 == null) continue;
                if (windowInfo.processName.contains("cloudmusic")) {
                    arrayList.add(b2);
                    continue;
                }
                arrayList2.add(b2);
            }
            arrayList.addAll(arrayList2);
            lastResult = arrayList;
            return lastResult;
        }
        catch (Throwable throwable) {
            lastResult = List.of();
            return lastResult;
        }
    }

    public static boolean c() {
        if (!WindowTitleBridge.a()) {
            return false;
        }
        try {
            for (WindowInfo windowInfo : WindowTitleBridge.d()) {
                if (!windowInfo.processName.contains("cloudmusic")) continue;
                return true;
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        return false;
    }

    private static List<WindowInfo> d() {
        ArrayList<WindowInfo> arrayList = new ArrayList<WindowInfo>(24);
        User32Api.INSTANCE.a((pointer, pointer2) -> {
            try {
                if (!User32Api.INSTANCE.b(pointer)) {
                    return true;
                }
                int n2 = User32Api.INSTANCE.a(pointer);
                if (n2 <= 0 || n2 > 512) {
                    return true;
                }
                char[] cArray = new char[n2 + 2];
                int n3 = User32Api.INSTANCE.a(pointer, cArray, cArray.length);
                if (n3 <= 0) {
                    return true;
                }
                String string = Native.toString((char[])cArray).trim();
                if (string.isEmpty()) {
                    return true;
                }
                IntByReference intByReference = new IntByReference();
                User32Api.INSTANCE.a(pointer, intByReference);
                String string2 = WindowTitleBridge.a(intByReference.getValue());
                if (string2.isEmpty() || !WindowTitleBridge.a(string2)) {
                    return true;
                }
                arrayList.add(new WindowInfo(string, string2));
            }
            catch (Throwable throwable) {
                // empty catch block
            }
            return true;
        }, Pointer.NULL);
        return arrayList;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static String a(int n2) {
        if (n2 <= 0) {
            return "";
        }
        String string = processNameCache.get(n2);
        if (string != null) {
            return string;
        }
        String string2 = "";
        Pointer pointer = null;
        try {
            IntByReference intByReference;
            char[] cArray;
            pointer = Kernel32Api.INSTANCE.a(4096, false, n2);
            if (pointer != null && Kernel32Api.INSTANCE.a(pointer, 0, cArray = new char[1024], intByReference = new IntByReference(cArray.length))) {
                String string3 = Native.toString((char[])cArray);
                int n3 = Math.max(string3.lastIndexOf(92), string3.lastIndexOf(47));
                string2 = (n3 >= 0 ? string3.substring(n3 + 1) : string3).toLowerCase(Locale.ROOT);
            }
        }
        catch (Throwable throwable) {
        }
        finally {
            if (pointer != null) {
                try {
                    Kernel32Api.INSTANCE.a(pointer);
                }
                catch (Throwable throwable) {}
            }
        }
        if (processNameCache.size() > 256) {
            processNameCache.clear();
        }
        processNameCache.put(n2, string2);
        return string2;
    }

    private static boolean a(String string) {
        for (String string2 : KNOWN_PROCESSES) {
            if (!string2.equals(string)) continue;
            return true;
        }
        return false;
    }

    static DetectedTrack a(String string, String string2) {
        String string3;
        String string4 = string.trim();
        String string5 = string4.toLowerCase(Locale.ROOT);
        for (String string6 : TITLE_SUFFIXES) {
            if (!string5.endsWith(string6.toLowerCase(Locale.ROOT))) continue;
            string4 = string4.substring(0, string4.length() - string6.length()).trim();
            break;
        }
        string4 = string4.replaceFirst("^[\u25b6\u25ba\u23f8]\\s*", "");
        string4 = string4.replaceFirst("^(\u6b63\u5728\u64ad\u653e|Now Playing|\u6682\u505c\u4e2d)[:\uff1a]\\s*", "");
        if ((string4 = string4.trim()).isEmpty()) {
            return null;
        }
        String string7 = string4.toLowerCase(Locale.ROOT);
        for (String string8 : IDLE_TITLES) {
            if (!string7.equals(string8)) continue;
            return null;
        }
        String[] stringArray = string4.split("\\s+-\\s+");
        String string9 = "";
        if (stringArray.length >= 2) {
            string3 = stringArray[0].trim();
            string9 = stringArray[1].trim();
        } else {
            string3 = string4.trim();
        }
        if (!WindowTitleBridge.b(string3) || string3.length() > 120) {
            return null;
        }
        if (!string9.isEmpty() && !WindowTitleBridge.b(string9)) {
            string9 = "";
        }
        if (string3.equalsIgnoreCase(string9)) {
            string9 = "";
        }
        return new DetectedTrack(string2, string3, string9, string);
    }

    private static boolean b(String string) {
        for (int i2 = 0; i2 < string.length(); ++i2) {
            if (!Character.isLetterOrDigit(string.charAt(i2))) continue;
            return true;
        }
        return false;
    }

    static {
        lastResult = List.of();
        processNameCache = new ConcurrentHashMap<Integer, String>();
    }

    public static interface User32Api
    extends StdCallLibrary {
        public static final User32Api INSTANCE = (User32Api)Native.load((String)"user32", User32Api.class, (Map)W32APIOptions.DEFAULT_OPTIONS);

        public boolean a(WindowEnumCallback var1, Pointer var2);

        public int a(Pointer var1);

        public int a(Pointer var1, char[] var2, int var3);

        public boolean b(Pointer var1);

        public int a(Pointer var1, IntByReference var2);
    }

    public static interface Kernel32Api
    extends StdCallLibrary {
        public static final Kernel32Api INSTANCE = (Kernel32Api)Native.load((String)"kernel32", Kernel32Api.class, (Map)W32APIOptions.DEFAULT_OPTIONS);

        public Pointer a(int var1, boolean var2, int var3);

        public boolean a(Pointer var1, int var2, char[] var3, IntByReference var4);

        public boolean a(Pointer var1);
    }

    private record WindowInfo(String title, String processName) {
    }

    public static final class DetectedTrack {
        private final String processName;
        private final String song;
        private final String artist;
        private final String rawTitle;

        DetectedTrack(String string, String string2, String string3, String string4) {
            this.processName = string;
            this.song = string2;
            this.artist = string3;
            this.rawTitle = string4;
        }

        public String a() {
            return this.processName;
        }

        public String b() {
            return this.song;
        }

        public String c() {
            return this.artist;
        }

        public String d() {
            return this.rawTitle;
        }

        public boolean e() {
            return this.processName != null && this.processName.contains("cloudmusic");
        }

        public String toString() {
            return this.processName + " | " + this.song + " - " + this.artist + " | title=" + this.rawTitle;
        }
    }

    public static interface WindowEnumCallback
    extends Callback {
        public boolean invoke(Pointer var1, Pointer var2);
    }
}

