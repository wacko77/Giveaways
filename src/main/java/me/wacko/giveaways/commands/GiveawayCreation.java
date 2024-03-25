package me.wacko.giveaways.commands;

import me.wacko.giveaways.Giveaways;
import me.wacko.giveaways.manager.GiveawayManager;
import me.wacko.giveaways.util.Messages;
import me.wacko.giveaways.util.MessagesFile;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;


public class GiveawayCreation implements CommandExecutor {

    private final GiveawayManager gm;
    private final Giveaways plugin;

    public GiveawayCreation(GiveawayManager gm, Giveaways plugin) {
        this.gm = gm;
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This can only be ran by players!");
            return true;
        }

        Messages message = new Messages(plugin);
        Player player = (Player) sender;

        if (player.hasPermission("giveaways.start")) {
            if (args.length == 0) {
                player.sendMessage("Usage: /giveaway create");
                return true;
            }

            if (args.length >= 1) {
                if (args[0].equalsIgnoreCase("create")) {

                    if (!player.hasPermission("giveaways.duration") && args.length >= 2) {
                        message.sendNoPermissionDuration(player);
                        return false;
                    }

                    ItemStack prize = player.getInventory().getItemInMainHand();
                    player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));

                    if (!isAir(prize)) {
                        if (!player.hasPermission("giveaways.duration") || args.length < 2) {
                            gm.createGiveaway(player, prize, 60);
                        } else {
                            long duration = Long.parseLong(args[1]);
                            gm.createGiveaway(player, prize, duration);
                        }
                    } else {
                        message.sendNoItemInHand(player);
                    }

                    return true;
                }

                if (args[0].equalsIgnoreCase("forcestop")) {
                    gm.forceStopGiveaway(player);
                }
            }
        } else {
            message.sendNoPermission(player);
        }

        return true;
    }

    private boolean isAir(ItemStack itemStack) {
        return itemStack.getType() == Material.AIR;
    }
}
