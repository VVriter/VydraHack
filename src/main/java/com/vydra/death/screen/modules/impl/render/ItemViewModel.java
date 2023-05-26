package com.vydra.death.screen.modules.impl.render;

import com.vydra.death.screen.modules.Category;
import com.vydra.death.screen.modules.Module;
import com.vydra.death.screen.modules.settings.Setting;

public class ItemViewModel extends Module {

    public static ItemViewModel INSTANCE;

    public static Setting<Double> swingSpeed;

    public static Setting<Double> tx;
    public static Setting<Double> ty;
    public static Setting<Double> tz;

    public static Setting<Double> rx;
    public static Setting<Double> ry;
    public static Setting<Double> rz;

    public static Setting<Double> sx;
    public static Setting<Double> sy;
    public static Setting<Double> sz;



    public ItemViewModel() {
        super("ItemViewModel", "", Category.RENDER);
        INSTANCE = this;

        swingSpeed =  new Setting<>("Swing speed", (double)20, this, 1, 40);

        tx = new Setting<>("Translate X", (double)0, this, -300, 300);
        ty = new Setting<>("Translate Y", (double)0, this, -300, 300);
        tz = new Setting<>("Translate Z", (double)0, this, -300, 300);

        rx = new Setting<>("Rotate X", (double)0, this, 0, 300);
        ry = new Setting<>("Rotate Y", (double)0, this, 0, 300);
        rz = new Setting<>("Rotate Z", (double)0, this, 0, 300);

        sx = new Setting<>("Scale X", (double)100, this, 0, 300);
        sy = new Setting<>("Scale Y", (double)100, this, 0, 300);
        sz = new Setting<>("Scale Z", (double)100, this, 0, 300);
    }


}
