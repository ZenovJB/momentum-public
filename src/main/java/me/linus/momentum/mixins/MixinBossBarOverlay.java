package me.linus.momentum.mixins;

import me.linus.momentum.Momentum;
import me.linus.momentum.api.events.mixin.BossBarEvent;
import net.minecraft.client.gui.GuiBossOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiBossOverlay.class)
public class MixinBossBarOverlay{

    @Inject(method = "renderBossHealth", at = @At("HEAD"), cancellable = true)
    private void renderBossHealth(CallbackInfo ci){
        BossBarEvent event = new BossBarEvent();
        Momentum.EVENT_BUS.post(event);
        if (event.isCancelled()){
            ci.cancel();
        }
    }
}