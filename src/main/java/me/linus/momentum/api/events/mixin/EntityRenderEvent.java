package me.linus.momentum.api.events.mixin;

import me.linus.momentum.api.events.Event;

public class EntityRenderEvent extends Event {

    private float partialTicks;

    public EntityRenderEvent(final float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return partialTicks;
    }
}
