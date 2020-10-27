package me.linus.momentum.utils.command;

import me.linus.momentum.utils.command.commands.*;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import me.linus.momentum.Momentum;

import java.util.HashSet;

public class CommandManager {
    public static HashSet<Command> commands = new HashSet<>();

    public static void init() {
        commands.clear();
        commands.add(new Toggle());
        commands.add(new Bind());
        commands.add(new Prefix());
        commands.add(new Help());
        commands.add(new Dupe());
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void chatEvent(ClientChatEvent event) {
        String[] args = event.getMessage().split(" ");
        if (event.getMessage().startsWith(Momentum.prefix)) {
            event.setCanceled(true);
            for (Command c: commands){
                if (args[0].equalsIgnoreCase(Momentum.prefix + c.getCommand())){
                    c.onCommand(args);
                }
            }
        }
    }
}
