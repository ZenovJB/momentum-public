package me.linus.momentum.api.events.mixin.access;

//don't use this for anything but system time

import net.minecraft.util.Timer;

public interface MinecraftInterface {
    Timer getTimer();
}
