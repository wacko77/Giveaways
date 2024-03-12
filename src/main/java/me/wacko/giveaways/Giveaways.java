package me.wacko.giveaways;

import me.wacko.giveaways.commands.GiveawayCreation;
import me.wacko.giveaways.commands.GiveawaysCommand;
import me.wacko.giveaways.listeners.GiveawaysListener;
import me.wacko.giveaways.model.Giveaway;
import me.wacko.giveaways.model.GiveawayManager;
import me.wacko.giveaways.placeholders.GiveawaysExpansion;
import me.wacko.giveaways.util.GiveawaysStorage;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Giveaways extends JavaPlugin {

    private static Giveaways plugin;
    private final GiveawayManager gm = new GiveawayManager(this);
    private List<Giveaway> activeGiveaways = new ArrayList<>();
    @Override
    public void onEnable() {

        plugin = this;

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        GiveawaysStorage.setup();
        GiveawaysStorage.get().addDefault("Giveaways", "");
        GiveawaysStorage.get().options().copyDefaults(true);
        GiveawaysStorage.save();

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) { //
            new GiveawaysExpansion(this).register(); //
        }

        getCommand("giveaway").setExecutor(new GiveawayCreation(gm));
        getCommand("giveaways").setExecutor(new GiveawaysCommand(this));
        getServer().getPluginManager().registerEvents(new GiveawaysListener(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public List<Giveaway> getActiveGiveaways() {return activeGiveaways;}

}
