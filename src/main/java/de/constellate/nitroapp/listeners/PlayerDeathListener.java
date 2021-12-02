package de.constellate.nitroapp.listeners;

import de.constellate.nitroapp.Main;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerRespawnEvent event) {

        Player player = event.getPlayer();
        Location location = player.getLocation();

        player.sendMessage(Main.getPrefix() + "Your location of death: " + ChatColor.GREEN + location.getBlockX() + " " + location.getBlockY() + " " + location.getBlockZ());
        player.getInventory().addItem(ItemBuilder.from(Material.FIREWORK_ROCKET).setName(ChatColor.GREEN + "Your location of death").setLore(ChatColor.GRAY + "X: " + location.getBlockX(), ChatColor.GRAY + "Y: " + location.getBlockY(), ChatColor.GRAY + "Z: " + location.getBlockZ()).build());

    }

}
