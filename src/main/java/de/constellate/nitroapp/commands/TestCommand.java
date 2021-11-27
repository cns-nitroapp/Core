package de.constellate.nitroapp.commands;

import de.constellate.nitroapp.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        sender.sendMessage(Main.getPrefix() + "Test successful!");

        return false;
    }
}
