package com.vydra.death.screen.modules.impl.render;

import com.vydra.death.screen.modules.Category;
import com.vydra.death.screen.modules.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class FullBright extends Module {

    public FullBright() {
        super("FullBright", "Full bright, module that changes brightness", Category.RENDER);
    }

    float oldGamma;

    @Override
    public void onEnable() {
        super.onEnable();
        oldGamma = mc.gameSettings.gammaSetting;
        mc.gameSettings.gammaSetting = 10000f;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.gameSettings.gammaSetting = oldGamma;
    }
}
