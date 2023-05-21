package com.vydra.death.screen.gui.components;

import com.vydra.death.screen.gui.Component;
import com.vydra.death.screen.modules.Category;
import com.vydra.death.screen.util.Render2d;

import java.awt.*;

import static com.vydra.death.screen.util.Render2d.mc;

public class CategoryComponent extends Component {

    private Category category;
    public CategoryComponent(Category category, int x) {
        super(x, 5, 100, 22);
        this.category = category;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        super.draw(mouseX, mouseY);
        Render2d.drawGradientRectHorizontal(
                new Rectangle((int) x-1, (int) y-1, getWidth()+2, getHeight()-3),
                new Color(0xC9B9166E, true),
                new Color(0xC95D439C, true)
        );
        Render2d.drawGradientRectHorizontal(
                new Rectangle((int) x, (int) y, getWidth(), getHeight()-5),
                new Color(0xC95D439C, true),
                new Color(0xC9B9166E, true)
        );
        mc.getRenderItem().renderItemAndEffectIntoGUI(category.getItemStack(), (int) x, (int) y);
        mc.fontRenderer.drawString(category.name(), (int) x+20, (int) y+5, Color.white.getRGB());
    }

}
