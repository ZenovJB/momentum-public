package me.linus.momentum.module.modules.combat;

import me.linus.momentum.module.Category;
import me.linus.momentum.module.Module;
import me.linus.momentum.utils.settings.Setting;
import net.minecraft.block.Block;
import net.minecraft.item.ItemEndCrystal;

public class FastPlace extends Module {
    public FastPlace() {
        super("FastPlace", Category.Combat);
    }

    public Setting crystal;
    public Setting block;
    Setting hidden;

    @Override
    public void setup() {
        rSetting(block = new Setting("Blocks", this, false, ""));
        rSetting(crystal = new Setting("Crystals", this, true, ""));
        rSetting(hidden = new Setting("Hidden", this, false, "hide"));
    }

    @Override
    public void onUpdate(){
        if (mc.player.getHeldItemMainhand().getItem() instanceof ItemEndCrystal) {
            if (this.crystal.getBVal()) {
                mc.rightClickDelayTimer = 0;
            }
        }

        if (Block.getBlockFromItem(mc.player.getHeldItemMainhand().getItem()).getDefaultState().isFullBlock()) {
            if (this.block.getBVal()) {
                mc.rightClickDelayTimer = 0;
            }
    }
}}
