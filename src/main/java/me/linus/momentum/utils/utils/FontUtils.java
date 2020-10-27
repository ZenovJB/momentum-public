package me.linus.momentum.utils.utils;

import me.linus.momentum.utils.Colors;
import me.linus.momentum.Momentum;
import net.minecraft.client.Minecraft;

public class FontUtils {
	private static final Minecraft mc = Minecraft.getMinecraft();
	
	public static float drawStringWithShadow(boolean customFont, String text, int x, int y, Colors color){
		if(customFont) return Momentum.fontRenderer.drawStringWithShadow(text, x, y, color);
		else return mc.fontRenderer.drawStringWithShadow(text, x, y, color.getRGB());
	}

	public static float drawString(boolean customFont, String text, int x, int y, Colors color){
		if(customFont) return Momentum.fontRenderer.drawString(text, x, y, color);
		else return mc.fontRenderer.drawString(text, x, y, color.getRGB());
	}

	public static int getStringWidth(boolean customFont, String str){
		if (customFont) return Momentum.fontRenderer.getStringWidth(str);
		else return mc.fontRenderer.getStringWidth(str);
	}

	public static int getFontHeight(boolean customFont){
		if (customFont) return Momentum.fontRenderer.getHeight();
		else return mc.fontRenderer.FONT_HEIGHT;
	}
}