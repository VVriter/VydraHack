package com.vydra.death.screen.modules.settings;

import com.vydra.death.screen.modules.Module;
import lombok.Getter;

public class Setting {

    @Getter
    private SettingType settingType;
    @Getter
    private String name;
    @Getter
    private Module module;

    public Setting(SettingType settingType, String name, Module module) {
        this.settingType = settingType;
        this.name = name;
        this.module = module;
    }

}
