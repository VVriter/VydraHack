package com.vydra.death.screen.gui.click.components.sets.color;

import com.vydra.death.screen.gui.click.GuiUtil;
import com.vydra.death.screen.gui.click.IGuiComponent;
import com.vydra.death.screen.modules.settings.Setting;
import com.vydra.death.screen.util.animations.EaseLeft;
import com.vydra.death.screen.util.animations.EaseRight;

public class ColorSettingComponent implements IGuiComponent {

    private int x;
    private int y;
    private Setting setting;
    private int height;
    private boolean isOpened;
    private EaseRight easeRight;
    private EaseLeft easeLeft;

    // Components
    private ColorSettingButton colorSettingButton;
    private ColorSettingSwitcherComponent colorSettingSwitcherComponent;
    private ColorSettingHueBar colorSettingHueBar;

    public ColorSettingComponent(Setting setting, int x, int y) {
        this.setting = setting;
        this.x = x;
        this.y = y;
        this.height = 13;
        isOpened = false;

        easeRight = new EaseRight();
        easeLeft = new EaseLeft();

        colorSettingButton = new ColorSettingButton(setting, x, y);
        colorSettingSwitcherComponent = new ColorSettingSwitcherComponent(setting, x, y);
        colorSettingHueBar = new ColorSettingHueBar(setting, x, y);
    }

    @Override
    public void onClick(int x, int y, int state) {
        colorSettingButton.onClick(x, y, state);
        colorSettingSwitcherComponent.onClick(x, y, state);
        colorSettingHueBar.onClick(x, y, state);

        if (GuiUtil.isHoveringOnTheComponent(colorSettingButton, x, y)) {
            isOpened = !isOpened;
            if (isOpened)
                height = 43;
            else
                height = 13;
        }
    }

    @Override
    public void draw() {
        colorSettingButton.setY(y);
        colorSettingButton.draw();

        if (isOpened) {
            colorSettingSwitcherComponent.setY(y + colorSettingButton.getHeight());
            colorSettingSwitcherComponent.draw();

            colorSettingHueBar.setY(y + colorSettingButton.getHeight() + colorSettingSwitcherComponent.getHeight());
            colorSettingHueBar.draw();
        }
    }

    @Override
    public void onHover(int x, int y) {
        colorSettingSwitcherComponent.onHover(x, y);
        colorSettingButton.onHover(x, y);
        colorSettingHueBar.onHover(x, y);
    }

    @Override
    public int getWidth() {
        return 100;
    }

    @Override
    public int getHeight() {
        return height;
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
}
