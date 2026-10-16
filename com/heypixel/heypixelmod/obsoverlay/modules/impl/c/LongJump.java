/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.c;

import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventRender2D;
import com.heypixel.heypixelmod.obsoverlay.events.impl.LocalPlayerTickPreEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PlayerMotionEvent;
import com.heypixel.heypixelmod.obsoverlay.events.impl.PacketInterceptEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;
import com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting;
import com.heypixel.heypixelmod.obsoverlay.utils.PlayerRotationUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.lwjgl.glfw.GLFW;

@ModuleInfo(a="LongJump", b="\u8df3\u8fdc\u51a0\u519b", d=ModuleCategory.MOVEMENT, c="Allows you to use fireball longjump")
public class LongJump
extends ModuleBase {
    public static com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.b c = null;
    private final List<Integer> d = new ArrayList<Integer>();
    private final LinkedBlockingQueue<Packet<?>> e = new LinkedBlockingQueue();
    private boolean f = false;
    private boolean g = false;
    private int h = 0;
    private int i = -1;
    private boolean j = false;
    private boolean k = false;
    private boolean l = false;
    private boolean m = false;
    private boolean n = false;
    private long o = 0L;
    private int p = 0;
    private int q = 0;
    private int r = 0;
    private int s = 0;

    private void p() {
        while (!this.e.isEmpty()) {
            try {
                Packet<?> packet = this.e.poll();
                if (packet == null || a.m_91403_() == null) continue;
                packet.m_5797_((PacketListener)a.m_91403_());
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    private void c(int n2) {
        if (n2 < this.d.size()) {
            int n3 = this.d.get(n2);
            int n4 = 0;
            while (!this.e.isEmpty() && n4 <= n3) {
                try {
                    Packet<?> packet = this.e.poll();
                    if (packet != null && a.m_91403_() != null) {
                        packet.m_5797_((PacketListener)a.m_91403_());
                    }
                    ++n4;
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
            for (int i2 = n2 + 1; i2 < this.d.size(); ++i2) {
                this.d.set(i2, this.d.get(i2) - (n3 + 1));
            }
        }
    }

    private int q() {
        for (int i2 = 0; i2 < 9; ++i2) {
            ItemStack itemStack = com.heypixel.heypixelmod.obsoverlay.modules.impl.c.LongJump.a.f_91074_.m_150109_().m_8020_(i2);
            if (itemStack.m_41619_() || itemStack.m_41720_() != Items.f_42613_) continue;
            return i2;
        }
        return -1;
    }

    private int r() {
        int n2 = 0;
        for (int i2 = 0; i2 < 9; ++i2) {
            ItemStack itemStack = com.heypixel.heypixelmod.obsoverlay.modules.impl.c.LongJump.a.f_91074_.m_150109_().m_8020_(i2);
            if (itemStack.m_41720_() != Items.f_42613_) continue;
            n2 += itemStack.m_41613_();
        }
        return n2;
    }

    private int s() {
        int n2 = this.q();
        if (n2 == -1) {
            com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.a("\u00a7cNo FireBall!");
            this.a(false);
        }
        return n2;
    }

    @Override
    public void d() {
        this.p();
        this.h = 0;
        this.g = true;
        this.i = -1;
        this.f = false;
        this.j = false;
        this.l = false;
        c = null;
        this.k = false;
        this.m = false;
        this.n = false;
        this.o = 0L;
        this.p = 0;
        this.q = 0;
        this.r = 0;
        this.s = 0;
        this.d.clear();
        com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.a("\u00a7aLongJump enabled! Press Mouse4 to jump & use fireball, Mouse5 to release each knockback");
    }

    @Override
    public void e() {
        this.p();
        if (this.i != -1 && com.heypixel.heypixelmod.obsoverlay.modules.impl.c.LongJump.a.f_91074_ != null) {
            com.heypixel.heypixelmod.obsoverlay.modules.impl.c.LongJump.a.f_91074_.m_150109_().f_35977_ = this.i;
        }
        com.heypixel.heypixelmod.obsoverlay.modules.impl.c.LongJump.a.f_91066_.f_92095_.m_7249_(false);
        com.heypixel.heypixelmod.obsoverlay.modules.impl.c.LongJump.a.f_91066_.f_92089_.m_7249_(false);
        c = null;
        this.l = false;
        this.k = false;
        this.m = false;
        this.n = false;
        this.o = 0L;
        this.p = 0;
        this.q = 0;
        this.r = 0;
        this.s = 0;
        this.d.clear();
        super.e();
    }

    @EventTarget
    public void onUpdate(LocalPlayerTickPreEvent ad2) {
        if (this.m()) {
            if (this.k) {
                this.a(false);
            } else {
                int n2;
                boolean bl;
                if (this.g) {
                    if (!PlayerRotationUtils.a()) {
                        this.f = true;
                    }
                    this.g = false;
                }
                boolean bl2 = bl = GLFW.glfwGetMouseButton((long)a.m_91268_().m_85439_(), (int)3) == 1;
                if (bl && !this.m) {
                    this.m = true;
                    if (!this.l && this.h == 0 && (n2 = this.s()) != -1) {
                        this.i = com.heypixel.heypixelmod.obsoverlay.modules.impl.c.LongJump.a.f_91074_.m_150109_().f_35977_;
                        com.heypixel.heypixelmod.obsoverlay.modules.impl.c.LongJump.a.f_91074_.m_150109_().f_35977_ = n2;
                        this.h = 1;
                        com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.a("\u00a7eStarting fireball usage #" + (this.p + 1));
                    }
                } else if (!bl) {
                    this.m = false;
                }
                int n3 = n2 = GLFW.glfwGetMouseButton((long)a.m_91268_().m_85439_(), (int)4) == 1 ? 1 : 0;
                if (n2 != 0 && !this.n) {
                    this.n = true;
                    if (this.j && this.s < this.q) {
                        com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.a("\u00a7aReleasing " + (this.s + 1) + "/" + this.q);
                        this.c(this.s);
                        ++this.s;
                        if (this.s >= this.q) {
                            com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.a("\u00a7aAll released! Stopping LongJump.");
                            this.j = false;
                            this.a(false);
                        }
                    } else if (!this.j) {
                        com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.a("\u00a7cNo intercepted packets");
                        this.a(false);
                    } else {
                        com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.a("\u00a7cAll already released");
                    }
                } else if (n2 == 0) {
                    this.n = false;
                }
            }
        }
    }

    @EventTarget
    public void onRender2D(EventRender2D eventRender2D) {
        if (this.m()) {
            Object object;
            int n2 = a.m_91268_().m_85445_();
            int n3 = a.m_91268_().m_85446_();
            if (this.j) {
                int n4 = this.e.size();
                long l2 = System.currentTimeMillis();
                long l3 = l2 - this.o;
                object = String.format("\u00a7eIntercepting: %d packets | Time: %.1fs | Press Mouse5 (%d/%d)", n4, Float.valueOf((float)l3 / 1000.0f), this.s, this.q);
            } else {
                object = this.l ? "\u00a7aUsing fireball #" + this.p : "\u00a7bWaiting for input | Mouse4: Jump & use fireball | Mouse5: Release";
            }
            float f2 = (float)n2 / 2.0f - (float)com.heypixel.heypixelmod.obsoverlay.modules.impl.c.LongJump.a.f_91062_.m_92895_((String)object) / 2.0f;
            float f3 = (float)n3 / 2.0f + 20.0f;
            eventRender2D.guiGraphics().m_280488_(com.heypixel.heypixelmod.obsoverlay.modules.impl.c.LongJump.a.f_91062_, (String)object, (int)f2, (int)f3, -1);
        }
    }

    @EventTarget
    public void onPacket(PacketInterceptEvent m2) {
        if (this.m() && com.heypixel.heypixelmod.obsoverlay.modules.impl.c.LongJump.a.f_91073_ != null) {
            if (this.j && m2.b() == com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.d) {
                Packet<?> packet = m2.c();
                if (packet instanceof ClientboundPlayerPositionPacket) {
                    this.k = true;
                    m2.a(true);
                } else {
                    ClientboundSetEntityMotionPacket clientboundSetEntityMotionPacket;
                    if (packet instanceof ClientboundSetEntityMotionPacket && (clientboundSetEntityMotionPacket = (ClientboundSetEntityMotionPacket)packet).m_133192_() == com.heypixel.heypixelmod.obsoverlay.modules.impl.c.LongJump.a.f_91074_.m_19879_()) {
                        ++this.q;
                        this.d.add(this.e.size());
                        a.execute(() -> com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.a("\u00a7e" + this.q + " received"));
                    }
                    m2.a(true);
                    this.e.add(packet);
                }
            } else {
                Packet<?> packet = m2.c();
                if (packet instanceof ClientboundSetEntityMotionPacket) {
                    ClientboundSetEntityMotionPacket clientboundSetEntityMotionPacket = (ClientboundSetEntityMotionPacket)packet;
                    if (m2.b() == com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.d && clientboundSetEntityMotionPacket.m_133192_() == com.heypixel.heypixelmod.obsoverlay.modules.impl.c.LongJump.a.f_91074_.m_19879_() && this.p > 0 && !this.j) {
                        ++this.q;
                        this.d.add(this.e.size());
                        a.execute(() -> com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.a("\u00a7eReceived #" + this.q + ", starting packet interception"));
                        m2.a(true);
                        this.e.add(m2.c());
                        this.j = true;
                        this.o = System.currentTimeMillis();
                        a.execute(() -> com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.a("\u00a7ePacket interception started, press Mouse5 to release each"));
                    }
                }
            }
        } else if (this.j) {
            a.execute(() -> {
                this.p();
                this.j = false;
            });
        }
    }

    @EventTarget
    public void onMotion(PlayerMotionEvent k2) {
        if (this.m()) {
            if (k2.b() == com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.a.a) {
                if (this.h > 0) {
                    if (this.h == 1) {
                        float f2;
                        float f3;
                        ++this.p;
                        com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.a("\u00a7aJumping for fireball #" + this.p);
                        com.heypixel.heypixelmod.obsoverlay.modules.impl.c.LongJump.a.f_91066_.f_92089_.m_7249_(true);
                        if (!this.f) {
                            f3 = com.heypixel.heypixelmod.obsoverlay.modules.impl.c.LongJump.a.f_91074_.m_146908_() - 180.0f;
                            f2 = 88.0f;
                        } else {
                            f3 = com.heypixel.heypixelmod.obsoverlay.modules.impl.c.LongJump.a.f_91074_.m_146908_();
                            f2 = 90.0f;
                        }
                        c = new com.heypixel.heypixelmod.obsoverlay.utils.ResourceLocationFactory.b(f3, f2);
                    }
                    if (this.h >= 2) {
                        this.h = 0;
                        int n2 = this.s();
                        if (n2 != -1) {
                            com.heypixel.heypixelmod.obsoverlay.modules.impl.c.LongJump.a.f_91074_.m_150109_().f_35977_ = n2;
                            this.r = this.r();
                            com.heypixel.heypixelmod.obsoverlay.modules.impl.c.LongJump.a.f_91066_.f_92095_.m_7249_(true);
                            this.l = true;
                            com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.a("\u00a7eFireball #" + this.p + " started, initial count: " + this.r);
                        } else {
                            this.a(false);
                        }
                    }
                    if (this.h != 0) {
                        ++this.h;
                    }
                }
            } else if (this.l) {
                int n3 = this.r();
                if (n3 < this.r) {
                    com.heypixel.heypixelmod.obsoverlay.modules.impl.c.LongJump.a.f_91066_.f_92095_.m_7249_(false);
                    com.heypixel.heypixelmod.obsoverlay.modules.impl.c.LongJump.a.f_91066_.f_92089_.m_7249_(false);
                    c = null;
                    this.l = false;
                    com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.a("\u00a7eFireball #" + this.p + " used! Count: " + this.r + " -> " + n3 + ", waiting for next input");
                } else if (this.q() == -1) {
                    com.heypixel.heypixelmod.obsoverlay.modules.impl.c.LongJump.a.f_91066_.f_92095_.m_7249_(false);
                    com.heypixel.heypixelmod.obsoverlay.modules.impl.c.LongJump.a.f_91066_.f_92089_.m_7249_(false);
                    c = null;
                    this.l = false;
                    com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.a("\u00a7cNo more fireballs available!");
                }
            }
        }
    }
}

