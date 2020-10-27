package me.linus.momentum.module.modules.render;

import me.linus.momentum.module.Category;
import me.linus.momentum.module.Module;
import me.linus.momentum.utils.Colors;
import me.linus.momentum.utils.settings.Setting;
import me.linus.momentum.utils.utils.RenderUtil;
import net.minecraft.tileentity.*;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;

public class StorageESP extends Module {
    public StorageESP() {
        super("StorageESP", Category.Render);
    }

    Setting chests;
    Setting shulkers;
    Setting dispenser;
    Setting hoppers;
    Setting echests;
    Setting furnaces;
    Setting droppers;
    Setting mode;
    Setting hidden;
    
    int alpha;

    @Override
    public void setup(){
        ArrayList<String> options = new ArrayList<>();
        options.add("Filled");
        options.add("Outline");
        
        rSetting(chests = new Setting("Chests", this, true, ""));
        rSetting(shulkers = new Setting("Shulkers", this, true, ""));
        rSetting(dispenser = new Setting("Dispensers", this, true, ""));
        rSetting(hoppers = new Setting("Hoppers", this, true, ""));
        rSetting(echests = new Setting("Ender Chests", this, true, ""));
        rSetting(furnaces = new Setting("Furnaces", this, true, ""));
        rSetting(droppers = new Setting("Droppers", this, true, ""));
        rSetting(hidden = new Setting("Hidden", this, false, "hide"));
        rSetting(mode = new Setting("Mode", this, "Outline", options, ""));
    }
    
    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {
            mc.world.loadedTileEntityList.stream().filter(tileEntity -> rangeTileCheck(tileEntity)).forEach(tileEntity -> {
                if (tileEntity instanceof TileEntityChest && chests.getBVal()){
                    RenderUtil.drawBoundingBox(mc.world.getBlockState(tileEntity.getPos()).getSelectedBoundingBox(mc.world, tileEntity.getPos()), 7, new Colors(0, 0, 255, alpha));
                }
                if (tileEntity instanceof TileEntityEnderChest && echests.getBVal()){
                    RenderUtil.drawBoundingBox(mc.world.getBlockState(tileEntity.getPos()).getSelectedBoundingBox(mc.world, tileEntity.getPos()), 7, new Colors(180, 70, 200, alpha));
                }
                if (tileEntity instanceof TileEntityShulkerBox && shulkers.getBVal()){
                    RenderUtil.drawBoundingBox(mc.world.getBlockState(tileEntity.getPos()).getSelectedBoundingBox(mc.world, tileEntity.getPos()), 7, new Colors(255, 0, 0, alpha));
                }
                if(tileEntity instanceof TileEntityDispenser && dispenser.getBVal()){
                    RenderUtil.drawBoundingBox(mc.world.getBlockState(tileEntity.getPos()).getSelectedBoundingBox(mc.world, tileEntity.getPos()), 7, new Colors(61, 58, 58, alpha));
                }
                if(tileEntity instanceof TileEntityFurnace && furnaces.getBVal()){
                    RenderUtil.drawBoundingBox(mc.world.getBlockState(tileEntity.getPos()).getSelectedBoundingBox(mc.world, tileEntity.getPos()), 7, new Colors(61, 58, 58, alpha));
                }
                if(tileEntity instanceof TileEntityHopper && hoppers.getBVal()){
                    RenderUtil.drawBoundingBox(mc.world.getBlockState(tileEntity.getPos()).getSelectedBoundingBox(mc.world, tileEntity.getPos()), 7, new Colors(61, 58, 58, alpha));
                }
                if(tileEntity instanceof TileEntityDropper && droppers.getBVal()){
                    RenderUtil.drawBoundingBox(mc.world.getBlockState(tileEntity.getPos()).getSelectedBoundingBox(mc.world, tileEntity.getPos()), 7, new Colors(61, 58, 58, alpha));
                }
            });
        }

    private boolean rangeTileCheck(TileEntity tileEntity) {
        if (tileEntity.getDistanceSq(mc.player.posX, mc.player.posY, mc.player.posZ) > 400){
            return false;
        }

        if (tileEntity.getDistanceSq(mc.player.posX, mc.player.posY, mc.player.posZ) >= 32400){
            alpha = 255;
        }
        else if (tileEntity.getDistanceSq(mc.player.posX, mc.player.posY, mc.player.posZ) >= 16900 && tileEntity.getDistanceSq(mc.player.posX, mc.player.posY, mc.player.posZ) < 32400){
            alpha = 255;
        }
        else if (tileEntity.getDistanceSq(mc.player.posX, mc.player.posY, mc.player.posZ) >= 6400 && tileEntity.getDistanceSq(mc.player.posX, mc.player.posY, mc.player.posZ) < 16900){
            alpha = 255;
        }
        else if (tileEntity.getDistanceSq(mc.player.posX, mc.player.posY, mc.player.posZ) >= 900 && tileEntity.getDistanceSq(mc.player.posX, mc.player.posY, mc.player.posZ) < 6400){
            alpha = 255;
        }
        else {
            alpha = 255;
        }

        return true;
    }
    }

