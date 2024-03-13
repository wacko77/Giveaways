package me.wacko.giveaways.model;

import me.wacko.giveaways.Giveaways;
import me.wacko.giveaways.storage.FlatFile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GiveawayManager {

    private final Giveaways plugin;
    private final FlatFile file;
    private int nextId = 1;
    private final List<Giveaway> activeGiveaways;
    private final List<Player> hostsWithActiveGiveaways;

    public GiveawayManager(Giveaways plugin, List<Giveaway> activeGiveaways) {
        this.plugin = plugin;
        this.file = new FlatFile(plugin);
        this.activeGiveaways = activeGiveaways;
        this.hostsWithActiveGiveaways = new ArrayList<>();
    }

    public void createGiveaway(Player host, ItemStack prize, long duration) {
        if (hostsWithActiveGiveaways.contains(host)){
            host.sendMessage(String.format("%s%sYou cannot create multiple giveaways!", ChatColor.DARK_RED,  ChatColor.BOLD));
        }

        Giveaway giveaway = new Giveaway(prize, nextId++, host, duration);

        activeGiveaways.add(giveaway);
        file.saveGiveaways(activeGiveaways);

        giveaway.start();

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> end(giveaway), duration * 20);
    }

    public Player chooseWinner(Giveaway giveaway) {
        List<Player> participants = giveaway.getParticipants();
        Random rand = new Random();
        int index = rand.nextInt(participants.size());
        return participants.get(index);
    }

    public void end(Giveaway giveaway) {
        if(giveaway.getParticipants().isEmpty()) {
            Player host = giveaway.getHost();
            host.getInventory().addItem(giveaway.getPrize());
            giveaway.getHost().sendMessage("No one entered your giveaway!");

            giveaway.end();
            file.removeGiveaway(giveaway);
            activeGiveaways.remove(giveaway);

            return;
        }
        Player winner = chooseWinner(giveaway);
        if(winner != null){
            winner.sendMessage("You have won a giveaway containing: " + giveaway.getPrize().getType());
            winner.getInventory().addItem(giveaway.getPrize());
            giveaway.end();
            file.removeGiveaway(giveaway);
            activeGiveaways.remove(giveaway);
        }
    }

}
