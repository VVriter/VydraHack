package com.vydra.death.screen.modules.impl.movement;

import com.vydra.death.screen.events.UpdateWalkingPlayerEvent;
import com.vydra.death.screen.modules.Category;
import com.vydra.death.screen.modules.Module;
import com.vydra.death.screen.modules.settings.types.BooleanSetting;
import com.vydra.death.screen.modules.settings.types.SliderSetting;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class InstantSpeed extends Module {

    BooleanSetting speedPotionCheck = new BooleanSetting.Builder()
            .withModule(this)
            .withName("SpeedPotionCheck")
            .withDefaultValue(true)
            .build();

    SliderSetting speed = new SliderSetting.Builder()
            .withModule(this)
            .withName("Speed")
            .min(0.1)
            .max(10)
            .withDefaultValue(3.4)
            .build();

    public InstantSpeed() {
        super("InstantSpeed", Category.MOVEMENT);
    }

    @SubscribeEvent
    public void onUpdateWalkingPlayerEvent(UpdateWalkingPlayerEvent event) {
        if (speedPotionCheck.isValue()) {
            if (hasSpeedEffect()) changeSpeed();
        } else {
            changeSpeed();
        }
    }

    private void changeSpeed() {
        double[] calc = directionSpeed(this.speed.getValue() / 10.0);
        mc.player.motionX = calc[0];
        mc.player.motionZ = calc[1];
    }

    private double[] directionSpeed(double speed) {
        float forward = mc.player.movementInput.moveForward;
        float side = mc.player.movementInput.moveStrafe;
        float yaw = mc.player.prevRotationYaw + (mc.player.rotationYaw - mc.player.prevRotationYaw) * mc.getRenderPartialTicks();
        if (forward != 0.0f) {
            if (side > 0.0f) {
                yaw += (float) (forward > 0.0f ? -45 : 45);
            } else if (side < 0.0f) {
                yaw += (float) (forward > 0.0f ? 45 : -45);
            }
            side = 0.0f;
            if (forward > 0.0f) {
                forward = 1.0f;
            } else if (forward < 0.0f) {
                forward = -1.0f;
            }
        }
        double sin = Math.sin(Math.toRadians(yaw + 90.0f));
        double cos = Math.cos(Math.toRadians(yaw + 90.0f));
        double posX = (double) forward * speed * cos + (double) side * speed * sin;
        double posZ = (double) forward * speed * sin - (double) side * speed * cos;
        return new double[]{posX, posZ};
    }

    private boolean hasSpeedEffect() {

        if (mc.player != null) {
            Potion speedPotion = MobEffects.SPEED;

            for (PotionEffect effect : mc.player.getActivePotionEffects()) {
                if (effect.getPotion() == speedPotion) {
                    return true; // Player has Speed effect
                }
            }
        }

        return false; // Player does not have Speed effect or player is null
    }
}
