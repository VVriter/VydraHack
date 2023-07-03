package com.vydra.death.screen.utils.player;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

import static com.vydra.death.screen.utils.render.Render2d.mc;

public class BlockProcessor {

    private BlockPos pos;
    public BlockProcessor(BlockPos pos) {
        this.pos = pos;
    }

    public void placeBlock() {

    }

    public void breakBlock() {

    }

    public Block getBlock() {
        return mc.world.getBlockState(pos).getBlock();
    }
}
