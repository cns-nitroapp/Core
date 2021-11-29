package de.constellate.nitroapp.listeners;

import de.constellate.nitroapp.Main;
import de.constellate.nitroapp.commands.BalanceCommand;
import de.constellate.nitroapp.utils.Config;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginDescriptionFile;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        Config config = Main.getInstance().getConfiguration();

        event.setJoinMessage(ChatColor.GREEN + "+ " + ChatColor.DARK_GRAY + "┃ " + ChatColor.GRAY + player.getName());

        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.DARK_GRAY + "» " + ChatColor.GRAY + "Welcome to" + ChatColor.GREEN + " Nitroapp Remastered" + ChatColor.DARK_GRAY + " «"));
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);

        if(player.isOp()) {
            player.setDisplayName(ChatColor.DARK_AQUA + "GM " + ChatColor.DARK_GRAY + "┃ " + ChatColor.GRAY + player.getName());
        }
        else {
            player.setDisplayName(ChatColor.GRAY + player.getName());
        }

        if (!config.getConfig().contains("player.balance." + player.getUniqueId() )) {
            config.getConfig().set("player.balance." + player.getUniqueId(), 0);
        }

        Main.getInstance().getTablistManager().setPlayerList(player);
        Main.getInstance().getTablistManager().setAllPlayerTeams();

        player.sendMessage(" ");
        player.sendMessage(ChatColor.DARK_GRAY + "»" + ChatColor.GRAY + " Welcome to " + ChatColor.GREEN + "Nitroapp Remastered");
        player.sendMessage(ChatColor.DARK_GRAY + "»" + ChatColor.GRAY + " Running on " + ChatColor.GREEN + "Core v1");
        player.sendMessage(" ");
        player.sendMessage(ChatColor.GRAY + "You currently have " + ChatColor.GOLD + BalanceCommand.getBalance(player.getUniqueId()) + " ⛃");
        player.sendMessage(ChatColor.GRAY + "Use " + ChatColor.GREEN + "/backpack " + ChatColor.GRAY + "to open your backpack!");
        player.sendMessage(" ");

    }

}