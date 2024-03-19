package me.wacko.giveaways;

import me.wacko.giveaways.commands.GiveawayCreation;
import me.wacko.giveaways.commands.GiveawaysCommand;
import me.wacko.giveaways.listeners.GiveawaysListener;
import me.wacko.giveaways.manager.GiveawayManager;
import me.wacko.giveaways.placeholders.GiveawaysExpansion;
import me.wacko.giveaways.util.FlatFile;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Giveaways extends JavaPlugin {

    private FlatFile file;
    private final GiveawayManager gm = new GiveawayManager(this);

    @Override
    public void onEnable() {

        file = new FlatFile(this);

        file.loadGiveaways();

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) { //
            new GiveawaysExpansion(this).register(); //
        }

        getCommand("giveaway").setExecutor(new GiveawayCreation(gm));
        getCommand("giveaways").setExecutor(new GiveawaysCommand(this, gm));
        getServer().getPluginManager().registerEvents(new GiveawaysListener(), this);

    }

    @Override
    public void onDisable() {
        System.out.print("Disabling Giveaways!");
    }



}
