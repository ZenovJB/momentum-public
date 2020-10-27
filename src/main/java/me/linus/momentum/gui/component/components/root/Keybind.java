package me.linus.momentum.gui.component.components.root;

import me.linus.momentum.Momentum;
import me.linus.momentum.gui.component.render.Render;
import me.linus.momentum.module.modules.gui.ClickGUI;
import me.linus.momentum.utils.Colors;
import me.linus.momentum.utils.utils.FontUtils;
import org.lwjgl.input.Keyboard;

import me.linus.momentum.gui.component.Component;
import me.linus.momentum.gui.component.components.Button;

import static me.linus.momentum.Momentum.moduleManager;

public class Keybind extends Component {

	private boolean hovered;
	private boolean binding;
	private Button parent;
	private int offset;
	private int x;
	private int y;
	
	public Keybind(Button button, int offset) {
		this.parent = button;
		this.x = button.parent.getX() + button.parent.getWidth();
		this.y = button.parent.getY() + button.offset;
		this.offset = offset;
	}
	
	@Override
	public void setOff(int newOff) {
		offset = newOff;
	}
	
	@Override
	public void renderComponent() {
		ClickGUI gui = (ClickGUI) moduleManager.getModuleByName("ClickGUI");
		Render.drawRectStatic(parent.parent.getX(), parent.parent.getY() + offset, parent.parent.getX() + (parent.parent.getWidth() * 1), parent.parent.getY() + offset + 12, this.hovered ? new Colors(56, 56, 56, gui.getAlpha()) : new Colors(36, 36, 36, gui.getAlpha()));
		FontUtils.drawStringWithShadow(gui.cfont.getBVal(), binding ? "Select a Key ..." : ("Bind: " + Keyboard.getKeyName(this.parent.mod.getKey())), (parent.parent.getX() + 4), (parent.parent.getY() + offset + 2), new Colors(255, 255, 255));
	}
	
	@Override
	public void updateComponent(int mouseX, int mouseY) {
		this.hovered = isMouseOnButton(mouseX, mouseY);
		this.y = parent.parent.getY() + offset;
		this.x = parent.parent.getX();
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) {
		if (isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.open) {
			this.binding = !this.binding;
		}
	}
	
	@Override
	public void keyTyped(char typedChar, int key) {
		if (this.binding) {
			this.parent.mod.setKey(key);
			this.binding = false;
			Momentum.configManager.SaveAll();
		}
	}
	
	public boolean isMouseOnButton(int x, int y) {
		if (x > this.x && x < this.x + 88 && y > this.y && y < this.y + 12) {
			return true;
		}
		return false;
	}
}
