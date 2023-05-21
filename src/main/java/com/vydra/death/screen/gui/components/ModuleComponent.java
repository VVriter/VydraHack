package com.vydra.death.screen.gui.components;

import com.vydra.death.screen.Main;
import com.vydra.death.screen.gui.Component;
import com.vydra.death.screen.gui.Frame;
import com.vydra.death.screen.modules.Module;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static com.vydra.death.screen.util.Render2d.mc;

public class ModuleComponent extends Component {

    private Module module;
    public boolean isOpened = false;

    private int settingYOffset;
    public List<Component> settings;
    public ModuleButtonComponent moduleComponentButton;


    public ModuleComponent(Module module, int x, int y) {
        super(x, y, 100, 13);
        this.settingYOffset = y;
        this.module = module;
        settings = new ArrayList<>();
        moduleComponentButton = new ModuleButtonComponent(module, x, y);
        Main.settingManager.modulesSettings(module).forEach(setting -> {


            switch (setting.mode) {

                case BOOL: {
                    BooleanComponent component = new BooleanComponent(setting, (int) x, (int) (settingYOffset+getHeight()));
                    settings.add(component);
                    settingYOffset += component.getHeight();
                    break;
                }

            }


        });
    }


    @Override
    public void onClick(int mouseX, int mouseY, int state) {
        super.onClick(mouseX, mouseY, state);

        switch (state) {
            case 0: {
                if (Frame.isHoveringOnTheComponent(moduleComponentButton, mouseX, mouseY))
                    module.toogle();
                break;
            }
            case 1: {
                isOpened = !isOpened;

                if (isOpened) {
                    int h = 13;
                    for (Component component : settings) {
                        h += component.getHeight();
                    }
                    setHeight(h);
                } else {
                    setHeight(13);
                }

                break;
            }
        }

    }

    @Override
    public void draw(int mouseX, int mouseY) {
        super.draw(mouseX, mouseY);


        moduleComponentButton.setY((int) y);
        moduleComponentButton.draw(mouseX, mouseY);


        this.settingYOffset = (int) y;
        //Shit
        if (!Main.settingManager.modulesSettings(module).isEmpty()) {
            if (isOpened) {
                drawStringCustom("-",(int) x+getWidth()-6, (int) y+4, Color.WHITE.getRGB(), 0.9, 0.9);
                settings.forEach(setting -> {
                    setting.setY(settingYOffset + setting.getHeight());
                    setting.draw(mouseX, mouseY);
                    settingYOffset += setting.getHeight();
                });
            } else drawStringCustom("+",(int) x+getWidth()-6, (int) y+4, Color.WHITE.getRGB(), 0.9, 0.9);
        }

    }





    public void handleSettingClick(int mouseX, int mouseY, int state) {
        settings.forEach(setting -> {
            if (Frame.isHoveringOnTheComponent(setting, mouseX, mouseY))
                setting.onClick(mouseX, mouseY, state);
        });
    }

    public void drawStringCustom(String text, int x, int y, int color, double scaleX, double scaleY) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(scaleX, scaleY, 1.0);
        mc.fontRenderer.drawString(text, (int) (x / scaleX), (int) (y / scaleY), color);
        GlStateManager.popMatrix();
    }
}
