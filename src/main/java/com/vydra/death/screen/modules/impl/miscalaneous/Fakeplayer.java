package com.vydra.death.screen.modules.impl.miscalaneous;

import com.mojang.authlib.GameProfile;
import com.vydra.death.screen.events.UpdateWalkingPlayerEvent;
import com.vydra.death.screen.modules.Category;
import com.vydra.death.screen.modules.Module;
import com.vydra.death.screen.modules.settings.types.BooleanSetting;
import com.vydra.death.screen.modules.settings.types.SliderSetting;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.UUID;

public class Fakeplayer extends Module {
    public Fakeplayer() {
        super("FakePlayer", Category.MISCELLANEOUS);
    }

    private BooleanSetting rotations = new BooleanSetting.Builder()
            .withModule(this)
            .withName("Rotation")
            .withDefaultValue(true)
            .build();

    private BooleanSetting attack = new BooleanSetting.Builder()
            .withModule(this)
            .withName("Attack")
            .withDefaultValue(true)
            .build();

    private BooleanSetting move = new BooleanSetting.Builder()
            .withModule(this)
            .withName("Move")
            .withDefaultValue(false)
            .build();

    private SliderSetting speed = new SliderSetting.Builder()
            .withModule(this)
            .withName("Speed")
            .withDefaultValue(0.3)
            .max(5)
            .min(0.1)
            .build();

    private EntityOtherPlayerMP player;
    private float[] startRotations;

    @Override
    public void onEnable() {
        super.onEnable();
        if (mc.player == null) {
            onDisable();
            return;
        }
        if (player == null) {
            player = new EntityOtherPlayerMP(mc.world, new GameProfile(UUID.randomUUID(), mc.player.getName()));
            player.copyLocationAndAnglesFrom(mc.player);
            player.inventory.copyInventory(mc.player.inventory);
        }
        mc.world.addEntityToWorld(-1337, player);
        startRotations = new float[]{ mc.player.rotationYaw, mc.player.rotationPitch, mc.player.prevRotationYaw };
    }

    @Override
    public void onDisable() {
        super.onDisable();
        if (player != null) {
            mc.world.removeEntityFromWorld(-1337);
            player = null;
        }
    }

    @SubscribeEvent
    public void onUpdate(UpdateWalkingPlayerEvent event) {
        if (player == null) return;

        if (attack.isValue()) {
            player.swingArm(EnumHand.MAIN_HAND);
            player.swingArm(EnumHand.OFF_HAND);
        }

        if (rotations.isValue() && mc.player != null) {
            float[] rotations = getRotations(player, new Vec3d(mc.player.posX, mc.player.posY + 1, mc.player.posZ));
            player.rotationYawHead = rotations[0];
            player.rotationYaw = rotations[0];
            player.rotationPitch = rotations[1];
        }

        if (move.isValue()) {
            double[] calc = directionSpeed(speed.getValue());
            player.move(MoverType.SELF, calc[0], 0, calc[1]);
        }
    }

    public static float[] getRotations(Entity from, Vec3d vec) {
        Vec3d eyesPos = new Vec3d(from.posX, from.posY + from.getEyeHeight(), from.posZ);
        double diffX = vec.x - eyesPos.x;
        double diffY = vec.y - eyesPos.y;
        double diffZ = vec.z - eyesPos.z;
        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90F;
        float pitch = (float) -Math.toDegrees(Math.atan2(diffY, diffXZ));

        return new float[]{MathHelper.wrapDegrees(yaw), MathHelper.wrapDegrees(pitch)};
    }

    public double[] directionSpeed(double speed) {
        float forward = (float) speed / 6;
        float side = 0;
        float yaw = startRotations[0] + (startRotations[0] - startRotations[2]) * mc.getRenderPartialTicks();

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
