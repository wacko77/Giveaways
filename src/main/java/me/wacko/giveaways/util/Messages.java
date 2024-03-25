package me.wacko.giveaways.util;

import me.wacko.giveaways.Giveaways;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Messages {

    private final MessagesFile file;

    public Messages(Giveaways plugin) {this.file = plugin.getMessagesFile();}

    public void sendPlayerMessage(String message, Player p) {
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
    public void sendPlayerMessageBroadcast(String message) {
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public void sendStartBroadcastMessage() {
        String message = file.getMessagesConfig().getString("giveaway-start-broadcast");
        if (message != null) {
            sendPlayerMessageBroadcast(message);
        }
        file.saveMessagesConfig();
    }

    public void sendEndBroadcastMessage() {
        String message = file.getMessagesConfig().getString("giveaway-end-broadcast");
        if (message != null) {
            sendPlayerMessageBroadcast(message);
        }
        file.saveMessagesConfig();
    }

    public String sendGiveawayWin(Player p) {
        String message = file.getMessagesConfig().getString("giveaway-win");
        if (message != null) {
            sendPlayerMessage(message, p);
        }
        file.saveMessagesConfig();
        return message;
    }

    public String sendGiveawayEndNoParticipants(Player p) {
        String message = file.getMessagesConfig().getString("giveaway-end-no-participants");
        if (message != null) {
            sendPlayerMessage(message, p);
        }
        file.saveMessagesConfig();
        return message;
    }

    public String sendNoPermission(Player p) {
        String message = file.getMessagesConfig().getString("no-permission");
        if (message != null) {
            sendPlayerMessage(message, p);
        }
        file.saveMessagesConfig();
        return message;
    }

    public String sendNoPermissionDuration(Player p) {
        String message = file.getMessagesConfig().getString("no-permission-duration");
        if (message != null) {
            sendPlayerMessage(message, p);
        }
        file.saveMessagesConfig();
        return message;
    }

    public String sendNoItemInHand(Player p) {
        String message = file.getMessagesConfig().getString("no-item-in-hand-on-giveaway-create");
        if (message != null) {
            sendPlayerMessage(message, p);
        }
        file.saveMessagesConfig();
        return message;
    }

}
