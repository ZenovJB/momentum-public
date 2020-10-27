package me.linus.momentum.gui.component.components;

import java.util.ArrayList;

import me.linus.momentum.gui.component.render.Render;
import me.linus.momentum.module.modules.gui.ClickGUI;
import me.linus.momentum.gui.component.Component;
import me.linus.momentum.gui.component.Frame;
import me.linus.momentum.gui.component.components.root.*;
import me.linus.momentum.utils.Colors;
import me.linus.momentum.utils.utils.FontUtils;
import me.linus.momentum.utils.settings.Setting;
import me.linus.momentum.module.Module;

import static me.linus.momentum.Momentum.moduleManager;
import static me.linus.momentum.Momentum.settingsManager;

public class Button extends Component {

	public Module mod;
	public Frame parent;
	public int offset;
	private boolean isHovered;
	private ArrayList<Component> rootcomponents;
	public boolean open;
	private int height;
	public int red;
	
	public Button(Module mod, Frame parent, int offset) {
		this.mod = mod;
		this.parent = parent;
		this.offset = offset;
		this.rootcomponents = new ArrayList<Component>();
		this.open = false;
		height = 12;
		int opY = offset + 12;
		if (settingsManager.getSettingsByMod(mod) != null) {
			for (Setting s : settingsManager.getSettingsByMod(mod)){
				if (s.isCombo()){
					this.rootcomponents.add(new ModeButton(s, this, mod, opY));
					opY += 12;
				}
				if (s.isSlider()){
					this.rootcomponents.add(new Slider(s, this, opY));
					opY += 12;
				}
				if (s.isCheck()){
					this.rootcomponents.add(new Checkbox(s, this, opY));
					opY += 12;
				}
			}
		}
		this.rootcomponents.add(new Keybind(this, opY));
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
		Render.drawRectStatic(parent.getX(), this.parent.getY() + this.offset, parent.getX() + parent.getWidth(), this.parent.getY() + 12 + this.offset, this.isHovered ? (this.mod.isToggled() ? new Colors(gui.getRed(), gui.getGreen(), gui.getBlue(), gui.getAlpha()) : new Colors(34, 34, 34, gui.getAlpha())) : (this.mod.isToggled() ? new Colors(gui.getRed(), gui.getGreen(), gui.getBlue(), gui.getAlpha()) : new Colors(17, 17, 17, gui.getAlpha())));
		FontUtils.drawStringWithShadow(gui.cfont.getBVal(), this.mod.getName(), parent.getX() + 2, this.parent.getY() + this.offset + 2, this.mod.isToggled() ? new Colors(255, 255, 255) : new Colors(255, 255, 255));
		if (this.rootcomponents.size() > 2)
		if (this.open) {
			if (!this.rootcomponents.isEmpty()) {
				for (Component comp : this.rootcomponents) {
					comp.renderComponent();
				}
			}
		}
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
			this.mod.toggle();
		}
		if (isMouseOnButton(mouseX, mouseY) && button == 1) {
			this.open = !this.open;
			this.parent.refresh();
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
		if (x > parent.getX() && x < parent.getX() + parent.getWidth() && y > this.parent.getY() + this.offset && y < this.parent.getY() + 12 + this.offset) {
			return true;
		}
		return false;
	}
}
