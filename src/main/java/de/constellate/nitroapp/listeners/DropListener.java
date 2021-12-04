package de.constellate.nitroapp.listeners;

import de.constellate.nitroapp.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class DropListener implements Listener {

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {

        Player player = event.getPlayer();
        String dropped = event.getItemDrop().getName().toUpperCase();

        if (dropped.contains("IRON") || dropped.contains("GOLD") || dropped.contains("DIAMOND") || dropped.contains("NETHERITE")) {
            if (!player.isSneaking()) {
                event.setCancelled(true);
                player.playSound(player.getLocation(), Sound.BLOCK_AMETHYST_CLUSTER_PLACE, 1, 1);
                player.sendMessage(Main.getAssistantPrefix() + "We have prevented you from dropping a valuable item. " + ChatColor.GRAY + "Press " + ChatColor.of("#7F96FF") + "Shift + Q" + ChatColor.GRAY + " to confirm this action.");
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GRAY + "Crouch while dropping to confirm"));
            }
        }

    }

}
