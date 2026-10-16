/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils;

import com.heypixel.heypixelmod.obsoverlay.b.ClientConfigurationManager;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AntiBotLogWriter {
    private static final SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final BufferedWriter b;
    private static final BufferedWriter c;

    public static void a(String string) {
        try {
            b.write("[%s] %s\n".formatted(a.format(new Date()), string));
            b.flush();
        }
        catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }

    public static void b(String string) {
        try {
            c.write("[%s] %s\n".formatted(a.format(new Date()), string));
            c.flush();
        }
        catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }

    public static void a() {
        try {
            b.close();
            c.close();
        }
        catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }

    static {
        try {
            b = new BufferedWriter(new FileWriter(new File(com.heypixel.heypixelmod.obsoverlay.ClientVersion.b.clientFolder, "antibots.log")));
            c = new BufferedWriter(new FileWriter(new File(com.heypixel.heypixelmod.obsoverlay.ClientVersion.b.clientFolder, "playerinfo.log")));
        }
        catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }
}

