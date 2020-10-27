package me.linus.momentum.module.modules.render;

import me.linus.momentum.module.Category;
import me.linus.momentum.module.Module;
import me.linus.momentum.utils.settings.Setting;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Objects;

public class FullBright extends Module {
    public FullBright() {
        super("FullBright", Category.Render);
        nightVision.setPotionDurationMax(true);
    }

    public Setting mode;
    Setting hidden;

    private boolean hasEffect = false;
    private final PotionEffect nightVision = new PotionEffect(Objects.requireNonNull(Potion.getPotionById(16)));


    @Override
    public void setup(){
        ArrayList<String> options = new ArrayList<>();
        options.add("Gamma");
        options.add("Vanilla");
        options.add("Shader");

        rSetting(mode = new Setting("Mode", this, "Gamma", options, "mode"));
        rSetting(hidden = new Setting("Hidden", this, false, "hide"));
    }

    @Override
    public void onEnable() {
        if (mode.getSVal() == "Gamma") {
            if (mc.player != null) {
                mc.gameSettings.gammaSetting = +100;
            }
        }

        else if (mode.getSVal() == "Vanilla") {
            if (mc.player == null) return;

            mc.player.addPotionEffect(nightVision);
            hasEffect = true;
        }

        else {}
    }

    @Override
    public void onUpdate(){
        if (mc.player == null) return;

        if (!hasEffect) {
            mc.player.addPotionEffect(nightVision);
            hasEffect = true;
        }
    }

    @Override
    public void onDisable() {
        if (mode.getSVal() == "Gamma") {
            if (mc.player != null) {
                mc.gameSettings.gammaSetting = 25;
            }
        }

        if (mode.getSVal() == "Vanilla") {mc.player.removeActivePotionEffect(nightVision.getPotion());}
    }
}
