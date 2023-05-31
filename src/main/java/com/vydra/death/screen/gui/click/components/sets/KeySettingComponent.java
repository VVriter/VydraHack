package com.vydra.death.screen.gui.click.components.sets;

import com.vydra.death.screen.gui.click.GuiUtil;
import com.vydra.death.screen.gui.click.IGuiComponent;
import com.vydra.death.screen.modules.impl.client.Gui;
import com.vydra.death.screen.modules.settings.Setting;
import com.vydra.death.screen.modules.settings.types.BooleanSetting;
import com.vydra.death.screen.modules.settings.types.KeyBindSetting;
import com.vydra.death.screen.utils.Render2d;
import com.vydra.death.screen.utils.animations.EaseLeft;
import com.vydra.death.screen.utils.animations.EaseRight;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.Objects;

import static com.vydra.death.screen.gui.click.GuiUtil.drawStringCustom;

public class KeySettingComponent implements IGuiComponent {


    private int x;
    private int y;
    private Setting setting;

    private EaseRight easeRight;
    private EaseLeft easeLeft;

    public KeySettingComponent(Setting setting, int x, int y) {
        this.setting = setting;
        this.x = x;
        this.y = y;

        easeRight = new EaseRight();
        easeLeft = new EaseLeft();
    }







    @Override
    public void onClick(int x, int y, int state) {

        GuiUtil.isListeningKey = true;
        GuiUtil.keyListenedSetting = setting;

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
                Gui.getInstance().baseButtonOutlineColorFirst.getValue(),
                Gui.getInstance().baseButtonOutlineColorSecond.getValue()
        );

        Render2d.drawGradientRectHorizontal(
                new Rectangle((int) x, (int) y, getWidth(), getHeight()),
                Gui.getInstance().baseButtonColorFirst.getValue(),
                Gui.getInstance().baseButtonColorSecond.getValue()
        );




        if (!isHovering && hoverAnimationx != 0) {
            hoverAnimationx = easeLeft.getValue(hoverAnimationx, y, 5);
        }

        if (isHovering && hoverAnimationx != getWidth()) {
            hoverAnimationx = easeRight.getValue(hoverAnimationx, y, 5);
        }

        Render2d.drawGradientRectHorizontal(
                new Rectangle((int) x, (int) y, hoverAnimationx, getHeight()),
                Gui.getInstance().baseButtonColorFirst.getValue(),
                Gui.getInstance().baseButtonColorSecond.getValue()
        );





        if (GuiUtil.isListeningKey && GuiUtil.keyListenedSetting.equals(setting)) {
            Render2d.drawGradientRectHorizontal(
                    new Rectangle((int) x, (int) y, hoverAnimationx, getHeight()),
                    Gui.getInstance().baseButtonOutlineColorFirst.getValue(),
                    Gui.getInstance().baseButtonOutlineColorSecond.getValue()
            );
            Render2d.drawGradientRectHorizontal(
                    new Rectangle((int) x, (int) y, getWidth(), getHeight()),
                    new Color(0xBA4F0B9B, true),
                    new Color(0xC93911A1, true)
            );
            drawStringCustom("Listening...", (int) x+2, (int) y+4, Color.WHITE.getRGB(), 0.9, 0.9);
        } else {
            Render2d.drawGradientRectHorizontal(
                    new Rectangle((int) x, (int) y, hoverAnimationx, getHeight()),
                    new Color(0x8C1A0750, true),
                    new Color(0x8C1A0750, true)
            );
            Render2d.drawGradientRectHorizontal(
                    new Rectangle((int) x, (int) y, getWidth(), getHeight()),
                    Gui.getInstance().baseButtonOutlineColorFirst.getValue(),
                    Gui.getInstance().baseButtonOutlineColorSecond.getValue()
            );
            drawStringCustom(setting.getName(), (int) x+2, (int) y+4, Color.WHITE.getRGB(), 0.9, 0.9);

           String keyname = Keyboard.getKeyName( ((KeyBindSetting)setting).getValue() );

            if (Objects.equals(keyname, "NONE")) {
                drawStringCustom(Keyboard.getKeyName(((KeyBindSetting)setting).getValue()),(int) x+getWidth()-24, (int) y+4, Color.WHITE.getRGB(), 0.9, 0.9);
            } else {
                drawStringCustom(Keyboard.getKeyName(((KeyBindSetting)setting).getValue()),(int) x+getWidth()-6, (int) y+4, Color.WHITE.getRGB(), 0.9, 0.9);
            }

        }

        isHovering = false;

    }


    @Override
    public void onKeyTyped(int keycode) {
        IGuiComponent.super.onKeyTyped(keycode);
        if (GuiUtil.isListeningKey && GuiUtil.keyListenedSetting.equals(setting)) {
            if (keycode == Keyboard.KEY_DELETE) {
                ((KeyBindSetting)setting).setValue(Keyboard.KEY_NONE);
                GuiUtil.isListeningKey = false;
                GuiUtil.keyListenedSetting = null;
                return;
            }
            ((KeyBindSetting)setting).setValue(keycode);
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
