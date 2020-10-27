package me.linus.momentum.module.modules.combat;

import me.linus.momentum.module.Category;
import me.linus.momentum.module.Module;
import me.linus.momentum.utils.settings.Setting;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class Quiver extends Module {
    public Quiver() {super("Quiver", Category.Combat);}

    public Setting strength;
    public Setting speed;
    Setting hidden;

    public boolean hasSpeed = false;
    public boolean hasStrength = false;

    @Override
    public void setup(){
        rSetting(strength = new Setting("Strength", this, true, ""));
        rSetting(speed = new Setting("Speed", this, true, ""));
        rSetting(hidden = new Setting("Hidden", this, false, "hide"));
    }

    private int findBowInHotbar() {
        int slot = 0;
        for (int i = 0; i < 9; i++) {
            if (mc.player.inventory.getStackInSlot(i).getItem() == Items.BOW) {
                slot = i;
                break;
            }
        }
        return slot;
    }

    private boolean ifArrowInHotbar() {
        boolean inInv = false;
        for (int i = 0; i < 9; i++) {
            if (mc.player.inventory.getStackInSlot(i).getItem() == Items.TIPPED_ARROW) {
                inInv = true;
                break;
            }
        }
        return inInv;
    }

    @Override
    public void onUpdate() {
        PotionEffect speedEffect = mc.player.getActivePotionEffect(Potion.getPotionById(1));
        PotionEffect strengthEffect = mc.player.getActivePotionEffect(Potion.getPotionById(5));

        if (speedEffect != null) {
            hasSpeed = true;
        }

        if (strengthEffect != null) {
            hasStrength = true;
        }

        if (strength.getBVal() == true) {
            if (this.isToggled() && hasStrength == false && ifArrowInHotbar() == true) {
                mc.player.inventory.currentItem = findBowInHotbar();
                mc.player.connection.sendPacket(new CPacketPlayer.Rotation(0, 90, true));
                if (mc.player.getHeldItemMainhand().getItem() instanceof ItemBow && mc.player.getItemInUseMaxCount() >= 3) {
                    mc.player.connection.sendPacket(new CPacketPlayerTryUseItem());
                }
            }

            if (speed.getBVal() == true) {
                if (this.isToggled() && hasSpeed == false && ifArrowInHotbar() == true) {
                    mc.player.inventory.currentItem = findBowInHotbar();
                    mc.player.connection.sendPacket(new CPacketPlayer.Rotation(0, 90, true));
                    if (mc.player.getHeldItemMainhand().getItem() instanceof ItemBow && mc.player.getItemInUseMaxCount() >= 3) {
                        mc.player.connection.sendPacket(new CPacketPlayerTryUseItem());
                    }
                }

            }

        }


    }
}
