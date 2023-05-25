package com.vydra.death.screen.gui.click.components.sets;

import com.vydra.death.screen.gui.click.GuiUtil;
import com.vydra.death.screen.gui.click.IGuiComponent;
import com.vydra.death.screen.modules.settings.Setting;
import com.vydra.death.screen.util.Render2d;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.Objects;

import static com.vydra.death.screen.gui.click.GuiUtil.drawStringCustom;

public class KeySettingComponent implements IGuiComponent {


    private int x;
    private int y;
    private Setting setting;

    public KeySettingComponent(Setting setting, int x, int y) {
        this.setting = setting;
        this.x = x;
        this.y = y;
    }







    @Override
    public void onClick(int x, int y, int state) {

        GuiUtil.isListeningKey = true;
        GuiUtil.keyListenedSetting = setting;

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

        Render2d.drawGradientRectHorizontal(
                new Rectangle((int) x, (int) y, getWidth(), getHeight()),
                new Color(0xBA4F0B9B, true),
                new Color(0xC93911A1, true)
        );

        if (GuiUtil.isListeningKey && GuiUtil.keyListenedSetting.equals(setting)) {
            Render2d.drawGradientRectHorizontal(
                    new Rectangle((int) x, (int) y, getWidth(), getHeight()),
                    new Color(0xBA4F0B9B, true),
                    new Color(0xC93911A1, true)
            );
            drawStringCustom("Listening...", (int) x+2, (int) y+4, Color.WHITE.getRGB(), 0.9, 0.9);
        } else {
            Render2d.drawGradientRectHorizontal(
                    new Rectangle((int) x, (int) y, getWidth(), getHeight()),
                    new Color(0xC95D439C, true),
                    new Color(0xC9B9166E, true)
            );
            drawStringCustom(setting.name, (int) x+2, (int) y+4, Color.WHITE.getRGB(), 0.9, 0.9);


            String keyname = Keyboard.getKeyName((Integer) setting.getValue());

            if (Objects.equals(keyname, "NONE")) {
                drawStringCustom(Keyboard.getKeyName((Integer) setting.getValue()),(int) x+getWidth()-24, (int) y+4, Color.WHITE.getRGB(), 0.9, 0.9);
            } else {
                drawStringCustom(Keyboard.getKeyName((Integer) setting.getValue()),(int) x+getWidth()-6, (int) y+4, Color.WHITE.getRGB(), 0.9, 0.9);
            }

        }



    }


    @Override
    public void onKeyTyped(int keycode) {
        IGuiComponent.super.onKeyTyped(keycode);
        if (GuiUtil.isListeningKey && GuiUtil.keyListenedSetting.equals(setting)) {
            if (keycode == Keyboard.KEY_DELETE) {
                setting.setValue(Keyboard.KEY_NONE);
                GuiUtil.isListeningKey = false;
                GuiUtil.keyListenedSetting = null;
                return;
            }
            setting.setValue(keycode);
            GuiUtil.isListeningKey = false;
            GuiUtil.keyListenedSetting = null;
        }
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

    @Override
    public void setY(int y) {
        IGuiComponent.super.setY(y);
        this.y = y;
    }
}
