package me.linus.momentum.api.events;

import me.linus.momentum.Momentum;
import me.linus.momentum.module.ModuleManager;
import me.zero.alpine.EventManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventProcessor {

    Minecraft mc = Minecraft.getMinecraft();

    public static EventProcessor INSTANCE;
    public EventProcessor(){
        INSTANCE = this;
    }

    @SubscribeEvent
    public void render(RenderWorldLastEvent event) {
        if (event.isCanceled()) return;
        ModuleManager.render(event);
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Post event) {
        Momentum.EVENT_BUS.post(event);
        if(event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR) {
            ModuleManager.onRender();
        }
    }

    public boolean isNull() {
        return (mc.player == null || mc.world == null);
    }

}