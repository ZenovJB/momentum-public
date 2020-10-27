package me.linus.momentum.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;
import net.minecraft.entity.Entity;

public class Wrapper {
    protected static Minecraft mc = Minecraft.getMinecraft();

    private static FontRenderer fontRenderer;

    public static Minecraft getMinecraft() {
        return Minecraft.getMinecraft();
    }
    public static EntityPlayerSP getPlayer() {
        return getMinecraft().player;
    }
    public static World getWorld() {
        return getMinecraft().world;
    }
    public static int getKey(String keyname){
        return Keyboard.getKeyIndex(keyname.toUpperCase());
    }
    public static Entity getRenderEntity() { return mc.getRenderViewEntity();
    }
}