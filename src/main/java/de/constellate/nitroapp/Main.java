package de.constellate.nitroapp;

import de.constellate.nitroapp.backpack.BackpackManager;
import de.constellate.nitroapp.commands.BackpackCommand;
import de.constellate.nitroapp.commands.BalanceCommand;
import de.constellate.nitroapp.commands.PayCommand;
import de.constellate.nitroapp.commands.TestCommand;
import de.constellate.nitroapp.listeners.BlockBreakListener;
import de.constellate.nitroapp.listeners.ChatListener;
import de.constellate.nitroapp.listeners.JoinListener;
import de.constellate.nitroapp.listeners.QuitListener;
import de.constellate.nitroapp.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;


public final class Main extends JavaPlugin {

    private static Main instance;
    private Config config;
    private BackpackManager backpackManager;

    @Override
    public void onLoad() {
        instance = this;
        config = new Config();
    }

    @Override
    public void onEnable() {
        Bukkit.getLogger().fine("Activating Nitroapp-Core");

        listenerRegistration();
        commandRegistration();

        backpackManager = new BackpackManager();

    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().fine("Deactivating Nitroapp-Core");
        backpackManager.save();
        config.save();
    }

    public static Main getInstance() {
        return instance;
    }

    public Config getConfiguration() {
        return config;
    }

    public BackpackManager getBackpackManager() {
        return backpackManager;
    }

    public static String getPrefix() {
        return ChatColor.BLUE + "System " + ChatColor.DARK_GRAY + "┃ " + ChatColor.GRAY;
    }

    private void listenerRegistration() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new JoinListener(), this);
        pluginManager.registerEvents(new QuitListener(), this);
        pluginManager.registerEvents(new ChatListener(), this);
        pluginManager.registerEvents(new BlockBreakListener(), this);
    }

    private void commandRegistration() {
        getCommand("test").setExecutor(new TestCommand());
        getCommand("backpack").setExecutor(new BackpackCommand());
        getCommand("balance").setExecutor(new BalanceCommand());
        getCommand("pay").setExecutor(new PayCommand());
    }
}
