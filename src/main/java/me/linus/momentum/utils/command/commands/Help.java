package me.linus.momentum.utils.command.commands;

import me.linus.momentum.Momentum;
import me.linus.momentum.module.modules.gui.ClickGUI;
import me.linus.momentum.utils.command.Command;
import me.linus.momentum.utils.managers.MessageManager;
import net.minecraft.util.text.TextFormatting;

public class Help extends Command {
    public Help() {
        super("Help", new String[] {"help"});
    }

    @Override
    public void onCommand(String[] args) {

        MessageManager.sendRawMessage(TextFormatting.BLUE + "[Momentum Help]");
        MessageManager.sendRawMessage(TextFormatting.BLUE + "[Current Prefix: " + Momentum.prefix + "]");
        MessageManager.sendRawMessage(TextFormatting.BLUE + "[Current GUI Key: " + "P]");
        MessageManager.sendRawMessage(TextFormatting.WHITE + Momentum.prefix + "toggle [module] - toggles a specific modules");
        MessageManager.sendRawMessage(TextFormatting.WHITE + Momentum.prefix + "bind [module] - binds a specific modules");
        MessageManager.sendRawMessage(TextFormatting.WHITE + Momentum.prefix + "prefix [character] - changes the prefix");
        MessageManager.sendRawMessage(TextFormatting.WHITE + Momentum.prefix + "help - shows all the commands");
    }
}
