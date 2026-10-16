/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils.b;

import cn.paradisemc.ZKMIndy;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@ZKMIndy
public class PlatformDirectoryResolver {
    private static final byte[] a = new byte[]{-1, -40, -1, -32, 0, 16, 74, 70, 73, 70};

    public static Set<String> a() throws IOException {
        HashSet<String> hashSet = new HashSet<String>();
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            Object object;
            File file = new File(System.getenv("APPDATA") + "\\Tencent\\QQ\\Misc");
            if (file.exists() && file.isDirectory() && (object = file.listFiles()) != null) {
                for (File[] fileArray : object) {
                    String string;
                    if (fileArray.isDirectory() || !(string = fileArray.getName()).matches("[0-9]+") || string.length() < 5 || string.length() > 10 || !PlatformDirectoryResolver.a((File)fileArray)) continue;
                    hashSet.add(string);
                }
            }
            if (((File)(object = new File(System.getenv("PUBLIC") + "\\Documents\\Tencent\\QQ\\UserDataInfo.ini"))).exists() && ((File)object).isFile()) {
                String string;
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Files.newInputStream(((File)object).toPath(), new OpenOption[0])));
                while ((string = bufferedReader.readLine()) != null && !string.isEmpty()) {
                    File file2;
                    if (!string.startsWith("UserDataSavePath=") || !(file2 = new File(string.split("=")[1])).exists() || !file2.isDirectory()) continue;
                    for (File file3 : Objects.requireNonNull(file2.listFiles())) {
                        if (!file3.isDirectory() || file3.getName().length() < 6 || file3.getName().length() > 10 || !file3.getName().matches("^[0-9]*$")) continue;
                        hashSet.add(file3.getName());
                    }
                }
            }
        }
        return hashSet;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static boolean a(File file) {
        try (FileInputStream fileInputStream = new FileInputStream(file);){
            byte[] byArray = new byte[10];
            if (fileInputStream.read(byArray) != 0) {
                boolean bl = Arrays.equals(byArray, a);
                return bl;
            }
            boolean bl = false;
            return bl;
        }
        catch (Exception exception) {
            return false;
        }
    }
}

