package me.linus.momentum.module.modules.movement;

import me.linus.momentum.module.Category;
import me.linus.momentum.module.Module;
import me.linus.momentum.utils.settings.Setting;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NoSlow extends Module {
    public NoSlow() {super("NoSlow", Category.Movement);}

    Setting hidden;

    @Override
    public void setup(){
        rSetting(hidden = new Setting("Hidden", this, false, "hide"));
    }

    @SubscribeEvent
    public void onInputUpdate(InputUpdateEvent event) {
        if (mc.player.isHandActive() && !mc.player.isRiding()) {
            event.getMovementInput().moveStrafe *= 5;
            event.getMovementInput().moveForward *= 5;
        }
    }
}


