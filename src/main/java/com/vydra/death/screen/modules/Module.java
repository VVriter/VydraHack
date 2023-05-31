package com.vydra.death.screen.modules;

import com.vydra.death.screen.modules.settings.types.KeyBindSetting;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;

import java.util.*;

public class Module {

    public Minecraft mc = Minecraft.getMinecraft();

    @Getter private String name;
    @Getter private String description;
    @Getter private Category category;
    @Getter public boolean isEnabled = false;

    @Getter
    public KeyBindSetting keySetting = new KeyBindSetting.Builder()
            .withDefaultValue(Keyboard.KEY_NONE)
            .withModule(this)
            .withDescription(this.getName() + " module keybind")
            .withName("Key")
            .build();

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
        keySetting.setValue(key);
    }




    public void onEnable() {
        isEnabled = true;
        MinecraftForge.EVENT_BUS.register(this);
        listeners.forEach(MinecraftForge.EVENT_BUS::register);
    }
    public void onDisable() {
        isEnabled = false;
        MinecraftForge.EVENT_BUS.unregister(this);
        listeners.forEach(MinecraftForge.EVENT_BUS::unregister);
    }

    private List<Object> listeners = new ArrayList<>();

    public void registerListener(Object... objects) {
        listeners.addAll(Arrays.asList(objects));
    }

    public void toogle() {
        if (isEnabled) onDisable();
        else onEnable();
    }

}

