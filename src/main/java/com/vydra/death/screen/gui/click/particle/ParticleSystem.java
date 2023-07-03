package com.vydra.death.screen.gui.click.particle;

import com.vydra.death.screen.gui.click.GuiMain;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

import javax.vecmath.Vector2f;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

public final class ParticleSystem {

    private final int PARTS = 100;
    private final Particle[] particles = new Particle[PARTS];

    private ScaledResolution scaledResolution;

    public ParticleSystem(ScaledResolution scaledResolution) {
        this.scaledResolution = scaledResolution;
        for (int i = 0; i < PARTS; i++) {
            this.particles[i] = new Particle(new Vector2f((float) (Math.random() * scaledResolution.getScaledWidth()), (float) (Math.random() * scaledResolution.getScaledHeight())));
        }
    }

    public void update() {
        for (int i = 0; i < PARTS; i++) {
            final Particle particle = this.particles[i];
            if (this.scaledResolution != null) {
                final boolean isOffScreenX = particle.getPos().x > this.scaledResolution.getScaledWidth() || particle.getPos().x < 0;
                final boolean isOffScreenY = particle.getPos().y > this.scaledResolution.getScaledHeight() || particle.getPos().y < 0;
                if (isOffScreenX || isOffScreenY) {
                    particle.respawn(this.scaledResolution);
                }
            }
            particle.update();
        }
    }

    public void render(int mouseX, int mouseY) {
        final boolean isInHudEditor = Minecraft.getMinecraft().currentScreen instanceof GuiMain && Minecraft.getMinecraft().player != null;

        for (int i = 0; i < PARTS; i++) {
            final Particle particle = this.particles[i];

            if (isInHudEditor) {
                for (int j = 1; j < PARTS; j++) {
                    if (i != j) {
                        final Particle otherParticle = this.particles[j];
                        final Vector2f diffPos = new Vector2f(particle.getPos());
                        diffPos.sub(otherParticle.getPos());
                        final float diff = diffPos.length();
                        final int distance = 80 / (scaledResolution.getScaleFactor() <= 1 ? 3 : scaledResolution.getScaleFactor());
                        if (diff < distance) {
                            final int lineAlpha = (int) map(diff, distance, 0, 0, 127);
                            if (lineAlpha > 8) {
                                drawLine(particle.getPos().x + particle.getSize() / 2.0f, particle.getPos().y + particle.getSize() / 2.0f, otherParticle.getPos().x + otherParticle.getSize() / 2.0f, otherParticle.getPos().y + otherParticle.getSize() / 2.0f, 2f, changeAlpha(new Color(0x08A9EA).getRGB(), lineAlpha));
                            }
                        }
                    }
                }
            }

            particle.render(mouseX, mouseY);
        }
    }

    private int changeAlpha(int origColor, int userInputedAlpha) {
        origColor = origColor & 0x00FFFFFF;
        return (userInputedAlpha << 24) | origColor;
    }

    private void drawLine(float x, float y, float x1, float y1, float thickness, int hex) {
        float red = (hex >> 16 & 0xFF) / 255.0F;
        float green = (hex >> 8 & 0xFF) / 255.0F;
        float blue = (hex & 0xFF) / 255.0F;
        float alpha = (hex >> 24 & 0xFF) / 255.0F;

        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GlStateManager.shadeModel(GL_SMOOTH);
        glLineWidth(thickness);
        glEnable(GL_LINE_SMOOTH);
        glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(x, y, 0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(x1, y1, 0).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(GL_FLAT);
        glDisable(GL_LINE_SMOOTH);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    // linearly maps value from the range (a..b) to (c..d)
    public static double map(double value, double a, double b, double c, double d) {
        // first map value from (a..b) to (0..1)
        value = (value - a) / (b - a);
        // then map it from (0..1) to (c..d) and return it
        return c + value * (d - c);
    }

    public ScaledResolution getScaledResolution() {
        return scaledResolution;
    }

    public void setScaledResolution(ScaledResolution scaledResolution) {
        this.scaledResolution = scaledResolution;
    }
}