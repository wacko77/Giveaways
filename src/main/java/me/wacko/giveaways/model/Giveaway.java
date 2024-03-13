package me.wacko.giveaways.model;

import me.wacko.giveaways.Giveaways;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;
import java.util.*;

import static java.lang.Long.parseLong;

public class Giveaway implements Serializable {
    private static final long serialVersionUID = 1L;

    private ItemStack prize;
    private List<Player> participants = new ArrayList<>();
    private int id;
    private Player host;
    private long duration;
    private boolean active;
    private boolean ended;

    private AsyncPlayerChatEvent event;
    private Giveaways plugin;

    public Giveaway(ItemStack prize, int id, Player host, long duration) {
        this.prize = prize;
        this.id = id;
        this.host = host;
        this.duration = duration;
    }

    public void start() {

        if(isActive()) return;
        if(isEnded()) return;
        if(participants.isEmpty()) return;

        active = true;

        Bukkit.broadcastMessage("Giveaway #" + id + " has started! Prize: " + prize.getType().toString());
    }

    public void end(){
        if (!isActive() || isEnded()) return;

        active = false;
        ended = true;

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
    public boolean isEnded() {return ended;}
    public long getDuration() {return duration;}
    public void setDuration(long duration) {
        this.duration = duration;
    }

}






