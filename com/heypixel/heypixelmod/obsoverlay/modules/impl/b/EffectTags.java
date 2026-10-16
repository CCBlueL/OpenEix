/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.b;

import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventRender2D;
import com.heypixel.heypixelmod.obsoverlay.events.impl.WorldRenderEvent;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;
import com.heypixel.heypixelmod.obsoverlay.utils.EntityDistanceUtils;
import com.heypixel.heypixelmod.obsoverlay.utils.SharedESPData;
import com.heypixel.heypixelmod.obsoverlay.utils.PlayerEffectTracker;
import com.heypixel.heypixelmod.obsoverlay.utils.renderer.MiSansFontRenderer;
import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.world.entity.Entity;
import org.antlr.v4.runtime.misc.OrderedHashSet;

@ModuleInfo(a="EffectTags", b="\u6548\u679c\u6807\u7b7e", c="Show the player's effect tags.", d=com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory.MISC)
public class EffectTags
extends ModuleBase {
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a c = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Debug").a(false).a().b();
    private final com.heypixel.heypixelmod.obsoverlay.d.SettingModel.a d = com.heypixel.heypixelmod.obsoverlay.d.SettingBuilder.a(this, "Shared").a(true).a().b();
    private final List<PlayerEffectEntry> e = new CopyOnWriteArrayList<PlayerEffectEntry>();

    @EventTarget
    public void update(WorldRenderEvent p2) {
        try {
            this.a(p2.a());
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    private void a(float f2) {
        this.e.clear();
        for (Object object : EffectTags.a.f_91073_.m_104735_()) {
            if (object == EffectTags.a.f_91074_ || !(object instanceof AbstractClientPlayer)) continue;
            double d2 = EffectTags.a(f2, ((Entity)object).f_19854_, object.m_20185_());
            double d3 = EffectTags.a(f2, ((Entity)object).f_19855_, object.m_20186_()) + (double)object.m_20206_();
            double d4 = EffectTags.a(f2, ((Entity)object).f_19856_, object.m_20189_());
            com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.b b2 = EntityDistanceUtils.a(d2, d3, d4, f2);
            this.e.add(new PlayerEffectEntry((AbstractClientPlayer)object, b2, PlayerEffectTracker.a((AbstractClientPlayer)object)));
        }
        if (this.d.m()) {
            Map<String, SharedESPData> map = PlayerEffectTracker.a();
            for (SharedESPData af2 : map.values()) {
                double d5 = af2.b();
                double d6 = af2.c() + (double)EffectTags.a.f_91074_.m_20206_();
                double d7 = af2.d();
                com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.b b3 = EntityDistanceUtils.a(d5, d6, d7, f2);
                this.e.add(new PlayerEffectEntry(null, b3, Set.of(af2.i())));
            }
        }
    }

    @EventTarget
    public void onRender(EventRender2D eventRender2D) {
        for (PlayerEffectEntry a2 : this.e) {
            eventRender2D.stack().m_85836_();
            double d2 = 0.0;
            for (String string : a2.c()) {
                com.heypixel.heypixelmod.obsoverlay.utils.renderer.MiSansFontRenderer.a.a(eventRender2D.stack(), I18n.m_118938_((String)string, (Object[])new Object[0]), a2.b().a + 10.0f, (double)a2.b().b + d2, Color.RED, true, 0.3f);
                d2 += com.heypixel.heypixelmod.obsoverlay.utils.renderer.MiSansFontRenderer.a.a(true, (double)0.3f);
            }
            if (this.c.m() && a2.a() != null) {
                String string;
                AbstractClientPlayer abstractClientPlayer = a2.a();
                string = new OrderedHashSet();
                string.add((Object)("X: " + abstractClientPlayer.m_20185_()));
                string.add((Object)("Y: " + abstractClientPlayer.m_20186_()));
                string.add((Object)("Z: " + abstractClientPlayer.m_20189_()));
                string.add((Object)("Ticks: " + abstractClientPlayer.f_19797_));
                Iterator iterator = string.iterator();
                while (iterator.hasNext()) {
                    String string2 = (String)iterator.next();
                    com.heypixel.heypixelmod.obsoverlay.utils.renderer.MiSansFontRenderer.a.a(eventRender2D.stack(), string2, a2.b().a + 10.0f, (double)a2.b().b + d2, Color.RED, true, 0.35f);
                    d2 += com.heypixel.heypixelmod.obsoverlay.utils.renderer.MiSansFontRenderer.a.a(true, (double)0.35f);
                }
            }
            eventRender2D.stack().m_85849_();
        }
    }

    public static float a(float f2, float f3, float f4) {
        return f3 + f2 * (f4 - f3);
    }

    public static double a(double d2, double d3, float f2) {
        return d3 + d2 * ((double)f2 - d3);
    }

    public static double a(float f2, double d2, double d3) {
        return d2 + (double)f2 * (d3 - d2);
    }

    private static class PlayerEffectEntry {
        AbstractClientPlayer a;
        com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.b b;
        Set<String> c;

        public PlayerEffectEntry(AbstractClientPlayer abstractClientPlayer, com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.b b2, Set<String> set) {
            this.a = abstractClientPlayer;
            this.b = b2;
            this.c = set;
        }

        public AbstractClientPlayer a() {
            return this.a;
        }

        public void a(AbstractClientPlayer abstractClientPlayer) {
            this.a = abstractClientPlayer;
        }

        public com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.b b() {
            return this.b;
        }

        public void a(com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.b b2) {
            this.b = b2;
        }

        public Set<String> c() {
            return this.c;
        }

        public void a(Set<String> set) {
            this.c = set;
        }

        public boolean equals(Object object) {
            AbstractClientPlayer abstractClientPlayer;
            if (object == this) {
                return true;
            }
            if (!(object instanceof PlayerEffectEntry)) {
                return false;
            }
            PlayerEffectEntry a2 = (PlayerEffectEntry)object;
            if (!a2.a(this)) {
                return false;
            }
            AbstractClientPlayer abstractClientPlayer2 = this.a();
            if (Objects.equals(abstractClientPlayer2, abstractClientPlayer = a2.a())) {
                com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.b b2;
                com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.b b3 = this.b();
                if (Objects.equals(b3, b2 = a2.b())) {
                    Set<String> set = this.c();
                    Set<String> set2 = a2.c();
                    return Objects.equals(set, set2);
                }
                return false;
            }
            return false;
        }

        protected boolean a(Object object) {
            return object instanceof PlayerEffectEntry;
        }

        public int hashCode() {
            int n2 = 59;
            int n3 = 1;
            AbstractClientPlayer abstractClientPlayer = this.a();
            n3 = n3 * 59 + (abstractClientPlayer == null ? 43 : abstractClientPlayer.hashCode());
            com.heypixel.heypixelmod.obsoverlay.utils.ClientChatFormatting.b b2 = this.b();
            n3 = n3 * 59 + (b2 == null ? 43 : b2.hashCode());
            Set<String> set = this.c();
            return n3 * 59 + (set == null ? 43 : ((Object)set).hashCode());
        }

        public String toString() {
            return "ItemTracker.TargetInfo(player=" + String.valueOf(this.a()) + ", position=" + String.valueOf(this.b()) + ", description=" + String.valueOf(this.c()) + ")";
        }
    }
}

