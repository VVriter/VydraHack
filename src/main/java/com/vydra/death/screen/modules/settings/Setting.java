package com.vydra.death.screen.modules.settings;

import com.vydra.death.screen.modules.Module;

public class Setting {

    private SettingType settingType;
    private String name;
    private Module module;

    public Setting(SettingType settingType, String name, Module module) {
        this.settingType = settingType;
        this.name = name;
        this.module = module;
    }

    public SettingType getSettingType() {
        return settingType;
    }

    public String getName() {
        return name;
    }

    public Module getModule() {
        return module;
    }
}
