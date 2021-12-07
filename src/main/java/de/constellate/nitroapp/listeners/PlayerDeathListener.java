package de.constellate.nitroapp.listeners;

import de.constellate.nitroapp.Main;
import de.constellate.nitroapp.location.LocationManager;
import de.constellate.nitroapp.utils.TimeManager;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {

        Player player = event.getPlayer();
        Location location = player.getLocation();

        player.sendMessage(Main.getPrefix() + "Your location of death: " + ChatColor.GREEN + location.getBlockX() + " " + location.getBlockY() + " " + location.getBlockZ());
        player.getInventory().addItem(ItemBuilder.from(Material.FIREWORK_ROCKET).setName(ChatColor.GREEN + "Your location of death").setLore(ChatColor.GRAY + "X: " + location.getBlockX(), ChatColor.GRAY + "Y: " + location.getBlockY(), ChatColor.GRAY + "Z: " + location.getBlockZ()).build());

    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {

        Player player = event.getEntity();

        event.setDeathMessage(ChatColor.RED + "†" + ChatColor.DARK_GRAY + " ┃ " + ChatColor.GRAY + player.getName());
        LocationManager.deleteLocation(player);

    }

    @EventHandler
    public void PlayerDamageListener(EntityDamageEvent event) //Listens to EntityDamageEvent
    {
        if (((event.getDamage() - event.getFinalDamage()) <= 0) //Checks if the entity will die and if entity is player
                && event.getEntity() instanceof Player) {

            World world = event.getEntity().getWorld();

            if (TimeManager.isWeekend()) {
                world.setGameRule(GameRule.KEEP_INVENTORY, true);
            } else {
                world.setGameRule(GameRule.KEEP_INVENTORY, false);
            }
        }
    }

}
