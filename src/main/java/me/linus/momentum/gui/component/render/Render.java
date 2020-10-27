package me.linus.momentum.gui.component.render;

import me.linus.momentum.utils.Colors;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class Render {

    public static void drawRectStatic(int leftX, int leftY, int rightX, int rightY, Colors color){
        Gui.drawRect(leftX,leftY,rightX,rightY,color.getRGB());
    }
    
    public static void drawRectGradient(int leftX, int leftY, int rightX, int rightY, Colors startColor, Colors endColor){
        float s = (startColor.getRGB() >> 24 & 255) / 255.0F;
        float s1 = (startColor.getRGB() >> 16 & 255) / 255.0F;
        float s2 = (startColor.getRGB() >> 8 & 255) / 255.0F;
        float s3 = (startColor.getRGB() & 255) / 255.0F;
        float e1 = (endColor.getRGB() >> 24 & 255) / 255.0F;
        float e2 = (endColor.getRGB() >> 16 & 255) / 255.0F;
        float e3 = (endColor.getRGB() >> 8 & 255) / 255.0F;
        float e4 = (endColor.getRGB() & 255) / 255.0F;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(rightX, leftY, 0).color(s1, s2, s3, s).endVertex();
        bufferbuilder.pos(leftX, leftY, 0).color(s1, s2, s3, s).endVertex();
        bufferbuilder.pos(leftX, rightY, 0).color(e2, e3, e4, e1).endVertex();
        bufferbuilder.pos(rightX, rightY, 0).color(e2, e4, e4, e1).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }
    
}
