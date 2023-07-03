package com.vydra.death.screen.modules.impl.combat;

import com.vydra.death.screen.modules.Category;
import com.vydra.death.screen.modules.Module;
import com.vydra.death.screen.utils.player.BlockUtil;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

public class SkullBurrow extends Module {
    public SkullBurrow() {
        super("SkullBurrow", Category.COMBAT);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        final int slot = findHotbarBlock(ItemSkull.class);
        if (slot == -1) {
            onDisable();
            return;
        }
        int lastSlot = -1;
        if (SkullBurrow.mc.player.getHeldItemMainhand().getItem().getClass() != ItemSkull.class) {
            lastSlot = SkullBurrow.mc.player.inventory.currentItem;
            SkullBurrow.mc.getConnection().sendPacket(new CPacketHeldItemChange(slot));
        }
        BlockUtil.placeBlock(new BlockPos(SkullBurrow.mc.player.getPositionVector()), EnumHand.MAIN_HAND, true, true, SkullBurrow.mc.player.isSneaking());
        if (lastSlot != -1) {
            SkullBurrow.mc.getConnection().sendPacket(new CPacketHeldItemChange(lastSlot));
        }
        onDisable();
    }

    private int findHotbarBlock(final Class clazz) {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY) {
                if (clazz.isInstance(stack.getItem())) {
                    return i;
                }
                if (stack.getItem() instanceof ItemBlock) {
                    final Block block;
                    if (clazz.isInstance(block = ((ItemBlock)stack.getItem()).getBlock())) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }
}
