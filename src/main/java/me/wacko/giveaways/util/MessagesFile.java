package me.wacko.giveaways.util;

import java.io.File;
import java.io.IOException;
import me.wacko.giveaways.Giveaways;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;


public class MessagesFile {

    private FileConfiguration messagesConfig;
    private File messagesFile;
    private Giveaways plugin;

    public MessagesFile(Giveaways plugin) {
        this.plugin = plugin;
    }

    public void saveDefaultMessagesConfig() {
        if (messagesFile == null) {
            messagesFile = new File(plugin.getDataFolder(), "messages.yml");
        }
        if (!messagesFile.exists()) {
            plugin.saveResource("messages.yml", false);
        }
    }

    public void loadMessagesConfig() {
        if (messagesFile == null) {
            messagesFile = new File(plugin.getDataFolder(), "messages.yml");
        }
        messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
    }

    public FileConfiguration getMessagesConfig() {
        if (messagesConfig == null) {
            loadMessagesConfig();
        }
        return messagesConfig;
    }

    public void saveMessagesConfig() {
        if (messagesConfig == null || messagesFile == null) {
            return;
        }
        try {
            getMessagesConfig().save(messagesFile);
        } catch (IOException ex) {
            plugin.getLogger().severe("Could not save messages config to " + messagesFile);
        }
    }

    public void reloadMessagesConfig() {
        if (messagesFile == null) {
            messagesFile = new File(plugin.getDataFolder(), "messages.yml");
        }
        messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
    }

}
