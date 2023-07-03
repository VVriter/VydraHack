package com.vydra.death.screen.modules.impl.render;

import com.google.common.collect.Maps;
import com.vydra.death.screen.modules.Category;
import com.vydra.death.screen.modules.Module;
import com.vydra.death.screen.modules.settings.types.BooleanSetting;
import com.vydra.death.screen.modules.settings.types.ColorSetting;
import com.vydra.death.screen.modules.settings.types.SliderSetting;
import com.vydra.death.screen.utils.EntityUtil;
import com.vydra.death.screen.utils.render.Render2d;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import java.awt.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Map;

public class PyroArrow extends Module {

    BooleanSetting invisibles = new BooleanSetting.Builder()
            .withModule(this)
            .withName("Invisibles")
            .withDefaultValue(true)
            .build();

    ColorSetting colorSetting = new ColorSetting.Builder()
            .withModule(this)
            .withName("Color")
            .withDefaultValue(new Color(0x980598))
            .build();

    SliderSetting radius = new SliderSetting.Builder()
            .withModule(this)
            .withName("Radius")
            .min(1)
            .max(100)
            .withDefaultValue(20)
            .build();

    SliderSetting size = new SliderSetting.Builder()
            .withModule(this)
            .withName("Size")
            .min(1)
            .max(10)
            .withDefaultValue(8.1)
            .build();

    BooleanSetting outline = new BooleanSetting.Builder()
            .withModule(this)
            .withName("Outline")
            .withDefaultValue(true)
            .build();

    SliderSetting outlineWidth = new SliderSetting.Builder()
            .withModule(this)
            .withName("OutlineWidth")
            .min(1)
            .max(10)
            .withDefaultValue(1.1)
            .build();

    SliderSetting fadeDistance = new SliderSetting.Builder()
            .withModule(this)
            .withName("FadeDistance")
            .min(1)
            .max(10)
            .withDefaultValue(3.1)
            .build();

    public PyroArrow() {
        super("PyroArrows", Category.RENDER);
    }

    private final EntityListener entityListener = new EntityListener();

    @SubscribeEvent
    public void onRender2d(RenderGameOverlayEvent.Text event) {
        this.entityListener.render();
        mc.world.loadedEntityList.forEach(o -> {
            if (o instanceof EntityPlayer && this.isValid((EntityPlayer) o)) {
                EntityPlayer entity = (EntityPlayer) o;
                Vec3d pos = this.entityListener.getEntityLowerBounds().get(entity);
                if (pos != null && !this.isOnScreen(pos)) {
                    Color color = EntityUtil.getColor(entity, colorSetting.getValue().getRed(), colorSetting.getValue().getBlue(), (int) MathHelper.clamp(255.0f - 255.0f / (float) this.fadeDistance.getValue() * mc.player.getDistance(entity), 100.0f, 255.0f), colorSetting.getValue().getAlpha(), true);
                    int x = Display.getWidth() / 2 / (mc.gameSettings.guiScale == 0 ? 1 : mc.gameSettings.guiScale);
                    int y = Display.getHeight() / 2 / (mc.gameSettings.guiScale == 0 ? 1 : mc.gameSettings.guiScale);
                    float yaw = this.getRotations(entity) - mc.player.rotationYaw;
                    GL11.glTranslatef((float) x, (float) y, 0.0f);
                    GL11.glRotatef(yaw, 0.0f, 0.0f, 1.0f);
                    GL11.glTranslatef((float) (-x), (float) (-y), 0.0f);
                    Render2d.drawTracerPointer(x, y - (float) this.radius.getValue(), (float) this.size.getValue(), 2.0f, 1.0f, this.outline.isValue(), (float) outlineWidth.getValue(), color.getRGB());
                    GL11.glTranslatef((float) x, (float) y, 0.0f);
                    GL11.glRotatef(-yaw, 0.0f, 0.0f, 1.0f);
                    GL11.glTranslatef((float) (-x), (float) (-y), 0.0f);
                }
            }
        });
    }


    private boolean isOnScreen(Vec3d pos) {
        if (!(pos.x > -1.0)) return false;
        if (!(pos.y < 1.0)) return false;
        if (!(pos.x > -1.0)) return false;
        if (!(pos.z < 1.0)) return false;
        int n = mc.gameSettings.guiScale == 0 ? 1 : mc.gameSettings.guiScale;
        if (!(pos.x / (double) n >= 0.0)) return false;
        int n2 = mc.gameSettings.guiScale == 0 ? 1 : mc.gameSettings.guiScale;
        if (!(pos.x / (double) n2 <= (double) Display.getWidth())) return false;
        int n3 = mc.gameSettings.guiScale == 0 ? 1 : mc.gameSettings.guiScale;
        if (!(pos.y / (double) n3 >= 0.0)) return false;
        int n4 = mc.gameSettings.guiScale == 0 ? 1 : mc.gameSettings.guiScale;
        return pos.y / (double) n4 <= (double) Display.getHeight();
    }

    private boolean isValid(EntityPlayer entity) {
        return entity != mc.player && (!entity.isInvisible() || this.invisibles.isValue() != false) && entity.isEntityAlive();
    }

    private float getRotations(EntityLivingBase ent) {
        double x = ent.posX - mc.player.posX;
        double z = ent.posZ - mc.player.posZ;
        return (float) (-(Math.atan2(x, z) * 57.29577951308232));
    }


    private static final Frustum frustrum = new Frustum();
    private static final FloatBuffer screenCoords = BufferUtils.createFloatBuffer(3);
    private static final IntBuffer viewport = BufferUtils.createIntBuffer(16);
    private static final FloatBuffer modelView = BufferUtils.createFloatBuffer(16);
    private static final FloatBuffer projection = BufferUtils.createFloatBuffer(16);

    public static Vec3d to2D(double x, double y, double z) {
        GL11.glGetFloat(2982, modelView);
        GL11.glGetFloat(2983, projection);
        GL11.glGetInteger(2978, viewport);
        boolean result = GLU.gluProject((float) x, (float) y, (float) z, modelView, projection, viewport, screenCoords);
        if (result) {
            return new Vec3d(screenCoords.get(0), (float) Display.getHeight() - screenCoords.get(1), screenCoords.get(2));
        }
        return null;
    }


    private static class EntityListener {
        private final Map<Entity, Vec3d> entityUpperBounds = Maps.newHashMap();
        private final Map<Entity, Vec3d> entityLowerBounds = Maps.newHashMap();

        private EntityListener() {
        }

        private void render() {
            if (!this.entityUpperBounds.isEmpty()) {
                this.entityUpperBounds.clear();
            }
            if (!this.entityLowerBounds.isEmpty()) {
                this.entityLowerBounds.clear();
            }
            for (Entity e : mc.world.loadedEntityList) {
                Vec3d bound = this.getEntityRenderPosition(e);
                bound.add(new Vec3d(0.0, (double) e.height + 0.2, 0.0));
                Vec3d upperBounds = to2D(bound.x, bound.y, bound.z);
                Vec3d lowerBounds =to2D(bound.x, bound.y - 2.0, bound.z);
                if (upperBounds == null || lowerBounds == null) continue;
                this.entityUpperBounds.put(e, upperBounds);
                this.entityLowerBounds.put(e, lowerBounds);
            }
        }

        private Vec3d getEntityRenderPosition(Entity entity) {
            double partial = 1;
            double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partial - mc.getRenderManager().viewerPosX;
            double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partial - mc.getRenderManager().viewerPosY;
            double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partial - mc.getRenderManager().viewerPosZ;
            return new Vec3d(x, y, z);
        }

        public Map<Entity, Vec3d> getEntityLowerBounds() {
            return this.entityLowerBounds;
        }
    }
}
