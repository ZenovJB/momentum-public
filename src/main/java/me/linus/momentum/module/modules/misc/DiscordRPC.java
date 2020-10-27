package me.linus.momentum.module.modules.misc;

import me.linus.momentum.utils.managers.DiscordManager;
import me.linus.momentum.module.Category;
import me.linus.momentum.module.Module;
import me.linus.momentum.utils.settings.Setting;

public class DiscordRPC extends Module {
    public DiscordRPC() {
        super("DiscordRPC", Category.Miscellaneous);
    }

    Setting hidden;

    @Override
    public void setup(){
        rSetting(hidden = new Setting("Hidden", this, false, "hide"));
    }


    @Override
    public void onEnable() { DiscordManager.startup(); }

    @Override
    public void onDisable() {
        DiscordManager.shutdown();
    }
}
