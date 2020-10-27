package me.linus.momentum.utils.utils;

import me.linus.momentum.api.events.mixin.EventPlayerMove;
import me.linus.momentum.utils.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.MovementInput;

public class MovementUtils {
    private static final Minecraft mc = Wrapper.getMinecraft();

    public static float getSpeed() {
        return (float)Math.sqrt(mc.player.motionX * mc.player.motionX + mc.player.motionZ * mc.player.motionZ);
    }

    public static void strafe() {
        strafe(getSpeed());
    }

    public static boolean isMoving() {
        return mc.player != null && (mc.player.movementInput.moveForward != 0.0F || mc.player.movementInput.moveStrafe != 0.0F);
    }

    public static boolean hasMotion() {
        return mc.player.motionX != 0.0D && mc.player.motionZ != 0.0D && mc.player.motionY != 0.0D;
    }

    public static void strafe(float speed) {
        if (isMoving()) {
            double yaw = getDirection();
            mc.player.motionX = -Math.sin(yaw) * (double)speed;
            mc.player.motionZ = Math.cos(yaw) * (double)speed;
        }
    }

    public static void forward(double length) {
        double yaw = Math.toRadians((double)mc.player.rotationYaw);
        mc.player.setPosition(mc.player.posX + -Math.sin(yaw) * length, mc.player.posY, mc.player.posZ + Math.cos(yaw) * length);
    }

    public static double getDirection() {
        float rotationYaw = mc.player.rotationYaw;
        if (mc.player.moveForward < 0.0F) {
            rotationYaw += 180.0F;
        }

        float forward = 1.0F;
        if (mc.player.moveForward < 0.0F) {
            forward = -0.5F;
        } else if (mc.player.moveForward > 0.0F) {
            forward = 0.5F;
        }

        if (mc.player.moveStrafing > 0.0F) {
            rotationYaw -= 90.0F * forward;
        }

        if (mc.player.moveStrafing < 0.0F) {
            rotationYaw += 90.0F * forward;
        }

        return Math.toRadians((double)rotationYaw);
    }

    public static EntityPlayerSP player() {
        return mc.player;
    }

    public static MovementInput movementInput() {
        return player().movementInput;
    }

    public static double x() {
        return player().posX;
    }

    public static void x(double x) {
        player().posX = x;
    }

    public static double y() {
        return player().posY;
    }

    public static void y(double y) {
        player().posY = y;
    }

    public static double z() {
        return player().posZ;
    }

    public static void z(double z) {
        player().posZ = z;
    }

    public static float yaw() {
        return player().rotationYaw;
    }

    public static void yaw(float yaw) {
        player().rotationYaw = yaw;
    }

    public static float pitch() {
        return player().rotationPitch;
    }

    public static void pitch(float pitch) {
        player().rotationPitch = pitch;
    }

    public static void setMoveSpeed(EventPlayerMove event, double speed) {
        double forward = (double)movementInput().moveForward;
        double strafe = (double)movementInput().moveStrafe;
        float yaw = yaw();
        if (forward == 0.0D && strafe == 0.0D) {
            event.setX(0.0D);
            event.setZ(0.0D);
        } else {
            if (forward != 0.0D) {
                if (strafe > 0.0D) {
                    yaw += (float)(forward > 0.0D ? -45 : 45);
                } else if (strafe < 0.0D) {
                    yaw += (float)(forward > 0.0D ? 45 : -45);
                }

                strafe = 0.0D;
                if (forward > 0.0D) {
                    forward = 1.0D;
                } else if (forward < 0.0D) {
                    forward = -1.0D;
                }
            }

            event.setX(forward * speed * Math.cos(Math.toRadians((double)(yaw + 90.0F))) + strafe * speed * Math.sin(Math.toRadians((double)(yaw + 90.0F))));
            event.setZ(forward * speed * Math.sin(Math.toRadians((double)(yaw + 90.0F))) - strafe * speed * Math.cos(Math.toRadians((double)(yaw + 90.0F))));
        }

    }
}
