package com.vydra.death.screen.gui.components;

import com.vydra.death.screen.gui.Component;
import com.vydra.death.screen.modules.settings.Setting;
import com.vydra.death.screen.util.Render2d;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;

import static com.vydra.death.screen.util.Render2d.mc;

public class BooleanComponent extends Component {

    private Setting<Boolean> setting;
    public BooleanComponent(Setting<Boolean> setting, int x, int y) {
        super(x, y, 100, 13);
        this.setting = setting;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        super.draw(mouseX, mouseY);
        Render2d.drawGradientRectHorizontal(
                new Rectangle((int) x-1, (int) y-1, getWidth()+2, getHeight()+2),
                new Color(0xC9B9166E, true),
                new Color(0xC95D439C, true)
        );


        if (setting.getValue()) {
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


        drawStringCustom(setting.name, (int) x+2, (int) y+4, Color.WHITE.getRGB(), 0.9, 0.9);

    }

    @Override
    public void onClick(int mouseX, int mouseY, int state) {
        super.onClick(mouseX, mouseY, state);
        setting.setValue(!setting.getValue());
    }



    public void drawStringCustom(String text, int x, int y, int color, double scaleX, double scaleY) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(scaleX, scaleY, 1.0);
        mc.fontRenderer.drawString(text, (int) (x / scaleX), (int) (y / scaleY), color);
        GlStateManager.popMatrix();
    }
}
