package me.linus.momentum.utils.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.linus.momentum.Momentum;
import me.linus.momentum.utils.command.Command;
import me.linus.momentum.utils.managers.MessageManager;
import org.lwjgl.input.Keyboard;

public class Prefix extends Command {
    public Prefix() {
        super("Prefix", new String[]{"prefix"});
    }

    @Override
    public void onCommand(String[] args) {
        if (args.length > 1) {
            try {
                Momentum.prefix = args[1];
                    MessageManager.sendMessagePrefix(ChatFormatting.LIGHT_PURPLE + "Default Prefix" + ChatFormatting.WHITE + " is now bound to " + ChatFormatting.RED + args[2].toUpperCase() + ChatFormatting.GRAY + " (" + ChatFormatting.WHITE + Keyboard.getKeyIndex(args[2].toUpperCase() + "") + ChatFormatting.GRAY + ")");
                } catch (Exception e) {
                    MessageManager.sendMessagePrefix(ChatFormatting.LIGHT_PURPLE + "Default Prefix" + ChatFormatting.WHITE + " is now bound to " + ChatFormatting.RED + args[2].toUpperCase() + ChatFormatting.GRAY + " (" + ChatFormatting.WHITE + Keyboard.getKeyIndex(args[2].toUpperCase() + "") + ChatFormatting.GRAY + ")");
                }
            }
        }
    }

