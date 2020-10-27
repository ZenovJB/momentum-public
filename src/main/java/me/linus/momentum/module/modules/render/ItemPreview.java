package me.linus.momentum.module.modules.render;

import me.linus.momentum.module.Category;
import me.linus.momentum.module.Module;
import me.linus.momentum.utils.settings.Setting;

public class ItemPreview extends Module {
    public ItemPreview() {
        super("ItemPreview", Category.Render);
    }

    public Setting maps;
    Setting shulkers;
    Setting hidden;

    @Override
    public void setup(){
        rSetting(maps = new Setting("Maps", this, true, ""));
        rSetting(shulkers = new Setting("Shulkers", this, true, ""));
        rSetting(hidden = new Setting("Hidden", this, false, "hide"));
    }

    public boolean getMap(){
        return maps.getBVal();
    }

    public boolean getShulker(){
        return shulkers.getBVal();
    }
}
