package com.vydra.death.screen.modules.impl.render;

import com.vydra.death.screen.events.RenderItemInFirstPersonEvent;
import com.vydra.death.screen.mixins.IEntityRenderer;
import com.vydra.death.screen.modules.Category;
import com.vydra.death.screen.modules.Module;
import com.vydra.death.screen.modules.settings.types.BooleanSetting;
import com.vydra.death.screen.modules.settings.types.ColorSetting;
import com.vydra.death.screen.modules.settings.types.SliderSetting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;

public class ItemShader extends Module {
    public ItemShader() {
        super("ItemShader", Category.RENDER);
    }

    @SubscribeEvent
    public void on(RenderItemInFirstPersonEvent event) {
        if (!forceRender)
            event.setCanceled(true);
    }





    private SliderSetting radius = new SliderSetting.Builder()
            .withDefaultValue(2.7)
            .withModule(this)
            .min(1)
            .max(20)
            .withName("Radius")
            .build();

    private ColorSetting color = new ColorSetting.Builder()
            .withModule(this)
            .withName("Color")
            .withDefaultValue(Color.MAGENTA)
            .build();

    private SliderSetting mix = new SliderSetting.Builder()
            .withDefaultValue(0.4)
            .withModule(this)
            .min(0)
            .max(1)
            .withName("Mix")
            .build();

    private SliderSetting alpha = new SliderSetting.Builder()
            .withDefaultValue(21)
            .withModule(this)
            .min(1)
            .max(255)
            .withName("Alpha")
            .build();

    private SliderSetting imageMix = new SliderSetting.Builder()
            .withDefaultValue(0.2)
            .withModule(this)
            .min(0)
            .max(1)
            .withName("ImageMix")
            .build();

    private BooleanSetting blur = new BooleanSetting.Builder()
            .withDefaultValue(true)
            .withModule(this)
            .withName("Blur")
            .build();

    boolean forceRender = true;


    @SubscribeEvent
    public void re(RenderWorldLastEvent event) {
        GlStateManager.pushMatrix();
        GlStateManager.pushAttrib();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        GlStateManager.enableAlpha();
        com.vydra.death.screen.utils.shader.ItemShader shader = com.vydra.death.screen.utils.shader.ItemShader.ITEM_SHADER;
        shader.blur = blur.isValue();
        shader.mix = (float) mix.getValue();
        shader.alpha = (float) alpha.getValue();
        shader.imageMix = (float) imageMix.getValue();
        shader.useImage = true;
        shader.startDraw(mc.getRenderPartialTicks());
        forceRender = true;
        ((IEntityRenderer) mc.entityRenderer).invokeRenderHand(mc.getRenderPartialTicks(), 2);
        forceRender = false;
        shader.stopDraw(color.getValue(), (float) radius.getValue(), 1.0f);
        GlStateManager.disableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.disableDepth();
        GlStateManager.popAttrib();
        GlStateManager.popMatrix();
    }
}
