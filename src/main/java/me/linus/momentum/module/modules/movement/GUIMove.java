package me.linus.momentum.module.modules.movement;

import me.linus.momentum.module.Category;
import me.linus.momentum.module.Module;
import me.linus.momentum.utils.settings.Setting;
import net.minecraft.client.gui.GuiChat;
import org.lwjgl.input.Keyboard;


public class GUIMove extends Module {
    public GUIMove() {
        super("GUIMove", Category.Movement);
    }

    Setting hidden;

    @Override
    public void setup(){
        rSetting(hidden = new Setting("Hidden", this, false, "hide"));
    }

    public void onUpdate(){
            if (mc.currentScreen != null && !(mc.currentScreen instanceof GuiChat)){
                if (Keyboard.isKeyDown(200)){
                    mc.player.rotationPitch -= 5;
                }
                if (Keyboard.isKeyDown(208)){
                    mc.player.rotationPitch += 5;
                }
                if (Keyboard.isKeyDown(205)){
                    mc.player.rotationYaw += 5;
                }
                if (Keyboard.isKeyDown(203)){
                    mc.player.rotationYaw -= 5;
                }
                if (mc.player.rotationPitch > 90) mc.player.rotationPitch = 90;
                if (mc.player.rotationPitch < -90) mc.player.rotationPitch = -90;
            }
        }
    }


