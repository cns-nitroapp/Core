package de.constellate.nitroapp.commands;

import de.constellate.nitroapp.Main;
import de.constellate.nitroapp.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PayCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;
        Config config = Main.getInstance().getConfiguration();

        if (args.length == 0 || args[1].length() == 0) {
            sendUsage(sender);
            return true;
        }

        UUID sender_uuid = player.getUniqueId();
        UUID recipient_uuid = BalanceCommand.getUUID(args[0]);

        if (recipient_uuid == null) {
            sender.sendMessage(Main.getPrefix() + ChatColor.RED + args[0] + ChatColor.GRAY + " is not online.");
        }
        else {

            if (config.getConfig().contains("player.balance." + recipient_uuid)) {

                try {

                    //System.out.println("Balance of " + player.getName() + ": " + BalanceCommand.getBalance(player.getUniqueId()));
                    //System.out.println("Balance of " + args[0] + ": " + BalanceCommand.getBalance(BalanceCommand.getUUID(args[0])));

                    int new_recipient_balance = BalanceCommand.getBalance(BalanceCommand.getUUID(args[0])) + Integer.parseInt(args[1]);
                    int new_sender_balance = BalanceCommand.getBalance(player.getUniqueId()) - Integer.parseInt(args[1]);

                    Player recipient = Bukkit.getPlayer(args[0]);

                    BalanceCommand.setBalance(player.getUniqueId(), new_sender_balance);
                    BalanceCommand.setBalance(BalanceCommand.getUUID(args[0]), new_recipient_balance);

                    player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_DIAMOND, 1, 1);
                    recipient.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_DIAMOND, 1, 1);

                    sender.sendMessage(Main.getPrefix() + "Sent " + ChatColor.GREEN + args[1] + "⛃" + ChatColor.GRAY + " to " + ChatColor.GREEN +  recipient.getDisplayName());
                    recipient.sendMessage(Main.getPrefix() + "Received " + ChatColor.GREEN + args[1] + " ⛃" + ChatColor.GRAY + " from " + player.getDisplayName());

                    return true;

                } catch (NumberFormatException e) {
                    sender.sendMessage(Main.getPrefix() + "Please enter a valid number.");
                    return true;
                }

            } else {
                sender.sendMessage(Main.getPrefix() + ChatColor.RED + args[0] + ChatColor.GRAY + " is not online.");
                return true;
            }
        } return true;
    }

    private void sendUsage(CommandSender sender) {
        sender.sendMessage(Main.getPrefix() + "Usage: " + ChatColor.GREEN + "/pay <Player> <Amount>");
    }

}
