package me.linus.momentum.mixins;

import me.linus.momentum.module.modules.render.ESP;
import me.linus.momentum.utils.utils.ESPUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.awt.*;

import static me.linus.momentum.Momentum.moduleManager;

@Mixin(RenderLivingBase.class)
public abstract class MixinLivingEntityRender<T extends EntityLivingBase> extends Render<T> {

    @Shadow
    protected ModelBase mainModel;

    protected MixinLivingEntityRender() {
        super(null);
    }

    @Overwrite
    protected void renderModel(T entitylivingbaseIn, float p_77036_2_, float p_77036_3_, float p_77036_4_, float p_77036_5_, float p_77036_6_, float scaleFactor) {
        boolean isPlayer = entitylivingbaseIn instanceof EntityPlayer;

        if (!bindEntityTexture(entitylivingbaseIn)) {
            return;
        }

        Minecraft mc = Minecraft.getMinecraft();
        boolean fancyGraphics = mc.gameSettings.fancyGraphics;
        mc.gameSettings.fancyGraphics = false;
        ESP esp = (ESP) moduleManager.getModuleByName("ESP");
        float gamma = mc.gameSettings.gammaSetting;
        boolean player = entitylivingbaseIn instanceof EntityPlayer && entitylivingbaseIn != Minecraft.getMinecraft().player;
        mc.gameSettings.gammaSetting = 100000F;
        if (esp.isToggled()) {
            switch (esp.getMode()) {
                case "Outline":
                if (player) {
                    Color n = new Color(esp.getRed(), esp.getGreen(), esp.getBlue());
                    ESPUtils.setColor(Color.WHITE);
                    mainModel.render(entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);
                    ESPUtils.renderOne((float) esp.getWidth());
                    mainModel.render(entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);
                    ESPUtils.renderTwo();
                    mainModel.render(entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);
                    ESPUtils.renderThree();
                    ESPUtils.renderFour();
                    ESPUtils.setColor(n);
                    mainModel.render(entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);
                    ESPUtils.renderFive();
                    ESPUtils.setColor(Color.WHITE);
            }
            }
        }

        mc.gameSettings.fancyGraphics = fancyGraphics;
        mc.gameSettings.gammaSetting = gamma;

        if (!(esp.getMode() == "Box") || !esp.isToggled() || !isPlayer) {
            this.mainModel.render(entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);
        }

    }
}







