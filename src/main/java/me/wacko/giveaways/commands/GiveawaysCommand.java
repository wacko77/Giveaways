package me.wacko.giveaways.commands;

import me.wacko.giveaways.Giveaways;
import me.wacko.giveaways.gui.GiveawaysGUI;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveawaysCommand implements CommandExecutor {
    private final Giveaways plugin;

    public GiveawaysCommand(Giveaways plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Command can only be run by players!");
        }

        Player player = (Player) sender;

        if (player.hasPermission("giveaways.command")) {
            GiveawaysGUI giveawaysGUI = new GiveawaysGUI(plugin);
            giveawaysGUI.open(player);
        } else {
            player.sendMessage(String.format("%s%sYou do not have permission to do that!", ChatColor.DARK_RED,  ChatColor.BOLD));
        }

        return true;
    }
}
