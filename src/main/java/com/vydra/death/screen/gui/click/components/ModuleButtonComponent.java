package com.vydra.death.screen.gui.click.components;

import com.vydra.death.screen.gui.click.GuiUtil;
import com.vydra.death.screen.gui.click.IGuiComponent;
import com.vydra.death.screen.modules.Module;
import com.vydra.death.screen.utils.Render2d;
import com.vydra.death.screen.utils.animations.EaseLeft;
import com.vydra.death.screen.utils.animations.EaseRight;

import java.awt.*;

import static com.vydra.death.screen.gui.click.GuiUtil.drawStringCustom;

public class ModuleButtonComponent implements IGuiComponent {


    private Module module;
    private int x;
    private int y;
    private EaseRight easeRight;
    private EaseLeft easeLeft;


    public ModuleButtonComponent(Module module, int x, int y) {
        this.module = module;
        this.x = x;
        this.y = y;
        easeRight = new EaseRight();
        easeLeft = new EaseLeft();
    }






    @Override
    public void onClick(int x, int y, int state) {
        switch (state) {

            case 0: {
                module.toogle();
                break;
            }

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
        Render2d.drawGradientRectVertical(
                new Rectangle((int) x-1, (int) y-1, getWidth()+2, getHeight()+2),
                new Color(0xC9B9166E, true),
                new Color(0xC95D439C, true)
        );


        if (module.isEnabled) {
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


        //Rendering module name
        drawStringCustom(module.getName(), (int) x+2, (int) y+4, Color.WHITE.getRGB(), 0.9, 0.9);

        isHovering = false;
    }


    @Override
    public void setY(int y) {
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
