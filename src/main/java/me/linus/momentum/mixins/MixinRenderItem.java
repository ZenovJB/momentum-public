package me.linus.momentum.mixins;


import me.linus.momentum.module.modules.render.ItemPreview;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.world.storage.MapData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static me.linus.momentum.Momentum.moduleManager;

@Mixin(RenderItem.class)
public class MixinRenderItem {
    @Shadow
    public float zLevel;

    @Inject(method = "renderItemModelIntoGUI", at = @At("HEAD"), cancellable = true)
    public void renderItemModelIntoGUIPRE (ItemStack stack, int x, int y, IBakedModel bakedmodel, CallbackInfo cb) {
        ItemPreview map = (ItemPreview) moduleManager.getModuleByName("ItemPreview");
        if (map.isToggled() && map.getMap() == true && !stack.isEmpty() && stack.getItem() instanceof ItemMap) {
            Minecraft mc = Minecraft.getMinecraft();
            MapData data = ((ItemMap) stack.getItem()).getMapData(stack, mc.world);

            if (data != null) {
                GlStateManager.pushMatrix();
                GlStateManager.translate((float) x, (float) y, 100.0F + zLevel);
                GlStateManager.scale(0.125F, 0.125F, 0.125F);
                mc.entityRenderer.getMapItemRenderer().renderMap(data, true);
                GlStateManager.popMatrix();

                cb.cancel();
            }
        }
    }
}