package de.constellate.nitroapp.inventory;

import de.constellate.nitroapp.Main;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class CategoryInventory {

    public void openCategories(Player player) {

        Gui categories = Gui.gui()
                .title(Component.text("Villager" + Main.getGuiSuffix()))
                .rows(3)
                .create();

        //categories.getFiller().fill(ItemBuilder.from(Material.BLACK_STAINED_GLASS_PANE).setName(ChatColor.DARK_GRAY + "").asGuiItem());

        VendorInventory vendorInventory = new VendorInventory();

        categories.setItem(2, 2, ItemBuilder.from(Material.DIAMOND_SWORD).setName(ChatColor.GREEN + "Weapons").asGuiItem(e -> vendorInventory.openVendor(player, "weapons")));
        categories.setItem(2, 3, ItemBuilder.from(Material.NETHERITE_CHESTPLATE).setName(ChatColor.GREEN + "Armor").asGuiItem(e -> vendorInventory.openVendor(player, "armor")));
        categories.setItem(2, 4, ItemBuilder.from(Material.IRON_PICKAXE).setName(ChatColor.GREEN + "Tools").asGuiItem(e -> vendorInventory.openVendor(player, "tools")));
        categories.setItem(2, 6, ItemBuilder.from(Material.DIAMOND).setName(ChatColor.GREEN + "Minerals").asGuiItem(e -> vendorInventory.openVendor(player, "minerals")));
        categories.setItem(2, 7, ItemBuilder.from(Material.SPRUCE_LOG).setName(ChatColor.GREEN + "Building Blocks").asGuiItem(e -> vendorInventory.openVendor(player, "building")));
        categories.setItem(2, 8, ItemBuilder.from(Material.COOKED_BEEF).setName(ChatColor.GREEN + "Food").asGuiItem(e -> vendorInventory.openVendor(player, "food")));

        categories.open(player);

    }

}
