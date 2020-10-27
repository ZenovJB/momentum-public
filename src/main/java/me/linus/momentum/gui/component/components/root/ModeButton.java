package me.linus.momentum.gui.component.components.root;

import me.linus.momentum.Momentum;
import me.linus.momentum.gui.component.render.Render;
import me.linus.momentum.module.Module;
import me.linus.momentum.module.modules.gui.ClickGUI;
import me.linus.momentum.utils.Colors;
import me.linus.momentum.utils.utils.FontUtils;
import me.linus.momentum.gui.component.Component;
import me.linus.momentum.gui.component.components.Button;
import me.linus.momentum.utils.settings.Setting;

import static me.linus.momentum.Momentum.moduleManager;

public class ModeButton extends Component {

	private boolean hovered;
	private Button parent;
	private Setting set;
	private int offset;
	private int x;
	private int y;
	private Module mod;

	private int modeIndex;
	
	public ModeButton(Setting set, Button button, Module mod, int offset) {
		this.set = set;
		this.parent = button;
		this.mod = mod;
		this.x = button.parent.getX() + button.parent.getWidth();
		this.y = button.parent.getY() + button.offset;
		this.offset = offset;
		this.modeIndex = 0;
	}
	
	@Override
	public void setOff(int newOff) {
		offset = newOff;
	}
	
	@Override
	public void renderComponent() {
		ClickGUI gui = (ClickGUI) moduleManager.getModuleByName("ClickGUI");
		Render.drawRectStatic(parent.parent.getX(), parent.parent.getY() + offset, parent.parent.getX() + (parent.parent.getWidth() * 1), parent.parent.getY() + offset + 12, this.hovered ? new Colors(56, 56, 56, gui.getAlpha()) : new Colors(36, 36, 36, gui.getAlpha()));
		FontUtils.drawStringWithShadow(gui.cfont.getBVal(),"Mode: " + set.getSVal(), (parent.parent.getX() + 4), (parent.parent.getY() + offset + 2), new Colors(255, 255, 255));
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
			int maxIndex = set.getOptions().size();

			if (modeIndex + 1 >= maxIndex)
				modeIndex = 0;
			else
				modeIndex += 1;

			set.setValString(set.getOptions().get(modeIndex));
		}
		Momentum.configManager.SaveAll();
	}
	
	public boolean isMouseOnButton(int x, int y) {
		if (x > this.x && x < this.x + 88 && y > this.y && y < this.y + 12) {
			return true;
		}
		return false;
	}
}
