package me.linus.momentum.module.modules.combat;

import me.linus.momentum.module.Module;
import me.linus.momentum.module.Category;
import me.linus.momentum.utils.settings.Setting;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class HoleMovement extends Module {
    public HoleMovement() {
        super("HoleMovement", Category.Movement);
    }

    private int packets;
    private boolean jumped;
    private final double[] oneblockPositions = new double[]{0.42, 0.75};

    public Setting holetp;
    public Setting anchor;
    Setting hidden;

    BlockPos playerPos;

    @Override
    public void setup() {
        rSetting(holetp = new Setting("Teleport", this, true, "holetp"));
        rSetting(anchor = new Setting("Anchor", this, false, "holeanchor"));
        rSetting(hidden = new Setting("Hidden", this, false, "hide"));
    }

    public void onUpdate() {
        if (holetp.getBVal() == true) {
            if (HoleMovement.mc.world == null || HoleMovement.mc.player == null) {
                return;
            }
            if (!HoleMovement.mc.player.onGround) {
                if (HoleMovement.mc.gameSettings.keyBindJump.isKeyDown()) {
                    this.jumped = true;
                }
            } else {
                this.jumped = false;
            }
            if (!this.jumped && HoleMovement.mc.player.fallDistance < 0.5 && this.isInHole() && HoleMovement.mc.player.posY - this.getNearestBlockBelow() <= 1.125 && HoleMovement.mc.player.posY - this.getNearestBlockBelow() <= 0.95 && !this.isOnLiquid() && !this.isInLiquid()) {
                if (!HoleMovement.mc.player.onGround) {
                    this.packets++;
                }
                if (!HoleMovement.mc.player.onGround && !HoleMovement.mc.player.isInsideOfMaterial(Material.WATER) && !HoleMovement.mc.player.isInsideOfMaterial(Material.LAVA) && !HoleMovement.mc.gameSettings.keyBindJump.isKeyDown() && !HoleMovement.mc.player.isOnLadder() && this.packets > 0) {
                    final BlockPos blockPos = new BlockPos(HoleMovement.mc.player.posX, HoleMovement.mc.player.posY, HoleMovement.mc.player.posZ);
                    for (final double position : this.oneblockPositions) {
                        HoleMovement.mc.player.connection.sendPacket(new CPacketPlayer.Position(blockPos.getX() + 0.5f, HoleMovement.mc.player.posY - position, blockPos.getZ() + 0.5f, true));
                    }
                    HoleMovement.mc.player.setPosition(blockPos.getX() + 0.5f, this.getNearestBlockBelow() + 0.1, blockPos.getZ() + 0.5f);
                    this.packets = 0;
                }
            }
        }

        if (anchor.getBVal() == true) {
            if (mc.player == null){
                return;
            }

            if (mc.player.posY < 0){
                return;
            }

            if (mc.player.posY > 40){
                return;
            }

            double newX;
            double newZ;

            if (mc.player.posX > Math.round(mc.player.posX)){
                newX = Math.round(mc.player.posX) + 0.5;
            }
            else if (mc.player.posX < Math.round(mc.player.posX)){
                newX = Math.round(mc.player.posX) - 0.5;
            }
            else {
                newX = mc.player.posX;
            }

            if (mc.player.posZ > Math.round(mc.player.posZ)){
                newZ = Math.round(mc.player.posZ) + 0.5;
            }
            else if (mc.player.posZ < Math.round(mc.player.posZ)){
                newZ = Math.round(mc.player.posZ) - 0.5;
            }
            else {
                newZ = mc.player.posZ;
            }

            playerPos = new BlockPos(newX, mc.player.posY, newZ);

            if (mc.world.getBlockState(playerPos).getBlock() != Blocks.AIR){
                return;
            }

            if (mc.world.getBlockState(playerPos.down()).getBlock() == Blocks.AIR
                    && mc.world.getBlockState(playerPos.down().east()).getBlock() != Blocks.AIR
                    && mc.world.getBlockState(playerPos.down().west()).getBlock() != Blocks.AIR
                    && mc.world.getBlockState(playerPos.down().north()).getBlock() != Blocks.AIR
                    && mc.world.getBlockState(playerPos.down().south()).getBlock() != Blocks.AIR
                    && mc.world.getBlockState(playerPos.down(2)).getBlock() != Blocks.AIR){

                mc.player.motionX = 0;
                mc.player.motionZ = 0;
            }

            else if (mc.world.getBlockState(playerPos.down()).getBlock() == Blocks.AIR
                    && mc.world.getBlockState(playerPos.down(2)).getBlock() == Blocks.AIR
                    && mc.world.getBlockState(playerPos.down(2).east()).getBlock() != Blocks.AIR
                    && mc.world.getBlockState(playerPos.down(2).west()).getBlock() != Blocks.AIR
                    && mc.world.getBlockState(playerPos.down(2).north()).getBlock() != Blocks.AIR
                    && mc.world.getBlockState(playerPos.down(2).south()).getBlock() != Blocks.AIR
                    && mc.world.getBlockState(playerPos.down(3)).getBlock() != Blocks.AIR){

                mc.player.motionX = 0;
                mc.player.motionZ = 0;
            }
        }
    }

    private boolean isInHole() {
        final BlockPos blockPos = new BlockPos(HoleMovement.mc.player.posX, HoleMovement.mc.player.posY, HoleMovement.mc.player.posZ);
        final IBlockState blockState = HoleMovement.mc.world.getBlockState(blockPos);
        return this.isBlockValid(blockState, blockPos);
    }

    private double getNearestBlockBelow() {
        for (double y = HoleMovement.mc.player.posY; y > 0.0; y -= 0.001) {
            if (!(HoleMovement.mc.world.getBlockState(new BlockPos(HoleMovement.mc.player.posX, y, HoleMovement.mc.player.posZ)).getBlock() instanceof BlockSlab) && HoleMovement.mc.world.getBlockState(new BlockPos(HoleMovement.mc.player.posX, y, HoleMovement.mc.player.posZ)).getBlock().getDefaultState().getCollisionBoundingBox(HoleMovement.mc.world, new BlockPos(0, 0, 0)) != null) {
                return y;
            }
        }
        return -1.0;
    }

    private boolean isBlockValid(final IBlockState blockState, final BlockPos blockPos) {
        return blockState.getBlock() == Blocks.AIR && HoleMovement.mc.player.getDistanceSq(blockPos) >= 1.0 && HoleMovement.mc.world.getBlockState(blockPos.up()).getBlock() == Blocks.AIR && HoleMovement.mc.world.getBlockState(blockPos.up(2)).getBlock() == Blocks.AIR && (this.isBedrockHole(blockPos) || this.isObbyHole(blockPos) || this.isBothHole(blockPos) || this.isElseHole(blockPos));
    }

    private boolean isObbyHole(final BlockPos blockPos) {
        final BlockPos[] array;
        final BlockPos[] touchingBlocks = array = new BlockPos[]{blockPos.north(), blockPos.south(), blockPos.east(), blockPos.west(), blockPos.down()};
        for (final BlockPos touching : array) {
            final IBlockState touchingState = HoleMovement.mc.world.getBlockState(touching);
            if (touchingState.getBlock() == Blocks.AIR || touchingState.getBlock() != Blocks.OBSIDIAN) {
                return false;
            }
        }
        return true;
    }

    private boolean isBedrockHole(final BlockPos blockPos) {
        final BlockPos[] array;
        final BlockPos[] touchingBlocks = array = new BlockPos[]{blockPos.north(), blockPos.south(), blockPos.east(), blockPos.west(), blockPos.down()};
        for (final BlockPos touching : array) {
            final IBlockState touchingState = HoleMovement.mc.world.getBlockState(touching);
            if (touchingState.getBlock() == Blocks.AIR || touchingState.getBlock() != Blocks.BEDROCK) {
                return false;
            }
        }
        return true;
    }

    private boolean isBothHole(final BlockPos blockPos) {
        final BlockPos[] array;
        final BlockPos[] touchingBlocks = array = new BlockPos[]{blockPos.north(), blockPos.south(), blockPos.east(), blockPos.west(), blockPos.down()};
        for (final BlockPos touching : array) {
            final IBlockState touchingState = HoleMovement.mc.world.getBlockState(touching);
            if (touchingState.getBlock() == Blocks.AIR || (touchingState.getBlock() != Blocks.BEDROCK && touchingState.getBlock() != Blocks.OBSIDIAN)) {
                return false;
            }
        }
        return true;
    }

    private boolean isElseHole(final BlockPos blockPos) {
        final BlockPos[] array;
        final BlockPos[] touchingBlocks = array = new BlockPos[]{blockPos.north(), blockPos.south(), blockPos.east(), blockPos.west(), blockPos.down()};
        for (final BlockPos touching : array) {
            final IBlockState touchingState = HoleMovement.mc.world.getBlockState(touching);
            if (touchingState.getBlock() == Blocks.AIR || !touchingState.isFullBlock()) {
                return false;
            }
        }
        return true;
    }

    private boolean isOnLiquid() {
        final double y = HoleMovement.mc.player.posY - 0.03;
        for (int x = MathHelper.floor(HoleMovement.mc.player.posX); x < MathHelper.ceil(HoleMovement.mc.player.posX); x++) {
            for (int z = MathHelper.floor(HoleMovement.mc.player.posZ); z < MathHelper.ceil(HoleMovement.mc.player.posZ); z++) {
                final BlockPos pos = new BlockPos(x, MathHelper.floor(y), z);
                if (HoleMovement.mc.world.getBlockState(pos).getBlock() instanceof BlockLiquid) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isInLiquid() {
        final double y = HoleMovement.mc.player.posY + 0.01;
        for (int x = MathHelper.floor(HoleMovement.mc.player.posX); x < MathHelper.ceil(HoleMovement.mc.player.posX); x++) {
            for (int z = MathHelper.floor(HoleMovement.mc.player.posZ); z < MathHelper.ceil(HoleMovement.mc.player.posZ); z++) {
                final BlockPos pos = new BlockPos(x, (int) y, z);
                if (HoleMovement.mc.world.getBlockState(pos).getBlock() instanceof BlockLiquid) {
                    return true;
                }
            }
        }
        return false;
    }
}