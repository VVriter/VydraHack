package com.vydra.death.screen.gui.click.components;

import com.vydra.death.screen.gui.click.IGuiComponent;
import com.vydra.death.screen.modules.Module;
import com.vydra.death.screen.util.Render2d;

import java.awt.*;

import static com.vydra.death.screen.gui.click.GuiUtil.drawStringCustom;

public class ModuleButtonComponent implements IGuiComponent {


    private Module module;
    private int x;
    private int y;


    public ModuleButtonComponent(Module module, int x, int y) {
        this.module = module;
        this.x = x;
        this.y = y;
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

    @Override
    public void onHover(int x, int y) {

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

        //Rendering module name
        drawStringCustom(module.getName(), (int) x+2, (int) y+4, Color.WHITE.getRGB(), 0.9, 0.9);
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
