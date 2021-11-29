package de.constellate.nitroapp.commands;

import de.constellate.nitroapp.Main;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TeleportCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        if (args.length == 0) {
            sendUsage(sender);
            return true;
        }

        int player_balance = BalanceCommand.getBalance(player.getUniqueId());
        UUID other_player_uuid = BalanceCommand.getUUID(args[0]);
        Player other_player = Bukkit.getPlayer(args[0]);

        if (!(other_player_uuid == null)) {

            int new_player_balance = player_balance - 500;


            if (new_player_balance >= -10) {
                sender.sendMessage(Main.getPrefix() + "Teleportation request sent to " + ChatColor.GRAY + "→ " + other_player.getDisplayName());
                sender.sendMessage(Main.getPrefix() + ChatColor.AQUA + "Do not move. The request will otherwise be cancelled.");
                player.playSound(player.getLocation(), Sound.BLOCK_AMETHYST_CLUSTER_PLACE, 1, 1);
                other_player.playSound(other_player.getLocation(), Sound.BLOCK_AMETHYST_CLUSTER_PLACE, 1, 1);
                sendRequest(player, other_player, new_player_balance);

            } else {
                sender.sendMessage(Main.getPrefix() + ChatColor.RED + "Insufficient funds.");
                return true;
            }

        } else {
            sender.sendMessage(Main.getPrefix() + ChatColor.RED + args[0] + ChatColor.GRAY + " is not online.");
            return true;
        }

        return true;
    }

    private void sendUsage(CommandSender sender) {

        TextComponent component = new TextComponent(Main.getPrefix() + "Usage: /tp <Player>" + " ┃ " + ChatColor.GRAY + "Requests a teleportation to a player.");
        component.setHoverEvent(new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GOLD + "500 ⛃").create()) );

        sender.spigot().sendMessage(component);
    }

    private void sendRequest(Player player, Player other_player, int new_player_balance) {
        TextComponent component = new TextComponent(Main.getPrefix() + player.getName() + ChatColor.GRAY + " wants to teleport to you. → " + ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "Accept" + ChatColor.DARK_GRAY + "]");
        component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaccept " + player.getName() + " " + player.getLocation().getBlockX() + player.getLocation().getBlockY() + player.getLocation().getBlockZ()));
        other_player.playSound(player.getLocation(), Sound.BLOCK_AMETHYST_CLUSTER_PLACE, 1, 1);
        other_player.spigot().sendMessage(component);
    }

}
