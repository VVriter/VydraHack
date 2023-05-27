package com.vydra.death.screen.gui.click.components;

import com.vydra.death.screen.Main;
import com.vydra.death.screen.gui.click.GuiUtil;
import com.vydra.death.screen.gui.click.IGuiComponent;
import com.vydra.death.screen.modules.Category;
import com.vydra.death.screen.modules.Module;
import com.vydra.death.screen.util.Render2d;
import com.vydra.death.screen.util.animations.EaseIn;
import com.vydra.death.screen.util.animations.EaseOut;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.vydra.death.screen.util.Render2d.mc;

public class CategoryComponent implements IGuiComponent {


    private int x;
    private int y;
    private EaseIn easeIn;
    private EaseOut easeOut;
    private boolean isOpened;
    private List<Module> modules;
    private int offset;
    private Category category;

    List<IGuiComponent> components;

    public CategoryComponent(Category category, int x, int y) {
        this.x = x;
        this.y = y;
        this.category = category;
        easeIn = new EaseIn();
        easeOut = new EaseOut();
        isOpened = true;
        components = new ArrayList<>();
        Stream.of(Main.moduleManager.getModules()).filter(e-> e.getCategory().equals(category)).forEach(module -> {
            ModuleComponent component = new ModuleComponent(module, x, y);
            components.add(component);
        });
    }


    @Override
    public void draw() {
        offset = y;


        //Drawin category shit
        Render2d.drawGradientRectHorizontal(
                new Rectangle((int) x-1, (int) y-1, getWidth()+2, getHeight()+2),
                new Color(0xC9B9166E, true),
                new Color(0xC95D439C, true)
        );
        Render2d.drawGradientRectHorizontal(
                new Rectangle((int) x, (int) y, getWidth(), getHeight()),
                new Color(0xC95D439C, true),
                new Color(0xC9B9166E, true)
        );
        mc.getRenderItem().renderItemAndEffectIntoGUI(category.getItemStack(), (int) x, (int) y);
        mc.fontRenderer.drawString(category.name(), (int) x+20, (int) y+5, Color.white.getRGB());

        offset += getHeight();

        if (isOpened) {
            //Drawin modules
            components.stream().filter(component -> component instanceof ModuleComponent).forEach(component -> {
                ((ModuleComponent) component).setY(offset);
                component.draw();
                offset += component.getHeight();
            });
        }

    }

    @Override
    public void onClick(int x, int y, int state) {


        components.stream().filter(component -> GuiUtil.isHoveringOnTheComponent(component, x, y)).forEach(component -> component.onClick(x, y, state));


        if (GuiUtil.isHoveringOnTheComponent(this, x, y)) {
            switch (state) {

                case 1: {
                    isOpened = !isOpened;
                    break;
                }

            }
        }

    }

    @Override
    public void onHover(int x, int y) {
        components.stream().filter(component -> GuiUtil.isHoveringOnTheComponent(component, x, y)).forEach(component -> component.onHover(x, y));

        if (!GuiUtil.isHoveringOnTheComponent(this, x, y)) return;
        
    }


    @Override
    public void onKeyTyped(int keycode) {
        IGuiComponent.super.onKeyTyped(keycode);
        components.stream().forEach(component -> component.onKeyTyped(keycode));
    }

    public void setX(int x) {
        this.x = x;
    }








    @Override
    public int getWidth() {
        return 100;
    }

    @Override
    public int getHeight() {
        return 17;
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
