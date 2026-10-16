/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.music;

import java.io.File;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.SourceDataLine;

public class MusicPlaybackEngine {
    private static final int READ_BUFFER = 8192;
    private volatile PlaybackState state = PlaybackState.STOPPED;
    private volatile File currentFile;
    private volatile String currentLabel = "";
    private volatile MusicPlaybackEngine streamOpener;
    private volatile boolean networkStream;
    private volatile Thread worker;
    private volatile SourceDataLine line;
    private volatile AudioInputStream stream;
    private volatile boolean abort;
    private volatile boolean paused;
    private volatile float volume = 0.7f;
    private volatile long pendingSeek = -1L;
    private volatile long positionOffset = 0L;
    private volatile long totalFrames = -1L;
    private volatile float sampleRate = 44100.0f;
    private volatile int frameSize = 4;
    private volatile long durationHintMillis = -1L;
    private volatile String lastError;
    private volatile Runnable endListener;
    private volatile long startedAt = 0L;

    public void a(Runnable runnable) {
        this.endListener = runnable;
    }

    public String a() {
        return this.lastError;
    }

    public void b() {
        this.lastError = null;
    }

    public PlaybackState c() {
        return this.state;
    }

    public boolean d() {
        return this.state != PlaybackState.STOPPED;
    }

    public boolean e() {
        return this.state == PlaybackState.PAUSED;
    }

    public File f() {
        return this.currentFile;
    }

    public String g() {
        return this.currentLabel;
    }

    public boolean h() {
        return this.networkStream;
    }

    public float i() {
        return this.volume;
    }

    public void a(float f2) {
        this.volume = Math.max(0.0f, Math.min(1.0f, f2));
        this.q();
    }

    public long j() {
        if (this.durationHintMillis > 0L) {
            return this.durationHintMillis;
        }
        if (this.totalFrames > 0L && this.sampleRate > 0.0f) {
            return (long)((double)((float)this.totalFrames / this.sampleRate) * 1000.0);
        }
        return -1L;
    }

    public long k() {
        SourceDataLine sourceDataLine = this.line;
        if (sourceDataLine == null) {
            return 0L;
        }
        long l2 = this.positionOffset + sourceDataLine.getLongFramePosition();
        if (l2 < 0L) {
            l2 = 0L;
        }
        if (this.sampleRate <= 0.0f) {
            return 0L;
        }
        return (long)((double)((float)l2 / this.sampleRate) * 1000.0);
    }

    public synchronized void a(File file, long l2) {
        if (file == null || !file.isFile()) {
            this.p();
            this.lastError = "\u6587\u4ef6\u4e0d\u5b58\u5728";
            return;
        }
        com.heypixel.heypixelmod.obsoverlay.music.MusicPlaybackEngine.PlaybackState(() -> com.heypixel.heypixelmod.obsoverlay.music.AudioFormatSupport.c(file), file.getName(), l2, false, file);
    }

    public synchronized void a(MusicPlaybackEngine b2, String string, long l2) {
        if (b2 == null) {
            this.p();
            this.lastError = "\u65e0\u6548\u7684\u97f3\u9891\u6e90";
            return;
        }
        this.a(b2, string == null ? "\u7f51\u7edc\u97f3\u9891" : string, l2, true, null);
    }

    private void a(MusicPlaybackEngine b2, String string, long l2, boolean bl, File file) {
        this.p();
        this.currentFile = file;
        this.currentLabel = string;
        this.streamOpener = b2;
        this.networkStream = bl;
        this.durationHintMillis = l2;
        this.abort = false;
        this.paused = false;
        this.pendingSeek = -1L;
        this.positionOffset = 0L;
        this.totalFrames = -1L;
        this.state = PlaybackState.PLAYING;
        this.startedAt = System.currentTimeMillis();
        Thread thread = new Thread(() -> this.a(b2), "EixClient-MusicPlayer");
        thread.setDaemon(true);
        this.worker = thread;
        thread.start();
    }

    public synchronized void l() {
        if (this.state == PlaybackState.PLAYING) {
            this.m();
        } else if (this.state == PlaybackState.PAUSED) {
            this.n();
        }
    }

    public synchronized void m() {
        if (this.state == PlaybackState.PLAYING) {
            this.paused = true;
            this.state = PlaybackState.PAUSED;
        }
    }

    public synchronized void n() {
        if (this.state == PlaybackState.PAUSED) {
            this.paused = false;
            this.state = PlaybackState.PLAYING;
        }
    }

    public synchronized void o() {
        this.p();
    }

    private void p() {
        Thread thread;
        this.abort = true;
        SourceDataLine sourceDataLine = this.line;
        if (sourceDataLine != null) {
            try {
                sourceDataLine.flush();
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        if ((thread = this.worker) != null && thread.isAlive() && thread != Thread.currentThread()) {
            try {
                thread.join(1200L);
            }
            catch (InterruptedException interruptedException) {
                Thread.currentThread().interrupt();
            }
        }
        this.worker = null;
        this.state = PlaybackState.STOPPED;
        this.paused = false;
        this.pendingSeek = -1L;
        this.streamOpener = null;
        this.networkStream = false;
    }

    public void a(long l2) {
        long l3;
        if (this.sampleRate <= 0.0f) {
            return;
        }
        long l4 = (long)((double)l2 / 1000.0 * (double)this.sampleRate);
        if (l4 < 0L) {
            l4 = 0L;
        }
        if ((l3 = this.totalFrames) > 0L && l4 > l3) {
            l4 = l3;
        }
        this.pendingSeek = l4;
    }

    public void b(long l2) {
        this.a(this.k() + l2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void a(MusicPlaybackEngine b2) {
        AudioInputStream audioInputStream = null;
        SourceDataLine sourceDataLine = null;
        boolean bl = false;
        try {
            audioInputStream = b2.open();
            AudioFormat audioFormat = audioInputStream.getFormat();
            this.sampleRate = audioFormat.getSampleRate() > 0.0f ? audioFormat.getSampleRate() : 44100.0f;
            this.frameSize = Math.max(1, audioFormat.getFrameSize());
            this.totalFrames = audioInputStream.getFrameLength();
            sourceDataLine = AudioSystem.getSourceDataLine(audioFormat);
            int n2 = Math.max(8192, (int)(this.sampleRate * (float)this.frameSize * 0.2f));
            n2 -= n2 % this.frameSize;
            sourceDataLine.open(audioFormat, n2);
            this.stream = audioInputStream;
            this.line = sourceDataLine;
            this.a(sourceDataLine);
            sourceDataLine.start();
            int n3 = Math.max(8192, this.frameSize * 256);
            byte[] byArray = new byte[n3 - n3 % this.frameSize];
            while (!this.abort) {
                long l2;
                if (this.paused) {
                    if (sourceDataLine.isRunning()) {
                        sourceDataLine.stop();
                    }
                    AudioStreamOpener.c(15L);
                    continue;
                }
                if (!sourceDataLine.isRunning()) {
                    sourceDataLine.start();
                }
                if ((l2 = this.pendingSeek) >= 0L) {
                    this.pendingSeek = -1L;
                    this.a(l2, sourceDataLine);
                    continue;
                }
                int n4 = audioInputStream.read(byArray, 0, byArray.length);
                if (n4 < 0) {
                    bl = true;
                    break;
                }
                if (n4 == 0) continue;
                sourceDataLine.write(byArray, 0, n4);
            }
            if (!this.abort) {
                try {
                    sourceDataLine.drain();
                }
                catch (Throwable throwable) {}
            } else {
                try {
                    sourceDataLine.flush();
                }
                catch (Throwable throwable) {
                    // empty catch block
                }
            }
            this.stream = null;
            this.line = null;
        }
        catch (Throwable throwable) {
            try {
                if (!this.abort) {
                    this.lastError = throwable.getMessage() == null ? throwable.getClass().getSimpleName() : throwable.getMessage();
                }
            }
            catch (Throwable throwable2) {
                throw throwable2;
            }
            finally {
                this.stream = null;
                this.line = null;
                AudioStreamOpener.a(sourceDataLine);
                AudioStreamOpener.a(audioInputStream);
                boolean bl2 = this.abort;
                if (!bl2) {
                    this.state = PlaybackState.STOPPED;
                    this.paused = false;
                }
                Runnable runnable = this.endListener;
                if (bl && !bl2 && runnable != null) {
                    try {
                        runnable.run();
                    }
                    catch (Throwable throwable3) {}
                }
            }
        }
        AudioStreamOpener.a(sourceDataLine);
        AudioStreamOpener.a(audioInputStream);
        boolean bl3 = this.abort;
        if (!bl3) {
            this.state = PlaybackState.STOPPED;
            this.paused = false;
        }
        Runnable runnable = this.endListener;
        if (bl && !bl3 && runnable != null) {
            try {
                runnable.run();
            }
            catch (Throwable throwable) {}
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void a(long l2, SourceDataLine sourceDataLine) {
        try {
            long l3;
            sourceDataLine.stop();
            sourceDataLine.flush();
            AudioInputStream audioInputStream = this.stream;
            if (audioInputStream == null) {
                return;
            }
            long l4 = this.positionOffset + sourceDataLine.getLongFramePosition();
            if (l2 < l4) {
                AudioInputStream audioInputStream2;
                AudioStreamOpener.a(audioInputStream);
                AudioStreamOpener b2 = this.streamOpener;
                if (b2 == null) {
                    return;
                }
                this.stream = audioInputStream2 = b2.open();
                audioInputStream = audioInputStream2;
                l4 = 0L;
            }
            for (long i2 = l2 - l4; i2 > 0L && !this.abort && (l3 = audioInputStream.skip(i2)) > 0L; i2 -= l3) {
            }
            this.positionOffset = l2 - sourceDataLine.getLongFramePosition();
            if (this.positionOffset >= 0L) return;
            this.positionOffset = 0L;
            return;
        }
        catch (Throwable throwable) {
            this.lastError = "\u8df3\u8f6c\u5931\u8d25: " + throwable.getMessage();
            return;
        }
        finally {
            if (!this.paused) {
                try {
                    sourceDataLine.start();
                }
                catch (Throwable throwable) {}
            }
        }
    }

    private void q() {
        SourceDataLine sourceDataLine = this.line;
        if (sourceDataLine != null) {
            this.a(sourceDataLine);
        }
    }

    private void a(SourceDataLine sourceDataLine) {
        try {
            if (!sourceDataLine.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                return;
            }
            FloatControl floatControl = (FloatControl)sourceDataLine.getControl(FloatControl.Type.MASTER_GAIN);
            floatControl.setValue(AudioStreamOpener.a(floatControl, this.volume));
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    private static float a(FloatControl floatControl, float f2) {
        if (f2 <= 1.0E-4f) {
            return floatControl.getMinimum();
        }
        float f3 = (float)(20.0 * Math.log10(f2));
        return Math.max(floatControl.getMinimum(), Math.min(floatControl.getMaximum(), f3));
    }

    private static void c(long l2) {
        try {
            Thread.sleep(l2);
        }
        catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
        }
    }

    private static void a(AutoCloseable autoCloseable) {
        if (autoCloseable == null) {
            return;
        }
        try {
            autoCloseable.close();
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    public static enum PlaybackState {
        STOPPED,
        PLAYING,
        PAUSED;

    }

    public static interface AudioStreamOpener {
        public AudioInputStream open() throws Exception;
    }
}

