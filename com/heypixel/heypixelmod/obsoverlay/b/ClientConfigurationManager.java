/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.b;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.heypixel.heypixelmod.obsoverlay.b.ClientConfig;
import com.heypixel.heypixelmod.obsoverlay.d.a.ModeSetting;
import com.heypixel.heypixelmod.obsoverlay.d.SettingValue;
import com.heypixel.heypixelmod.obsoverlay.d.SettingValueType;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.Scoreboard;
import com.heypixel.heypixelmod.obsoverlay.utils.EntityNameFilter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientConfigurationManager {
    public static final Logger logger = LogManager.getLogger(b.class);
    public static final File clientFolder = new File(System.getenv("APPDATA"), "EixClient");
    public static final File configFolder = new File(Minecraft.m_91087_().f_91069_, "config/Naven");
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final File configFile;
    private a config;

    public ClientConfigurationManager() {
        if (!clientFolder.exists()) {
            clientFolder.mkdirs();
        }
        if (!configFolder.exists()) {
            configFolder.mkdirs();
        }
        this.configFile = new File(clientFolder, "config.json");
        this.config = new a();
    }

    public a a() {
        return this.config;
    }

    public void b() {
        if (this.configFile.exists()) {
            try (InputStreamReader inputStreamReader = new InputStreamReader(Files.newInputStream(this.configFile.toPath(), new OpenOption[0]), StandardCharsets.UTF_8);){
                this.config = (a)this.gson.fromJson((Reader)inputStreamReader, a.class);
                if (this.config == null) {
                    this.config = new a();
                }
                logger.info("Loaded config from JSON.");
            }
            catch (Exception exception) {
                logger.error("Failed to load JSON config!", (Throwable)exception);
                this.config = new a();
            }
        } else {
            logger.info("No config found. Loading defaults.");
        }
        this.e();
    }

    public void c() {
        this.f();
        try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(Files.newOutputStream(this.configFile.toPath(), new OpenOption[0]), StandardCharsets.UTF_8);){
            this.gson.toJson((Object)this.config, (Appendable)outputStreamWriter);
            logger.info("Saved config to JSON.");
        }
        catch (Exception exception) {
            logger.error("Failed to save JSON config!", (Throwable)exception);
        }
    }

    public void a(String string) {
        try {
            this.config = (a)this.gson.fromJson(string, a.class);
            if (this.config == null) {
                this.config = new a();
            }
            this.e();
            logger.info("Loaded config from Cloud String.");
        }
        catch (Exception exception) {
            logger.error("Failed to load Cloud config!", (Throwable)exception);
        }
    }

    public String d() {
        this.f();
        return this.gson.toJson((Object)this.config);
    }

    public void b(String string) throws IOException {
        this.f();
        File file = new File(configFolder, string + ".json");
        try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(Files.newOutputStream(file.toPath(), new OpenOption[0]), StandardCharsets.UTF_8);){
            this.gson.toJson((Object)this.config, (Appendable)outputStreamWriter);
            logger.info("Saved config to: " + file.getAbsolutePath());
        }
    }

    public void c(String string) throws IOException {
        File file = new File(configFolder, string + ".json");
        if (!file.exists()) {
            throw new FileNotFoundException("\u914d\u7f6e\u6587\u4ef6\u4e0d\u5b58\u5728: " + string);
        }
        try (InputStreamReader inputStreamReader = new InputStreamReader(Files.newInputStream(file.toPath(), new OpenOption[0]), StandardCharsets.UTF_8);){
            this.config = (a)this.gson.fromJson((Reader)inputStreamReader, a.class);
            if (this.config == null) {
                this.config = new a();
            }
            this.e();
            logger.info("Loaded config from: " + file.getAbsolutePath());
        }
    }

    private void e() {
        com.heypixel.heypixelmod.obsoverlay.modules.c c2 = com.heypixel.heypixelmod.obsoverlay.a.a().g();
        if (this.config == null) {
            this.config = new a();
        }
        if (this.config.modules == null) {
            this.config.modules = new HashMap<String, a.b>();
        }
        if (this.config.friends == null) {
            this.config.friends = new ArrayList<String>();
        }
        if (this.config.gui == null) {
            this.config.gui = new a.a();
        }
        for (Map.Entry<String, a.b> object : this.config.modules.entrySet()) {
            try {
                com.heypixel.heypixelmod.obsoverlay.modules.b b2 = c2.d(object.getKey());
                a.b b3 = object.getValue();
                if (b2 == null || b3 == null) continue;
                b2.b(b3.key);
                b2.a(b3.enabled);
                if (b3.values == null) continue;
                for (Map.Entry<String, Object> entry : b3.values.entrySet()) {
                    c c3 = com.heypixel.heypixelmod.obsoverlay.a.a().d().a(b2, entry.getKey());
                    if (c3 == null && "Scoreboard".equals(b2.b()) && "ArrayList Font".equals(entry.getKey())) {
                        c3 = com.heypixel.heypixelmod.obsoverlay.a.a().d().a(b2, "Font");
                    }
                    if (c3 != null && b2 instanceof x && c3.a() == f.e || c3 == null) continue;
                    this.a(c3, entry.getValue());
                }
            }
            catch (Exception exception) {
                logger.warn("Module or value not found: " + object.getKey());
            }
        }
        m.a().clear();
        for (String string : this.config.friends) {
            m.b(string);
        }
        com.heypixel.heypixelmod.obsoverlay.c.a.a = this.config.gui.x;
        com.heypixel.heypixelmod.obsoverlay.c.a.b = this.config.gui.y;
        com.heypixel.heypixelmod.obsoverlay.c.a.c = this.config.gui.width;
        com.heypixel.heypixelmod.obsoverlay.c.a.d = this.config.gui.height;
    }

    private void a(c c2, Object object) {
        if (object == null) {
            return;
        }
        switch (c2.a()) {
            case a: {
                if (!(object instanceof Boolean)) break;
                c2.b().a((Boolean)object);
                break;
            }
            case b: {
                if (!(object instanceof Number)) break;
                c2.c().a(((Number)object).floatValue());
                break;
            }
            case d: {
                if (!(object instanceof String)) break;
                c2.d().a((String)object);
                break;
            }
            case c: {
                if (!(object instanceof Number)) break;
                int n2 = ((Number)object).intValue();
                e e2 = c2.e();
                if (n2 < 0 || n2 >= e2.m().length) break;
                e2.a(n2);
                break;
            }
            case e: {
                if (!(object instanceof Map)) break;
                Map map = (Map)object;
                com.heypixel.heypixelmod.obsoverlay.d.a.b b2 = c2.f();
                if (map.containsKey("x") && map.get("x") instanceof Number) {
                    b2.a(((Number)map.get("x")).floatValue());
                }
                if (!map.containsKey("y") || !(map.get("y") instanceof Number)) break;
                b2.b(((Number)map.get("y")).floatValue());
                break;
            }
            case f: {
                if (object instanceof Number) {
                    c2.g().a(((Number)object).intValue());
                    break;
                }
                if (!(object instanceof String)) break;
                String string = (String)object;
                try {
                    c2.g().a(Integer.parseInt(string.trim()));
                }
                catch (NumberFormatException numberFormatException) {}
                break;
            }
        }
    }

    private void f() {
        com.heypixel.heypixelmod.obsoverlay.modules.c c2 = com.heypixel.heypixelmod.obsoverlay.a.a().g();
        this.config.modules.clear();
        for (com.heypixel.heypixelmod.obsoverlay.modules.b b2 : c2.a()) {
            a.b b3 = new a.b();
            b3.key = b2.o();
            b3.enabled = b2.m();
            for (c c3 : com.heypixel.heypixelmod.obsoverlay.a.a().d().a()) {
                if (c3.i() != b2) continue;
                switch (c3.a()) {
                    case a: {
                        b3.values.put(c3.j(), c3.b().m());
                        break;
                    }
                    case b: {
                        b3.values.put(c3.j(), Float.valueOf(c3.c().q()));
                        break;
                    }
                    case d: {
                        b3.values.put(c3.j(), c3.d().n());
                        break;
                    }
                    case c: {
                        b3.values.put(c3.j(), c3.e().o());
                        break;
                    }
                    case e: {
                        if (b2 instanceof x) break;
                        HashMap<String, Float> hashMap = new HashMap<String, Float>();
                        hashMap.put("x", Float.valueOf(c3.f().n()));
                        hashMap.put("y", Float.valueOf(c3.f().o()));
                        b3.values.put(c3.j(), hashMap);
                        break;
                    }
                    case f: {
                        b3.values.put(c3.j(), c3.g().m());
                        break;
                    }
                }
            }
            this.config.modules.put(b2.b(), b3);
        }
        this.config.friends = new ArrayList<String>(m.a());
        this.config.gui.x = com.heypixel.heypixelmod.obsoverlay.c.a.a;
        this.config.gui.y = com.heypixel.heypixelmod.obsoverlay.c.a.b;
        this.config.gui.width = com.heypixel.heypixelmod.obsoverlay.c.a.c;
        this.config.gui.height = com.heypixel.heypixelmod.obsoverlay.c.a.d;
    }
}

