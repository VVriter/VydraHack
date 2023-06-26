package com.vydra.death.screen.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

@AllArgsConstructor
@Data
public class BlockPosWithFacing {
     private BlockPos blockPos;
     private EnumFacing enumFacing;
}
