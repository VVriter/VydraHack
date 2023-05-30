package com.vydra.death.screen.gui.click.components.sets.color;

import com.vydra.death.screen.gui.click.GuiUtil;
import com.vydra.death.screen.gui.click.IGuiComponent;
import com.vydra.death.screen.modules.settings.Setting;
import com.vydra.death.screen.utils.Render2d;
import com.vydra.death.screen.utils.animations.EaseLeft;
import com.vydra.death.screen.utils.animations.EaseRight;

import java.awt.*;
import java.awt.geom.Point2D;

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
                new Rectangle((int) x-1, (int) y-1, getWidth()+2, getHeight()+2)
        );


        if (!isHovering && hoverAnimationx != 0) {
            hoverAnimationx = easeLeft.getValue(hoverAnimationx, y, 5);
        }

        if (isHovering && hoverAnimationx != getWidth()) {
            hoverAnimationx = easeRight.getValue(hoverAnimationx, y, 5);
        }

        Render2d.drawGradientRectHorizontal(
                new Rectangle((int) x, (int) y, hoverAnimationx, getHeight()),
                new Color(0x8C1A0750, true),
                new Color(0x8C1A0750, true)
        );


        drawStringCustom(setting.name, (int) x+2, (int) y+4, Color.WHITE.getRGB(), 0.9, 0.9);
        Render2d.drawFilledCircle(new Point2D.Double(x + getWidth() - 10, y+6),(Color) setting.getValue(), 4);

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
