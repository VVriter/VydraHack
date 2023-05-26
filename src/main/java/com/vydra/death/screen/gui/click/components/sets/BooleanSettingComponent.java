package com.vydra.death.screen.gui.click.components.sets;

import com.vydra.death.screen.gui.click.GuiUtil;
import com.vydra.death.screen.gui.click.IGuiComponent;
import com.vydra.death.screen.modules.settings.Setting;
import com.vydra.death.screen.util.Render2d;
import com.vydra.death.screen.util.animations.EaseLeft;
import com.vydra.death.screen.util.animations.EaseRight;

import java.awt.*;

import static com.vydra.death.screen.gui.click.GuiUtil.drawStringCustom;

public class BooleanSettingComponent implements IGuiComponent {

    private Setting setting;
    private int x;
    private int y;

    private EaseRight easeRight;
    private EaseLeft easeLeft;

    public BooleanSettingComponent(Setting setting, int x, int y) {
        this.setting = setting;
        this.x = x;
        this.y = y;

        easeRight = new EaseRight();
        easeLeft = new EaseLeft();
    }


    @Override
    public void setY(int y) {
        IGuiComponent.super.setY(y);
        this.y = y;
    }


    @Override
    public void onClick(int x, int y, int state) {
        if (state == 0) {
            setting.setValue(!(boolean)setting.getValue());
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
                new Color(0xC9B9166E, true),
                new Color(0xC95D439C, true)
        );


        if ((boolean)setting.getValue()) {
            Render2d.drawGradientRectHorizontal(
                    new Rectangle((int) x, (int) y, getWidth(), getHeight()),
                    new Color(0xBA4F0B9B, true),
                    new Color(0xC93911A1, true)
            );
        } else {
            Render2d.drawGradientRectHorizontal(
                    new Rectangle((int) x, (int) y, getWidth(), getHeight()),
                    new Color(0xC95D439C, true),
                    new Color(0xC9B9166E, true)
            );
        }

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
}
