package me.linus.momentum.mixins;

import me.linus.momentum.Momentum;
import me.linus.momentum.utils.Colors;
import me.linus.momentum.utils.utils.FontUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;

@Mixin(GuiMainMenu.class)
public class MixinMainMenu extends GuiScreen {

    @Inject(method = "drawScreen", at = @At("TAIL"), cancellable = true)
    public void drawText(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        FontUtils.drawStringWithShadow(true, "Momentum Utility Mod " + Momentum.version, 2, 2, new Colors(54, 139, 224));
        FontUtils.drawStringWithShadow(true, "created by linustouchtips and jake", 2, 11, new Colors(54, 139, 224));
        ResourceLocation location = new ResourceLocation("momentum", "momentum.png");
        this.mc.getTextureManager().bindTexture(location);
        GL11.glPushMatrix();
        this.drawModalRectWithCustomSizedTexture(4, 23, 0, 0, 80, 80, 80, 80);
        GL11.glPopMatrix();
    }

}