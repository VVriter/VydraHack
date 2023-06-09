package com.vydra.death.screen;

import com.vydra.death.screen.managers.ConfigManager;
import com.vydra.death.screen.managers.DiscordRpcManager;
import com.vydra.death.screen.managers.RotationManager;
import com.vydra.death.screen.modules.ModuleManager;
import com.vydra.death.screen.modules.settings.SettingManager;

import com.vydra.death.screen.managers.IconTitleManager;

import com.vydra.death.screen.plugins.PluginLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod(modid = Main.MODID, name = Main.NAME, version = Main.VERSION)
public class Main {
    public static final String MODID = "vydrahack";
    public static final String NAME = "VydraHack";
    public static final String VERSION = "1.0";

    private static Logger logger;

    public static SettingManager settingManager;
    public static ModuleManager moduleManager;

    public static PluginLoader pluginLoader;

    public static ConfigManager configManager;
    public static DiscordRpcManager discordRpcManager;
    public static IconTitleManager iconTitleManager;
    public static RotationManager rotationManager;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        logger.info("Loading vydra hack");

        settingManager = new SettingManager();
        moduleManager = new ModuleManager();
        moduleManager.register();

        try {
            moduleManager.loadConfig(new File("./vydra/default.cfg"));
        } catch (Exception ignored) {}

        configManager = new ConfigManager();
        discordRpcManager = new DiscordRpcManager();
        iconTitleManager = new IconTitleManager();
        rotationManager = new RotationManager();

        try {
            pluginLoader = new PluginLoader();
        } catch (Exception ignored) {

        }
    }


    @EventHandler
    public void onShutdown(FMLServerStoppedEvent event) {
        moduleManager.saveConfig(moduleManager.getDefaultConfigFile());
    }

}
