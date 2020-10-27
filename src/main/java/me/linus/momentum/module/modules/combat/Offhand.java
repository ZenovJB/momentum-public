package me.linus.momentum.module.modules.combat;

import me.linus.momentum.Momentum;
import me.linus.momentum.module.Category;
import me.linus.momentum.module.Module;
import me.linus.momentum.utils.settings.Setting;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;

import java.util.ArrayList;

public class Offhand extends Module {

    public Setting swordGap;
    public Setting minHealth;
    public Setting mode;
    Setting hidden;

    public Offhand() {
        super("Offhand", Category.Combat);
    }

    @Override public void setup() {
        ArrayList<String> option = new ArrayList<>();
        option.add("Crystal");
        option.add("Gapple");
        option.add("Totem");

        rSetting(swordGap = new Setting("Sword Gap", this, true, "swordGap"));
        rSetting(minHealth = new Setting("SwitchHealth", this, 16.0, 0.0, 36.0, false, "switchoff"));
        rSetting(mode = new Setting("Mode", this, "Gapple", option, "mode"));
        rSetting(hidden = new Setting("Hidden", this, false, "hide"));
    }

    @Override public void onUpdate() {
        if (mc.player == null || mc.world == null) return;

        int itemSlot = getItemSlot();
        if (itemSlot == -1) return;

        mc.playerController.windowClick(mc.player.inventoryContainer.windowId, itemSlot, 0, ClickType.PICKUP, mc.player);
        mc.playerController.windowClick(mc.player.inventoryContainer.windowId, 45, 0, ClickType.PICKUP, mc.player);
        mc.playerController.windowClick(mc.player.inventoryContainer.windowId, itemSlot, 0, ClickType.PICKUP, mc.player);
    }

    private int getItemSlot() {
        Item itemToSearch = Items.TOTEM_OF_UNDYING;

        if (!(mc.player.getHealth() + mc.player.getAbsorptionAmount() <= minHealth.getDVal())) {
            if (swordGap.getBVal() && mc.player.getHeldItemMainhand().getItem() == Items.DIAMOND_SWORD) {
                itemToSearch = Items.GOLDEN_APPLE;
            } else {
                switch (mode.getSVal()) {
                    case "Crystal":
                        itemToSearch = Items.END_CRYSTAL;
                        break;
                    case "Gapple":
                        itemToSearch = Items.GOLDEN_APPLE;
                        break;
                }

                if (itemToSearch == mc.player.getHeldItemOffhand().getItem()) {
                    return -1;
                }
            }
        }

        for (int i = 9; i < 36; i++) {
            if (mc.player.inventory.getStackInSlot(i).getItem() == itemToSearch) {
                return i < 9 ? i + 36 : i;
            }
        }

        return -1;
    }

    @Override
    public String getHudInfo() {
        return " \u00A77[\u00A7f" + this.mode.getSVal() + "\u00A77]";
    }
}