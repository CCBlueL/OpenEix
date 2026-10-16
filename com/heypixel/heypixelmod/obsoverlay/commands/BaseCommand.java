/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.commands;

import com.heypixel.heypixelmod.obsoverlay.commands.CommandInfo;

public abstract class BaseCommand {
    private String a;
    private String b;
    private String[] c;

    public BaseCommand(String string, String string2, String[] stringArray) {
        this.a = string;
        this.b = string2;
        this.c = stringArray;
    }

    public BaseCommand() {
    }

    protected void a() {
        if (this.getClass().isAnnotationPresent(CommandInfo.class)) {
            CommandInfo commandInfo = this.getClass().getAnnotation(CommandInfo.class);
            this.a = commandInfo.a();
            this.b = commandInfo.b();
            this.c = commandInfo.c();
        }
    }

    public abstract void a(String[] var1);

    public abstract String[] b(String[] var1);

    public String b() {
        return this.a;
    }

    public String c() {
        return this.b;
    }

    public String[] d() {
        return this.c;
    }
}

