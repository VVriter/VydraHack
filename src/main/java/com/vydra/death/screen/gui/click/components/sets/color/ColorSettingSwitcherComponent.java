package com.vydra.death.screen.gui.click.components.sets.color;

import com.vydra.death.screen.gui.click.GuiMain;
import com.vydra.death.screen.gui.click.GuiUtil;
import com.vydra.death.screen.gui.click.IGuiComponent;
import com.vydra.death.screen.modules.impl.client.Gui;
import com.vydra.death.screen.modules.settings.Setting;
import com.vydra.death.screen.modules.settings.types.ColorSetting;
import com.vydra.death.screen.utils.Render2d;

import java.awt.*;
import java.awt.geom.Point2D;

public class ColorSettingSwitcherComponent implements IGuiComponent {

    private Setting setting;
    private int x;
    private int y;

    public ColorSettingSwitcherComponent(Setting setting, int x, int y) {
        this.setting = setting;
        this.x = x;
        this.y = y;
    }

    @Override
    public void onClick(int x, int y, int state) {
        if (GuiUtil.isHoveringOnTheComponent(this, x, y)) {
            if (state == 0) {
                GuiMain.isDragging = true;
                ((ColorSetting)setting).setValue(colorFromPoint(new Point2D.Double(x, y)));
            }
        }
    }

    @Override
    public void onHover(int x, int y) {
    }

    @Override
    public void draw() {
        Render2d.drawGradientRectHorizontal(
                new Rectangle(x - 1, y - 1, getWidth() + 2, getHeight() + 2),
                Gui.getInstance().baseButtonOutlineColorFirst.getValue(),
                Gui.getInstance().baseButtonOutlineColorSecond.getValue()
        );
        Render2d.drawGradientRectHorizontal(
                new Rectangle(x, y, getWidth(), getHeight()),
                ((ColorSetting)setting).getValue(),
                Color.WHITE
        );
        Render2d.drawGradientRectVertical(new Rectangle(x, y, getWidth(), getHeight()), Color.black, new Color(1, 1, 1, 0));
        Render2d.drawFilledCircle(pointFromColor(), ((ColorSetting)setting).getValue(), 3);
    }

    private Color colorFromPoint(Point2D.Double point) {
        float saturation = (float) Math.abs(100 * (point.x - x) / getWidth());
        float light = (float) Math.abs(80 * (getHeight() - (point.y - y)) / getHeight());
        float[] hsb = Color.RGBtoHSB(((ColorSetting)setting).getValue().getRed(), ((ColorSetting)setting).getValue().getGreen(), ((ColorSetting)setting).getValue().getBlue(), null);
        return Color.getHSBColor(hsb[0], saturation / 100, light / 100);
    }

    private Point2D.Double pointFromColor() {
        float[] hsb = Color.RGBtoHSB(((ColorSetting)setting).getValue().getRed(), ((ColorSetting)setting).getValue().getGreen(), ((ColorSetting)setting).getValue().getBlue(), null);
        float saturation = hsb[1] * 100;
        float light = hsb[2] * 100;
        return new Point2D.Double(x + getWidth() * saturation / 100, y + getHeight() - (getHeight()) * light / 100);
    }

    @Override
    public int getWidth() {
        return 100;
    }

    @Override
    public int getHeight() {
        return 30;
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
        this.y = y;
    }
}
