package de.constellate.nitroapp.commands;

import de.constellate.nitroapp.Main;
import de.constellate.nitroapp.utils.Config;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class VendorCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Config config = Main.getInstance().getConfiguration();
        Player player = (Player) sender;
        String item = player.getInventory().getItemInMainHand().getType().name();

        if(args.length == 0) {
            sendUsage(sender);
            return true;
        } else if (args[0].equals("set")) {
            config.getConfig().set("item.vendor_value." + item + ".name", item.substring(0, 1).toUpperCase() + item.substring(1));
            config.getConfig().set("item.vendor_value." + item + ".price", Integer.parseInt(args[1]));
            sender.sendMessage(Main.getPrefix() + ChatColor.GREEN + item + ChatColor.GRAY + " added to the vendors list at " + ChatColor.GOLD + args[1] + " ⛃");
        } else if (args[0].equals("remove")) {
            config.getConfig().set("item.vendor_value." + item, null);
            sender.sendMessage(Main.getPrefix() + ChatColor.GREEN + item + ChatColor.GRAY + " has been deleted from the vendors list.");
        } else {
            sendUsage(sender);
        }

        return true;
    }

    private void sendUsage(CommandSender sender) {
        sender.sendMessage(Main.getPrefix() + "Usage:");
        sender.sendMessage(Main.getPrefix() + ChatColor.GREEN + "/vendor set <Price>" + ChatColor.DARK_GRAY + " ┃ " + ChatColor.GRAY + "Sets a selling price for an item.");
        sender.sendMessage(Main.getPrefix() + ChatColor.GREEN + "/vendor remove" + ChatColor.DARK_GRAY + " ┃ " + ChatColor.GRAY + "Removes an item from the vendors list.");
    }

}
