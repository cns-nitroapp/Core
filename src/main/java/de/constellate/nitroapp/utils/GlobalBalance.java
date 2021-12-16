package de.constellate.nitroapp.utils;

import com.google.gson.JsonObject;
import de.constellate.nitroapp.Main;
import de.constellate.nitroapp.inventory.SelectionInventory;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.GuiItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import javax.net.ssl.HttpsURLConnection;
import java.io.OutputStream;
import java.net.URL;

public class GlobalBalance {

    public static void logData() {

        Config config = Main.getInstance().getConfiguration();
        if (config.getConfig().contains("transaction_logger")) {
            if (config.getConfig().getBoolean("transaction_logger")) {
                ConfigurationSection sec = config.getConfig().getConfigurationSection("player.balance");

                int total = 0;
                int amount = 0;

                for (String key : sec.getKeys(false)) {
                    total = total + config.getConfig().getInt("player.balance." + key);
                    amount++;
                }

                JsonObject json = new JsonObject();
                json.addProperty("total", total);
                json.addProperty("average", total / amount);
                json.addProperty("amount", amount);

                API.call("/global", json);
            }
        }


    }

}
