package de.constellate.nitroapp.waypoints;

import de.constellate.nitroapp.Main;
import de.constellate.nitroapp.utils.Config;
import org.bukkit.Location;

import java.util.UUID;

public class WaypointManager {

    Config config = Main.getInstance().getConfiguration();

    public void setWaypoint(UUID uuid, String name, Location location) {
        config.getConfig().set("waypoints.waypoint." + name.toLowerCase() + ".world", location.getWorld().getName());
        config.getConfig().set("waypoints.waypoint." + name.toLowerCase() + ".x", location.getBlockX());
        config.getConfig().set("waypoints.waypoint." + name.toLowerCase() + ".y", location.getBlockY());
        config.getConfig().set("waypoints.waypoint." + name.toLowerCase() + ".z", location.getBlockZ());
        config.getConfig().set("waypoints.owner." + name.toLowerCase(), uuid.toString());
    }

    /*public Object getWaypoint(String name) {
        if (config.getConfig().contains("waypoints.waypoint." + name.toLowerCase())) {
            return config.getConfig().get("waypoint.waypoint." + name.toLowerCase());
        } else {
            return null;
        }
    }*/

    public boolean WaypointExists(String name) {
        return config.getConfig().contains("waypoints.waypoint." + name.toLowerCase());
    }

}