package com.vydra.death.screen.modules;

import com.google.gson.*;
import com.vydra.death.screen.Main;
import com.vydra.death.screen.modules.impl.client.FakeVanillaClient;
import com.vydra.death.screen.modules.impl.client.DiscordRPC;
import com.vydra.death.screen.modules.impl.client.Gui;
import com.vydra.death.screen.modules.impl.combat.SkullBurrow;
import com.vydra.death.screen.modules.impl.exploit.HitboxDesync;
import com.vydra.death.screen.modules.impl.miscalaneous.KickSound;
import com.vydra.death.screen.modules.impl.miscalaneous.NoEntityTrace;
import com.vydra.death.screen.modules.impl.exploit.CornerClip;
import com.vydra.death.screen.modules.impl.movement.FastFall;
import com.vydra.death.screen.modules.impl.movement.InstantSpeed;
import com.vydra.death.screen.modules.impl.render.*;
import com.vydra.death.screen.modules.impl.miscalaneous.Fakeplayer;
import com.vydra.death.screen.modules.settings.Setting;
import com.vydra.death.screen.modules.settings.types.BooleanSetting;
import com.vydra.death.screen.modules.settings.types.ColorSetting;
import com.vydra.death.screen.modules.settings.types.KeyBindSetting;
import com.vydra.death.screen.modules.settings.types.SliderSetting;
import lombok.Getter;
import lombok.SneakyThrows;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.json.JSONObject;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public class ModuleManager {

    @Getter
    private final Module[] modules = {
            new Gui(),
            new FullBright(),
            new FakeVanillaClient(),
            new DiscordRPC(),
            new CornerClip(),
            new ItemViewModel(),
            new NoInterpolation(),
            new NoEntityTrace(),
            new Fakeplayer(),
            new ItemShader(),
            new HitboxDesync(),
            new BurrowEsp(),
            new PyroArrow(),
            new KickSound(),
            new FastFall(),
            new Search(),
            new InstantSpeed(),
            new SkullBurrow()
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

    public void registerModule(Module module) {
        addObjectToArray(modules, module);
    }

    private void addObjectToArray(Object[] array, Object objectToAdd) {
        // Create a new array with increased length
        Object[] newArray = new Object[array.length + 1];

        // Copy the existing elements to the new array
        System.arraycopy(array, 0, newArray, 0, array.length);

        // Add the new object to the last index of the new array
        newArray[newArray.length - 1] = objectToAdd;

        // Update the reference to the new array
        array = newArray;
    }


    @SubscribeEvent
    public void onTyping(InputEvent.KeyInputEvent event) {
        Stream.of(modules).forEach(e-> {
            if (e.getKeySetting().getValue() == Keyboard.getEventKey() && Keyboard.getEventKeyState())
                    e.toogle();
        });
    }

    @SneakyThrows
    public Module getModuleByName(String name) {
        for (Module module : modules) { if (Objects.equals(module.getName(), name)) return module; }
        throw new Exception("Module not found");
    }

    @Getter
    private final File defaultConfigFile = new File("./vydra/default.cfg");

    @SneakyThrows
    public void saveConfig(File file) {
        if (!new File("./vydra").exists()) new File("./vydra").mkdir();
        if (!file.exists()) file.createNewFile();

        JSONObject finalObject = new JSONObject();
        for (Module module : modules) {
            JSONObject object = new JSONObject();
            object.put("enabled", module.isEnabled);
            for (Setting s : Main.settingManager.modulesSettings(module)) {
                switch (s.getSettingType()) {
                    case KEYBIND: {
                        object.put(s.getName(), ((KeyBindSetting) s).getValue());
                        break;
                    }
                    case COLOR: {
                        object.put(s.getName(), ((ColorSetting) s).getValue().getRGB());
                        break;
                    }
                    case SLIDER: {
                        object.put(s.getName(), ((SliderSetting) s).getValue());
                        break;
                    }
                    case BOOLEAN: {
                        object.put(s.getName(), ((BooleanSetting)s).isValue());
                        break;
                    }
                }
            }

            finalObject.put(module.getName(), object);
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement je = new JsonParser().parse(finalObject.toString());

        FileWriter writer = new FileWriter(file);
        writer.write(gson.toJson(je));
        writer.close();
    }

    @SneakyThrows
    public void loadConfig(File file) {
        JSONObject content = new JSONObject(readFile(file));

        for (Module module : modules) {
            JSONObject moduleSettingsContent = content.getJSONObject(module.getName());
            if (moduleSettingsContent.getBoolean("enabled")) module.onEnable();
            for (Setting setting : Main.settingManager.modulesSettings(module)) {
                switch (setting.getSettingType()) {
                    case KEYBIND: {
                        int value = moduleSettingsContent.getInt(setting.getName());
                        ((KeyBindSetting) setting).setValue(value);
                        break;
                    }
                    case COLOR: {
                        int value = moduleSettingsContent.getInt(setting.getName());
                        ((ColorSetting) setting).setValue(new Color(value));
                        break;
                    }
                    case SLIDER: {
                        long value = moduleSettingsContent.getLong(setting.getName());
                        ((SliderSetting) setting).setValue(value);
                        break;
                    }
                    case BOOLEAN: {
                        boolean value = moduleSettingsContent.getBoolean(setting.getName());
                        ((BooleanSetting)setting).setValue(value);
                        break;
                    }
                }
            }
        }
    }


    @SneakyThrows
    public static String readFile(File file) throws IOException {
        final BufferedReader br = new BufferedReader(new FileReader(file));
        final StringBuilder sb = new StringBuilder();
        String line = br.readLine();

        while (line != null) {
            sb.append(line);
            sb.append(System.lineSeparator());
            line = br.readLine();
        }
        final String everything = sb.toString();
        return everything;
    }
}
