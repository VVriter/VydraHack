package com.vydra.death.screen.modules.impl.client;

import com.vydra.death.screen.Main;
import com.vydra.death.screen.gui.click.GuiUtil;
import com.vydra.death.screen.modules.Category;
import com.vydra.death.screen.modules.Module;
import com.vydra.death.screen.modules.settings.types.ColorSetting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class ModulesEnabledList extends Module {

    public ModulesEnabledList() {
        super("ModulesEnabledList", "Displays enabled modules", Category.CLIENT);
    }

    private ColorSetting color = new ColorSetting.Builder()
            .withModule(this)
            .withName("Color")
            .withDescription("Color setting for font")
            .withDefaultValue(Color.RED)
            .build();

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent event) {
        AtomicInteger offset = new AtomicInteger(1);
        Stream.of(Main.moduleManager.getModules()).filter(e-> e.isEnabled).forEach(e-> {
            GuiUtil.drawStringCustom(e.getName() + " [" + Keyboard.getKeyName(e.getKeySetting().getValue()) + "]", 1, offset.get(), color.getValue().getRGB(), 1, 1);
            offset.addAndGet(10);
        });
    }

}
