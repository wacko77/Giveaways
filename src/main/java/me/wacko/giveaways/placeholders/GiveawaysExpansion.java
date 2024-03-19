package me.wacko.giveaways.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.wacko.giveaways.Giveaways;
import me.wacko.giveaways.commands.GiveawaysCommand;
import me.wacko.giveaways.model.Giveaway;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GiveawaysExpansion extends PlaceholderExpansion {

    private final Giveaways plugin;

    public GiveawaysExpansion(Giveaways plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "giveaways";
    }

    @Override
    public @NotNull String getAuthor() {
        return "wacko";
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    public String onRequest(OfflinePlayer player, @NotNull String params) {

        if (params.equalsIgnoreCase("booty")){
            return "It works!";
        }

        return null;
    }

}
