package com.vydra.death.screen.modules.impl.render;

import com.vydra.death.screen.modules.Category;
import com.vydra.death.screen.modules.Module;
import org.lwjgl.input.Keyboard;

public class FullBright extends Module {
     public FullBright() {
         super("FullBright", "s", Category.RENDER, Keyboard.KEY_NONE);
     }

    @Override
    public void onEnable() {
        super.onEnable();
        mc.gameSettings.gammaSetting = 10000f;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.gameSettings.gammaSetting = 1f;
    }
}
