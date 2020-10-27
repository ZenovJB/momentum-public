package me.linus.momentum.api.events.mixin;

import me.linus.momentum.api.events.Event;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class BreakBlockEvent extends Event {

    private BlockPos pos;
    private EnumFacing face;

    public BreakBlockEvent(BlockPos pos, EnumFacing face){
        this.pos = pos;
        this.face = face;
    }

    public BlockPos getPos(){
        return pos;
    }

    public void setPos(BlockPos pos){
        this.pos = pos;
    }

    public EnumFacing getFace(){
        return face;
    }

    public void setFace(EnumFacing face){
        this.face = face;
    }
}