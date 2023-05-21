package com.vydra.death.screen.gui;

import com.vydra.death.screen.modules.Category;
import com.vydra.death.screen.util.Render2d;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class VydraGui extends GuiScreen {

    int xOffset = 3;
    List<Frame> frames;

    @Override
    public void initGui() {
        super.initGui();
        frames = new ArrayList<>();
        Stream.of(Category.values()).forEach(e -> {
            Frame frame = new Frame(e, xOffset);
            frames.add(frame);
            xOffset += 105;
        });
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        ScaledResolution sr = new ScaledResolution(mc);
        Render2d.drawSvastica(new Point2D.Double(sr.getScaledWidth()/2, sr.getScaledHeight()/2), 10, Color.RED.getRGB(), 100);

        frames.forEach(frame -> frame.draw(mouseX, mouseY));
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        frames.forEach(frame -> frame.handleClick(mouseX, mouseY, mouseButton));
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
