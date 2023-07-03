package com.vydra.death.screen.commands;

import com.vydra.death.screen.events.CommandInvokeEvent;
import lombok.Getter;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.List;

public class Command {
    @Getter
    private String name;

    @Getter
    private String description;

    @Getter
    private final List<Option> options = new ArrayList<>();

    public final void addOption(Option option) {
        options.add(option);
    }

    public void invoke() {
        CommandInvokeEvent event = new CommandInvokeEvent(this);
        MinecraftForge.EVENT_BUS.post(event);

        if (event.isCanceled()) return;
    }
}
