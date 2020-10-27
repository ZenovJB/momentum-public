package me.linus.momentum.mixins;

import me.linus.momentum.Momentum;
import me.linus.momentum.api.events.mixin.BreakBlockEvent;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static me.linus.momentum.Momentum.moduleManager;

@Mixin(PlayerControllerMP.class)
public class MixinPlayerControllerMP {

    @Inject(method = "onPlayerDamageBlock(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/EnumFacing;)Z", at = @At("HEAD"), cancellable = true)
    private void onPlayerDamageBlock(BlockPos posBlock, EnumFacing directionFacing, CallbackInfoReturnable<Boolean> cir){
        BreakBlockEvent event = new BreakBlockEvent(posBlock, directionFacing);
        Momentum.EVENT_BUS.post(event);
        if (event.isCancelled()){
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "resetBlockRemoving", at = @At("HEAD"), cancellable = true)
    private void resetBlock(CallbackInfo ci){
        if (moduleManager.isModuleToggled("MultiTask")) ci.cancel();
    }

}