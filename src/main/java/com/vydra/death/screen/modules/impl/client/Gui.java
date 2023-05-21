package com.vydra.death.screen.modules.impl.client;

import com.vydra.death.screen.gui.VydraGui;
import com.vydra.death.screen.modules.Category;
import com.vydra.death.screen.modules.Module;
import com.vydra.death.screen.modules.settings.Setting;
import org.lwjgl.input.Keyboard;

public class Gui extends Module {
    public Gui(){
        super("Gui", "", Category.CLIENT, Keyboard.KEY_P);
    }

    private Setting<Boolean> bebra = new Setting<>("bBe23123123123bra", true, this);
    public Setting<Double> test = new Setting<>("Radius", (double)4, this, 0, 10);

    @Override
    public void onDisable() {
        super.onDisable();
        onEnable();
    }

    @Override
    public void toogle() {
        super.toogle();
        mc.displayGuiScreen(new VydraGui());
    }
}
