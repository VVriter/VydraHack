package com.vydra.death.screen.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
@AllArgsConstructor
@Data
public class MoveEvent extends Event {
    private double x, y, z;
}
