package me.linus.momentum.module.modules.render;

import me.linus.momentum.module.Category;
import me.linus.momentum.module.Module;
import me.linus.momentum.utils.settings.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;

public class ESP extends Module {
    public ESP() {
        super("ESP", Category.Render);
    }

    int opacityGradient;

    Setting mode;
    Setting red;
    Setting green;
    Setting blue;
    Setting linewidth;
    Setting hidden;
    Setting crystal;
    Setting players;
    Setting pearls;
    Setting exp;

    // don't use optifine when using outline esp

    @Override
    public void setup(){
        ArrayList<String> options = new ArrayList<>();
        options.add("Glow");
        options.add("Outline");

        rSetting(red = new Setting("Red", this, 255, 0, 255, true, "r"));
        rSetting(green = new Setting("Green", this, 255, 0, 255, true, "g"));
        rSetting(blue = new Setting("Blue", this, 255, 0, 255, true, "b"));
        rSetting(linewidth = new Setting("Line Width", this, 5, 0, 7, false, "width"));
        rSetting(mode = new Setting("Mode", this, "Outline", options, ""));
        rSetting(players = new Setting("Players", this, true, "hide"));
        rSetting(pearls = new Setting("Pearls", this, false, "hide"));
        rSetting(exp = new Setting("Experience", this, false, "hide"));
        rSetting(crystal = new Setting("Crystal", this, true, "hide"));
        rSetting(hidden = new Setting("Hidden", this, false, "hide"));
    }

    public int getRed() {
        return red.getDVal();
    }

    public int getGreen() {
        return green.getDVal();
    }

    public int getBlue() {
        return blue.getDVal();
    }

    public float getWidth() {
        return linewidth.getDVal();
    }

    public String getMode(){return mode.getSVal();}

    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event){
        mc.world.loadedEntityList.stream().filter(entity -> entity != mc.player).filter(entity -> rangeEntityCheck(entity)).forEach(entity -> {
            if (crystal.getBVal() && entity instanceof EntityEnderCrystal) {
                entity.setGlowing(true);
            }

            if (!crystal.getBVal() && entity instanceof EntityEnderCrystal && entity.isGlowing()) {
                entity.setGlowing(false);
            }

            if (mode.getSVal() == "Glow" && players.getBVal() && entity instanceof EntityPlayer) {
                entity.setGlowing(true);
            }

            if (!(mode.getSVal() == "Glow") && players.getBVal() && entity instanceof EntityPlayer && entity.isGlowing()) {
                entity.setGlowing(false);
            }
        });
    }

    public void onDisable(){
        mc.world.loadedEntityList.stream().forEach(entity -> {
            if (entity instanceof EntityEnderCrystal && entity.isGlowing()){
                entity.setGlowing(false);
            }

            if (entity instanceof EntityPlayer && entity.isGlowing()){
                entity.setGlowing(false);
            }
        });
    }

    private boolean rangeEntityCheck(Entity entity) {
        if (entity.getDistance(mc.player) > 100){
            return false;
        }

        if (entity.getDistance(mc.player) >= 180){
            opacityGradient = 50;
        }
        else if (entity.getDistance(mc.player) >= 130 && entity.getDistance(mc.player) < 180){
            opacityGradient = 100;
        }
        else if (entity.getDistance(mc.player) >= 80 && entity.getDistance(mc.player) < 130){
            opacityGradient = 150;
        }
        else if (entity.getDistance(mc.player) >= 30 && entity.getDistance(mc.player) < 80){
            opacityGradient = 200;
        }
        else {
            opacityGradient = 255;
        }

        return true;
    }
}
