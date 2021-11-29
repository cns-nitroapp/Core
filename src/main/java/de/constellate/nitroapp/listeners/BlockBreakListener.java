package de.constellate.nitroapp.listeners;

import de.constellate.nitroapp.Main;
import de.constellate.nitroapp.commands.BalanceCommand;
import de.constellate.nitroapp.utils.Config;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerHarvestBlockEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MainHand;

public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {

        Config config = Main.getInstance().getConfiguration();
        Player player = event.getPlayer();

        String item = player.getInventory().getItemInMainHand().getType().name();

        if (item.endsWith("PICKAXE") || item.endsWith("SHOVEL") || item.endsWith("AXE")) {

            int player_balance = BalanceCommand.getBalance(player.getUniqueId());

            BalanceCommand.setBalance(player.getUniqueId(), player_balance + 1);

            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GRAY + "Block mined" + ChatColor.DARK_GRAY + " ┃ " + ChatColor.GOLD + "+1 ⛃"));
            config.save();
        }

    }

}
