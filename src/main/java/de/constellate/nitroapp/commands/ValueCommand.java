package de.constellate.nitroapp.commands;

import de.constellate.nitroapp.Main;
import de.constellate.nitroapp.utils.Config;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ValueCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Config config = Main.getInstance().getConfiguration();
        Player player = (Player) sender;
        String item = player.getInventory().getItemInMainHand().getType().name();
        int amount = player.getInventory().getItemInMainHand().getAmount();

        if(args.length == 0) {

            getValue(sender, config, item, amount);

            return true;
        }

        switch (args[0]) {
            case "get": {
                getValue(sender, config, item, amount);
                break;
            }
            case "set": {
                if (player.isOp()) {
                    if (!(item.equals("AIR"))) {
                        try {
                            int value = Integer.parseInt(args[1]);
                            config.getConfig().set("item.value." + item, value);
                            sender.sendMessage(Main.getPrefix() + "Value of " + ChatColor.GREEN + item + ChatColor.GRAY + " set to " + ChatColor.GOLD + args[1] + " ⛃");
                        } catch (NumberFormatException e) {
                            sender.sendMessage(Main.getPrefix() + "Please enter a valid number.");
                        }
                    } else {
                        sender.sendMessage(Main.getPrefix() + "Air cannot be sellable.");
                    }
                } else {
                    sender.sendMessage(Main.getPrefix() + "You are not permitted to execute this command.");
                }
                break;
            }
            case "reset": {
                if (player.isOp()) {
                    config.getConfig().set("item.value." + item, null);
                    sender.sendMessage(Main.getPrefix() + "Value of " + ChatColor.GREEN + item + ChatColor.GRAY + " reset.");
                } else {
                    sender.sendMessage(Main.getPrefix() + "You are not permitted to execute this command.");
                }
            }
        }

        return true;
    }

    private void getValue(CommandSender sender, Config config, String item, int amount) {
        if(config.getConfig().contains("item.value." + item)) {
            int value = config.getConfig().getInt("item.value." + item);
            int total = value * amount;
            sender.sendMessage(Main.getPrefix() + "Value of " + ChatColor.GREEN + amount + " " + item + ChatColor.DARK_GRAY + " → " + ChatColor.GOLD + total + " ⛃");
        } else {
            sender.sendMessage(Main.getPrefix() + "Item is not sellable.");
        }
    }

}
