/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.modules.impl.b;

import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleCategory;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleBase;
import net.minecraft.world.entity.Entity;

@ModuleInfo(a="ClientFriend", b="\u5ba2\u6237\u7aef\u670b\u53cb", c="Treat other users as friend!", d=ModuleCategory.MISC)
public class ClientFriend
extends ModuleBase {
    public static boolean a(Entity entity) {
        return false;
    }
}

