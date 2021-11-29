package de.constellate.nitroapp.commands;

import de.constellate.nitroapp.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BroadcastCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {

        if ((sender.isOp()) &&
                (cmd.getName().equalsIgnoreCase("broadcast")))
        {
            if (args.length == 0)
            {
                sender.sendMessage(Main.getPrefix() + "Please specify an argument.");
                return true;
            }
            StringBuilder str = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                str.append(args[i]).append(" ");
            }
            String s = str.toString();
            String coloredString = ChatColor.translateAlternateColorCodes('&', s);
            Bukkit.broadcastMessage("§8[§e§l!§8]§7 " + coloredString);
        }
        return true;


    }

}
