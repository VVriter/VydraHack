package com.vydra.death.screen.modules.settings.types;

import com.vydra.death.screen.Main;
import com.vydra.death.screen.modules.Module;
import com.vydra.death.screen.modules.settings.Setting;
import com.vydra.death.screen.modules.settings.SettingType;
import lombok.Getter;
import lombok.Setter;

public class KeyBindSetting extends Setting {
    @Getter
    private String name;
    @Getter
    private Module module;
    @Getter
    private String description;

    @Getter
    @Setter
    private int value;


    public KeyBindSetting(Builder builder) {
        super(SettingType.KEYBIND, builder.name, builder.module);
        this.name = builder.name;
        this.module = builder.module;
        this.description = builder.description;
        this.value = builder.value;
        Main.settingManager.settings.add(this);
    }



    public static class Builder {
        public String name;
        public Module module;
        private String description;

        private int value;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withModule(Module module) {
            this.module = module;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }


        public Builder withDefaultValue(int value) {
            this.value = value;
            return this;
        }

        public KeyBindSetting build() {
            return new KeyBindSetting(this);
        }
    }
}
