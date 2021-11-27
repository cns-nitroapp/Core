package de.constellate.nitroapp.listeners;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        event.setJoinMessage(ChatColor.GREEN + "+ " + ChatColor.DARK_GRAY + "┃ " + ChatColor.GRAY + player.getName());

        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.DARK_GRAY + "» " + ChatColor.BLUE + "Welcome to Nitroapp Remastered" + ChatColor.DARK_GRAY + " «"));
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);

        if(player.isOp()) {
            player.setDisplayName(ChatColor.DARK_AQUA + "GM " + ChatColor.DARK_GRAY + "┃ " + ChatColor.GRAY + player.getName());
        }
        else {
            player.setDisplayName(ChatColor.GRAY + player.getName());
        }

    }

}