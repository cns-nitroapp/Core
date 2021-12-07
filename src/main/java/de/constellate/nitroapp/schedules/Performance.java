package de.constellate.nitroapp.schedules;

import de.constellate.nitroapp.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Performance {

    public static void getPerformance() {
        double tps = Lag.getTPS();

        if (tps < 19) {
            Bukkit.broadcastMessage("§8[" + ChatColor.YELLOW + "●●" + ChatColor.GRAY + "●" + "§8] " + ChatColor.YELLOW + "Slight performance issues");
            playSound();
        } else {
            Bukkit.broadcastMessage("§8[" + ChatColor.RED + "●" + ChatColor.GRAY + "●●" + "§8] " + ChatColor.RED + "Moderate performance issues");
            playSound();
        }

    }

    private static void playSound() {
        for (Player player : Bukkit.getOnlinePlayers())
        {
            player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_HURT, 1, 1);
        }
    }

}
