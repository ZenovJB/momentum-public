package me.linus.momentum.module.modules.misc;

import me.linus.momentum.module.Category;
import me.linus.momentum.module.Module;
import me.linus.momentum.utils.settings.Setting;
import net.minecraft.entity.player.EnumPlayerModelParts;
import java.util.Random;

public class SkinBlinker extends Module {
    public SkinBlinker() {
        super("SkinBlinker", Category.Miscellaneous);
    }

    private static final EnumPlayerModelParts[] PARTS_HORIZONTAL;
    private static final EnumPlayerModelParts[] PARTS_VERTICAL;
    private Random r;
    private int len = EnumPlayerModelParts.values().length;
    private int slowness = 2;

    Setting hidden;

    @Override
    public void setup(){
        rSetting(hidden = new Setting("Hidden", this, false, "hide"));
    }

    @Override
    public void onUpdate() {
                if (mc.player.ticksExisted % slowness != 0) {
                    return;
                }

                mc.gameSettings.switchModelPartEnabled(EnumPlayerModelParts.values()[this.r.nextInt(this.len)]);
    }

    static {
        PARTS_HORIZONTAL = new EnumPlayerModelParts[]{EnumPlayerModelParts.LEFT_SLEEVE, EnumPlayerModelParts.JACKET, EnumPlayerModelParts.HAT, EnumPlayerModelParts.LEFT_PANTS_LEG, EnumPlayerModelParts.RIGHT_PANTS_LEG, EnumPlayerModelParts.RIGHT_SLEEVE};
        PARTS_VERTICAL = new EnumPlayerModelParts[]{EnumPlayerModelParts.HAT, EnumPlayerModelParts.JACKET, EnumPlayerModelParts.LEFT_SLEEVE, EnumPlayerModelParts.RIGHT_SLEEVE, EnumPlayerModelParts.LEFT_PANTS_LEG, EnumPlayerModelParts.RIGHT_PANTS_LEG};
    }
}
