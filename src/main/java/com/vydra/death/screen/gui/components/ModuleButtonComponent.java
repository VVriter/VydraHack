package com.vydra.death.screen.gui.components;

import com.vydra.death.screen.gui.Component;
import com.vydra.death.screen.modules.Module;
import com.vydra.death.screen.util.Render2d;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;

import static com.vydra.death.screen.util.Render2d.mc;

public class ModuleButtonComponent extends Component {

    private Module module;
    public ModuleButtonComponent(Module module, int x, int y) {
        super(x, y, 100, 13);
        this.module = module;
    }


    @Override
    public void draw(int mouseX, int mouseY) {
        super.draw(mouseX, mouseY);
        Render2d.drawGradientRectVertical(
                new Rectangle((int) x-1, (int) y-1, getWidth()+2, getHeight()+2),
                new Color(0xC9B9166E, true),
                new Color(0xC95D439C, true)
        );


        if (module.isEnabled) {
            Render2d.drawGradientRectHorizontal(
                    new Rectangle((int) x, (int) y, getWidth(), getHeight()),
                    new Color(0xC9439C92, true),
                    new Color(0xC90B34BE, true)
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


    public void drawStringCustom(String text, int x, int y, int color, double scaleX, double scaleY) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(scaleX, scaleY, 1.0);
        mc.fontRenderer.drawString(text, (int) (x / scaleX), (int) (y / scaleY), color);
        GlStateManager.popMatrix();
    }
}
