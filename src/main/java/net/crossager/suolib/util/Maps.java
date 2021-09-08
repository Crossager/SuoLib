package net.crossager.suolib.util;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.LogManager;

public class Maps {
    public static void saveMap(FileConfiguration config, Map<?, ?> map, String prePath, String surPath) {
        try {
            for (Object key : map.keySet()) {
                config.set(prePath + "." + key + "." + surPath, map.get(key));
            }
            LogManager.getLogManager().getLogger("CrossagerAPI").log(Level.INFO, "Saved config map in file: " + config.getName() + ", in section: " + prePath + ", with surpath: " + surPath);
        } catch (Exception e){
            LogManager.getLogManager().getLogger("CrossagerAPI").log(Level.SEVERE, "Couldn't save config map in file: " + config.getName() + ", in section: " + prePath + ", with surpath: " + surPath);
        }
    }
    public static Map loadMap(FileConfiguration config, String prePath, String surPath) {
        Map map = new HashMap<>();
        try {
            LogManager.getLogManager().getLogger("CrossagerAPI").log(Level.INFO, "Loaded config map in file: " + config.getName() + ", in section: " + prePath + ", with surpath: " + surPath);
            if (config.isSet(prePath))
                for (String key : config.getConfigurationSection(prePath).getKeys(false)){
                    map.put(config.get(prePath + "." + key), config.get(prePath + "." + key + "." + surPath));
                }
        } catch (Exception e){
            LogManager.getLogManager().getLogger("CrossagerAPI").log(Level.SEVERE, "Couldn't load map in file: " + config.getName() + ", in section: " + prePath + ", with surpath: " + surPath);
        }
        return map;
    }
}
