/*
package me.linus.momentum.gui.hud;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import me.linus.momentum.gui.hud.component.HUDComponent;
import me.linus.momentum.gui.hud.component.HUDFrame;
import me.linus.momentum.gui.hud.component.HUDCategory;
import net.minecraft.client.gui.GuiScreen;

public class HUD extends GuiScreen {

    public static ArrayList<HUDFrame> frames;
    public static int color = -1;
    public static int framecolor = new Color(38, 41, 39).getRGB();

    public HUD() {
        this.frames = new ArrayList<HUDFrame>();
        int frameX = 5;
        for (HUDCategory hudcategory : HUDCategory.values()) {
            HUDFrame frame = new Frame(hudcategory);
            frame.setX(frameX);
            frames.add(frame);
            frameX += frame.getWidth() + 1;
        }
    }

    @Override
    public void initGui() {
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        for (HUDFrame frame : frames) {
            frame.renderFrame(this.fontRenderer);
            frame.updatePosition(mouseX, mouseY);
            for (HUDComponent comp : frame.getComponents()) {
                comp.updateComponent(mouseX, mouseY);
            }
        }
    }

    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        for (HUDFrame frame : frames) {
            if (frame.isWithinHeader(mouseX, mouseY) && mouseButton == 0) {
                frame.setDrag(true);
                frame.dragX = mouseX - frame.getX();
                frame.dragY = mouseY - frame.getY();
            }
            if (frame.isWithinHeader(mouseX, mouseY) && mouseButton == 1) {
                frame.setOpen(!frame.isOpen());
            }
            if (frame.isOpen()) {
                if (!frame.getComponents().isEmpty()) {
                    for (HUDComponent component : frame.getComponents()) {
                        component.mouseClicked(mouseX, mouseY, mouseButton);
                    }
                }
            }
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        for (HUDFrame frame : frames) {
            if (frame.isOpen() && keyCode != 1) {
                if (!frame.getComponents().isEmpty()) {
                    for (HUDComponent component : frame.getComponents()) {
                        component.keyTyped(typedChar, keyCode);
                    }
                }
            }
        }
        if (keyCode == 1) {
            this.mc.displayGuiScreen(null);
        }
    }


    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        for (HUDFrame frame : frames) {
            frame.setDrag(false);
        }
        for (HUDFrame frame : frames) {
            if (frame.isOpen()) {
                if (!frame.getComponents().isEmpty()) {
                    for (HUDComponent component : frame.getComponents()) {
                        component.mouseReleased(mouseX, mouseY, state);
                    }
                }
            }
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}

 */
