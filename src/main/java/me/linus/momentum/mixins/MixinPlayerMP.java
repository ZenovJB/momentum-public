package me.linus.momentum.mixins;

import me.linus.momentum.Momentum;
import me.linus.momentum.api.events.mixin.EventPlayerDamageBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

@Mixin(PlayerControllerMP.class)
public class MixinPlayerMP {

    @Inject(method = "onPlayerDamageBlock", at = @At("HEAD"), cancellable = true)
    public void onPlayerDamageBlock(BlockPos posBlock, EnumFacing directionFacing, CallbackInfoReturnable<Boolean> pinfo)
    {
        EventPlayerDamageBlock event = new EventPlayerDamageBlock(posBlock, directionFacing);
        Momentum.EVENT_BUS.post(event);
        if (event.isCancelled())
        {
            pinfo.setReturnValue(false);
            pinfo.cancel();
        }
    }
}