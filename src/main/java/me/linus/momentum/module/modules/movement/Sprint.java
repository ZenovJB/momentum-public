package me.linus.momentum.module.modules.movement;

import me.linus.momentum.module.Category;
import me.linus.momentum.module.Module;
import me.linus.momentum.utils.settings.Setting;

public class Sprint extends Module {
    public Sprint() {
        super("Sprint", Category.Movement);
    }

    Setting hidden;

    @Override
    public void setup(){
        rSetting(hidden = new Setting("Hidden", this, false, "hide"));
    }

    public void onUpdate() {
        try {
                if (mc.gameSettings.keyBindForward.isKeyDown() && !(mc.player.collidedHorizontally)) {
                    if (!mc.player.isSprinting()) {
                        mc.player.setSprinting(true);
                    }
                }
            } catch (Exception ignored) {
        }
    }
}
