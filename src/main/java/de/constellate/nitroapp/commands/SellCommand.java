package de.constellate.nitroapp.commands;

import de.constellate.nitroapp.Main;
import de.constellate.nitroapp.utils.Config;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SellCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Config config = Main.getInstance().getConfiguration();
        Player player = (Player) sender;

        String item = player.getInventory().getItemInMainHand().getType().name();
        int amount = player.getInventory().getItemInMainHand().getAmount();

        if (config.getConfig().contains("item.value." + item)) {

            int value = config.getConfig().getInt("item.value." + item);
            int total = value * amount;

            if (!(args.length == 0)) {

                if (args[1].equals("-confirm")) {

                    if (args[0].equals(item)) {
                        player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                        BalanceCommand.setBalance(player.getUniqueId(), BalanceCommand.getBalance(player.getUniqueId()) + total);
                        player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_DIAMOND, 1, 1);
                        sender.sendMessage(Main.getPrefix() + "Sold " + ChatColor.GREEN + amount + " " + item + ChatColor.GRAY + " for " + ChatColor.GOLD + total + " ⛃");
                    } else {
                        sender.sendMessage(Main.getPrefix() + "Please hold the item you want to sell in your hand.");
                    }

                } else {

                    sendConfirmation(player, item, total, amount);

                }
                return true;
            } else {
                sendConfirmation(player, item, total, amount);
            }

        } else {
            sender.sendMessage(Main.getPrefix() + "Item is not sellable.");
        }

        return true;
    }

    private void sendConfirmation(Player player, String item, Integer total, Integer amount) {
        TextComponent component = new TextComponent(Main.getPrefix() + ChatColor.GRAY + "Sell " + ChatColor.GREEN + amount + " " + item + ChatColor.GRAY + " for " + ChatColor.GOLD + total + " ⛃" + ChatColor.GRAY + " → " + ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "Confirm" + ChatColor.DARK_GRAY + ChatColor.DARK_GRAY + "]");
        component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/sell " + item + " -confirm"));
        component.setHoverEvent(new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.DARK_GRAY + "→ " +ChatColor.GREEN + "Click to confirm "+ ChatColor.GOLD + total + " ⛃" + ChatColor.DARK_GRAY + " ←").create()) );

        player.playSound(player.getLocation(), Sound.BLOCK_AMETHYST_CLUSTER_PLACE, 1, 1);

        player.spigot().sendMessage(component);
    }

}
