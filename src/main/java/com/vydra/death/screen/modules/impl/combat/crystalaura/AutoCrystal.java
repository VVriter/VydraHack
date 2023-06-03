package com.vydra.death.screen.modules.impl.combat.crystalaura;

import com.vydra.death.screen.modules.Category;
import com.vydra.death.screen.modules.Module;
import com.vydra.death.screen.modules.impl.combat.crystalaura.listener.WorkingPosCalculator;
import com.vydra.death.screen.modules.impl.combat.crystalaura.listener.TargetListener;

public class AutoCrystal extends Module {

    public AutoCrystal() {
        super("AutoCrystal", "", Category.COMBAT);
        AutoCystalConstants.instance = this;
        registerListener(
                new TargetListener(),
                new WorkingPosCalculator()
        );
    }

}
