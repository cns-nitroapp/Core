package de.constellate.nitroapp.commands;

import de.constellate.nitroapp.Main;
import de.constellate.nitroapp.utils.Config;
import de.constellate.nitroapp.waypoints.WaypointManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Locale;

public class WaypointCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;
        Config config = Main.getInstance().getConfiguration();
        WaypointManager waypoint = new WaypointManager();

        if(args.length == 0) {
            sendUsage(player);
        } else {

            switch (args[0]) {
                case "create": {
                    if (!(args[1] == null)) {
                        int balance = BalanceCommand.getBalance(player.getUniqueId());

                        if (balance >= 50000) {
                            waypoint.setWaypoint(player.getUniqueId(), args[1], player.getLocation());
                            BalanceCommand.setBalance(player.getUniqueId(),BalanceCommand.getBalance(player.getUniqueId()) - 50000);
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GRAY + "Waypoint created" + ChatColor.DARK_GRAY + " ┃ " + ChatColor.RED + "-50000 ⛃"));
                            player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1, 1);
                            player.sendMessage(Main.getPrefix() + "Waypoint " + ChatColor.GREEN + args[1] + ChatColor.GRAY + " has been created!");
                        } else {
                            player.sendMessage(Main.getPrefix() + ChatColor.RED + "Insufficient funds.");
                        }

                        break;
                    } else {
                        sendUsage(sender);
                        break;
                    }
                }
                default: {
                    if (waypoint.WaypointExists(args[0])) {
                        String world_arg = args[0].toLowerCase();
                        World world = Bukkit.getWorld(config.getConfig().getString("waypoints.waypoint." + world_arg + ".world"));
                        int x = config.getConfig().getInt("waypoints.waypoint." + world_arg + ".x");
                        int y = config.getConfig().getInt("waypoints.waypoint." + world_arg + ".y");
                        int z = config.getConfig().getInt("waypoints.waypoint." + world_arg + ".z");
                        Location location = new Location(world, x, y, z);
                        String wp_name = args[0];
                        player.teleport(location);
                        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 10, 20));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 20 * 10, 1));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 20 * 5, 1));
                        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                        player.sendMessage(Main.getPrefix() + "You have been teleported to " + ChatColor.GREEN + wp_name.substring(0, 1).toUpperCase() + wp_name.substring(1));

                    } else {
                        sender.sendMessage(Main.getPrefix() + "Waypoint " + ChatColor.GREEN + args[0] + ChatColor.GRAY + " does not exist.");
                    }
                    break;
                }
            }

        }

        return false;
    }

    private void sendUsage(CommandSender sender) {

        TextComponent waypointCreate = new TextComponent(Main.getPrefix() + ChatColor.GREEN + "/waypoint create <Name>" + ChatColor.DARK_GRAY + " ┃ " + ChatColor.GRAY + "Create a public waypoint.");
        waypointCreate.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/waypoint create"));
        waypointCreate.setHoverEvent(new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GOLD + "50000 ⛃").create()) );

        TextComponent waypointTeleport = new TextComponent(Main.getPrefix() + ChatColor.GREEN + "/waypoint <Name>" + ChatColor.DARK_GRAY + " ┃ " + ChatColor.GRAY + "Teleport to a public waypoint.");
        waypointTeleport.setHoverEvent(new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GOLD + "100 ⛃").create()) );

        sender.sendMessage(Main.getPrefix() + "Usage: ");
        sender.spigot().sendMessage(waypointTeleport);
        sender.spigot().sendMessage(waypointCreate);
    }

}
