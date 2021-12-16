package de.constellate.nitroapp.inventory;

import de.constellate.nitroapp.Main;
import de.constellate.nitroapp.utils.Config;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class VendorInventory {

    public void openVendor(Player player, String category) {

        Config config = Main.getInstance().getConfiguration();
        ConfigurationSection sec = config.getConfig().getConfigurationSection("item.vendor_value");

        PaginatedGui gui = Gui.paginated()
                .title(Component.text("Villager" + Main.getGuiSuffix()))
                .rows(5)
                .pageSize(36)
                .create();

        CategoryInventory categoryInventory = new CategoryInventory();

        // Previous item
        gui.setItem(5, 3, ItemBuilder.from(Material.RED_DYE).setName(ChatColor.GREEN + "Previous").setLore(ChatColor.GRAY + "Go back to the previous page").asGuiItem(e -> gui.previous()));

        // Back to selection
        gui.setItem(5, 5, ItemBuilder.from(Material.HEART_OF_THE_SEA).setName(ChatColor.GREEN + "Back").setLore(ChatColor.GRAY + "Go back to the category selection").asGuiItem(e -> categoryInventory.openCategories(player) ));

        // Next item
        gui.setItem(5, 7, ItemBuilder.from(Material.GREEN_DYE).setName(ChatColor.GREEN + "Next").setLore(ChatColor.GRAY + "Skip ahead to the next page").asGuiItem(e -> gui.next()) );

        for (int i : new int[]{1, 2, 4, 6, 8, 9}) {
            gui.setItem(5, i, ItemBuilder.from(Material.GRAY_STAINED_GLASS_PANE).setName(" ").asGuiItem());
        }

        for(String key : sec.getKeys(false)){
            if (config.getConfig().getString("item.vendor_value." + key + ".category").equals(category)) {
                String name = config.getConfig().getString("item.vendor_value." + key + ".name");
                int price = config.getConfig().getInt("item.vendor_value." + key + ".price");

                //gui.addItem(new GuiItem(Material.getMaterial(key)));

                SelectionInventory selection = new SelectionInventory();

                GuiItem guiItem = ItemBuilder.from(Material.getMaterial(key)).setName(ChatColor.GREEN + name).setLore(ChatColor.GOLD + Integer.toString(price) + " ⛃").asGuiItem(e -> selection.openSelection(player, key, price));
                gui.addItem(guiItem);
            }
        }

        gui.open(player);
    }

}
