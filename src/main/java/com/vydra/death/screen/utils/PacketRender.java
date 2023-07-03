package com.vydra.death.screen.utils;

public class PacketRender {
    private static float yaws = 0;
    private static float pitchs = 0;

    public static float getYaw() {
        return yaws;
    }

    public static float getPitch() {
        return pitchs;
    }

    public static void setYaw(float yaw) {
        yaws = yaw;
    }

    public static void setPitch(float pitch) {
        pitchs = pitch;
    }
}