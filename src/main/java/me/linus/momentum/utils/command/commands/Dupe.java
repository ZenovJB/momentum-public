package me.linus.momentum.utils.command.commands;

import me.linus.momentum.utils.command.Command;
import me.linus.momentum.utils.managers.MessageManager;
import net.minecraft.util.text.TextFormatting;

public class Dupe extends Command {
    public Dupe() {
        super("Dupe", new String[]{"dupe"});
    }

    String pos;

    @Override
    public void onCommand(String[] args) {
        if (args.length < 2) {
            try {
                MessageManager.sendRawMessage(TextFormatting.BLUE + "[Momentum Dupe]" + TextFormatting.WHITE + "please set a starting positon (!dupe xyz) ...");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (args.length > 2) {
            try {
                MessageManager.sendRawMessage(TextFormatting.BLUE + "[Momentum Dupe]" + TextFormatting.WHITE + "starting positon set at " + args[2]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        pos = args[2];
    }

    public String getPos(){
        return pos;
    }
}