package de.constellate.nitroapp.listeners;

import de.constellate.nitroapp.location.LocationManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        LocationManager.deleteLocation(player);
        event.setQuitMessage(ChatColor.RED + "- " + ChatColor.DARK_GRAY + "┃ " + ChatColor.GRAY + player.getName());
    }

}
