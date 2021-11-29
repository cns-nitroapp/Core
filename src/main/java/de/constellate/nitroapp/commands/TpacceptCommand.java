package de.constellate.nitroapp.commands;

import de.constellate.nitroapp.Main;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public class TpacceptCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;
        UUID requester_uuid = BalanceCommand.getUUID(args[0]);
        Player requester = Bukkit.getPlayer(args[0]);

        //requester.getLocation().getBlockX() + requester.getLocation().getBlockY() + requester.getLocation().getBlockZ()

        if (requester_uuid != null) {

            if (!(String.valueOf(requester.getLocation().getBlockX()) + requester.getLocation().getBlockY() + requester.getLocation().getBlockZ()).equals(String.valueOf(player.getLocation().getBlockX()) + player.getLocation().getBlockY() + player.getLocation().getBlockZ())) {

                if (args[1].equals(String.valueOf(requester.getLocation().getBlockX()) + requester.getLocation().getBlockY() + requester.getLocation().getBlockZ())) {
                    requester.teleport(player.getLocation());
                    sender.sendMessage(Main.getPrefix() + requester.getName() + ChatColor.GRAY + " has been teleported to your location.");
                    requester.sendMessage(Main.getPrefix() + "You have been teleported to " + player.getDisplayName() + ChatColor.GRAY + ".");

                    BalanceCommand.setBalance(requester_uuid, BalanceCommand.getBalance(requester_uuid) - 500);
                    BalanceCommand.setBalance(player.getUniqueId(), BalanceCommand.getBalance(player.getUniqueId()) + 20);

                    requester.playSound(requester.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);

                    requester.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 10, 20));
                    requester.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 20 * 10, 1));
                    requester.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 20 * 5, 1));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 20 * 5, 1));

                    requester.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GRAY + "Teleported" + ChatColor.DARK_GRAY + " ┃ " + ChatColor.RED + "-500 ⛃"));
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GRAY + "Accepted teleportation" + ChatColor.DARK_GRAY + " ┃ " + ChatColor.GOLD + "+20 ⛃"));

                    return true;
                } else {
                    sender.sendMessage(Main.getPrefix() + ChatColor.RED + "Request cancelled. The player has moved.");
                    requester.sendMessage(Main.getPrefix() + ChatColor.RED + "Request cancelled. You have moved.");
                    return true;
                }
            } else {
                sender.sendMessage(Main.getPrefix() + requester.getName() + " is already at your location.");
                return true;
            }
        } else {
            sender.sendMessage(Main.getPrefix() + ChatColor.RED + args[0] + ChatColor.GRAY + " is not online.");
        }

        return true;
    }

}
