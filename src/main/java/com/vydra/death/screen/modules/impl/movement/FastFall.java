package com.vydra.death.screen.modules.impl.movement;

import com.vydra.death.screen.events.UpdateWalkingPlayerEvent;
import com.vydra.death.screen.mixins.IMinecraft;
import com.vydra.death.screen.mixins.ITimer;
import com.vydra.death.screen.modules.Category;
import com.vydra.death.screen.modules.Module;
import com.vydra.death.screen.modules.settings.types.BooleanSetting;
import com.vydra.death.screen.modules.settings.types.SliderSetting;
import com.vydra.death.screen.utils.Timer;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FastFall extends Module {


    BooleanSetting sneaking = new BooleanSetting.Builder()
            .withModule(this)
            .withDefaultValue(true)
            .withName("Sneaking")
            .build();

    SliderSetting delay = new SliderSetting.Builder()
            .withName("Delay")
            .withModule(this)
            .withDefaultValue(3)
            .min(1)
            .max(10)
            .build();

    SliderSetting ticks = new SliderSetting.Builder()
            .withName("Ticks")
            .withModule(this)
            .withDefaultValue(3)
            .min(1)
            .max(10)
            .build();


    public FastFall() {
        super("FastFall", Category.MOVEMENT);
    }

    private Timer timer;
    boolean timed;
    boolean sneaks;

    @Override
    public void onEnable() {
        super.onEnable();
        timer = new Timer();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        timer = null;
        ((ITimer) ((IMinecraft) mc).getTimer()).setTickLength(50.0f);
        if (sneaking.isValue())
            mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
    }

    @SubscribeEvent
    public void onUpdateWalkingPlayerEvent(UpdateWalkingPlayerEvent event) {
        if (mc.player.isInWater() || mc.player.isInLava()) return;

        if (!mc.player.onGround && mc.player.motionY < 0 && !mc.player.movementInput.jump && !sneaks && timer.hasPassedMs(delay.getValue())) {
            ((ITimer) ((IMinecraft) mc).getTimer()).setTickLength((float) (50.0f - ticks.getValue()));
            if (sneaking.isValue())
                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
            sneaks = true;
            timed = true;
            timer.reset();
        }
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        if (!event.getEntity().equals(mc.player)) return;

        if ((timed || sneaks) && mc.player.onGround) {
            timed = false;
            sneaks = false;
            ((ITimer) ((IMinecraft) mc).getTimer()).setTickLength(50.0f);
            if (sneaking.isValue())
                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
        }
    }

}
