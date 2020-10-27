package me.linus.momentum.module.modules.gui;

import me.linus.momentum.gui.GUI;
import me.linus.momentum.module.Category;
import me.linus.momentum.module.Module;
import me.linus.momentum.utils.settings.Setting;

public class ClickGUI extends Module {
    public ClickGUI() {
        super("ClickGUI", Category.GUI);
    }

    public Setting red;
    public Setting green;
    public Setting blue;
    public Setting alpha;
    public Setting cfont;
    public Setting gradient;
    Setting hidden;

    @Override
    public void setup() {
        rSetting(red = new Setting("Red", this, 255, 0, 255, true, "r"));
        rSetting(green = new Setting("Green", this, 255, 0, 255, true, "g"));
        rSetting(blue = new Setting("Blue", this, 255, 0, 255, true, "b"));
        rSetting(alpha = new Setting("Alpha", this, 255, 0, 255, true, "alpha"));
        rSetting(cfont = new Setting("Custom Font", this, true, "cfont"));
        rSetting(hidden = new Setting("Hidden", this, false, "hide"));
        rSetting(gradient = new Setting("Rainbow", this, false, "grad"));
    }

    @Override
    public void onEnable() {
        if (mc.player != null && mc.world != null) {
            mc.displayGuiScreen(new GUI());
            toggle();
        }
    }

    public int getRed() {
        return red.getDVal();
    }

    public int getGreen() {
        return green.getDVal();
    }

    public int getBlue() {
        return blue.getDVal();
    }

    public int getAlpha() {
        return alpha.getDVal();
    }

    public boolean getGradient() {
        return gradient.getBVal();
    }
}

