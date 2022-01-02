package net.crossager.suolib.files;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class YamlConfig {

    private Plugin plugin;
    private String fileName;
    private FileConfiguration dataConfig = null;
    private File configFile = null;
    public YamlConfig(Plugin plugin, String fileName) {
        this.fileName = fileName;
        this.plugin = plugin;
        saveDefaultConfig();
    }

    public void reload() {
        if (this.configFile == null)
            this.configFile = new File(this.plugin.getDataFolder(), fileName);

        this.dataConfig = YamlConfiguration.loadConfiguration(this.configFile);

        InputStream defaultStream = this.plugin.getResource(fileName);
        if (defaultStream  != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            this.dataConfig.setDefaults(defaultConfig);
        }

    }

    public FileConfiguration getConfig() {
        if (this.dataConfig == null)
            reload();
        return this.dataConfig;
    }

    public boolean save() {
        if (this.dataConfig == null || this.configFile == null)
            return false;

        try {
            this.getConfig().save(this.configFile);
            return true;
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not save: " + this.configFile, e);
            e.printStackTrace();
            return false;
        }
    }

    public void saveDefaultConfig() {
        if (this.configFile == null)
            this.configFile = new File(this.plugin.getDataFolder(), fileName);

        if (!this.configFile.exists()) {
            this.plugin.saveResource(fileName, false);
        }
    }
}
