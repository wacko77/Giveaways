package me.wacko.giveaways.util;

import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.logging.Level;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;


public class Messages {

    private final JavaPlugin plugin;
    private FileConfiguration messagesConfig = null;
    private File messagesFile = null;

    public Messages(JavaPlugin plugin) {
        this.plugin = plugin;
        // Ensure the messages.yml file exists
        saveDefaultConfig();
    }

    public void reloadConfig() {
        if (messagesFile == null) {
            messagesFile = new File(plugin.getDataFolder(), "messages.yml");
        }
        messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);

        // Look for defaults in the jar
        InputStream defConfigStream = plugin.getResource("messages.yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream));
            messagesConfig.setDefaults(defConfig);
        }
    }


    public FileConfiguration getConfig() {
        if (messagesConfig == null) {
            reloadConfig();
        }
        return messagesConfig;
    }

    public void saveConfig() {
        if (messagesConfig == null || messagesFile == null) {
            return;
        }
        try {
            getConfig().save(messagesFile);
        } catch (IOException ex) {
            plugin.getLogger().log(Level.SEVERE, "Could not save config to " + messagesFile, ex);
        }
    }

    public void saveDefaultConfig() {
        if (messagesFile == null) {
            messagesFile = new File(plugin.getDataFolder(), "messages.yml");
        }
        if (!messagesFile.exists()) {
            plugin.saveResource("messages.yml", false);
        }
    }

    public void saveDefaultMessages() {
        if (messagesFile == null) {
            messagesFile = new File(plugin.getDataFolder(), "messages.yml");
        }
        if (!messagesFile.exists()) {
            try {
                Files.copy(plugin.getResource("messages.yml"), messagesFile.toPath());
            } catch (IOException ex) {
                plugin.getLogger().log(Level.SEVERE, "Could not save default config to " + messagesFile, ex);
            }
        }
    }
}
