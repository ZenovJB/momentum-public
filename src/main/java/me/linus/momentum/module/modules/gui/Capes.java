package me.linus.momentum.module.modules.gui;

import me.linus.momentum.module.Category;
import me.linus.momentum.module.Module;
import me.linus.momentum.utils.settings.Setting;

import java.util.ArrayList;

public class Capes extends Module {
    public Capes() {
        super("Capes", Category.GUI);
    }

    public Setting mode;
    Setting hidden;

    @Override
    public void setup(){
        ArrayList<String> options = new ArrayList<>();
        options.add("2013");
        options.add("S8N");
        options.add("Momentum");

        rSetting(mode = new Setting("Mode", this, "Momentum", options, ""));
        rSetting(hidden = new Setting("Hidden", this, false, "hide"));
    }
}
