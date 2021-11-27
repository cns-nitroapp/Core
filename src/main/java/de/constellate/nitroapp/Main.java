package de.constellate.nitroapp;

import de.constellate.nitroapp.commands.TestCommand;
import de.constellate.nitroapp.listeners.ChatListener;
import de.constellate.nitroapp.listeners.JoinListener;
import de.constellate.nitroapp.listeners.QuitListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getLogger().fine("Activating Nitroapp-Core");

        listenerRegistration();
        commandRegistration();

    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().fine("Deactivating Nitroapp-Core");
    }

    public static String getPrefix() {
        return ChatColor.BLUE + "System " + ChatColor.DARK_GRAY + "┃ " + ChatColor.GRAY;
    }

    private void listenerRegistration() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new JoinListener(), this);
        pluginManager.registerEvents(new QuitListener(), this);
        pluginManager.registerEvents(new ChatListener(), this);
    }

    private void commandRegistration() {
        getCommand("test").setExecutor(new TestCommand());
    }
}
