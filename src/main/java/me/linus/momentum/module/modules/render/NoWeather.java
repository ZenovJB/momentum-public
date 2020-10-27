package me.linus.momentum.module.modules.render;

import me.linus.momentum.module.Category;
import me.linus.momentum.module.Module;
import me.linus.momentum.utils.settings.Setting;

public class NoWeather extends Module {
    public NoWeather() {
        super("NoWeather", Category.Render);
    }

    Setting hidden;

    @Override
    public void setup(){
        rSetting(hidden = new Setting("Hidden", this, false, "hide"));
    }

    public void onUpdate() {
        if (mc.world == null) return;
        if (mc.world.isRaining()) {
            mc.world.setRainStrength(0);
        }
    }
}
