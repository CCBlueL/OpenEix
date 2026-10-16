/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils.e.d;

import io.github.humbleui.skija.Data;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public class ResourceStreamLoader {
    public static Optional<byte[]> a(String string) {
        Optional<byte[]> optional;
        block8: {
            InputStream inputStream = ResourceStreamLoader.c(string);
            try {
                optional = Optional.of(inputStream.readAllBytes());
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
                catch (IOException iOException) {
                    return Optional.empty();
                }
            }
            inputStream.close();
        }
        return optional;
    }

    public static Optional<Data> b(String string) {
        return ResourceStreamLoader.a(string).map(Data::makeFromBytes);
    }

    public static InputStream c(String string) {
        InputStream inputStream = ResourceStreamLoader.class.getResourceAsStream(string);
        if (inputStream == null) {
            throw new IllegalArgumentException("Resource not found: " + string);
        }
        return inputStream;
    }
}

