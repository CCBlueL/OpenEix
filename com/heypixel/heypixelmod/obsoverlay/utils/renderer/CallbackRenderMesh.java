/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils.renderer;

import com.heypixel.heypixelmod.obsoverlay.utils.renderer.GuiQuadRenderer;
import com.heypixel.heypixelmod.obsoverlay.utils.renderer.MeshBuilder;
import com.heypixel.heypixelmod.obsoverlay.utils.renderer.ShaderProgram;

public class CallbackRenderMesh
extends MeshBuilder {
    private final ShaderProgram c;

    public CallbackRenderMesh(ShaderProgram i2, GuiQuadRenderer c2, com.heypixel.heypixelmod.obsoverlay.utils.renderer.MeshBuilder.VertexComponentCount ... aArray) {
        super(c2, aArray);
        this.c = i2;
    }

    @Override
    protected void f() {
        this.c.a();
    }
}

