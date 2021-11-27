package de.constellate.nitroapp.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();

        event.setFormat("%1$s" + ChatColor.DARK_GRAY + " » " + ChatColor.GRAY + "%2$s");
        event.setMessage(ChatColor.translateAlternateColorCodes('&', event.getMessage()));

    }

}
