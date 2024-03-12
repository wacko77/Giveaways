package me.wacko.giveaways.model;

import me.wacko.giveaways.Giveaways;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;

public class GiveawayManager {

    private final Giveaways plugin;
    private int nextId = 1;

    public GiveawayManager(Giveaways plugin) {
        this.plugin = plugin;
    }

    public void createGiveaway(Player host, ItemStack prize, long duration) {
        Giveaway giveaway = new Giveaway(prize, nextId++, host, duration);
        plugin.getActiveGiveaways().add(giveaway);

        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> end(giveaway), duration * 20);
        // save to flat file
    }

    public void createGiveawayNoDur(Player host, ItemStack prize) {
        Giveaway giveaway = new Giveaway(prize, nextId++, host);
        plugin.getActiveGiveaways().add(giveaway);

        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> end(giveaway), 20 * 60);

        // save to flat file
    }

    public Player chooseWinner(Giveaway giveaway) {
        List<Player> participants = giveaway.getParticipants();
        if(participants.isEmpty()){
            Player host = giveaway.getHost();
            host.getInventory().addItem(giveaway.getPrize());
        }
        Random rand = new Random();
        int index = rand.nextInt(participants.size());
        return participants.get(index);
    }

    public void end(Giveaway giveaway) {
        Player winner = chooseWinner(giveaway);
        if(winner != null){
            winner.sendMessage("You have won a giveaway containing: " + giveaway.getPrize().getType());
            winner.getInventory().addItem(giveaway.getPrize());
        }
        plugin.getActiveGiveaways().remove(giveaway);
    }

}
