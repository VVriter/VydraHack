package com.vydra.death.screen.gui.click;

import com.vydra.death.screen.modules.settings.Setting;
import net.minecraft.client.renderer.GlStateManager;

import static com.vydra.death.screen.utils.Render2d.mc;

public class GuiUtil {


    public static boolean isListeningKey = false;
    public static Setting keyListenedSetting = null;


    public static boolean isHoveringOnTheComponent(IGuiComponent component, int mouseX, int mouseY) {
        return mouseX > component.getX() && mouseX < component.getX() + component.getWidth() && mouseY > component.getY() && mouseY < component.getY() + component.getHeight();
    }

    public static void drawStringCustom(String text, int x, int y, int color, double scaleX, double scaleY) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(scaleX, scaleY, 1.0);
        mc.fontRenderer.drawString(text, (int) (x / scaleX), (int) (y / scaleY), color);
        GlStateManager.popMatrix();
    }
}
