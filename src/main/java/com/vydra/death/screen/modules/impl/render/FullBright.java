package com.vydra.death.screen.modules.impl.render;

import com.vydra.death.screen.modules.Category;
import com.vydra.death.screen.modules.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class FullBright extends Module {
    public FullBright() {
         super("FullBright", "s", Category.RENDER, Keyboard.KEY_NONE);
     }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        mc.gameSettings.gammaSetting = 10000f;
    }
}
