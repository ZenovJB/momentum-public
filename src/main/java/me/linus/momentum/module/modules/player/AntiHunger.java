package me.linus.momentum.module.modules.player;

import me.linus.momentum.module.Category;
import me.linus.momentum.module.Module;
import me.linus.momentum.utils.settings.Setting;

public class AntiHunger extends Module {
    public AntiHunger() {
        super("AntiHunger", Category.Player);
    }

    Setting hidden;

    @Override
    public void setup(){
        rSetting(hidden = new Setting("Hidden", this, false, "hide"));
    }

    public void onUpdate() {
        mc.player.getFoodStats().setFoodLevel(20);
    }
}

