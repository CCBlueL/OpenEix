/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.music;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FilterInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Locale;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public final class AudioFormatSupport {
    private static final String MP3_READER = "javazoom.spi.mpeg.sampled.file.MpegAudioFileReader";
    private static final String VORBIS_READER = "javazoom.spi.vorbis.sampled.file.VorbisAudioFileReader";
    private static Boolean mp3Available;
    private static Boolean vorbisAvailable;

    private AudioFormatSupport() {
    }

    public static boolean a(File file) {
        return file != null && AudioFormat.a(file.getName());
    }

    public static boolean a(String string) {
        String string2 = string.toLowerCase(Locale.ROOT);
        return string2.endsWith(".mp3") || string2.endsWith(".wav") || string2.endsWith(".wave") || string2.endsWith(".ogg") || string2.endsWith(".oga") || string2.endsWith(".aif") || string2.endsWith(".aiff") || string2.endsWith(".au");
    }

    public static boolean a() {
        if (mp3Available == null) {
            mp3Available = AudioFormat.b(MP3_READER);
        }
        return mp3Available;
    }

    public static boolean b() {
        if (vorbisAvailable == null) {
            vorbisAvailable = AudioFormat.b(VORBIS_READER);
        }
        return vorbisAvailable;
    }

    private static boolean b(String string) {
        try {
            Class.forName(string);
            return true;
        }
        catch (Throwable throwable) {
            return false;
        }
    }

    private static Object a(String string, String string2, File file) {
        try {
            Class<?> clazz = Class.forName(string);
            Object obj = clazz.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            Method method = clazz.getMethod(string2, File.class);
            return method.invoke(obj, file);
        }
        catch (Throwable throwable) {
            return null;
        }
    }

    public static AudioFileFormat b(File file) {
        Object object;
        String string = file.getName().toLowerCase(Locale.ROOT);
        if (string.endsWith(".mp3")) {
            Object object2 = AudioFormat.a(MP3_READER, "getAudioFileFormat", file);
            if (object2 instanceof AudioFileFormat) {
                AudioFileFormat audioFileFormat = (AudioFileFormat)object2;
                return audioFileFormat;
            }
        } else if ((string.endsWith(".ogg") || string.endsWith(".oga")) && (object = AudioFormat.a(VORBIS_READER, "getAudioFileFormat", file)) instanceof AudioFileFormat) {
            AudioFileFormat audioFileFormat = (AudioFileFormat)object;
            return audioFileFormat;
        }
        try {
            return AudioSystem.getAudioFileFormat(file);
        }
        catch (Throwable throwable) {
            return null;
        }
    }

    public static AudioInputStream c(File file) throws Exception {
        AudioInputStream audioInputStream = AudioFormat.d(file);
        return AudioFormat.a(audioInputStream);
    }

    public static AudioInputStream d(File file) throws Exception {
        String string = file.getName().toLowerCase(Locale.ROOT);
        if (string.endsWith(".mp3")) {
            Object object = AudioFormat.a(MP3_READER, "getAudioInputStream", file);
            if (object instanceof AudioInputStream) {
                AudioInputStream audioInputStream = (AudioInputStream)object;
                return audioInputStream;
            }
            throw new UnsupportedOperationException("MP3 \u89e3\u7801\u5668\u4e0d\u53ef\u7528\uff08mp3spi \u672a\u52a0\u8f7d\uff09");
        }
        if (string.endsWith(".ogg") || string.endsWith(".oga")) {
            Object object = AudioFormat.a(VORBIS_READER, "getAudioInputStream", file);
            if (object instanceof AudioInputStream) {
                AudioInputStream audioInputStream = (AudioInputStream)object;
                return audioInputStream;
            }
            throw new UnsupportedOperationException("OGG \u89e3\u7801\u5668\u4e0d\u53ef\u7528\uff08vorbisspi \u672a\u52a0\u8f7d\uff09");
        }
        return AudioSystem.getAudioInputStream(file);
    }

    public static AudioInputStream a(InputStream inputStream, String string) throws Exception {
        return AudioFormat.a(AudioFormat.b(inputStream, string));
    }

    public static AudioInputStream b(InputStream inputStream, String string) throws Exception {
        BufferedInputStream bufferedInputStream;
        Object object;
        if (inputStream instanceof BufferedInputStream) {
            object = (BufferedInputStream)inputStream;
            bufferedInputStream = object;
        } else {
            bufferedInputStream = new BufferedInputStream(inputStream, 65536);
        }
        BufferedInputStream bufferedInputStream2 = bufferedInputStream;
        object = AudioFormat.c(bufferedInputStream2, string);
        switch (1.$SwitchMap$com$heypixel$heypixelmod$obsoverlay$music$AudioSupport$StreamKind[((Enum)object).ordinal()]) {
            case 1: {
                Object object2 = AudioFormat.a(MP3_READER, "getAudioInputStream", bufferedInputStream2);
                if (object2 instanceof AudioInputStream) {
                    AudioInputStream audioInputStream = (AudioInputStream)object2;
                    return audioInputStream;
                }
                throw new UnsupportedOperationException("MP3 \u89e3\u7801\u5668\u4e0d\u53ef\u7528\uff08mp3spi \u672a\u52a0\u8f7d\uff09");
            }
            case 2: {
                Object object3 = AudioFormat.a(VORBIS_READER, "getAudioInputStream", bufferedInputStream2);
                if (object3 instanceof AudioInputStream) {
                    AudioInputStream audioInputStream = (AudioInputStream)object3;
                    return audioInputStream;
                }
                throw new UnsupportedOperationException("OGG \u89e3\u7801\u5668\u4e0d\u53ef\u7528\uff08vorbisspi \u672a\u52a0\u8f7d\uff09");
            }
            case 3: {
                throw new UnsupportedOperationException("FLAC \u65e0\u635f\u6d41\u65e0\u6cd5\u76f4\u63a5\u64ad\u653e\uff0c\u8bf7\u4e0b\u8f7d\u540e\u518d\u64ad\uff08\u6216\u7528 standard/exhigh \u6863\u4f4d\uff09");
            }
            case 4: {
                throw new UnsupportedOperationException("MP4/AAC \u6d41\u65e0\u6cd5\u76f4\u63a5\u64ad\u653e\uff0c\u8bf7\u4e0b\u8f7d\u540e\u518d\u64ad");
            }
        }
        return AudioSystem.getAudioInputStream(bufferedInputStream2);
    }

    public static AudioFormatSupport c(InputStream inputStream, String string) {
        Object object;
        try {
            if (inputStream instanceof BufferedInputStream) {
                object = (BufferedInputStream)inputStream;
                ((BufferedInputStream)object).mark(16);
                byte[] byArray = new byte[12];
                int n2 = ((FilterInputStream)object).read(byArray);
                ((BufferedInputStream)object).reset();
                if (n2 >= 4) {
                    if (byArray[0] == 73 && byArray[1] == 68 && byArray[2] == 51) {
                        return AudioFormat.MP3;
                    }
                    if ((byArray[0] & 0xFF) == 255 && (byArray[1] & 0xE0) == 224) {
                        return AudioFormat.MP3;
                    }
                    if (byArray[0] == 79 && byArray[1] == 103 && byArray[2] == 103 && byArray[3] == 83) {
                        return AudioFormat.OGG;
                    }
                    if (byArray[0] == 82 && byArray[1] == 73 && byArray[2] == 70 && byArray[3] == 70) {
                        return AudioFormat.WAV;
                    }
                    if (byArray[0] == 70 && byArray[1] == 79 && byArray[2] == 82 && byArray[3] == 77) {
                        return AudioFormat.AIFF;
                    }
                    if (byArray[0] == 102 && byArray[1] == 76 && byArray[2] == 97 && byArray[3] == 67) {
                        return AudioFormat.FLAC;
                    }
                    if (n2 >= 8 && byArray[4] == 102 && byArray[5] == 116 && byArray[6] == 121 && byArray[7] == 112) {
                        return AudioFormat.MP4;
                    }
                    if (byArray[0] == 46 && byArray[1] == 115 && byArray[2] == 110 && byArray[3] == 100) {
                        return AudioFormat.AU;
                    }
                }
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        if (string != null) {
            object = string.toLowerCase(Locale.ROOT);
            int n3 = ((String)object).indexOf(63);
            if (n3 >= 0) {
                object = ((String)object).substring(0, n3);
            }
            if (((String)object).endsWith(".mp3")) {
                return AudioFormat.MP3;
            }
            if (((String)object).endsWith(".ogg") || ((String)object).endsWith(".oga")) {
                return AudioFormat.OGG;
            }
            if (((String)object).endsWith(".wav")) {
                return AudioFormat.WAV;
            }
            if (((String)object).endsWith(".aiff") || ((String)object).endsWith(".aif")) {
                return AudioFormat.AIFF;
            }
            if (((String)object).endsWith(".flac")) {
                return AudioFormat.FLAC;
            }
            if (((String)object).endsWith(".mp4") || ((String)object).endsWith(".m4a")) {
                return AudioFormat.MP4;
            }
        }
        return AudioFormat.UNKNOWN;
    }

    public static boolean a(AudioFormatSupport a2) {
        return switch (a2) {
            case AudioFormat.MP3 -> AudioFormat.a();
            case AudioFormat.OGG -> AudioFormat.b();
            case AudioFormat.WAV, AudioFormat.AIFF, AudioFormat.AU -> true;
            default -> false;
        };
    }

    private static Object a(String string, String string2, InputStream inputStream) {
        try {
            Class<?> clazz = Class.forName(string);
            Object obj = clazz.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            Method method = clazz.getMethod(string2, InputStream.class);
            return method.invoke(obj, inputStream);
        }
        catch (Throwable throwable) {
            return null;
        }
    }

    private static AudioInputStream a(AudioInputStream audioInputStream) {
        int n2;
        AudioFormat audioFormat = audioInputStream.getFormat();
        if (audioFormat.getEncoding() == AudioFormat.Encoding.PCM_SIGNED && audioFormat.getSampleSizeInBits() == 16 && !audioFormat.isBigEndian()) {
            return audioInputStream;
        }
        float f2 = audioFormat.getSampleRate() > 0.0f ? audioFormat.getSampleRate() : 44100.0f;
        AudioFormat audioFormat2 = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, f2, 16, n2 = Math.max(1, audioFormat.getChannels()), n2 * 2, f2, false);
        if (AudioSystem.isConversionSupported(audioFormat2, audioFormat)) {
            try {
                return AudioSystem.getAudioInputStream(audioFormat2, audioInputStream);
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        return audioInputStream;
    }

    public static long a(AudioFileFormat audioFileFormat) {
        if (audioFileFormat == null) {
            return -1L;
        }
        try {
            Number number;
            long l2;
            Object object = audioFileFormat.properties().get("duration");
            if (object instanceof Number && (l2 = (number = (Number)object).longValue()) > 0L) {
                return l2 / 1000L;
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        try {
            long l3 = audioFileFormat.getFrameLength();
            float f2 = audioFileFormat.getFormat().getSampleRate();
            if (l3 > 0L && f2 > 0.0f) {
                return (long)((double)((float)l3 / f2) * 1000.0);
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        return -1L;
    }

    public static String a(AudioFileFormat audioFileFormat, String string) {
        if (audioFileFormat == null) {
            return null;
        }
        try {
            Object object = audioFileFormat.properties().get(string);
            if (object == null) {
                return null;
            }
            String string2 = String.valueOf(object).trim();
            return string2.isEmpty() ? null : string2;
        }
        catch (Throwable throwable) {
            return null;
        }
    }

    public static enum AudioFormat {
        MP3,
        OGG,
        WAV,
        AIFF,
        AU,
        FLAC,
        MP4,
        UNKNOWN;

    }
}

