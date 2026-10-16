/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.music.nowplaying;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.heypixel.heypixelmod.obsoverlay.music.nowplaying.NowPlayingInfo;
import com.heypixel.heypixelmod.obsoverlay.utils.BackgroundExecutor;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class WindowsSMTCBridge {
    private static final Logger LOGGER = LogManager.getLogger(WindowsSMTCBridge.class);
    private static final String SCRIPT = String.join((CharSequence)"\n", "$ErrorActionPreference='SilentlyContinue'", "[Console]::OutputEncoding=[System.Text.Encoding]::UTF8", "$OutputEncoding=[System.Text.Encoding]::UTF8", "Add-Type -AssemblyName System.Runtime.WindowsRuntime", "$g=([System.WindowsRuntimeSystemExtensions].GetMethods()|Where-Object{$_.Name -eq 'AsTask' -and $_.GetParameters().Count -eq 1 -and $_.GetParameters()[0].ParameterType.Name -eq 'IAsyncOperation`1'})[0]", "function Await($op,$t){ $k=$g.MakeGenericMethod($t); $tk=$k.Invoke($null,@($op)); $tk.Wait(-1)|Out-Null; return $tk.Result }", "$mt=[Windows.Media.Control.GlobalSystemMediaTransportControlsSessionManager,Windows.Media.Control,ContentType=WindowsRuntime]", "$pt=[Windows.Media.Control.GlobalSystemMediaTransportControlsSessionMediaProperties,Windows.Media.Control,ContentType=WindowsRuntime]", "$mgr=$null", "$sentReady=$false", "while($true){", "  if($mgr -eq $null){", "    try{ $mgr=Await ($mt::RequestAsync()) $mt }catch{ $mgr=$null }", "    if($mgr -eq $null){", "      [Console]::Out.WriteLine('{\"state\":\"no-manager\"}'); [Console]::Out.Flush()", "      Start-Sleep -Milliseconds 2000; continue", "    }", "  }", "  if(-not $sentReady){ [Console]::Out.WriteLine('{\"state\":\"ready\"}'); [Console]::Out.Flush(); $sentReady=$true }", "  $o=$null", "  try{", "    $s=$mgr.GetCurrentSession()", "    if($s -ne $null){", "      $p=Await ($s.TryGetMediaPropertiesAsync()) $pt", "      $pi=$s.GetPlaybackInfo()", "      $tl=$s.GetTimelineProperties()", "      $pos=[int]$tl.Position.TotalMilliseconds", "      $playing=($pi.PlaybackStatus -eq 4)", "      if($playing){ $pos=[int]([DateTimeOffset]::Now - $tl.LastUpdatedTime).TotalMilliseconds + $pos }", "      $o=[ordered]@{app=$s.SourceAppUserModelId;title=$p.Title;artist=$p.Artist;album=$p.AlbumTitle;pos=$pos;dur=[int]$tl.EndTime.TotalMilliseconds;playing=$playing}", "    }", "  }catch{ $mgr=$null }", "  if($o -ne $null){ $json=$o|ConvertTo-Json -Compress; $isPlaying=$o.playing } else { $json='{\"state\":\"idle\"}'; $isPlaying=$false }", "  [Console]::Out.WriteLine($json)", "  [Console]::Out.Flush()", "  if($isPlaying){ Start-Sleep -Milliseconds 400 } else { Start-Sleep -Milliseconds 1200 }", "}");
    private final AtomicBoolean running = new AtomicBoolean(false);
    private final AtomicBoolean supported = new AtomicBoolean(true);
    private volatile Process process;
    private volatile Thread readerThread;
    private volatile Thread watchdogThread;
    private volatile NowPlayingInfo latest = NowPlayingInfo.NONE;
    private volatile long lastUpdateAt;
    private volatile boolean ready;
    private volatile String lastError;
    private volatile int restartCount;

    public boolean a() {
        return this.running.get();
    }

    public boolean b() {
        return this.supported.get();
    }

    public boolean c() {
        return this.ready;
    }

    public String d() {
        return this.lastError;
    }

    public NowPlayingInfo e() {
        return this.latest;
    }

    public long f() {
        return this.lastUpdateAt;
    }

    public synchronized void g() {
        if (this.running.get()) {
            return;
        }
        this.running.set(true);
        this.restartCount = 0;
        BackgroundExecutor.a(() -> {
            if (!this.running.get()) {
                return;
            }
            if (!WindowsSMTCBridge.i()) {
                this.supported.set(false);
                this.lastError = "\u672a\u627e\u5230 powershell.exe";
                LOGGER.warn("SMTC \u4e0d\u53ef\u7528: {}", (Object)this.lastError);
                this.running.set(false);
                return;
            }
            if (!this.running.get()) {
                return;
            }
            this.j();
            if (this.running.get()) {
                this.k();
            }
        });
    }

    public synchronized void h() {
        this.running.set(false);
        this.ready = false;
        this.l();
        Thread thread = this.watchdogThread;
        this.watchdogThread = null;
        if (thread != null) {
            thread.interrupt();
        }
    }

    private static boolean i() {
        try {
            Process process = new ProcessBuilder("powershell.exe", "-NoProfile", "-NonInteractive", "-Command", "exit 0").redirectErrorStream(true).start();
            boolean bl = process.waitFor(6L, TimeUnit.SECONDS);
            if (!bl) {
                process.destroyForcibly();
                return false;
            }
            return process.exitValue() == 0;
        }
        catch (Throwable throwable) {
            return false;
        }
    }

    private void j() {
        try {
            Process process;
            String string = Base64.getEncoder().encodeToString(SCRIPT.getBytes(StandardCharsets.UTF_16LE));
            ProcessBuilder processBuilder = new ProcessBuilder("powershell.exe", "-NoProfile", "-NonInteractive", "-ExecutionPolicy", "Bypass", "-EncodedCommand", string);
            processBuilder.redirectErrorStream(false);
            this.process = process = processBuilder.start();
            Thread thread = new Thread(() -> this.a(process), "EixClient-SMTC-Reader");
            thread.setDaemon(true);
            this.readerThread = thread;
            thread.start();
            Thread thread2 = new Thread(() -> this.b(process), "EixClient-SMTC-Err");
            thread2.setDaemon(true);
            thread2.start();
        }
        catch (Throwable throwable) {
            this.lastError = "\u542f\u52a8 SMTC \u6865\u5931\u8d25: " + throwable.getMessage();
            LOGGER.warn(this.lastError, throwable);
            this.supported.set(false);
            this.running.set(false);
        }
    }

    private void a(Process process) {
        block9: {
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));){
                String string;
                while ((string = bufferedReader.readLine()) != null && this.running.get()) {
                    if (this.process != process) {
                        break;
                    }
                    this.a(string);
                }
            }
            catch (Throwable throwable) {
                if (!this.running.get()) break block9;
                this.lastError = "\u8bfb\u53d6 SMTC \u8f93\u51fa\u5931\u8d25: " + throwable.getMessage();
            }
        }
    }

    private void b(Process process) {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getErrorStream(), StandardCharsets.UTF_8));){
            String string;
            for (int i2 = 0; (string = bufferedReader.readLine()) != null && i2 < 40; ++i2) {
                if (string.startsWith("#< CLIXML") || string.isBlank()) continue;
                LOGGER.debug("[SMTC] {}", (Object)string);
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    private void a(String string) {
        String string2 = string.trim();
        if (string2.isEmpty()) {
            return;
        }
        try {
            JsonObject jsonObject = JsonParser.parseString((String)string2).getAsJsonObject();
            if (jsonObject.has("state")) {
                String string3 = jsonObject.get("state").getAsString();
                if ("ready".equals(string3)) {
                    this.ready = true;
                } else if ("no-manager".equals(string3)) {
                    this.supported.set(false);
                    this.lastError = "\u7cfb\u7edf\u5a92\u4f53\u63a7\u5236\u4e0d\u53ef\u7528\uff08SMTC \u521d\u59cb\u5316\u5931\u8d25\uff09";
                }
                if (!"ready".equals(string3)) {
                    this.latest = NowPlayingInfo.NONE;
                    this.lastUpdateAt = System.currentTimeMillis();
                }
                return;
            }
            this.ready = true;
            this.latest = new NowPlayingInfo(WindowsSMTCBridge.a(jsonObject, "app"), WindowsSMTCBridge.a(jsonObject, "title"), WindowsSMTCBridge.a(jsonObject, "artist"), WindowsSMTCBridge.a(jsonObject, "album"), WindowsSMTCBridge.b(jsonObject, "pos"), WindowsSMTCBridge.b(jsonObject, "dur"), jsonObject.has("playing") && jsonObject.get("playing").getAsBoolean(), System.currentTimeMillis(), "SMTC");
            this.lastUpdateAt = System.currentTimeMillis();
        }
        catch (Throwable throwable) {
            LOGGER.debug("\u89e3\u6790 SMTC \u8f93\u51fa\u5931\u8d25: {}", (Object)string2);
        }
    }

    private static String a(JsonObject jsonObject, String string) {
        try {
            return jsonObject.has(string) && !jsonObject.get(string).isJsonNull() ? jsonObject.get(string).getAsString() : "";
        }
        catch (Throwable throwable) {
            return "";
        }
    }

    private static long b(JsonObject jsonObject, String string) {
        try {
            return jsonObject.has(string) && !jsonObject.get(string).isJsonNull() ? jsonObject.get(string).getAsLong() : -1L;
        }
        catch (Throwable throwable) {
            return -1L;
        }
    }

    private void k() {
        Thread thread = new Thread(() -> {
            while (this.running.get()) {
                try {
                    Thread.sleep(2000L);
                }
                catch (InterruptedException interruptedException) {
                    return;
                }
                if (!this.running.get()) {
                    return;
                }
                Process process = this.process;
                if (process != null && process.isAlive()) continue;
                if (this.restartCount >= 5) {
                    this.supported.set(false);
                    this.lastError = "SMTC \u6865\u53cd\u590d\u9000\u51fa\uff0c\u5df2\u505c\u6b62\u91cd\u8bd5";
                    this.running.set(false);
                    return;
                }
                ++this.restartCount;
                this.l();
                try {
                    Thread.sleep(2200L);
                }
                catch (InterruptedException interruptedException) {
                    return;
                }
                if (!this.running.get()) continue;
                this.j();
            }
        }, "EixClient-SMTC-Watchdog");
        thread.setDaemon(true);
        this.watchdogThread = thread;
        thread.start();
    }

    private void l() {
        Process process = this.process;
        this.process = null;
        if (process == null) {
            return;
        }
        try {
            process.destroy();
        }
        catch (Throwable throwable) {
            try {
                process.destroyForcibly();
            }
            catch (Throwable throwable2) {
                // empty catch block
            }
            return;
        }
        BackgroundExecutor.a(() -> {
            try {
                if (process.isAlive()) {
                    process.destroyForcibly();
                }
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }, 1500L, TimeUnit.MILLISECONDS);
    }
}

