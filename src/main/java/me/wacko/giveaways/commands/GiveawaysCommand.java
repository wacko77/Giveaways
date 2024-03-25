package me.wacko.giveaways.commands;

import me.wacko.giveaways.Giveaways;
import me.wacko.giveaways.gui.GiveawaysGUI;
import me.wacko.giveaways.manager.GiveawayManager;
import me.wacko.giveaways.util.FlatFile;
import me.wacko.giveaways.util.Messages;
import me.wacko.giveaways.util.MessagesFile;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class GiveawaysCommand implements CommandExecutor {
    private final Giveaways plugin;
    private final GiveawayManager gm;
    private final MessagesFile messagesFile;

    public GiveawaysCommand(Giveaways plugin, GiveawayManager gm) {
        this.plugin = plugin;
        this.gm = gm;
        this.messagesFile = plugin.getMessagesFile();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {

        if (!(sender instanceof Player) && !(sender instanceof ConsoleCommandSender)) {
            sender.sendMessage("Command can only be run by players!");
        }

        Messages message = new Messages(plugin);
        Player player = (Player) sender;

        if (args.length == 0) {
            if (player.hasPermission("giveaways.command")) {
                GiveawaysGUI giveawaysGUI = new GiveawaysGUI(plugin, gm);
                giveawaysGUI.open(player);
            } else {
                message.sendNoPermission(player);
            }
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (player.hasPermission("giveaways.admin") || sender instanceof ConsoleCommandSender) {
                messagesFile.reloadMessagesConfig();
                plugin.reloadConfig();

                player.sendMessage("Reloaded all configs!");
                return true;
            }
        }

        return true;
    }
}
