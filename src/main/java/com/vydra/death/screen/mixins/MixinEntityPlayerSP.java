package com.vydra.death.screen.mixins;

import com.mojang.authlib.GameProfile;
import com.vydra.death.screen.events.MotionUpdateEvent;
import com.vydra.death.screen.events.MoveEvent;
import com.vydra.death.screen.events.UpdateWalkingPlayerEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayerSP.class)
public abstract class MixinEntityPlayerSP extends EntityPlayer {

    public MixinEntityPlayerSP(World worldIn, GameProfile gameProfileIn) {
        super(worldIn, gameProfileIn);
    }

    @Inject(method = "onUpdateWalkingPlayer", at = @At("HEAD"), cancellable = true)
    public void OnPreUpdateWalkingPlayer(CallbackInfo callbackInfo) {
        UpdateWalkingPlayerEvent eventPlayerMotionUpdate = new UpdateWalkingPlayerEvent();
        MinecraftForge.EVENT_BUS.post(eventPlayerMotionUpdate);
        if (eventPlayerMotionUpdate.isCanceled())
            callbackInfo.cancel();
    }

    @Redirect( method = "move", at = @At( value = "INVOKE", target = "Lnet/minecraft/client/entity/AbstractClientPlayer;move(Lnet/minecraft/entity/MoverType;DDD)V" ) )
    public void move(AbstractClientPlayer player, MoverType type, double x, double y, double z) {
        MoveEvent event = new MoveEvent(x, y, z);
        MinecraftForge.EVENT_BUS.post(event);
        super.move(type, event.getX(), event.getY(), event.getZ());
    }

    @Inject( method = "onUpdate",
            at = @At( value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;onUpdateWalkingPlayer()V", shift = At.Shift.BEFORE ) )
    public void onPreMotionUpdate(CallbackInfo info) {
        MotionUpdateEvent event = new MotionUpdateEvent(Minecraft.getMinecraft().player.rotationYaw, Minecraft.getMinecraft().player.rotationPitch, Minecraft.getMinecraft().player.posX, Minecraft.getMinecraft().player.posY, Minecraft.getMinecraft().player.posZ, Minecraft.getMinecraft().player.onGround, Minecraft.getMinecraft().player.noClip, 0);
        MinecraftForge.EVENT_BUS.post(event);
    }
}