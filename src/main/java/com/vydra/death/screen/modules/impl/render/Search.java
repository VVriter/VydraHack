package com.vydra.death.screen.modules.impl.render;

import com.vydra.death.screen.modules.Category;
import com.vydra.death.screen.modules.Module;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Search extends Module {
    public Search() {
        super("Search", Category.RENDER);
    }

    @SubscribeEvent
    public void onRender(RenderWorldLastEvent event) {

    }

}
