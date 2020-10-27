package me.linus.momentum.mixins;

import me.linus.momentum.module.ModuleManager;
import me.linus.momentum.module.modules.render.NoRender;
import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static me.linus.momentum.Momentum.moduleManager;

@Mixin(EntityRenderer.class)
public class MixinEntityRender{

    @Inject(method = "hurtCameraEffect", at = @At("HEAD"), cancellable = true)
    public void hurtCameraEffect(float ticks, CallbackInfo info){
        if (ModuleManager.isModuleToggled("NoRender") && (((NoRender) moduleManager.getModuleByName("NoRender")).getHurtCam()));
            info.cancel();
    }
}