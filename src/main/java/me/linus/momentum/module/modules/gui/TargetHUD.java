package me.linus.momentum.module.modules.gui;

import me.linus.momentum.gui.component.render.Render;
import me.linus.momentum.module.Category;
import me.linus.momentum.module.Module;
import me.linus.momentum.utils.Colors;
import me.linus.momentum.utils.utils.FontUtils;
import me.linus.momentum.utils.utils.MathUtils;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.util.Comparator;
import java.util.Objects;

import static me.linus.momentum.Momentum.moduleManager;

public class TargetHUD extends Module {
    public TargetHUD() {
        super("TargetHUD", Category.GUI);
    }

    String playerinfo;

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent event) {
        ClickGUI gui = (ClickGUI) moduleManager.getModuleByName("ClickGUI");
        Render.drawRectStatic(2, 2, 180, 87, new Colors(0, 0, 0, gui.getAlpha()));

        EntityPlayer e = (EntityPlayer) mc.world.loadedEntityList.stream()
                .filter(entity -> IsValidEntity(entity))
                .map(entity -> (EntityLivingBase) entity)
                .min(Comparator.comparing(c -> mc.player.getDistance(c)))
                .orElse(null);

        if (e == null)
            return;

        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);

        GuiInventory.drawEntityOnScreen(28, 70, 30, 60, 60, e);

        GlStateManager.enableRescaleNormal();
        GlStateManager.enableTexture2D();
        GlStateManager.enableBlend();

        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

        if (e.getEquipmentAndArmor() == Items.ELYTRA) {
            playerinfo = "Wasp";
        }

        if (e.getEquipmentAndArmor() == Items.DIAMOND_CHESTPLATE) {
            playerinfo = "Armored";
        }

        if (e.getEquipmentAndArmor() == null) {
            playerinfo = "NewFag";
        }

        else {
            playerinfo = "None";
        }

        FontUtils.drawStringWithShadow(gui.cfont.getBVal(), e.getName(), 54, 10, new Colors(255, 255, 255));
        FontUtils.drawStringWithShadow(gui.cfont.getBVal(), "Health: " + String.valueOf(e.getHealth() + e.getAbsorptionAmount()), 54, 22, new Colors(255, 255, 255));
        FontUtils.drawStringWithShadow(gui.cfont.getBVal(), "Ping: " + getPing(e) + " ms", 54, 34, new Colors(255, 255, 255));
        FontUtils.drawStringWithShadow(gui.cfont.getBVal(), "Player Info: " + playerinfo, 54, 46, new Colors(255, 255, 255));
        GL11.glPushMatrix();
        GL11.glScalef(0.75f, 0.75f, 0.75f);
        if (e.getHeldItemMainhand().item == Items.END_CRYSTAL) {FontUtils.drawStringWithShadow(gui.cfont.getBVal(), "Target: Crystalling!" , 54, 58, new Colors(255, 255, 255)); }
        if (e.getHeldItemMainhand().item == Items.GOLDEN_APPLE) {FontUtils.drawStringWithShadow(gui.cfont.getBVal(), "Target: Eating Gapple!" , 54, 58, new Colors(255, 255, 255)); }
        if (e.getHeldItemMainhand().item == Items.DIAMOND_SWORD) {FontUtils.drawStringWithShadow(gui.cfont.getBVal(), "Target: Swording!" , 54, 58, new Colors(255, 255, 255)); }
        if (e.getHeldItemMainhand().item == Items.POTIONITEM) {FontUtils.drawStringWithShadow(gui.cfont.getBVal(), "Target: Drinking Potion!" , 54, 58, new Colors(255, 255, 255)); }
        if (e.getHeldItemMainhand().item == Items.ENDER_PEARL) {FontUtils.drawStringWithShadow(gui.cfont.getBVal(), "Target: Using Pearl!" , 54, 58, new Colors(255, 255, 255)); }
        if (e.getHeldItemMainhand().item == Items.DIAMOND_PICKAXE) {FontUtils.drawStringWithShadow(gui.cfont.getBVal(), "Target: Attempting to City!" , 54, 58, new Colors(255, 255, 255)); }
        GL11.glPopMatrix();
    }

        private boolean IsValidEntity (Entity e){
            if (!(e instanceof EntityPlayer)) {
                return false;
            }

            if (e instanceof EntityPlayer) {
                if (e == mc.player) {
                    return false;
                }
            }

            return true;
        }

        public int getPing (EntityPlayer player){
            int ping = 0;
            try {
                ping = (int) MathUtils.clamp((float) Objects.requireNonNull(mc.getConnection()).getPlayerInfo(player.getUniqueID()).getResponseTime(), 1, 300.0f);
            } catch (NullPointerException ignored) {}
            return ping;
        }

    }



