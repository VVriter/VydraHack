package com.vydra.death.screen.modules.impl.miscalaneous;

import com.vydra.death.screen.events.PacketEvent;
import com.vydra.death.screen.events.SetOpaqueCubeEvent;
import com.vydra.death.screen.mixins.ICPacketPlayer;
import com.vydra.death.screen.modules.Category;
import com.vydra.death.screen.modules.Module;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class Freecam extends Module {
    public Freecam() { super("Freecam", "Module to do fly in walls", Category.MISCALANEOUS, Keyboard.KEY_G); }


    private Vec3d startPos = null;
    private EntityOtherPlayerMP fakePlayer = null;


    @Override
    public void onEnable() {
        super.onEnable();
        startPos = mc.player.getPositionVector();
        fakePlayer = new EntityOtherPlayerMP(mc.world, mc.player.getGameProfile());
        fakePlayer.rotationYawHead = mc.player.getRotationYawHead();
        fakePlayer.copyLocationAndAnglesFrom(mc.player);
        mc.world.addEntityToWorld((int) -(Math.random() * 10000), fakePlayer);
        mc.gameSettings.gammaSetting = 10000.0F;
        mc.player.capabilities.isFlying = true;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.player.capabilities.isFlying = false;
        mc.world.removeEntityFromWorld(fakePlayer.getEntityId());
        mc.player.setPosition(startPos.x, startPos.y, startPos.z);
        mc.gameSettings.gammaSetting = 1F;
    }



    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (mc.player == null) return;
        mc.player.capabilities.isFlying = true;

        if (mc.gameSettings.keyBindJump.isKeyDown())
            mc.player.motionY = 1;
        if (mc.gameSettings.keyBindSneak.isKeyDown())
            mc.player.motionY = -1;

        double[] calc = directionSpeed(1);
        mc.player.motionX = calc[0];
        mc.player.motionZ = calc[1];
    }


    @SubscribeEvent
    public void onPacket(PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketPlayer && fakePlayer != null) {
            ((ICPacketPlayer) event.getPacket()).setX(startPos.x);
            ((ICPacketPlayer) event.getPacket()).setY(startPos.y);
            ((ICPacketPlayer) event.getPacket()).setZ(startPos.z);
            ((ICPacketPlayer) event.getPacket()).setOnGround(true);
        }
    }

    @SubscribeEvent
    public void onOpaqu(SetOpaqueCubeEvent event) {
        event.setCanceled(true);
    }


    @SubscribeEvent
    public void onInteract(PlayerInteractEvent event) {
        rotate(fakePlayer, new Vec3d(event.getPos().getX(), event.getPos().getY(), event.getPos().getZ()), true);
    }




    public void rotate(Entity fakeplayer , Vec3d vec, boolean sendPacket) {
        float[] rotations = getRotations(fakeplayer ,vec);

        if (sendPacket) mc.player.connection.sendPacket(new CPacketPlayer.Rotation(rotations[0], rotations[1],  mc.player.onGround));
        //mc.player.rotationYaw = rotations[0];
        //mc.player.rotationPitch = rotations[1];
    }

    public static float[] getRotations(Entity from, Vec3d vec) {
        Vec3d eyesPos = new Vec3d(from.posX, from.posY + from.getEyeHeight(), from.posZ);
        double diffX = vec.x - eyesPos.x;
        double diffY = vec.y - eyesPos.y;
        double diffZ = vec.z - eyesPos.z;
        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90F;
        float pitch = (float) -Math.toDegrees(Math.atan2(diffY, diffXZ));

        return new float[] { from.rotationYaw + MathHelper.wrapDegrees(yaw - from.rotationYaw), from.rotationPitch + MathHelper.wrapDegrees(pitch - from.rotationPitch) };
    }

    public double[] directionSpeed(int speed) {
        float forward = mc.player.movementInput.moveForward;
        float side = mc.player.movementInput.moveStrafe;
        float yaw = mc.player.prevRotationYaw + (mc.player.rotationYaw - mc.player.prevRotationYaw) * mc.getRenderPartialTicks();

        if (forward != 0.0f) {
            if (side > 0.0f) {
                yaw += (forward > 0.0f) ? -45 : 45;
            } else if (side < 0.0f) {
                yaw += (forward > 0.0f) ? 45 : -45;
            }
            side = 0.0f;
            forward = Math.signum(forward);
        }

        double sin = Math.sin(Math.toRadians(yaw + 90.0));
        double cos = Math.cos(Math.toRadians(yaw + 90.0));

        double posX = forward * speed * cos + side * speed * sin;
        double posZ = forward * speed * sin - side * speed * cos;

        return new double[]{posX, posZ};
    }
}
