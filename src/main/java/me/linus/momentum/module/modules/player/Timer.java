package me.linus.momentum.module.modules.player;

import me.linus.momentum.module.Category;
import me.linus.momentum.utils.settings.Setting;
import me.linus.momentum.module.Module;


public class Timer extends Module{

    public Timer() {
        super("Timer", Category.Player);
    }

    public Setting timer;
    Setting hidden;

    @Override
    public void setup() {
        rSetting(timer = new Setting("Ticks", this, 4, 1, 20, false, "timer"));
        rSetting(hidden = new Setting("Hidden", this, false, "hide"));
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.timer.tickLength = 50;
    }

    @Override
    public void onUpdate() {
        mc.timer.tickLength = 50.0f / timer.getDVal();
    }
}
