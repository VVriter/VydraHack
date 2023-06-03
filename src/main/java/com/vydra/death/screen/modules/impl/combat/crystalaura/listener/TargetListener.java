package com.vydra.death.screen.modules.impl.combat.crystalaura.listener;

import com.vydra.death.screen.events.UpdateWalkingPlayerEvent;
import com.vydra.death.screen.modules.impl.combat.crystalaura.AutoCystalConstants;
import com.vydra.death.screen.modules.settings.types.SliderSetting;
import com.vydra.death.screen.utils.Render3d;
import com.vydra.death.screen.utils.TargetUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;

public class TargetListener {


    private SliderSetting setting = new SliderSetting.Builder()
            .withModule(AutoCystalConstants.instance)
            .min(1)
            .max(6)
            .withDefaultValue(5)
            .withName("TargetRange")
            .build();


    @SubscribeEvent
    public void onUpdate(UpdateWalkingPlayerEvent event) {
        AutoCystalConstants.target = TargetUtil.getClosest(setting.getValue());
    }

   /* @SubscribeEvent
    public void draw(RenderWorldLastEvent event) {
        if (AutoCystalConstants.target == null) return;
        Render3d.drawProperBoxESP(
                new BlockPos(AutoCystalConstants.target.posX, AutoCystalConstants.target.posY, AutoCystalConstants.target.posZ),
                Color.RED,
                1,
                true,
                true,
                100,
                2
        );
    } */


}
