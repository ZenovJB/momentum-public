package me.linus.momentum.utils;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

public class Colors extends Color {
    public Colors (int rgb) {
        super(rgb);
    }

    public Colors (int rgba, boolean hasalpha) {
        super(rgba,hasalpha);
    }

    public Colors (int r, int g, int b) {
        super(r,g,b);
    }

    public Colors (int r, int g, int b, int a) {
        super(r,g,b,a);
    }

    public Colors (Color color) {
        super(color.getRed(),color.getGreen(),color.getBlue(),color.getAlpha());
    }

    public Colors (Colors color, int a) {
        super(color.getRed(),color.getGreen(),color.getBlue(),a);
    }

    public static Colors fromHSB (float hue, float saturation, float brightness) {
        return new Colors(Color.getHSBColor(hue,saturation,brightness));
    }

    public float getHue() {
        return RGBtoHSB(getRed(),getGreen(),getBlue(),null)[0];
    }

    public float getSaturation() {
        return RGBtoHSB(getRed(),getGreen(),getBlue(),null)[1];
    }

    public float getBrightness() {
        return RGBtoHSB(getRed(),getGreen(),getBlue(),null)[2];
    }

    public void glColor() {
        GL11.glColor4f(getRed()/255.0f,getGreen()/255.0f,getBlue()/255.0f,getAlpha()/255.0f);
    }
}