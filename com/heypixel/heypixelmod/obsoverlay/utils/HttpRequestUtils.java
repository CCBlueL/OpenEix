/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils;

import cn.paradisemc.ZKMIndy;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import org.apache.commons.io.FileUtils;

@ZKMIndy
public class HttpRequestUtils {
    public static final String a = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0";

    public HttpRequestUtils() {
        HttpURLConnection.setFollowRedirects(true);
    }

    public static HttpURLConnection a(String string, String string2, String string3) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection)new URL(string).openConnection();
        httpURLConnection.setRequestMethod(string2);
        httpURLConnection.setConnectTimeout(5000);
        httpURLConnection.setReadTimeout(10000);
        httpURLConnection.setRequestProperty("User-Agent", string3);
        httpURLConnection.setInstanceFollowRedirects(true);
        httpURLConnection.setDoOutput(true);
        return httpURLConnection;
    }

    public static String b(String string, String string2, String string3) {
        try {
            String string4;
            HttpURLConnection httpURLConnection = HttpRequestUtils.a(string, string2, string3);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            while ((string4 = bufferedReader.readLine()) != null) {
                stringBuilder.append(string4).append("\n");
            }
            bufferedReader.close();
            return stringBuilder.toString();
        }
        catch (SocketTimeoutException socketTimeoutException) {
            System.err.println("Read timed out");
            return null;
        }
        catch (IOException iOException) {
            System.err.println("Error while making request");
            return null;
        }
    }

    public static String c(String string, String string2, String string3) {
        try {
            String string4;
            HttpURLConnection httpURLConnection = HttpRequestUtils.a(string, string2, string3);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            while ((string4 = bufferedReader.readLine()) != null) {
                stringBuilder.append(string4);
            }
            bufferedReader.close();
            return stringBuilder.toString();
        }
        catch (SocketTimeoutException socketTimeoutException) {
            System.err.println("Read timed out");
            return null;
        }
        catch (IOException iOException) {
            System.err.println("Error while making request");
            return null;
        }
    }

    public static String a(String string) throws IOException {
        return HttpRequestUtils.b(string, "GET", a);
    }

    public static String b(String string) throws IOException {
        return HttpRequestUtils.c(string, "GET", a);
    }

    public static void a(String string, File file) throws IOException {
        FileUtils.copyInputStreamToFile((InputStream)HttpRequestUtils.a(string, "GET", a).getInputStream(), (File)file);
    }

    public static String a(InputStream inputStream) throws IOException {
        String string;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        while ((string = bufferedReader.readLine()) != null) {
            stringBuilder.append(string);
        }
        return stringBuilder.toString();
    }
}

