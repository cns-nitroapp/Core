package de.constellate.nitroapp.inventory;

import de.constellate.nitroapp.Main;
import de.constellate.nitroapp.utils.StringFormatter;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SelectionInventory {

    public void openSelection(Player player, String item, int price) {

        Gui selection = Gui.gui()
                .title(Component.text("Villager" + Main.getGuiSuffix()))
                .rows(4)
                .create();

        String name = StringFormatter.capitalizeWord(item);

        CategoryInventory categoryInventory = new CategoryInventory();

        selection.setItem(2, 2, getItem(player, item, name, price, 1, selection));
        selection.setItem(2, 5, getItem(player, item, name, price, 32, selection));
        selection.setItem(2, 8, getItem(player, item, name, price, 64, selection));
        selection.setItem(4, 5, ItemBuilder.from(Material.HEART_OF_THE_SEA).setName(ChatColor.GREEN + "Back").setLore(ChatColor.GRAY + "Go back to the category selection").asGuiItem(e -> categoryInventory.openCategories(player) ));

        selection.open(player);

    }

    private GuiItem getItem(Player player, String item, String name, int price, int amount, Gui gui) {
        InventoryManager inventoryManager = new InventoryManager();
        return ItemBuilder.from(Material.getMaterial(item)).setName(ChatColor.GREEN + name + ChatColor.GRAY + " x" + amount).setLore(ChatColor.GOLD + Integer.toString(price*amount) + " ⛃").setAmount(amount).glow().asGuiItem(e -> inventoryManager.buyItem(player, new ItemStack(Material.getMaterial(item), amount), price, gui, amount));
    }

}
