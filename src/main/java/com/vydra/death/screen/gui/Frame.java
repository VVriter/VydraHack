package com.vydra.death.screen.gui;

import com.vydra.death.screen.Main;
import com.vydra.death.screen.gui.components.CategoryComponent;
import com.vydra.death.screen.gui.components.ModuleComponent;
import com.vydra.death.screen.modules.Category;
import com.vydra.death.screen.modules.Module;

import java.util.ArrayList;
import java.util.List;

public class Frame {
    private Category category;
    private int x;
    int offset = 0;

    List<Component> components = new ArrayList<>();
    public Frame(Category category, int x) {
        this.category = category;
        this.x = x;



        CategoryComponent categoryComponent = new CategoryComponent(category, x);
        components.add(categoryComponent);
        offset += categoryComponent.getHeight();

        for (Module module : Main.moduleManager.getModules()) {
            if (module.getCategory().equals(category)) {
                ModuleComponent moduleComponent = new ModuleComponent(module, x, offset);
                components.add(moduleComponent);
                offset += moduleComponent.getHeight();
            }
        }

    }

    public void draw(int mouseX, int mouseY) {

        offset = 22;

        components.forEach(component ->  {

            if (component instanceof ModuleComponent) {
                component.setY(offset);
                offset += component.getHeight();
            }

            component.draw(mouseX, mouseY);
        });
    }

    public void handleClick(int mouseX, int mouseY, int state) {
        for (Component component : components) {
            if (component instanceof ModuleComponent)
                ((ModuleComponent)component).handleSettingClick(mouseX, mouseY, state);
            if (isHoveringOnTheComponent(component, mouseX, mouseY))
                component.onClick(mouseX, mouseY, state);
        }
    }


    public static boolean isHoveringOnTheComponent(Component component, int mouseX, int mouseY) {
        return mouseX > component.x && mouseX < component.x + component.getWidth() && mouseY > component.y && mouseY < component.y + component.getHeight();
    }

}
