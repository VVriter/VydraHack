package com.vydra.death.screen.modules.impl.render;

import com.vydra.death.screen.modules.Category;
import com.vydra.death.screen.modules.Module;
import com.vydra.death.screen.modules.settings.Setting;
import com.vydra.death.screen.utils.Render2d;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;

public class Ambience extends Module {
    public Ambience() {
        super("Ambience", "", Category.RENDER);
    }

    public Setting<Color> colorSetting = new Setting<>("ColorSet", new Color(0xF8A04C0, true),this);

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent event) {
        ScaledResolution sr = new ScaledResolution(mc);
        Render2d.drawGradientRectHorizontal(new Rectangle(0, 0, sr.getScaledWidth(), sr.getScaledHeight()), colorSetting.getValue(), colorSetting.getValue());
    }
}
