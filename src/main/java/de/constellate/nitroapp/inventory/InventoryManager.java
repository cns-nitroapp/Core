package de.constellate.nitroapp.inventory;

import de.constellate.nitroapp.Main;
import de.constellate.nitroapp.commands.BalanceCommand;
import de.constellate.nitroapp.utils.TransactionLogger;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class InventoryManager {

    public boolean hasSpace(Player player) {

        if (player.getInventory().firstEmpty() == -1) {
            return false;
        } else {
            return true;
        }

    }

    public void buyItem(Player player, ItemStack itemStack, int cost, Gui gui, int amount) {

        int balance = BalanceCommand.getBalance(player.getUniqueId());
        int total = cost * amount;

        if (balance - cost < 0) {
            player.sendMessage(Main.getPrefix() + ChatColor.RED + "Insufficient funds");
            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
        } else if (!hasSpace(player)) {
            player.sendMessage(Main.getPrefix() + ChatColor.RED + "Your inventory is full");
            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
        } else {
            gui.getFiller().fill(ItemBuilder.from(Material.GREEN_STAINED_GLASS_PANE).setName(ChatColor.DARK_GRAY + "").asGuiItem());
            player.getInventory().addItem(itemStack);
            BalanceCommand.setBalance(player.getUniqueId(), balance - total);
            player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_DIAMOND, 1, 1);
            TransactionLogger.logTransaction(player.getName(), player.getUniqueId(), "Nitroapp", UUID.fromString("6e60018f-f59b-4148-b233-9055d3430051"), total);
            gui.getFiller().fill(ItemBuilder.from(Material.BLACK_STAINED_GLASS_PANE).setName(ChatColor.DARK_GRAY + "").asGuiItem());
        }

    }

}
