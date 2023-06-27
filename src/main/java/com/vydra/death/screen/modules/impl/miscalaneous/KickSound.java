package com.vydra.death.screen.modules.impl.miscalaneous;

import com.vydra.death.screen.modules.Category;
import com.vydra.death.screen.modules.Module;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class KickSound extends Module {
    public KickSound() {
        super("KickSound", Category.MISCELLANEOUS);
    }

    @SubscribeEvent
    public void onGuiOpenEvent(GuiOpenEvent event) {
        if (event.getGui() instanceof GuiDisconnected)
            playSound(new ResourceLocation("kick.wav"));
    }


    public static void playSound(ResourceLocation rl) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    InputStream bufferedInputStream = new BufferedInputStream(mc.getResourceManager().getResource(rl).getInputStream());
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                            bufferedInputStream);
                    clip.open(inputStream);
                    clip.start();
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        }).start();
    }

}
