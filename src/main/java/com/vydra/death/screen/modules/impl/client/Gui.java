package com.vydra.death.screen.modules.impl.client;

import com.vydra.death.screen.gui.click.GuiMain;
import com.vydra.death.screen.gui.click.GuiUtil;
import com.vydra.death.screen.modules.Category;
import com.vydra.death.screen.modules.Module;
import com.vydra.death.screen.modules.settings.types.ColorSetting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class Gui extends Module {
    public Gui(){
        super("Gui", "", Category.CLIENT, Keyboard.KEY_P);
    }

    private ColorSetting baseColor = new ColorSetting.Builder()
            .withName("BaseColor")
            .withModule(this)
            .withDescription("Base color of gui")
            .withDefaultValue(new Color(0x4A4ACC))
            .build();

    private ColorSetting secondBaseColor = new ColorSetting.Builder()
            .withName("SecondBaseColor")
            .withModule(this)
            .withDescription("SecondBase color of gui")
            .withDefaultValue(new Color(0x0AE7E7))
            .build();

    private ColorSetting activeColor = new ColorSetting.Builder()
            .withName("ActiveColor")
            .withModule(this)
            .withDescription("activeColor color of gui")
            .withDefaultValue(new Color(0xECEC08))
            .build();

    private ColorSetting secondActiveColor = new ColorSetting.Builder()
            .withName("SecondActiveColor")
            .withModule(this)
            .withDescription("secondActiveColor color of gui")
            .withDefaultValue(new Color(0xF80606))
            .build();

    @Override
    public void onDisable() {
        super.onDisable();
        onEnable();
    }

    @Override
    public void toogle() {
        super.toogle();
        mc.displayGuiScreen(new GuiMain());
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        GuiUtil.baseColor = baseColor.getValue();
        GuiUtil.secondBaseColor = secondBaseColor.getValue();
        GuiUtil.activeColor = activeColor.getValue();
        GuiUtil.secondActiveColor = secondActiveColor.getValue();
    }
}
