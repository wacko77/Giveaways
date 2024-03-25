package me.wacko.giveaways.manager;

import me.wacko.giveaways.Giveaways;
import me.wacko.giveaways.model.Giveaway;
import me.wacko.giveaways.util.FlatFile;
import me.wacko.giveaways.util.Messages;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class GiveawayManager {
    private final Giveaways plugin;
    private final FlatFile file;
    private int nextId = 1;
    private final List<Giveaway> activeGiveaways;
    private final Map<Integer, Giveaway> giveaways;
    private final List<Player> hostsWithActiveGiveaways;

    public GiveawayManager(Giveaways plugin) {
        this.plugin = plugin;
        this.file = new FlatFile(plugin);
        this.activeGiveaways = new ArrayList<>();
        this.giveaways = new HashMap<>();
        this.hostsWithActiveGiveaways = new ArrayList<>();
    }

    public void createGiveaway(Player host, ItemStack prize, long duration) {
        if (hostsWithActiveGiveaways.contains(host)){
            host.sendMessage(String.format("%s%sYou cannot create multiple giveaways!", ChatColor.DARK_RED,  ChatColor.BOLD));
            host.getInventory().addItem(prize);
            return;
        }

        Giveaway giveaway = new Giveaway(prize, nextId++, host, duration, plugin);

        giveaways.put(nextId, giveaway);

        activeGiveaways.add(giveaway);
        file.saveGiveaways(activeGiveaways);
        hostsWithActiveGiveaways.add(host);

        giveaway.start();

        long ticks = duration * 20;

        new BukkitRunnable() {
            @Override
            public void run() {
                if(!giveaway.isActive() || !activeGiveaways.contains(giveaway)) {cancel(); return;}

                end(giveaway);
            }
        }.runTaskLaterAsynchronously(plugin, ticks);

        new BukkitRunnable() {
            long remainingTicks = ticks;

            @Override
            public void run() {
                if(!giveaway.isActive() || !activeGiveaways.contains(giveaway)) {cancel(); return;}

                if (remainingTicks <= 0) {
                    cancel();
                } else {
                    remainingTicks--;

                    long remainingSeconds = remainingTicks / 20;

                    giveaway.setDuration(remainingSeconds);
                }

            }
        }.runTaskTimerAsynchronously(plugin, 0, 1);
    }

    private Player chooseWinner(Giveaway giveaway) {
        List<Player> participants = giveaway.getParticipants();
        Random rand = new Random();
        int index = rand.nextInt(participants.size());
        return participants.get(index);
    }

    private void end(Giveaway giveaway) {
        if(giveaway.getParticipants().isEmpty()) {
            Messages message = new Messages(plugin);
            Player host = giveaway.getHost();
            host.getInventory().addItem(giveaway.getPrize());
            host.sendMessage(message.sendGiveawayEndNoParticipants(host));

            stopGiveaway(giveaway);

            return;
        }
        Player winner = chooseWinner(giveaway);
        if(winner != null){
            Messages message = new Messages(plugin);
            winner.sendMessage(message.sendGiveawayWin(winner));
            winner.getInventory().addItem(giveaway.getPrize());
            stopGiveaway(giveaway);
        }
    }

    public void forceStopGiveaway(Player player) {
        for (Map.Entry<Integer, Giveaway> entry : giveaways.entrySet()) {
            Giveaway giveaway = entry.getValue();

            if (player.getUniqueId() == giveaway.getHost().getUniqueId() && hostsWithActiveGiveaways.contains(player)) {
                stopGiveaway(giveaway);
                giveaway.getHost().getInventory().addItem(giveaway.getPrize());
            }
        }
    }

    private void stopGiveaway(Giveaway giveaway) {
        giveaways.remove(giveaway.getId());
        giveaway.end();
        file.removeGiveaway(giveaway);
        activeGiveaways.remove(giveaway);
        hostsWithActiveGiveaways.remove(giveaway.getHost());
        file.saveGiveaways(activeGiveaways);
    }

    public List<Giveaway> getActiveGiveaways() {
        return activeGiveaways;
    }
}
