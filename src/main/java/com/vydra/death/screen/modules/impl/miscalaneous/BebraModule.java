package com.vydra.death.screen.modules.impl.miscalaneous;

import com.vydra.death.screen.modules.Category;
import com.vydra.death.screen.modules.Module;
import com.vydra.death.screen.util.EspUtil;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BebraModule extends Module {
    public BebraModule() { super("BEBRAMODULE", "cummye", Category.MISCALANEOUS, 0x00); }

    @Override
    public void onEnable() {
        super.onEnable();
        System.out.println("Enabled");
    }

    @Override
    public void onDisable() {
        super.onDisable();
        System.out.println("Disabled");
    }

    @SubscribeEvent
    public void onTick(RenderWorldLastEvent event) {
        mc.world.loadedEntityList.forEach(e-> {
            EspUtil.changeEntityTextureAlpha(e, 0.5f, event.getPartialTicks());
        });
    }

}
