package me.linus.momentum.module;

import me.linus.momentum.api.events.mixin.RenderEvent;
import me.linus.momentum.utils.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import static me.linus.momentum.Momentum.settingsManager;

public abstract class Module {
    protected static Minecraft mc = Minecraft.getMinecraft();

    private String name, displayName;
    private Category category;
    private boolean toggled;
    private Integer key;
    private boolean hidden;
    private boolean moved;

    public Module(String name , Category category) {
        this.name = name;
        this.category = category;
        toggled = false;
        hidden = false;
        moved = false;
        key = Keyboard.KEY_NONE;
        setup();
    }

    public void registerSettings() {
        selfSettings();
    }

    public void onEnable() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    @SubscribeEvent
    public void gameTickEvent(TickEvent event) {
        if (this.isToggled()) {
            onUpdate();
        }
    }

    public void onWorldRender(RenderWorldLastEvent event) {}

    public void render(RenderEvent event) {}

    public void onUpdate() {}

    public void selfSettings() {}

    public void rSetting(Setting setting) {
        settingsManager.rSetting(setting);
    }

    public void onToggle() {}

    public void onRender(){}

    public void toggle() {
        toggled = !toggled;
        onToggle();
        if (toggled) {
            onEnable();
            moved = true;
        } else {
            onDisable();
            moved = false;
        }
    }

    public void disable(){toggled = false;}

    public Integer getKey(){
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public String getHudInfo(){return "";}

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isToggled() {
        return toggled;
    }

    public boolean isMoved() {
        return moved;
    }

    public void hide(){
        this.hidden = !this.hidden;
    }

    public boolean isHidden(){return hidden;}

    public String getDisplayName() {
        return displayName == null ? name : displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setup() {}

    public void onTick(TickEvent.ClientTickEvent var1) {}

}