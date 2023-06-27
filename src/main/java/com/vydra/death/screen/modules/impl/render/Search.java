package com.vydra.death.screen.modules.impl.render;

import com.vydra.death.screen.modules.Category;
import com.vydra.death.screen.modules.Module;
import com.vydra.death.screen.utils.Render3d;
import com.vydra.death.screen.utils.player.BlockUtil;
import net.minecraft.block.BlockPortal;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Search extends Module {
    public Search() {
        super("Search", Category.RENDER);
    }

    @SubscribeEvent
    public void onRender(RenderWorldLastEvent event) {

    }

}
