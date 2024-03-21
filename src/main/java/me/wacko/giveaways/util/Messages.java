package me.wacko.giveaways.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Messages {

    private MessagesFile file;

    public Messages(MessagesFile file) {
        this.file = file;
    }

    public void sendPlayerMessage(String message) {
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public void sendStartBroadcastMessage() {
        String message = file.getMessagesConfig().getString("giveaway-start-broadcast");
        if (message != null) {
            sendPlayerMessage(message);
        }
        file.saveMessagesConfig();
    }

    public void sendEndBroacastMessage() {
        String message = file.getMessagesConfig().getString("giveaway-end-broadcast");
        if (message != null) {
            sendPlayerMessage(message);
        }
        file.saveMessagesConfig();
    }

    public void sendFirstLine() {
        String message = file.getMessagesConfig().getString("first-line");
        if (message != null) {
            sendPlayerMessage(message);
        }
        file.saveMessagesConfig();
    }

    public void sendSecondLine() {
        String message = file.getMessagesConfig().getString("second-line");
        if (message != null) {
            sendPlayerMessage(message);
        }
        file.saveMessagesConfig();
    }

    public void sendThirdLine() {
        String message = file.getMessagesConfig().getString("third-line");
        if (message != null) {
            sendPlayerMessage(message);
        }
        file.saveMessagesConfig();
    }

    public void sendFourthLine() {
        String message = file.getMessagesConfig().getString("fourth-line");
        if (message != null) {
            sendPlayerMessage(message);
        }
        file.saveMessagesConfig();
    }

    public void sendGiveawayWin() {
        String message = file.getMessagesConfig().getString("giveaway-win");
        if (message != null) {
            sendPlayerMessage(message);
        }
        file.saveMessagesConfig();
    }

    public void sendGiveawayEndNoParticipants() {
        String message = file.getMessagesConfig().getString("giveaway-end-no-participants");
        if (message != null) {
            sendPlayerMessage(message);
        }
        file.saveMessagesConfig();
    }

    public void sendNoPermission() {
        String message = file.getMessagesConfig().getString("no-permission");
        if (message != null) {
            sendPlayerMessage(message);
        }
        file.saveMessagesConfig();
    }



}
