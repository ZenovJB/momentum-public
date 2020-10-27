package me.linus.momentum.module.modules.combat;

import me.linus.momentum.module.Category;
import me.linus.momentum.module.Module;
import me.linus.momentum.utils.settings.Setting;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public class QuickEXP extends Module {
    public QuickEXP() {
        super("QuickEXP", Category.Combat);
    }

    public Setting mode;
    Setting hidden;

    @Override
    public void setup() {
        ArrayList<String> option = new ArrayList<>();
        option.add("Throw");
        option.add("AutoMend");

        rSetting(mode = new Setting("Mode", this, "AutoMend", option, ""));
        rSetting(hidden = new Setting("Hidden", this, false, "hide"));
    }

    @Override
    public void onUpdate() {
        Item itemMainHand = mc.player.getHeldItemMainhand().getItem();
        Item itemONotMainHand = mc.player.getHeldItemOffhand().getItem();
        boolean expInMainHand = itemMainHand instanceof ItemExpBottle;
        boolean expNotInMainHand = itemONotMainHand instanceof ItemExpBottle;
        int ArmorDurability = getArmorDurability();

        if (mode.getSVal() == "Throw") {
            if (expInMainHand | expNotInMainHand) {
                mc.rightClickDelayTimer = 0;
            }
        }

        if (mode.getSVal() == "AutoMend") {
            if (mc.player.isSneaking() && 0 < ArmorDurability) {
                mc.player.inventory.currentItem = findExpInHotbar();
                mc.player.connection.sendPacket(new CPacketPlayer.Rotation(0, 90, true));
                mc.rightClickDelayTimer = 0;
                mc.rightClickMouse();
            }

            super.onUpdate();
        }
    }


    private int findExpInHotbar() {
        int slot = 0;
        for (int i = 0; i < 9; i++) {
            if (mc.player.inventory.getStackInSlot(i).getItem() == Items.EXPERIENCE_BOTTLE) {
                slot = i;
                break;
            }
        }
        return slot;
    }

    private int getArmorDurability() {
        int TotalDurability = 0;

        for (ItemStack itemStack : mc.player.inventory.armorInventory) {
            TotalDurability = TotalDurability + itemStack.getItemDamage();
        }
        return TotalDurability;
    }

    @Override
    public String getHudInfo() {
        return " \u00A77[\u00A7f" + this.mode.getSVal() + "\u00A77]";
    }
}

