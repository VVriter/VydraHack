package com.vydra.death.screen.modules.impl.client;

import com.vydra.death.screen.Main;
import com.vydra.death.screen.modules.Category;
import com.vydra.death.screen.modules.Module;
import org.lwjgl.input.Keyboard;

public class DiscordRPC extends Module {
    public DiscordRPC() {super("DiscordRPC", "", Category.CLIENT);}

    @Override
    public void onEnable() {
        super.onEnable();
        Main.discordRpcManager.start();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        Main.discordRpcManager.stop();
    }
}
