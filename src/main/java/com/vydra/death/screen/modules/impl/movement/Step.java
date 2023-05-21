package com.vydra.death.screen.modules.impl.movement;

import com.vydra.death.screen.mixins.IEntityPlayerSP;
import com.vydra.death.screen.mixins.IMinecraft;
import com.vydra.death.screen.mixins.ITimer;
import com.vydra.death.screen.modules.Category;
import com.vydra.death.screen.modules.Module;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class Step extends Module {
    public Step() { super("Step", "", Category.MOVEMENT, Keyboard.KEY_NONE); }

    double[] offset = { 0.42, 0.75, 1.0 };

    @Override
    public void onDisable() {
        super.onDisable();
        ((ITimer)((IMinecraft)mc).getTimer()).setTickLength(50f);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (mc.player == null || mc.world == null) return;


        double height = getHeight();
        ((ITimer)((IMinecraft)mc).getTimer()).setTickLength(50f);

        if (!canStep(height)) return;

        ((ITimer) ((IMinecraft) mc).getTimer()).setTickLength((float) (200));
        for (double offset : offset) {
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + offset, mc.player.posZ, false));
        }
        mc.player.setPosition(mc.player.posX, mc.player.posY + 1, mc.player.posZ);

    }


    private boolean canStep(double height) {
        if (height > 1) return false;
        double yOffset = 0.05;
        AxisAlignedBB box = mc.player.getEntityBoundingBox().offset(0.0, yOffset, 0.0);
        List<AxisAlignedBB> collisionBoxes = mc.world.getCollisionBoxes(mc.player, box.offset(0.0, height, 0.0));
        return collisionBoxes.isEmpty() && !mc.player.isOnLadder() && !mc.player.isInWater() &&
                !mc.player.isInLava() && mc.player.onGround && ((IEntityPlayerSP) mc.player).getPrevOnGround() &&
                mc.player.collidedHorizontally;
    }

    private double getHeight() {
        double maxY = 0.0;
        AxisAlignedBB grow = mc.player.getEntityBoundingBox().offset(0.0, 0.05, 0.0).grow(0.05);
        List<AxisAlignedBB> collisionBoxes = mc.world.getCollisionBoxes(mc.player, grow);
        for (AxisAlignedBB aabb : collisionBoxes) {
            if (aabb.maxY > maxY) {
                maxY = aabb.maxY;
            }
        }
        return maxY - mc.player.posY;
    }

}
