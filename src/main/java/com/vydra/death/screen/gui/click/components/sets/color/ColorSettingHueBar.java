package com.vydra.death.screen.gui.click.components.sets.color;

import com.vydra.death.screen.gui.click.GuiMain;
import com.vydra.death.screen.gui.click.GuiUtil;
import com.vydra.death.screen.gui.click.IGuiComponent;
import com.vydra.death.screen.modules.settings.Setting;
import com.vydra.death.screen.utils.Render2d;

import java.awt.*;
import java.awt.geom.Point2D;

public class ColorSettingHueBar implements IGuiComponent {




    private Setting<Color> setting;
    private int x;
    private int y;
    private int difference = 360;

    public ColorSettingHueBar(Setting<Color> setting, int x, int y) {
        this.setting = setting;
        this.x = x;
        this.y = y;
    }








    @Override
    public void onClick(int x, int y, int state) {
        if (GuiUtil.isHoveringOnTheComponent(this, x, y)) {
            if (state == 0) {
                GuiMain.isDragging = false;
                GuiMain.isDragging = true;
                double percent = ((float) x - (this.x + 3)) / ((float) this.getWidth() - 6);
                float result = (float) (0 + this.difference * percent);

                float[] hsb = Color.RGBtoHSB(setting.getValue().getRed(), setting.getValue().getGreen(), setting.getValue().getBlue(), null);
                float saturation = hsb[1];
                float brightness = hsb[2];
                float alpha = setting.getValue().getAlpha() / 255f;

                Color newColor = Color.getHSBColor(result / difference, saturation, brightness);
                int red = newColor.getRed();
                int green = newColor.getGreen();
                int blue = newColor.getBlue();
                int alphaInt = (int) (alpha * 255);

                this.setting.setValue(new Color(red, green, blue, alphaInt));
            }
        }
    }

    @Override
    public void onHover(int x, int y) {

    }

    @Override
    public void draw() {
        Render2d.drawGradientRectHorizontal(
                new Rectangle(x - 1, y - 1, getWidth() + 2, getHeight() + 2)
        );


        Render2d.drawHueBar(new Rectangle(x, y, getWidth(), getHeight()));
        Render2d.drawFilledCircle(new Point2D.Double(x + Math.round(getWidth() * getHue() / difference), y + 6), Color.WHITE, 5);
    }


    private float getHue() {
        float[] hsb = Color.RGBtoHSB(setting.getValue().getRed(), setting.getValue().getGreen(), setting.getValue().getBlue(), null);
        return hsb[0] * difference;
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
