package de.constellate.nitroapp.commands;

import de.constellate.nitroapp.Main;
import de.constellate.nitroapp.location.LocationManager;
import de.constellate.nitroapp.utils.TransactionLogger;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class BackCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Player player = (Player) sender;
        Location location = LocationManager.getLocation(player);

        if (location != null) {

            int balance = BalanceCommand.getBalance(player.getUniqueId());
            int cost = 1000;

            if (balance >= 1000) {
                BalanceCommand.setBalance(player.getUniqueId(), balance - cost);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GRAY + "Teleported back" + ChatColor.DARK_GRAY + " ┃ " + ChatColor.RED + "-1000 ⛃"));
                //LocationManager.saveLocation(player);
                player.teleport(location);
                LocationManager.deleteLocation(player);
                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                player.sendMessage(Main.getPrefix() + "You have been teleported to your last location.");
                TransactionLogger.logTransaction(player.getName(), player.getUniqueId(), "Nitroapp", UUID.fromString("6e60018f-f59b-4148-b233-9055d3430051"), 1000);
            } else {
                player.sendMessage(Main.getPrefix() + ChatColor.RED + "Insufficient funds.");
                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            }
        } else {
            player.sendMessage(Main.getPrefix() + ChatColor.RED + "Your last location has expired.");
            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
        }

        return false;
    }

}
