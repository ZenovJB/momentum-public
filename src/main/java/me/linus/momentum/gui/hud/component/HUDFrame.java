/*
package me.linus.momentum.gui.hud.component;

import java.awt.*;
import java.util.ArrayList;

import me.linus.momentum.gui.GUI;
import me.linus.momentum.gui.component.components.Button;
import me.linus.momentum.module.Category;
import me.linus.momentum.module.Module;
import me.linus.momentum.module.modules.gui.ClickGUI;
import me.linus.momentum.utils.Colors;
import me.linus.momentum.utils.utils.FontUtils;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

import static me.linus.momentum.Momentum.moduleManager;

public class HUDFrame {

    public ArrayList<HUDComponent> components;
    public Category category;
    private boolean open;
    private int width;
    private int y;
    private int x;
    private int barHeight;
    private boolean isDragging;
    public int dragX;
    public int dragY;

    public HUDFrame(Category cat) {
        this.components = new ArrayList<HUDComponent>();
        this.category = cat;
        this.width = 126;
        this.x = 5;
        this.y = 5;
        this.barHeight = 18;
        this.dragX = 0;
        this.open = true;
        this.isDragging = false;
        int tY = this.barHeight;

        for (Module mod : moduleManager.getModules()) {
            if (mod.getCategory().equals(category)){
                HUDButton modButton = new HUDButton(mod, this, tY);
                this.components.add(modButton);
                tY += 12;
            }
        }
    }

    public ArrayList<HUDComponent> getComponents() {
        return components;
    }

    public void setX(int newX) {
        this.x = newX;
    }

    public void setY(int newY) {
        this.y = newY;
    }

    public void setDrag(boolean drag) {
        this.isDragging = drag;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public void renderFrame(FontRenderer fontRenderer) {
        ClickGUI gui = (ClickGUI) moduleManager.getModuleByName("ClickGUI");
        Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.barHeight, GUI.framecolor);
        FontUtils.drawStringWithShadow(gui.cfont.getBVal(), this.category.name(), this.x + 2, this.y + 4, new Colors(gui.getRed(), gui.getGreen(), gui.getBlue()));
        FontUtils.drawStringWithShadow(gui.cfont.getBVal(), this.open ? "-" : "+", (this.x + this.width - 8), this.y + 4, new Colors(255, 255, 255));
        if (this.open) {
            if (!this.components.isEmpty()) {
                for (HUDComponent component : components) {
                    component.renderComponent();
                }
            }
        }
    }

    public void refresh() {
        int off = this.barHeight;
        for (HUDComponent comp : components) {
            comp.setOff(off);
            off += comp.getHeight();
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public void updatePosition(int mouseX, int mouseY) {
        if (this.isDragging) {
            this.setX(mouseX - dragX);
            this.setY(mouseY - dragY);
        }
    }

    public boolean isWithinHeader(int x, int y) {
        if (x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.barHeight) {
            return true;
        }
        return false;
    }
}

 */