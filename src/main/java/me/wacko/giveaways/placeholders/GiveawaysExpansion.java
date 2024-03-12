package me.wacko.giveaways.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.wacko.giveaways.Giveaways;
import me.wacko.giveaways.commands.GiveawaysCommand;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class GiveawaysExpansion extends PlaceholderExpansion {

    private Giveaways plugin;

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

        /*if (params.equalsIgnoreCase("participants")) {
            return GiveawaysCommand.confirmParticipation();
        }*/

        return params;
    }
}
