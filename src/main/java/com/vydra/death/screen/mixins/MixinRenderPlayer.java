package com.vydra.death.screen.mixins;

import com.vydra.death.screen.utils.PacketRender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderPlayer.class)
public abstract class MixinRenderPlayer {

    @Shadow public abstract ModelPlayer getMainModel();

    @Shadow protected abstract void setModelVisibilities(AbstractClientPlayer clientPlayer);

    private float
            renderPitch,
            renderYaw,
            renderHeadYaw,
            prevRenderHeadYaw,
            lastRenderHeadYaw = 0,
            prevRenderPitch,
            lastRenderPitch = 0;

    @Inject(method = "doRender", at = @At("HEAD"))
    private void rotateBegin(AbstractClientPlayer entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        if (entity == Minecraft.getMinecraft().player) {
            prevRenderHeadYaw = entity.prevRotationYawHead;
            prevRenderPitch = entity.prevRotationPitch;
            renderPitch = entity.rotationPitch;
            renderYaw = entity.rotationYaw;
            renderHeadYaw = entity.rotationYawHead;
            entity.rotationPitch = PacketRender.getPitch();
            entity.prevRotationPitch = lastRenderPitch;
            entity.rotationYaw = PacketRender.getYaw();
            entity.rotationYawHead = PacketRender.getYaw();
            entity.prevRotationYawHead = lastRenderHeadYaw;
        }
    }

    @Inject(method = "doRender", at = @At("RETURN"))
    private void rotateEnd(AbstractClientPlayer entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        if (entity == Minecraft.getMinecraft().player) {
            lastRenderHeadYaw = entity.rotationYawHead;
            lastRenderPitch = entity.rotationPitch;
            entity.rotationPitch = renderPitch;
            entity.rotationYaw = renderYaw;
            entity.rotationYawHead = renderHeadYaw;
            entity.prevRotationYawHead = prevRenderHeadYaw;
            entity.prevRotationPitch = prevRenderPitch;
        }
    }
}