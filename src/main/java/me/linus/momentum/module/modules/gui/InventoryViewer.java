package me.linus.momentum.module.modules.gui;

import me.linus.momentum.gui.component.render.Render;
import me.linus.momentum.module.Category;
import me.linus.momentum.module.Module;
import me.linus.momentum.utils.Colors;
import me.linus.momentum.utils.settings.Setting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class InventoryViewer extends Module {
    public InventoryViewer() {
        super("InventoryPreview", Category.GUI);
    }

    public Setting x;
    public Setting y;
    Setting hidden;

    public void setup() {
        rSetting(x = new Setting("Horizontal", this, 30, 0, 1000, true, ""));
        rSetting(y = new Setting("Vertical", this, 2, 0, 1000, true, ""));
        rSetting(hidden = new Setting("Hidden", this, false, "hide"));
    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Post event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.HOTBAR) {
            return;
        }
            GlStateManager.pushMatrix();
            RenderHelper.enableGUIStandardItemLighting();
            Render.drawRectStatic(x.getDVal(), y.getDVal(), x.getDVal() + 145, y.getDVal() + 48, new Colors(0, 0, 0, 125));
            for (int i = 0; i < 27; i++) {
                final ItemStack itemStack = mc.player.inventory.mainInventory.get(i + 9);
                int offsetX = (x.getDVal() + (i % 9) * 16);
                int offsetY = (y.getDVal() + (i / 9) * 16);
                mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, offsetX, offsetY);
                mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRenderer, itemStack, offsetX, offsetY, null);
            }

            RenderHelper.disableStandardItemLighting();
            mc.getRenderItem().zLevel = 0.0F;
            GlStateManager.popMatrix();
        }
}
