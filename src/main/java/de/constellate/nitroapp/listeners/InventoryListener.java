package de.constellate.nitroapp.listeners;

import de.constellate.nitroapp.Main;
import de.constellate.nitroapp.backpack.Backpack;
import de.constellate.nitroapp.utils.Config;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class InventoryListener implements Listener {

    // Basic-Shop
    @EventHandler
    private void onVillagerInteract(PlayerInteractEntityEvent event) throws IOException {

        Player p = (Player) event.getPlayer();
        Config config = Main.getInstance().getConfiguration();
        ConfigurationSection sec = config.getConfig().getConfigurationSection("item.vendor_value");

        if (event.getRightClicked().getType() == EntityType.VILLAGER) {
            event.setCancelled(true);

            PaginatedGui gui = Gui.paginated()
                    .title(Component.text("Villager" + Main.getGuiSuffix()))
                    .rows(6)
                    .pageSize(45)
                    .create();

            Gui selection = Gui.gui()
                    .title(Component.text("Villager" + Main.getGuiSuffix()))
                    .rows(3)
                    .create();

            // Previous item
            gui.setItem(6, 3, ItemBuilder.from(Material.PAPER).setName(ChatColor.GREEN + "Previous").setLore(ChatColor.GRAY + "Go back to the previous page").asGuiItem(e -> gui.previous()));
// Next item
            gui.setItem(6, 7, ItemBuilder.from(Material.PAPER).setName(ChatColor.GREEN + "Next").setLore(ChatColor.GRAY + "Skip ahead to the next page").asGuiItem(e -> gui.next()) );

            for(String key : sec.getKeys(false)){
                String name = config.getConfig().getString("item.vendor_value." + key + ".name");
                int price = config.getConfig().getInt("item.vendor_value." + key + ".price");

                System.out.println(key);

                //gui.addItem(new GuiItem(Material.getMaterial(key)));

                GuiItem guiItem = ItemBuilder.from(Material.getMaterial(key)).setName(ChatColor.GREEN + name).setLore(ChatColor.GOLD + Integer.toString(price) + " ⛃").asGuiItem(e -> selection.open(p));
                gui.addItem(guiItem);

                //gui.addItem(new GuiItem(ItemBuilder.from(Material.getMaterial(key)).setName(ChatColor.GREEN + name).setLore(ChatColor.GOLD + Integer.toString(price) + " ⛃").glow().build()));
            }

            gui.open(p);

        }

    }

    @EventHandler
    private void onInventoryDrag(InventoryClickEvent event) {

        if (event.getView().getTitle().contains(Main.getGuiSuffix())) {
            event.setCancelled(true);
        }

    }

}
