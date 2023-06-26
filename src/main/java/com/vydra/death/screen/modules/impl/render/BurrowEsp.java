package com.vydra.death.screen.modules.impl.render;

import com.vydra.death.screen.modules.Category;
import com.vydra.death.screen.modules.Module;
import com.vydra.death.screen.modules.settings.types.ColorSetting;
import com.vydra.death.screen.modules.settings.types.SliderSetting;
import com.vydra.death.screen.utils.Render3d;
import com.vydra.death.screen.utils.Timer;
import com.vydra.death.screen.utils.player.BlockUtil;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.block.BlockSkull;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BurrowEsp extends Module {

    public SliderSetting animationDelay = new SliderSetting.Builder()
            .withName("AnimationDelay")
            .withModule(this)
            .withDefaultValue(10)
            .min(1)
            .max(100)
            .build();

    public ColorSetting colorSetting = new ColorSetting.Builder()
            .withName("Color")
            .withModule(this)
            .withDefaultValue(new Color(0xDBD30CD3, true))
            .build();

    public SliderSetting alphaLimit = new SliderSetting.Builder()
            .withName("AlphaLimit")
            .min(50)
            .max(255)
            .withModule(this)
            .withDefaultValue(200)
            .build();

    @Getter
    private List<PosEsp> posEspList;

    public BurrowEsp() {
        super("BurrowEsp", "", Category.RENDER);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        posEspList = new ArrayList<>();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        posEspList = null;
    }

    @SubscribeEvent
    public void onTickAdding(TickEvent.ClientTickEvent event) {
        if (mc.world != null) {
            BlockUtil.getSphere(10, true, mc.player)
                    .stream()
                    .filter(block -> BlockUtil.getBlock(block) instanceof BlockSkull)
                    .forEach(block -> {
                        if (isPosExistsInList(block)) return;
                        posEspList.add(new PosEsp(block));
                    });
        }
    }

    @SubscribeEvent
    public void onTickClearing(TickEvent.ClientTickEvent event) {
        Iterator<PosEsp> iterator = getPosEspList().iterator();
        while (iterator.hasNext()) {
            PosEsp renderpos = iterator.next();
            if (!renderpos.exists && renderpos.scale == 0) {
                iterator.remove();
            }
            if (!(BlockUtil.getBlock(renderpos.getPos()) instanceof BlockSkull)) {
                renderpos.setExists(false);
            }
        }
    }


    @SubscribeEvent
    public void onRender3d(RenderWorldLastEvent event) {
        getPosEspList().forEach(PosEsp::draw);
    }

    private boolean isPosExistsInList(BlockPos pos) {
        for (PosEsp esp : getPosEspList()) {
            if (esp.getPos().equals(pos)) {
                return true;
            }
        }
        return false;
    }

    private class PosEsp {
        @Getter
        @Setter
        private boolean exists;

        @Getter
        private BlockPos pos;

        @Getter
        @Setter
        private long scale;

        @Getter
        Timer timer;

        public PosEsp(BlockPos pos) {
            this.pos = pos;
            this.scale = 0;
            this.exists = true;
            timer = new Timer();
        }


        public void draw() {
            if (exists) {
                if (scale < alphaLimit.getValue() && timer.hasPassedMs(animationDelay.getValue())) {
                    scale++;
                }
            } else {
                if (scale > 0 && timer.hasPassedMs(animationDelay.getValue())) {
                    scale--;
                }
            }

            Render3d.drawProperBox(getPos(), colorSetting.getValue(), (int) getScale());

        }
    }

}
