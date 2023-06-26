package com.vydra.death.screen.modules;

import com.vydra.death.screen.modules.action.DisableAction;
import com.vydra.death.screen.modules.action.EnableAction;
import com.vydra.death.screen.modules.action.HelpAction;
import com.vydra.death.screen.modules.settings.types.KeyBindSetting;
import lombok.Getter;
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
    private List<Object> listeners = new ArrayList<>();

    @Getter
    private List<IModuleAction> moduleActions = new ArrayList<>();

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
        registerDefaultActions();
    }

    public Module(String name, Category category) {
        this.name = name;
        this.category = category;
        registerDefaultActions();
    }

    public Module(String name, String description, Category category, int key) {
        keySetting.setValue(key);
        this.name = name;
        this.description = description;
        this.category = category;
        keySetting.setValue(key);
        registerDefaultActions();
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

    public void registerListeners(Object... objects) {
        listeners.addAll(Arrays.asList(objects));
    }

    public void registerAction(IModuleAction action) {
        moduleActions.add(action);
    }

    public void registerActions(IModuleAction... actions) {
        moduleActions.addAll(Arrays.asList(actions));
    }

    public void toogle() {
        if (isEnabled) onDisable();
        else onEnable();
    }




    private void registerDefaultActions() {
        registerActions(
                new HelpAction(this),
                new EnableAction(this),
                new DisableAction(this)
        );
    }

}

