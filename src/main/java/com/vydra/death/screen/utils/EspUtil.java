package com.vydra.death.screen.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class EspUtil {
    public static void renderBoxAroundEntity(Entity entity, float partialTicks) {
        Minecraft mc = Minecraft.getMinecraft();

        double renderPosX = mc.getRenderManager().viewerPosX;
        double renderPosY = mc.getRenderManager().viewerPosY;
        double renderPosZ = mc.getRenderManager().viewerPosZ;

        double entityX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks - renderPosX;
        double entityY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks - renderPosY;
        double entityZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks - renderPosZ;

        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();

        // Set the color of the box outline (red in this case)
        GlStateManager.color(1.0f, 0.0f, 0.0f, 1.0f);

        GlStateManager.translate(entityX, entityY, entityZ);
        GL11.glLineWidth(2.0f);
        GL11.glBegin(GL11.GL_LINE_LOOP);

        // Draw the box outline
        GL11.glVertex3d(-0.5, 0, -0.5);
        GL11.glVertex3d(-0.5, entity.height, -0.5);
        GL11.glVertex3d(0.5, entity.height, -0.5);
        GL11.glVertex3d(0.5, 0, -0.5);
        GL11.glVertex3d(-0.5, 0, -0.5);
        GL11.glVertex3d(-0.5, 0, 0.5);
        GL11.glVertex3d(-0.5, entity.height, 0.5);
        GL11.glVertex3d(0.5, entity.height, 0.5);
        GL11.glVertex3d(0.5, 0, 0.5);
        GL11.glVertex3d(-0.5, 0, 0.5);

        GL11.glVertex3d(-0.5, 0, -0.5);
        GL11.glVertex3d(-0.5, 0, 0.5);
        GL11.glVertex3d(-0.5, entity.height, 0.5);
        GL11.glVertex3d(-0.5, entity.height, -0.5);

        GL11.glVertex3d(0.5, 0, -0.5);
        GL11.glVertex3d(0.5, 0, 0.5);
        GL11.glVertex3d(0.5, entity.height, 0.5);
        GL11.glVertex3d(0.5, entity.height, -0.5);

        GL11.glEnd();

        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }



    public static void changeEntityTextureAlpha(Entity entity, float alpha, float partialTicks) {
        Render<Entity> renderer = Minecraft.getMinecraft().getRenderManager().getEntityRenderObject(entity);
        Minecraft mc = Minecraft.getMinecraft();
        double renderPosX = mc.getRenderManager().viewerPosX;
        double renderPosY = mc.getRenderManager().viewerPosY;
        double renderPosZ = mc.getRenderManager().viewerPosZ;

        double entityX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks - renderPosX;
        double entityY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks - renderPosY;
        double entityZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks - renderPosZ;

        if (renderer != null) {
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);

            GlStateManager.translate(entityX, entityY, entityZ);

            renderer.doRender(entity, 0.0, 0.0, 0.0, 0.0f, 0.0f);

            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
    }

}
