package me.linus.momentum.module.modules.misc;

import me.linus.momentum.Momentum;
import me.linus.momentum.module.Category;
import me.linus.momentum.module.Module;
import me.linus.momentum.utils.settings.Setting;
import net.minecraft.client.gui.GuiChat;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class PrefixChat extends Module {
    public PrefixChat() {
        super("Prefix", Category.Miscellaneous);
    }

    Setting hidden;

    @Override
    public void setup(){
        rSetting(hidden = new Setting("Hidden", this, false, "hide"));
    }

    @SubscribeEvent
    public void onKeyPress(InputEvent.KeyInputEvent event) {
        if (mc.currentScreen == null && Keyboard.isKeyDown(Keyboard.KEY_EQUALS)) {
            mc.displayGuiScreen(new GuiChat(Momentum.prefix));
        }
    }
}