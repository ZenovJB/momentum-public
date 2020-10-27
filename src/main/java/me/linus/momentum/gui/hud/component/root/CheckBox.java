/*
package me.linus.momentum.gui.hud.component.root;

import java.awt.*;
import java.util.ArrayList;

import me.linus.momentum.gui.component.components.Button;
import me.linus.momentum.module.modules.gui.ClickGUI;
import me.linus.momentum.gui.component.Component;
import me.linus.momentum.utils.Colors;
import me.linus.momentum.utils.utils.FontUtils;
import me.linus.momentum.utils.settings.Setting;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import static me.linus.momentum.Momentum.moduleManager;

public class CheckBox extends Component {

    public Setting setting;
    public Button parent;
    public int color;
    public int offset;
    private boolean isHovered;
    private ArrayList<Component> rootcomponents;
    public boolean open;
    private int height;
    public int red;

    public CheckBox(Setting setting, Button parent, int offset) {
        this.setting = setting;
        this.parent = parent;
        this.offset = offset;
        this.rootcomponents = new ArrayList<Component>();
        this.open = false;
        height = 12;
        int opY = offset + 12;
    }

    @Override
    public void setOff(int newOff) {
        offset = newOff;
        int opY = offset + 12;
        for(Component comp : this.rootcomponents) {
            comp.setOff(opY);
            opY += 12;
        }
    }



    @Override
    public void renderComponent() {
        ClickGUI gui = (ClickGUI) moduleManager.getModuleByName("ClickGUI");
        if (this.setting.getBVal() == true) {
            this.color =  new Color(gui.getRed(), gui.getGreen(), gui.getBlue()).getRGB();
        }
        else {
            if (this.isHovered){
                this.color = new Color(56, 56, 56).getRGB();
            }
            else {
                this.color = new Color(36, 36, 36).getRGB();
            }
        }
        Gui.drawRect(parent.parent.getX(), this.parent.parent.getY() + this.offset, parent.parent.getX() + parent.parent.getWidth(), this.parent.parent.getY() + 12 + this.offset, color);
        FontUtils.drawStringWithShadow(gui.cfont.getBVal(), this.setting.getDisplayName(), (parent.parent.getX() + 4), (parent.parent.getY() + offset + 2), this.setting.isEnabled() ? new Colors(255, 255, 255) : new Colors(255, 255, 255));
    }



    @Override
    public int getHeight() {
        if (this.open) {
            return (12 * (this.rootcomponents.size() + 1));
        }
        return 12;
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        this.isHovered = isMouseOnButton(mouseX, mouseY);
        if (!this.rootcomponents.isEmpty()) {
            for (Component comp : this.rootcomponents) {
                comp.updateComponent(mouseX, mouseY);
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (isMouseOnButton(mouseX, mouseY) && button == 0) {
            this.setting.toggle();
        }
        if (isMouseOnButton(mouseX, mouseY) && button == 1) {
            this.open = !this.open;
            this.parent.parent.refresh();
        }
        for (Component comp : this.rootcomponents) {
            comp.mouseClicked(mouseX, mouseY, button);
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        for (Component comp : this.rootcomponents) {
            comp.mouseReleased(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void keyTyped(char typedChar, int key) {
        for (Component comp : this.rootcomponents) {
            comp.keyTyped(typedChar, key);
        }
    }

    public boolean isMouseOnButton(int x, int y) {
        if (x > parent.parent.getX() && x < parent.parent.getX() + parent.parent.getWidth() && y > this.parent.parent.getY() + this.offset && y < this.parent.parent.getY() + 12 + this.offset) {
            return true;
        }
        return false;
    }
}

 */
