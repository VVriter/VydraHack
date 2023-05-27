package com.vydra.death.screen;

import com.vydra.death.screen.managers.DiscordRpcManager;
import com.vydra.death.screen.managers.RotationManager;
import com.vydra.death.screen.modules.ModuleManager;
import com.vydra.death.screen.modules.settings.SettingManager;

import com.vydra.death.screen.managers.IconTitleManager;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = Main.MODID, name = Main.NAME, version = Main.VERSION)
public class Main {
    public static final String MODID = "vydrahack";
    public static final String NAME = "Vydra Hack";
    public static final String VERSION = "1.0";

    private static Logger logger;

    public static SettingManager settingManager;
    public static ModuleManager moduleManager;
    public static RotationManager rotationManager;
    public static DiscordRpcManager discordRpcManager;
    public static IconTitleManager iconTitleManager;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        iconTitleManager = new IconTitleManager();
        settingManager = new SettingManager();
        moduleManager = new ModuleManager();
        moduleManager.register();
        discordRpcManager = new DiscordRpcManager();
        rotationManager = new RotationManager();
    }

}
