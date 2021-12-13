package de.constellate.nitroapp.listeners;

import de.constellate.nitroapp.Main;
import de.constellate.nitroapp.inventory.CategoryInventory;
import de.constellate.nitroapp.utils.Config;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.io.*;


public class InventoryListener implements Listener {

    // Basic-Shop
    @EventHandler
    private void onVillagerInteract(PlayerInteractEntityEvent event) {

        Player player = (Player) event.getPlayer();
        Config config = Main.getInstance().getConfiguration();
        ConfigurationSection sec = config.getConfig().getConfigurationSection("item.vendor_value");

        if (event.getRightClicked().getType() == EntityType.VILLAGER && !event.getPlayer().isSneaking()) {
            event.setCancelled(true);

            CategoryInventory categoryInventory = new CategoryInventory();
            categoryInventory.openCategories(player);

        }

    }

    @EventHandler
    private void onInventoryDrag(InventoryClickEvent event) {

        if (event.getView().getTitle().contains(Main.getGuiSuffix())) {
            event.setCancelled(true);
        }

    }

}
