package me.linus.momentum.gui.component;

import java.util.ArrayList;

import me.linus.momentum.gui.component.components.Button;
import me.linus.momentum.gui.component.render.Render;
import me.linus.momentum.module.Category;
import me.linus.momentum.module.Module;
import me.linus.momentum.module.modules.gui.ClickGUI;
import me.linus.momentum.utils.Colors;
import me.linus.momentum.utils.utils.FontUtils;
import net.minecraft.client.gui.FontRenderer;
import static me.linus.momentum.Momentum.moduleManager;

public class Frame {

	public ArrayList<Component> components;
	public Category category;
	private boolean open;
	private int width;
	private int y;
	private int x;
	private int barHeight;
	private boolean isDragging;
	public int dragX;
	public int dragY;

	public Frame(Category cat) {
		this.components = new ArrayList<Component>();
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
				Button modButton = new Button(mod, this, tY);
				this.components.add(modButton);
				tY += 12;
			}
		}
	}

	public ArrayList<Component> getComponents() {
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
		/*
		int alpha = gui.getAlpha();
		if ((alpha + 40) <= 255) {
			alpha += 40;
		} else {
			alpha = gui.getAlpha();
		}
		 */

		if (gui.getGradient()) {Render.drawRectGradient(this.x, this.y, this.x + this.width, this.y + this.barHeight, new Colors(64, 52, 235), new Colors(235, 159, 52));}
		else {Render.drawRectStatic(this.x, this.y, this.x + this.width, this.y + this.barHeight, new Colors(38, 41, 39));}
		FontUtils.drawStringWithShadow(gui.cfont.getBVal(), this.category.name(), this.x + 2, this.y + 4, new Colors(gui.getRed(), gui.getGreen(), gui.getBlue()));
		if (this.open) {
			if (!this.components.isEmpty()) {
				for (Component component : components) {
					component.renderComponent();
				}
			}
		}
	}

	public void refresh() {
		int off = this.barHeight;
		for (Component comp : components) {
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