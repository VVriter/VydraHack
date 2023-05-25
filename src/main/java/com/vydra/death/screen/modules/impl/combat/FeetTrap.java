package com.vydra.death.screen.modules.impl.combat;

import com.vydra.death.screen.modules.Category;
import com.vydra.death.screen.modules.Module;
import com.vydra.death.screen.modules.settings.Setting;
import com.vydra.death.screen.util.Timer;
import com.vydra.death.screen.util.player.BlockUtil;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.util.stream.Stream;

public class FeetTrap extends Module {
    public FeetTrap() { super("FeetTrap", "", Category.COMBAT, 0x00); }

    public Setting<Double> delay = new Setting<>("Delay", (double)50, this, 0, 200);
    private Setting<Boolean> rotate = new Setting<>("Rotate", true, this);
    private Setting<Boolean> packet = new Setting<>("Packet", true, this);
    private Setting<Boolean> jumpDisable = new Setting<>("JumpDisable", true, this);
    private Setting<Boolean> sneaking = new Setting<>("Sneaking", true, this);

    Timer timer;

    @Override
    public void onEnable() {
        super.onEnable();
        timer = new Timer();
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (jumpDisable.getValue() && mc.gameSettings.keyBindJump.isKeyDown()) onDisable();
        centerMotion();

        Stream.of(getTemplate(mc.player)).forEach(blockPos -> {
            if (timer.hasPassedMs(delay.getValue())) {
                if (BlockUtil.getBlock(blockPos).equals(Blocks.AIR)) {
                    BlockUtil.placeBlock(blockPos, EnumHand.MAIN_HAND, rotate.getValue(), packet.getValue(), sneaking.getValue());
                    timer.reset();
                    return;
                }
            }
        });
    }

    public void centerMotion() {
        if (canPlaceEachBlockInTemplate()) {
            return;
        }

        double[] centerPos = {Math.floor(mc.player.posX) + 0.5, Math.floor(mc.player.posY), Math.floor(mc.player.posZ) + 0.5};

        mc.player.motionX = (centerPos[0] - mc.player.posX) / 2;
        mc.player.motionZ = (centerPos[2] - mc.player.posZ) / 2;
    }


    public boolean canPlaceEachBlockInTemplate() {

        for (BlockPos pos : getTemplate(mc.player)) {
            if (BlockUtil.getBlock(pos).equals(Blocks.AIR)) {
                if (!BlockUtil.canPlaceBlock(pos)) return false;
            }
        }

        return true;
    }

    private BlockPos[] getTemplate(EntityPlayerSP player) {

        BlockPos p = new BlockPos(Math.floor(player.posX), Math.floor(player.posY), Math.floor(player.posZ)).add(0 , -1, 0);

        final BlockPos[] template = {
                p.add(1, 0, 0),
                p.add(0, 0, 1),
                p.add(-1, 0, 0),
                p.add(0,0,-1),

                p.add(1, 1, 0),
                p.add(-1, 1, 0),
                p.add(0, 1, 1),
                p.add(0,1,-1),
        };
        
        return template;
    }

}
