package me.wacko.giveaways.commands;

import me.wacko.giveaways.model.GiveawayManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class GiveawayCreation implements CommandExecutor {

    private final GiveawayManager gm;

    public GiveawayCreation(GiveawayManager gm) {
        this.gm = gm;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("This can only be ran by players!");
            return true;
        }

        Player player = (Player) sender;

        if (player.hasPermission("giveaways.start")) {
            if (args.length == 0) {
                player.sendMessage("Usage: /giveaway create");
                return true;
            }

            if (args.length >= 1) {
                if (args[0].equalsIgnoreCase("create")) {

                    if(!player.hasPermission("giveaways.duration") && args.length >= 2) {
                        player.sendMessage(String.format("%sYou cannot set a duration for your giveaway!\nIf you want to do so, please purchase a rank from our shop that gives you that freedom!", ChatColor.GREEN));
                        return false;
                    }

                    ItemStack prize = player.getInventory().getItemInMainHand();
                    player.getInventory().remove(prize);

                    if (!player.hasPermission("giveaways.duration")) {
                        gm.createGiveaway(player, prize, 60);
                    } else {
                        long duration = Long.parseLong(args[1]);
                        gm.createGiveaway(player, prize, duration);
                    }

                    return true;
                } else return false;
            }
        } else {
            player.sendMessage(String.format("%s%sYou do not have permission to do that!", ChatColor.DARK_RED,  ChatColor.BOLD));
        }

        return true;
    }
}
