package me.wacko.giveaways;

import me.wacko.giveaways.commands.GiveawayCreation;
import me.wacko.giveaways.commands.GiveawaysCommand;
import me.wacko.giveaways.listeners.GiveawaysListener;
import me.wacko.giveaways.model.Giveaway;
import me.wacko.giveaways.model.GiveawayManager;
import me.wacko.giveaways.placeholders.GiveawaysExpansion;
import me.wacko.giveaways.storage.FlatFile;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Giveaways extends JavaPlugin {

    private FlatFile file;
    private List<Giveaway> activeGiveaways = new ArrayList<>();
    GiveawayManager gm = new GiveawayManager(this, activeGiveaways);

    @Override
    public void onEnable() {

        file = new FlatFile(this);

        activeGiveaways = file.loadGiveaways();

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) { //
            new GiveawaysExpansion(this).register(); //
        }

        getCommand("giveaway").setExecutor(new GiveawayCreation(gm));
        getCommand("giveaways").setExecutor(new GiveawaysCommand(this));
        getServer().getPluginManager().registerEvents(new GiveawaysListener(), this);

    }

    @Override
    public void onDisable() {
        file.saveGiveaways(activeGiveaways);

        System.out.print("Disabling Giveaways!");
    }

    public List<Giveaway> getActiveGiveaways() {return activeGiveaways;}
    public FlatFile getFlatFile() {return file;}

}
