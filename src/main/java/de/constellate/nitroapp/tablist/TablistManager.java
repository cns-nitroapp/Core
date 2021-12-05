package de.constellate.nitroapp.tablist;

import de.constellate.nitroapp.utils.TimeManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class TablistManager {

    public void setPlayerList(Player player) {

        player.setPlayerListHeader(ChatColor.GREEN + "\n          » Nitroapp Remastered «          \n" + ChatColor.GRAY + "Welcome, " + player.getDisplayName() + "\n");

        if (TimeManager.isWeekend()) {
            player.setPlayerListFooter(ChatColor.GREEN + "\nKeep-Inventory is active\n" + ChatColor.DARK_GRAY + "\nremastered.nitroapp.de\n");
        } else {
            player.setPlayerListFooter(ChatColor.DARK_GRAY + "\nremastered.nitroapp.de\n");
        }

    }

    public void setAllPlayerTeams() {
        Bukkit.getOnlinePlayers().forEach(this::setPlayerTeams);
    }

    public void setPlayerTeams(Player player) {

        Scoreboard scoreboard = player.getScoreboard();
        Team players = scoreboard.getTeam("002players");

        if(players == null) {
            players = scoreboard.registerNewTeam("002players");
        }

        Team operators = scoreboard.getTeam("001operators");

        if (operators == null) {
            operators = scoreboard.registerNewTeam("001operators");
        }

        players.setColor(ChatColor.GRAY);

        operators.setPrefix(ChatColor.DARK_AQUA + "GM " + ChatColor.DARK_GRAY + "┃ ");
        operators.setColor(ChatColor.GRAY);


        for (Player target : Bukkit.getOnlinePlayers()) {

            if (target.isOp()) {
                operators.addEntry(target.getName());
                continue;
            }

            players.addEntry(target.getName());

        }

    }

}
