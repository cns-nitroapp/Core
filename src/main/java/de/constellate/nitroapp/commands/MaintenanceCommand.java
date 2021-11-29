package de.constellate.nitroapp.commands;

import de.constellate.nitroapp.Main;
import de.constellate.nitroapp.maintenance.MaintenanceManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MaintenanceCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;
        MaintenanceManager maintenance = new MaintenanceManager();

        if (args.length == 0) {

            if (maintenance.getMaintenance()) {
                sender.sendMessage(Main.getPrefix() + "Maintenance mode is currently" + ChatColor.GREEN + " enabled.");
            } else {
                sender.sendMessage(Main.getPrefix() + "Maintenance mode is currently" + ChatColor.RED + " disabled.");
            }

            return true;

        } else {
            if (args[0].equals("set")) {
                if (args[1].equals("true")) {
                    maintenance.setMaintenance(true);
                    sender.sendMessage(Main.getPrefix() + "Maintenance mode" + ChatColor.GREEN + " enabled.");
                    return true;
                } else if (args[1].equals("false")) {
                    maintenance.setMaintenance(false);
                    sender.sendMessage(Main.getPrefix() + "Maintenance mode" + ChatColor.RED + " disabled.");
                    return true;
                } else {
                    sender.sendMessage(Main.getPrefix() + "Invalid parameters. Please use either true or false.");
                    return true;
                }
            } else {
                sender.sendMessage(Main.getPrefix() + "Invalid parameters.");
                return true;
            }
        }
    }

}
