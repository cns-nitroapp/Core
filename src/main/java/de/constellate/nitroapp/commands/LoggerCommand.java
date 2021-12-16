package de.constellate.nitroapp.commands;

import de.constellate.nitroapp.Main;
import de.constellate.nitroapp.utils.Config;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LoggerCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Player player = (Player) sender;
        Config config = Main.getInstance().getConfiguration();

        if (args.length == 0) {
            sendUsage(player);
        } else {
            if (args[0].equalsIgnoreCase("on") || args[0].equalsIgnoreCase("enable")) {
                config.getConfig().set("server.transaction_logger", true);
                config.save();
                player.sendMessage(Main.getPrefix() + "Transaction logger enabled.");
            } else if (args[0].equalsIgnoreCase("off") || args[0].equalsIgnoreCase("disable")) {
                config.getConfig().set("server.transaction_logger", false);
                config.save();
                sender.sendMessage(Main.getPrefix() + "Transaction logger disabled.");
            } else {
                sendUsage(player);
            }
        }

        return false;
    }

    private static void sendUsage(Player player) {
        player.sendMessage(Main.getPrefix() + "/logger <on|off>");
    }

}
