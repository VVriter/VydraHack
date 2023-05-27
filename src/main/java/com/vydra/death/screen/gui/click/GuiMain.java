package com.vydra.death.screen.gui.click;

import com.vydra.death.screen.gui.click.components.CategoryComponent;
import com.vydra.death.screen.modules.Category;
import com.vydra.death.screen.util.Render2d;
import net.minecraft.client.gui.GuiScreen;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class GuiMain extends GuiScreen {

    public static boolean isDragging;

    private List<CategoryComponent> frames;
    private int xOffset;
    private int yOffset;

    @Override
    public void initGui() {
        super.initGui();
        xOffset = 3;
        yOffset = 3;
        frames = new ArrayList<>();
        isDragging = false;

        Stream.of(Category.values()).forEach(category -> {
            CategoryComponent component = new CategoryComponent(category, xOffset, yOffset);
            frames.add(component);
            xOffset += component.getWidth() + 3;
        });
    }


    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        frames.forEach(frame -> {
            frame.draw();
            frame.onHover(mouseX, mouseY);
        });


        if(isDragging) {
            try {
                mouseClicked(mouseX, mouseY, 0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        frames.forEach(frame -> frame.onClick(mouseX, mouseY, mouseButton));
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        isDragging = false;
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        frames.forEach(frame -> frame.onKeyTyped(keyCode));
    }

    @Override
    public boolean doesGuiPauseGame() { return false; }
}
