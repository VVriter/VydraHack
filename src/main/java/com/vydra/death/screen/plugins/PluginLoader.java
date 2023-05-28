package com.vydra.death.screen.plugins;

import com.vydra.death.screen.Main;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PluginLoader {
    public ArrayList<VydraPlugin> plugins;

    public PluginLoader() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        plugins = new ArrayList<>();

        for (File file : Main.configManager.pluginsPath.listFiles()) {
            if (!FilenameUtils.getExtension(file.getName()).equals("jar")) return;

            URLClassLoader classLoader = new URLClassLoader(new URL[]{file.toURI().toURL()});
            JarFile plugin = new JarFile(file);
            Enumeration<JarEntry> entries = plugin.entries();

            while (entries.hasMoreElements()) {
                JarEntry jarEntry = entries.nextElement();
                if (jarEntry.isDirectory() || !jarEntry.getName().endsWith(".class")) continue;
                String className = jarEntry.getName().substring(0, jarEntry.getName().length() - 6).replace('/', '.');
                Class<?> clazz = classLoader.loadClass(className);

            }
        }
    }
}
