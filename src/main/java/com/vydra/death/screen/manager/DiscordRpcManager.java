package com.vydra.death.screen.manager;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;

public class DiscordRpcManager {

    private String id = "1110491935409446994";
    private DiscordRichPresence presence = new DiscordRichPresence();
    private DiscordRPC rpc = DiscordRPC.INSTANCE;
    public void start() {
        DiscordEventHandlers eventHandlers = new DiscordEventHandlers();
        rpc.Discord_Initialize(id, eventHandlers, true, null);

        presence.largeImageKey = "image";
        presence.largeImageText = "VydraHack enjoying";
        presence.startTimestamp = System.currentTimeMillis() / 1000L;
        presence.details = "Owning ur fucked mother";
        presence.state = "Shop - discord.gg/2FEhkrET5q";

        rpc.Discord_UpdatePresence(presence);
    }

    public void update() {

    }

    public void stop() {
        rpc.Discord_Shutdown();
        rpc.Discord_ClearPresence();
    }
}
