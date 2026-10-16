/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils.e.c;

import io.github.humbleui.skija.ColorType;
import io.github.humbleui.skija.DirectContext;
import io.github.humbleui.skija.Image;
import io.github.humbleui.skija.SurfaceOrigin;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import org.apache.commons.io.IOUtils;

public class SkiaImageCache {
    private final Map<String, Image> a = new HashMap<String, Image>();
    private final Map<Integer, Image> b = new HashMap<Integer, Image>();

    public boolean a(int n2, float f2, float f3, SurfaceOrigin surfaceOrigin) {
        if (!this.b.containsKey(n2)) {
            Image image = Image.adoptGLTextureFrom((DirectContext)com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.a.a.b(), (int)n2, (int)3553, (int)((int)f2), (int)((int)f3), (int)32856, (SurfaceOrigin)surfaceOrigin, (ColorType)ColorType.RGBA_8888);
            this.b.put(n2, image);
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean a(ResourceLocation resourceLocation) {
        if (this.a.containsKey(resourceLocation.m_135815_())) return true;
        ResourceManager resourceManager = Minecraft.m_91087_().m_91098_();
        try {
            Resource resource = resourceManager.m_215593_(resourceLocation);
            try (InputStream inputStream = resource.m_215507_();){
                byte[] byArray = inputStream.readAllBytes();
                Image image = Image.makeDeferredFromEncodedBytes((byte[])byArray);
                if (image == null) {
                    boolean bl = false;
                    return bl;
                }
                this.a.put(resourceLocation.m_135815_(), image);
                boolean bl = true;
                return bl;
            }
            catch (IOException iOException) {
                iOException.printStackTrace();
                return true;
            }
        }
        catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        return true;
    }

    public boolean a(String string) {
        if (!this.a.containsKey(string)) {
            Optional<byte[]> optional = com.heypixel.heypixelmod.obsoverlay.utils.WorldBlockUtils.d.a.a(string);
            if (optional.isPresent()) {
                this.a.put(string, Image.makeDeferredFromEncodedBytes((byte[])optional.get()));
                return true;
            }
            return false;
        }
        return true;
    }

    public boolean a(File file) {
        if (!this.a.containsKey(file.getName())) {
            try {
                byte[] byArray = IOUtils.toByteArray((InputStream)new FileInputStream(file));
                this.a.put(file.getName(), Image.makeDeferredFromEncodedBytes((byte[])byArray));
                return true;
            }
            catch (IOException iOException) {
                iOException.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public Image b(String string) {
        if (this.a.containsKey(string)) {
            return this.a.get(string);
        }
        return null;
    }

    public Image a(int n2) {
        if (this.b.containsKey(n2)) {
            return this.b.get(n2);
        }
        return null;
    }
}

