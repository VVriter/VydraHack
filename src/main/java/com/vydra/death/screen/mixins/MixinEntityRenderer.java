package com.vydra.death.screen.mixins;

import com.vydra.death.screen.events.RenderItemInFirstPersonEvent;
import com.vydra.death.screen.modules.impl.miscalaneous.NoEntityTrace;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import static com.vydra.death.screen.utils.Render2d.mc;

@Mixin( EntityRenderer.class )
public class MixinEntityRenderer {

    @Inject( method = "getMouseOver", at = @At( value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getPositionEyes(F)Lnet/minecraft/util/math/Vec3d;", shift = At.Shift.BEFORE ), cancellable = true )
    public void nt_getMouseOver(float partialTicks, CallbackInfo info) {

        if (NoEntityTrace.getInstance().isEnabled && NoEntityTrace.getInstance().can()) {
            info.cancel();
            mc.profiler.endSection();
        }
    }

    @Redirect(method = "renderHand", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;renderItemInFirstPerson(F)V"))
    private void renderHand(ItemRenderer itemRenderer, float partialTicks)
    {
        RenderItemInFirstPersonEvent event = new RenderItemInFirstPersonEvent();
        MinecraftForge.EVENT_BUS.post(event);
        if (!event.isCanceled()) itemRenderer.renderItemInFirstPerson(partialTicks);
    }

}
