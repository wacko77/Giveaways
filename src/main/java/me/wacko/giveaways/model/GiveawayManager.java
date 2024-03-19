package me.wacko.giveaways.model;

import me.wacko.giveaways.Giveaways;
import me.wacko.giveaways.storage.FlatFile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

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
            return;
        }

        Giveaway giveaway = new Giveaway(prize, nextId++, host, duration);

        activeGiveaways.add(giveaway);
        file.saveGiveaways(activeGiveaways);
        hostsWithActiveGiveaways.add(host);

        giveaway.start();

        long ticks = duration * 20;
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> end(giveaway), duration * 20);

        new BukkitRunnable() {
            long remainingTicks = ticks;

            @Override
            public void run() {
                if (remainingTicks <= 0) {
                    cancel();
                } else {
                    remainingTicks--;

                    long remainingSeconds = remainingTicks / 20;

                    giveaway.setDuration(remainingSeconds);
                }
            }
        }.runTaskTimer(plugin, 0, 1);
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
            giveaway.getHost().sendMessage(String.format("%s%sNo one entered your giveaway!", ChatColor.AQUA,  ChatColor.BOLD));

            giveaway.end();
            file.removeGiveaway(giveaway);
            activeGiveaways.remove(giveaway);
            hostsWithActiveGiveaways.remove(host);

            return;
        }
        Player winner = chooseWinner(giveaway);
        if(winner != null){
            winner.sendMessage("You have won a giveaway containing: " + giveaway.getPrize().getType());
            winner.getInventory().addItem(giveaway.getPrize());
            giveaway.end();
            file.removeGiveaway(giveaway);
            activeGiveaways.remove(giveaway);
            hostsWithActiveGiveaways.remove(giveaway.getHost());
        }
    }

    public void forceStop(Giveaway giveaway) {

        giveaway.getHost().sendMessage("Giveaway stopped!");

        giveaway.getHost().getInventory().addItem(giveaway.getPrize());

        file.removeGiveaway(giveaway);
        activeGiveaways.remove(giveaway);
        hostsWithActiveGiveaways.remove(giveaway.getHost());
        giveaway.end();
    }

}
