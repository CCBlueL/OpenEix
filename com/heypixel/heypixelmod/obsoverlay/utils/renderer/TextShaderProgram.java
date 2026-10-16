/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils.renderer;

import com.heypixel.heypixelmod.obsoverlay.utils.renderer.ShaderProgram;

public class TextShaderProgram {
    public static ShaderProgram a;

    public static void a() {
        a = new ShaderProgram("text.vert", "text.frag");
    }
}

