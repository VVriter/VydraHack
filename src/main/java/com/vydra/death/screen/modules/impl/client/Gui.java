package com.vydra.death.screen.modules.impl.client;

import com.vydra.death.screen.gui.click.GuiMain;
import com.vydra.death.screen.gui.click.GuiUtil;
import com.vydra.death.screen.modules.Category;
import com.vydra.death.screen.modules.Module;
import com.vydra.death.screen.modules.settings.DoubleValueSetting;
import com.vydra.death.screen.modules.settings.Setting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class Gui extends Module {
    public Gui(){
        super("Gui", "", Category.CLIENT, Keyboard.KEY_P);
    }

    private Setting<Boolean> bebra = new Setting<>("bBe23123123123bra", true, this);
    public Setting<Double> test = new Setting<>("Radius", (double)4, this, 0, 10);
    public Setting<Color> colorSetting = new Setting<>("ColorSet", Color.YELLOW,this);
    public Setting<DoubleValueSetting> doubleValueSettingSetting = new Setting<>("DoubleValueSetting", new DoubleValueSetting(1, 3), this);

    public Setting<Color> baseColor = new Setting<>("BaseColor", new Color(0x4A4ACC),this);
    public Setting<Color> secondBaseColor = new Setting<>("SecondBaseColor", new Color(0x0AE7E7),this);
    public Setting<Color> activeColor = new Setting<>("ActiveColor", new Color(0xECEC08),this);
    public Setting<Color> secondActiveColor = new Setting<>("ColorSet", new Color(0xF80606),this);

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
