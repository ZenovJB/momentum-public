package me.linus.momentum.api.events.mixin.access;

//don't use this for anything but system time

public interface TimerInterface {
        float getTickLength();
        void setTickLength(float length);
    }

