package com.vydra.death.screen.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class TargetUtil {
    public static Entity getClosest(double maxRange) {
        double lowestDistance = Integer.MAX_VALUE;
        Entity closest = null;
        Minecraft mc = Minecraft.getMinecraft();
        for (Entity entity : mc.world.loadedEntityList) {
            if (entity == mc.player) continue;
            if (entity instanceof EntityPlayer) {
                if (entity.getDistance(mc.player) < lowestDistance) {
                    if (entity.getPositionVector().distanceTo(mc.player.getPositionVector()) <= maxRange) {
                        lowestDistance = entity.getDistance(mc.player);
                        closest = entity;
                    }
                }
            }
        }
        return closest;
    }
}
