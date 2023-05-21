package com.vydra.death.screen.modules.impl.client;

import com.vydra.death.screen.events.PacketEvent;
import com.vydra.death.screen.mixins.ICPacketCustomPayload;
import com.vydra.death.screen.modules.Category;
import com.vydra.death.screen.modules.Module;
import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class BirkaBypass extends Module {
    public BirkaBypass() { super("BirkaBypass", "", Category.CLIENT, Keyboard.KEY_NONE); }

    @SubscribeEvent
    public void onBypass(PacketEvent.Send event) {
        if (!mc.isIntegratedServerRunning()) {
            if (event.getPacket().getClass().getName().equals("net.minecraftforge.fml.common.network.internal.FMLProxyPacket")) {
                event.setCanceled(true);
            } else if (event.getPacket() instanceof CPacketCustomPayload) {
                if (((CPacketCustomPayload) event.getPacket()).getChannelName().equalsIgnoreCase("MC|Brand")) {
                    ((ICPacketCustomPayload) event.getPacket()).setData(new PacketBuffer(Unpooled.buffer()).writeString("vanilla"));
                }
            }
        }
    }

}
