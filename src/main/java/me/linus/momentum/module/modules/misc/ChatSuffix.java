package me.linus.momentum.module.modules.misc;

import me.linus.momentum.Momentum;
import me.linus.momentum.module.Category;
import me.linus.momentum.module.Module;
import me.linus.momentum.utils.settings.Setting;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChatSuffix extends Module {
    public ChatSuffix() {
        super("ChatSuffix", "Adds a suffix to the end of your messages", Category.Miscellaneous);
    }

    Setting hidden;

    @Override
    public void setup(){
        rSetting(hidden = new Setting("Hidden", this, false, "hide"));
    }

    @Override
    public void onEnable() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    @SubscribeEvent
    public void onChat(ClientChatEvent event) {
        String suffix = " \uff5c " + "\u1d0d\u1d0f\u1d0d\u1d07\u0274\u1d1b\u1d1c\u1d0d";
        if (event.getMessage().startsWith("/")) return;
        if (event.getMessage().startsWith(Momentum.prefix)) return;
        if (event.getMessage().contains(suffix)) return;
        event.setMessage(event.getMessage() + suffix);
    }
}