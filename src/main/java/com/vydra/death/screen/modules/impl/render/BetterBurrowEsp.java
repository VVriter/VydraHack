package com.vydra.death.screen.modules.impl.render;

import com.vydra.death.screen.modules.Category;
import com.vydra.death.screen.modules.Module;
import com.vydra.death.screen.modules.settings.Setting;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BetterBurrowEsp extends Module {
    public BetterBurrowEsp() {super("BetterBurrowEsp", "", Category.RENDER, 0x00);}

    private Setting<Boolean> heads = new Setting<>("Heads", true, this);
    private Setting<Boolean> chest = new Setting<>("Chest", true, this);
    private Setting<Boolean> echest = new Setting<>("EChest", true, this);
    private Setting<Boolean> obsidian = new Setting<>("Obsidian", true, this);


    @SubscribeEvent
    public void worldLastRenderEvent(RenderWorldLastEvent event) {

    }

}
