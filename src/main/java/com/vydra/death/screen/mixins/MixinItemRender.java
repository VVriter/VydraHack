package com.vydra.death.screen.mixins;

import com.vydra.death.screen.modules.impl.render.ItemViewModel;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public class MixinItemRender {

    @Inject(method = "renderItemSide", at = @At("HEAD"))
    public void renderItemSide(EntityLivingBase entitylivingbaseIn, ItemStack heldStack, ItemCameraTransforms.TransformType transform, boolean leftHanded, CallbackInfo ci) {
        if (ItemViewModel.INSTANCE.INSTANCE.isEnabled) {
            GlStateManager.scale(ItemViewModel.INSTANCE.INSTANCE.sx.getValue() / 100F, ItemViewModel.INSTANCE.sy.getValue() / 100F, ItemViewModel.INSTANCE.sz.getValue() / 100F);
            if (transform == ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND) {
                GlStateManager.translate(ItemViewModel.INSTANCE.tx.getValue() / 100F, ItemViewModel.INSTANCE.ty.getValue() / 100F, ItemViewModel.INSTANCE.tz.getValue() / 100F);
                GlStateManager.rotate((float) ItemViewModel.INSTANCE.rx.getValue(), 1, 0, 0);
                GlStateManager.rotate((float) ItemViewModel.INSTANCE.ry.getValue(), 0, 1, 0);
                GlStateManager.rotate((float) ItemViewModel.INSTANCE.rz.getValue(), 0, 0, 1);
            } else if (transform == ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND) {
                GlStateManager.translate(-ItemViewModel.INSTANCE.tx.getValue() / 100F, ItemViewModel.INSTANCE.ty.getValue() / 100F, ItemViewModel.INSTANCE.tz.getValue() / 100F);
                GlStateManager.rotate((float) -ItemViewModel.INSTANCE.rx.getValue(), 1, 0, 0);
                GlStateManager.rotate((float) ItemViewModel.INSTANCE.ry.getValue(), 0, 1, 0);
                GlStateManager.rotate((float) ItemViewModel.INSTANCE.rz.getValue(), 0, 0, 1);
            }
        }
    }


    @Inject(method = "rotateArm", at = @At("HEAD"), cancellable = true)
    public void rotateArmHook(float p_187458_1_, CallbackInfo ci) {
        if (ItemViewModel.INSTANCE.isEnabled && ItemViewModel.INSTANCE.noSway.isValue()) {
            ci.cancel();
        }
    }


}