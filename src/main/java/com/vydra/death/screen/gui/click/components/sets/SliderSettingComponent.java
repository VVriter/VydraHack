package com.vydra.death.screen.gui.click.components.sets;

import com.vydra.death.screen.gui.click.GuiMain;
import com.vydra.death.screen.gui.click.GuiUtil;
import com.vydra.death.screen.gui.click.IGuiComponent;
import com.vydra.death.screen.modules.settings.Setting;
import com.vydra.death.screen.modules.settings.types.SliderSetting;
import com.vydra.death.screen.utils.Render2d;

import java.awt.*;

import static com.vydra.death.screen.gui.click.GuiUtil.drawStringCustom;

public class SliderSettingComponent implements IGuiComponent {


    private Setting setting;
    private int x;
    private int y;
    public double difference;


    public SliderSettingComponent(Setting setting, int x, int y) {
        this.setting = setting;
        this.x = x;
        this.y = y;
        this.difference = ((SliderSetting)setting).getMax() - ((SliderSetting)setting).getMin();
    }


    @Override
    public void onClick(int x, int y, int state) {
        if (GuiUtil.isHoveringOnTheComponent(this, x, y)) {
            if (state == 0) {
                GuiMain.isDragging = true;
                setSettingFromX(x);
            }
        }
    }


    public void setSettingFromX(int mouseX) {
        double percent = ((double) mouseX - this.x) / ((double) this.getWidth());
        double result = ((SliderSetting)setting).getMin() + ((float) this.difference * percent);
        ((SliderSetting)setting).setValue((double) Math.round(10.0 * result) / 10.0);
    }


    @Override
    public void onHover(int x, int y) {
        if (GuiUtil.isHoveringOnTheComponent(this, x, y)) {

        }
    }

    @Override
    public void draw() {
        Render2d.drawGradientRectHorizontal(new Rectangle((int) x-1, (int) y-1, getWidth()+2, getHeight()+2));


        Render2d.drawGradientRectHorizontalActiveGui(new Rectangle((int) x, (int) y, (int) ((int) (getWidth() - 4) * (Double.parseDouble(String.valueOf(((SliderSetting)setting).getValue())) - ((SliderSetting)setting).getMin()) / difference), getHeight()));


        //Rendering module name
        drawStringCustom(setting.getName(), (int) x+2, (int) y+4, Color.WHITE.getRGB(), 0.9, 0.9);
        drawStringCustom(String.valueOf(((SliderSetting)setting).getValue()),(int) x+getWidth()-15, (int) y+4, Color.WHITE.getRGB(), 0.9, 0.9);
    }


    @Override
    public void setY(int y) {
        IGuiComponent.super.setY(y);
        this.y = y;
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
}
