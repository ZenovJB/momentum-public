package me.linus.momentum.module.modules.combat;

import me.linus.momentum.Momentum;
import me.linus.momentum.api.events.mixin.PacketEvent;
import me.linus.momentum.module.Category;
import me.linus.momentum.module.Module;
import me.linus.momentum.utils.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;

import java.util.ArrayList;

public class Criticals extends Module {
    public Criticals() {super("Criticals", Category.Combat);}

    public Setting mode;
    Setting hidden;

    @Override
    public void setup(){
        ArrayList<String> option = new ArrayList<>();
        option.add("Packet");
        option.add("Jump");
        option.add("Vanilla");

        rSetting(mode = new Setting("Mode", this, "Packet", option, ""));
        rSetting(hidden = new Setting("Hidden", this, false, "hide"));
    }

    public void onEnable(){
        Momentum.EVENT_BUS.subscribe(this);
    }

    public void onDisable(){
        Momentum.EVENT_BUS.unsubscribe(this);
    }

    @EventHandler
    private Listener<PacketEvent.Send> sendListener = new Listener<>(event -> {
        if (event.getPacket() instanceof CPacketUseEntity) {
            if(((CPacketUseEntity) event.getPacket()).getAction() == CPacketUseEntity.Action.ATTACK && mc.player.onGround) {
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.1f, mc.player.posZ, false));
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
            }
        }
    });

    @Override
    public String getHudInfo() {
        return " \u00A77[\u00A7f" + this.mode.getSVal() + "\u00A77]";
    }
}
