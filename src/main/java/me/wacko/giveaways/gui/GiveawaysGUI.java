package me.wacko.giveaways.gui;

import me.wacko.giveaways.Giveaways;
import me.wacko.giveaways.model.Giveaway;
import me.wacko.giveaways.util.ItemStackUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
            ItemStack prize = createItem(giveaway);
            setItem(slotIndex, prize, player ->{
                if (player != giveaway.getHost()) {
                    giveaway.addParticipant(player);
                    open(player);
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

    private ItemStack createItem(Giveaway giveaway){
        List<String> lore = getLore(giveaway);
        ItemStack prize = ItemStackUtil.getItem(giveaway.getPrize().getType().toString(), giveaway.getPrize().getType(), 1, lore);

        return prize;
    }

    private static List<String> getLore(Giveaway giveaway){
        List<String> lore = new ArrayList<>();

        List<Player> participants = giveaway.getParticipants();

        if (!(participants == null)) {
            lore.add("# of participants: " + participants.size());
        } else {
            lore.add("# of participants: 0");
        }

        long remainingTimeMillis = giveaway.getDuration() - System.currentTimeMillis();
        String remainingTimeFormatted = formatDuration(remainingTimeMillis);
        lore.add("Ends in: " + remainingTimeFormatted);

        return lore;
    }

    private static String formatDuration(long millis) {
        if (millis < 0) {
            return "Expired";
        }
        long days = TimeUnit.MILLISECONDS.toDays(millis);
        millis -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
        return String.format("%d days %d hours %d minutes %d seconds", days, hours, minutes, seconds);
    }

}
