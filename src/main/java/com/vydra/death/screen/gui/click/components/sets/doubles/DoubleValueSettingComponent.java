package com.vydra.death.screen.gui.click.components.sets.doubles;


import com.vydra.death.screen.gui.click.IGuiComponent;
import com.vydra.death.screen.modules.settings.DoubleValueSetting;
import com.vydra.death.screen.modules.settings.Setting;

public class DoubleValueSettingComponent implements IGuiComponent {


    private int x;
    private int y;
    private Setting<DoubleValueSetting> setting;



    public DoubleValueSettingComponent(Setting<DoubleValueSetting> setting, int x, int y) {
        this.x = x;
        this.y = y;
        this.setting = setting;
    }






    @Override
    public void onClick(int x, int y, int state) {

    }

    @Override
    public void onHover(int x, int y) {

    }

    @Override
    public void draw() {

    }










    @Override
    public int getWidth() {
        return 100;
    }

    @Override
    public int getHeight() {
        return 13;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setY(int y) {
        IGuiComponent.super.setY(y);
        this.y = y;
    }

    @Override
    public void onKeyTyped(int keycode) {
        IGuiComponent.super.onKeyTyped(keycode);
    }
}
