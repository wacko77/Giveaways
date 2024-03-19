package me.wacko.giveaways.gui;

import me.wacko.giveaways.Giveaways;
import me.wacko.giveaways.model.Giveaway;
import me.wacko.giveaways.util.ItemStackUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.*;

public class GiveawaysGUI extends AbstractGUI {

    public GiveawaysGUI(Giveaways plugin) {
        super(9*6, "Current Giveaways");
        int slotIndex = 0;

        List<Giveaway> activeGiveaways = plugin.getActiveGiveaways();

        if (activeGiveaways != null) {
            for (Giveaway giveaway : activeGiveaways) {
                ItemStack prize = createItem(giveaway, plugin);
                setItem(slotIndex++, prize, player -> {
                    if (player != giveaway.getHost()) {
                        giveaway.addParticipant(player);
                        // todo: open giveawaysGUI when they click to participate in the giveaway
                    } else {
                        player.sendMessage("Can't do that!");
                    }
                });

                if (slotIndex >= getInventory().getSize()) {
                    break;
                }
            }
        } else {
            ItemStack item = ItemStackUtil.getItem(ChatColor.GREEN + "No Active Giveaways!", Material.BEDROCK, 1, Collections.singletonList(ChatColor.GRAY + "Type /giveaway create to make a giveaway!"));
            setItem(1, item);
        }
    }

    private ItemStack createItem(Giveaway giveaway, Giveaways plugin){
        List<String> lore = getLore(giveaway, plugin);
        ItemStack prize = ItemStackUtil.getItem(giveaway.getPrize().getType() + " "  + "x" + giveaway.getPrize().getAmount(), giveaway.getPrize().getType(), 1, lore);

        return prize;
    }

    private static List<String> getLore(Giveaway giveaway, Giveaways plugin){
        List<String> lore = new ArrayList<>();

        List<Player> participants = giveaway.getParticipants();
        long duration = giveaway.getDuration();
        Player host = giveaway.getHost();

        lore.add("");
        lore.add(ChatColor.GOLD + "Host: " + ChatColor.GRAY + host.getName());
        lore.add("");

        if (participants != null) {
            lore.add(ChatColor.GOLD + "Participants in Giveaway: "  + ChatColor.GRAY + participants.size());
        } else {
            lore.add(ChatColor.GOLD + "Participants in Giveaway: 0");
        }

        lore.add("");
        lore.add(ChatColor.GOLD + "Ends in: "  + ChatColor.GRAY +  duration + "s");

        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    if (onlinePlayer.getOpenInventory().getTitle().equals("Current Giveaways")) {
                        lore.set(2, ChatColor.GOLD + "Ends in: "  + ChatColor.GRAY +  duration + "s");
                    }
                }

            }
        }.runTaskTimer(plugin, 0, 20);

        return lore;
    }

}
