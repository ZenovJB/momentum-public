package me.linus.momentum.utils.command.commands;

import net.minecraft.util.text.TextFormatting;
import me.linus.momentum.Momentum;
import me.linus.momentum.utils.managers.MessageManager;
import me.linus.momentum.module.Module;
import me.linus.momentum.utils.command.Command;

public class Toggle extends Command {
    public Toggle() {
        super("Toggle", new String[]{"toggle"});
    }

    @Override
    public void onCommand(String[] args) {
        if (args.length > 1) {
            try {
            for (Module m: Momentum.moduleManager.getModules()) {
                if (m.getName().equalsIgnoreCase(args[1])) {
                    m.toggle();
                    if (m.isToggled()) {
                        MessageManager.sendMessagePrefix(TextFormatting.AQUA + m.getName() + TextFormatting.WHITE + " is now " + TextFormatting.GREEN + "ON");
                    } else {
                        MessageManager.sendMessagePrefix(TextFormatting.AQUA + m.getName() + TextFormatting.WHITE + " is now " + TextFormatting.RED + "OFF");
                    }
                }
            }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
