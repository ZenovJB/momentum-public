package me.linus.momentum.module.modules.movement;

import me.linus.momentum.api.events.forge.MoveEvent;
import me.linus.momentum.module.Category;
import me.linus.momentum.module.Module;
import me.linus.momentum.utils.settings.Setting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;

public class Step extends Module {
    public Step() {
        super("Step", Category.Movement);
    }

    public Setting height;
    public Setting mode;
    Setting hidden;

    @Override
    public void setup() {
        ArrayList<String> options = new ArrayList<>();
        options.add("Reverse");
        options.add("Upwards");
        options.add("Both");

        rSetting(height = new Setting("Height", this, 2, 0, 20, true, ""));
        rSetting(mode = new Setting("Mode", this, "Reverse", options, ""));
        rSetting(hidden = new Setting("Hidden", this, false, "hide"));
    }

    @Override
    public void onDisable(){
        mc.player.stepHeight = 0;

        if (mc.player != null && mc.player.onGround && !mc.player.isInWater() && !mc.player.isOnLadder()) {
            for (double y = 0.0; y < this.height.getDVal() + 0.5; y += 0.01) {
                if (!mc.world.getCollisionBoxes(mc.player, mc.player.getEntityBoundingBox().offset(0.0, -y, 0.0)).isEmpty()) {
                    mc.player.motionY = 0;
                    break;
                }
            }
        }
    }

    @SubscribeEvent
    public void onMove(MoveEvent event) {
        if (mode.getSVal() == "Reverse") {
            if (mc.player.isInWater() || mc.player.isInLava() || mc.player.isOnLadder() || mc.gameSettings.keyBindJump.isKeyDown()) {
                return;
            }

            if (mc.player != null && mc.player.onGround && !mc.player.isInWater() && !mc.player.isOnLadder()) {
                for (double y = 0.0; y < this.height.getDVal() + 0.5; y += 0.01) {
                    if (!mc.world.getCollisionBoxes(mc.player, mc.player.getEntityBoundingBox().offset(0.0, -y, 0.0)).isEmpty()) {
                        mc.player.motionY = -10.0;
                        break;
                    }
                }
            }
        } else if (mode.getSVal() == "Upward") {
            if (mc.player != null || mc.world != null) {
                    mc.player.stepHeight = (float) height.getDVal();
                }
        }

        else {
            if (mc.player.isInWater() || mc.player.isInLava() || mc.player.isOnLadder() || mc.gameSettings.keyBindJump.isKeyDown()) {
                return;
            }

            if (mc.player != null && mc.player.onGround && !mc.player.isInWater() && !mc.player.isOnLadder()) {
                for (double y = 0.0; y < this.height.getDVal() + 0.5; y += 0.01) {
                    if (!mc.world.getCollisionBoxes(mc.player, mc.player.getEntityBoundingBox().offset(0.0, -y, 0.0)).isEmpty()) {
                        mc.player.motionY = -10.0;
                        break;
                    }
                }
            }

            if (mc.player != null || mc.world != null) {
                mc.player.stepHeight = (float) height.getDVal();
            }
        }
    }

    @Override
    public String getHudInfo() {
        return  " \u00A77[\u00A7f" + this.mode.getSVal() + "\u00A77]";
    }
}

