package me.wacko.giveaways.util;

import me.wacko.giveaways.Giveaways;
import me.wacko.giveaways.model.Giveaway;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FlatFile {
    private final Giveaways plugin;
    private final File file;

    public FlatFile(Giveaways plugin) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), "giveaways.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Giveaway> loadGiveaways() {
        List<Giveaway> giveaways = new ArrayList<>();
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        if (config.contains("giveaways")) {
            for (String key : config.getConfigurationSection("giveaways").getKeys(false)) {
                int id = Integer.parseInt(key);
                ItemStack prize = config.getItemStack("giveaways." + key + ".prize");
                Player host = plugin.getServer().getPlayer(config.getString("giveaways." + key + ".host"));
                List<Player> participants = new ArrayList<>();
                List<String> participantNames = config.getStringList("giveaways." + key + ".participants");
                for (String participantName : participantNames) {
                    Player participant = plugin.getServer().getPlayer(participantName);
                    if (participant != null) {
                        participants.add(participant);
                    }
                }

                giveaways.add(new Giveaway(prize, id, host, participants));
            }
        }

        return giveaways;
    }

    public void saveGiveaways(List<Giveaway> giveaways) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        for (Giveaway giveaway : giveaways) {
            String key = Integer.toString(giveaway.getId());
            config.set("giveaways." + key + ".prize", giveaway.getPrize());
            config.set("giveaways." + key + ".host", giveaway.getHost().getName());
            List<String> participantNames = new ArrayList<>();
            for (Player participant : giveaway.getParticipants()) {
                participantNames.add(participant.getName());
            }
            config.set("giveaways." + key + ".participants", participantNames);
        }

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeGiveaway(Giveaway giveawayToRemove) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        // Remove entries associated with the giveaway to remove
        String key = Integer.toString(giveawayToRemove.getId());
        config.set("giveaways." + key, null);

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
