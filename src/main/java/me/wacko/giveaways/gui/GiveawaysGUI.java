package me.wacko.giveaways.gui;

import me.wacko.giveaways.Giveaways;
import me.wacko.giveaways.model.Giveaway;
import me.wacko.giveaways.util.ItemStackUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class GiveawaysGUI extends AbstractGUI {

    private final Giveaways plugin;
    private int slotIndex = 0;

    public GiveawaysGUI(Giveaways plugin) {
        super(9*6, "Current Giveaways");
        this.plugin = plugin;

        List<Giveaway> activeGiveaways = plugin.getActiveGiveaways();

        for (Giveaway giveaway : activeGiveaways) {
            ItemStack prize = createItem(giveaway, plugin);
            setItem(slotIndex, prize, player ->{
                if (player != giveaway.getHost()) {
                    giveaway.addParticipant(player);
                    // todo: open giveawaysGUI when they click to participate in the giveaway
                } else {
                    player.sendMessage("Can't do that!");
                }
            });

            slotIndex++;

            if (slotIndex >= getInventory().getSize()) {
                break;
            }
        }
    }

    private ItemStack createItem(Giveaway giveaway, Giveaways plugin){
        List<String> lore = getLore(giveaway, plugin);
        ItemStack prize = ItemStackUtil.getItem(giveaway.getPrize().getType().toString(), giveaway.getPrize().getType(), 1, lore);

        return prize;
    }

    private static List<String> getLore(Giveaway giveaway, Giveaways plugin){
        List<String> lore = new ArrayList<>();

        List<Player> participants = giveaway.getParticipants();

        if (!(participants == null)) {
            lore.add("# of participants: " + participants.size());
        } else {
            lore.add("# of participants: 0");
        }

        //long duration = giveaway.getDuration();
        //Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(plugin, () -> lore.add("Ends in: " + duration), 0L, 20L);

        return lore;
    }

}
