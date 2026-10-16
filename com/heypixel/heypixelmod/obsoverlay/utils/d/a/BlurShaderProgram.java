/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils.d.a;

import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.PostProcess;
import com.heypixel.heypixelmod.obsoverlay.utils.ElapsedTimeTimer;
import com.heypixel.heypixelmod.obsoverlay.utils.d.QuadMeshRenderer;
import com.heypixel.heypixelmod.obsoverlay.utils.d.ShaderCompiler;
import com.heypixel.heypixelmod.obsoverlay.utils.d.BufferUploaderState;
import it.unimi.dsi.fastutil.ints.IntDoubleImmutablePair;
import net.minecraft.client.Minecraft;

public class BlurShaderProgram {
    public static final BlurShaderProgram a = new BlurShaderProgram();
    public static final BlurShaderProgram b = new BlurShaderProgram();
    private static final IntDoubleImmutablePair[] c = new IntDoubleImmutablePair[]{IntDoubleImmutablePair.of((int)1, (double)1.25), IntDoubleImmutablePair.of((int)1, (double)2.25), IntDoubleImmutablePair.of((int)2, (double)2.0), IntDoubleImmutablePair.of((int)2, (double)3.0), IntDoubleImmutablePair.of((int)2, (double)4.25), IntDoubleImmutablePair.of((int)3, (double)2.5), IntDoubleImmutablePair.of((int)3, (double)3.25), IntDoubleImmutablePair.of((int)3, (double)4.25), IntDoubleImmutablePair.of((int)3, (double)5.5), IntDoubleImmutablePair.of((int)4, (double)3.25), IntDoubleImmutablePair.of((int)4, (double)4.0), IntDoubleImmutablePair.of((int)4, (double)5.0), IntDoubleImmutablePair.of((int)4, (double)6.0), IntDoubleImmutablePair.of((int)4, (double)7.25), IntDoubleImmutablePair.of((int)4, (double)8.25), IntDoubleImmutablePair.of((int)5, (double)4.5), IntDoubleImmutablePair.of((int)5, (double)5.25), IntDoubleImmutablePair.of((int)5, (double)6.25), IntDoubleImmutablePair.of((int)5, (double)7.25), IntDoubleImmutablePair.of((int)5, (double)8.5)};
    private static c d;
    private static c e;
    private static c f;
    private final com.heypixel.heypixelmod.obsoverlay.utils.RemotePlayer.a[] g = new com.heypixel.heypixelmod.obsoverlay.utils.RemotePlayer.a[6];
    private final ElapsedTimeTimer h = new ElapsedTimeTimer();
    private boolean i = true;

    public void a() {
        for (int i2 = 0; i2 < this.g.length; ++i2) {
            if (this.g[i2] != null) {
                this.g[i2].d();
                continue;
            }
            this.g[i2] = new com.heypixel.heypixelmod.obsoverlay.utils.RemotePlayer.a(1.0 / Math.pow(2.0, i2));
        }
    }

    public void a(int n2) {
        int n3;
        if (d == null) {
            d = new c("blur.vert", "blur_down.frag");
            e = new c("blur.vert", "blur_up.frag");
            f = new c("passthrough.vert", "passthrough.frag");
        }
        if (this.i) {
            for (int i2 = 0; i2 < this.g.length; ++i2) {
                if (this.g[i2] != null) continue;
                this.g[i2] = new com.heypixel.heypixelmod.obsoverlay.utils.RemotePlayer.a(1.0 / Math.pow(2.0, i2));
            }
            this.i = false;
        }
        if (com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a(PostProcess.class).r()) {
            if (this.h.a(1000 / com.heypixel.heypixelmod.obsoverlay.EixClientCore.a().g().a(PostProcess.class).q())) {
                this.h.a();
            } else {
                return;
            }
        }
        IntDoubleImmutablePair intDoubleImmutablePair = c[n2 - 1];
        int n4 = intDoubleImmutablePair.leftInt();
        double d2 = intDoubleImmutablePair.rightDouble();
        com.heypixel.heypixelmod.obsoverlay.utils.RemotePlayer.b.a();
        this.a(this.g[0], Minecraft.m_91087_().m_91385_().m_83975_(), d, d2);
        for (n3 = 0; n3 < n4; ++n3) {
            this.a(this.g[n3 + 1], this.g[n3].a, d, d2);
        }
        for (n3 = n4; n3 >= 1; --n3) {
            this.a(this.g[n3 - 1], this.g[n3].a, e, d2);
        }
        Minecraft.m_91087_().m_91385_().m_83947_(true);
        f.a();
        com.heypixel.heypixelmod.obsoverlay.utils.RemotePlayer.d.e(this.g[0].a);
        f.a("uTexture", 0);
        com.heypixel.heypixelmod.obsoverlay.utils.RemotePlayer.b.c();
    }

    public int b() {
        return this.g[0].a;
    }

    private void a(com.heypixel.heypixelmod.obsoverlay.utils.RemotePlayer.a a2, int n2, c c2, double d2) {
        a2.a();
        a2.b();
        c2.a();
        com.heypixel.heypixelmod.obsoverlay.utils.RemotePlayer.d.e(n2);
        c2.a("uTexture", 0);
        c2.a("uHalfTexelSize", 0.5 / (double)a2.c, 0.5 / (double)a2.d);
        c2.a("uOffset", d2);
        com.heypixel.heypixelmod.obsoverlay.utils.RemotePlayer.b.b();
    }
}

