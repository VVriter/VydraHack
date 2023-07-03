package com.vydra.death.screen.utils.render;

import com.vydra.death.screen.gui.click.GuiUtil;
import com.vydra.death.screen.utils.ColorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.awt.geom.Point2D;

import static java.awt.Color.white;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;

public class Render2d {

    public static Tessellator tessellator = Tessellator.getInstance();
    public static BufferBuilder bufferbuilder = tessellator.getBuffer();
    public static Minecraft mc = Minecraft.getMinecraft();


    public static void drawGradientRectVertical(Rectangle rectangle, Color startColor, Color endColor) {
        double zLevel=0.0;

        float f = (float) startColor.getRed() / 255.0f;
        float f1 = (float) startColor.getGreen() / 255.0f;
        float f2 = (float) startColor.getBlue() / 255.0f;
        float f3 = (float) startColor.getAlpha() / 255.0f;
        float f4 = (float) endColor.getRed() / 255.0f;
        float f5 = (float) endColor.getGreen() / 255.0f;
        float f6 = (float) endColor.getBlue() / 255.0f;
        float f7 = (float) endColor.getAlpha() / 255.0f;

        double x = rectangle.x;
        double y = rectangle.y;
        double w = rectangle.width;
        double h = rectangle.height;

        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)x, (double)y + h, (double)zLevel).color(f, f1, f2, f3).endVertex();
        bufferbuilder.pos((double)x + w, (double)y + h, (double)zLevel).color(f, f1, f2, f3).endVertex();
        bufferbuilder.pos((double)x + w, (double)y, (double)zLevel).color(f4, f5, f6, f7).endVertex();
        bufferbuilder.pos((double)x, (double)y, (double)zLevel).color(f4, f5, f6, f7).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public static void drawGradientRectHorizontal(Rectangle rectangle, Color startColor, Color endColor) {
        double zLevel = 0.0;

        float f = (float) startColor.getRed() / 255.0f;
        float f1 = (float) startColor.getGreen() / 255.0f;
        float f2 = (float) startColor.getBlue() / 255.0f;
        float f3 = (float) startColor.getAlpha() / 255.0f;
        float f4 = (float) endColor.getRed() / 255.0f;
        float f5 = (float) endColor.getGreen() / 255.0f;
        float f6 = (float) endColor.getBlue() / 255.0f;
        float f7 = (float) endColor.getAlpha() / 255.0f;

        double x = rectangle.x;
        double y = rectangle.y;
        double w = rectangle.width;
        double h = rectangle.height;

        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double) x, (double) y + h, (double) zLevel).color(f4, f5, f6, f7).endVertex();
        bufferbuilder.pos((double) x + w, (double) y + h, (double) zLevel).color(f, f1, f2, f3).endVertex();
        bufferbuilder.pos((double) x + w, (double) y, (double) zLevel).color(f, f1, f2, f3).endVertex();
        bufferbuilder.pos((double) x, (double) y, (double) zLevel).color(f4, f5, f6, f7).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }



    public static void drawFilledCircle(Point2D.Double point, Color color, int r)
    {
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        bufferbuilder.begin(GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION_COLOR);

        bufferbuilder.pos(point.getX(), point.getY(), 0.0D).color(white.getRed(), white.getGreen(), white.getBlue(), white.getAlpha()).endVertex();

        for (int i = 0; i <= 360; i++)
        {
            double x2 = Math.sin(((i * Math.PI) / 180)) * r;
            double y2 = Math.cos(((i * Math.PI) / 180)) * r;

            bufferbuilder.pos(point.getX() + x2, point.getY() + y2, 0.0D).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        }
        tessellator.draw();
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }



    public static void drawGradientLine(Point2D.Double one, Point2D.Double two, Color startColor, Color endColor, int lineWidth)
    {
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.glLineWidth(lineWidth);
        GlStateManager.shadeModel(7425);
        bufferbuilder.begin(1, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(one.getX(), one.getY(), 0.0D).color(startColor.getRed(), startColor.getGreen(), startColor.getBlue(), startColor.getAlpha()).endVertex();
        bufferbuilder.pos(two.getX(), two.getY(), 0.0D).color(endColor.getRed(), endColor.getGreen(), endColor.getBlue(), endColor.getAlpha()).endVertex();

        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }


    public static void drawSvastica(Point2D.Double point, float width, int color, int size) {
        drawGradientLine(point, new Point2D.Double(point.x, point.y + size), new Color(color), new Color(color), (int) width);
        drawGradientLine(new Point2D.Double(point.x, point.y + size), new Point2D.Double(point.x - size, point.y+size), new Color(color), new Color(color), (int) width);

        drawGradientLine(point, new Point2D.Double(point.x, point.y - size), new Color(color), new Color(color), (int) width);
        drawGradientLine(new Point2D.Double(point.x, point.y - size), new Point2D.Double(point.x + size, point.y-size), new Color(color), new Color(color), (int) width);



        drawGradientLine(point, new Point2D.Double(point.x+size, point.y), new Color(color), new Color(color), (int) width);
        drawGradientLine(new Point2D.Double(point.x+size, point.y), new Point2D.Double(point.x + size, point.y+size), new Color(color), new Color(color), (int) width);

        drawGradientLine(point, new Point2D.Double(point.x-size, point.y), new Color(color), new Color(color), (int) width);
        drawGradientLine(new Point2D.Double(point.x-size, point.y), new Point2D.Double(point.x - size, point.y-size), new Color(color), new Color(color), (int) width);
    }


    public static void drawHueBar(Rectangle rectangle) {
        double x = rectangle.getX();
        double y = rectangle.getY();
        double w = rectangle.getWidth();
        double h = rectangle.getHeight();
        for (int i = 0; i < w; i++) {
            float hue = (float) i / (float) w;
            Color color = Color.getHSBColor(hue, 1.0f, 1.0f);
            drawRectangle(new Rectangle((int) (x + i), (int) y, 1, (int) h), color);
        }
    }




    public static void drawRectangle(Rectangle rectangle, Color color) {
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.glLineWidth(1);

        double x = rectangle.x;
        double y = rectangle.y;
        double w = rectangle.width;
        double h = rectangle.height;

        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(x, y + h, 0.0D).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos(x + w, rectangle.y + h, 0.0D).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos(x + w, rectangle.y, 0.0D).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos(x, rectangle.y, 0.0D).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        tessellator.draw();

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }


    public static void drawVGradientRect(float left, float top, float right, float bottom, int startColor, int endColor) {
        float f = (float) (startColor >> 24 & 255) / 255.0F;
        float f1 = (float) (startColor >> 16 & 255) / 255.0F;
        float f2 = (float) (startColor >> 8 & 255) / 255.0F;
        float f3 = (float) (startColor & 255) / 255.0F;
        float f4 = (float) (endColor >> 24 & 255) / 255.0F;
        float f5 = (float) (endColor >> 16 & 255) / 255.0F;
        float f6 = (float) (endColor >> 8 & 255) / 255.0F;
        float f7 = (float) (endColor & 255) / 255.0F;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(right, top, 0).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos(left, top, 0).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos(left, bottom, 0).color(f5, f6, f7, f4).endVertex();
        bufferbuilder.pos(right, bottom, 0).color(f5, f6, f7, f4).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }




    public static void drawGlow(Point2D.Double one, Point2D.Double two, int color) {
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        drawVGradientRect((int) one.getX(), (int) one.getY(), (int) two.getX(), (int) (one.getY() + (two.getY() - one.getY()) / 2f), ColorUtil.toRGBA(new Color(color).getRed(), new Color(color).getGreen(), new Color(color).getBlue(), 0), color);
        drawVGradientRect((int) one.getX(), (int) (one.getY() + (two.getY() - one.getY()) / 2f), (int) two.getX(), (int) two.getY(), color, ColorUtil.toRGBA(new Color(color).getRed(), new Color(color).getGreen(), new Color(color).getBlue(), 0));
        int radius = (int) ((two.getY() - one.getY()) / 2f);
        drawPolygonPart(new Point2D.Double(one.getX(), (one.getY() + (two.getY() - one.getY()) / 2f)), radius, 0, color, ColorUtil.toRGBA(new Color(color).getRed(), new Color(color).getGreen(), new Color(color).getBlue(), 0));
        drawPolygonPart(new Point2D.Double(one.getX(), (one.getY() + (two.getY() - one.getY()) / 2f)), radius, 1, color, ColorUtil.toRGBA(new Color(color).getRed(), new Color(color).getGreen(), new Color(color).getBlue(), 0));
        drawPolygonPart(new Point2D.Double(two.getX(), (one.getY() + (two.getY() - one.getY()) / 2f)), radius, 2, color, ColorUtil.toRGBA(new Color(color).getRed(), new Color(color).getGreen(), new Color(color).getBlue(), 0));
        drawPolygonPart(new Point2D.Double(two.getX(), (one.getY() + (two.getY() - one.getY()) / 2f)), radius, 3, color, ColorUtil.toRGBA(new Color(color).getRed(), new Color(color).getGreen(), new Color(color).getBlue(), 0));
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public static void drawPolygonPart(Point2D.Double point, int radius, int part, int color, int endcolor) {
        float alpha = (float) (color >> 24 & 255) / 255.0F;
        float red = (float) (color >> 16 & 255) / 255.0F;
        float green = (float) (color >> 8 & 255) / 255.0F;
        float blue = (float) (color & 255) / 255.0F;
        float alpha1 = (float) (endcolor >> 24 & 255) / 255.0F;
        float red1 = (float) (endcolor >> 16 & 255) / 255.0F;
        float green1 = (float) (endcolor >> 8 & 255) / 255.0F;
        float blue1 = (float) (endcolor & 255) / 255.0F;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(point.getX(), point.getY(), 0).color(red, green, blue, alpha).endVertex();
        final double TWICE_PI = Math.PI * 2;
        for (int i = part * 90; i <= part * 90 + 90; i++) {
            double angle = (TWICE_PI * i / 360) + Math.toRadians(180);
            bufferbuilder.pos(point.getX() + Math.sin(angle) * radius, point.getY() + Math.cos(angle) * radius, 0).color(red1, green1, blue1, alpha1).endVertex();
        }
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public static void drawTriangle(Point2D.Double one, Point2D.Double two, Point2D.Double three, Color color) {
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.glLineWidth(1);

        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(one.getX(), one.getY(), 0.0D).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos(two.getX(), two.getY(), 0.0D).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos(three.getX(), three.getY(), 0.0D).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        tessellator.draw();

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }






    public static void drawTracerPointer(float x, float y, float size, float widthDiv, float heightDiv, boolean outline, float outlineWidth, int color) {
        boolean blend = GL11.glIsEnabled(3042);
        float alpha = (float) (color >> 24 & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        hexColor(color);
        GL11.glBegin(7);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x - size / widthDiv, y + size);
        GL11.glVertex2d(x, y + size / heightDiv);
        GL11.glVertex2d(x + size / widthDiv, y + size);
        GL11.glVertex2d(x, y);
        GL11.glEnd();
        if (outline) {
            GL11.glLineWidth(outlineWidth);
            GL11.glColor4f(0.0f, 0.0f, 0.0f, alpha);
            GL11.glBegin(2);
            GL11.glVertex2d(x, y);
            GL11.glVertex2d(x - size / widthDiv, y + size);
            GL11.glVertex2d(x, y + size / heightDiv);
            GL11.glVertex2d(x + size / widthDiv, y + size);
            GL11.glVertex2d(x, y);
            GL11.glEnd();
        }
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        if (!blend) {
            GL11.glDisable(3042);
        }
        GL11.glDisable(2848);
    }

    public static void hexColor(int hexColor) {
        float red = (float) (hexColor >> 16 & 0xFF) / 255.0f;
        float green = (float) (hexColor >> 8 & 0xFF) / 255.0f;
        float blue = (float) (hexColor & 0xFF) / 255.0f;
        float alpha = (float) (hexColor >> 24 & 0xFF) / 255.0f;
        GL11.glColor4f(red, green, blue, alpha);
    }









    private static final Shader ROUNDED = new Shader("shaders/rounded.fsh", true);
    private static final Shader ROUNDED_GRADIENT = new Shader("shaders/rounded_gradient.fsh", true);
    private static final Shader ROUNDED_OUTLINE = new Shader("shaders/rounded_outline.fsh", true);
    private static final Shader SHADOW = new Shader("shaders/shadow.fsh", true);
    private static final Shader CIRCLE = new Shader("shaders/outline_circle.fsh", true);
    private static final Shader ROUNDED_TEXTURE = new Shader("shaders/rounded_texture.fsh", true);



    public static void drawRoundedRect(float x, float y, float x2, float y2, float round, int color) {
        drawRoundedGradientRect(x, y, x2, y2, round, 1, color, color, color, color);
    }

    public static void drawRoundedRect(float x, float y, float x2, float y2, float round, float swapX, float swapY, int firstColor, int secondColor) {
        float[] c = ColorUtility.getRGBAf(firstColor);
        float[] c1 = ColorUtility.getRGBAf(secondColor);

        GlStateManager.color(0, 0, 0, 0);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

        ROUNDED.useProgram();
        ROUNDED.setupUniform2f("size", (x2 - round) * 2, (y2 - round) * 2);
        ROUNDED.setupUniform1f("round", round);
        ROUNDED.setupUniform2f("smoothness", 0.f, 1.5f);
        ROUNDED.setupUniform2f("swap", swapX, swapY);
        ROUNDED.setupUniform4f("firstColor", c[0], c[1], c[2], c[3]);
        ROUNDED.setupUniform4f("secondColor", c1[0], c1[1], c1[2], c[3]);

        allocTextureRectangle(x, y, x2, y2);
        ROUNDED.unloadProgram();

        GlStateManager.disableBlend();
    }

    public static void drawRoundedGradientRect(float x, float y, float x2, float y2, float round, int color1, int color2, int color3, int color4) {
        drawRoundedGradientRect(x, y, x2, y2, round, 1, color1, color2, color3, color4);
    }

    public static void drawRoundedGradientRect(float x, float y, float x2, float y2, float round, float value, int color1, int color2, int color3, int color4) {
        float[] c1 = ColorUtility.getRGBAf(color1);
        float[] c2 = ColorUtility.getRGBAf(color2);
        float[] c3 = ColorUtility.getRGBAf(color3);
        float[] c4 = ColorUtility.getRGBAf(color4);

        GlStateManager.color(0, 0, 0, 0);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        ROUNDED_GRADIENT.useProgram();
        ROUNDED_GRADIENT.setupUniform2f("size", x2 * 2, y2 * 2);
        ROUNDED_GRADIENT.setupUniform4f("round", round, round, round, round);
        ROUNDED_GRADIENT.setupUniform2f("smoothness", 0.f, 1.5f);
        ROUNDED_GRADIENT.setupUniform1f("value", value);
        ROUNDED_GRADIENT.setupUniform4f("color1", c1[0], c1[1], c1[2], c1[3]);
        ROUNDED_GRADIENT.setupUniform4f("color2", c2[0], c2[1], c2[2], c2[3]);
        ROUNDED_GRADIENT.setupUniform4f("color3", c3[0], c3[1], c3[2], c3[3]);
        ROUNDED_GRADIENT.setupUniform4f("color4", c4[0], c4[1], c4[2], c4[3]);

        allocTextureRectangle(x, y, x2, y2);
        ROUNDED_GRADIENT.unloadProgram();
        GlStateManager.disableBlend();
    }

    public static void drawRoundedShadow(float x, float y, float x2, float y2, float softness, float radius, int color) {
        float[] c = ColorUtility.getRGBAf(color);
        GlStateManager.color(0, 0, 0, 0);
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

        SHADOW.useProgram();
        SHADOW.setupUniform2f("size", (x2 - radius) * 2, (y2 - radius) * 2);
        SHADOW.setupUniform1f("softness", softness);
        SHADOW.setupUniform1f("radius", radius);
        SHADOW.setupUniform4f("color", c[0], c[1], c[2], c[3]);

        allocTextureRectangle(x - (softness / 2f), y - (softness / 2f), x2 + (softness), y2 + (softness));
        SHADOW.unloadProgram();

        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
    }

    public static void drawRoundedOutlineRect(float x, float y, float x2, float y2, float round, float thickness, int color) {
        float[] c = ColorUtility.getRGBAf(color);

        GlStateManager.color(0, 0, 0, 0);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        ROUNDED_OUTLINE.useProgram();
        ROUNDED_OUTLINE.setupUniform2f("size", x2 * 2, y2 * 2);
        ROUNDED_OUTLINE.setupUniform1f("round", round);
        ROUNDED_OUTLINE.setupUniform1f("thickness", thickness);
        ROUNDED_OUTLINE.setupUniform2f("smoothness", thickness - 1.5f, thickness);
        ROUNDED_OUTLINE.setupUniform4f("color", c[0], c[1], c[2], c[3]);
        allocTextureRectangle(x, y, x2, y2);
        ROUNDED_OUTLINE.unloadProgram();
        GlStateManager.disableBlend();
    }

    public static void drawARCCircle(float x, float y, float radius, float progress, float borderThickness, int color) {
        drawARCCircle(x, y, radius, progress, 100, borderThickness, color, color);
    }

    public static void drawARCCircle(float x, float y, float radius, float progress, int change, float borderThickness, int color) {
        drawARCCircle(x, y, radius, progress, change, borderThickness, color, color);
    }

    public static void drawARCCircle(float x, float y, float radius, float progress, float borderThickness, int firstColor, int secondColor) {
        drawARCCircle(x, y, radius, progress, 100, borderThickness, firstColor, secondColor);
    }

    public static void drawARCCircle(float x, float y, float radius, float progress, int change, float borderThickness, int firstColor, int secondColor) {
        float[] c = ColorUtility.getRGBAf(firstColor);
        float[] c1 = ColorUtility.getRGBAf(secondColor);
        GlStateManager.color(0, 0, 0, 0);
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        CIRCLE.useProgram();
        CIRCLE.setupUniform2f("pos", x * 2 - radius, ((Minecraft.getMinecraft().displayHeight - (radius * 2)) - (y * 2)) + radius - 1);
        CIRCLE.setupUniform1f("radius", radius);
        CIRCLE.setupUniform1f("radialSmoothness", 1.0f);
        CIRCLE.setupUniform1f("borderThickness", borderThickness);
        CIRCLE.setupUniform1f("progress", progress);
        CIRCLE.setupUniform1i("change", change);
        CIRCLE.setupUniform4f("firstColor", c[0], c[1], c[2], c[3]);
        CIRCLE.setupUniform4f("secondColor", c1[0], c1[1], c1[2], c1[3]);
        CIRCLE.setupUniform2f("gradient", 0.2f, 2f);
        allocTextureRectangle(0, 0, mc.displayWidth, mc.displayHeight);
        CIRCLE.unloadProgram();
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
    }

    public static void drawImage(ResourceLocation tex, float x, float y, float x2, float y2) {
        mc.getTextureManager().bindTexture(tex);
        GlStateManager.color(1, 1, 1, 1);
        allocTextureRectangle(x, y, x2, y2);
        GlStateManager.bindTexture(0);

    }

    public static void drawRoundedTexture(ResourceLocation tex,float x, float y, float x2, float y2, float round, float alpha) {
        GlStateManager.color(0, 0, 0, 0);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.disableAlpha();
        ROUNDED_TEXTURE.useProgram();
        ROUNDED_TEXTURE.setupUniform2f("size", (x2 - round) * 2, (y2 - round) * 2);
        ROUNDED_TEXTURE.setupUniform1f("round", round);
        ROUNDED_TEXTURE.setupUniform1f("alpha", alpha);
        drawImage(tex,x,y,x2,y2);
        ROUNDED_TEXTURE.unloadProgram();

        GlStateManager.disableBlend();
    }

    public static void allocTextureRectangle(float x, float y, float width, float height) {
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(x, y, 0).tex(0, 0).endVertex();
        bufferbuilder.pos(x, y + height, 0).tex(0, 1).endVertex();
        bufferbuilder.pos(x + width, y + height, 0).tex(1, 1).endVertex();
        bufferbuilder.pos(x + width, y, 0).tex(1, 0).endVertex();
        tessellator.draw();
    }

    public static void drawRect(float x, float y, float width, float height, int color) {
        drawGradientRect(x, y, width, height, color, color, color, color);
    }

    public static void drawCRect(float x, float y, float width, float height, int color) {
        drawGradientRect(x, y, width - x, height, color, color, color, color);
    }

    public static void drawGradientRect(float x, float y, float width, float height, int color1, int color2, int color3, int color4) {
        float[] c1 = ColorUtility.getRGBAf(color1);
        float[] c2 = ColorUtility.getRGBAf(color2);
        float[] c3 = ColorUtility.getRGBAf(color3);
        float[] c4 = ColorUtility.getRGBAf(color4);

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(x, height + y, 0.0D).color(c1[0], c1[1], c1[2], c1[3]).endVertex();
        bufferbuilder.pos(width + x, height + y, 0.0D).color(c2[0], c2[1], c2[2], c2[3]).endVertex();
        bufferbuilder.pos(width + x, y, 0.0D).color(c3[0], c3[1], c3[2], c3[3]).endVertex();
        bufferbuilder.pos(x, y, 0.0D).color(c4[0], c4[1], c4[2], c4[3]).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(GL11.GL_FLAT);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawTriangle(float x, float z, float distance, int color) {
        Minecraft mc = Minecraft.getMinecraft();
        float pt = mc.getRenderPartialTicks();
        float playerX = (float) (mc.player.prevPosX + (mc.player.posX - mc.player.prevPosX) * pt);
        float playerZ = (float) (mc.player.prevPosZ + (mc.player.posZ - mc.player.prevPosZ) * pt);
        float playerYaw = mc.player.prevRotationYaw + (mc.player.rotationYaw - mc.player.prevRotationYaw) * pt;
        float radian = (float) (Math.atan2(z - playerZ, x - playerX) - Math.toRadians(playerYaw + 180));
        float degree = (float) Math.toDegrees(radian);
        float cos = MathHelper.cos(radian);
        float sin = MathHelper.sin(radian);
        float centerX = mc.displayWidth / 4f;
        float centerY = mc.displayHeight / 4f;
        GlStateManager.enableBlend();
        GlStateManager.pushMatrix();
        GlStateManager.translate(centerX + distance * cos, centerY + distance * sin, 0);
        GlStateManager.rotate(degree + 90, 0, 0, 1);
        float[] colors = ColorUtility.getRGBAf(color);
        float width = 6, height = 12;
        GlStateManager.disableTexture2D();
        GL11.glEnable(GL11.GL_POLYGON_SMOOTH);
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        bufferbuilder.begin(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(0, 0 - height, 0).color(colors[0], colors[1], colors[2], colors[3]).endVertex();
        bufferbuilder.pos(0 - width, 0, 0).color(colors[0], colors[1], colors[2], colors[3]).endVertex();
        bufferbuilder.pos(0, -3, 0).color(colors[0], colors[1], colors[2], colors[3]).endVertex();
        float r = Math.max(colors[0] - 0.1f, 0);
        float g = Math.max(colors[1] - 0.1f, 0);
        float b = Math.max(colors[2] - 0.1f, 0);
        bufferbuilder.pos(0, 0 - height, 0).color(r, g, b, colors[3]).endVertex();
        bufferbuilder.pos(0, -3, 0).color(r, g, b, colors[3]).endVertex();
        bufferbuilder.pos(0 + width, 0, 0).color(r, g, b, colors[3]).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(GL11.GL_FLAT);
        GL11.glDisable(GL11.GL_POLYGON_SMOOTH);
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
        GlStateManager.disableBlend();
    }

}
