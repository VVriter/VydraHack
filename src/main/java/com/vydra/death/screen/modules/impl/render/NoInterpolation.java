package com.vydra.death.screen.modules.impl.render;

import com.vydra.death.screen.modules.Category;
import com.vydra.death.screen.modules.Module;

public class NoInterpolation extends Module {

    public static NoInterpolation INSTANCE;

    public NoInterpolation() {
        super("NoInterpolation", "Removes entity interpolation", Category.RENDER);
        INSTANCE = this;
    }
}
