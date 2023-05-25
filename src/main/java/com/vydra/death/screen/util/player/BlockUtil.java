package com.vydra.death.screen.util.player;

import com.vydra.death.screen.Main;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static com.vydra.death.screen.util.Render2d.mc;

public class BlockUtil {

    public static final List<Block> blackList = Arrays.asList(Blocks.ENDER_CHEST, Blocks.CHEST, Blocks.TRAPPED_CHEST, Blocks.CRAFTING_TABLE, Blocks.ANVIL, Blocks.BREWING_STAND, Blocks.HOPPER, Blocks.DROPPER, Blocks.DISPENSER, Blocks.TRAPDOOR, Blocks.ENCHANTING_TABLE);
    public static final List<Block> shulkerList = Arrays.asList(Blocks.WHITE_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.SILVER_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.BLACK_SHULKER_BOX);

    public static Block getBlock(BlockPos pos) {
        try {
            return mc.world.getBlockState(pos).getBlock();
        } catch (NullPointerException e) {
            return null;
        }
    }

     public static boolean placeBlock(BlockPos pos, EnumHand hand, boolean rotate, boolean packet, boolean isSneaking) {
        boolean sneaking = false;
        EnumFacing side = BlockUtil.getFirstFacing(pos);
        if (side == null) {
            return isSneaking;
        }
        BlockPos neighbour = pos.offset(side);
        EnumFacing opposite = side.getOpposite();
        Vec3d hitVec = new Vec3d(neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        Block neighbourBlock = Minecraft.getMinecraft().world.getBlockState(neighbour).getBlock();
        if (!Minecraft.getMinecraft().player.isSneaking() && (blackList.contains(neighbourBlock) || shulkerList.contains(neighbourBlock))) {
            Minecraft.getMinecraft().player.connection.sendPacket(new CPacketEntityAction(Minecraft.getMinecraft().player, CPacketEntityAction.Action.START_SNEAKING));
            Minecraft.getMinecraft().player.setSneaking(true);
            sneaking = true;
        }
        if (rotate)
            RotationUtil.packetFacePitchAndYaw(RotationUtil.getRotations(hitVec)[1],RotationUtil.getRotations(hitVec)[0]);

        mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(hand));
        BlockUtil.rightClickBlock(neighbour, hitVec, hand, opposite, packet);
        Minecraft.getMinecraft().player.swingArm(EnumHand.MAIN_HAND);
        if(rotate)
            Main.rotationManager.stopRotating();
        return sneaking || isSneaking;
    }


    private static EnumFacing getFirstFacing(BlockPos pos) {
        Iterator<EnumFacing> iterator = BlockUtil.getPossibleSides(pos).iterator();
        if (iterator.hasNext()) {
            EnumFacing facing = iterator.next();
            return facing;
        }
        return null;
    }

    private static List<EnumFacing> getPossibleSides(BlockPos pos) {
        ArrayList<EnumFacing> facings = new ArrayList<>();
        for (EnumFacing side : EnumFacing.values()) {
            IBlockState blockState;
            BlockPos neighbour = pos.offset(side);
            if (!Minecraft.getMinecraft().world.getBlockState(neighbour).getBlock().canCollideCheck(Minecraft.getMinecraft().world.getBlockState(neighbour), false) || (blockState = mc.world.getBlockState(neighbour)).getMaterial().isReplaceable())
                continue;
            facings.add(side);
        }
        return facings;
    }


    public static void rightClickBlock(BlockPos pos, Vec3d vec, EnumHand hand, EnumFacing direction, boolean packet) {
        if (packet) {
            float f = (float) (vec.x - (double) pos.getX());
            float f1 = (float) (vec.y - (double) pos.getY());
            float f2 = (float) (vec.z - (double) pos.getZ());
            mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(pos, direction, hand, f, f1, f2));
        } else {
            mc.playerController.processRightClickBlock(mc.player, mc.world, pos, direction, vec, hand);
        }
        mc.player.swingArm(EnumHand.MAIN_HAND);
    }



    public static boolean canPlaceBlock(BlockPos pos) {
        ArrayList<Entity> entities = new ArrayList<Entity>();
        entities.addAll(mc.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(pos)));
        for (Entity entity : entities) {
            if (entity.isEntityAlive()) {
                return false;
            }
        }
        if(!getBlock(pos).equals(Blocks.AIR))
            return false;
        return true;
    }



    public static List<BlockPos> getSphere(float radius, boolean ignoreAir, Entity entity) {
        ArrayList<BlockPos> sphere = new ArrayList<BlockPos>();
        BlockPos pos = new BlockPos(entity.getPositionVector());
        int posX = pos.getX();
        int posY = pos.getY();
        int posZ = pos.getZ();
        int radiuss = (int)radius;
        int x = posX - radiuss;
        while ((float)x <= (float)posX + radius) {
            int z = posZ - radiuss;
            while ((float)z <= (float)posZ + radius) {
                int y = posY - radiuss;
                while ((float)y < (float)posY + radius) {
                    if ((float)((posX - x) * (posX - x) + (posZ - z) * (posZ - z) + (posY - y) * (posY - y)) < radius * radius) {
                        BlockPos position = new BlockPos(x, y, z);
                        if (!ignoreAir || mc.world.getBlockState(position).getBlock() != Blocks.AIR) {
                            sphere.add(position);
                        }
                    }
                    ++y;
                }
                ++z;
            }
            ++x;
        }
        return sphere;
    }


    public static boolean canPlayerSeeBlock(BlockPos pos, World world) {
        RayTraceResult rayTraceResult = world.rayTraceBlocks(
                new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ),
                new Vec3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5),
                false, true, false
        );

        return rayTraceResult != null && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK && rayTraceResult.getBlockPos().equals(pos);
    }
}
