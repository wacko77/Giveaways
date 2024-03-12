package me.wacko.giveaways.commands;

import me.wacko.giveaways.model.GiveawayManager;
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

        if(args.length == 0){
            player.sendMessage("Usage: /giveaway create");
            return true;
        }

        if(args.length >= 1){
            if(args[0].equalsIgnoreCase("create")) {

                player.sendMessage("Usage: /giveaway create");
                ItemStack prize = player.getInventory().getItemInMainHand();
                player.getInventory().remove(prize);
                gm.createGiveawayNoDur(player, prize);



                return true;

            } else if(args[0].equalsIgnoreCase("create") && player.hasPermission("giveaways.duration")) {

                player.sendMessage("Usage: /giveaway create <durationInSeconds>");
                ItemStack prize = player.getInventory().getItemInMainHand();
                player.getInventory().remove(prize);
                long duration = Long.parseLong(args[1]);
                long endTime = System.currentTimeMillis() + (duration * 1000);

                gm.createGiveaway(player, prize, endTime);

                return true;
            }
        }

        return true;
    }
}
