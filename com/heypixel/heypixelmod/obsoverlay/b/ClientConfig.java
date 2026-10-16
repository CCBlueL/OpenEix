/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.b;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientConfig {
    public Map<String, ClientConfigurationManager> modules = new HashMap<String, ModuleSettings>();
    public List<String> friends = new ArrayList<String>();
    public List<String> killSays = new ArrayList<String>();
    public List<String> spammerMessages = new ArrayList<String>();
    public ProxySettings proxy = new ProxySettings();
    public ClientConfig gui = new GuiLayoutSettings();

    public static class ProxySettings {
        public String host;
        public int port;
    }

    public static class GuiLayoutSettings {
        public float x = 0.0f;
        public float y = 0.0f;
        public float width = 600.0f;
        public float height = 400.0f;
    }

    public static class ModuleSettings {
        public int key;
        public boolean enabled;
        public Map<String, Object> values = new HashMap<String, Object>();
    }
}

