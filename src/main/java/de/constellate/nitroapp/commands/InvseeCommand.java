package de.constellate.nitroapp.commands;

import de.constellate.nitroapp.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class InvseeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length == 0) {
            sender.sendMessage(Main.getPrefix() + "/inventory <player>");
        } else {
            Player player = (Player) sender;
            Player target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                sender.sendMessage(Main.getPrefix() + "Player not found.");
            } else {
                player.openInventory(target.getInventory());
            }

        }

        return false;
    }
}
