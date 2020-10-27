package me.linus.momentum.module.modules.player;

import me.linus.momentum.Momentum;
import me.linus.momentum.api.events.mixin.BreakBlockEvent;
import me.linus.momentum.module.Category;
import me.linus.momentum.module.Module;
import me.linus.momentum.utils.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.init.MobEffects;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public class QuickMine extends Module {
    public QuickMine(){
        super("QuickMine", Category.Player);
    }

    Setting mode;
    Setting hidden;

    public void setup(){

        ArrayList<String> options = new ArrayList<>();
        options.add("Vanilla");
        options.add("Damage");
        options.add("Instant");

        rSetting(mode = new Setting("Mode", this, "Instant", options, ""));
        rSetting(hidden = new Setting("Hidden", this, false, ""));
    }

    public void onEnable(){
        Momentum.EVENT_BUS.subscribe(this);
    }

    public void onDisable(){
        Momentum.EVENT_BUS.unsubscribe(this);
        mc.player.removePotionEffect(MobEffects.HASTE);
    }

    public void onUpdate(){
        Minecraft.getMinecraft().playerController.blockHitDelay = 0;
        if (mode.getSVal() == "Vanilla") {
            PotionEffect effect = new PotionEffect(MobEffects.HASTE, 80950, 1, false, false);
            mc.player.addPotionEffect(new PotionEffect(effect));
        }

        if (!(mode.getSVal() == "Vanilla") && mc.player.isPotionActive(MobEffects.HASTE)) {
            mc.player.removePotionEffect(MobEffects.HASTE);
        }
    }

    @EventHandler
    private final Listener<BreakBlockEvent> listener = new Listener<>(event -> {

        if (mc.world == null || mc.player == null){
            return;
        }
        if (canBreak(event.getPos())){
            if (mode.getSVal() == "Damage"){
                if (mc.playerController.curBlockDamageMP >= 0.7f){
                    mc.playerController.curBlockDamageMP = 1.0f;
                }
            }

            if (mode.getSVal() == "Instant"){
                mc.player.swingArm(EnumHand.MAIN_HAND);
                mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, event.getPos(), event.getFace()));
                mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, event.getPos(), event.getFace() ));
                mc.playerController.onPlayerDestroyBlock(event.getPos());
                mc.world.setBlockToAir(event.getPos());
            }
        }
    });

    private boolean canBreak(BlockPos pos){
        final IBlockState blockState = mc.world.getBlockState(pos);
        final Block block = blockState.getBlock();
        return block.getBlockHardness(blockState, mc.world, pos) != -1;
    }

    @Override
    public String getHudInfo() {
        return  " \u00A77[\u00A7f" + this.mode.getSVal() + "\u00A77]";
    }
}
