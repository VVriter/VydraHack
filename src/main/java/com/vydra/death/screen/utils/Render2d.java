package com.vydra.death.screen.utils;

import com.vydra.death.screen.gui.click.GuiUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

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

}
