package me.linus.momentum.module.modules.movement;

import me.linus.momentum.module.Category;
import me.linus.momentum.module.Module;
import me.linus.momentum.utils.settings.Setting;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class BiomeSpeed extends Module {
    public BiomeSpeed() {
        super("BiomeSpeed", Category.Movement);
    }

    Setting hidden;

    @Override
    public void setup(){
        rSetting(hidden = new Setting("Hidden", this, false, "hide"));
    }

    public void onUpdate() {
        Blocks.ICE.slipperiness = 0F;
        Blocks.PACKED_ICE.slipperiness = 0F;
        Blocks.FROSTED_ICE.slipperiness = 0F;
    }

    public void onDisable() {
        Blocks.ICE.slipperiness = 0.98F;
        Blocks.PACKED_ICE.slipperiness = 0.98F;
        Blocks.FROSTED_ICE.slipperiness = 0.98F;
    }
}
