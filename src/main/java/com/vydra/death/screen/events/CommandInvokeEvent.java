package com.vydra.death.screen.events;

import com.vydra.death.screen.commands.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
@AllArgsConstructor
public class CommandInvokeEvent extends Event {
    @Getter
    private Command command;
}
