package com.vydra.death.screen.util.player;

import com.vydra.death.screen.mixins.IEntityPlayerSP;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class RotationUtil {
    private static Minecraft mc = Minecraft.getMinecraft();
    public static Vec3d getEyesPos() {
        return new Vec3d(RotationUtil.mc.player.posX, RotationUtil.mc.player.posY + (double) RotationUtil.mc.player.getEyeHeight(), RotationUtil.mc.player.posZ);
    }

    public static float[] getLegitRotations(Vec3d vec) {
        Vec3d eyesPos = RotationUtil.getEyesPos();
        double diffX = vec.x - eyesPos.x;
        double diffY = vec.y - eyesPos.y;
        double diffZ = vec.z - eyesPos.z;
        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        float pitch = (float) (-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[]{RotationUtil.mc.player.rotationYaw + MathHelper.wrapDegrees(yaw - RotationUtil.mc.player.rotationYaw), RotationUtil.mc.player.rotationPitch + MathHelper.wrapDegrees(pitch - RotationUtil.mc.player.rotationPitch)};
    }

    public static void faceVector(Vec3d vec, boolean normalizeAngle) {
        float[] rotations = RotationUtil.getLegitRotations(vec);
        RotationUtil.mc.player.connection.sendPacket(new CPacketPlayer.Rotation(rotations[0], normalizeAngle ? (float) MathHelper.normalizeAngle((int) rotations[1], 360) : rotations[1], RotationUtil.mc.player.onGround));
    }

    public static void rotate(Vec3d vec, boolean sendPacket) {
        float[] rotations = getRotations(vec);

        if (sendPacket) mc.player.connection.sendPacket(new CPacketPlayer.Rotation(rotations[0], rotations[1], mc.player.onGround));
        mc.player.rotationYaw = rotations[0];
        mc.player.rotationPitch = rotations[1];
    }

    public static float[] getRotations(Vec3d vec) {
        Vec3d eyesPos = new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ);
        double diffX = vec.x - eyesPos.x;
        double diffY = vec.y - eyesPos.y;
        double diffZ = vec.z - eyesPos.z;
        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90F;
        float pitch = (float) -Math.toDegrees(Math.atan2(diffY, diffXZ));

        return new float[] { mc.player.rotationYaw + MathHelper.wrapDegrees(yaw - mc.player.rotationYaw), mc.player.rotationPitch + MathHelper.wrapDegrees(pitch - mc.player.rotationPitch) };
    }

    public static void packetFacePitchAndYaw(float p_Pitch, float p_Yaw)
    {
        boolean l_IsSprinting = mc.player.isSprinting();

        if (l_IsSprinting != ((IEntityPlayerSP)mc.player).getServerSprintState()) {
            if (l_IsSprinting) {
                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SPRINTING));
            }
            else {
                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
            }

            ((IEntityPlayerSP)mc.player).setServerSprintState(l_IsSprinting);
        }

        boolean l_IsSneaking = mc.player.isSneaking();

        if (l_IsSneaking != ((IEntityPlayerSP)mc.player).getServerSneakState()) {
            if (l_IsSneaking) {
                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
            }
            else {
                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            }

            ((IEntityPlayerSP)mc.player).setServerSneakState(l_IsSneaking);
        }

        if (mc.getRenderViewEntity() == mc.player) {
            float l_Pitch = p_Pitch;
            float l_Yaw = p_Yaw;

            AxisAlignedBB axisalignedbb = mc.player.getEntityBoundingBox();
            double l_PosXDifference = mc.player.posX - ((IEntityPlayerSP)mc.player).getLastReportedPosX();
            double l_PosYDifference = axisalignedbb.minY - ((IEntityPlayerSP)mc.player).getLastReportedPosY();
            double l_PosZDifference = mc.player.posZ - ((IEntityPlayerSP)mc.player).getLastReportedPosZ();
            double l_YawDifference = l_Yaw - ((IEntityPlayerSP)mc.player).getLastReportedYaw();
            double l_RotationDifference = l_Pitch - ((IEntityPlayerSP)mc.player).getLastReportedPitch();
            ((IEntityPlayerSP)mc.player).setPositionUpdateTicks(((IEntityPlayerSP)mc.player).getPositionUpdateTicks()+1);
            boolean l_MovedXYZ = l_PosXDifference * l_PosXDifference + l_PosYDifference * l_PosYDifference + l_PosZDifference * l_PosZDifference > 9.0E-4D || ((IEntityPlayerSP)mc.player).getPositionUpdateTicks() >= 20;
            boolean l_MovedRotation = l_YawDifference != 0.0D || l_RotationDifference != 0.0D;

            if (mc.player.isRiding()) {
                mc.player.connection.sendPacket(new CPacketPlayer.PositionRotation(mc.player.motionX, -999.0D, mc.player.motionZ, l_Yaw, l_Pitch, mc.player.onGround));
                l_MovedXYZ = false;
            }
            else if (l_MovedXYZ && l_MovedRotation) {
                mc.player.connection.sendPacket(new CPacketPlayer.PositionRotation(mc.player.posX, axisalignedbb.minY, mc.player.posZ, l_Yaw, l_Pitch, mc.player.onGround));
            }
            else if (l_MovedXYZ) {
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, axisalignedbb.minY, mc.player.posZ, mc.player.onGround));
            }
            else if (l_MovedRotation) {
                mc.player.connection.sendPacket(new CPacketPlayer.Rotation(l_Yaw, l_Pitch, mc.player.onGround));
            }
            else if (((IEntityPlayerSP)mc.player).getPrevOnGround() != mc.player.onGround) {
                mc.player.connection.sendPacket(new CPacketPlayer(mc.player.onGround));
            }

            if (l_MovedXYZ) {
                ((IEntityPlayerSP)mc.player).setLastReportedPosX(mc.player.posX);
                ((IEntityPlayerSP)mc.player).setLastReportedPosY(axisalignedbb.minY);
                ((IEntityPlayerSP)mc.player).setLastReportedPosZ(mc.player.posZ);
                ((IEntityPlayerSP)mc.player).setPositionUpdateTicks(0);
            }

            if (l_MovedRotation) {
                ((IEntityPlayerSP)mc.player).setLastReportedYaw(l_Yaw);
                ((IEntityPlayerSP)mc.player).setLastReportedPitch(l_Pitch);
            }

            ((IEntityPlayerSP)mc.player).setPrevOnGround(mc.player.onGround);
            ((IEntityPlayerSP)mc.player).setAutoJumpEnabled(mc.gameSettings.autoJump);
        }
    }

    public static float smoothRotation(float from, float to, float speed) {
        float f = MathHelper.wrapDegrees(to - from);

        if (f > speed) {
            f = speed;
        }

        if (f < -speed) {
            f = -speed;
        }

        return from + f;
    }

    public static float[] limitAngleChange(float[] currRot, float[] targetRot, float turnSpeed) {
        float currentYaw = currRot[0];
        float currentPitch = currRot[1];
        float targetYaw = targetRot[0];
        float targetPitch = targetRot[1];
        float yawDifference = getAngleDifference(targetYaw, currentYaw);
        float pitchDifference = getAngleDifference(targetPitch, currentPitch);
        float limitedYaw = currentYaw + ((yawDifference > turnSpeed) ? turnSpeed : Math.max(yawDifference, -turnSpeed));
        float limitedPitch = currentPitch + ((pitchDifference > turnSpeed) ? turnSpeed : Math.max(pitchDifference, -turnSpeed));
        return new float[]{limitedYaw, limitedPitch};
    }

    public static float getAngleDifference(float a, float b) {
        return ((a - b) % 360.0F + 540.0F) % 360.0F - 180.0F;
    }


    public static final float[] smoothRotation(float[] paramArrayOffloat1, float[] paramArrayOffloat2, float paramFloat) {
        float f1 = getAngleDifference(paramArrayOffloat2[0], paramArrayOffloat1[0]);
        float f2 = getAngleDifference(paramArrayOffloat2[1], paramArrayOffloat1[1]);
        float f3 = paramFloat;
        if (f1 > paramFloat) {
            f3 = paramFloat;
        } else {
            f3 = Math.max(f1, -paramFloat);
        }
        float f4 = paramFloat;
        if (f2 > paramFloat) {
            f4 = paramFloat;
        } else {
            f4 = Math.max(f2, -paramFloat);
        }
        float f5 = paramArrayOffloat1[0] + f3;
        float f6 = paramArrayOffloat1[1] + f4;
        return new float[] { f5, f6 };
    }
}