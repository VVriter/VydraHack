package com.vydra.death.screen.modules.settings.types;

import com.vydra.death.screen.Main;
import com.vydra.death.screen.modules.Module;
import com.vydra.death.screen.modules.settings.Setting;
import com.vydra.death.screen.modules.settings.SettingType;
import lombok.Getter;
import lombok.Setter;

public class SliderSetting extends Setting {


    @Getter
    private String name;
    @Getter
    private Module module;
    @Getter
    private String description;

    @Getter @Setter
    private double min;
    @Getter @Setter
    private double max;
    @Getter @Setter
    private double value;


    public SliderSetting(Builder builder) {
        super(SettingType.SLIDER, builder.name, builder.module);
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

        private double min;
        private double max;
        private double value;

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

        public Builder min(double min) {
            this.min = min;
            return this;
        }

        public Builder max(double max) {
            this.max = max;
            return this;
        }

        public Builder withDefaultValue(double value) {
            this.value = value;
            return this;
        }

        public SliderSetting build() {
            return new SliderSetting(this);
        }
    }
}
