package de.constellate.nitroapp.listeners;

import de.constellate.nitroapp.Main;
import de.constellate.nitroapp.utils.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerListPingListener implements Listener {

    @EventHandler
    public void onPing(ServerListPingEvent event){

        Config config = Main.getInstance().getConfiguration();

        if (config.getConfig().contains("server.maintenance")) {

            if (config.getConfig().getBoolean("server.maintenance")) {
                event.setMotd("§8» §aNitroapp Remastered §8[§a1.17.1§8]§r\n§8» §cWe are currently in maintenance");
            } else {
                event.setMotd("§8» §aNitroapp Remastered §8[§a1.17.1§8]§r\n§8» §7Minecraft Gameserver");
            }

        } else {
            event.setMotd("§8» §aNitroapp Remastered §8[§a1.17.1§8]§r\n§8» §c§lInvalid configuration");
        }

    }

}
