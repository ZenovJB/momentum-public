package me.linus.momentum.api.events.mixin;


import me.linus.momentum.api.events.Event;
import net.minecraft.entity.Entity;

public class OutlineEvent extends Event {

    private Entity entity;

    public OutlineEvent(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return this.entity;
    }
}
