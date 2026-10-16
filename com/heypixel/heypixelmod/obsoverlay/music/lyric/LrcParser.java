/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.music.lyric;

import com.heypixel.heypixelmod.obsoverlay.music.lyric.LyricLine;
import com.heypixel.heypixelmod.obsoverlay.music.lyric.LyricDocument;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class LrcParser {
    private static final Pattern TIME_TAG = Pattern.compile("\\[(\\d{1,3}):(\\d{1,2})(?:[.:](\\d{1,3}))?]");
    private static final Pattern OFFSET_TAG = Pattern.compile("\\[offset:\\s*([+-]?\\d+)\\s*]", 2);
    private static final Pattern CREDIT_LINE = Pattern.compile("^\\s*(\u4f5c\u8bcd|\u4f5c\u66f2|\u7f16\u66f2|\u5236\u4f5c\u4eba|\u6df7\u97f3|\u5f55\u97f3|\u5409\u4ed6|\u8d1d\u65af|\u9f13|\u548c\u58f0|\u6bcd\u5e26|\u76d1\u5236|\u51fa\u54c1|\u7b56\u5212|\u8bcd|\u66f2|OP|SP|lyricist|composer|arranger|producer|mixing|recording|guitar|bass|drums|mastering)[^:\uff1a]{0,8}[:\uff1a]", 2);
    private static final long TRANSLATION_TOLERANCE_MS = 2000L;

    private LrcParser() {
    }

    public static LyricDocument a(String string, String string2, String string3, String string4) {
        return LrcParser.a(string, string2, string3, string4, true);
    }

    public static LyricDocument a(String string, String string2, String string3, String string4, boolean bl) {
        if (string == null || string.isBlank()) {
            return LyricDocument.EMPTY;
        }
        long l2 = LrcParser.c(string);
        List<RawLine> list = LrcParser.a(string, l2, bl);
        if (list.isEmpty()) {
            return LyricDocument.EMPTY;
        }
        List<RawLine> list2 = LrcParser.a(string2, l2, false);
        List<LyricLine> list3 = LrcParser.a(list, list2);
        if (list3.isEmpty()) {
            return LyricDocument.EMPTY;
        }
        list3.sort(Comparator.comparingLong(LyricLine::a));
        return new LyricDocument(list3, string3, string4);
    }

    private static long c(String string) {
        Matcher matcher = OFFSET_TAG.matcher(string);
        if (matcher.find()) {
            try {
                return Long.parseLong(matcher.group(1));
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
        }
        return 0L;
    }

    private static List<RawLine> a(String string, long l2, boolean bl) {
        ArrayList<RawLine> arrayList = new ArrayList<RawLine>(128);
        if (string == null || string.isBlank()) {
            return arrayList;
        }
        String string2 = string.replace("\r\n", "\n").replace('\r', '\n');
        for (String string3 : string2.split("\n")) {
            if (string3.isBlank()) continue;
            Matcher matcher = TIME_TAG.matcher(string3);
            ArrayList<Long> arrayList2 = new ArrayList<Long>(2);
            int n2 = 0;
            boolean bl2 = true;
            while (matcher.find()) {
                if (matcher.start() > n2 && !string3.substring(n2, matcher.start()).isBlank()) {
                    bl2 = false;
                    break;
                }
                arrayList2.add(LrcParser.a(matcher.group(1), matcher.group(2), matcher.group(3)));
                n2 = matcher.end();
            }
            if (!bl2 || arrayList2.isEmpty()) continue;
            String string4 = string3.substring(n2).trim();
            if (bl && CREDIT_LINE.matcher(string4).find()) continue;
            for (Long l3 : arrayList2) {
                arrayList.add(new RawLine(Math.max(0L, l3 + l2), string4));
            }
        }
        arrayList.sort(Comparator.comparingLong(RawLine::timeMs));
        return arrayList;
    }

    private static List<LyricLine> a(List<RawLine> list, List<RawLine> list2) {
        ArrayList<RawLine> arrayList = new ArrayList<RawLine>(list2.size());
        for (RawLine rawLine : list2) {
            if (rawLine.text().isEmpty()) continue;
            arrayList.add(rawLine);
        }
        Object[] objectArray = new String[list.size()];
        int n2 = 0;
        if (!arrayList.isEmpty()) {
            int n3;
            for (n3 = 0; n3 < list.size(); ++n3) {
                int n4;
                RawLine rawLine = list.get(n3);
                if (rawLine.text().isEmpty() || (n4 = LrcParser.a(arrayList, rawLine.timeMs())) < 0 || Math.abs(((RawLine)arrayList.get(n4)).timeMs() - rawLine.timeMs()) > 2000L) continue;
                objectArray[n3] = ((RawLine)arrayList.get(n4)).text();
                ++n2;
            }
            n3 = 0;
            for (RawLine rawLine : list) {
                if (rawLine.text().isEmpty()) continue;
                ++n3;
            }
            if (n3 > 0 && n2 < n3 / 2) {
                Arrays.fill(objectArray, null);
                int n5 = 0;
                for (int i2 = 0; i2 < list.size() && n5 < arrayList.size(); ++i2) {
                    if (list.get(i2).text().isEmpty()) continue;
                    objectArray[i2] = ((RawLine)arrayList.get(n5++)).text();
                }
            }
        }
        ArrayList<LyricLine> arrayList2 = new ArrayList<LyricLine>(list.size());
        for (int i3 = 0; i3 < list.size(); ++i3) {
            RawLine rawLine = list.get(i3);
            arrayList2.add(new LyricLine(rawLine.timeMs(), rawLine.text(), (String)(objectArray[i3] == null ? "" : objectArray[i3])));
        }
        return arrayList2;
    }

    private static int a(List<RawLine> list, long l2) {
        int n2;
        if (list.isEmpty()) {
            return -1;
        }
        int n3 = 0;
        int n4 = list.size() - 1;
        while (n3 < n4) {
            n2 = n3 + n4 >>> 1;
            if (list.get(n2).timeMs() < l2) {
                n3 = n2 + 1;
                continue;
            }
            n4 = n2;
        }
        n2 = n3;
        if (n3 > 0) {
            long l3 = Math.abs(list.get(n3).timeMs() - l2);
            long l4 = Math.abs(list.get(n3 - 1).timeMs() - l2);
            if (l4 < l3) {
                n2 = n3 - 1;
            }
        }
        return n2;
    }

    private static long a(String string, String string2, String string3) {
        long l2 = LrcParser.d(string);
        long l3 = LrcParser.d(string2);
        long l4 = 0L;
        if (string3 != null && !string3.isEmpty()) {
            l4 = string3.length() == 1 ? LrcParser.d(string3) * 100L : (string3.length() == 2 ? LrcParser.d(string3) * 10L : LrcParser.d(string3.substring(0, 3)));
        }
        return l2 * 60000L + l3 * 1000L + l4;
    }

    private static long d(String string) {
        try {
            return Long.parseLong(string);
        }
        catch (NumberFormatException numberFormatException) {
            return 0L;
        }
    }

    public static boolean a(String string) {
        if (string == null || string.isBlank()) {
            return false;
        }
        return TIME_TAG.matcher(string).find();
    }

    public static String b(String string) {
        if (string == null) {
            return "";
        }
        String string2 = string.replaceAll("[\uff08(\u3010\\[][^\uff09)\u3011\\]]*[\uff09)\u3011\\]]", " ").replaceAll("\\s+", " ").trim();
        String string3 = string2.toLowerCase(Locale.ROOT);
        for (String string4 : new String[]{"official", "mv", "audio", "lyric", "hd", "hq", "\u5b8c\u6574\u7248", "\u65e0\u635f", "\u9ad8\u97f3\u8d28"}) {
            if (!string3.endsWith(string4)) continue;
            string2 = string2.substring(0, string2.length() - string4.length()).trim();
            string3 = string2.toLowerCase(Locale.ROOT);
        }
        return string2.isEmpty() ? string.trim() : string2;
    }

    private record RawLine(long timeMs, String text) {
    }
}

