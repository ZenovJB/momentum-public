package me.linus.momentum.module.modules.gui;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.linus.momentum.Momentum;
import me.linus.momentum.module.Category;
import me.linus.momentum.module.Module;
import me.linus.momentum.utils.Colors;
import me.linus.momentum.utils.Wrapper;
import me.linus.momentum.utils.utils.FontUtils;
import me.linus.momentum.utils.settings.Setting;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static me.linus.momentum.Momentum.moduleManager;

public class HUD extends Module {
    public HUD() {
        super("HUD", Category.GUI);
    }

    public String coords;
    public int y;

    private Setting rainbow;
    private Setting cfont;
    public Setting cords;
    public Setting welcome;
    public Setting watermark;
    Setting hidden;
    public Setting compass;

    private static final double HALF_PI = Math.PI / 2;
    ScaledResolution resolution = new ScaledResolution(mc);

    final double centerX = resolution.getScaledWidth() * 1.11;
    final double centerY = resolution.getScaledHeight_double() * 1.8;

    @Override
    public void setup() {
        rSetting(rainbow = new Setting("Rainbow", this, false, "rainbow"));
        rSetting(cfont = new Setting("Custom Font", this, true, "cfont"));
        rSetting(cords = new Setting("Coordinates", this, false, "cords"));
        rSetting(welcome = new Setting("Welcomer", this, true, "welcome"));
        rSetting(compass = new Setting("Compass", this, false, ""));
        rSetting(watermark = new Setting("WaterMark", this, false, "watermark"));
        rSetting(hidden = new Setting("Hidden", this, false, "hide"));
    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent event) {
        if (mc.player != null && mc.world != null) {
            if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
                ClickGUI gui = (ClickGUI) moduleManager.getModuleByName("ClickGUI");
                ScaledResolution resolution = new ScaledResolution(mc);
                float[] hue = new float[]{(float) (System.currentTimeMillis() % 11520L) / 11520.0f};
                int rgb = Color.HSBtoRGB(hue[0], 1.0f, 1.0f);
                int red = rgb >> 16 & 255;
                int green = rgb >> 8 & 255;
                int blue = rgb & 255;

                if (cords.getBVal() == true) {
                    y = ((mc.currentScreen instanceof GuiChat) ? 15 : 2);
                    if (mc.player.dimension == -1) {
                        coords = "" + ChatFormatting.WHITE + mc.player.getPosition().getX() + ", " + mc.player.getPosition().getY() + ", " + mc.player.getPosition().getZ() +
                                ChatFormatting.GRAY + " [" + ChatFormatting.WHITE + mc.player.getPosition().getX() * 8 + ", " + mc.player.getPosition().getZ() * 8 + ChatFormatting.GRAY + "]";
                    } else {
                        coords = "" + ChatFormatting.WHITE + mc.player.getPosition().getX() + ", " + mc.player.getPosition().getY() + ", " + Math.floor(mc.player.getPosition().getZ()) +
                                ChatFormatting.GRAY + " [" + ChatFormatting.WHITE + mc.player.getPosition().getX() / 8 + ", " + mc.player.getPosition().getZ() / 8 + ChatFormatting.GRAY + "]";
                    }

                    FontUtils.drawStringWithShadow(cfont.getBVal(), coords, 1, resolution.getScaledHeight() - y - mc.fontRenderer.FONT_HEIGHT, new Colors(gui.getRed(), gui.getGreen(), gui.getBlue()));
                }

                if (welcome.getBVal() == true) {
                    if (rainbow.getBVal() == true)
                        FontUtils.drawStringWithShadow(cfont.getBVal(), "Welcome " + mc.player.getName() + "! :^)", resolution.getScaledWidth() / 2 - mc.fontRenderer.getStringWidth("welcome " + mc.player.getName() + "! :^)") / 2, 2, new Colors(red, green, blue));
                    else {
                        FontUtils.drawStringWithShadow(cfont.getBVal(), "Welcome " + mc.player.getName() + "! :^)", resolution.getScaledWidth() / 2 - mc.fontRenderer.getStringWidth("welcome " + mc.player.getName() + "! :^)") / 2, 2, new Colors(gui.getRed(), gui.getGreen(), gui.getBlue()));
                    }
                }

                if (watermark.getBVal() == true) {
                    ResourceLocation location;
                    location = new ResourceLocation("momentum", "watermark.png");
                    if (rainbow.getBVal() == true) {
                        this.mc.getTextureManager().bindTexture(location);
                        GL11.glPushMatrix();
                        Gui.drawModalRectWithCustomSizedTexture(2, 2, 0, 0, 60, 60, 60, 60);
                        GL11.glScalef(1.7f, 2.0f, 1.5f);
                        FontUtils.drawStringWithShadow(cfont.getBVal(), Momentum.name, 41, 4, new Colors(red, green, blue));
                        GL11.glPopMatrix();
                        GL11.glPushMatrix();
                        GL11.glScalef(1.3f, 1.3f, 1.3f);
                        FontUtils.drawStringWithShadow(cfont.getBVal(), Momentum.fullversion, 57, 22, new Colors(red, green, blue));
                        GL11.glPopMatrix();
                    } else {
                        this.mc.getTextureManager().bindTexture(location);
                        GL11.glPushMatrix();
                        Gui.drawModalRectWithCustomSizedTexture(2, 2, 0, 0, 60, 60, 60, 60);
                        GL11.glScalef(1.7f, 2.0f, 1.5f);
                        FontUtils.drawStringWithShadow(cfont.getBVal(), Momentum.name, 41, 4, new Colors(gui.getRed(), gui.getGreen(), gui.getBlue()));
                        GL11.glPopMatrix();
                        GL11.glPushMatrix();
                        GL11.glScalef(1.3f, 1.3f, 1.3f);
                        FontUtils.drawStringWithShadow(cfont.getBVal(), Momentum.fullversion, 57, 22, new Colors(gui.getRed(), gui.getGreen(), gui.getBlue()));
                        GL11.glPopMatrix();
                    }
                }

                if (compass.getBVal() == true){
                    for (Direction dir : Direction.values()) {
                        double rad = getPosOnCompass(dir);
                        FontUtils.drawStringWithShadow(((ClickGUI) moduleManager.getModuleByName("ClickGUI")).cfont.getBVal(), dir.name(), (int) (centerX + getX(rad)), (int) (centerY + getY(rad)), dir == Direction.N ? new Colors(255, 0, 0, 255) : new Colors(255, 255, 255, 255));
                    }
                }



                else {}

            }

        }
    }

    private double getX(double rad) {
        return Math.sin(rad) * (30);
    }

    private double getY(double rad) {
        final double epicPitch = MathHelper.clamp(Wrapper.getRenderEntity().rotationPitch + 30f, -90f, 90f);
        final double pitchRadians = Math.toRadians(epicPitch);
        return Math.cos(rad) * Math.sin(pitchRadians) * (30);
    }

    private static double getPosOnCompass(Direction dir) {
        double yaw = Math.toRadians(MathHelper.wrapDegrees(Wrapper.getRenderEntity().rotationYaw));
        int index = dir.ordinal();
        return yaw + (index * HALF_PI);
    }

    private enum Direction {
        N,
        W,
        S,
        E
    }


}
