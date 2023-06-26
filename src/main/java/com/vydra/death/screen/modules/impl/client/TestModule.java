package com.vydra.death.screen.modules.impl.client;

import com.vydra.death.screen.Main;
import com.vydra.death.screen.modules.Category;
import com.vydra.death.screen.modules.Module;

import java.io.File;

public class TestModule extends Module {
    public TestModule() {
        super("Test", Category.CLIENT);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        Main.moduleManager.loadConfig(new File("./bebra.cfg"));
    }
}
