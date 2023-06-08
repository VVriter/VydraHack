package com.vydra.death.screen.modules;

import com.vydra.death.screen.modules.impl.client.BirkaBypass;
import com.vydra.death.screen.modules.impl.client.DiscordRPC;
import com.vydra.death.screen.modules.impl.client.Gui;
import com.vydra.death.screen.modules.impl.client.ModulesEnabledList;
import com.vydra.death.screen.modules.impl.combat.crystalaura.AutoCrystal;
import com.vydra.death.screen.modules.impl.exploit.HitboxDesync;
import com.vydra.death.screen.modules.impl.miscalaneous.NoEntityTrace;
import com.vydra.death.screen.modules.impl.movement.CornerClip;
import com.vydra.death.screen.modules.impl.render.FullBright;
import com.vydra.death.screen.modules.impl.render.ItemShader;
import com.vydra.death.screen.modules.impl.render.ItemViewModel;
import com.vydra.death.screen.modules.impl.render.NoInterpolation;
import com.vydra.death.screen.modules.impl.miscalaneous.Fakeplayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

import java.util.Objects;
import java.util.stream.Stream;

public class ModuleManager {

    private Module[] modules = {
            new Gui(),
            new FullBright(),
            new BirkaBypass(),
            new DiscordRPC(),
            new CornerClip(),
            new ItemViewModel(),
            new NoInterpolation(),
            new ModulesEnabledList(),
            new NoEntityTrace(),
            new Fakeplayer(),
            new AutoCrystal(),
            new ItemShader(),
            new HitboxDesync()
    };

    public void register() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void unregister() {
        Stream.of(modules).forEach(e-> {
            if (e.isEnabled)
                e.onDisable();
        });
        MinecraftForge.EVENT_BUS.unregister(this);
    }


    @SubscribeEvent
    public void onTyping(InputEvent.KeyInputEvent event) {
        Stream.of(modules).forEach(e-> {
            if (e.getKeySetting().getValue() == Keyboard.getEventKey()) {
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
        for (Module module : modules) { if (Objects.equals(module.getName(), name)) return module; }
        return null;
    }

}
