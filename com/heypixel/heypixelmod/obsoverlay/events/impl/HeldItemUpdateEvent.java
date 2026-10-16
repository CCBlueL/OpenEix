/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.events.impl;

import com.heypixel.heypixelmod.obsoverlay.events.api.events.Event;
import java.util.Objects;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

public class HeldItemUpdateEvent
implements Event {
    private final InteractionHand a;
    private ItemStack b;

    public HeldItemUpdateEvent(InteractionHand interactionHand, ItemStack itemStack) {
        this.a = interactionHand;
        this.b = itemStack;
    }

    public InteractionHand a() {
        return this.a;
    }

    public ItemStack b() {
        return this.b;
    }

    public void a(ItemStack itemStack) {
        this.b = itemStack;
    }

    public boolean equals(Object object) {
        InteractionHand interactionHand;
        if (object == this) {
            return true;
        }
        if (!(object instanceof HeldItemUpdateEvent)) {
            return false;
        }
        HeldItemUpdateEvent af2 = (HeldItemUpdateEvent)object;
        if (!af2.a(this)) {
            return false;
        }
        InteractionHand interactionHand2 = this.a();
        if (Objects.equals(interactionHand2, interactionHand = af2.a())) {
            ItemStack itemStack = this.b();
            ItemStack itemStack2 = af2.b();
            return Objects.equals(itemStack, itemStack2);
        }
        return false;
    }

    protected boolean a(Object object) {
        return object instanceof HeldItemUpdateEvent;
    }

    public int hashCode() {
        int n2 = 59;
        int n3 = 1;
        InteractionHand interactionHand = this.a();
        n3 = n3 * 59 + (interactionHand == null ? 43 : interactionHand.hashCode());
        ItemStack itemStack = this.b();
        return n3 * 59 + (itemStack == null ? 43 : itemStack.hashCode());
    }

    public String toString() {
        return "EventUpdateHeldItem(hand=" + String.valueOf(this.a()) + ", item=" + String.valueOf(this.b()) + ")";
    }
}

