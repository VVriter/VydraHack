package com.vydra.death.screen.modules;

import com.vydra.death.screen.modules.impl.client.BirkaBypass;
import com.vydra.death.screen.modules.impl.client.DiscordRPC;
import com.vydra.death.screen.modules.impl.client.Gui;
import com.vydra.death.screen.modules.impl.combat.FeetTrap;
import com.vydra.death.screen.modules.impl.miscalaneous.Freecam;
import com.vydra.death.screen.modules.impl.miscalaneous.BebraModule;
import com.vydra.death.screen.modules.impl.miscalaneous.ObsidianFiller;
import com.vydra.death.screen.modules.impl.movement.CornerClip;
import com.vydra.death.screen.modules.impl.movement.Step;
import com.vydra.death.screen.modules.impl.render.FullBright;
import com.vydra.death.screen.modules.impl.render.ItemViewModel;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

import java.util.stream.Stream;

public class ModuleManager {

    private Module[] modules = {
            new BebraModule(),
            new Freecam(),
            new Gui(),
            new Step(),
            new FullBright(),
            new FeetTrap(),
            new BirkaBypass(),
            new ObsidianFiller(),
            new DiscordRPC(),
            new CornerClip(),
            new ItemViewModel()
    };

    public void register() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void unregister() {
        Stream.of(modules).forEach(e-> {
            if (e.isEnabled) e.onDisable();
        });
        MinecraftForge.EVENT_BUS.unregister(this);
    }


    @SubscribeEvent
    public void onTyping(InputEvent.KeyInputEvent event) {
        Stream.of(modules).forEach(e-> {
            if (e.getKey() == Keyboard.getEventKey()) {
                if (Keyboard.getEventKeyState()) {
                    e.toogle();
                }
            }
        });
    }

    public Module[] getModules() {
        return modules;
    }

    public Module getModuleByName(String name) {
        for (Module module : modules) { if (module.getName() == name) return module; }
        return null;
    }


}
