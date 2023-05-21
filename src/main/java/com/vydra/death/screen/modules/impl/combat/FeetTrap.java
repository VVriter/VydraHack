package com.vydra.death.screen.modules.impl.combat;

import com.vydra.death.screen.modules.Category;
import com.vydra.death.screen.modules.Module;
import com.vydra.death.screen.util.player.BlockUtil;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.util.stream.Stream;

public class FeetTrap extends Module {
    public FeetTrap() { super("FeetTrap", "", Category.COMBAT, Keyboard.KEY_X); }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        Stream.of(getTemplate(mc.player)).forEach(blockPos -> {
            BlockUtil.placeBlock(blockPos, EnumHand.MAIN_HAND, true, true, true);
        });
        onDisable();
    }



    private BlockPos[] getTemplate(EntityPlayerSP player) {

        BlockPos p = new BlockPos(Math.floor(player.posX), Math.floor(player.posY), Math.floor(player.posZ)).add(0 , -1, 0);

        final BlockPos[] template = {
                p.add(1, 0, 0),
                p.add(-1, 0, 0),
                p.add(0, 0, 1),
                p.add(0,0,-1),

                p.add(1, 1, 0),
                p.add(-1, 1, 0),
                p.add(0, 1, 1),
                p.add(0,1,-1),
        };
        
        return template;
    }

}
