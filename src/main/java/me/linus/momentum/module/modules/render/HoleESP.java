package me.linus.momentum.module.modules.render;

import me.linus.momentum.api.events.mixin.PacketEvent;
import me.linus.momentum.module.Category;
import me.linus.momentum.module.Module;
import me.linus.momentum.utils.Colors;
import me.linus.momentum.utils.GeometryMasks;
import me.linus.momentum.utils.settings.Setting;
import me.linus.momentum.utils.utils.EntityUtil;
import me.linus.momentum.utils.utils.RenderUtil;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class HoleESP extends Module {
    public HoleESP() {
        super("HoleESP", Category.Render);
    }
    
    public Setting range;
    public Setting mode;
    public Setting obbyRed;
    public Setting obbyGreen;
    public Setting obbyBlue;
    public Setting bRockRed;
    public Setting bRockGreen;
    public Setting bRockBlue;
    public Setting alpha;
    public Setting alpha2;
    public Setting width;
    Setting hidden;

    @Override
    public void setup() {
        ArrayList<String> options = new ArrayList<>();
        options.add("WireFrameBottom");
        options.add("FilledBottom");
        options.add("FilledBox");

        rSetting(range = new Setting("Range", this, 7, 0, 10, true, ""));
        rSetting(mode = new Setting("Mode", this, "WireFrameBottom", options,""));
        rSetting(obbyRed = new Setting("Obsidian Red", this, 255, 0, 255, true, "or"));
        rSetting(obbyGreen = new Setting("Obsidian Green", this, 255, 0, 255, true, "og"));
        rSetting(obbyBlue = new Setting("Obsidian Blue", this, 255, 0, 255, true, "ob"));
        rSetting(bRockRed = new Setting("Bedrock Red", this, 255, 0, 255, true, "br"));
        rSetting(bRockGreen = new Setting("Bedrock Green", this, 255, 0, 255, true, "bg"));
        rSetting(bRockBlue = new Setting("Bedrock Blue", this, 255, 0, 255, true, "bb"));
        rSetting(alpha = new Setting("Box Alpha", this, 125, 0, 255, true, ""));
        rSetting(alpha2 = new Setting("WireFrame Alpha", this, 255, 0, 255, true, "ba"));
        rSetting(width = new Setting("Line Width", this, 2f, 0, 7, false, "linewidth"));
        rSetting(hidden = new Setting("Hidden", this, false, "hide"));
    }

    private BlockPos render;
    private static boolean isSpoofingAngles;
    private static double yaw;
    private static double pitch;
    
    @EventHandler
    private Listener<PacketEvent.Send> packetListener = new Listener<PacketEvent.Send>(event -> {
        Packet packet = event.getPacket();
        if (packet instanceof CPacketPlayer && isSpoofingAngles) {
            ((CPacketPlayer)packet).yaw = (float)yaw;
            ((CPacketPlayer)packet).pitch = (float)pitch;
        }
    }, new Predicate[0]);

    @Override
    public void onUpdate() {
        BlockPos blockPos;
        List<BlockPos> bRockHoles = this.findBRockHoles();
        List<BlockPos> obbyHoles = this.findObbyHoles();
        BlockPos shouldRender = null;
        Iterator<BlockPos> iterator = bRockHoles.iterator();
        while (iterator.hasNext()) {
            shouldRender = blockPos = iterator.next();
        }
        iterator = obbyHoles.iterator();
        while (iterator.hasNext()) {
            shouldRender = blockPos = iterator.next();
        }
        this.render = shouldRender;
    }

    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {
        GL11.glEnable(2884);
        if (this.render != null) {
            for (BlockPos hole : this.findObbyHoles()) {
                if (mode.getSVal() == "FilledBox") {
                    RenderUtil.prepare(7);
                    RenderUtil.drawBoxFromBlockPos(hole, new Colors(this.obbyRed.getDVal(), this.obbyGreen.getDVal(), this.obbyBlue.getDVal(), this.alpha.getDVal()), 63);
                    RenderUtil.drawBoundingBoxBlockPos(hole, this.width.getDVal(), this.obbyRed.getDVal(), this.obbyGreen.getDVal(), this.obbyBlue.getDVal(), this.alpha2.getDVal());
                    RenderUtil.release();
                }
                if (mode.getSVal() == "FilledBottom") {
                    RenderUtil.prepare(7);
                    RenderUtil.drawBoxFromBlockPos(hole, new Colors(this.obbyRed.getDVal(), this.obbyGreen.getDVal(), this.obbyBlue.getDVal(), this.alpha.getDVal()), GeometryMasks.Quad.DOWN);
                    RenderUtil.drawBoundingBoxBottomBlockPos(hole, this.width.getDVal(), this.obbyRed.getDVal(), this.obbyGreen.getDVal(), this.obbyBlue.getDVal(), this.alpha2.getDVal());
                    RenderUtil.release();
                }
                if (mode.getSVal() != "WireFrameBottom") continue;
                RenderUtil.prepare(7);
                RenderUtil.drawBoundingBoxBottomBlockPos(hole, this.width.getDVal(), this.obbyRed.getDVal(), this.obbyGreen.getDVal(), this.obbyBlue.getDVal(), this.alpha2.getDVal());
                RenderUtil.release();
            }

            for (BlockPos hole : this.findBRockHoles()) {
                if (mode.getSVal() == "FilledBox") {
                    RenderUtil.prepare(7);
                    RenderUtil.drawBoxFromBlockPos(hole, new Colors(this.bRockRed.getDVal(), this.bRockGreen.getDVal(), this.bRockBlue.getDVal(), this.alpha.getDVal()), 63);
                    RenderUtil.drawBoundingBoxBlockPos(hole, this.width.getDVal(), this.bRockRed.getDVal(), this.bRockGreen.getDVal(), this.bRockBlue.getDVal(), this.alpha2.getDVal());
                    RenderUtil.release();
                }

                if (mode.getSVal() == "FilledBottom") {
                    RenderUtil.prepare(7);
                    RenderUtil.drawBoxFromBlockPos(hole, new Colors(this.bRockRed.getDVal(), this.bRockGreen.getDVal(), this.bRockBlue.getDVal(), this.alpha.getDVal()), GeometryMasks.Quad.DOWN);
                    RenderUtil.drawBoundingBoxBottomBlockPos(hole, this.width.getDVal(), this.bRockRed.getDVal(), this.bRockGreen.getDVal(), this.bRockBlue.getDVal(), this.alpha2.getDVal());
                    RenderUtil.release();
                }
                if (mode.getSVal() != "WireFrameBottom") continue;
                RenderUtil.prepare(7);
                RenderUtil.drawBoundingBoxBottomBlockPos(hole, this.width.getDVal(), this.bRockRed.getDVal(), this.bRockGreen.getDVal(), this.bRockBlue.getDVal(), this.alpha2.getDVal());
                RenderUtil.release();
            }
        }
    }

    private void lookAtPacket(double px, double py, double pz, EntityPlayer me) {
        double[] v = EntityUtil.calculateLookAt(px, py, pz, me);
        HoleESP.setYawAndPitch((float)v[0], (float)v[1]);
    }

    private boolean IsObbyHole(BlockPos blockPos) {
        BlockPos boost = blockPos.add(0, 1, 0);
        BlockPos boost2 = blockPos.add(0, 0, 0);
        BlockPos boost3 = blockPos.add(0, 0, -1);
        BlockPos boost4 = blockPos.add(1, 0, 0);
        BlockPos boost5 = blockPos.add(-1, 0, 0);
        BlockPos boost6 = blockPos.add(0, 0, 1);
        BlockPos boost7 = blockPos.add(0, 2, 0);
        BlockPos boost8 = blockPos.add(0.5, 0.5, 0.5);
        BlockPos boost9 = blockPos.add(0, -1, 0);
        return !(HoleESP.mc.world.getBlockState(boost).getBlock() != Blocks.AIR || this.IsBRockHole(blockPos) || HoleESP.mc.world.getBlockState(boost2).getBlock() != Blocks.AIR || HoleESP.mc.world.getBlockState(boost7).getBlock() != Blocks.AIR || HoleESP.mc.world.getBlockState(boost3).getBlock() != Blocks.OBSIDIAN && HoleESP.mc.world.getBlockState(boost3).getBlock() != Blocks.BEDROCK || HoleESP.mc.world.getBlockState(boost4).getBlock() != Blocks.OBSIDIAN && HoleESP.mc.world.getBlockState(boost4).getBlock() != Blocks.BEDROCK || HoleESP.mc.world.getBlockState(boost5).getBlock() != Blocks.OBSIDIAN && HoleESP.mc.world.getBlockState(boost5).getBlock() != Blocks.BEDROCK || HoleESP.mc.world.getBlockState(boost6).getBlock() != Blocks.OBSIDIAN && HoleESP.mc.world.getBlockState(boost6).getBlock() != Blocks.BEDROCK || HoleESP.mc.world.getBlockState(boost8).getBlock() != Blocks.AIR || HoleESP.mc.world.getBlockState(boost9).getBlock() != Blocks.OBSIDIAN && HoleESP.mc.world.getBlockState(boost9).getBlock() != Blocks.BEDROCK);
    }

    private boolean IsBRockHole(BlockPos blockPos) {
        BlockPos boost = blockPos.add(0, 1, 0);
        BlockPos boost2 = blockPos.add(0, 0, 0);
        BlockPos boost3 = blockPos.add(0, 0, -1);
        BlockPos boost4 = blockPos.add(1, 0, 0);
        BlockPos boost5 = blockPos.add(-1, 0, 0);
        BlockPos boost6 = blockPos.add(0, 0, 1);
        BlockPos boost7 = blockPos.add(0, 2, 0);
        BlockPos boost8 = blockPos.add(0.5, 0.5, 0.5);
        BlockPos boost9 = blockPos.add(0, -1, 0);

        return HoleESP.mc.world.getBlockState(boost).getBlock() == Blocks.AIR && HoleESP.mc.world.getBlockState(boost2).getBlock() == Blocks.AIR && HoleESP.mc.world.getBlockState(boost7).getBlock() == Blocks.AIR && HoleESP.mc.world.getBlockState(boost3).getBlock() == Blocks.BEDROCK && HoleESP.mc.world.getBlockState(boost4).getBlock() == Blocks.BEDROCK && HoleESP.mc.world.getBlockState(boost5).getBlock() == Blocks.BEDROCK && HoleESP.mc.world.getBlockState(boost6).getBlock() == Blocks.BEDROCK && HoleESP.mc.world.getBlockState(boost8).getBlock() == Blocks.AIR && HoleESP.mc.world.getBlockState(boost9).getBlock() == Blocks.BEDROCK;
    }


    public static BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(HoleESP.mc.player.posX), Math.floor(HoleESP.mc.player.posY), Math.floor(HoleESP.mc.player.posZ));
    }

    private List<BlockPos> findObbyHoles() {
        NonNullList positions = NonNullList.create();
        positions.addAll((Collection)this.getSphere(HoleESP.getPlayerPos(), this.range.getDVal(), this.range.getDVal(), false, true, 0).stream().filter(this::IsObbyHole).collect(Collectors.toList()));
        return positions;
    }

    private List<BlockPos> findBRockHoles() {
        NonNullList positions = NonNullList.create();
        positions.addAll((Collection)this.getSphere(HoleESP.getPlayerPos(), this.range.getDVal(), this.range.getDVal(), false, true, 0).stream().filter(this::IsBRockHole).collect(Collectors.toList()));
        return positions;
    }

    public List<BlockPos> getSphere(BlockPos loc, float r, int h, boolean hollow, boolean sphere, int plus_y) {
        ArrayList<BlockPos> circleblocks = new ArrayList<BlockPos>();
        int cx = loc.getX();
        int cy = loc.getY();
        int cz = loc.getZ();
        int x = cx - (int)r;
        while ((float)x <= (float)cx + r) {
            int z = cz - (int)r;
            while ((float)z <= (float)cz + r) {
                int y = sphere ? cy - (int)r : cy;
                do {
                    float f = sphere ? (float)cy + r : (float)(cy + h);
                    if (!((float)y < f)) break;
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
                    if (!(!(dist < (double)(r * r)) || hollow && dist < (double)((r - 1.0f) * (r - 1.0f)))) {
                        BlockPos l = new BlockPos(x, y + plus_y, z);
                        circleblocks.add(l);
                    }
                    ++y;
                } while (true);
                ++z;
            }
            ++x;
        }
        return circleblocks;
    }

    private static void setYawAndPitch(float yaw1, float pitch1) {
        yaw = yaw1;
        pitch = pitch1;
        isSpoofingAngles = true;
    }

    private static void resetRotation() {
        if (isSpoofingAngles) {
            yaw = HoleESP.mc.player.rotationYaw;
            pitch = HoleESP.mc.player.rotationPitch;
            isSpoofingAngles = false;
        }
    }

    @Override
    public void onDisable() {
        this.render = null;
        HoleESP.resetRotation();
    }
} 