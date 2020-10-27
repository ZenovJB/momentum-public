package me.linus.momentum.module.modules.movement;

import me.linus.momentum.Momentum;
import me.linus.momentum.api.events.mixin.PacketEvent;
import me.linus.momentum.api.events.mixin.PushEvent;
import me.linus.momentum.module.Category;
import me.linus.momentum.module.Module;
import me.linus.momentum.utils.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;

public class Velocity extends Module {
    public Velocity() {
        super("Velocity", Category.Movement);
    }

    Setting yVel;
    Setting xVel;
    Setting nopush;
    Setting hidden;

    @Override
    public void setup() {
        rSetting(xVel = new Setting("Horizontal", this, 0, 0, 100, false, "x"));
        rSetting(yVel = new Setting("Vertical", this, 0, 0, 100, false, "y"));
        rSetting(nopush = new Setting("No Push", this, true, ""));
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
    private final Listener<PacketEvent.Receive> listener = new Listener<>(event -> {
        if (event.getPacket() instanceof SPacketEntityVelocity) {
            if (((SPacketEntityVelocity) event.getPacket()).getEntityID() == mc.player.getEntityId())
                event.cancel();
        }
        if (event.getPacket() instanceof SPacketExplosion) {
            event.cancel();
        }
    });

    @EventHandler
    private final Listener<PushEvent> waterPushEventListener = new Listener<>(event -> {
        if (nopush.getBVal() == true) {
            event.cancel();
        }
    });

    public boolean getPush(){return nopush.getBVal();}

    @Override
    public String getHudInfo() {
        return " \u00A77[\u00A7f" + "H: " + this.xVel.getDVal() + "%" + " V: " + this.yVel.getDVal() + "%" + "\u00A77]";
    }
}
