package com.vydra.death.screen.utils;

import net.minecraft.entity.Entity;

import java.awt.*;

public class EntityUtil {

    public static Color getColor(Entity entity, int red, int green, int blue, int alpha, boolean colorFriends) {
        Color color = new Color((float) red / 255.0f, (float) green / 255.0f, (float) blue / 255.0f, (float) alpha / 255.0f);
        return color;
    }


}
