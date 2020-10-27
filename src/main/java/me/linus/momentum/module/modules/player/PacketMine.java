package me.linus.momentum.module.modules.player;

import me.linus.momentum.Momentum;
import me.linus.momentum.api.events.mixin.EventPlayerDamageBlock;
import me.linus.momentum.module.Category;
import me.linus.momentum.module.Module;
import me.linus.momentum.utils.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

public class PacketMine extends Module {
    public PacketMine() {
        super("PacketMine", Category.Player);
    }

    Setting hidden;

    @Override
    public void setup(){
        rSetting(hidden = new Setting("Hidden", this, false, "hide"));
    }

    @Override
    public void onEnable() {
        Momentum.EVENT_BUS.subscribe(this);
    }

    @Override
    public void onDisable() {
        Momentum.EVENT_BUS.unsubscribe(this);
    }

    @EventHandler
    private Listener<EventPlayerDamageBlock> OnDamageBlock = new Listener<>(event ->
    {
        if (canBreak(event.getPos())) {
            mc.player.swingArm(EnumHand.MAIN_HAND);
            mc.player.connection.sendPacket(new CPacketPlayerDigging( CPacketPlayerDigging.Action.START_DESTROY_BLOCK, event.getPos(), event.getDirection()));
            mc.player.connection.sendPacket(new CPacketPlayerDigging( CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, event.getPos(), event.getDirection()));
            event.cancel();
        }
    });

    private boolean canBreak(BlockPos pos)
    {
        final IBlockState blockState = mc.world.getBlockState(pos);
        final Block block = blockState.getBlock();

        return block.getBlockHardness(blockState, Minecraft.getMinecraft().world, pos) != -1;
    }

}