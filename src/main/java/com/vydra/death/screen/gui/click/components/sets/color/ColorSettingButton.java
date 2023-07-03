package com.vydra.death.screen.gui.click.components.sets.color;

import com.vydra.death.screen.gui.click.GuiUtil;
import com.vydra.death.screen.gui.click.IGuiComponent;
import com.vydra.death.screen.modules.impl.client.Gui;
import com.vydra.death.screen.modules.settings.Setting;
import com.vydra.death.screen.modules.settings.types.ColorSetting;
import com.vydra.death.screen.utils.render.Render2d;
import com.vydra.death.screen.utils.animations.EaseLeft;
import com.vydra.death.screen.utils.animations.EaseRight;

import java.awt.*;

import static com.vydra.death.screen.gui.click.GuiUtil.drawStringCustom;

public class ColorSettingButton implements IGuiComponent {

    private int x;
    private int y;
    private Setting setting;
    private EaseRight easeRight;
    private EaseLeft easeLeft;

    public ColorSettingButton(Setting setting, int x, int y) {
        this.setting = setting;
        this.x = x;
        this.y = y;

        easeRight = new EaseRight();
        easeLeft = new EaseLeft();
    }


    @Override
    public void onClick(int x, int y, int state) {
        if (GuiUtil.isHoveringOnTheComponent(this, x, y)) {

        }
    }


    int hoverAnimationx = 0;
    boolean isHovering = false;

    @Override
    public void onHover(int x, int y) {
        if (GuiUtil.isHoveringOnTheComponent(this, x, y))
        {
            isHovering = true;
        }
    }


    @Override
    public void draw() {


        Render2d.drawGradientRectHorizontal(
                new Rectangle((int) x-1, (int) y-1, getWidth()+2, getHeight()+2),
                Gui.getInstance().baseButtonOutlineColorFirst.getValue(),
                Gui.getInstance().baseButtonOutlineColorSecond.getValue()
        );


        if (!isHovering && hoverAnimationx != 0) {
            hoverAnimationx = easeLeft.getValue(hoverAnimationx, y, 5);
        }

        if (isHovering && hoverAnimationx != getWidth()) {
            hoverAnimationx = easeRight.getValue(hoverAnimationx, y, 5);
        }

        Render2d.drawGradientRectHorizontal(
                new Rectangle((int) x, (int) y, hoverAnimationx, getHeight()),
                Gui.getInstance().baseButtonColorFirst.getValue(),
                Gui.getInstance().baseButtonColorSecond.getValue()
        );


        drawStringCustom(setting.getName(), (int) x+2, (int) y+4, Color.WHITE.getRGB(), 0.9, 0.9);

        Render2d.drawRectangle(new Rectangle(x + getWidth() - 13, y +1, 10, 10), Color.WHITE);
        Render2d.drawRectangle(new Rectangle(x + getWidth() - 12, y +2, 8, 8), ((ColorSetting)setting).getValue());

        isHovering = false;
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
}
