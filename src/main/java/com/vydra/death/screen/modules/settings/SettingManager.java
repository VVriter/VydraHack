package com.vydra.death.screen.modules.settings;

import com.vydra.death.screen.modules.Module;

import java.util.ArrayList;

public class SettingManager {
    public ArrayList<Setting> settings;

    public SettingManager(){
        settings = new ArrayList<>();
    }

    public Setting getSetting(Module module, String name){
        for(Setting setting : modulesSettings(module))
            if(setting.getName() == name)
                return setting;
        return null;
    }

    public Setting getSettingByName(String name){
        for(Setting setting : settings)
            if(setting.getName() == name)
                return setting;
        return null;
    }

    public ArrayList<Setting> modulesSettings(Module module){
        ArrayList<Setting> settings = new ArrayList<>();
        for(Setting setting : this.settings){
            if(setting.getModule().getName() == module.getName())
                settings.add(setting);
        }
        return settings;
    }

    public ArrayList<String> settingsNames(){
        ArrayList<String> names = new ArrayList<>();
        for(Setting setting : this.settings){
            names.add(setting.getName());
        }
        return names;
    }

    public ArrayList<String> settingsNames(Module module){
        ArrayList<String> names = new ArrayList<>();
        for(Setting setting : this.modulesSettings(module)){
            names.add(setting.getName());
        }
        return names;
    }
}
