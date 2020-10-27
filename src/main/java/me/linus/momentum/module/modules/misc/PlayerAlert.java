package me.linus.momentum.module.modules.misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.linus.momentum.module.Category;
import me.linus.momentum.module.Module;
import me.linus.momentum.utils.managers.MessageManager;
import me.linus.momentum.utils.settings.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class PlayerAlert extends Module {
    public PlayerAlert() {
        super("PlayerAlert", Category.Miscellaneous);
    }

    public int playerDelay;
    Setting hidden;

    @Override
    public void setup(){
        rSetting(hidden = new Setting("Hidden", this, false, "hide"));
    }


    public void onUpdate() {

        ++playerDelay;

        if (mc.world != null && mc.player != null) {

            for (Entity entity : mc.world.getLoadedEntityList()) {
                if (entity instanceof EntityPlayer && this.playerDelay >= 100) {
                    MessageManager.sendMessagePrefix(ChatFormatting.GRAY + "[" + ChatFormatting.LIGHT_PURPLE + "PlayerRangeLogger" + ChatFormatting.GRAY + "] " + ChatFormatting.WHITE + "Found a " + ChatFormatting.AQUA + "player " + ChatFormatting.WHITE + "at " + ChatFormatting.GRAY + "[" + ChatFormatting.WHITE + Math.round(entity.lastTickPosX) + ChatFormatting.GRAY + ", " + ChatFormatting.WHITE + Math.round(entity.lastTickPosY) + ChatFormatting.GRAY + ", " + ChatFormatting.WHITE + Math.round(entity.lastTickPosZ) + ChatFormatting.GRAY + "]");
                }
            }
        }
    }
}