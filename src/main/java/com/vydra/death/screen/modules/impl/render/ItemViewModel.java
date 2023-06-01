package com.vydra.death.screen.modules.impl.render;

import com.vydra.death.screen.modules.Category;
import com.vydra.death.screen.modules.Module;
import com.vydra.death.screen.modules.settings.types.BooleanSetting;
import com.vydra.death.screen.modules.settings.types.SliderSetting;
import it.unimi.dsi.fastutil.booleans.BooleanSet;

public class ItemViewModel extends Module {

    public static ItemViewModel INSTANCE;

    public SliderSetting swingSpeed = new SliderSetting.Builder()
            .withName("Swing speed")
            .withModule(this)
            .min(1)
            .max(40)
            .withDefaultValue(20)
            .build();

    public BooleanSetting noSway = new BooleanSetting.Builder()
            .withModule(this)
            .withName("NoSway")
            .withDefaultValue(true)
            .build();

    public SliderSetting tx = new SliderSetting.Builder()
            .withName("Translate X")
            .withModule(this)
            .min(-300)
            .max(300)
            .withDefaultValue(0)
            .build();

    public SliderSetting ty = new SliderSetting.Builder()
            .withName("Translate Y")
            .withModule(this)
            .min(-300)
            .max(300)
            .withDefaultValue(0)
            .build();

    public SliderSetting tz = new SliderSetting.Builder()
            .withName("Translate Z")
            .withModule(this)
            .min(-300)
            .max(300)
            .withDefaultValue(0)
            .build();

    public SliderSetting rx = new SliderSetting.Builder()
            .withName("Rotate X")
            .withModule(this)
            .min(-300)
            .max(300)
            .withDefaultValue(0)
            .build();

    public SliderSetting ry = new SliderSetting.Builder()
            .withName("Rotate Y")
            .withModule(this)
            .min(-300)
            .max(300)
            .withDefaultValue(0)
            .build();

    public SliderSetting rz = new SliderSetting.Builder()
            .withName("Rotate Z")
            .withModule(this)
            .min(-300)
            .max(300)
            .withDefaultValue(0)
            .build();

    public SliderSetting sx = new SliderSetting.Builder()
            .withName("Scale X")
            .withModule(this)
            .min(0)
            .max(300)
            .withDefaultValue(100)
            .build();

    public SliderSetting sy = new SliderSetting.Builder()
            .withName("Scale Y")
            .withModule(this)
            .min(0)
            .max(300)
            .withDefaultValue(100)
            .build();

    public SliderSetting sz = new SliderSetting.Builder()
            .withName("Scale Z")
            .withModule(this)
            .min(0)
            .max(300)
            .withDefaultValue(100)
            .build();



    public ItemViewModel() {
        super("ItemViewModel", "", Category.RENDER);
        INSTANCE = this;
    }


}
