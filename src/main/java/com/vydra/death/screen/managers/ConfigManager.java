package com.vydra.death.screen.managers;

import com.vydra.death.screen.Main;
import net.minecraft.client.Minecraft;

import java.io.File;

public class ConfigManager {
    public File basePath;
    public File pluginsPath;

    public ConfigManager() {
        basePath = new File(Minecraft.getMinecraft().gameDir.getAbsoluteFile(), Main.NAME);
        pluginsPath = new File(basePath, "plugins");
    }
}
