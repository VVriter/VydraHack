package com.vydra.death.screen.gui.components;

import com.vydra.death.screen.gui.Component;
import com.vydra.death.screen.gui.VydraGui;
import com.vydra.death.screen.modules.settings.Setting;
import com.vydra.death.screen.util.Render2d;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;

import static com.vydra.death.screen.util.Render2d.mc;
import static java.lang.Math.round;

public class SliderComponent extends Component {

    public Setting setting;
    int offset;

    public double difference;

    public SliderComponent(Setting setting, int x, int y) {
        super(x, y, 100, 13);
        this.setting = setting;
        this.offset = (int) (getWidth()/setting.max);
        this.difference = setting.max - setting.min;
    }


    @Override
    public void draw(int mouseX, int mouseY) {
        super.draw(mouseX, mouseY);
        Render2d.drawGradientRectVertical(
                new Rectangle((int) x-1, (int) y-1, getWidth()+2, getHeight()+2),
                new Color(0xC9B9166E, true),
                new Color(0xC95D439C, true)
        );


        Render2d.drawGradientRectVertical(
                new Rectangle((int) x, (int) y, (int) (offset*(double)setting.value), getHeight()),
                new Color(0xC9439C92, true),
                new Color(0xC90B34BE, true)
        );


        //Rendering module name
        drawStringCustom(setting.name, (int) x+2, (int) y+4, Color.WHITE.getRGB(), 0.9, 0.9);
        drawStringCustom(String.valueOf(setting.getValue()),(int) x+getWidth()-11, (int) y+4, Color.WHITE.getRGB(), 0.9, 0.9);
    }

    @Override
    public void onClick(int mouseX, int mouseY, int state) {
        super.onClick(mouseX, mouseY, state);
        if (VydraGui.isDragging) {
            setSettingFromX(mouseX);
        }
    }


    public void setSettingFromX(int mouseX) {
        if(setting.getValue() instanceof Double) {
            double percent = ((double) mouseX - this.x) / ((double) getWidth());
            double result = this.setting.min + ((float) this.difference * percent);
            this.setting.setValue((double) round(10.0 * result) / 10.0);
        }else if(setting.getValue() instanceof Integer){
            double percent = ((double) mouseX - this.x) / ((double) getWidth());
            int result = (int)this.setting.min + (int) round((float) this.difference * percent);
            this.setting.setValue(result);
        }
    }

    public void drawStringCustom(String text, int x, int y, int color, double scaleX, double scaleY) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(scaleX, scaleY, 1.0);
        mc.fontRenderer.drawString(text, (int) (x / scaleX), (int) (y / scaleY), color);
        GlStateManager.popMatrix();
    }
}
