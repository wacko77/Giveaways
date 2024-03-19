package me.wacko.giveaways.model;

import me.wacko.giveaways.Giveaways;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.io.Serializable;
import java.util.*;

import static java.lang.Long.parseLong;

public class Giveaway {
    private ItemStack prize;
    private List<Player> participants = new ArrayList<>();
    private int id;
    private Player host;
    private long duration;
    private boolean active;
    private BukkitTask runnable;

    public Giveaway(ItemStack prize, int id, Player host, long duration) {
        this.prize = prize;
        this.id = id;
        this.host = host;
        this.duration = duration;
    }

    public Giveaway(ItemStack prize, int id, Player host, List<Player> participants) {
        this.prize = prize;
        this.id = id;
        this.host = host;
        this.participants = participants;
    }

    public void start() {

        active = true;

        Bukkit.broadcastMessage("Giveaway #" + id + " has started! Prize: " + prize.getType().toString());
    }

    public void end(){

        active = false;

        Bukkit.broadcastMessage("Giveaway #" + id + " has ended!");
    }

    public void addParticipant(Player player) {
        if (!participants.contains(player)) {
            participants.add(player);
        }
    }
    public void removeParticipant(Player player) {
        participants.remove(player);
    }
    public int getId() {return id;}
    public ItemStack getPrize() {return prize;}
    public Player getHost() {return host;}
    public List<Player> getParticipants() {return participants;}
    public boolean isActive() {return active;}
    public long getDuration() {return duration;}
    public void setDuration(long duration) {
        this.duration = duration;
    }
    public BukkitTask getRunnable() {return runnable;}
    public void setRunnable(BukkitTask runnable) {this.runnable = runnable;}

}






