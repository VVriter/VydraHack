package com.vydra.death.screen.modules.impl.miscalaneous;

import com.vydra.death.screen.events.UpdateWalkingPlayerEvent;
import com.vydra.death.screen.modules.Category;
import com.vydra.death.screen.modules.Module;
import com.vydra.death.screen.modules.settings.Setting;
import com.vydra.death.screen.utils.Render3d;
import com.vydra.death.screen.utils.Timer;
import com.vydra.death.screen.utils.player.BlockUtil;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class ObsidianFiller extends Module {
    public ObsidianFiller() {super("ObsidianFiller", "", Category.MISCALANEOUS, Keyboard.KEY_N);}

    public Setting<Double> radius = new Setting<>("Radius", (double)3, this, 1, 5);
    public Setting<Double> delay = new Setting<>("Delay", (double)50, this, 0, 200);

    Timer timer;

    @Override
    public void onEnable() {
        super.onEnable();
        timer = new Timer();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        timer = null;
    }


    @SubscribeEvent
    public void onTick(UpdateWalkingPlayerEvent event) {
        BlockUtil.getSphere(radius.getValue().floatValue(), true, mc.player).forEach(pos -> {
            if (!isProvidedBlockNeeded(pos)) return;
            if (!timer.hasPassedMs(delay.getValue())) return;

            place(pos, event);

            timer.reset();
        });
    }


    @SubscribeEvent
    public void render(RenderWorldLastEvent event) {

        BlockUtil.getSphere(radius.getValue().floatValue(), true, mc.player).forEach(pos -> {
            if (!isProvidedBlockNeeded(pos)) return;

            Render3d.drawProperBoxESP(pos, new Color(0x710606FA, true), 1, true, true, 50, 1);

        });

    }

    void place(BlockPos pos, UpdateWalkingPlayerEvent event) {
        if (BlockUtil.getBlock(pos.add(1, 0, 0)).equals(Blocks.AIR)) {
            BlockUtil.placeBlock(pos.add(1, 0, 0), EnumHand.MAIN_HAND, true, false, false);
            event.setCanceled(true);
            return;
        }

        if (BlockUtil.getBlock(pos.add(-1, 0, 0)).equals(Blocks.AIR)) {
            BlockUtil.placeBlock(pos.add(-1, 0, 0), EnumHand.MAIN_HAND, true, false, false);
            event.setCanceled(true);
            return;
        }


        if (BlockUtil.getBlock(pos.add(0, 1, 0)).equals(Blocks.AIR)) {
            BlockUtil.placeBlock(pos.add(0, 1, 0), EnumHand.MAIN_HAND, true, false, false);
            event.setCanceled(true);
            return;
        }

        if (BlockUtil.getBlock(pos.add(0, -1, 0)).equals(Blocks.AIR)) {
            BlockUtil.placeBlock(pos.add(0, -1, 0), EnumHand.MAIN_HAND, true, false, false);
            event.setCanceled(true);
            return;
        }

        if (BlockUtil.getBlock(pos.add(0, 0, 1)).equals(Blocks.AIR)) {
            BlockUtil.placeBlock(pos.add(0, 0, 1), EnumHand.MAIN_HAND, true, false, false);
            event.setCanceled(true);
            return;
        }

        if (BlockUtil.getBlock(pos.add(0, 0, -1)).equals(Blocks.AIR)) {
            BlockUtil.placeBlock(pos.add(0, 0, -1), EnumHand.MAIN_HAND, true, true, false);
            event.setCanceled(true);
            return;
        }
    }

    public boolean isProvidedBlockNeeded(BlockPos pos) {

        if (!BlockUtil.canPlayerSeeBlock(pos, mc.world)) return false;
        if (BlockUtil.getBlock(pos).equals(Blocks.OBSIDIAN)) return false;
        if (BlockUtil.getBlock(pos).equals(Blocks.BEDROCK)) return false;

        if (BlockUtil.getBlock(pos.add(1, 0, 0)).equals(Blocks.AIR)) return true;
        if (BlockUtil.getBlock(pos.add(-1, 0, 0)).equals(Blocks.AIR)) return true;
        if (BlockUtil.getBlock(pos.add(0, 1, 0)).equals(Blocks.AIR)) return true;
        if (BlockUtil.getBlock(pos.add(0, -1, 0)).equals(Blocks.AIR)) return true;
        if (BlockUtil.getBlock(pos.add(0, 0, 1)).equals(Blocks.AIR)) return true;
        if (BlockUtil.getBlock(pos.add(0, 0, -1)).equals(Blocks.AIR)) return true;

        return false;

    }
}
