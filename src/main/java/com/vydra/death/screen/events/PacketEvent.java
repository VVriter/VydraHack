package com.vydra.death.screen.events;

import lombok.Getter;
import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class PacketEvent extends Event {

    @Getter
    private final Packet packet;

    public PacketEvent(final Packet packet) {
        this.packet = packet;
    }

    public final static class Receive extends PacketEvent {
        public Receive(final Packet packet) {
            super(packet);
        }
    }

    public final static class Send extends PacketEvent {
        public Send(final Packet packet) {
            super(packet);
        }
    }
}