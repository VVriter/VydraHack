package com.vydra.death.screen.modules;

import com.vydra.death.screen.modules.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;

public class Module {

    public Minecraft mc = Minecraft.getMinecraft();

    private String name;
    private String description;
    private Category category;
    private int key = 1;
    public boolean isEnabled = false;

    public Setting<Integer> keySetting = new Setting<>("Key", Keyboard.KEY_NONE, this);

    public Module(String name, String description, Category category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }

    public Module(String name, String description, Category category, int key) {
        keySetting.setValue(key);
        this.name = name;
        this.description = description;
        this.category = category;
        this.key = key;
    }

    public void onEnable() {
        isEnabled = true;
        MinecraftForge.EVENT_BUS.register(this);
    }
    public void onDisable() {
        isEnabled = false;
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    public void toogle() {
        if (isEnabled) onDisable();
        else onEnable();
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public int getKey() {
        return keySetting.getValue();
    }
    public void setKey(int key) { keySetting.setValue(key); }

}

