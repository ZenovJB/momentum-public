package me.linus.momentum.module.modules.render;

import me.linus.momentum.Momentum;
import me.linus.momentum.api.events.mixin.BossBarEvent;
import me.linus.momentum.module.Category;
import me.linus.momentum.module.Module;
import me.linus.momentum.utils.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;

public class NoRender extends Module {
    public NoRender(){ super("NoRender", Category.Render); }

    public Setting armor;
    public Setting fire;
    public Setting hurtCam;
    public Setting noBossBar;
    Setting hidden;

    @Override
    public void setup() {
        rSetting(armor = new Setting("Armor", this, true, ""));
        rSetting(fire = new Setting("Fire", this, true, ""));
        rSetting(hurtCam = new Setting("Hurt Camera", this, true, ""));
        rSetting(noBossBar = new Setting("Boss Bar", this, true, ""));
        rSetting(hidden = new Setting("Hidden", this, false, "hide"));
    }

    @EventHandler
    public Listener<RenderBlockOverlayEvent> blockOverlayEventListener = new Listener<>(event -> {
        if (fire.getBVal() && event.getOverlayType() == RenderBlockOverlayEvent.OverlayType.FIRE) event.setCanceled(true);
    });


    @EventHandler
    private final Listener<RenderBlockOverlayEvent> renderBlockOverlayEventListener = new Listener<>(event -> {
        event.setCanceled(true);
    });

    @EventHandler
    private final Listener<BossBarEvent> bossbarEventListener = new Listener<>(event -> {
        if (noBossBar.getBVal()){
            event.cancel();
        }
    });

    public void onEnable(){
        Momentum.EVENT_BUS.subscribe(this);
    }

    public void onDisable(){
        Momentum.EVENT_BUS.unsubscribe(this);
    }


    public boolean getArmor(){
        return armor.getBVal();
    }

    public boolean getHurtCam(){
        return hurtCam.getBVal();
    }
}