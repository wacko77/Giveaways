package me.wacko.giveaways;

import me.wacko.giveaways.commands.GiveawayCreation;
import me.wacko.giveaways.commands.GiveawaysCommand;
import me.wacko.giveaways.commands.TabCompletion;
import me.wacko.giveaways.listeners.GiveawaysListener;
import me.wacko.giveaways.manager.GiveawayManager;
import me.wacko.giveaways.placeholders.GiveawaysExpansion;
import me.wacko.giveaways.util.FlatFile;
import me.wacko.giveaways.util.MessagesFile;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Giveaways extends JavaPlugin {

    private FlatFile flatFile;
    private MessagesFile messagesFile;
    private final GiveawayManager gm = new GiveawayManager(this);

    @Override
    public void onEnable() {
        messagesFile = new MessagesFile(this);
        flatFile = new FlatFile(this);

        flatFile.loadGiveaways();
        messagesFile.saveDefaultMessagesConfig();
        messagesFile.loadMessagesConfig();

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new GiveawaysExpansion(this).register();
        }

        getCommand("giveaway").setExecutor(new GiveawayCreation(gm));
        getCommand("giveaway").setTabCompleter(new TabCompletion());
        getCommand("giveaways").setExecutor(new GiveawaysCommand(this, gm, messagesFile));
        getServer().getPluginManager().registerEvents(new GiveawaysListener(), this);

    }

    @Override
    public void onDisable() {
        System.out.print("Disabling Giveaways!");

        messagesFile.saveMessagesConfig();
    }

    public MessagesFile getMessagesFile() {
        return messagesFile;
    }

    public FlatFile getFlatFile() {
        return flatFile;
    }

}
