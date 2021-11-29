package de.constellate.nitroapp.commands;

import de.constellate.nitroapp.Main;
import de.constellate.nitroapp.backpack.Backpack;
import de.constellate.nitroapp.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BalanceCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;
        Config config = Main.getInstance().getConfiguration();

        if(args.length == 0) {
            int balance = config.getConfig().getInt("player.balance." + player.getUniqueId());
            sender.sendMessage(Main.getPrefix() + "My balance: " + ChatColor.GOLD + balance + " ⛃");
            return true;
        }

        if (config.getConfig().contains("player.balance." + getUUID(args[0]) )) {
            int balance = config.getConfig().getInt("player.balance." + getUUID(args[0]) );
            sender.sendMessage(Main.getPrefix() + "Balance of " + args[0] + ": " + ChatColor.GOLD + balance + " ⛃");
        } else {
            sender.sendMessage(Main.getPrefix() + "Cannot get balance of " + ChatColor.RED + args[0]);
            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
        }

        return true;
    }

    public static UUID getUUID(String name) {
        if (!(Bukkit.getPlayer(name) == null)) {
            return Bukkit.getPlayer(name).getUniqueId();
        } else {
            return null;
        }
    }

    public static Integer getBalance(UUID uuid) {

        Config config = Main.getInstance().getConfiguration();

        if(config.getConfig().contains("player.balance." + uuid)) {
            return config.getConfig().getInt("player.balance." + uuid);
        } else {
            return 0;
        }
    }

    public static void setBalance(UUID uuid, Integer balance) {

        Config config = Main.getInstance().getConfiguration();
        config.getConfig().set("player.balance." + uuid, balance);

    }

}
