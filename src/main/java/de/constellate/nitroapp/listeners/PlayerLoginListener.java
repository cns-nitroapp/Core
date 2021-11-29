package de.constellate.nitroapp.listeners;

import de.constellate.nitroapp.Main;
import de.constellate.nitroapp.maintenance.MaintenanceManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerLoginListener implements Listener {

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {

        Player player = event.getPlayer();
        MaintenanceManager maintenance = new MaintenanceManager();

        if(!player.isOp() && maintenance.getMaintenance()) {
            event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
            event.setKickMessage(Main.getPrefix() + ChatColor.RED + "Maintenance\n\n"  + "We are currently in maintenance. Please check again later.");
        }
        
    }

}
