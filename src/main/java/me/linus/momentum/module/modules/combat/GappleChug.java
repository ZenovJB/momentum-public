package me.linus.momentum.module.modules.combat;

import me.linus.momentum.module.Category;
import me.linus.momentum.module.Module;
import me.linus.momentum.utils.settings.Setting;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Items;
import net.minecraft.item.*;

public class GappleChug extends Module {
    public GappleChug() {
        super("GappleChug", Category.Combat);
    }

    private boolean isEating = false;

    Setting chorus;
    Setting hidden;

    @Override
    public void setup(){
        rSetting(chorus = new Setting("Disable On Chorus", this, true, ""));
        rSetting(hidden = new Setting("Hidden", this, false, "hide"));
    }

    @Override
    public void onEnable(){
        mc.player.inventory.currentItem = findGapple();
    }

    @Override
    public void onUpdate() {
        Item itemMainHand = mc.player.getHeldItemMainhand().getItem();
        Item itemONotMainHand = mc.player.getHeldItemOffhand().getItem();
        boolean gapInMainHand = itemMainHand instanceof ItemAppleGold;
        boolean gapNotInMainHand = itemONotMainHand instanceof ItemAppleGold;
        /*
        boolean chorusInMainHand = itemMainHand instanceof ItemChorusFruit;
        boolean chorusNotInMainHand = itemONotMainHand instanceof ItemChorusFruit;
         */
            isEating = true;
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true);
            mc.rightClickMouse();
    }

    private int findGapple() {
        int slot = 0;
        for (int i = 0; i < 9; i++) {
            if (mc.player.inventory.getStackInSlot(i).getItem() == Items.GOLDEN_APPLE) {
                slot = i;
                break;
            }
        }
        return slot;
    }
}
