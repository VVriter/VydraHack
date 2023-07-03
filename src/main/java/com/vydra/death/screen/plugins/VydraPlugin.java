package com.vydra.death.screen.plugins;

import com.vydra.death.screen.Main;
import com.vydra.death.screen.modules.Module;

public abstract class VydraPlugin {
    public abstract String getName();
    public abstract String getVersion();

    public void load() {
        System.out.println("Loading plugin with name " + getName() + "|" + getVersion());
    }

    public void unload() {
        System.out.println(getName() + "|" + getVersion() + " " + "unloaded successfully");
    }

    protected final void registerModule(Module module) {
        Main.moduleManager.registerModule(module);
    }
}
