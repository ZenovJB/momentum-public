package me.linus.momentum.mixins;

import me.linus.momentum.Momentum;
import me.linus.momentum.module.ModuleManager;
import me.linus.momentum.module.modules.gui.Capes;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static me.linus.momentum.Momentum.moduleManager;

import javax.annotation.Nullable;
import java.util.UUID;

@Mixin(AbstractClientPlayer.class)
public abstract class MixinAbstractClientPlayer{

    @Shadow @Nullable protected abstract NetworkPlayerInfo getPlayerInfo();

    @Inject(method = "getLocationCape", at = @At("HEAD"), cancellable = true)
    public void getLocationCape(CallbackInfoReturnable<ResourceLocation> cir){
        UUID uuid = getPlayerInfo().getGameProfile().getId();
        Capes capesModule = ((Capes) moduleManager.getModuleByName("Capes"));

        if (ModuleManager.isModuleToggled("Capes") && Momentum.getInstance().capeUtils.hasCape(uuid)){
            if (capesModule.mode.getSVal() == "2013"){
                cir.setReturnValue(new ResourceLocation("momentum:2013.png"));
            }
            if (capesModule.mode.getSVal() == "S8N"){
                cir.setReturnValue(new ResourceLocation("momentum:sean.png"));
            }
            if (capesModule.mode.getSVal() == "Momentum") {
                cir.setReturnValue(new ResourceLocation("momentum:momentum_cape.png"));
            }
        }
    }
}

