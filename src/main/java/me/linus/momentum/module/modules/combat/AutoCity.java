package me.linus.momentum.module.modules.combat;

import java.util.ArrayList;
import java.util.List;

import me.linus.momentum.Momentum;
import me.linus.momentum.module.Category;
import me.linus.momentum.module.Module;
import me.linus.momentum.module.modules.gui.ClickGUI;
import me.linus.momentum.utils.settings.Setting;
import me.linus.momentum.utils.utils.RenderUtil;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;


import static me.linus.momentum.Momentum.moduleManager;

public class AutoCity extends Module {
    public AutoCity() {
        super("AutoCity", Category.Combat);
    }

    private boolean firstRun;
    private BlockPos mineTarget = null;
    private EntityPlayer closestTarget;

    public Setting range;
    public Setting mode;
    public Setting esp;
    public Setting toggle;
    Setting hidden;

    @Override
    public void setup() {
        ArrayList<String> option = new ArrayList<>();
        option.add("Packet");
        option.add("Vanilla");

        rSetting(range = new Setting("Range", this, 7, 0, 9, true, "range"));
        rSetting(esp = new Setting("BreakESP", this, true, ""));
        rSetting(toggle = new Setting("Toggles", this, true, ""));
        rSetting(mode = new Setting("Mode", this, "Packet", option,""));
        rSetting(hidden = new Setting("Hidden", this, false, "hide"));
    }

    @Override
    public void onEnable() {
        if(mc.player == null) {
            this.toggle();
            return;
        }
        MinecraftForge.EVENT_BUS.register(this);
        firstRun = true;
    }

    @Override
    public void onDisable() {
        if(mc.player == null) {
            return;
        }
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    @Override
    public void onUpdate() {
        if(mc.player == null) {
            return;
        }
        findClosestTarget();

        if (closestTarget == null) {
            if (firstRun) {
                firstRun = false;
            }
            this.toggle();
            return;
        }

        if (firstRun && mineTarget != null) {
            firstRun = false;
        }

        findCityBlock();
        if(mineTarget != null) {
            int newSlot = -1;
            for (int i = 0; i < 9; i++) {
                ItemStack stack = mc.player.inventory.getStackInSlot(i);
                if (stack == ItemStack.EMPTY) {
                    continue;
                }
                if ((stack.getItem() instanceof ItemPickaxe)) {
                    newSlot = i;
                    break;
                }
            }
            if (newSlot != -1) {
                mc.player.inventory.currentItem = newSlot;
            }

            boolean enabled = Momentum.moduleManager.getModuleByName("PacketMine").isToggled();

            if(!enabled && mode.getSVal() == "Packet") {
                Momentum.moduleManager.getModuleByName("PacketMine").toggle();
            }

            mc.player.swingArm(EnumHand.MAIN_HAND);
            mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, mineTarget, EnumFacing.DOWN));
            mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, mineTarget, EnumFacing.DOWN));

            if(!enabled && mode.getSVal() == "Packet") {
                Momentum.moduleManager.getModuleByName("PacketMine").toggle();
            }

            if (toggle.getBVal() == true) {this.toggle();}

        } else {
            this.toggle();
        }
    }

    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {
        GL11.glEnable(2884);
        ClickGUI gui = (ClickGUI) moduleManager.getModuleByName("ClickGUI");
        if (esp.getBVal() == true){
            RenderUtil.prepare(7);
            RenderUtil.drawBoundingBoxBlockPos(mineTarget, 1.5f, gui.getRed(), gui.getGreen(), gui.getBlue(), 125);
            RenderUtil.release();
        }
    }

    public BlockPos findCityBlock() {
        Double dist = Double.valueOf(this.range.getDVal());
        Vec3d vec = closestTarget.getPositionVector();
        if(mc.player.getPositionVector().distanceTo(vec) <= dist) {
            BlockPos targetX = new BlockPos(vec.add(1, 0, 0));
            BlockPos targetXMinus = new BlockPos(vec.add(-1, 0, 0));
            BlockPos targetZ = new BlockPos(vec.add(0, 0, 1));
            BlockPos targetZMinus = new BlockPos(vec.add(0, 0, -1));
            if(canBreak(targetX)) {
                mineTarget = targetX;
            }
            if(!canBreak(targetX) && canBreak(targetXMinus)) {
                mineTarget = targetXMinus;
            }
            if(!canBreak(targetX) && !canBreak(targetXMinus) && canBreak(targetZ)) {
                mineTarget = targetZ;
            }
            if(!canBreak(targetX) && !canBreak(targetXMinus) && !canBreak(targetZ) && canBreak(targetZMinus)) {
                mineTarget = targetZMinus;
            }
            if((!canBreak(targetX) && !canBreak(targetXMinus) && !canBreak(targetZ) && !canBreak(targetZMinus)) || mc.player.getPositionVector().distanceTo(vec) > dist) {
                mineTarget = null;
            }
        }
        return mineTarget;
    }

    private boolean canBreak(BlockPos pos) {
        final IBlockState blockState = mc.world.getBlockState(pos);
        final Block block = blockState.getBlock();
        return block.getBlockHardness(blockState, mc.world, pos) != -1;
    }

    private void findClosestTarget() {
        List<EntityPlayer> playerList = mc.world.playerEntities;

        closestTarget = null;

        for (EntityPlayer target : playerList) {

            if (target == mc.player) {
                continue;
            }

            if (!isLiving(target)) {
                continue;
            }

            if ((target).getHealth() <= 0) {
                continue;
            }

            if (closestTarget == null) {
                closestTarget = target;
                continue;
            }

            if (mc.player.getDistance(target) < mc.player.getDistance(closestTarget)) {
                closestTarget = target;
            }

        }

    }

    public static boolean isLiving(Entity e) {
        return e instanceof EntityLivingBase;
    }

    @Override
    public String getHudInfo() {
        return " \u00A77[\u00A7f" + this.mode.getSVal() + "\u00A77]";
    }
}