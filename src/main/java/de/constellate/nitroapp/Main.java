package de.constellate.nitroapp;

import de.constellate.nitroapp.backpack.BackpackManager;
import de.constellate.nitroapp.commands.*;
import de.constellate.nitroapp.listeners.*;
import de.constellate.nitroapp.schedules.Lag;
import de.constellate.nitroapp.schedules.Performance;
import de.constellate.nitroapp.tablist.TablistManager;
import de.constellate.nitroapp.utils.Config;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;


public final class Main extends JavaPlugin {

    private static Main instance;
    private Config config;
    private Config vendor;
    private BackpackManager backpackManager;
    private TablistManager tablistManager;

    @Override
    public void onLoad() {
        instance = this;
        config = new Config("config");
        //vendor = new Config("vendor");
    }

    @Override
    public void onEnable() {
        Bukkit.getLogger().fine("Activating Nitroapp-Core");

        listenerRegistration();
        commandRegistration();
        schedulerRegistration();

        backpackManager = new BackpackManager();
        tablistManager = new TablistManager();

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

    public TablistManager getTablistManager() { return tablistManager; }

    public static String getPrefix() {
        return ChatColor.BLUE + "System " + ChatColor.DARK_GRAY + "┃ " + ChatColor.GRAY;
    }

    public static String getWarningPrefix() {
        return ChatColor.RED + "Warning " + ChatColor.DARK_GRAY + "┃ " + ChatColor.GRAY;
    }

    public static String getInfoPrefix() {
        return ChatColor.YELLOW + "Info " + ChatColor.DARK_GRAY + "┃ " + ChatColor.GRAY;
    }

    public static String getAssistantPrefix() {
        return ChatColor.of("#7F96FF") + "Assistant " + ChatColor.DARK_GRAY + "┃ " + ChatColor.GRAY;
    }

    public static String getGuiSuffix() {
        return ChatColor.DARK_GRAY + " [" + ChatColor.GREEN + "Core" + ChatColor.DARK_GRAY + "]";
    }

    private void listenerRegistration() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new JoinListener(), this);
        pluginManager.registerEvents(new QuitListener(), this);
        pluginManager.registerEvents(new ChatListener(), this);
        pluginManager.registerEvents(new BlockBreakListener(), this);
        pluginManager.registerEvents(new ServerListPingListener(), this);
        pluginManager.registerEvents(new PlayerLoginListener(), this);
        pluginManager.registerEvents(new InventoryListener(), this);
        pluginManager.registerEvents(new PlayerDeathListener(), this);
        pluginManager.registerEvents(new DropListener(), this);
    }

    private void commandRegistration() {
        getCommand("test").setExecutor(new TestCommand());
        getCommand("backpack").setExecutor(new BackpackCommand());
        getCommand("balance").setExecutor(new BalanceCommand());
        getCommand("pay").setExecutor(new PayCommand());
        getCommand("sell").setExecutor(new SellCommand());
        getCommand("value").setExecutor(new ValueCommand());
        getCommand("waypoint").setExecutor(new WaypointCommand());
        getCommand("tp").setExecutor(new TeleportCommand());
        getCommand("tpaccept").setExecutor(new TpacceptCommand());
        getCommand("maintenance").setExecutor(new MaintenanceCommand());
        getCommand("broadcast").setExecutor(new BroadcastCommand());
        getCommand("vendor").setExecutor(new VendorCommand());
    }

    private void schedulerRegistration() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Lag(), 100L, 1L);

        new BukkitRunnable() {
            public void run() {
                Performance.getPerformance();
            }
        }.runTaskTimer(this, 0L, 6000L);

    }

}
