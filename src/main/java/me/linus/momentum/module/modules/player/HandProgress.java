package me.linus.momentum.module.modules.player;

import me.linus.momentum.module.Category;
import me.linus.momentum.module.Module;
import me.linus.momentum.utils.settings.Setting;

public class HandProgress extends Module {
    public HandProgress() {
        super("HandProgress", Category.Render);
    }

    Setting height;
    Setting hidden;

    public void setup(){
        rSetting(height = new Setting("Height", this , 0.5, 0, 1, false, ""));
        rSetting(hidden = new Setting("Hidden", this, false, "hide"));
    }

    public void onUpdate(){
        mc.entityRenderer.itemRenderer.equippedProgressOffHand = (float) height.getDVal();
    }
}
