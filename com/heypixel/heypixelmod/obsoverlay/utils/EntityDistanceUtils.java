/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils;

import com.heypixel.heypixelmod.mixin.O.accessors.GameRendererAccessor;
import com.heypixel.heypixelmod.obsoverlay.utils.f.Vector2f;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public class EntityDistanceUtils {
    private static final Minecraft a = Minecraft.m_91087_();

    public static EntityTargetList a(double d2, double d3, double d4, float f2) {
        Entity entity;
        Vec3 vec3 = EntityDistanceUtils.a.m_91290_().f_114358_.m_90583_();
        Quaternionf quaternionf = new Quaternionf((Quaternionfc)a.m_91290_().m_253208_());
        quaternionf.conjugate();
        Vector3f vector3f = new Vector3f((float)(vec3.f_82479_ - d2), (float)(vec3.f_82480_ - d3), (float)(vec3.f_82481_ - d4));
        vector3f.rotate((Quaternionfc)quaternionf);
        if (((Boolean)EntityDistanceUtils.a.f_91066_.m_231830_().m_231551_()).booleanValue() && (entity = a.m_91288_()) instanceof Player) {
            Player player = (Player)entity;
            EntityDistanceUtils.a(player, vector3f, f2);
        }
        double d5 = ((GameRendererAccessor)EntityDistanceUtils.a.f_91063_).invokeGetFov(EntityDistanceUtils.a.m_91290_().f_114358_, f2, true);
        return EntityDistanceUtils.a(vector3f, d5);
    }

    private static void a(Player player, Vector3f vector3f, float f2) {
        float f3 = player.f_19787_;
        float f4 = f3 - player.f_19867_;
        float f5 = -(f3 + f4 * f2);
        float f6 = Mth.m_14179_((float)f2, (float)player.f_36099_, (float)player.f_36100_);
        Quaternionf quaternionf = new Quaternionf().rotationX(Math.abs(Mth.m_14089_((float)(f5 * (float)Math.PI - 0.2f)) * f6) * 5.0f * ((float)Math.PI / 180));
        quaternionf.conjugate();
        vector3f.rotate((Quaternionfc)quaternionf);
        Quaternionf quaternionf2 = new Quaternionf().rotationZ(Mth.m_14031_((float)(f5 * (float)Math.PI)) * f6 * 3.0f * ((float)Math.PI / 180));
        quaternionf2.conjugate();
        vector3f.rotate((Quaternionfc)quaternionf2);
        Vector3f vector3f2 = new Vector3f(Mth.m_14031_((float)(f5 * (float)Math.PI)) * f6 * 0.5f, -Math.abs(Mth.m_14089_((float)(f5 * (float)Math.PI)) * f6), 0.0f);
        vector3f2.y = -vector3f2.y;
        vector3f.add((Vector3fc)vector3f2);
    }

    private static EntityTargetList a(Vector3f vector3f, double d2) {
        float f2 = (float)a.m_91268_().m_85446_() / 2.0f;
        float f3 = f2 / (vector3f.z() * (float)Math.tan(Math.toRadians(d2 / 2.0)));
        return vector3f.z() < 0.0f ? new EntityTargetList(-vector3f.x() * f3 + (float)a.m_91268_().m_85445_() / 2.0f, (float)a.m_91268_().m_85446_() / 2.0f - vector3f.y() * f3) : new EntityTargetList(Float.MAX_VALUE, Float.MAX_VALUE);
    }
}

