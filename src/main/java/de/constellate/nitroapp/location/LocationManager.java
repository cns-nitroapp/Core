package de.constellate.nitroapp.location;

import de.constellate.nitroapp.Main;
import de.constellate.nitroapp.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;

public class LocationManager {

    public static void saveLocation(Player player) {

        Config config = Main.getInstance().getConfiguration();
        String x = String.valueOf(player.getLocation().getBlockX());
        String y = String.valueOf(player.getLocation().getBlockY());
        String z = String.valueOf(player.getLocation().getBlockZ());
        String world = player.getLocation().getWorld().getName();

        config.set("player.locations." + player.getUniqueId().toString() + ".x", x);
        config.set("player.locations." + player.getUniqueId().toString() + ".y", y);
        config.set("player.locations." + player.getUniqueId().toString() + ".z", z);
        config.set("player.locations." + player.getUniqueId().toString() + ".world", world);

    }

    public static Location getLocation(Player player) {

        Config config = Main.getInstance().getConfiguration();
        UUID uuid = player.getUniqueId();

        if (config.getConfig().getString("player.locations." + uuid) != null) {
            String x = config.getConfig().getString("player.locations." + uuid + ".x");
            String y = config.getConfig().getString("player.locations." + uuid + ".y");
            String z = config.getConfig().getString("player.locations." + uuid + ".z");
            String world = config.getConfig().getString("player.locations." + uuid + ".world");
            return new Location(Bukkit.getWorld(world), Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(z));
        } else {
            return null;
        }
    }

    public static void deleteLocation(Player player) {
        Config config = Main.getInstance().getConfiguration();
        config.set("player.locations." + player.getUniqueId(), null);
    }

}
