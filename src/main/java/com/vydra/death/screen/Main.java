package com.vydra.death.screen;

import com.vydra.death.screen.manager.RotationManager;
import com.vydra.death.screen.modules.ModuleManager;
import com.vydra.death.screen.modules.settings.SettingManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = Main.MODID, name = Main.NAME, version = Main.VERSION)
public class Main {
    public static final String MODID = "antideathscreen";
    public static final String NAME = "Anti Death Screen Bug";
    public static final String VERSION = "1.0";

    private static Logger logger;

    public static SettingManager settingManager;
    public static ModuleManager moduleManager;
    public static RotationManager rotationManager;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        settingManager = new SettingManager();
        moduleManager = new ModuleManager();
        moduleManager.register();

        rotationManager = new RotationManager();
    }

}
