package me.linus.momentum.module.modules.misc;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import me.linus.momentum.module.Category;
import me.linus.momentum.module.Module;
import me.linus.momentum.utils.settings.Setting;

import java.util.ArrayList;
import com.mojang.authlib.GameProfile;

public class FakePlayer extends Module {
    public FakePlayer() {
        super("FakePlayer", Category.Miscellaneous);
    }

    public Setting name;
    Setting hidden;

    @Override
    public void setup(){
        ArrayList<String> namelist = new ArrayList<>();
        namelist.add("linustouchtips24");
        namelist.add("popbob");
        namelist.add("FitMC");
        namelist.add("jj20051");
        namelist.add("Hausemaster");

        rSetting(name = new Setting("Name", this, "linustouchtips24", namelist, "name"));
        rSetting(hidden = new Setting("Hidden", this, false, "hide"));
    }

    private EntityOtherPlayerMP _fakePlayer;

    @Override
    public void onEnable()
    {
        super.onEnable();
        _fakePlayer = null;

        if (mc.world == null)
        {
            this.toggle();
            return;
        }

        String s = "";

        switch (name.getSVal()) {
            case "linustouchtips24":
                s = "linustouchtips24";
                break;
            case "popbob":
                s = "popbob";
                break;
            case "fitmc":
                s = "Fit";
                break;
            case "jj20051":
                s = "jj20051";
                break;
            case "Hausemaster":
                s = "Hausemaster";
                break;
        }

        _fakePlayer = new EntityOtherPlayerMP(mc.world, new GameProfile(mc.player.getUniqueID(), s));

        mc.world.addEntityToWorld(_fakePlayer.getEntityId(), _fakePlayer);
        _fakePlayer.attemptTeleport(mc.player.posX, mc.player.posY, mc.player.posZ);
    }

    @Override
    public void onDisable()
    {
        if(!(mc.world == null)) {
            mc.world.removeEntity(_fakePlayer);
        }
    }
}