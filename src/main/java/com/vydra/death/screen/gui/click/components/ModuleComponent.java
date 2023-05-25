package com.vydra.death.screen.gui.click.components;

import com.vydra.death.screen.Main;
import com.vydra.death.screen.gui.click.GuiUtil;
import com.vydra.death.screen.gui.click.IGuiComponent;
import com.vydra.death.screen.gui.click.components.sets.BooleanSettingComponent;
import com.vydra.death.screen.gui.click.components.sets.KeySettingComponent;
import com.vydra.death.screen.gui.click.components.sets.SliderSettingComponent;
import com.vydra.death.screen.modules.Module;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import static com.vydra.death.screen.gui.click.GuiUtil.drawStringCustom;

public class ModuleComponent implements IGuiComponent {

    private int x;
    private int y;
    private Module module;
    private List<IGuiComponent> settings;
    private boolean isOpened;
    public int settingsOffset;
    private ModuleButtonComponent moduleButtonComponent;

    public ModuleComponent(Module module, int x, int y) {
        this.x = x;
        this.y = y;
        this.settingsOffset = 13;
        isOpened = false;
        this.module = module;
        settings = new ArrayList<>();


        moduleButtonComponent = new ModuleButtonComponent(module, x, y);

        Main.settingManager.modulesSettings(module).forEach(setting -> {
            switch (setting.mode) {
                case BOOL: {
                    settings.add(new BooleanSettingComponent(setting, x, y));
                    break;
                }

                case NUMBER: {
                    settings.add(new SliderSettingComponent(setting, x, y));
                    break;
                }

                case KEY: {
                    settings.add(new KeySettingComponent(setting, x, y));
                    break;
                }
            }
        });
    }



    @Override
    public void onClick(int x, int y, int state) {


        settings.stream().filter(setting -> GuiUtil.isHoveringOnTheComponent(setting, x, y)).forEach(e-> e.onClick(x, y, state));


        if (GuiUtil.isHoveringOnTheComponent(moduleButtonComponent, x, y)) {
            if (state == 1) {

                if (!isOpened) {
                    int off = 0;
                    for (IGuiComponent component : settings) {
                        off += component.getHeight();
                    }
                    settingsOffset = 13 + off;
                } else {
                    settingsOffset = 13;
                }

                isOpened = !isOpened;
            }
            moduleButtonComponent.onClick(x, y, state);
        }


    }



    @Override
    public void onHover(int x, int y) {

        settings.stream().filter(setting -> GuiUtil.isHoveringOnTheComponent(setting, x, y)).forEach(e-> e.onHover(x, y));

        if (!GuiUtil.isHoveringOnTheComponent(this, x, y)) return;
        //Hovering logic here
    }

    @Override
    public void draw() {
        moduleButtonComponent.setY(getY());
        moduleButtonComponent.draw();

        int settingYOffset = y + 13;

        if (!Main.settingManager.modulesSettings(module).isEmpty()) {
            if (isOpened) {
                drawStringCustom("-",(int) x+getWidth()-6, (int) y+4, Color.WHITE.getRGB(), 0.9, 0.9);
                for (IGuiComponent component : settings) {
                    component.setY(settingYOffset);
                    component.draw();
                    settingYOffset += component.getHeight();
                }
            } else drawStringCustom("+",(int) x+getWidth()-6, (int) y+4, Color.WHITE.getRGB(), 0.9, 0.9);
        }
    }


    @Override
    public void onKeyTyped(int keycode) {
        IGuiComponent.super.onKeyTyped(keycode);
        settings.stream().forEach(e-> e.onKeyTyped(keycode));
    }

    public void setY(int y) {
        this.y = y;
    }



    @Override
    public int getWidth() {
        return 100;
    }

    @Override
    public int getHeight() {
        return settingsOffset;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }
}
