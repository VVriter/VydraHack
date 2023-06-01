package com.vydra.death.screen.modules.impl.client;

import com.vydra.death.screen.gui.click.GuiMain;
import com.vydra.death.screen.modules.Category;
import com.vydra.death.screen.modules.Module;
import com.vydra.death.screen.modules.settings.types.BooleanSetting;
import com.vydra.death.screen.modules.settings.types.ColorSetting;
import lombok.Getter;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class Gui extends Module {

    @Getter private static Gui instance;

    public Gui(){
        super("Gui", "", Category.CLIENT, Keyboard.KEY_P);
        instance = this;
    }


    //Category settings
    public BooleanSetting iconsEnabled = new BooleanSetting.Builder()
            .withModule(this)
            .withName("Enable icons")
            .withDefaultValue(true)
            .build();

    public ColorSetting baseGuiCategoryColorFirst = new ColorSetting.Builder()
            .withModule(this)
            .withName("Category color 1")
            .withDefaultValue(new Color(0xFFA10707, true))
            .build();

    public ColorSetting baseGuiCategoryColorSecond = new ColorSetting.Builder()
            .withModule(this)
            .withName("Category color 2")
            .withDefaultValue(new Color(0xFFA10707, true))
            .build();

    public ColorSetting baseGuiCategoryColorFirstOutline = new ColorSetting.Builder()
            .withModule(this)
            .withName("Category outline 1")
            .withDefaultValue(Color.GRAY)
            .build();

    public ColorSetting baseGuiCategoryColorSecondOutline = new ColorSetting.Builder()
            .withModule(this)
            .withName("Category outline 2 2")
            .withDefaultValue(Color.GRAY)
            .build();


    //Frames
    public BooleanSetting settingsIndecatorEnabled = new BooleanSetting.Builder()
            .withModule(this)
            .withName("Settings indecator")
            .withDefaultValue(true)
            .build();

    public ColorSetting baseButtonColorFirst = new ColorSetting.Builder()
            .withModule(this)
            .withName("Button color 1")
            .withDefaultValue(Color.GRAY)
            .build();

    public ColorSetting baseButtonColorSecond = new ColorSetting.Builder()
            .withModule(this)
            .withName("Button color 2")
            .withDefaultValue(Color.GRAY)
            .build();

    public ColorSetting baseButtonOutlineColorFirst = new ColorSetting.Builder()
            .withModule(this)
            .withName("Button outline color 1")
            .withDefaultValue(Color.GRAY)
            .build();

    public ColorSetting baseButtonOutlineColorSecond = new ColorSetting.Builder()
            .withModule(this)
            .withName("Button outline color 2")
            .withDefaultValue(Color.GRAY)
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


}
