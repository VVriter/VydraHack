package com.vydra.death.screen.mixins;

import net.minecraft.network.play.client.CPacketPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(CPacketPlayer.class)
public interface ICPacketPlayer {

    @Accessor(value = "yaw")
    float getYaw();

    @Accessor(value = "yaw")
    void setYaw(float yaw);

    @Accessor(value = "pitch")
    float getPitch();

    @Accessor(value = "pitch")
    void setPitch(float pitch);

    @Accessor(value = "z")
    void setZ(double y);

    @Accessor(value = "z")
    double getZ();

    @Accessor(value = "x")
    void setX(double y);

    @Accessor(value = "x")
    double getX();

    @Accessor(value = "y")
    void setY(double y);

    @Accessor(value = "y")
    double getY();

    @Accessor(value = "onGround")
    void setOnGround(boolean onGround);

    @Accessor(value = "rotating")
    boolean isRotating();

    @Accessor(value = "moving")
    boolean isMoving();

}