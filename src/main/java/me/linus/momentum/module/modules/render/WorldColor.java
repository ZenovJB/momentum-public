package me.linus.momentum.module.modules.render;

import me.linus.momentum.module.Category;
import me.linus.momentum.module.Module;
import me.linus.momentum.utils.settings.Setting;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;

public class WorldColor extends Module {
    public WorldColor() {
        super("WorldColor", Category.Render);
    }

    public Setting red;
    public Setting green;
    public Setting blue;
    public Setting rainbow;
    Setting hidden;

    @Override
    public void setup(){
        rSetting(red = new Setting("Red", this, 0, 0, 255, true, "red"));
        rSetting(green = new Setting("Green", this, 0, 0, 255, true, "green"));
        rSetting(blue = new Setting("Blue", this, 0, 0, 255, true, "blue"));
        rSetting(rainbow = new Setting("Rainbow", this, false, "rainbow"));
        rSetting(hidden = new Setting("Hidden", this, false, "hide"));
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onFogColorRender(EntityViewRenderEvent.FogColors event) {
        float[] hue = new float[]{(float) (System.currentTimeMillis() % 11520L) / 11520.0f};
        int rgb = Color.HSBtoRGB(hue[0], 1.0f, 1.0f);
        int r = rgb >> 16 & 255;
        int g = rgb >> 8 & 255;
        int b = rgb & 255;
        if (rainbow.getBVal()) {
            event.setRed( r / 255f);
            event.setGreen( g / 255f);
            event.setBlue( b / 255f);
        } else {
            event.setRed(red.getDVal() / 255f);
            event.setGreen(green.getDVal() / 255f);
            event.setBlue(blue.getDVal() / 255f);
        }
    }

    @SubscribeEvent
    public void fog(EntityViewRenderEvent.FogDensity event) {
        event.setDensity(0);
        event.setCanceled(true);
    }
}
