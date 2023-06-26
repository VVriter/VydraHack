package com.vydra.death.screen.events;

import lombok.*;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@EqualsAndHashCode(callSuper = true)
@Cancelable
@AllArgsConstructor
@Data
public class MotionUpdateEvent extends Event {
    private float rotationYaw, rotationPitch;
    private double posX, posY, posZ;
    private boolean onGround, noClip;
    private int stage;
}
