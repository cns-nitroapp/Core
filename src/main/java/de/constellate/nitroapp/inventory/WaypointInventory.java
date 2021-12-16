package de.constellate.nitroapp.inventory;

import de.constellate.nitroapp.Main;
import de.constellate.nitroapp.commands.BalanceCommand;
import de.constellate.nitroapp.utils.Config;
import de.constellate.nitroapp.utils.StringFormatter;
import de.constellate.nitroapp.utils.TransactionLogger;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

public class WaypointInventory {

    public void openWaypointIndex(Player player) {

        Config config = Main.getInstance().getConfiguration();
        ConfigurationSection sec = config.getConfig().getConfigurationSection("waypoints.waypoint");

        PaginatedGui gui = Gui.paginated()
                .title(Component.text("Waypoints" + Main.getGuiSuffix()))
                .rows(5)
                .create();

        gui.setItem(5, 3, ItemBuilder.from(Material.RED_DYE).setName(ChatColor.GREEN + "Previous").setLore(ChatColor.GRAY + "Go back to the previous page").asGuiItem(e -> gui.previous()));
        gui.setItem(5, 7, ItemBuilder.from(Material.GREEN_DYE).setName(ChatColor.GREEN + "Next").setLore(ChatColor.GRAY + "Skip ahead to the next page").asGuiItem(e -> gui.next()) );

        for (int i : new int[]{1, 2, 4, 5, 6, 8, 9}) {
            gui.setItem(5, i, ItemBuilder.from(Material.GRAY_STAINED_GLASS_PANE).setName(" ").asGuiItem());
        }

        for (String key : sec.getKeys(false)) {

            String name = StringFormatter.capitalizeWord(key);
            String x = config.getConfig().getString("waypoints.waypoint." + key + ".x");
            String y = config.getConfig().getString("waypoints.waypoint." + key + ".y");
            String z = config.getConfig().getString("waypoints.waypoint." + key + ".z");
            String world = config.getConfig().getString("waypoints.waypoint." + key + ".world");
            String owner = config.getConfig().getString("waypoints.owner." + key);
            boolean isPublic = config.getConfig().getBoolean("waypoints.waypoint." + key + ".visibility");

            if (!config.getConfig().contains("waypoints.waypoint." + key + ".visibility")) {
                GuiItem guiItem = ItemBuilder.from(getItem(world)).setName(ChatColor.BLUE + name + ChatColor.DARK_GRAY + ChatColor.DARK_GRAY).setLore(ChatColor.GRAY + "Click to open menu").asGuiItem(e -> openWaypointMenu(player, key));
                gui.addItem(guiItem);
            } else if (isPublic) {
                GuiItem guiItem = ItemBuilder.from(getItem(world)).setName(ChatColor.BLUE + name + ChatColor.DARK_GRAY + ChatColor.DARK_GRAY).setLore(ChatColor.GRAY + "Click to open menu").asGuiItem(e -> openWaypointMenu(player, key));
                gui.addItem(guiItem);
            } else if (!isPublic && owner.equals(player.getUniqueId().toString()) || player.isOp()) {
                GuiItem guiItem = ItemBuilder.from(getItem(world)).setName(ChatColor.BLUE + name + ChatColor.DARK_GRAY + " ┃ " + ChatColor.RED + "Private" + ChatColor.DARK_GRAY).setLore(ChatColor.GRAY + "Click to open menu").glow().asGuiItem(e -> openWaypointMenu(player, key));
                gui.addItem(guiItem);
            }

            //GuiItem guiItem = ItemBuilder.from(getItem(world)).setName(ChatColor.GREEN + name).setLore(ChatColor.GRAY + "Click to teleport to " + ChatColor.GREEN + name, " ", ChatColor.GRAY + "X: " + x, ChatColor.GRAY + "Y: " + y, ChatColor.GRAY + "Z: " + z).glow().asGuiItem(e -> openWaypointMenu(player, key));

        }

        gui.open(player);

    }

    public void openWaypointMenu(Player player, String waypoint) {

        Config config = Main.getInstance().getConfiguration();
        ConfigurationSection sec = config.getConfig().getConfigurationSection("waypoints.waypoint." + waypoint);

        Gui gui = Gui.gui()
                .title(Component.text(StringFormatter.capitalizeWord(waypoint) + Main.getGuiSuffix()))
                .rows(4)
                .create();

        for (int i : new int[]{1, 2, 3, 4, 6, 7, 8, 9}) {
            gui.setItem(4, i, ItemBuilder.from(Material.GRAY_STAINED_GLASS_PANE).setName(" ").asGuiItem());
        }

        gui.setItem(4, 5, ItemBuilder.from(Material.HEART_OF_THE_SEA).setName(ChatColor.GREEN + "Back").setLore(ChatColor.GRAY + "Go back to the waypoint index").asGuiItem(e -> openWaypointIndex(player) ));

        gui.setItem(2, 5, ItemBuilder.from(Material.ENDER_PEARL).setName(ChatColor.DARK_PURPLE + "Teleport").setLore(ChatColor.GOLD + "100 ⛃").glow().asGuiItem(e -> player.performCommand("waypoint " + waypoint)));

        if (Objects.equals(config.getConfig().getString("waypoints.owner." + waypoint), player.getUniqueId().toString()) || player.isOp()) {
            gui.setItem(2, 7, ItemBuilder.from(Material.TNT).setName(ChatColor.RED + "Delete").setLore(ChatColor.GRAY + "Remove this waypoint", "", ChatColor.RED + "This action is irreversible!").asGuiItem( e -> deleteWaypoint(player, waypoint, gui) ));

            if (!config.getConfig().contains("waypoints.waypoint." + waypoint + ".visibility")) {
                gui.setItem(2, 3, ItemBuilder.from(Material.LIME_DYE).setName(ChatColor.BLUE + "Visibility").setLore(ChatColor.GRAY + "Make this waypoint " + ChatColor.RED + "private.", "", ChatColor.GOLD + "500.000 ⛃").asGuiItem(e -> setVisibility(player, waypoint, false, gui)));
            } else if (config.getConfig().getBoolean("waypoints.waypoint." + waypoint + ".visibility")) {
                gui.setItem(2, 3, ItemBuilder.from(Material.LIME_DYE).setName(ChatColor.BLUE + "Visibility").setLore(ChatColor.GRAY + "Make this waypoint " + ChatColor.RED + "private.", "", ChatColor.GOLD + "500.000 ⛃").asGuiItem(e -> setVisibility(player, waypoint, false, gui)));
            } else if (!config.getConfig().getBoolean("waypoints.waypoint." + waypoint + ".visibility")) {
                gui.setItem(2, 3, ItemBuilder.from(Material.RED_DYE).setName(ChatColor.BLUE + "Visibility").setLore(ChatColor.GRAY + "Make this waypoint " + ChatColor.GREEN + "public.", "", ChatColor.GOLD + "500.000 ⛃").asGuiItem(e -> setVisibility(player, waypoint, true, gui)));
            }

        }

        gui.open(player);

    }

    private Material getItem(String world) {
        switch (world) {
            case "world":
                return Material.GRASS_BLOCK;
            case "world_nether":
                return Material.NETHERRACK;
            case "world_the_end":
                return Material.END_STONE;
            default:
                return Material.COBBLESTONE;
        }
    }

    private void setVisibility(Player player, String waypoint, boolean visibility, Gui gui) {
        Config config = Main.getInstance().getConfiguration();

        int balance = BalanceCommand.getBalance(player.getUniqueId());

        if (balance >= 500000) {
            BalanceCommand.setBalance(player.getUniqueId(), balance - 500000);
            TransactionLogger.logTransaction(player.getName(), player.getUniqueId(), "Nitroapp", UUID.fromString("6e60018f-f59b-4148-b233-9055d3430051"), 500000);
            player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_DIAMOND, 1, 1);

            if (visibility) {
                player.sendMessage(Main.getPrefix() + ChatColor.GRAY + "Successfully changed the visibility of this waypoint to " + ChatColor.GREEN + "public.");
            } else {
                player.sendMessage(Main.getPrefix() + ChatColor.GRAY + "Successfully changed the visibility of this waypoint to " + ChatColor.RED + "private.");
            }

            config.getConfig().set("waypoints.waypoint." + waypoint + ".visibility", visibility);
            config.save();
        } else {
            player.sendMessage(Main.getPrefix() + ChatColor.RED + "Insufficient funds.");
            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
        }

        gui.close(player);

    }

    private void deleteWaypoint(Player player, String waypoint, Gui gui) {
        Config config = Main.getInstance().getConfiguration();
        config.getConfig().set("waypoints.waypoint." + waypoint, null);
        player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_DIAMOND, 1, 1);
        gui.close(player);
        player.sendMessage(Main.getPrefix() + ChatColor.RED + "Successfully deleted this waypoint.");
    }

}
