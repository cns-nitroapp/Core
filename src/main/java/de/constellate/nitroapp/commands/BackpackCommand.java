package de.constellate.nitroapp.commands;

import de.constellate.nitroapp.Main;
import de.constellate.nitroapp.backpack.Backpack;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class BackpackCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {

            openBackpack(player, null);
            return true;
        } else {
            if (sender.hasPermission("nitroapp.backpack.others")) {
                Player target = Bukkit.getPlayer(args[0]);

                if (target == null) {
                    sender.sendMessage(Main.getPrefix() + ChatColor.RED + "Player not found.");
                } else {
                    openBackpack(player, target);
                }

            } else {
                openBackpack((Player) sender, null);
            }
        }

        return true;

    }

    public static void openBackpack(Player player, Player target) {
        player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_LEATHER, 1, 1);
        Backpack backpack;
        if (target != null) {
            backpack = Main.getInstance().getBackpackManager().getBackpack(target.getUniqueId());
        } else {
            backpack = Main.getInstance().getBackpackManager().getBackpack(player.getUniqueId());
        }
        player.openInventory(backpack.getInventory());
    }

}
